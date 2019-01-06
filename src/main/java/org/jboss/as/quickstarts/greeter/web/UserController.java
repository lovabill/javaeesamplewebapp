package org.jboss.as.quickstarts.greeter.web;

import org.jboss.as.quickstarts.greeter.domain.User;
import org.jboss.as.quickstarts.greeter.service.UserService;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class UserController {

    private String username;

    private String greeting;

    @Inject
    private FacesContext facesContext;

    @Inject
    private UserService userService;

    @Named
    @Produces
    @RequestScoped
    private User newUser = new User();


    public void create() {
        try {
            userService.createUser(newUser);
            String message = "A new user with id " + newUser.getId() + " has been created successfully";
            facesContext.addMessage(null, new FacesMessage(message));
        } catch (Exception e) {
            String message = "An error has occured while creating the user (see log for details)";
            facesContext.addMessage(null, new FacesMessage(message));
        }
    }

    public void greet() {
        User user = userService.getForUsername(username);
        if (user != null) {
            greeting = "Hello, " + user.getFirstName() + " " + user.getLastName() + "!";
        } else {
            greeting = "No such user exists!'";
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGreeting() {
        return greeting;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }

    public void sayHi() {
        userService.sayHi();
    }
}
