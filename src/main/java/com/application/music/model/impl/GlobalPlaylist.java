package com.application.music.model.impl;

import com.application.music.model.Playlist;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.application.music.utility.ApplicationConstant.*;

public class GlobalPlaylist implements Playlist {


    private String playlistName;
    private ArrayList<String> songList;
    private int currentSongId;

    public GlobalPlaylist() {
        songList = new ArrayList<>();
        playlistName = DEFAULT_PLAYLIST_NAME;
        currentSongId = 0;
        loadSong(DEFAULT_PLAYLIST_LOCATION);
    }


    private int getCurrentSongId() {
        return currentSongId;
    }

    public void setSongList(List<String> songList) {
        this.songList = new ArrayList<>(songList);
        setCurrentSongId(0);
    }

    private void setCurrentSongId(int currentSongId) {
        this.currentSongId = currentSongId;
    }

    public void createSongList(String location) {
        File directory = new File(location);
        setSongList(Arrays.stream(directory.list()).filter(str -> str.endsWith(".mp3")).map(str -> (location + "//" + str)).collect(Collectors.toList()));


        //        File[] fileList = directory.listFiles( new FilenameFilter() {
//            @Override
//            public boolean accept(File dir, String name) {
//                return name.endsWith(".mp3");
//            }
//        });
    }

    public void setPlaylistName(String name) {
        playlistName = name;
    }

    @Override
    public String getPlaylistName() {
        return playlistName;
    }

    @Override
    public List<String> getSongList() {
        return songList;
    }


    @Override
    public String getCurrentSongPath() {
        if(songList!=null && songList.size()!=0)
            return songList.get(getCurrentSongId());
        else
            throw new RuntimeException("No song found");
    }

    @Override
    public void nextSong() {
        setCurrentSongId((getCurrentSongId()+1)%songList.size());
    }

    @Override
    public void prevSong() {
        setCurrentSongId((getCurrentSongId()-1)%songList.size());
    }

    @Override
    public void loadSong(String directoryPath) {
        File directory = new File(directoryPath);
        ArrayList<String> list = new ArrayList<>(Arrays.stream(directory.list()).filter(str -> str.endsWith(".mp3")).map(str -> (directoryPath + "//" + str)).collect(Collectors.toList()));
        songList.addAll(list);
    }

}
