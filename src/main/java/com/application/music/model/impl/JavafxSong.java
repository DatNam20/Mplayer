package com.application.music.model.impl;

import com.application.music.model.Song;
import com.application.music.observer.SongObserver;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static com.application.music.utility.ApplicationConstant.SONG_END;

public class JavafxSong implements Song {
    private Logger logger = Logger.getLogger(JavafxSong.class.getName());
    private String songName;
    private Media media;
    private MediaPlayer mp;
    private SongObserver serviceObserver;

    public JavafxSong(String path, SongObserver observer){
        File songFile = new File(path);
        media = new Media(songFile.toURI().toString());
        mp = new MediaPlayer(media);
        System.out.println(mp.getStatus());
        setSongName(songFile.getName().substring(0,songFile.getName().length()-4));
        serviceObserver = observer;

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
        logger.info(mp.getStatus().toString());
    }

    @Override
    public void play() {
        mp.play();
        logger.info(mp.getStatus().toString());
        mp.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mp.stop();
                notifyObservers(SONG_END);
            }
        });
    }

    @Override
    public void pause() {
        mp.pause();
        logger.info(mp.getStatus().toString());
    }


    public void notifyObservers(String status) {
        serviceObserver.updateSongStatus(status);
    }
}
