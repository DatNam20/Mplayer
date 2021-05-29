package com.application.music.service;

import com.application.music.model.Playlist;
import com.application.music.model.Song;
import com.application.music.model.impl.GlobalPlaylist;
import com.application.music.model.impl.JavafxSong;

import java.io.File;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;


public class MusicService {

    private Playlist plist;
    private Song currentSong;

    Logger logger = Logger.getLogger(MusicService.class.getName());

    public MusicService(){
        logger.info( "Creating Music Service");
        plist = new GlobalPlaylist();
        currentSong = new JavafxSong(plist.getCurrentSongPath());
        logger.info( "Music Service created with default playlist and song");
    }

    public String openDirectory(File file){
        logger.info( "Opening Directory : " + file.getAbsolutePath());
        plist.setLocation(file.getAbsolutePath());
        plist.createSongList();
        return file.getAbsolutePath()==null?"Cant Open":file.getAbsolutePath();
    }



    public List<String> getPlaylistSong() {
        return plist.getSongList();
    }

    public String getPlaylistName() {
        return plist.getPlaylistName();
    }

    public String getSongName() {
        return currentSong.getSongName();
    }

    public void playCurrentSong() {
        logger.info( "play called");
        currentSong.play();
    }

    public void pauseCurrentSong() {
        logger.info( "pause called");
        currentSong.pause();
    }

    public void stopCurrentSong() {
        logger.info( "stop called");
        currentSong.stop();
    }

    public void playNextSong() {
        logger.info( "playNextSong called");
        currentSong.stop();
        plist.nextSong();
        currentSong = new JavafxSong(plist.getCurrentSongPath());
        logger.info( "Next Song : " + currentSong.getSongName());
        currentSong.play();
    }

    public void playPrevSong() {
        logger.info( "playPrevSong called");
        currentSong.stop();
        plist.prevSong();
        currentSong = new JavafxSong(plist.getCurrentSongPath());
        logger.info( "Previous Song : " + currentSong.getSongName());
        currentSong.play();
    }
}
