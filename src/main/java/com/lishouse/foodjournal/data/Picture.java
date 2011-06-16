package com.lishouse.foodjournal.data;

import com.google.appengine.api.datastore.Blob;
import com.googlecode.objectify.Key;

import java.util.Date;

import javax.persistence.Id;

public class Picture {
    @Id
    private Long id;
    private String name;
    private String mimeType;
    private Date lastModified;

    @JsonHide
    private Key<User> user;
    @JsonHide
    private Blob content;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getMimeType() {
        return mimeType;
    }
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Date getLastModified() {
        return lastModified;
    }
    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public Key<User> getUserKey() {
        return user;
    }
    public void setUser(User user) {
        this.user = new Key<User>(User.class, user.getUsername());
    }

    public Blob getContent() {
        return content;
    }
    public void setContent(Blob content) {
        this.content = content;
    }
}
