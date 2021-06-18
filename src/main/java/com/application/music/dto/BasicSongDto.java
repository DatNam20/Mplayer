package com.application.music.dto;

import javafx.util.Duration;

public class BasicSongDto {
    String songName;
    Double songDuration;
    String artist;
    String album;
    Object Image;
    Double songElapsedTime;

    public Double getSongElapsedTime() {
        return songElapsedTime;
    }

    public void setSongElapsedTime(Double songElapsedTime) {
        this.songElapsedTime = songElapsedTime;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public void setImage(Object image) {
        Image = image;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public Object getImage() {
        return Image;
    }

    public void setSongDuration(Double songDuration) {
        this.songDuration = songDuration;
    }

    public Double getSongDuration() {
        return songDuration;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }
}
