package com.jchip.country.code;

public abstract class FallHelper extends MainHelper {

    public static final int IMAGE = -1;

    public static final int DEFAULT_SPAN_SIZE = 1;

    public static final int PORTRAIT_INPUT_COUNT = 11;
    public static final int LANDSCAPE_INPUT_COUNT = 30;

    public static final int PORTRAIT_SPAN_COUNT = 5;
    public static final int LANDSCAPE_SPAN_COUNT = 8;

    public static final int PORTRAIT_FULL_SPAN_INDEX = 2;
    public static final int PORTRAIT_BOTTOM_LINE_INDEX = 3;

    public static final int LANDSCAPE_FULL_SPAN_INDEX = 3;
    public static final int LANDSCAPE_BOTTOM_LINE_INDEX = 4;

    public static final int[] portraitIndexes = new int[]{IMAGE, ALPHA_2, COUNTRY, IMAGE, CURRENCY, CALL_CODE, TIMEZONE, CAPITAL};
    public static final int[] landscapeIndexes = new int[]{IMAGE, ALPHA_2, ALPHA_3, OFFICIAL, IMAGE, NUMERIC, CURRENCY, SYMBOL, CALL_CODE, POPULATION, TIMEZONE, CAPITAL};

    public static final int[] portraitOffsets = new int[]{0, 0, -38, 0, 0, -38, -70, -88};
    //   public static final int[] portraitOffsets = new int[]{0, 0, -40, 0, 0, -40, -75, -95};
    public static final int[] landscapeOffsets = new int[]{0, 5, -45, -85, 0, 5, -45, -85, -110, -135, -120, -125};

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