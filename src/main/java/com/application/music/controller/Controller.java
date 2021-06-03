package com.application.music.controller;

import com.application.music.dto.BasicSongDto;
import com.application.music.dto.PlaylistDto;
import com.application.music.observer.SongObserver;
import com.application.music.service.MusicService;
import com.application.music.utility.TimeUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.time.Duration;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static com.application.music.utility.ApplicationConstant.*;

public class Controller{
    Logger logger = Logger.getLogger(Controller.class.getName());

    public static volatile boolean shutdown=false;

    @FXML
    private Button btn;

    @FXML
    private Button playpause;

    @FXML
    private Pane rootPaneId;

    @FXML
    private Slider slider;

    @FXML
    private Label playlistLabel, songNameLabel, duration, artistLabel, albumLabel;

    @FXML
    private ListView<String> playlistView;

    private MusicService mser;

    public Controller(){
         mser = new MusicService(this);
    }

    @FXML
    public void initialize(){
        refreshPlaylist(mser.getPlaylistDto());
        slider.setOnMouseClicked(mouseEvent -> {
            mser.seek(slider.getValue());
        });
    }



    @FXML protected void playpause(ActionEvent event) {
        btn = (Button) event.getSource();

        if(UI_PLAY.equals(playpause.getText())){
            mser.playCurrentSong();
            playpause.setText(UI_PAUSE);
        }else if (UI_PAUSE.equals(playpause.getText())){
            mser.pauseCurrentSong();
            playpause.setText(UI_PLAY);
        }else
            throw new RuntimeException("UNKNOWN Selection at play / pause");
        updateSongDetails(mser.getSongDto());
    }

    public void updateSongDetails(BasicSongDto songDto) {
        songNameLabel.setText(songDto.getSongName());
        duration.setText(TimeUtil.elapsedTime(songDto.getSongDuration().intValue()));
        slider.setMax(songDto.getSongDuration());
        artistLabel.setText(songDto.getArtist());
        albumLabel.setText(songDto.getAlbum());
        updateSlider();
    }

    public void updateSlider()
    {

        Runnable r = new Runnable() {
            public void run() {
                while ( playpause.getText().equals(UI_PAUSE)) {

                    //System.out.println(mp.getCurrentTime().toMinutes());      // Debug

                    slider.setValue(mser.getElapsedTime());

                    try
                    {
                        Thread.sleep(1000);
                    }
                    catch(InterruptedException ex)
                    {
//                        Thread.currentThread().interrupt();
                    }
                }
            }
        };
//        slider.setValue(duration.toSeconds());
        Thread thread = new Thread(r);
        thread.setDaemon(true);
        thread.start();
    }

    @FXML protected void stop(ActionEvent event) {
        btn = (Button) event.getSource();
        mser.stopCurrentSong();
        playpause.setText(UI_PLAY);
    }

    @FXML protected void next(ActionEvent event) {
        btn = (Button) event.getSource();
        mser.nextSong();
        if(UI_PLAY.equals(playpause.getText())){
            playpause.setText(UI_PAUSE);
        }
        mser.playCurrentSong();
        updateSongDetails(mser.getSongDto());
    }

//    @FXML protected void seek(MouseEvent event) {
//        mser.seek(slider.getValue());
//        updateSongDetails(mser.getSongDto());
//    }

    @FXML protected void skipAhead(ActionEvent event) {
        btn = (Button) event.getSource();
        mser.seek(mser.getElapsedTime()+SKIP_CONST);
        updateSongDetails(mser.getSongDto());
    }

    @FXML protected void skipBack(ActionEvent event) {
        btn = (Button) event.getSource();
        mser.seek(mser.getElapsedTime()-SKIP_CONST);
        updateSongDetails(mser.getSongDto());
    }

    @FXML protected void previous(ActionEvent event) {
        btn = (Button) event.getSource();
        mser.prevSong();
        if(UI_PLAY.equals(playpause.getText())){
            playpause.setText(UI_PAUSE);
        }
        mser.playCurrentSong();
        updateSongDetails(mser.getSongDto());
    }

    @FXML protected void openFolder(ActionEvent event) {
        btn = (Button) event.getSource();
        Stage stage = (Stage)rootPaneId.getScene().getWindow();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File file = directoryChooser.showDialog(stage);
        if(file.exists()) {
            mser.openDirectory(file);
            refreshPlaylist(mser.getPlaylistDto());
        }else{
            playlistLabel.setText("Cant Open this folder");
            throw new RuntimeException("Cant open folder" + file.getAbsolutePath());
        }



    }

    @FXML protected void repeat(ActionEvent event) {
        btn = (Button) event.getSource();
        if(mser.updateRepeat()==0)
            btn.setText("REPT1") ;
        else
            btn.setText("REPT@") ;
    }

    @FXML protected void shuffle(ActionEvent event) {
        btn = (Button) event.getSource();
        if(mser.updateShuffle())
            btn.setText("SHFL0") ;
        else
            btn.setText("SHFL1") ;
    }

    private void refreshPlaylist(PlaylistDto playlistDto) {
        playlistLabel.setText(playlistDto.getPlaylistName());
        ObservableList<String> items = FXCollections.observableArrayList(playlistDto.getPlaylistSong().stream().map(str -> str.substring((str.lastIndexOf(FILE_NAME_SEPARATOR)+2),(str.length()-4))).collect(Collectors.toList()));
        playlistView.setItems(items);
    }


}
