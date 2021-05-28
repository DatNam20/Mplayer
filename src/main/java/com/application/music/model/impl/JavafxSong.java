package com.application.music.model.impl;

import com.application.music.model.Song;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

public class JavafxSong implements Song
{
    private String name;
    private String mediaFile;
    private String album;
    private String artist;
    private int id;
    private int length;
    private Media media;
    private MediaPlayer mp;


    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getArtist() {
        return null;
    }

    @Override
    public String getAlbum() {
        return null;
    }

    @Override
    public int getLength() {
        return 0;
    }

    @Override
    public void stop() {
        mp.stop();
    }

    @Override
    public void play() {
        mp.play();
    }

    @Override
    public void pause() {
        mp.pause();

    }

    @Override
    public void skipAhead() {

    }

    @Override
    public void skipBack() {

    }

    public void setMediaFile(String mediaFile, String function) {
        this.mediaFile = mediaFile;
        media = new Media(new File(mediaFile).toURI().toString());
        mp = new MediaPlayer(media);
        System.out.println(mp.getStatus());

        if ( function.equals("Play") )
        {
            this.play() ;
        }
        else if ( function.equals("Pause") )
        {
            this.pause() ;

        }
        else if ( function.equals("Stop") )
        {
            this.stop() ;
        }

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLength(int length) {
        this.length = length;
    }

}
