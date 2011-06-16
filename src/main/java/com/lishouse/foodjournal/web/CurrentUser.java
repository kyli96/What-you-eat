package com.lishouse.foodjournal.web;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import com.google.inject.Inject;
import com.google.inject.servlet.RequestScoped;

import javax.servlet.http.HttpServletRequest;

import com.lishouse.foodjournal.data.User;

@RequestScoped
public class CurrentUser {
    @Inject
    private HttpServletRequest request;

    private UserService userService = UserServiceFactory.getUserService();

    private User user;

    public User getUser() {
        if ((user == null) && (userService.getCurrentUser() != null)) {
            user = User.fetchMe(userService);
        }
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public String getLoginUrl(String uri) {
        return userService.createLoginURL(uri);
    }
    public String getLogoutUrl(String uri) {
        return userService.createLogoutURL(uri);
    }
}
