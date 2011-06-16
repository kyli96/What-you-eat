package com.lishouse.foodjournal.web;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.google.sitebricks.At;
import com.google.sitebricks.Show;
import com.google.sitebricks.http.Get;
import com.google.sitebricks.http.Post;
import com.google.sitebricks.headless.Request;
import com.googlecode.objectify.Key;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lishouse.foodjournal.data.Entry;
import com.lishouse.foodjournal.data.User;
import com.lishouse.foodjournal.data.EntryStore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@At("/entries") @Show("EntriesPage.tpl.html")
public class EntriesPage {
    private static final Logger log = LoggerFactory.getLogger(EntriesPage.class);

    @Inject
    private EntryStore entryStore;

    @Inject
    private CurrentUser currentUser;

    private Entry entry = new Entry();
    private String signoutUrl;
    private User user;

    private boolean showForm = true;

    private final DateFormat dateFormat = new SimpleDateFormat("MMMMM d");

    @Post
    public String postEntry() {
        user = currentUser.getUser();
        if (null == user) {
            return currentUser.getLoginUrl("/entries");
        }

        this.entry.setId(UUID.randomUUID().getMostSignificantBits());
        this.entry.setUser(user);
        entryStore.save(this.entry);

        return "/entries";
    }

    @Get
    public void listEntries(HttpServletResponse resp) throws IOException {
        user = currentUser.getUser();
        if (null == user) {
            resp.sendRedirect(currentUser.getLoginUrl("/entries"));
            return;
        }
        signoutUrl = currentUser.getLogoutUrl(currentUser.getLoginUrl("/entries"));
    }

    public User getUser() {
        return user;
    }

    public String getSignoutUrl() {
        return signoutUrl;
    }

    public Entry getEntry() {
        return this.entry;
    }
    public void setEntry(Entry entry) {
        this.entry = entry;
    }

    public boolean getShowForm() {
        return showForm;
    }
}
