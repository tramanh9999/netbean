/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.domdemo.servlet.dom;

import com.domdemo.util.XMLUtility;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author ADMIN
 */
public class AddController extends HttpServlet {

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
    String xmlFile = "/WEB-INF/student_account.xml";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    //create element 
    // call dom and add elment 
    // write doc back to filepath
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

        
        
        
        String status = request.getParameter("txtst");
        
        String firstname = request.getParameter("txtfn");
        String middlename = request.getParameter("txtmn");
        String lastname = request.getParameter("txtln");
        String sex = request.getParameter("txtsex");
        String address = request.getParameter("txtadd");

        String realpath = getServletContext().getRealPath("/");
        String filepath = realpath + xmlFile;
        Document doc = XMLUtility.parseFileToDOM(filepath);
        if (doc != null) {
            Element studentElement = (Element) doc.createElement("student");
// remove break line in Students Node
            XMLUtility.removeBreakLineNode(doc);
            Node studentsNode = doc.getFirstChild();
            NodeList studentsListNode = studentsNode.getChildNodes();

            Element ele= null;
            studentsListNode.item(0).appendChild(ele);
            for (int x = 0; x < studentsListNode.getLength(); x++) {
                Node temNode = studentsListNode.item(x);

                System.out.println(temNode.getNodeName() + ":" + temNode.getTextContent());
            }

            Node cloneStudentNode = doc.getFirstChild().getFirstChild().cloneNode(true);

            //set randomString to id of student node
            NodeList nl = cloneStudentNode.getChildNodes();
            int sz = nl.getLength();

            NamedNodeMap attrs = ((Element) cloneStudentNode).getAttributes();

            Node idAtr = attrs.getNamedItem("id");
            idAtr.setNodeValue(randomString());

            NodeList studentChildNodes = cloneStudentNode.getChildNodes();

            for (int j = 0; j < studentChildNodes.getLength(); j++) {
                Node childnode = studentChildNodes.item(j);

                String nodeName = childnode.getNodeName();

                switch (nodeName) {
                    case "address":
                        childnode.setTextContent(address);
                        break;
                    case "firstname":
                        childnode.setTextContent(firstname);
                        break;
                    case "middlename":
                        childnode.setTextContent(middlename);
                        break;
                    case "lastname":
                        childnode.setTextContent(lastname);
                        break;
                    case "sex":
                        childnode.setTextContent(sex);
                        break;
                    case "status":
                        childnode.setTextContent(status);
                        break;

                }
            }

            studentElement.setAttribute("class", "XML");
            studentElement.setAttribute("id", randomString());

            Element tempElement = doc.createElement("address");
            tempElement.setTextContent(address);
            studentElement.appendChild(tempElement);

            tempElement = doc.createElement("firstname");
            tempElement.setTextContent(firstname);
            studentElement.appendChild((Node) tempElement);

            tempElement = doc.createElement("middlename");
            tempElement.setTextContent(middlename);
            studentElement.appendChild((Node) tempElement);

            tempElement = doc.createElement("lastname");
            tempElement.setTextContent(lastname);
            studentElement.appendChild((Node) tempElement);

            tempElement = doc.createElement("sex");
            tempElement.setTextContent(sex);
            studentElement.appendChild((Node) tempElement);
            
             tempElement = doc.createElement("status");
            tempElement.setTextContent(status);
            studentElement.appendChild((Node) tempElement);
            Node students = doc.getFirstChild();
            students.appendChild((Node) studentElement);
            XMLUtility.tranformDOMToXMLFile(doc, filepath);
            request.getRequestDispatcher("student.jsp").forward(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    String randomString() {
        String sample = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890";

        int max = sample.length() - 1;

        int sizeString = 6;
        String returnString = "";
        for (int i = 0; i < sizeString; i++) {
            int randomitem = (int) (max * Math.random());
            returnString += sample.charAt(randomitem);
        }
        return returnString;
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
