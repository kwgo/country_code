package com.jchip.wear.country.city;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class CityHelper {
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
    public static final int SORT_SORT_CITY = 1;
    public static final int SORT_ADMIN_CITY = 2;
    public static final int SORT_LARGER_CITY = 3;

    public static List<Integer> sortCities(final List<String[]> cities, final int sortIndex) {
        List<Integer> sortedCities = new ArrayList<>();
        if (sortIndex == SORT_ADMIN_CITY) {
            for (int index = 0; index < cities.size(); index++) {
                String[] city = cities.get(index);
                if (CityViewHelper.isPrimary(city) || CityViewHelper.isAdmin(city)) {
                    sortedCities.add(index);
                }
            }
        } else if (sortIndex == SORT_LARGER_CITY) {
            for (int index = 0; index < cities.size(); index++) {
                String[] city = cities.get(index);
                if (!CityViewHelper.isMinor(city)) {
                    sortedCities.add(index);
                }
            }
        } else {
            for (int index = 0; index < cities.size(); index++) {
                sortedCities.add(index);
            }
        }
        Collections.sort(sortedCities, new Comparator<Integer>() {
            @Override
            public int compare(Integer index1, Integer index2) {
                String[] city1 = cities.get(index1);
                String[] city2 = cities.get(index2);
                if (CityViewHelper.isPrimary(city1) && !CityViewHelper.isPrimary(city2)) {
                    return -1;
                } else if (!CityViewHelper.isPrimary(city1) && CityViewHelper.isPrimary(city2)) {
                    return +1;
                } else {
                    int adminIndex = 0;
                    if (sortIndex == SORT_SORT_CITY) {
                        adminIndex = city1[ADMIN_NAME].compareTo(city2[ADMIN_NAME]);
                    }
                    if (adminIndex != 0) {
                        return adminIndex;
                    } else {
                        if (CityViewHelper.isAdmin(city1) && !CityViewHelper.isAdmin(city2)) {
                            return -1;
                        } else if (!CityViewHelper.isAdmin(city1) && CityViewHelper.isAdmin(city2)) {
                            return +1;
                        } else {
                            return city1[CITY_ASCII].compareTo(city2[CITY_ASCII]);
                        }
                    }
                }
            }
        });
        return sortedCities;
    }

    public static List<Integer> searchCities(List<String[]> cities, List<Integer> indexInfo, String searchText) {
        List<Integer> searchCities = new ArrayList<>();
        if (!searchText.trim().isEmpty()) {
            searchText = searchText.trim().toUpperCase();
            for (int index : indexInfo) {
                String[] city = cities.get(index);
                for (int itemIndex = 0; itemIndex < city.length; itemIndex++) {
                    if(searchCities.contains(index)) {
                        break;
                    }
                    if (itemIndex == CITY || itemIndex == CITY_ASCII || itemIndex == ADMIN_NAME || itemIndex == LAT || itemIndex == LNG) {
                        if (city[itemIndex].toUpperCase().contains(searchText)) {
                            searchCities.add(index);
                        }
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