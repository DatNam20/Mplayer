package com.application.music.service;

import com.application.music.model.impl.GlobalPlaylist;
import com.mpatric.mp3agic.Mp3File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

public class MusicService {
    //wrong way to create object - read about constructor
    private GlobalPlaylist plist = new GlobalPlaylist();
//    private Media media = null;
//    private MediaPlayer mp = null;

    public String openDirectory(File file){
        plist.setLocation(file.getAbsolutePath());
        return file.getAbsolutePath()==null?"Cant Open":file.getAbsolutePath();
    }


    public void callPlaylist(String function) {

        plist.functionCalled(function);

/*        String testSong = "E:\\Music\\test.mp3";
        try{
            Mp3File mp3file = new Mp3File(testSong);
            System.out.println("Length of this mp3 is: " + mp3file.getLengthInSeconds() + " seconds");
            System.out.println("Bitrate: " + mp3file.getBitrate() + " kbps " + (mp3file.isVbr() ? "(VBR)" : "(CBR)"));
            System.out.println("Sample rate: " + mp3file.getSampleRate() + " Hz");
            System.out.println("Has ID3v1 tag?: " + (mp3file.hasId3v1Tag() ? "YES" : "NO"));
            System.out.println("Has ID3v2 tag?: " + (mp3file.hasId3v2Tag() ? "YES" : "NO"));
            System.out.println("Has custom tag?: " + (mp3file.hasCustomTag() ? "YES" : "NO"));
        } catch (Exception e){
            e.printStackTrace();
        }
*/

/*        System.out.println(value);
        if(null == media){
            media = new Media(new File(testSong).toURI().toString());
            System.out.println(media.getMetadata());
            mp = new MediaPlayer(media);
            System.out.println(mp.getStatus());
            mp.play();
        }
        if(mp!=null){
            if("Play".equals(value))
            {
                mp.play();
                System.out.println("Play clicked");
            }
            else if ("Pause".equals(value))
            {
                mp.pause();
                System.out.println("Pause clicked");
            }
            else if("Stop".equals(value))
            {
                mp.stop();
                System.out.println("Stop clicked");
            }
        }
        else
        {
            throw new RuntimeException("Unable to load Media and Mediaplayer classes");
        }
 */

    }
}
