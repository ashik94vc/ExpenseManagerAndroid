package com.cepheuen.expensemanager.utils;

import android.widget.DatePicker;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by Ashik Vetrivelu on 22/08/16.
 */
public class TimeUtils {

    public static String getDate(long timestamp)
    {
        Date date = new Date(timestamp);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        TimeZone istTimeZone = TimeZone.getTimeZone("Asia/Kolkata");
        sdf.setTimeZone(istTimeZone);
        String strtime = sdf.format(date);

        return strtime;
    }

    public static Long getTimestamp(DatePicker datePicker)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(datePicker.getYear(),datePicker.getMonth(),datePicker.getDayOfMonth());
        return calendar.getTimeInMillis();
    }
    public static Long getTimestamp(String date) throws ParseException
    {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        Date mDate = dateFormat.parse(date);
        return mDate.getTime();
    }

    public static List<String> getDatesInRange()
    {
        Date enddate = Calendar.getInstance().getTime();
        Date startdate = new Date(enddate.getTime() - 6*24*60*60*1000);
        List<String> dates = new ArrayList<>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startdate);

        while (calendar.getTime().before(enddate))
        {
            dates.add(getDate(calendar.getTimeInMillis()));
            calendar.add(Calendar.DATE, 1);
        }
        return dates;
    }

    public static Boolean getDatesInRange(Date startDate,Date endDate,Date validDate)
    {
        if(validDate.getTime() >= startDate.getTime() && validDate.getTime() <= endDate.getTime() )
            return true;
        return false;
    }
    public static String getTime(long timestamp)
    {
        Date date = new Date(timestamp);
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        TimeZone istTimeZone = TimeZone.getTimeZone("Asia/Kolkata");
        sdf.setTimeZone(istTimeZone);
        String strtime = sdf.format(date);

        return strtime;
    }
}
