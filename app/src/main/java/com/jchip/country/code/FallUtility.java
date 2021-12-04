package com.jchip.country.code;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.util.Log;

import java.util.Locale;

public abstract class FallUtility extends MainUtility {
    public static void runOnUiWorker(FallActivity activity, Runnable worker) {
        activity.runOnUiThread(() -> activity.showProcessing(true));
        new Thread(() -> {
            activity.runOnUiThread(() -> {
                try {
                    worker.run();
                } catch (Exception ex) {
                    Log.d("", ex.toString());
                }
                activity.showProcessing(false);
            });
        }).start();
    }

    @SuppressWarnings("deprecation")
    public static void setApplicationLanguage(Context context, String languageCode) {
        Resources resources = context.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(new Locale(languageCode.toLowerCase()));
        resources.updateConfiguration(configuration, displayMetrics);
    }


    public static String getImageRatio(Context context, int sourceId) {
        Bitmap image = BitmapFactory.decodeResource(context.getResources(), sourceId);
        int width = image.getWidth();
        int height = image.getHeight();
        int gcd = getGCD(width, height);
        return "" + width / gcd + " : " + height / gcd;
    }

    public static int getSourceId(Context context, String item, String type, String prefix) {
        String name = prefix + "_" + item.toLowerCase();
        return context.getResources().getIdentifier(name, type, context.getPackageName());
    }

    public static String getSourceText(Context context, String item, String type, String prefix) {
        int sourceId = getSourceId(context, item, type, prefix);
        return context.getResources().getString(sourceId);
    }

    private static int getGCD(int number1, int number2) {
        int gcd = 1;
        for (int number = 2; number <= number1 || number <= number2; number++) {
            if (number2 % number == 0 && number2 % number == 0) {
                gcd = number;
            }
        }
        return gcd;
    }
}