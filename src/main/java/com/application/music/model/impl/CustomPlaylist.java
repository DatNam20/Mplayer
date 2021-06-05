package com.application.music.model.impl;

import com.application.music.model.Playlist;
import com.application.music.utility.FileUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.application.music.utility.ApplicationConstant.*;
import static com.application.music.utility.ApplicationConstant.SHUFFLE_OFF;

public class CustomPlaylist implements Playlist {

    private String playlistName;
    private ArrayList<String> songList;
    private Set<String> songSet;
    private int currentSongId;
    private int repeat;
    private boolean shuffle;

    public CustomPlaylist() {
        songList = new ArrayList<>();
        songSet = new HashSet<>();
        playlistName = DEFAULT_PLAYLIST_NAME;
        currentSongId = 0;
        addSongs(FileUtil.loadSong(DEFAULT_PLAYLIST_LOCATION));
        repeat = REPEAT_ALL;
        shuffle = SHUFFLE_OFF;
    }


    @Override
    public String getPlaylistName() {
        return null;
    }

    @Override
    public List<String> getSongList() {
        return null;
    }

    @Override
    public String getCurrentSongPath() {
        return null;
    }

    @Override
    public void nextSong() {

    }

    @Override
    public void prevSong() {

    }

    @Override
    public void addSongs(List<String> songList) {

    }

    @Override
    public String removeSongs(List<String> songList) {
        return null;
    }

    @Override
    public void setShuffle(boolean value) {
        this.shuffle = shuffle;
    }

    @Override
    public void setRepeat(int value) {
        this.repeat = repeat;
    }

    @Override
    public int getRepeat() {
        return repeat;
    }

    @Override
    public boolean getShuffle() {
        return shuffle;
    }
}
