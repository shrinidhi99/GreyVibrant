package com.example.greyvibrant.front.RecyclerViewElements;

public class unfollowedArtistsItem {

    private String mArtistname;
    private int mAID;
    private int mImageResource;

    public unfollowedArtistsItem(int imageResource, String artistname, int AID) {
        mImageResource = imageResource;
        mArtistname = artistname;
        mAID = AID;
    }

    public String getmArtistname() {
        return mArtistname;
    }

    public void setmArtistname(String mArtistname) {
        this.mArtistname = mArtistname;
    }

    public int getmAID() {
        return mAID;
    }

    public void setmAID(int mAID) {
        this.mAID = mAID;
    }

    public int getmImageResource() {
        return mImageResource;
    }

    public void setmImageResource(int mImageResource) {
        this.mImageResource = mImageResource;
    }
}
