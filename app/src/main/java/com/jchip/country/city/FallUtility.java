package com.jchip.country.city;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Handler;
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
    public static void runOnUiLater(Runnable worker) {
        Handler handler = new Handler();
        handler.post(() -> {
            try {
                worker.run();
            } catch (Exception ex) {
                Log.d("", "runOnUiLater:", ex);
                ex.printStackTrace();
            }
        });
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

    public static Bitmap rotateBitmap(Context context, int sourceId, float angle) {
        return rotateBitmap(BitmapFactory.decodeResource(context.getResources(), sourceId), angle);
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
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

    public static void exitApp(Activity activity) {
        try {
            //activity.moveTaskToBack(true);
            activity.finishAndRemoveTask();
        } catch (Exception ignore) {
        }
    }
}