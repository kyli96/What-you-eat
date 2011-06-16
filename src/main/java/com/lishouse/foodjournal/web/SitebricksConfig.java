package com.lishouse.foodjournal.web;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.AbstractModule;

import com.lishouse.foodjournal.MainModule;

public class SitebricksConfig extends GuiceServletContextListener {
    @Override
    protected Injector getInjector() {
        return Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                install(new MainModule());
            }
        });
    }
}
