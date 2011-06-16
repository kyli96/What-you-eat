package com.lishouse.foodjournal;

import com.google.sitebricks.SitebricksModule;
import com.google.sitebricks.conversion.DateConverters;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import com.lishouse.foodjournal.web.*;
import com.lishouse.foodjournal.data.StoreModule;
import com.lishouse.foodjournal.data.JsonHide;

public class MainModule extends SitebricksModule {
    public static final String DEFAULT_DATE_TIME_FORMAT = "M/d/yyyy";

    @Override
    protected void configureSitebricks() {
        bindExplicitly();
        scan(SitebricksConfig.class.getPackage());

        install(new StoreModule());

        converter(new DateConverters.DateStringConverter(DEFAULT_DATE_TIME_FORMAT));
    }

    private void bindExplicitly() {
        at("/user/:userid").show(UserPage.class);
    }

    private static final ExclusionStrategy EXCLUDE = new ExclusionStrategy() {
        @Override
        public boolean shouldSkipField(FieldAttributes fieldAttributes) {
            return fieldAttributes.getAnnotation(JsonHide.class) != null;
        }
        
        @Override
        public boolean shouldSkipClass(Class<?> aClass) {
            return false;
        }
    };

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder()
            .setDateFormat("MM/dd/yyyy")
            .setExclusionStrategies(EXCLUDE)
            .setLongSerializationPolicy(LongSerializationPolicy.STRING)
            .create();
    }
}
