package com.lishouse.foodjournal.data;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.servlet.RequestScoped;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

public class StoreModule extends AbstractModule {
    static {
        ObjectifyService.register(Entry.class);
        ObjectifyService.register(User.class);
        ObjectifyService.register(Picture.class);
    }

    @Override
    protected void configure() {
    }

    @Provides
    @RequestScoped
    Objectify provideObjectify() {
        return ObjectifyService.begin();
    }
}
