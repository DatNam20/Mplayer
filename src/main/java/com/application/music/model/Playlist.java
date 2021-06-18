package com.application.music.model;

import javafx.stage.Stage;

import java.util.List;

public interface Playlist
{
    public String getPlaylistName();
    public void setPlaylistName(String newName);
    public List<String> getSongList();
    public void setSongList(List<String> songList);
    public String getCurrentSongPath();
    public void nextSong();
    public void prevSong();
    public void addSongs(List<String> songList);
    public String removeSongs(List<String> songList);
    public void setShuffle(boolean value);
    public void setRepeat(int value);
    public int getRepeat();
    public boolean getShuffle();
}
