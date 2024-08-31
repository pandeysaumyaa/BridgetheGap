package com.example.bridgethegap;

public class Post {
    private String imageUrl;
    private String caption;
    private String userEmail;
    private String postType; // Added postType field
    private long timestamp;

    public Post() {
        // Default constructor required for Firestore
    }

    // Modified constructor to accept postType parameter
    public Post(String imageUrl, String caption, String userEmail, String postType) {
        this.imageUrl = imageUrl;
        this.caption = caption;
        this.userEmail = userEmail;
        this.postType = postType;
    }

    // Getter and setter methods for postType
    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    // Other getter and setter methods remain unchanged

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
