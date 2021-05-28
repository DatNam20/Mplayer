package com.application.music.model;

public interface Song {
    public String getName() ;
    public String getArtist() ;
    public String getAlbum() ;
    public int getLength() ;
    public void stop() ;
    public void play() ;
    public void pause() ;
    public void skipAhead() ;
    public void skipBack() ;
    public void setMediaFile(String mediaFile, String function) ;
}
