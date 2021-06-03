package com.application.music.model;

public interface Song {
    public String getSongName() ;
    public void stop() ;
    public void play() ;
    public void pause() ;
    public Double getDuration();
    public String getAlbum();
    public String getArtist();
    public Object getImage();

    double getCurrentTime();

    void seek(double currentTime);
}
