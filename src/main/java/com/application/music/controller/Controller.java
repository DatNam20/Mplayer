package com.application.music.controller;

import com.application.music.dto.PlaylistDto;
import com.application.music.service.MusicService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.stream.Collectors;

import static com.application.music.utility.ApplicationConstant.*;

public class Controller {
    @FXML
    private Button btn;

    @FXML
    private Button playpause;

    @FXML
    private Pane rootPaneId;

    @FXML
    private Label playlistLabel, songNameLabel;

    @FXML
    private ListView<String> playlistView;

    private MusicService mser;

    public Controller(){
         mser = new MusicService();
    }

    @FXML
    public void initialize(){
        refreshPlaylist(mser.getPlaylistDto());
    }

    @FXML protected void playpause(ActionEvent event) {
        btn = (Button) event.getSource();
        songNameLabel.setText(mser.getSongName());
        if(UI_PLAY.equals(playpause.getText())){
            mser.playCurrentSong();
            playpause.setText(UI_PAUSE);
        }else if (UI_PAUSE.equals(playpause.getText())){
            mser.pauseCurrentSong();
            playpause.setText(UI_PLAY);
        }else
            throw new RuntimeException("UNKNOWN Selection at play / pause");
    }

    @FXML protected void stop(ActionEvent event) {
        btn = (Button) event.getSource();
        mser.stopCurrentSong();
        playpause.setText(UI_PLAY);
    }

    @FXML protected void next(ActionEvent event) {
        btn = (Button) event.getSource();
        mser.nextSong();
        songNameLabel.setText(mser.getSongName());
        if(UI_PLAY.equals(playpause.getText())){
            playpause.setText(UI_PAUSE);
        }
        mser.playCurrentSong();
    }

    @FXML protected void previous(ActionEvent event) {
        btn = (Button) event.getSource();
        mser.prevSong();
        songNameLabel.setText(mser.getSongName());
        if(UI_PLAY.equals(playpause.getText())){
            playpause.setText(UI_PAUSE);
        }
        mser.playCurrentSong();
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

    private void refreshPlaylist(PlaylistDto playlistDto) {
        playlistLabel.setText(playlistDto.getPlaylistName());
        ObservableList<String> items = FXCollections.observableArrayList(playlistDto.getPlaylistSong().stream().map(str -> str.substring((str.lastIndexOf(FILE_NAME_SEPARATOR)+2),(str.length()-4))).collect(Collectors.toList()));
        playlistView.setItems(items);
    }
}
