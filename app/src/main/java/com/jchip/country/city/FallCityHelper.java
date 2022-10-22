package com.jchip.country.city;

import android.content.Context;
import android.util.Log;

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

    public static final int SORT_ALL_CITY = 0;
    public static final int SORT_ADMIN_CITY = 1;
    public static final int SORT_LARGER_CITY = 2;

    public static List<Integer> sortCities(List<String[]> cities, int sortIndex) {
        List<Integer> sortedCities = new ArrayList<>();
        if (sortIndex == SORT_ADMIN_CITY) {
            Log.d("pp", "sortIndex == SORT_ADMIN_CITY");
            for (int index = 0; index < cities.size(); index++) {
                String[] city = cities.get(index);
                if (FallCityViewHelper.isPrimary(city) || FallCityViewHelper.isAdmin(city)) {
                    sortedCities.add(index);
                }
            }
        } else if (sortIndex == SORT_LARGER_CITY) {
            Log.d("pp", "sortIndex == SORT_LARGER_CITY");
            for (int index = 0; index < cities.size(); index++) {
                String[] city = cities.get(index);
                if (FallCityViewHelper.isMinor(city)) {
                    sortedCities.add(index);
                }
            }
        } else {
            Log.d("pp", "sortIndex == SORT_ALL_CITY");
            for (int index = 0; index < cities.size(); index++) {
                sortedCities.add(index);
            }
        }
        Log.d("pp", "sortedCities == size:" + sortedCities.size());

        return sortedCities;
//        return Collections.sort(sortedCities, new Comparator<String[]>() {
//            @Override
//            public int compare(String[] city1, String[] city2) {
//                return city1[0].compareTo(city2[0]);
//            }
//        });
    }

    public static List<Integer> searchCities(List<String[]> cities, List<Integer> indexInfo, String searchText) {
        List<Integer> searchCities = new ArrayList<>();
        if (!searchText.trim().isEmpty()) {
            searchText = searchText.toUpperCase();
            for (int item : indexInfo) {
                int index = indexInfo.get(item);
                String[] city = cities.get(index);
                for (String cityItem : city) {
                    if (cityItem.toUpperCase().contains(searchText)) {
                        searchCities.add(index);
                    }
                }
            }
        } else {
            searchCities = new ArrayList<>(indexInfo);
        }
        return searchCities;
    }

    public static List<String[]> getCities(Context context, String countryCode) {
        int rawId = context.getResources().getIdentifier("city_" + countryCode.toLowerCase(), "raw", context.getPackageName());
        InputStream file = context.getResources().openRawResource(rawId);
        BufferedReader reader = new BufferedReader(new InputStreamReader(file));
        List<String[]> cities = new ArrayList<>();
        String line;
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