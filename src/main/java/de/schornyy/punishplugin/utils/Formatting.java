package de.schornyy.punishplugin.utils;

import de.schornyy.punishplugin.punishment.Reason;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Formatting {

    public static String realeaseDate(Reason reason) {
        String releaseDate = "";
        //Grund = 0.0.0.7 / BannedTime = Now / UnBannedTime = BannedTime + Grund
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter= DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        //2020-03-20T03:59:04.506

        localDateTime = localDateTime.plusSeconds(reason.getSeconds());
        localDateTime =localDateTime.plusMinutes(reason.getMinits());
        localDateTime =localDateTime.plusHours(reason.getHours());
        localDateTime = localDateTime.plusDays(reason.getDays());
        localDateTime = localDateTime.plusMonths(reason.getMonth());
        localDateTime = localDateTime.plusYears(reason.getYears());

        releaseDate = localDateTime.format(dateTimeFormatter);


        return releaseDate;
    }

    public static String getFormattetDateTime() {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        return dateTime.format(formatter);
    }

}
