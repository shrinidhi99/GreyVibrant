package com.example.greyvibrant.front.RecyclerViewElements;

public class SongsHistoryItem {
    private String mSongname;
    private String mAlbum;
    private String mGenre;
    private String mLanguage;
    private String mArtistname;
    private int mAID;
    private int mSID;
    private int mUID;

    public SongsHistoryItem(String songname, String artistname, String album, String genre, String language, int SID, int AID, int UID) {
        mUID = UID;
        mSID = SID;
        mAID = AID;
        mSongname = songname;
        mArtistname = artistname;
        mAlbum = album;
        mGenre = genre;
        mLanguage = language;
    }

    public int getmUID() {
        return mUID;
    }

    public void setmUID(int mUID) {
        this.mUID = mUID;
    }

    public int getmAID() {
        return mAID;
    }

    public void setmAID(int mAID) {
        this.mAID = mAID;
    }

    public int getmSID() {
        return mSID;
    }

    public void setmSID(int mSID) {
        this.mSID = mSID;
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
}
