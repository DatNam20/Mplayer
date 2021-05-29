package com.application.music.model.impl;

import com.application.music.model.Song;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

public class JavafxSong implements Song
{
    private String songName;
    private Media media;
    private MediaPlayer mp;

    public JavafxSong(String path){
        File songFile = new File(path);
        media = new Media(songFile.toURI().toString());
        mp = new MediaPlayer(media);
        System.out.println(mp.getStatus());
        setSongName(songFile.getName().substring(0,songFile.getName().length()-4));
    }

    private void setSongName(String songName) {
        this.songName = songName;
    }

    @Override
    public String getSongName() {
        return songName;
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
}
