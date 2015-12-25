package com.hinohunomi.todaysonething;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyDateUtil {
    public static String getTodayString(String formatString) {
        Calendar cal = Calendar.getInstance();
        return formatDate(cal.getTime(), formatString);
    }

    public static String formatDate(Date date, String formatString) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatString);
        return sdf.format(date);
    }
}
