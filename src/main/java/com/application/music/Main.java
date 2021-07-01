package com.application.music;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Main.fxml"));
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Final.fxml"));

        primaryStage.setMinWidth(875);
        primaryStage.setMinHeight(700);

        Scene scene = new Scene(root, 950, 700);

        primaryStage.setTitle("MPlayer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
}
