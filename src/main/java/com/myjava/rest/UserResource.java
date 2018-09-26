package com.myjava.rest;

import com.google.gson.Gson;
import com.myjava.models.UserEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("user")
public class UserResource {
    @GET
    @Path("{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("username") String username) {
        int responseCode = 202; //accepted
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("yelpcamp");
        EntityManager em = emfactory.createEntityManager();
        String jsonData = "";
        try {
            TypedQuery<UserEntity> query = em.createQuery("FROM UserEntity u WHERE u.username=?1", UserEntity.class);
            query.setParameter(1, username);
            UserEntity user = query.getSingleResult();
            //# Convert to String
            if (user != null) {
                jsonData = new Gson().toJson(user, UserEntity.class); //serialization
            } else {
                responseCode = 404; //not found
            }
            if (!jsonData.equals("")) {
                responseCode = 200; //succeeded
            }
        } catch (Exception ex) {
            responseCode = 500;
        } finally {
            try {
                em.close();
                emfactory.close();
            } finally {
                if (responseCode != 200) {
                    return Response.status(responseCode).build();
                }
                return Response.ok()
                        .entity(jsonData)
                        .type(MediaType.APPLICATION_JSON_TYPE)
                        .build();
            }
        }
    }
}