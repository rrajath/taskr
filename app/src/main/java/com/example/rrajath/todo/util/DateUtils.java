package com.example.rrajath.todo.util;

import java.util.Date;

/**
 * Created by rrajath on 1/3/15.
 */
public class DateUtils {

    public static String getTimeElapsed(Date creationDate) {
        Date now = new Date();
        long diff = now.getTime() - creationDate.getTime();
        int diffSeconds = (int) diff / 1000 % 60;
        int diffMinutes = (int) diff / (60 * 1000) % 60;
        int diffHours = (int) diff / (60 * 60 * 1000);
        int diffDays = (int) (diff / (1000 * 60 * 60 * 24));

        if (diffDays >= 1) {
            return String.format("%d ago", diffDays);
        } else if (diffHours >= 1) {
            return String.format("%d ago", diffHours);
        } else if (diffMinutes >= 1) {
            return String.format("%d ago", diffMinutes);
        } else if (diffSeconds >= 1) {
            return String.format("%d ago", diffSeconds);
        } else {
            return "";
        }
    }
}
