package com.application.music.model.impl;

import com.application.music.model.Playlist;
import com.application.music.utility.FileUtil;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

import static com.application.music.utility.ApplicationConstant.*;

public class GlobalPlaylist implements Playlist {


    private String playlistName;
    private ArrayList<String> songList;
    private Set<String> songSet;
    private int currentSongId;
    private int repeat;
    private boolean shuffle;

    public GlobalPlaylist() {
        songList = new ArrayList<>();
        songSet = new HashSet<>();
        playlistName = DEFAULT_PLAYLIST_NAME;
        currentSongId = 0;
        addSongs(FileUtil.loadSong(DEFAULT_PLAYLIST_LOCATION));
        repeat = REPEAT_ALL;
        shuffle = SHUFFLE_OFF;
    }

    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }

    public void setShuffle(boolean shuffle) {
        this.shuffle = shuffle;
    }

    public int getRepeat() {
        return repeat;
    }

    public boolean getShuffle() {
        return shuffle;
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
            return null;
            //throw new RuntimeException("No song found");
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
    public void addSongs(List<String> list) {
        for ( String str : list )
        {
            if ( !songSet.contains(str) ) {
                songSet.add(str) ;
                songList.add(str) ;
            }

        }

    }

    @Override
    public String removeSongs(List<String> songList) {
        return "Can't Delete Songs from Global Playlist";
    }

}
