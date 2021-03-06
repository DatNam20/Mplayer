module Mplayer {
    requires javafx.fxml;
    requires javafx.controls;
    requires mp3agic;
    requires jlayer;
    requires javafx.media;
    requires java.desktop;
    requires java.logging;
    requires commons.lang3;

    opens com.application.music;
    opens com.application.music.controller;
    exports com.application.music;
    exports com.application.music.controller;
}