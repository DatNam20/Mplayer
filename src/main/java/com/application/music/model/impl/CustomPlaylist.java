package com.application.music.model.impl;

import com.application.music.model.Playlist;
import com.application.music.utility.FileUtil;

import java.util.*;

import static com.application.music.utility.ApplicationConstant.*;
import static com.application.music.utility.ApplicationConstant.SHUFFLE_OFF;

public class CustomPlaylist implements Playlist {

    private String playlistName;
    private ArrayList<String> songList;
    private Set<String> songSet;
    private int currentSongId;
    private int repeat;
    private boolean shuffle;

    public CustomPlaylist( String name, List<String> songs) {
        songList = new ArrayList<>(songs);
        songSet = new HashSet<>(songList);
        playlistName = name;
        currentSongId = 0;
        repeat = REPEAT_ALL;
        shuffle = SHUFFLE_OFF;
    }


    private int getCurrentSongId() {
        return currentSongId;
    }

    private void setCurrentSongId(int currentSongId) {
        this.currentSongId = currentSongId;
    }

    @Override
    public String getPlaylistName() {
        return playlistName;
    }

    @Override
    public void setPlaylistName(String newName) {
        this.playlistName = newName;
    }

    @Override
    public List<String> getSongList() {
        return songList;
    }

    @Override
    public void setSongList(List<String> songList) {
        this.songList = new ArrayList<>(songList);
        setCurrentSongId(0);
    }

    @Override
    public String getCurrentSongPath() {
        if(songList!=null && songList.size()!=0)
            return songList.get(getCurrentSongId());
        else
            return null;
    }

    @Override
    public void nextSong() {
        if(SHUFFLE_OFF == getShuffle() && REPEAT_ALL == getRepeat()){
            setCurrentSongId((getCurrentSongId()+1)%songList.size());
        }else if(SHUFFLE_ON == getShuffle() && REPEAT_ALL == getRepeat()){
            setCurrentSongId(((new Random()).nextInt(songList.size())%songList.size()));
        }else if(SHUFFLE_OFF == getShuffle() && REPEAT_ONE == getRepeat()){
            setCurrentSongId(getCurrentSongId());
        }else if(SHUFFLE_ON == getShuffle() && REPEAT_ONE == getRepeat()){
            setCurrentSongId(getCurrentSongId());
        }

    }

    @Override
    public void prevSong() {
        if(SHUFFLE_OFF == getShuffle() && REPEAT_ALL == getRepeat()){
            setCurrentSongId( ( (getCurrentSongId()+ songList.size()-1) % songList.size() ) );
        }else if(SHUFFLE_ON == getShuffle() && REPEAT_ALL == getRepeat()){
            setCurrentSongId(((new Random()).nextInt(songList.size())%songList.size()));
        }else if(SHUFFLE_OFF == getShuffle() && REPEAT_ONE == getRepeat()){
            setCurrentSongId(getCurrentSongId());
        }else if(SHUFFLE_ON == getShuffle() && REPEAT_ONE == getRepeat()){
            setCurrentSongId(getCurrentSongId());
        }

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
        this.shuffle = value;
    }

    @Override
    public void setRepeat(int value) {
        this.repeat = value;
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
