package com.fdev.todoapps.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormatter {


    public String getDayName(Date date){
        return new SimpleDateFormat("EEEE",Locale.ENGLISH).format(date);
    }

    public String getOnlyDate(Date date){
        return new SimpleDateFormat("dd", Locale.US).format(date);
    }

    public String getFullDate(Date date){
        return new SimpleDateFormat("dd-MM-yyyy", Locale.US).format(date);
    }

    public String getMonthYear(Date date){
        return new SimpleDateFormat("MMM \n YYYY").format(date);
    }

    public String getMonth(Date date){
        return new SimpleDateFormat("MMM").format(date);
    }

    public String getYear(Date date){
        return new SimpleDateFormat("YYYY").format(date);
    }

    public String getHourMinute(Date date){
        return new SimpleDateFormat("HH:mm").format(date);
    }
}
