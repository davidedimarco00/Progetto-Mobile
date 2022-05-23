package com.app.mypresence.model.utils;

import android.util.Pair;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class TimeManagement {
    public static Pair<Integer,Integer> getInterval(final String startShift, final String endShift) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("HH:mm");
        DateTime start1 = formatter.parseDateTime(startShift);
        DateTime end1 = formatter.parseDateTime(endShift);
        Duration duration1 = new Interval(start1, end1).toDuration();
        int hours = (int)(duration1.getStandardMinutes() / 60);
        int minutes = (int)(duration1.getStandardMinutes() % 60);
        return new Pair<Integer, Integer>(hours, minutes);
    }

}
