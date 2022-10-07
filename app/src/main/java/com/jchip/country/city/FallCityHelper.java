package com.jchip.country.city;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public abstract class FallCityHelper {
    public static final int CITY = 0;
    public static final int CITY_ASCII = 1;
    public static final int LAT = 2;
    public static final int LNG = 3;
    public static final int COUNTRY = 4;
    public static final int ISO2 = 5;
    public static final int ISO3 = 6;
    public static final int ADMIN_NAME = 7;
    public static final int CAPITAL = 8;
    public static final int POPULATION = 9;
    public static final int ID = 10;

    public static List<String[]> getCities(Context context, String countryCode) {
        int rawId = context.getResources().getIdentifier("city_" + countryCode, "raw", context.getPackageName());
        InputStream file = context.getResources().openRawResource(rawId);
        BufferedReader reader = new BufferedReader(new InputStreamReader(file));
        List<String[]> cities = new ArrayList<>();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                String[] cityInfo = line.split("\",\"");
                for (int index = 0; index < cityInfo.length; index++) {
                    cityInfo[index] = cityInfo[index].replace("\"", "");
                }
                cities.add(cityInfo);
            }
        } catch (Exception ignore) {

        }
        return cities;
    }

}