package com.example.greyvibrant.front;

public class Upload {
    private String mName;
    private String mSongUrl;

    public Upload() {
        // empty constructor needed
    }

    public Upload(String name, String songUrl) {
        if (name.trim().equals("")) {
            name = "No Name";
        }
        mName = name;
        mSongUrl = songUrl;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getSongUrl() {
        return mSongUrl;
    }

    public void setSongUrl(String songUrl) {
        mSongUrl = songUrl;
    }
}
