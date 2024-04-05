/**
 * The Photo class represents a photo in the photo application.
 * It contains information such as the photo's caption, path, last modified date, and tags.
 */
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

    /**
     * Constructs an empty photo with no caption, path, or last modified date.
     */
    public Photo() {
        photoCaption = null;
        path = null;
        setLastModDate(null);
    }

    /**
     * Constructs a photo with the specified caption, path, and last modified date.
     *
     * @param caption The caption of the photo.
     * @param photoPath The path of the photo.
     * @param date The last modified date of the photo.
     */
    public Photo(String caption, String photoPath, String date) {
        this.photoCaption = caption;
        this.path = photoPath;
        this.lastModifyDate = date;
    }

    /**
     * Retrieves the tags associated with the photo.
     *
     * @return An ArrayList containing the tags associated with the photo.
     */
    public ArrayList<String> getTags() {
        ArrayList<String> list = new ArrayList<String>();
        for (Tag t : listTags) {
            list.add(t.toString());
        }
        return list;
    }

    /**
     * Checks if the photo contains a specific tag.
     *
     * @param tagVal The value of the tag to check.
     * @return true if the photo contains the specified tag, otherwise false.
     */
    public boolean containsTag(String tagVal) {
        for (Tag t : listTags) {
            if (t.toString().equals(tagVal)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a tag to the photo.
     *
     * @param tag The tag to add to the photo.
     */
    public void addTag(Tag tag) {
        listTags.add(tag);
    }

    /**
     * Sets the caption of the photo and updates the last modified date.
     *
     * @param caption The new caption for the photo.
     */
    public void setPhotoCaption(String caption) {
        photoCaption = caption;
        setLastModDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a")));
    }

    /**
     * Retrieves the caption of the photo.
     *
     * @return The caption of the photo.
     */
    public String getPhotoCaption() {
        return photoCaption;
    }

    /**
     * Sets the path of the photo and updates the last modified date.
     *
     * @param path The new path for the photo.
     */
    public void setPath(String path) {
        this.path = path;
        setLastModDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a")));
    }

    /**
     * Retrieves the path of the photo.
     *
     * @return The path of the photo.
     */
    public String getPath() {
        return path;
    }

    /**
     * Retrieves the last modified date of the photo.
     *
     * @return The last modified date of the photo.
     */
    public String getLastModDate() {
        return lastModifyDate;
    }

    /**
     * Sets the last modified date of the photo.
     *
     * @param lastModDate The last modified date to set.
     */
    public void setLastModDate(String lastModDate) {
        this.lastModifyDate = lastModDate;
    }

    /**
     * Removes a tag from the photo.
     *
     * @param tagVal The value of the tag to remove.
     */
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
