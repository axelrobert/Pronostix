/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.pronostix.nba.utils;

import java.util.List;

/**
 *
 * @author François_2
 */
public class DateUtils {
    
    private static boolean isBissextile(int year) {
        if(((year % 400 == 0) && (year % 100 != 0)) || (year % 4 == 0)) {
            return true;
        } else {
            return false;
        }
    }
    
    public static String dateCalculator(int day, int month, int year, int numberOfDayInMonth) {
        if (day == numberOfDayInMonth) {
            if (month == 12) {
                year = year + 1;
            }
            month = ((month) % 12 + 1);
        }
        day = (day % numberOfDayInMonth) + 1;
        String dayAsString = day + "";
        String monthAsString = month + "";
        if (day < 10) {
            dayAsString = "0" + day;
        }
        if (month < 10) {
            monthAsString = "0" + month;
        }
        return (year + monthAsString + dayAsString);
    }
    
    public static String getNextDate(String date){
        int day = Integer.parseInt(date.substring(6, 8));
        int month = Integer.parseInt(date.substring(4, 6));
        int year = Integer.parseInt(date.substring(0, 4));
        if (month == 1 || month == 3 || month == 5 || month == 10 || month == 12) {
            return (dateCalculator(day, month, year, 31));
        } else if (month == 4 || month == 6 || month == 11) {
            return (dateCalculator(day, month, year, 30));
        } else if (month == 2) {
            if (!isBissextile(year)) {
                return (dateCalculator(day, month, year, 28));
            } else {
                return (dateCalculator(day, month, year, 29));
            }
        }
        return null;
    }
    
    public static String convertETHourToUTC1(String americanHour) {
        String fullHour = americanHour.split(" ")[0];
        int h = (Integer.parseInt(fullHour.split(":")[0]) + 18)%24;
        String m = fullHour.split(":")[1];
        if (h < 12) {
            return "0" +h + ":" + m;
        } else {
            return h + ":" + m;
        }
    }
    
    public static boolean checkIfGameBelongsToDate(String date, String hour) {
        String frenchHour = convertETHourToUTC1(hour);
        return (!frenchHour.startsWith("0"));
    }

    public static boolean checkDateOk(String[] invalidDates, String date) {
        boolean notInside = true;
        for (String s : invalidDates) {
            if (s.equals(date)) {
                notInside = false;
                break;
            }
        }
        return notInside;
    }
    
}
