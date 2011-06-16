package com.lishouse.foodjournal.data;

import com.googlecode.objectify.Key;

import javax.persistence.Entity;
import javax.persistence.Id;
//import javax.persistence.Transient;
import java.util.Date;

@Entity
public class Entry {
    @Id
    private Long id;

    @JsonHide
    private Key<User> userKey;

    private String title;
    private String desc;
    private Date date;
    private String location;
    private int rating;
    private Long pictureId;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Key<User> getUserKey() {
        return userKey;
    }
    public void setUser(User user) {
        this.userKey = new Key<User>(User.class, user.getUsername());
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public int getRating() {
        return rating;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }

    public Long getPictureId() {
        return pictureId;
    }
    public void setPictureId(Long pictureId) {
        this.pictureId = pictureId;
    }

    public boolean hasPicture() {
        return (pictureId != null);
    }

    @Override
    public String toString() {
        return "{title:\"" + title
            + "\", desc:\"" + desc
            + "\", pictureId:\"" + pictureId
            + "\"}";
    }
}
