package com.test.viableapp.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class DateUtils {

    private static final SimpleDateFormat dateFormatFromServer = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    public static String getUserAge(String dateOfBirth) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(dateFormatFromServer.parse(dateOfBirth));
            Calendar currentDay = Calendar.getInstance();
            int age = currentDay.get(Calendar.YEAR) - calendar.get(Calendar.YEAR);
            return String.format("%s years", age < 1 ? "N/A" : age);
        } catch (Exception e) {
            //make sure nothing goes wrong
            return "N/A";
        }
    }
}
