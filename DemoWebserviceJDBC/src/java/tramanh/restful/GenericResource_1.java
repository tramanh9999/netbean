/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tramanh.restful;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import tramanh.restful.dao.RegistrationDAO;
import tramanh.restful.dao.RegistrationDTO;

/**
 * REST Web Service
 *
 * @author ADMIN
 */
@Path("generic")
public class GenericResource_1 {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericResource_1
     */
    public GenericResource_1() {
    }

    /**
     * Retrieves representation of an instance of
     * tramanh.restful.GenericResource_1
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    public String getText() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of GenericResource_1
     *
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("text/plain")
    public void putText(String content) {
    }

    @Path("/checkLogin")
    @GET
    @Produces("text/plain")
    public String checkLogin(@QueryParam("username") String username, @QueryParam("password") String password) {
        String result = "failed";
        try {
            RegistrationDAO dao = new RegistrationDAO();

            result = dao.checkLogin(username, password);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Path("/findByName")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<RegistrationDTO> findByLikeName(@QueryParam("name") String search) {
        List<RegistrationDTO> result;
        RegistrationDAO dao = new RegistrationDAO();
        result = dao.findByLikeName(search);
        return result;
    }
}
