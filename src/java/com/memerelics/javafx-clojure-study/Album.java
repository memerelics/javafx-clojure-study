package com.memerelics.javafx_clojure_study;

public class Album {
    private String title;
    private String artist;
    private int release;

    public Album(String title, String artist, int release) {
        this.title = title;
        this.artist = artist;
        this.release = release;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getRelease() {
        return release;
    }

    public void setRelease(int release) {
        this.release = release;
    }
}
