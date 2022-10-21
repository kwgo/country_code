package com.jchip.country.city;

import java.text.DecimalFormat;

public abstract class FallCityViewHelper extends FallCityHelper {

    public static final int PORTRAIT_INPUT_COUNT = 16;
    public static final int LANDSCAPE_INPUT_COUNT = 32;

    public static final int[] detailIndexes = new int[]{
            CITY_ASCII, CITY, CAPITAL, ADMIN_NAME, LAT, LNG, POPULATION
    };

    public static final int[] headerIndexes = new int[]{
            R.string.head_city_ascii,
            R.string.head_city_name,
            R.string.head_city_capital,
            R.string.head_city_admin,
            R.string.head_city_lat,
            R.string.head_city_lng,
            R.string.head_city_population
    };

    public static int getHeaderIndex(int index) {
        return headerIndexes[index];
    }

    public static int getInputCount(boolean isPortrait) {
        return isPortrait ? PORTRAIT_INPUT_COUNT : LANDSCAPE_INPUT_COUNT;
    }

    public static String getNumberItem(String[] city, int index) {
        int number = FallUtility.parseInteger(city[index], 0);
        if (number < 10) {
            return number <= 0 ? "" : String.valueOf(number);
        } else {
            number = number < 1000 ? number / 10 * 10 : number / 100 * 100;
            return new DecimalFormat("###,###,###").format(number);
        }
    }

    public static boolean isEmpty(String[] city, int index) {
        return !(city[index] != null && city[index].trim().length() > 0);
    }

    public static boolean isPrimary(String[] city) {
        return "primary".equals(city[FallCityHelper.CAPITAL]);
    }

    public static boolean isAdmin(String[] city) {
        return "admin".equals(city[FallCityHelper.CAPITAL]);
    }

    public static boolean isMinor(String[] city) {
        return "minor".equals(city[FallCityHelper.CAPITAL]);
    }
}