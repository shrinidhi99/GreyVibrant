package com.example.greyvibrant.front.RecyclerViewElements;

public class remainingSongsItem {
    private String mSongname;
    private String mAlbum;
    private String mGenre;
    private String mLanguage;
    private String mArtistname;
    private String mSongurl;
    private int mAID;
    private int mUID;

    public remainingSongsItem(String songname, int AID, int UID, String album, String genre, String language, String artistname, String songurl) {

        mSongname = songname;
        mAlbum = album;
        mGenre = genre;
        mLanguage = language;
        mArtistname = artistname;
        mAID = AID;
        mSongurl = songurl;
        mUID = UID;
    }

    public String getmSongname() {
        return mSongname;
    }

    public void setmSongname(String mSongname) {
        this.mSongname = mSongname;
    }

    public String getmAlbum() {
        return mAlbum;
    }

    public void setmAlbum(String mAlbum) {
        this.mAlbum = mAlbum;
    }

    public String getmGenre() {
        return mGenre;
    }

    public void setmGenre(String mGenre) {
        this.mGenre = mGenre;
    }

    public String getmLanguage() {
        return mLanguage;
    }

    public void setmLanguage(String mLanguage) {
        this.mLanguage = mLanguage;
    }

    public String getmArtistname() {
        return mArtistname;
    }

    public void setmArtistname(String mArtistname) {
        this.mArtistname = mArtistname;
    }

    public String getmSongurl() {
        return mSongurl;
    }

    public void setmSongurl(String mSongurl) {
        this.mSongurl = mSongurl;
    }

    public int getmAID() {
        return mAID;
    }

    public void setmAID(int mAID) {
        this.mAID = mAID;
    }

    public int getmUID() {
        return mUID;
    }

    public void setmUID(int mUID) {
        this.mUID = mUID;
    }
}
