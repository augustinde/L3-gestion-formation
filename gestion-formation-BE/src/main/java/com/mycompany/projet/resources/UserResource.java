/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projet.resources;

import com.mycompany.projet.ejb.UserGestionnary;
import com.mycompany.projet.entities.Message;
import com.mycompany.projet.entities.User;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author valen
 */
@Path("/user")
public class UserResource {

    @EJB
    private UserGestionnary userGestionnary;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/login/{username}/{password}")
    public Object testValue(@PathParam("username") String username, @PathParam("password") String password) {
        if (username != null && password != null) {
            try {
                username = URLDecoder.decode(username, "UTF-8");
                password = URLDecoder.decode(password, "UTF-8");
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(QuestionResource.class.getName()).log(Level.SEVERE, null, ex);
            }

            User user = userGestionnary.requestUser(username, password);
            if (user != null) {
                return user;
            } else {
                return new Message("error", "Identifiant ou mot de passe incorrect.");
            }
        } else {
            return new Message("error", "Vous devez renseigner tous les champs.");
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/register/{username}/{email}/{password}")
    public Object testValue(@PathParam("username") String username, @PathParam("email") String email, @PathParam("password") String password) {
        if (username != null && password != null && email != null) {

            try {
                username = URLDecoder.decode(username, "UTF-8");
                email = URLDecoder.decode(email, "UTF-8");
                password = URLDecoder.decode(password, "UTF-8");
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(QuestionResource.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (!userGestionnary.existUser(username)) {
                userGestionnary.createUser(new User(username, email, password, "visitor"));
                return new Message("success", "L'utilisateur a bien été enregistré.");
            } else {
                return new Message("error", "L'utilisateur existe déjà.");
            }
        } else {
            return new Message("error", "Vous devez renseigner tous les champs.");
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/update/{id}/{username}/{email}/{password}")
    public Object testValue(@PathParam("id") int id, @PathParam("username") String username, @PathParam("email") String email, @PathParam("password") String password) {

        try {
            username = URLDecoder.decode(username, "UTF-8");
            email = URLDecoder.decode(email, "UTF-8");
            password = URLDecoder.decode(password, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(QuestionResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (userGestionnary.existUser(id)) {
            User requestedUser = userGestionnary.requestUser(id);
            
            
            if (!userGestionnary.existUser(username) || username.equals(requestedUser.getUserName())) {
                userGestionnary.updateUser(id, username, email, password);
                return new Message("success", "Les informations de l'utilisateur ont bien été mis à jour.");
            } else {
                return new Message("error", "Un autre utilisateur porte déjà ce nom.");
            }
        } else {
            return new Message("error", "L'utilisateur n'existe pas.");
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/read/{idUser}")
    public Object readDomain(@PathParam("idUser") int id) {
        if (userGestionnary.existUser(id)) {
            User user = userGestionnary.readUser(id);
            return user;
        } else {
            return new Message("error", "L'utilisateur n'existe pas.");
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/read/{count}/{startAt}")
    public Object readUsers(@PathParam("count") int count, @PathParam("startAt") int startAt) {
        try {
            return userGestionnary.readUsers(count, startAt);
        } catch (Exception e) {
            return new Message("error", "Une erreur est survenue.");
        }
    }
}
