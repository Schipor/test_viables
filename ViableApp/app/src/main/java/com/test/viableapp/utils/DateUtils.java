package com.test.viableapp.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class DateUtils {

    private static final SimpleDateFormat dateFormatFromServer = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    public static String getUserAge(String dateOfBirth) {
        try {
            Calendar dob = Calendar.getInstance();
            Calendar today = Calendar.getInstance();

            dob.setTime(dateFormatFromServer.parse(dateOfBirth));

            int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

            if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
                age--;
            }
            return String.format("%s years", age < 1 ? "N/A" : age);
        } catch (Exception e){
            //make sure nothing goes wrong
            return "N/A";
        }
    }
}
