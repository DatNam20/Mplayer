package com.application.music.controller;

import com.application.music.service.MusicService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

import static com.application.music.utility.ApplicationConstant.*;

public class Controller {
    @FXML
    private Button btn;

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
        refreshPlaylist(mser.getPlaylistSong());
    }

    @FXML protected void playpause(ActionEvent event) {
        btn = (Button) event.getSource();
        songNameLabel.setText(mser.getSongName());
        if(UI_PLAY.equals(btn.getText())){
            mser.playCurrentSong();
            btn.setText(UI_PAUSE);
        }else if (UI_PAUSE.equals(btn.getText())){
            mser.pauseCurrentSong();
            btn.setText(UI_PLAY);
        }else
            throw new RuntimeException("UNKNOWN Selection at play / pause");
    }

    @FXML protected void stop(ActionEvent event) {
        btn = (Button) event.getSource();
        mser.stopCurrentSong();
    }

    @FXML protected void next(ActionEvent event) {
        btn = (Button) event.getSource();
        mser.playNextSong();
        songNameLabel.setText(mser.getSongName());
    }

    @FXML protected void previous(ActionEvent event) {
        btn = (Button) event.getSource();
        mser.playPrevSong();
        songNameLabel.setText(mser.getSongName());
    }

    @FXML protected void openFolder(ActionEvent event) {
        btn = (Button) event.getSource();
        Stage stage = (Stage)rootPaneId.getScene().getWindow();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File file = directoryChooser.showDialog(stage);
        if(file.exists()) {
            mser.openDirectory(file);
            refreshPlaylist(mser.getPlaylistSong());
        }else{
            playlistLabel.setText("Cant Open this folder");
            throw new RuntimeException("Cant open folder" + file.getAbsolutePath());
        }



    }

    private void refreshPlaylist(List<String> playlistSong) {
        playlistLabel.setText(mser.getPlaylistName());
        ObservableList<String> items = FXCollections.observableArrayList(playlistSong);
        playlistView.setItems(items);
    }
}
