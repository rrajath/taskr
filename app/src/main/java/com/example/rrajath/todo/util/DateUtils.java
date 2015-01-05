package com.example.rrajath.todo.util;

import java.util.Date;

/**
 * Created by rrajath on 1/3/15.
 */
public class DateUtils {

    public static String getTimeElapsed(long creationTime) {
        long now = new Date().getTime();
        long diff = now - creationTime;
        int diffSeconds = (int) diff / 1000 % 60;
        int diffMinutes = (int) diff / (60 * 1000) % 60;
        int diffHours = (int) diff / (60 * 60 * 1000);
        int diffDays = (int) (diff / (1000 * 60 * 60 * 24));

        if (diffDays >= 1) {
            return String.format("%dd ago", diffDays);
        } else if (diffHours >= 1) {
            return String.format("%dh ago", diffHours);
        } else if (diffMinutes >= 1) {
            return String.format("%dm ago", diffMinutes);
        } else if (diffSeconds >= 1) {
            return String.format("%ds ago", diffSeconds);
        } else {
            return "";
        }
    }
}
