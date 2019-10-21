package com.example.greyvibrant.front.RecyclerViewElements;

public class myAlbumItem {
    private String mSongname;
    private String mAlbum;
    private String mGenre;
    private String mLanguage;

    public myAlbumItem(String songname, String album, String genre, String language) {

        mSongname = songname;
        mAlbum = album;
        mGenre = genre;
        mLanguage = language;
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
}
