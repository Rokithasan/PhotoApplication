package com.example.photoapplication.model;

import com.example.photoapplication.data.PhotoDataBase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class User implements Serializable {

    private static final long serialVersionUID = 5L;
    private String username;
    private String password;

    private ArrayList<Album> albums = new ArrayList<>();
    private ArrayList<TagType> tagTypes = new ArrayList<TagType>();
    public static Album currentSessionAlbum;

    public User() {
        username = null;
        password = null;
        tagTypes.add(new TagType("Location", true));
        tagTypes.add(new TagType("Person"));
    }
    public User(String name,String password) {
        this.username = name;
        this.password = password;
        tagTypes.add(new TagType("Location", true));
        tagTypes.add(new TagType("Person"));
    }
    public void setUserName(String name) {
        this.username = name;
    }
    public String getUserName() {
        return username;
    }
    public void addAlbum(Album album) {
        albums.add(album);
    }

    public void removeAlbum(String selectedAlbum) {
        Iterator<Album> iterator = albums.iterator();
        while(iterator.hasNext()) {
            Album a = iterator.next();
            if(a.getAlbumName().equals(selectedAlbum)) {
                iterator.remove();
            }
        }
    }
    public void renameAlbum(String currentName, String newName) {
        for(Album a: albums) {
            if(a.getAlbumName().equals(currentName)) {
                a.setAlbumName(newName);
            }
        }
    }

    public ArrayList<Album> getAlbumList() {
        return albums;
    }

    public void setCurrentSessionAlbum(String albumName) {
        User currentUser = PhotoDataBase.getCurrentSessionUser();
        ArrayList<Album> allAlbum = currentUser.getAlbumList();
        for(Album a: allAlbum) {
            if(a.getAlbumName().equals(albumName)) {
                currentSessionAlbum = a;
            }
        }
    }

    public static Album getCurrentSessionAlbum() {
        return currentSessionAlbum;
    }

    public void addTagType(TagType t) {
        tagTypes.add(t);
    }

    public void removeTagType(String tagType)
    {
        Iterator<TagType> iterator = tagTypes.iterator();
        while(iterator.hasNext())
        {
            TagType t = iterator.next();
            if(t.getName().equals(tagType))
            {
                iterator.remove();
            }
        }
    }

    public ArrayList<String> getTypeList()
    {
        ArrayList<String> typeList = new ArrayList<String>();
        for(TagType c : tagTypes)
        {
            typeList.add(c.getName());
        }
        return typeList;
    }

    public ArrayList<TagType> getTypes()
    {
        return tagTypes;
    }

}
