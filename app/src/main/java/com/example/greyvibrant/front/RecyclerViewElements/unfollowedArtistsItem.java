package com.example.greyvibrant.front.RecyclerViewElements;

public class unfollowedArtistsItem {

    private String mArtistname;
    private int mAID;
    private int mImageResource;
    private int mUID;

    public unfollowedArtistsItem(int imageResource, String artistname, int AID, int UID) {
        mImageResource = imageResource;
        mArtistname = artistname;
        mAID = AID;
        mUID = UID;
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

    public int getmUID() {
        return mUID;
    }

    public void setmUID(int mUID) {
        this.mUID = mUID;
    }
}
