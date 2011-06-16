package com.lishouse.foodjournal.web;

import com.google.inject.Inject;
import com.google.sitebricks.Show;
import com.google.sitebricks.rendering.EmbedAs;

import java.util.List;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.lishouse.foodjournal.data.Entry;
import com.lishouse.foodjournal.data.User;
import com.lishouse.foodjournal.data.EntryStore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@EmbedAs("EntryList") @Show("EntryList.tpl.html")
public class EntryList {
    private static final Logger log = LoggerFactory.getLogger(EntryList.class);

    @Inject
    private EntryStore entryStore;

    private List<Entry> entries;
    private User user;

    private final DateFormat dateFormat = new SimpleDateFormat("MMMMM d");

    public List<Entry> getEntries() {
        this.entries = entryStore.list(user);

        return this.entries;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public String format(Date date) {
        return dateFormat.format(date);
    }
}
