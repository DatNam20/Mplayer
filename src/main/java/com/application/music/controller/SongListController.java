package com.application.music.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.application.music.utility.ApplicationConstant.FILE_NAME_SEPARATOR;

public class SongListController {

    @FXML
    private ListView<String> songListView;

    @FXML
    private AnchorPane ap;

    private List<String> selectedSongList;

    public SongListController(){ }

    @FXML
    public void initialize() throws IOException {
        songListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public List<String> getSelectedSongList() {
        return selectedSongList;
    }

    public void setDisplaySongList(List<String> displaySongList) {
        songListView.getItems().addAll(displaySongList);
    }

    @FXML
    public void selectSongs(){
        ObservableList<String> selectedSongs = songListView.getSelectionModel().getSelectedItems();
        selectedSongList = new ArrayList<>(selectedSongs);
        Stage stage = (Stage) ap.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void cancel(){
        Stage stage = (Stage) ap.getScene().getWindow();
        stage.close();
    }
}
