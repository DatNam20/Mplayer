package com.application.music.dto;

import java.util.List;

public class PlaylistDto {
    private String playlistName;
    private List<String> playlistSong;

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public void setPlaylistSong(List<String> playlistSong) {
        this.playlistSong = playlistSong;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public List<String> getPlaylistSong() {
        return playlistSong;
    }

}
