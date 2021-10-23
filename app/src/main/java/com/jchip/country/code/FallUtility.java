package com.jchip.country.code;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public abstract class FallUtility extends MainUtility {
    public static void runOnUiWorker(FallActivity activity, Runnable worker) {
        activity.runOnUiThread(() -> activity.showProcessing(true));
        new Thread(() -> {
            activity.runOnUiThread(() -> {
                worker.run();
                activity.showProcessing(false);
            });
        }).start();
    }

    public static String getImageRatio(FallActivity activity, int sourceId) {
        Bitmap image = BitmapFactory.decodeResource(activity.getResources(), sourceId);
        int width = image.getWidth();
        int height = image.getHeight();
        int gcd = getGCD(width, height);
        return "" + width / gcd + " : " + height / gcd;
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