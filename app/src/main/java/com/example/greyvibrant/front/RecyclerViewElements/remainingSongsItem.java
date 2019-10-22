package com.example.greyvibrant.front.RecyclerViewElements;

public class remainingSongsItem {
    private String mSongname;
    private String mAlbum;
    private String mGenre;
    private String mLanguage;
    private String mArtistname;

    public remainingSongsItem(String songname, String album, String genre, String language, String artistname) {

        mSongname = songname;
        mAlbum = album;
        mGenre = genre;
        mLanguage = language;
        mArtistname = artistname;
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
