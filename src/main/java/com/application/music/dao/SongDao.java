package com.application.music.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SongDao {
    public Set<String> getAllSongsFromPlaylist(String name);
    public Set< String> getAllPlaylist();
    public Set< String> getAllSongs();
    public boolean addPlaylist(String playlist);
    public boolean deletePlaylist(String name);
    public boolean addSongsToPlaylist(List<String> songList, String name);
    public boolean deleteSongsFromPlaylist(List<String> songList, String name);
    public boolean renamePlaylist(String name, String newName);
    public boolean addSourceFolder(String folderName, String folderPath);
    public String removeSourceFolder(String folderName);
    public List<String> getAllSourceFolder();
    public void updateAllSongs(List<String> songList);
}
