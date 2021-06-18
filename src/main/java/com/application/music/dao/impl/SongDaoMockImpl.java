package com.application.music.dao.impl;

import com.application.music.dao.SongDao;

import java.util.*;
import java.util.logging.Logger;

public class SongDaoMockImpl implements SongDao {

    private Set<String> playlistDetail;

    private Set<String> songDetail;

    private Map<String, Set<String>> playlist;

    private Map<String, String> sourceFolders;

    private Logger logger = Logger.getLogger(this.getClass().getName());

    public SongDaoMockImpl(){
        playlistDetail = new HashSet<>();
        songDetail = new HashSet<>();
        playlist = new HashMap<>();
        sourceFolders = new HashMap<>();
    }


    @Override
    public Set<String> getAllSongsFromPlaylist(String name) {
        return playlist.get(name);
    }

    @Override
    public Set<String> getAllPlaylist() {
        return playlistDetail;
    }

    @Override
    public Set<String> getAllSongs() {
        return songDetail;
    }

    @Override
    public boolean addPlaylist(String name) {
        if(playlistDetail.contains(name))
            return false;
        playlistDetail.add(name);
        playlist.put(name, new HashSet<>());
        return true;
    }

    @Override
    public boolean deletePlaylist(String name) {
        if(playlistDetail.contains(name)){
            playlistDetail.remove(name);
            return true;
        }
        return false;
    }

    @Override
    public boolean addSongsToPlaylist(List<String> songList, String name) {
        Set<String> songSet = new HashSet<>(songList);
        if(playlist.containsKey(name)){
            playlist.get(name).addAll(songSet);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteSongsFromPlaylist(List<String> songList, String name) {
        logger.info(name);
        logger.info(songList.toString());
        if(playlist.containsKey(name)){
            playlist.get(name).removeAll(songList);
            return true;
        }
        logger.info("Failed to delete");
        return false;
    }

    @Override
    public boolean renamePlaylist(String name, String newName) {
        if( !playlistDetail.contains(newName) && playlistDetail.contains(name)){
            playlistDetail.remove(name);
            playlistDetail.add(newName);
            if(playlist.containsKey(name)){
                playlist.put(newName, playlist.get(name));
                playlist.remove(name);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean addSourceFolder(String folderName, String folderPath) {
        if(!sourceFolders.containsKey(folderName)) {
            sourceFolders.put(folderName, folderPath);
            return true;
        }
        return false;
    }

    @Override
    public String removeSourceFolder(String folderName) {
        String returnValue = "" ;
        if(sourceFolders.containsKey(folderName)){
            returnValue += sourceFolders.get(folderName);
            sourceFolders.remove(folderName);
        }
        return returnValue;
    }

    @Override
    public List<String> getAllSourceFolder() {
        return new ArrayList<String>(sourceFolders.keySet());
    }

    @Override
    public void updateAllSongs(List<String> songList) {
        Set<String> newSong = new HashSet<>(songList);
        Set<String> oldSong = new HashSet<>(songDetail);
        oldSong.removeAll(newSong);
        for(Map.Entry entry: playlist.entrySet() ){
            Set<String> tempSet = new HashSet<>();
            for(String path: (Set<String>)entry.getValue()){
                if(oldSong.contains(path)){
                    tempSet.add(path);
                }
            }
            ((Set<String>) entry.getValue()).removeAll(tempSet);

//            List<String> temp = new ArrayList<>();
//            for(String path: (List<String>)entry.getValue()){
//                if(oldSong.contains(path)){
//                    temp.add(path);
//                }
//            }
//            ((List<String>) entry.getValue()).removeAll(temp);
        }
        songDetail = newSong;
    }
}
