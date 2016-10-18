package com.example.ghostchat.model;

/**
 * Created by Administrator on 2016/10/13.
 */
public class Friends {
    private String name;
    private int imageId;
    private String firstLetter;

    public Friends(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    public Friends(String name, int imageId, String firstLetter) {
        this.name = name;
        this.imageId = imageId;
        this.firstLetter = firstLetter;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public String getName() {
        return name;
    }
    public int getImageId() {
        return imageId;
    }
}
