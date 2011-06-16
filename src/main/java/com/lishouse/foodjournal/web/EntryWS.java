package com.lishouse.foodjournal.web;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.google.gson.Gson;
import com.google.sitebricks.client.transport.Json;
import com.google.sitebricks.At;
import com.google.sitebricks.headless.Reply;
import com.google.sitebricks.headless.Service;
import com.google.sitebricks.headless.Request;
import com.google.sitebricks.http.Put;
import com.google.sitebricks.http.Get;

import com.lishouse.foodjournal.data.Entry;
import com.lishouse.foodjournal.data.User;
import com.lishouse.foodjournal.data.EntryStore;

@At("/ws/entry") @Service
public class EntryWS {
    @Inject
    private EntryStore entryStore;

    @Inject
    private Gson gson;

    @At("/:id") @Put
    Reply<?> putEntry(@Named("id") Long id, Request req) {
        Entry entry = req.read(Entry.class).as(Json.class);
        entry.setId(id);
        User user = new User();
        entry.setUser(user);
        entryStore.save(entry);

        return Reply.saying().ok();
    }

    @At("/:id") @Get
    Reply<String> getEntries(@Named("id") Long id) {
        Entry entry = entryStore.fetchEntry(id);

        return Reply.with(gson.toJson(entry))
            .type("application/json");
    }
}
