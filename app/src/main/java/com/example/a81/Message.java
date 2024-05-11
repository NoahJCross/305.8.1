package com.example.a81;

// Class representing a message in the chat
public class Message {

    // Fields for message properties
    private String name;      // Sender's name
    private String message;   // Message content
    private Boolean isUser;   // Indicator if the message is sent by the user

    // Constructor to initialize a Message object with name, message, and user indicator
    public Message(String name, String message, Boolean isUser){
        this.name = name;
        this.message = message;
        this.isUser = isUser;
    }

    // Getter and setter methods for the fields
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    // Method to check if the message is sent by the user
    public Boolean isUser() {
        return isUser;
    }
}
