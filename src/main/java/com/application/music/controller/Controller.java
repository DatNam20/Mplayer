package com.application.music.controller;

import com.application.music.Main;
import com.application.music.dao.SongDao;
import com.application.music.dto.BasicSongDto;
import com.application.music.dto.PlaylistDto;
import com.application.music.observer.SongObserver;
import com.application.music.service.MusicService;
import com.application.music.utility.TimeUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static com.application.music.utility.ApplicationConstant.*;

public class Controller implements SongObserver{
    Logger logger = Logger.getLogger(Controller.class.getName());

    @FXML
    private Button btn;

    @FXML
    private Button playpause;

    @FXML
    private Pane rootPaneId;

    @FXML
    private Slider slider;

    @FXML
    private ImageView songImage;

    @FXML
    private Label playlistLabel, songNameLabel, duration, artistLabel, albumLabel;

    @FXML
    private ListView<String> playlistView, allFoldersList, allPlaylists;

    private MusicService mser;

    public Controller(){
         mser = MusicService.getMusicService(this);
    }

    @FXML
    public void initialize() throws IOException {
        refreshPlaylist(mser.getPlaylistDto());
        refreshFoldersList(mser.getSongDao());
        updateSongDetails(mser.getSongDto());
        slider.setOnMouseClicked(mouseEvent -> {
            mser.seek(slider.getValue());
        });

    }



    @FXML protected void playpause(ActionEvent event) throws IOException {
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

    /**
     *
     * @param songDto :
     *
     */
    public void updateSongDetails(BasicSongDto songDto) {
        songNameLabel.setText(songDto.getSongName());
        duration.setText(TimeUtil.elapsedTime(songDto.getSongDuration().intValue()));
        slider.setMax(songDto.getSongDuration());
        artistLabel.setText(songDto.getArtist());
        albumLabel.setText(songDto.getAlbum());
        songImage.setImage((Image) songDto.getImage());
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

/*    @FXML protected void stop(ActionEvent event) {
        btn = (Button) event.getSource();
        mser.stopCurrentSong();
        playpause.setText(UI_PLAY);
    }
*/
    @FXML protected void next(ActionEvent event) throws IOException {
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

    @FXML protected void skipAhead(ActionEvent event) throws IOException {
        btn = (Button) event.getSource();
        mser.seek(mser.getElapsedTime()+SKIP_CONST);
        updateSongDetails(mser.getSongDto());
    }

    @FXML protected void skipBack(ActionEvent event) throws IOException {
        btn = (Button) event.getSource();
        mser.seek(mser.getElapsedTime()-SKIP_CONST);
        updateSongDetails(mser.getSongDto());
    }

    @FXML protected void previous(ActionEvent event) throws IOException {
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
            mser.addDirectory(file);
            refreshFoldersList(mser.getSongDao()); ;
//            mser.openDirectory(file);
            refreshPlaylist(mser.getPlaylistDto());
        }
        else {
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
        ObservableList<String> items = FXCollections.observableArrayList(
                playlistDto.getPlaylistSong().stream()
                        .map(str -> str.substring((str.lastIndexOf(FILE_NAME_SEPARATOR)+2),(str.length()-4)))
                        .collect(Collectors.toList()));
        playlistView.setItems(items);
    }

    @FXML protected void addPlaylist(){
        TextInputDialog nameDialog = new TextInputDialog("NewPlaylist");

        nameDialog.setTitle("Create New Playlist");
        nameDialog.setHeaderText("Enter Name ");

        nameDialog.showAndWait() ;
        String playlistName = nameDialog.getEditor().getText() ;
        mser.createPlaylist(playlistName);
        refreshPlaylistList(mser.getSongDao());
    }

    @FXML protected void renamePlaylist(){
//      method to get current name
//        String oldName = allPlaylists.getSelectionModel().getSelectedItem();
        logger.info("Rename Playlist called") ;
        TextInputDialog dialog = new TextInputDialog(playlistLabel.getText());
        dialog.setTitle("Rename Playlist");
        dialog.setHeaderText("Enter New Name ");
        dialog.showAndWait() ;
        String newName = dialog.getEditor().getText() ;
        mser.renamePlaylist(newName);
        refreshPlaylist(mser.getPlaylistDto());
        refreshPlaylistList(mser.getSongDao());
    }

    @FXML protected void removePlaylist(){
//      dialog box to confirm deletion

        mser.deletePlaylist(allPlaylists.getSelectionModel().getSelectedItem());
        refreshPlaylistList(mser.getSongDao());
        refreshPlaylist(mser.getPlaylistDto());
    }

    @FXML protected void addToPlaylist() throws IOException {

//        get list of songs

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getClassLoader().getResource("SongList.fxml"));
        Parent songList = fxmlLoader.load();
        Scene scene = new Scene(songList, 500, 550);
        Stage newWindow = new Stage();
        newWindow.setTitle("Add Songs To Playlist");
        newWindow.setScene(scene);

        //this is used to create composistion between child ui component and from here we will set the static fields
        SongListController c = fxmlLoader.getController();
        c.setDisplaySongList(mser.getAllSongs());

        newWindow.showAndWait();

        mser.addToPlaylist(mser.getPlaylistDto().getPlaylistName(), c.getSelectedSongList());
        refreshPlaylist(mser.getPlaylistDto());

    }



    @FXML protected void removeFromPlaylist() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getClassLoader().getResource("SongList.fxml"));
        Parent songList = fxmlLoader.load();
        Scene scene = new Scene(songList, 500, 550);
        Stage newWindow = new Stage();
        newWindow.setTitle("Add Songs To Playlist");
        newWindow.setScene(scene);

        //this is used to create composistion between child ui component and from here we will set the static fields
        SongListController c = fxmlLoader.getController();
        c.setDisplaySongList(mser.getPlaylistDto().getPlaylistSong());

        newWindow.showAndWait();

        mser.removeFromPlaylist(mser.getPlaylistDto().getPlaylistName(), c.getSelectedSongList());
        refreshPlaylist(mser.getPlaylistDto());
    }

    @FXML protected void setPlaylist(){
        allPlaylists.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent click) {

                if (click.getClickCount() == 2) {
                    logger.info("Load Playlist : " + allPlaylists.getSelectionModel().getSelectedItem());
                    mser.setCurrentPlaylist(allPlaylists.getSelectionModel().getSelectedItem());
                    logger.info("Playlist name : " + mser.getPlaylistDto().getPlaylistName());
                    logger.info("Songs list : " + mser.getPlaylistDto().getPlaylistSong());
                    refreshPlaylist(mser.getPlaylistDto());
                }
            }
        });
    }

    @FXML protected void addFolder(){

    }

    @FXML protected void removeFolder(){
//      dialog box to confirm deletion

        mser.removeDirectory(allFoldersList.getSelectionModel().getSelectedItem());
        refreshFoldersList(mser.getSongDao());
        refreshPlaylist(mser.getPlaylistDto());
    }

    private void refreshFoldersList(SongDao songDao) {
        allFoldersList.setItems(FXCollections.observableArrayList(songDao.getAllSourceFolder()));
    }

    private void refreshPlaylistList(SongDao songDao) {
        allPlaylists.setItems(FXCollections.observableArrayList(songDao.getAllPlaylist()));
    }

    @FXML protected void loadPlaylist(){

    }

    @FXML protected void refreshSong(){

    }

    @FXML protected void loadGlobalPlaylist(ActionEvent event){

        mser.loadGlobalPlaylist();
        refreshPlaylist(mser.getPlaylistDto());
    }

    @Override
    public void updateSongStatus(String status, Object value) {

        if(status.equals(SONG_READY)){
            updateSongDetails(mser.getSongDto());
        }
    }
}
