package com.application.music.model.impl;

import com.application.music.model.Song;
import com.application.music.observer.SongObserver;
import javafx.collections.MapChangeListener;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.util.logging.Logger;

import static com.application.music.utility.ApplicationConstant.*;

public class JavafxSong implements Song {
    private Logger logger = Logger.getLogger(JavafxSong.class.getName());
    private String songName;
    private Media media;
    private MediaPlayer mp;
    private SongObserver serviceObserver;
    private String album;
    private String artist;
    private Object image;


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
                notifyObservers(SONG_END,null);
            }
        });
        media.getMetadata().addListener(new MapChangeListener<String, Object>() {
            @Override
            public void onChanged(Change<? extends String, ? extends Object> ch) {
                if (ch.wasAdded()) {
                    handleMetadata(ch.getKey(), ch.getValueAdded());
                }
            }
        });
        mp.setOnReady(new Runnable() {
            @Override
            public void run() {
                notifyObservers(SONG_READY, null);
            }
        });

//        mp.currentTimeProperty()
//                .addListener((ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue)
//                        -> {
//            notifyObservers(UPDATE_SLIDER, newValue.toSeconds());
//        });
    }

    private void handleMetadata(String key, Object value) {
        if (key.equals("album")) {
            album = (value.toString());
        } else if (key.contains("artist")) {
            artist = (value.toString());
        } if (key.equals("title")) {
            songName = (value.toString());
        }  if (key.equals("image")) {
            image = value;
        }
    }

    @Override
    public void pause() {
        mp.pause();
        logger.info(mp.getStatus().toString());
    }

    @Override
    public Double getDuration() {
        return mp.getTotalDuration().toSeconds();
    }

    @Override
    public String getAlbum() {
        return album;
    }


    @Override
    public String getArtist() {
        return artist;
    }

    @Override
    public Object getImage() {
        return image;
    }

    @Override
    public double getCurrentTime() {
        return mp.getCurrentTime().toSeconds();
    }

    @Override
    public void seek(double currentTime) {
        if(currentTime<=0)
            currentTime = 0.0 ;
        else if(null != getDuration() && currentTime>=getDuration())
            currentTime = getDuration();
        mp.seek(Duration.seconds(currentTime));
    }


    public void notifyObservers(String status,Object value) {
        serviceObserver.updateSongStatus(status,value);
    }
}
