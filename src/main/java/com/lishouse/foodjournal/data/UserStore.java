package com.lishouse.foodjournal.data;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;

@Singleton
public class UserStore {
    @Inject
    private Objectify objfy;

    public User fetch(Key<User> userKey) {
        return objfy.get(userKey);
    }

}
