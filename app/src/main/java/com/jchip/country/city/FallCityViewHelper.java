package com.jchip.country.city;

public abstract class FallCityViewHelper extends FallCityHelper {

    public static final int TAG = -1;

    public static final int DEFAULT_SPAN_SIZE = 1;

    public static final int PORTRAIT_INPUT_COUNT = 11;
    public static final int LANDSCAPE_INPUT_COUNT = 30;

    public static final int PORTRAIT_SPAN_COUNT = 5;
    public static final int LANDSCAPE_SPAN_COUNT = 8;

    public static final int PORTRAIT_FULL_SPAN_INDEX = 2;
    public static final int PORTRAIT_BOTTOM_LINE_INDEX = 3;

    public static final int LANDSCAPE_FULL_SPAN_INDEX = 3;
    public static final int LANDSCAPE_BOTTOM_LINE_INDEX = 4;

    public static final int[] portraitIndexes = new int[]{TAG, CITY, POPULATION, TAG, ADMIN_NAME, LAT};
    public static final int[] landscapeIndexes = new int[]{TAG, CITY, ADMIN_NAME, LAT, POPULATION};

    public static final int[] portraitOffsets = new int[]{0, 0, -38, 0, 0, -38};
    public static final int[] landscapeOffsets = new int[]{0, 5, -45, -85, -110};

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

    public static int getFullSpanIndex(boolean isPortrait) {
        return isPortrait ? PORTRAIT_FULL_SPAN_INDEX : LANDSCAPE_FULL_SPAN_INDEX;
    }

    public static int getBottomLineIndex(boolean isPortrait) {
        return isPortrait ? PORTRAIT_BOTTOM_LINE_INDEX : LANDSCAPE_BOTTOM_LINE_INDEX;
    }

    public static int getItemIndex(boolean isPortrait, int index) {
        return isPortrait ? portraitIndexes[index] : landscapeIndexes[index];
    }

    public static int getItemCount(boolean isPortrait) {
        return isPortrait ? portraitIndexes.length : landscapeIndexes.length;
    }

    public static int getItemOffset(boolean isPortrait, int position) {
        int index = position % getItemCount(isPortrait);
        return isPortrait ? portraitOffsets[index] : landscapeOffsets[index];
    }

    public static int getSpanCount(boolean isPortrait) {
        return isPortrait ? PORTRAIT_SPAN_COUNT : LANDSCAPE_SPAN_COUNT;
    }

    public static int getSpanSize(boolean isPortrait, int position) {
        if (position % getItemCount(isPortrait) == getFullSpanIndex(isPortrait)) {
            return isPortrait ? PORTRAIT_SPAN_COUNT - getFullSpanIndex(isPortrait) : LANDSCAPE_SPAN_COUNT - getFullSpanIndex(isPortrait);
        }
        return DEFAULT_SPAN_SIZE;
    }
}