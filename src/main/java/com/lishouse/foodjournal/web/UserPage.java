package com.lishouse.foodjournal.web;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.google.sitebricks.At;
import com.google.sitebricks.Show;
import com.google.sitebricks.http.Get;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import com.lishouse.foodjournal.data.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@At("/user/:userId/:info") @Show("UserPage.tpl.html")
public class UserPage {
    private static final Logger log = LoggerFactory.getLogger(UserPage.class);

    @Inject
    private CurrentUser currentUser;

    private String signoutUrl;
    private User user;
    private User me;
    private String info;

    @Get
    public void showUser(@Named("userId") String userId, @Named("info") String info, HttpServletResponse resp) throws IOException {
        me = currentUser.getUser();
        if (null == me) {
            resp.sendRedirect(currentUser.getLoginUrl("/entries"));
            return;
        }
        signoutUrl = currentUser.getLogoutUrl(currentUser.getLoginUrl("/entries"));

        if ((null!= userId) && !userId.isEmpty()) {
            user = new User();
            user.setUsername(userId);
        }
        
        this.info = info;
    }

    public User getUser() {
        return user;
    }
    public User getMe() {
        return me;
    }
    public String getInfo() {
        return info;
    }
    public String getSignoutUrl() {
        return signoutUrl;
    }

}
