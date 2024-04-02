package com.example.photoapplication.model;

import com.example.photoapplication.data.PhotoDataBase;

import java.io.Serializable;
import java.util.ArrayList;

public class Album implements Serializable {
    private static final long serialVersionUID = 4L;
    private ArrayList<Photo> photos = new ArrayList<>();
    private String albumName;
    public Album() {
        albumName = null;
    }
    public Album(String name) {
        this.albumName = name;
    }
    public Album(String name, ArrayList<Photo> photos) {
        this.albumName = name;
        this.photos = photos;
    }
    public void setAlbumName(String newName) {
        this.albumName = newName;
    }
    public String getAlbumName() {
        return albumName;
    }
    public void addPhotos(Photo newPhoto) {
        photos.add(newPhoto);
    }
    public int PhotosNum() {
        return this.getPhotoList().size();
    }
    public ArrayList<Photo> getPhotoList() {
        return photos;
    }
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
