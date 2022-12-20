package com.jchip.wear.country.city;

import android.content.Context;

import com.jchip.wear.country.city.util.CountryUtility;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
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

    public static List<String[]> sortCities(final List<String[]> cities, final int sortIndex) {
        List<String[]> sortedCities = new ArrayList<>();
        if (sortIndex == SORT_ADMIN_CITY) {
            for (int index = 0; index < cities.size(); index++) {
                String[] city = cities.get(index);
                if (CityHelper.isPrimary(city) || CityHelper.isAdmin(city)) {
                    sortedCities.add(city);
                }
            }
        } else if (sortIndex == SORT_LARGER_CITY) {
            for (int index = 0; index < cities.size(); index++) {
                String[] city = cities.get(index);
                if (!CityHelper.isMinor(city)) {
                    sortedCities.add(city);
                }
            }
        } else {
            sortedCities.addAll(cities);
        }
        Collections.sort(sortedCities, new Comparator<String[]>() {
            @Override
            public int compare(String[] city1, String[] city2) {
                if (CityHelper.isPrimary(city1) && !CityHelper.isPrimary(city2)) {
                    return -1;
                } else if (!CityHelper.isPrimary(city1) && CityHelper.isPrimary(city2)) {
                    return +1;
                } else {
                    int adminIndex = 0;
                    if (sortIndex == SORT_SORT_CITY) {
                        adminIndex = city1[ADMIN_NAME].compareTo(city2[ADMIN_NAME]);
                    }
                    if (adminIndex != 0) {
                        return adminIndex;
                    } else {
                        if (CityHelper.isAdmin(city1) && !CityHelper.isAdmin(city2)) {
                            return -1;
                        } else if (!CityHelper.isAdmin(city1) && CityHelper.isAdmin(city2)) {
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

    public static List<String[]> searchCities(List<String[]> cities, String searchText) {
        List<String[]> searchCities = new ArrayList<>();
        if (!searchText.trim().isEmpty()) {
            searchText = searchText.trim().toUpperCase();
            for (String[] city : cities) {
                for (int searchIndex = 0; searchIndex < city.length; searchIndex++) {
                    if (searchIndex == CITY || searchIndex == CITY_ASCII || searchIndex == ADMIN_NAME || searchIndex == LAT || searchIndex == LNG) {
                        if (city[searchIndex].toUpperCase().contains(searchText)) {
                            searchCities.add(city);
                            break;
                        }
                    }
                }
            }
        } else {
            searchCities.addAll(cities);
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

    public static String getNumberItem(String value) {
        try {
            int number = CountryUtility.parseInteger(value, 0);
            if (number < 10) {
                return number <= 0 ? "" : String.valueOf(number);
            } else {
                number = number < 1000 ? number / 10 * 10 : number / 100 * 100;
                return new DecimalFormat("###,###,###").format(number);
            }
        } catch (Exception ignore) {
            return "";
        }
    }

    public static boolean isPrimary(String[] city) {
        return "primary".equals(city[CityHelper.CAPITAL]);
    }

    public static boolean isAdmin(String[] city) {
        return "admin".equals(city[CityHelper.CAPITAL]);
    }

    public static boolean isMinor(String[] city) {
        return "minor".equals(city[CityHelper.CAPITAL]);
    }
}