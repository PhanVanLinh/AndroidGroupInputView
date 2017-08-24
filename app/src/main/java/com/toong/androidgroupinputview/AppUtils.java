package com.toong.androidgroupinputview;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by PhanVanLinh on 24/08/2017.
 * phanvanlinh.94vn@gmail.com
 */

public class AppUtils {

    public static boolean isValidEmail(CharSequence target) {
        return target != null && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public static class DateTime {
        public static final String TIME_SERVER_PATTERN = "yyyy-MM-dd HH:mm:ss";
        public static final String DATE_SERVER_PATTERN = "yyyy-MM-dd";
        public static final String DATE_TIME_SERVER_PATTERN = "yyyy-MM-dd HH:mm:ss";
        public static final String DATE_UI_PATTERN = "yyyy-MM-dd";

        public static Calendar dateToCalendar(String time) throws ParseException {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_SERVER_PATTERN, Locale.getDefault());
            cal.setTime(sdf.parse(time));
            return cal;
        }

        public static Calendar dateTimeToCalendar(String time) throws ParseException {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_SERVER_PATTERN, Locale.getDefault());
            cal.setTime(sdf.parse(time));
            return cal;
        }

        public static long getCurrentTimeMillis() {
            return System.currentTimeMillis();
        }

        public static String convertDateToUIFormat(String inputDateStr) {
            DateFormat inputFormat = new SimpleDateFormat(TIME_SERVER_PATTERN);
            DateFormat outputFormat = new SimpleDateFormat(DATE_UI_PATTERN);
            Date date = null;
            try {
                date = inputFormat.parse(inputDateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return outputFormat.format(date);
        }
    }
}
