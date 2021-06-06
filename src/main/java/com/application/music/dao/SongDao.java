package com.application.music.dao;

import java.util.List;

public interface SongDao {
    public List<String> getAllSongsFromPlaylist(Integer pid);
    public List<String> getAllPlaylist();
    public void addPlaylist(String playlist);
    public void deletePlaylist(Integer pid);
    public void addSongsToPlaylist(List<String> songList, Integer pid);
    public void deleteSongsFromPlaylist(List<String> songList, Integer pid);
    public void renamePlaylist(Integer pid, String newName);
    public void addSourceFolder(String folder);
    public void removeSourceFolder(String folder);
    public List<String> getAllSourceFolder();
}
