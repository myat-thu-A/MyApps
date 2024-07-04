package it.thomas.myapps.MainAdapter.DateCounter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateCount {
    private final int year;
    private final int month;
    private final int day;

    public DateCount(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public static DateCount calculate(int sDay, int sMonth, int sYear, int eDay, int eMonth, int eYear) {
        String sDateString = sDay + " " + sMonth + " " + sYear;
        String eDateString = eDay + " " + eMonth + " " + eYear;
        SimpleDateFormat format = new SimpleDateFormat("dd MM yyyy");
        try{
            Date sDate = format.parse(sDateString);
            Date eDate = format.parse(eDateString);
            long d1 = sDate.getTime();
            long d2 = eDate.getTime();
            if (d1 > d2) {
                int tempDay = sDay;
                int tempMonth = sMonth;
                int tempYear = sYear;

                sDay = eDay;
                sMonth = eMonth;
                sYear = eYear;

                eDay = tempDay;
                eMonth = tempMonth;
                eYear = tempYear;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int yearDiff = eYear - sYear;
        int monthDiff = eMonth - sMonth;

        if(monthDiff < 0) {
            yearDiff = yearDiff -1;
            monthDiff = monthDiff + 12;
        }

        int dayDiff = eDay - sDay;
        if(dayDiff < 0) {
            if(monthDiff < 0) {
                monthDiff = monthDiff + 1;
                dayDiff = dayDiff + MonthsToDays(eMonth - 1, eYear);
            } else {
                yearDiff = yearDiff - 1;
                monthDiff = 11;
                dayDiff = dayDiff + MonthsToDays(eMonth - 1, eYear);
            }
        }

        return new DateCount(yearDiff, monthDiff, dayDiff);
    }

    private static int MonthsToDays(int month, int year) {
        if (month == 1 || month == 3 || month == 5 || month == 7
                || month == 8 || month == 10 || month == 12) {
            return 31;
        } else if (month == 2) {
            if(year % 4 == 0) {
                return 29;
            } else {
                return 28;
            }
        } else {
            return 30;
        }
    }
}
