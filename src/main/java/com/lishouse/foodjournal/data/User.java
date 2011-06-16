package com.lishouse.foodjournal.data;

import com.google.appengine.api.users.UserService;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {
    @Id
    private String username;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public static User fetchMe(UserService userService) {
        User user = new User();
        user.setUsername(userService.getCurrentUser().getNickname());
        return user;
    }
}
