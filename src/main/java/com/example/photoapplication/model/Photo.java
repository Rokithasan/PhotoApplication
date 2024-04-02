package com.example.photoapplication.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;

public class Photo implements Serializable {
    private static final long serialVersionUID = 3L;
    private String photoCaption;
    private String path;
    private String lastModifyDate;
    private ArrayList<Tag> listTags = new ArrayList<Tag>();
    public Photo() {
        photoCaption = null;
        path = null;
        setLastModDate(null);
    }
    public Photo(String caption, String photoPath, String date) {
        this.photoCaption = caption;
        this.path = photoPath;
        this.lastModifyDate = date;
    }
    public ArrayList<String> getTags() {
        ArrayList<String> list = new ArrayList<String>();
        for (Tag t : listTags) {
            list.add(t.toString());
        }
        return list;
    }
    public boolean containsTag(String tagVal) {
        for (Tag t : listTags) {
            if (t.toString().equals(tagVal)) {
                return true;
            }
        }
        return false;
    }
    public void addTag(Tag tag) {
        listTags.add(tag);
    }
    public void setPhotoCaption(String caption) {
        photoCaption = caption;
        setLastModDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a")));
    }
    public String getPhotoCaption() {
        return photoCaption;
    }
    public void setPath(String path) {
        this.path = path;
        setLastModDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a")));
    }
    public String getPath() {
        return path;
    }

    public String getLastModDate() {
        return lastModifyDate;
    }

    public void setLastModDate(String lastModDate) {
        this.lastModifyDate = lastModDate;
    }

    public void removeTag(String tagVal) {
        Iterator<Tag> iterator = listTags.iterator();
        while (iterator.hasNext()) {
            Tag t = iterator.next();
            if (t.toString().equals(tagVal)) {
                iterator.remove();
            }
        }
    }
}
