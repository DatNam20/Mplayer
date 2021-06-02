package com.application.music.service;

import com.application.music.dto.PlaylistDto;
import com.application.music.model.Playlist;
import com.application.music.model.Song;
import com.application.music.model.impl.GlobalPlaylist;
import com.application.music.model.impl.JavafxSong;
import com.application.music.observer.SongObserver;

import java.io.File;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;

import static com.application.music.utility.ApplicationConstant.SONG_END;


public class MusicService implements SongObserver {

    private Playlist plist;
    private Song currentSong;

    Logger logger = Logger.getLogger(MusicService.class.getName());

    public MusicService(){
        logger.info( "Creating Music Service");
        plist = new GlobalPlaylist();
        currentSong = new JavafxSong(plist.getCurrentSongPath(), this);
        logger.info( "Music Service created with default playlist and song");
    }

    public String openDirectory(File file){
        logger.info( "Opening Directory : " + file.getAbsolutePath());
        plist.loadSong(file.getAbsolutePath());
        return file.getAbsolutePath()==null?"Cant Open":file.getAbsolutePath();
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

    public void nextSong() {
        logger.info( "playNextSong called");
        currentSong.stop();
        plist.nextSong();
        currentSong = new JavafxSong(plist.getCurrentSongPath(),this);
        logger.info( "Next Song : " + currentSong.getSongName());
    }

    public void prevSong() {
        logger.info( "playPrevSong called");
        currentSong.stop();
        plist.prevSong();
        currentSong = new JavafxSong(plist.getCurrentSongPath(),this);
        logger.info( "Previous Song : " + currentSong.getSongName());
    }

    public PlaylistDto getPlaylistDto() {
        PlaylistDto playlistDto = new PlaylistDto();
        playlistDto.setPlaylistName(plist.getPlaylistName());
        playlistDto.setPlaylistSong(plist.getSongList());
        return playlistDto;
    }

    @Override
    public void updateSongStatus(String status) {
        if(status.equals(SONG_END)){
            this.nextSong();
            this.playCurrentSong();
        }
    }
}
