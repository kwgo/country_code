package com.jchip.country.code;

public abstract class FallHelper extends MainHelper {

    public static final int IMAGE = -1;

    public static final int DEFAULT_SPAN_SIZE = 1;

    public static final int PORTRAIT_SPAN_COUNT = 5;
    public static final int LANDSCAPE_SPAN_COUNT = 8;

    public static final int FULL_SPAN_FIELD_INDEX = 2;
    public static final int BOTTOM_COLUMN_INDEX = 3;

    public static final int[] portraitIndexes = new int[]{IMAGE, ALPHA_2, COUNTRY, IMAGE, CURRENCY, CALL_CODE, TIMEZONE, CAPITAL};
    public static final int[] landscapeIndexes = new int[]{IMAGE, ALPHA_2, OFFICIAL, IMAGE, ALPHA_3, NUMERIC, CURRENCY, SYMBOL, CALL_CODE, TIMEZONE, CAPITAL};

    public static final int[] portraitOffsets = new int[]{0, 0, -40, 0, 0, -40, -75, -95};
    public static final int[] landscapeOffsets = new int[]{0, 0, -40, 0, 0, -40, -70, -115, -135, -160, -170};

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
        if (position % getItemCount(isPortrait) == FULL_SPAN_FIELD_INDEX) {
            return isPortrait ? PORTRAIT_SPAN_COUNT - FULL_SPAN_FIELD_INDEX : LANDSCAPE_SPAN_COUNT - FULL_SPAN_FIELD_INDEX;
        }
        return DEFAULT_SPAN_SIZE;
    }


}