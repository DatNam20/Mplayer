package com.application.music.utility;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FileUtil {
    public static List<String> loadSong(String directoryPath){
        File directory = new File(directoryPath);
        ArrayList<String> list = new ArrayList<String>(Arrays.stream(directory.list())
                .filter(str -> str.endsWith(".mp3"))
                .map(str -> (directoryPath + "//" + str)).collect(Collectors.toList()));
        return list ;
    }
}
