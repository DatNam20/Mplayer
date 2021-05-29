package com.application.music.model;

import javafx.stage.Stage;

import java.util.List;

public interface Playlist
{
    public void createSongList() ;
    public void setPlaylistName(String name) ;
    public String getPlaylistName();
    public List<String> getSongList();
    public void setLocation(String absolutePath);
    public String getCurrentSongPath();
    public void nextSong();
    public void prevSong();
}
