package com.myjava.rest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.myjava.models.CommentEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@Path("comment")
public class CommentResource {
    @GET
    @Path("{camp_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllComments(@PathParam("camp_id") String camp_id) {
        int responseCode = 202; //accepted
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("yelpcamp");
        EntityManager em = emfactory.createEntityManager();
        List<CommentEntity> allComments;
        String jsonData = "";
        try {
            Query sqlQuery = em.createQuery("FROM CommentEntity c WHERE c.campgroundCampId=?1");
            sqlQuery.setParameter(1, Integer.parseInt(camp_id));
            allComments = (List<CommentEntity>) sqlQuery.getResultList();
            //# Convert to Json
            if (allComments != null) {
                jsonData = new Gson().toJson(allComments, new TypeToken<List<CommentEntity>>() {
                }.getType());
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

    @POST
    @Path("{camp_id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    //# Method execution takes place inside a transaction => no need return data along with response => response code 204
    public Response createComment(@PathParam("camp_id") String camp_id, InputStream incomingData) {
        int responseCode = 202; //accepted
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("yelpcamp");
        EntityManager em = emfactory.createEntityManager();
        StringBuilder data = new StringBuilder(); //holds Json data from request
        try {
            String line;
            //# Generate Json string from Buffer Reader
            BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
            while ((line = in.readLine()) != null) {
                data.append(line);
            }
            in.close();
            //# Convert to Persistence object
            CommentEntity newComment = new Gson().fromJson(data.toString(), CommentEntity.class); //deserialization needs Entity.class
            if (newComment != null) {
                //# Check if camp_id equals newComment.getCampgroundCampId
                if (Integer.parseInt(camp_id) == newComment.getCampgroundCampId()) {
                    //# Persist Persistence object to DB
                    em.getTransaction().begin();
                    em.persist(newComment);
                    em.getTransaction().commit();
                    responseCode = 201; //created
                } else {
                    responseCode = 400; //bad request: fake data from request
                }
            } else {
                responseCode = 400; //bad request
            }
        } catch (Exception ex) {
            if (em.getTransaction() != null) {
                em.getTransaction().rollback(); //must rollback in case of error
            }
            responseCode = 500; //server internal error
        } finally {
            try {
                em.close();
                emfactory.close();
            } finally {
                return Response.status(responseCode).build(); //should return 201 because the request is handled synchronously, not async
            }
        }
    }
}
