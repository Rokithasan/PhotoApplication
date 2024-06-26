/**
 * The PhotoDataBase class represents the data storage and management for users, albums, and photos
 * in a photo application. It provides methods to interact with user data, read from and write to
 * a serialized file, and handle the current session user.
 */

package com.example.photoapplication.data;

import com.example.photoapplication.model.Album;
import com.example.photoapplication.model.Photo;
import com.example.photoapplication.model.User;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class PhotoDataBase implements Serializable {
    private static final long serialVersionUID = 6L;
    private static PhotoDataBase instance = null;
    private ArrayList<User> users;
    public static User currentSessionUser;

    private PhotoDataBase() {
        users = new ArrayList<>();
        currentSessionUser = null;

        User stock = new User("stock");
        users.add(stock);
        Album stockAlbum = new Album("stock");
        stock.addAlbum(stockAlbum);

        Photo p1 = new Photo("Acura", "database/acura.jpg", LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a")));
        Photo p2 = new Photo("Bugatti Mistral", "database/bugatti_mistral.jpg", LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a")));
        Photo p3 = new Photo("Jaguar F-Type", "database/jaguar.jpg", LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a")));
        Photo p4 = new Photo("Lamborghini Urus", "database/lamborghini.jpg", LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a")));
        Photo p5 = new Photo("Lamborghini huracan", "database/lamborghini_huracan.jpg", LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a")));
        Photo p6 = new Photo("Porsche 911", "database/porsche.jpg", LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a")));

        stockAlbum.addPhotos(p1);
        stockAlbum.addPhotos(p2);
        stockAlbum.addPhotos(p3);
        stockAlbum.addPhotos(p4);
        stockAlbum.addPhotos(p5);
        stockAlbum.addPhotos(p6);
    }

    public static PhotoDataBase getInstance() {
        if (instance == null) {
            instance = new PhotoDataBase();
        }
        return instance;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void deleteUser(String username) {
        for (User user : users) {
            if (user.getUserName().equals(username)) {
                users.remove(user);
                break;
            }
        }
    }

    public ArrayList<String> getUsernames() {
        ArrayList<String> usernames = new ArrayList<String>();
        for (User user : users) {
            usernames.add(user.getUserName());
        }
        return usernames;
    }

    public boolean containsUser(String username) {
        for (User user : users) {
            if (user.getUserName().equals(username)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Reads user data from a serialized file.
     *
     * @return The PhotoDataBase instance with loaded user data.
     */
    public static PhotoDataBase readFromAFile() {
        PhotoDataBase loadUsers = PhotoDataBase.getInstance();
        File file = new File("database/users.ser");
        try {
            if (file.length() == 0) {
                //
            } else {
                try ( ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                    Object o = ois.readObject();
                    if (o instanceof ArrayList<?>) {
                        @SuppressWarnings("unchecked")
                        ArrayList<User> userList = (ArrayList<User>) o;
                        loadUsers.users = userList;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return loadUsers;
    }

    /**
     * Writes user data to a serialized file.
     *
     * @throws IOException If an I/O error occurs.
     */
    public void writeToAFile() throws IOException {
        File f = new File("database/users.ser");
        if(!f.exists()){
            f.createNewFile();
        }
        try ( ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("database/users.ser"))) {
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setCurrentSessionUser(String username) {
        for (User allUser : users) {
            if (allUser.getUserName().equals(username)) {
                currentSessionUser = allUser;
            }
        }
    }

    public static User getCurrentSessionUser() {
        return currentSessionUser;
    }

    public static void main(String[] args) {
        PhotoDataBase photoDataBase = PhotoDataBase.getInstance();
        photoDataBase.startApplication();
    }

    private void startApplication() {
        // Registering shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdownHook));
        // Code to start the main window or other initialization steps
    }

    private void shutdownHook() {
        // Save data to disk before shutting down
        try {
            writeToAFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
