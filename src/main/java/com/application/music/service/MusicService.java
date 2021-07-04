package com.application.music.service;

import com.application.music.controller.Controller;
import com.application.music.dao.SongDao;
import com.application.music.dao.impl.SongDaoMockImpl;
import com.application.music.dto.BasicSongDto;
import com.application.music.dto.PlaylistDto;
import com.application.music.model.Playlist;
import com.application.music.model.Song;
import com.application.music.model.impl.CustomPlaylist;
import com.application.music.model.impl.GlobalPlaylist;
import com.application.music.model.impl.JavafxSong;
import com.application.music.observer.SongObserver;
import com.application.music.utility.FileUtil;
import javafx.scene.image.Image;
import org.apache.commons.lang3.StringUtils;


import java.io.File;
import java.util.*;

import java.util.logging.Logger;

import static com.application.music.utility.ApplicationConstant.*;


public class MusicService implements SongObserver {

    private Playlist currentPlaylist,globalPlaylist;
    private Set<String> customPlaylists;
    private Song currentSong;
    private ArrayList<SongObserver> songObserver;
    private SongDao songDao;

    private static MusicService mser;

    Logger logger = Logger.getLogger(MusicService.class.getName());

    public static MusicService getMusicService(SongObserver controller){
        if(mser==null)
            mser = new MusicService(controller);
        return mser;
    }

    private MusicService(SongObserver controller){
        songDao = new SongDaoMockImpl() ;
        globalPlaylist = new GlobalPlaylist();
        globalPlaylist.setSongList(new ArrayList<>(songDao.getAllSongs())) ;
        currentPlaylist = globalPlaylist;
        customPlaylists = songDao.getAllPlaylist();
        songObserver = new ArrayList<>();
        this.songObserver.add(controller);
        this.songObserver.add(this);
        if(null != currentPlaylist.getCurrentSongPath())
            currentSong = new JavafxSong(currentPlaylist.getCurrentSongPath(), songObserver);
    }

    private boolean checkSong( ) {
        if ( currentSong == null ) {
            if ( currentPlaylist == null || currentPlaylist.getSongList().size() == 0 )
                return false ;
            else {
                currentSong = new JavafxSong(currentPlaylist.getCurrentSongPath(), songObserver);
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

    public boolean stopCurrentSong() {
        if (checkSong() == true ) {
            currentSong.stop();
            return true;
        }
        return false ;
    }

    public boolean nextSong() {
        if (checkSong() == true ) {
            currentSong.stop();
            currentPlaylist.nextSong();
            currentSong = new JavafxSong(currentPlaylist.getCurrentSongPath(),songObserver);
            return true;
        }
        return false ;
    }

    public boolean prevSong() {
        if (checkSong() == true ) {
            currentSong.stop();
            currentPlaylist.prevSong();
            currentSong = new JavafxSong(currentPlaylist.getCurrentSongPath(),songObserver);
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
            songDto.setSongElapsedTime(currentSong.getCurrentTime());
        }
        else {
            songDto.setSongName(" ");
            songDto.setSongDuration(0.0);
            songDto.setAlbum(" ");
            songDto.setArtist(" ");
            songDto.setImage(new Image(getClass().getResource(IMAGE_FILE_PATH).toString()));
            songDto.setSongElapsedTime(0.0);
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
        playlistDto.setPlaylistName(currentPlaylist.getPlaylistName());
        playlistDto.setPlaylistSong(currentPlaylist.getSongList());
        return playlistDto;
    }

    public int updateRepeat(){
        currentPlaylist.setRepeat((currentPlaylist.getRepeat() + 1)%TOTAL_REPEAT_OPTIONS);
        return currentPlaylist.getRepeat();
    }

    /**
     * This method toggles the shuffle option in the menu
     * @return : boolean - the current shuffle status
     */
    public boolean updateShuffle(){
        currentPlaylist.setShuffle(!currentPlaylist.getShuffle());
        return currentPlaylist.getShuffle();
    }


    public void setCurrentPlaylist(String selectedPlaylist){
        List<String> songList = new ArrayList<>(songDao.getAllSongsFromPlaylist(selectedPlaylist));
        currentPlaylist = new CustomPlaylist( selectedPlaylist, songList );
    }

    public void createPlaylist(String playlistName){
        songDao.addPlaylist(playlistName) ;
    }

    public void deletePlaylist(String playlistName){
        songDao.deletePlaylist(playlistName) ;
    }

    public void renamePlaylist(String playlistName){
        boolean check = songDao.renamePlaylist(currentPlaylist.getPlaylistName(), playlistName) ;
        if (check==true)
            currentPlaylist.setPlaylistName(playlistName);
    }

    public void addToPlaylist(String toPlaylist, List<String> songList){

        songDao.addSongsToPlaylist( songList, toPlaylist ) ;
        currentPlaylist.setSongList(new ArrayList<>(songDao.getAllSongsFromPlaylist(toPlaylist)));
    }

    public void removeFromPlaylist(String playlistName, List<String> songList){
//      parameter - List<String> songlist
        songDao.deleteSongsFromPlaylist(songList, playlistName) ;
        currentPlaylist.setSongList(new ArrayList<>(songDao.getAllSongsFromPlaylist(playlistName)));
    }


    public void addDirectory(File file){
        songDao.addSourceFolder(file.getName(), file.getAbsolutePath());
        ArrayList<String> newSongs = new ArrayList<>(songDao.getAllSongs()) ;
        newSongs.addAll(getAllFiles(file));
        songDao.updateAllSongs(newSongs);
        globalPlaylist.addSongs(newSongs);
    }

    public void removeDirectory(String folderName){
        String filepath = songDao.removeSourceFolder(folderName);
        ArrayList<String> newSongs = new ArrayList<>(songDao.getAllSongs()) ;
        newSongs.removeAll(getAllFiles(new File(filepath)));
        songDao.updateAllSongs(newSongs);
        globalPlaylist.addSongs(newSongs);
    }

    public List<String> getAllFiles(File file){
        File[] contentList = file.listFiles() ;
        ArrayList<String> songs = new ArrayList<>() ;
        if (contentList != null && contentList.length > 0) {
            for(int i=0; i< contentList.length; i++) {
                if (contentList[i].isDirectory())
                    songs.addAll(getAllFiles(contentList[i]));
            }
            songs.addAll(FileUtil.loadSong(file.getAbsolutePath()))  ;
        }
        return songs ;
    }

    public SongDao getSongDao() {
        return songDao;
    }

    public void loadGlobalPlaylist() {
        globalPlaylist = new GlobalPlaylist();
        globalPlaylist.setSongList(new ArrayList<>(songDao.getAllSongs())) ;
        currentPlaylist = globalPlaylist;
        if(null != currentPlaylist.getCurrentSongPath())
            currentSong = new JavafxSong(currentPlaylist.getCurrentSongPath(), songObserver);
    }

    public List<String> getAllSongs(){
        return new ArrayList<>(songDao.getAllSongs());
    }
}
