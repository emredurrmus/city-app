package com.library.meetapp.util;

import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

public class DateUtil {

    public static final DateTimeFormatter USERFRIENDLY_DATETIME_FORMATTER = DateTimeFormatter.ofPattern("dd MMM, HH:mm");


    public static String format(TemporalAccessor temporal, DateTimeFormatter formatter) {
        if (temporal == null) {
            return "";
        } else {
            return formatter.format(temporal);
        }
    }




}
