package com.example.a81;

public class User {

    // Singleton instance of User
    private static User instance;

    // User's username
    private String username;

    // Private constructor to prevent instantiation outside the class
    private User() {
    }

    // Singleton getInstance method without parameter
    public static User getInstance() {
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }

    // Singleton getInstance method with parameter
    public static User getInstance(String username) {
        if (instance == null) {
            instance = new User();
            instance.username = username;
        }
        return instance;
    }

    // Method to set the instance with a username
    public static void setInstance(String username){
        instance = new User();
        instance.username = username;
    }

    // Getter method for username
    public String getUsername() {
        return username;
    }

    // Setter method for username
    public void setUsername(String username) {
        this.username = username;
    }
}
