/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paperpark.servlet;

import com.paperpark.contants.ConfigConstants;
import com.paperpark.dao.model.ModelDAO;
import com.paperpark.entity.Model;
import com.paperpark.entity.Tag;
import com.paperpark.models.ModelDetail;
import com.paperpark.models.ModelList;
import com.paperpark.utils.JAXBUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author NhanTT
 */
@WebServlet(name = "ModelDetailServlet", urlPatterns = {"/model"})
public class ModelDetailServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String idString = request.getParameter("id");
        try (PrintWriter out = response.getWriter()) {
            int id = Integer.parseInt(idString);

            Model mainModel = new Model();

            ModelList relatedModelList = getRelatedModelListAndMainModel(request, mainModel, id);

            ModelDetail modelDetail = new ModelDetail(mainModel, relatedModelList);

            String modelDetailXml = JAXBUtils.marshall(modelDetail, ModelDetail.class);

            out.write(modelDetailXml);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            Logger.getLogger(ModelDetailServlet.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private ModelList getRelatedModelListAndMainModel(HttpServletRequest request, Model returnMainModel, int id) {
        HttpSession session = request.getSession();

        List<Model> allModels = (List<Model>) session.getAttribute("MODELS");
        if (allModels == null) {
            ServletContext context = session.getServletContext();
            ModelDAO modelDAO = ModelDAO.getInstance();
            allModels = modelDAO.getAllModels(context);
        }

        int modelId = id;

        Model mainModel = null;
        for (int i = 0; i < allModels.size(); ++i) {
            Model model = allModels.get(i);
            if (model.getId() == modelId) {
                mainModel = model;
                break;
            }
        }
        
        returnMainModel.copyValueOf(mainModel);

        List<Pair<Double, Model>> cosineArr = new ArrayList<>();
        for (int i = 0; i < allModels.size(); ++i) {
            Model model = allModels.get(i);
            if (Objects.equals(model.getId(), mainModel.getId())) {
                continue;
            }
            
            Double cosine = calculateCosine(mainModel, model);
            cosineArr.add(new Pair<>(cosine, model));
        }

        Collections.sort(cosineArr, 
                (Pair<Double, Model> o1, Pair<Double, Model> o2) -> 
                        o2.getKey().compareTo(o1.getKey()));
        
        // get top Config.MAX model from cosine array
        int upperBound = Math.min(ConfigConstants.MAX_RELATED_MODELS, allModels.size());
        List<Model> relatedModels = new ArrayList<>();
        
        for (int i = 0; i < upperBound; ++i) {
            Model relatedModel = cosineArr.get(i).getValue();
            relatedModels.add(relatedModel);
        }
        
        ModelList relatedModelList = new ModelList(relatedModels);
        
        return relatedModelList;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    /**
     * calculate cosine similarity of two models use the vectors [name, sheets,
     * parts, difficulty, estimateTime, category, tags]
     *
     * @param mainModel
     * @param model
     * @return
     */
    private Double calculateCosine(Model mainModel, Model model) {
        double[] mainModelVector = new double[7];
        double[] modelVector = new double[7];

        String[] mainSplittedName = mainModel.getName().split("\\s+");
        String[] splittedName = model.getName().split("\\s+");
        mainModelVector[0] = mainSplittedName.length;
        modelVector[0] = countSimilarStrings(mainSplittedName, splittedName);

        mainModelVector[1] = mainModel.getNumOfSheets();
        modelVector[1] = model.getNumOfSheets();

        mainModelVector[2] = mainModel.getNumOfParts() != null ? mainModel.getNumOfParts() : 0;
        modelVector[2] = model.getNumOfParts() != null ? model.getNumOfParts() : 0;

        mainModelVector[3] = mainModel.getDifficulty();
        modelVector[3] = model.getDifficulty();

        mainModelVector[4] = mainModel.getEstimateTime() != null ? mainModel.getEstimateTime() : 0;
        modelVector[4] = model.getEstimateTime() != null ? model.getEstimateTime() : 0;

        mainModelVector[5] = 1;
        modelVector[5] = mainModel.getCategoryId().equals(model.getCategoryId()) ? 1 : 0;

        mainModelVector[6] = mainModel.getTagCollection().size();
        modelVector[6] = countSimilarTags(mainModel.getTagCollection(), model.getTagCollection());

        double cosine = -1;
        double numerator = 0;
        for (int i = 0; i < mainModelVector.length; ++i) {
            numerator += mainModelVector[i] * modelVector[i];
        }
        
        double denominator = 0, mainMagnitude = 0, magnitude = 0;
        for (int i = 0; i < mainModelVector.length; ++i) {
            mainMagnitude += mainModelVector[i] * mainModelVector[i];
            magnitude += modelVector[i] * modelVector[i];
        }
        denominator = Math.sqrt(mainMagnitude * magnitude);
        
        cosine = numerator / denominator;

        return cosine;
    }

    private double countSimilarStrings(String[] mainSplittedName, String[] splittedName) {
        boolean[] check = new boolean[splittedName.length];
        for (int i = 0; i < check.length; ++i) {
            check[i] = false;
        }

        double count = 0;
        for (int i = 0; i < mainSplittedName.length; ++i) {
            for (int j = 0; j < splittedName.length; ++j) {
                if (!check[j] && mainSplittedName[i].equalsIgnoreCase(splittedName[j])) {
                    check[j] = true;
                    ++count;
                    break;
                }
            }
        }

        return count;
    }

    private double countSimilarTags(Collection<Tag> mainTags, Collection<Tag> tags) {
        double count = 0;
        for (Tag tag : tags) {
            if (mainTags.contains(tag)) {
                ++count;
            }
        }

        return count;
    }
    
}
