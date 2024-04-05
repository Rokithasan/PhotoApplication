/**
 * The Album class represents a collection of photos in the photo application.
 * It contains methods to manipulate the list of photos within the album and
 * retrieve information such as the album name, number of photos, photo list, and date range.
 */

package com.example.photoapplication.model;

import com.example.photoapplication.data.PhotoDataBase;

import java.io.Serializable;
import java.util.ArrayList;

public class Album implements Serializable {
    private static final long serialVersionUID = 4L;
    private ArrayList<Photo> photos = new ArrayList<>();
    private String albumName;
    /**
     * Constructs an empty album with no name.
     */
    public Album() {
        albumName = null;
    }
    /**
     * Constructs an album with the specified name.
     *
     * @param name The name of the album.
     */
    public Album(String name) {
        this.albumName = name;
    }
    /**
     * Constructs an album with the specified name and list of photos.
     *
     * @param name   The name of the album.
     * @param photos The list of photos in the album.
     */
    public Album(String name, ArrayList<Photo> photos) {
        this.albumName = name;
        this.photos = photos;
    }
    /**
     * Sets the name of the album.
     *
     * @param newName The new name for the album.
     */
    public void setAlbumName(String newName) {
        this.albumName = newName;
    }
    /**
     * Gets the name of the album.
     *
     * @return The name of the album.
     */
    public String getAlbumName() {
        return albumName;
    }
    /**
     * Adds a photo to the album.
     *
     * @param newPhoto The photo to add to the album.
     */
    public void addPhotos(Photo newPhoto) {
        photos.add(newPhoto);
    }

    /**
     * Returns the number of photos in the album.
     *
     * @return The number of photos in the album.
     */
    public int PhotosNum() {
        return this.getPhotoList().size();
    }

    /**
     * Returns the list of photos in the album.
     *
     * @return The list of photos in the album.
     */
    public ArrayList<Photo> getPhotoList() {
        return photos;
    }

    /**
     * Calculates and returns the date range of the photos in the album.
     *
     * @return The date range of the photos in the album.
     */
    public String dateRange() {
        String minDR = "99/99/9999";
        String maxDR = "00/00/0000";
        ArrayList<Photo> p = this.getPhotoList();
        if (p.size() == 0) {
            return "N/A";
        }
        for (Photo dP : p) {
            if (minDR.substring(6).compareTo(dP.getLastModDate().substring(6)) >= 0) {
                minDR = dP.getLastModDate();
                if (minDR.substring(3, 5).compareTo(dP.getLastModDate().substring(3, 5)) >= 0) {
                    minDR = dP.getLastModDate();
                    if (minDR.substring(0, 2).compareTo(dP.getLastModDate().substring(0, 2)) >= 0) {
                        minDR = dP.getLastModDate();
                    }
                }
            }
            if (maxDR.substring(6).compareTo(dP.getLastModDate().substring(6)) <= 0) {
                maxDR = dP.getLastModDate();
                if (maxDR.substring(3, 5).compareTo(dP.getLastModDate().substring(3, 5)) <= 0) {
                    maxDR = dP.getLastModDate();
                    if (maxDR.substring(0, 2).compareTo(dP.getLastModDate().substring(0, 2)) <= 0) {
                        maxDR = dP.getLastModDate();
                    }
                }
            }
        }
        return minDR.substring(0, 10) + " - " + maxDR.substring(0, 10);
    }

    public Album getAlbum(String selectedItem) {
        User u = PhotoDataBase.getCurrentSessionUser();
        ArrayList<Album> a = u.getAlbumList();
        for (Album aR : a) {
            if (aR.getAlbumName().equals(selectedItem)) {
                return aR;
            }
        }
        return null;
    }

}
