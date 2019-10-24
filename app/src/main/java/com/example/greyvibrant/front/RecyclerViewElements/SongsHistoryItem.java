package com.example.greyvibrant.front.RecyclerViewElements;

public class SongsHistoryItem {
    private String mSongname;
    private String mAlbum;
    private String mGenre;
    private String mLanguage;
    private String mArtistname;

    public SongsHistoryItem(String songname, String artistname, String album, String genre, String language) {

        mSongname = songname;
        mArtistname = artistname;
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

    public String getmArtistname() {
        return mArtistname;
    }

    public void setmArtistname(String mArtistname) {
        this.mArtistname = mArtistname;
    }
}
