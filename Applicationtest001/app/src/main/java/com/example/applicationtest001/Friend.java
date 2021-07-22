package com.example.applicationtest001;

public class Friend {
    private String name;
    private int imageId;
    private String describe;


    public Friend(String name, String describe, int imageId){
        this.name = name;
        this.describe = describe;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }

    public String getDescribe(){
        return describe;
    }
}
