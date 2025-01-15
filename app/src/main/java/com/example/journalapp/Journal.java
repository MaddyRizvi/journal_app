package com.example.journalapp;

import com.google.firebase.Timestamp;

// 2 Model class for Journal
public class Journal {

    private String title;
    private String thoughts;
    private String imageURL;

    private String userId;
    // import Timestamp of google.firebase
    private Timestamp timeAdded;
    private String userName;

    public Journal(){
    }

    public Journal(String title, String thoughts, String imageURL,
                   String userId, Timestamp timeAdded, String userName) {
        this.title = title;
        this.thoughts = thoughts;
        this.imageURL = imageURL;
        this.userId = userId;
        this.timeAdded = timeAdded;
        this.userName = userName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThoughts() {
        return thoughts;
    }

    public void setThoughts(String thoughts) {
        this.thoughts = thoughts;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Timestamp getTimeAdded() {
        return timeAdded;
    }

    public void setTimeAdded(Timestamp timeAdded) {
        this.timeAdded = timeAdded;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
