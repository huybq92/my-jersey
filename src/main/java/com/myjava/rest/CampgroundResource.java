package com.myjava.rest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.myjava.models.CampgroundEntity;

import javax.persistence.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;

@Path("")
public class CampgroundResource {
    @GET
    @Path("campground/{camp_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOneCampground(@PathParam("camp_id") String camp_id) {
        int responseCode = 202; //accepted
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("yelpcamp");
        EntityManager em = emfactory.createEntityManager();
        String jsonData = "";
        try {
            TypedQuery<CampgroundEntity> query = em.createQuery("FROM CampgroundEntity c WHERE c.campId=?1", CampgroundEntity.class); //# Introduction since JPA 2.x: defines straight away what to return, avoid continuous Casting.
            query.setParameter(1, Integer.parseInt(camp_id));
            CampgroundEntity campground = query.getSingleResult();
            //# Convert to Json
            if (campground != null) {
                jsonData = new Gson().toJson(campground, CampgroundEntity.class); //# Serialization no need Entity.class
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
                } else {
                    return Response.ok() // code 200
                            .entity(jsonData)
                            .type(MediaType.APPLICATION_JSON_TYPE)
                            .build();
                }
            }
        }
    }

    @GET
    @Path("campground")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCampgrounds() {
        int responseCode = 202; //accepted
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("yelpcamp");
        EntityManager em = emfactory.createEntityManager();
        String jsonData = "";
        try {
            Query query = em.createQuery("FROM CampgroundEntity c");
            List<CampgroundEntity> allCampgroundsList = (List<CampgroundEntity>) query.getResultList();
            //# Convert to Json
            if (allCampgroundsList != null) {
                jsonData = new Gson().toJson(allCampgroundsList, new TypeToken<List<CampgroundEntity>>() {
                }.getType());
            }
            if (!jsonData.equals("")) {
                responseCode = 200; //succeeded
            } else {
                responseCode = 404; //not found
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
                return Response.ok() // code 200
                        .entity(jsonData)
                        .type(MediaType.APPLICATION_JSON_TYPE)
                        .build();
            }
        }
    }
}