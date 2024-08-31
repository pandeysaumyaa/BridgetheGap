package com.example.bridgethegap;

public class User {

    private String userId;
    private String email;
    private String username;
    private String dateOfBirth;
    private String gender;
    private String location;
    private String bio; // Added field

    private String profilePictureUrl;

    public User() {
        // Default constructor required for Firestore
    }

    // Constructor without bio and profilePictureUrl parameters

    public User(String userId, String email, String username, String dateOfBirth, String gender, String location) {
        this.userId = userId;
        this.email = email;
        this.username = username;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.location = location;
    }

    // Constructor with all parameters including bio and profilePictureUrl

    public User(String userId, String email, String username, String dateOfBirth, String gender, String location, String bio, String profilePictureUrl) {
        this.userId = userId;
        this.email = email;
        this.username = username;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.location = location;
        this.bio = bio;
        this.profilePictureUrl = profilePictureUrl;
    }

    // Getters and setters for all fields

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getusername() {
        return username;
    }

    public void setusername(String username) {
        this.username = username;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }
}
