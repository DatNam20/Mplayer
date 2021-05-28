package com.application.music.model.impl;

import com.application.music.model.Playlist;
import com.application.music.model.Song;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.awt.Desktop;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

import static com.application.music.utility.ApplicationConstant.DEFAULT_PLAYLIST_LOCATION;

public class GlobalPlaylist implements Playlist {
    private String name;
    private String location = DEFAULT_PLAYLIST_LOCATION;
    private ArrayList<String> list;
    private int repeat;
    private boolean shuffle;
    private JavafxSong song;


    @Override
    public void functionCalled(String function) {

        File directory = new File(location);

        File[] fileList = directory.listFiles( new FilenameFilter() {

            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".mp3");
            }
        });

//        song = new JavafxSong();
        for (int i=0; i< fileList.length; i++)
        {
            song = new JavafxSong();
            song.setMediaFile(fileList[i].getAbsolutePath(), function);
            System.out.println(" # "+fileList[i].getName()) ;
        }

//        song.setMediaFile(fileList[0].getAbsolutePath(), function);
//        System.out.println(" # "+fileList[0].getName()) ;


    }


    @Override
    public void createList() {

    }

    @Override
    public void setName() {

    }

    @Override
    public void viewList() {

    }

    @Override
    public void addSong() {

    }

    @Override
    public void removeSong() {

    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }

    public void setShuffle(boolean shuffle) {
        this.shuffle = shuffle;
    }
}
