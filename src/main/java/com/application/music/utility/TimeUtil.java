package com.application.music.utility;

public class TimeUtil {
    public static String elapsedTime(int duration){
        StringBuilder time = new StringBuilder();
        int sec = duration%60;
        duration = duration/60;
        int min = duration%60;
        duration = duration/60;
        int hr = duration%24;
        String temp="";
        String s = (String.valueOf(min).length() == 1) ? "0" : "";
        String s1 = (String.valueOf(sec).length() == 1) ? "0" : "";
        if(hr!=0) {
            time.append(String.valueOf(hr) + ":" +
                    s + String.valueOf(min)+":"
            + s1 + String.valueOf(sec));

        }
        else if(min!=0){
            time.append(s + String.valueOf(min)+":"
                    + s1 + String.valueOf(sec));
        }
        else{
            time.append("00:"
                    + s1 + String.valueOf(sec));
        }
        return time.toString();
    }
}
