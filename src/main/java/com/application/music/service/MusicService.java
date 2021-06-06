package com.application.music.service;

import com.application.music.controller.Controller;
import com.application.music.dto.BasicSongDto;
import com.application.music.dto.PlaylistDto;
import com.application.music.model.Playlist;
import com.application.music.model.Song;
import com.application.music.model.impl.GlobalPlaylist;
import com.application.music.model.impl.JavafxSong;
import com.application.music.observer.SongObserver;
import com.application.music.utility.FileUtil;
import javafx.scene.image.Image;
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
        if(null != plist.getCurrentSongPath())
            currentSong = new JavafxSong(plist.getCurrentSongPath(), this);
        this.controller = controller;
        logger.info( "Music Service created with default playlist and song");
    }

    private boolean checkSong( ) {
        if ( currentSong == null ) {
            if ( plist == null || plist.getSongList().size() == 0 )
                return false ;
            else {
                currentSong = new JavafxSong(plist.getCurrentSongPath(), this);
                return true;
            }
        }

        return true ;
    }

/*********************** Song Controls *************************/
    public boolean playCurrentSong() {
        if (checkSong() == true ) {
            currentSong.play();
            return true;
        }
        return false ;
    }

    public boolean pauseCurrentSong() {
        if (checkSong() == true ) {
            currentSong.pause();
            return true;
        }
        return false ;
    }

/*    public boolean stopCurrentSong() {
        if (checkSong() == true ) {
            currentSong.stop();
            return true;
        }
        return false ;
    }
*/
    public boolean nextSong() {
        logger.info( "playNextSong called");
        if (checkSong() == true ) {
            currentSong.stop();
            plist.nextSong();
            currentSong = new JavafxSong(plist.getCurrentSongPath(),this);
            logger.info( "Next Song : " + currentSong.getSongName());
            return true;
        }
        return false ;
    }

    public boolean prevSong() {
        logger.info( "playPrevSong called");
        if (checkSong() == true ) {
            currentSong.stop();
            plist.prevSong();
            currentSong = new JavafxSong(plist.getCurrentSongPath(),this);
            logger.info( "Previous Song : " + currentSong.getSongName());
            return true;
        }
        return false ;
    }

    /*********************** Song updates ************************/

    @Override
    public void updateSongStatus(String status, Object value) {
        if(status.equals(SONG_END)){
            this.nextSong();
            this.playCurrentSong();
        } else if(status.equals(SONG_READY)){
            controller.updateSongDetails(getSongDto());
        }
    }


    public BasicSongDto getSongDto() {
        BasicSongDto songDto = new BasicSongDto();
        if (checkSong() == true ) {
            songDto.setSongName(currentSong.getSongName());
            songDto.setSongDuration(currentSong.getDuration());
            songDto.setAlbum(StringUtils.defaultIfEmpty(currentSong.getAlbum(), "Album-NA"));
            songDto.setArtist(StringUtils.defaultIfEmpty(currentSong.getArtist(), "Artist-NA"));
            songDto.setImage((currentSong.getImage()==null)?new Image(getClass().getResource(IMAGE_FILE_PATH).toString()):currentSong.getImage());
        }
        else {
            songDto.setSongName(" ");
            songDto.setSongDuration(0.0);
            songDto.setAlbum(" ");
            songDto.setArtist(" ");
            songDto.setImage(new Image(getClass().getResource(IMAGE_FILE_PATH).toString()));
        }
        return songDto;
    }

    public double getElapsedTime() {
        if (checkSong() == true ) {
            return (currentSong.getCurrentTime());
        }
        return 0.0 ;
    }

    public boolean seek(Double time) {
        if (checkSong() == true ) {
            currentSong.seek(time) ;
            return true;
        }
        return false ;
    }


/******************************* Playlist ******************************/

    public PlaylistDto getPlaylistDto() {
        PlaylistDto playlistDto = new PlaylistDto();
        playlistDto.setPlaylistName(plist.getPlaylistName());
        playlistDto.setPlaylistSong(plist.getSongList());
        return playlistDto;
    }

    public int updateRepeat(){
        plist.setRepeat((plist.getRepeat() + 1)%TOTAL_REPEAT_OPTIONS);
        return plist.getRepeat();
    }

    /**
     * This method toggles the shuffle option in the menu
     * @return : boolean - the current shuffle status
     */
    public boolean updateShuffle(){
        plist.setShuffle(!plist.getShuffle());
        return plist.getShuffle();
    }


    public void createPlaylist(String playlistName){

    }

    public void deletePlaylist(Integer playlistId){

    }

    public void renamePlaylist(Integer playlistId){

    }

    public void addToPlaylist(List<Integer> songId, Integer playlistId){

    }

    public void removeFromPlaylist(List<Integer> songId, Integer playlistId){

    }


    public void addDirectory(File file){

    }

    public void removeDirectory(Integer pathId){

    }

    public void refreshAllSongs(){

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
        plist.addSongs(FileUtil.loadSong(file.getAbsolutePath()));

        return file.getAbsolutePath()==null?"Cant Open":file.getAbsolutePath();
    }


}
