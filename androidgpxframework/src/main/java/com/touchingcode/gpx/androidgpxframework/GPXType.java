package com.touchingcode.gpx.androidgpxframework;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * This class converts values from String to the original data type and from the original data type to String
 *
 * Android version of the iOS-GPX-Framework from Watanabe Toshinori, Pierre-Loup
 * @author Yoshiki Kuno
 */
public class GPXType {

    public static Double latitude(String value) {
        Double latitude = Double.valueOf(value);
        if(+90 >= latitude && -90 <= latitude ){
            return latitude;
        }
        System.out.println("Not valid latitude");
        return 0.0;
    }

    public static String valueForLatitude(Double latitude) {
        return String.valueOf(latitude);
    }

    public static Double longitude(String value) {
        Double longitude = Double.valueOf(value);
        if(+180 >= longitude && -180 <= longitude ){
            return longitude;
        }
        System.out.println("Not valid longitude");
        return 0.0;
    }

    public static String valueForLongitude(Double latitude) {
        return String.valueOf(latitude);
    }

    public static Double degress(String value) {
        Double degress = Double.valueOf(value);
        if(0.0 <= degress && 360.0 >= degress ){
            return degress;
        } else {
            System.out.println("Not valid degress");
            return 0.0;
        }
    }

    public static String valueForDegress(Double degress) {
        return String.valueOf(degress);
    }

    public static int fix(String value){
        int fix;
        switch(value) {
            case "2d":
                fix = 1; break;
            case "3d":
                fix = 2; break;
            case "dgps":
                fix = 3; break;
            case "pps":
                fix = 4; break;
            default:
                fix = 0; break;
        }
        return fix;
    }

    public static String valueForFix(int fix){
        String value = "";
        switch (fix) {
            case 0:
                value = "none"; break;
            case 1:
                value = "2d"; break;
            case 2:
                value = "3d"; break;
            case 3:
                value = "dgps"; break;
            case 4:
                value = "pps"; break;
        }
        return value;
    }

    public static int dgpsStation(String value) {
        int i = Integer.valueOf(value);
        if(0 <= i && i <= 1023){
            return i;
        } else {
            System.out.println("Not valid dgpsStation");
            return 0;
        }
    }

    public static String valueForDgpsStation(int dgpsStation) {
        if (0 > dgpsStation || dgpsStation > 1023)
            return "0";

        return String.valueOf(dgpsStation);
    }

    public static Double decimal(String value) {
        return Double.valueOf(value);
    }

    public static String valueForDecimal(Double decimal) {
        return String.valueOf(decimal);
    }

    /**
     * This method creates java Date object from String
     * Basic Date format is YYYY-MM-DDThh:mm:ssZ
     *
     * @param value
     * @return date
     */
    public static Date dateTime(String value) {

        Date date = null;
        SimpleDateFormat formatter;

        // dateTime（YYYY-MM-DDThh:mm:ssZ）
        if (value.matches("([0-9]{4})-([0-9]{2})-([0-9]{2})T([0-9]{2}):([0-9]{2}):([0-9]{2})Z")){
            formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'", Locale.getDefault());
            try {
                date = formatter.parse(value);
            } catch(Exception e){
                e.printStackTrace();
            }
        }

        // dateTime（YYYY-MM-DDThh:mm:ss.SSSZ）
        if (value.matches("([0-9]{4})-([0-9]{2})-([0-9]{2})T([0-9]{2}):([0-9]{2}):([0-9]{2}):([0-9]{3})Z")){
            formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss:SSS'Z'", Locale.getDefault());
            try {
                date = formatter.parse(value);
            } catch(Exception e){
                e.printStackTrace();
            }
        }

        // dateTime（YYYY-MM-DDThh:mm:sszzzzzz）
        if (value.length() >= 22) {
            System.out.println("time zone checker called.....");
            formatter = new SimpleDateFormat("yyyy'-'MM'-'dd'T'hh':'mm':'ssZZZZZ", Locale.getDefault());
            String v = value.substring(0, 22);
            try {
                date = formatter.parse(value);
            } catch(Exception e){
                e.printStackTrace();
            }
        }

        // dateTime（YYYY-MM-DD）
        if (value.matches("([0-9]{4})-([0-9]{2})-([0-9]{2})")){
            formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            try {
                date = formatter.parse(value);
            } catch(Exception e){
                e.printStackTrace();
            }
        }

        // dateTime（YYYY-MM）
        if (value.matches("([0-9]{4})-([0-9]{2})")){
            formatter = new SimpleDateFormat("yyyy-MM", Locale.getDefault());
            try {
                date = formatter.parse(value);
            } catch(Exception e){
                e.printStackTrace();
            }
        }

        // dateTime（YYYY）
        if (value.matches("([0-9]{4})")){
            formatter = new SimpleDateFormat("yyyy", Locale.getDefault());
            try {
                date = formatter.parse(value);
            } catch(Exception e){
                e.printStackTrace();
            }
        }

        return date;
    }

    // format "yyyy'-'MM'-'dd'T'HH':'mm':'ss'Z'"
    public static String valueForDateTime(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZZZZZ", Locale.getDefault());

        return formatter.format(date);
    }

    public static int nonNegativeInteger(String value) {
        return Integer.valueOf(value);
    }

    public static String valueForNonNegativeInteger(int integer) {
        return String.valueOf(integer);
    }
}
