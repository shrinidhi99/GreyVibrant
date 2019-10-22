package com.example.greyvibrant.front.RecyclerViewElements;

public class unfollowedArtistsItem {

    private String mArtistname;
    private int mAID;

    public unfollowedArtistsItem(String artistname, int AID) {

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
}
