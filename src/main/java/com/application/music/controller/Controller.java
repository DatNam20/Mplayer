package com.application.music.controller;

import com.application.music.service.MusicService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;


public class Controller {
    @FXML
    private Button btn;
//    @FXML
//    private Button pause;
//    @FXML
//    private Button stop;
//    @FXML
//    private Button next;
//    @FXML
//    private Button prev;

    @FXML
    private GridPane gridPaneId;

    @FXML
    private Label label;
    private MusicService mser = new MusicService();

    @FXML protected void callService(ActionEvent event) {
        btn = (Button) event.getSource();
        label.setText(btn.getText());
        mser.callPlaylist(btn.getText());

    }

    @FXML protected void openFolder(ActionEvent event) {
        btn = (Button) event.getSource();
        Stage stage = (Stage)gridPaneId.getScene().getWindow();

        DirectoryChooser directoryChooser = new DirectoryChooser();
        File file = directoryChooser.showDialog(stage);
        if(file.exists())
            mser.openDirectory(file);
        label.setText(file.getAbsolutePath());

    }
}
