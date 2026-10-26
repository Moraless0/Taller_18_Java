package com.mycompany.gestorproyectos.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

    public static Date parseDate(String dateStr) throws ParseException {
        return dateFormat.parse(dateStr);
    }

    public static String formatDate(Date date) {
        return dateFormat.format(date);
    }

    public static boolean isValidDateRange(Date startDate, Date endDate) {
        return !endDate.before(startDate);
    }

    public static String getDateFormatPattern() {
        return DATE_FORMAT;
    }
}
