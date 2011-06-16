package com.lishouse.foodjournal.web;

import com.google.inject.Inject;
import com.google.sitebricks.At;
import com.google.sitebricks.rendering.EmbedAs;
import com.google.sitebricks.Show;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.lishouse.foodjournal.MainModule;

@EmbedAs("EntryForm") @Show("EntryForm.tpl.html")
public class EntryForm {
    private final DateFormat dateFormat = new SimpleDateFormat(MainModule.DEFAULT_DATE_TIME_FORMAT);

    public String getCurrentDate() {
        return dateFormat.format(new Date());
    }
}
