package com.application.music.model;

import javafx.stage.Stage;

import java.util.List;

public interface Playlist
{
    public String getPlaylistName();
    public List<String> getSongList();
    public String getCurrentSongPath();
    public void nextSong();
    public void prevSong();
    public void loadSong(String directoryPath);
    public void setShuffle(boolean value);
    public void setRepeat(int value);
    public int getRepeat();
    public boolean getShuffle();
}
