package com.lishouse.foodjournal.web;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import com.google.inject.Inject;
import com.google.gson.Gson;
import com.google.sitebricks.client.transport.Json;
import com.google.sitebricks.At;
import com.google.sitebricks.headless.Reply;
import com.google.sitebricks.headless.Service;
import com.google.sitebricks.headless.Request;
import com.google.sitebricks.http.Post;
import com.google.sitebricks.http.Get;

import java.util.List;
import java.util.UUID;

import com.lishouse.foodjournal.data.Entry;
import com.lishouse.foodjournal.data.User;
import com.lishouse.foodjournal.data.EntryStore;

@At("/ws/entries") @Service
public class EntriesWS {
    @Inject
    private EntryStore entryStore;

    private UserService userService = UserServiceFactory.getUserService();

    @Inject
    private Gson gson;

    @Post
    Reply<?> postEntry(Request req) {
        Entry entry = req.read(Entry.class).as(Json.class);
        User user = User.fetchMe(userService);
        entry.setId(UUID.randomUUID().getMostSignificantBits());
        entry.setUser(user);
        entryStore.save(entry);

        return Reply.saying().ok();
    }

    @Get
    Reply<String> getEntries(Request req) {
        User user = User.fetchMe(userService);
        List<Entry> entries = entryStore.list(user);

        return Reply.with(gson.toJson(entries))
            .type("application/json");
    }
}
