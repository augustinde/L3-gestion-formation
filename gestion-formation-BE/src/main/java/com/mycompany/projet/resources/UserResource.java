/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projet.resources;

import com.mycompany.projet.ejb.SkillGestionnary;
import com.mycompany.projet.ejb.SubdomainGestionnary;
import com.mycompany.projet.ejb.UserGestionnary;
import com.mycompany.projet.ejb.UserHasSkillGestionnary;
import com.mycompany.projet.entities.Message;
import com.mycompany.projet.entities.User;
import com.mycompany.projet.entities.UserHasSkill;
import com.mycompany.projet.entities.UserHasSkillPK;
import javax.ws.rs.Consumes;
import javax.ejb.EJB;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
    
    @EJB
    private SkillGestionnary skillGestionnary;
    
    @EJB
    private SubdomainGestionnary subdomainGestionnary;
    
    @EJB
    private UserHasSkillGestionnary userHasSkillGestionnary;

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/login")
    public Object loginUser(@FormParam("username") String username, @FormParam("password") String password) {
        if (username != null && password != null) {
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

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/register")
    public Object registerUser(@FormParam("username") String username, @FormParam("email") String email, @FormParam("password") String password, @FormParam("confirmation_password") String password2) {
        if (username != null && password != null && email != null && password2 != null) {
            if (!userGestionnary.existUser(username)) {
                if (password.equals(password2)) {
                    userGestionnary.createUser(new User(username, email, password, "VISITOR"));
                    return new Message("success", "L'utilisateur a bien été enregistré.");
                } else {
                    return new Message("error", "Les mots de passe doivent être identiques.");
                }
            } else {
                return new Message("error", "L'utilisateur existe déjà.");
            }
        } else {
            return new Message("error", "Vous devez renseigner tous les champs.");
        }
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/update")
    public Object updateUser(@FormParam("id") int id, @FormParam("username") String username, @FormParam("email") String email, @FormParam("password") String password, @FormParam("confirmation_password") String password2) {
        if (username != null && email != null) {
            if (userGestionnary.existUser(id)) {
                User requestedUser = userGestionnary.requestUser(id);

                if (!userGestionnary.existUser(username) || username.equals(requestedUser.getUserName())) {
                    if(!requestedUser.getUserPassword().equals(password)){
                        if(password.equals(password2)){
                            userGestionnary.updateUser(id, username, email, password);
                            return new Message("success", "Les informations de l'utilisateur ont bien été mis à jour.");
                        }else{
                            return new Message("error", "Les mots de passe doivent être identiques.");
                        }
                    }else{
                        userGestionnary.updateUser(id, username, email, password);
                        return new Message("success", "Les informations de l'utilisateur ont bien été mis à jour.");
                    }
                } else {
                    return new Message("error", "Un autre utilisateur porte déjà ce nom.");
                }
            } else {
                return new Message("error", "L'utilisateur n'existe pas.");
            }
        }else{
            return new Message("error", "Veuillez remplir tout les champs.");
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
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/count")
    public Object countUsers() {
        try{
            return userGestionnary.countUsers();
        }catch(Exception e){
            return new Message("error", "Une erreur est survenue.");
        }
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{userId}/skill/add/{skillId}")
    public void addUserHasSkill(@PathParam("userId") int userId, @PathParam("skillId") int skillId) {
        if (userGestionnary.existUser(userId) && skillGestionnary.existSkill(skillId)) {
            UserHasSkillPK relation = new UserHasSkillPK(userId, skillId);
            UserHasSkill userHasSkill = new UserHasSkill(relation, 0, 0);
            
            userHasSkillGestionnary.addSkillToUser(userHasSkill);
        }
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{userId}/skill/add/all")
    public void addUserHasSkillAll(@PathParam("userId") int userId) {
        if (userGestionnary.existUser(userId)) {
            userHasSkillGestionnary.addAll(userId);
        }
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{userId}/skill/remove/{skillId}")
    public void removeUserHasSkill(@PathParam("userId") int userId, @PathParam("skillId") int skillId) {
        if (userGestionnary.existUser(userId) && skillGestionnary.existSkill(skillId)) {
            userHasSkillGestionnary.remove(userId, skillId);
        }
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{userId}/skill/remove/all")
    public void removeUserHasSkillAll(@PathParam("userId") int userId) {
        if (userGestionnary.existUser(userId)) {
            userHasSkillGestionnary.removeAll(userId);
        }
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{userId}/skill/removeBySubdomainId/{subdomainId}")
    public void removeUserHasSkillBySubdomain(@PathParam("userId") int userId, @PathParam("subdomainId") int subdomainId) {
        if (userGestionnary.existUser(userId) && subdomainGestionnary.existSubdomain(subdomainId)) {
            userHasSkillGestionnary.removeBySubdomainId(userId, subdomainId);
        }
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{userId}/skill/addBySubdomainId/{subdomainId}")
    public void addBySubdomainId(@PathParam("userId") int userId, @PathParam("subdomainId") int subdomainId) {
        if (userGestionnary.existUser(userId) && subdomainGestionnary.existSubdomain(subdomainId)) {
            userHasSkillGestionnary.addBySubdomain(userId, subdomainId);
        }
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{userId}/skill/count")
    public Object countUserHasSKill(@PathParam("userId") int userId) {
        if (userGestionnary.existUser(userId)) {
            return userHasSkillGestionnary.count(userId);
        }else{
            return new Message("error", "L'utilisateur n'existe pas.");
        }
    }
}
