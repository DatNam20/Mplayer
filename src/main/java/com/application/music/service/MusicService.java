package com.application.music.service;

import com.application.music.controller.Controller;
import com.application.music.dto.BasicSongDto;
import com.application.music.dto.PlaylistDto;
import com.application.music.model.Playlist;
import com.application.music.model.Song;
import com.application.music.model.impl.GlobalPlaylist;
import com.application.music.model.impl.JavafxSong;
import com.application.music.observer.SongObserver;
import org.apache.commons.lang3.StringUtils;


import java.io.File;
import java.time.Duration;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;

import static com.application.music.utility.ApplicationConstant.*;


public class MusicService implements SongObserver {

    private Playlist plist;
    private Song currentSong;
    Controller controller;

    Logger logger = Logger.getLogger(MusicService.class.getName());

    public MusicService(Controller controller){
        logger.info( "Creating Music Service");
        plist = new GlobalPlaylist();
        currentSong = new JavafxSong(plist.getCurrentSongPath(), this);
        this.controller = controller;
        logger.info( "Music Service created with default playlist and song");
    }

    public String openDirectory(File file){
        logger.info( "Opening Directory : " + file.getAbsolutePath());

        File[] contentList = file.listFiles() ;
        if (contentList != null && contentList.length > 0) {
            for(int i=0; i< contentList.length; i++) {
                if (contentList[i].isDirectory())
                    openDirectory(contentList[i]);
            }

        }
        plist.loadSong(file.getAbsolutePath());

        return file.getAbsolutePath()==null?"Cant Open":file.getAbsolutePath();
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
    public void updateSongStatus(String status, Object value) {
        if(status.equals(SONG_END)){
            this.nextSong();
            this.playCurrentSong();
        } else if(status.equals(SONG_READY)){
            controller.updateSongDetails(getSongDto());
        }
    }

    public int updateRepeat(){
        plist.setRepeat((plist.getRepeat() + 1)%TOTAL_REPEAT_OPTIONS);
        return plist.getRepeat();
    }
    public boolean updateShuffle(){
        plist.setShuffle(!plist.getShuffle());
        return plist.getShuffle();
    }

    public BasicSongDto getSongDto() {
        BasicSongDto songDto = new BasicSongDto();
        songDto.setSongName(currentSong.getSongName());
        songDto.setSongDuration(currentSong.getDuration());
        songDto.setAlbum(StringUtils.defaultIfEmpty(currentSong.getAlbum(), "Album-NA"));
        songDto.setArtist(StringUtils.defaultIfEmpty(currentSong.getArtist(), "Artist-NA"));
        songDto.setImage(currentSong.getImage());
        return songDto;
    }

    public double getElapsedTime() {
        return (currentSong.getCurrentTime());
    }

    public void seek(Double time) {
        currentSong.seek(time) ;
    }

}
