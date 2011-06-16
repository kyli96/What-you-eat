package com.lishouse.foodjournal.data;

import com.google.inject.Inject;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.Query;

import java.util.List;
import java.util.Date;

public class EntryStore {
    @Inject
    private Objectify objectify;

    public Entry fetchEntry(Long id) {
        return objectify.find(Entry.class, id);
    }

    public void save(Entry entry) {
        if (entry.getDate() == null) {
            entry.setDate(new Date());
        }
        objectify.put(entry);
    }

    public List<Entry> list(User user) {
        List<Entry> list = objectify
            .query(Entry.class)
            .filter("userKey", new Key<User>(User.class, user.getUsername()))
            .order("date")
            .list();

        return list;
    }

    public Picture fetchPicture(Long id) {
        return objectify.find(Picture.class, id);
    }

    public void save(Picture pic) {
        pic.setLastModified(new Date());
        objectify.put(pic);
    }
}
