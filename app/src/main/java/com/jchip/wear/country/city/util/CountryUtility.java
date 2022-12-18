package com.jchip.wear.country.city.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public abstract class CountryUtility {
    public static void runOnUiWorker(Activity activity, int processingId, Runnable worker) {
        activity.runOnUiThread(() -> showProcessing(activity, processingId, true));
        new Thread(() -> {
            activity.runOnUiThread(() -> {
                try {
                    worker.run();
                } catch (Exception ex) {
                    Log.d("", ex.toString());
                }
                showProcessing(activity, processingId, false);
            });
        }).start();
    }

    public static void showProcessing(Activity activity, int processingId, boolean isShow) {
        final ProgressBar progressBar = activity.findViewById(processingId);
        progressBar.setVisibility(isShow ? View.VISIBLE : View.INVISIBLE);
    }

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

    public static void setApplicationLanguage(Context context, String languageCode) {
        Resources resources = context.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(new Locale(languageCode.toLowerCase()));
        }
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
        String name = (prefix != null ? prefix + "_" : "") + item.toLowerCase();
        Log.d("XX", "look name=" + name);

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
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                activity.finishAndRemoveTask();
            }
        } catch (Exception ignore) {
        }
    }

    public static PopupWindow popupWindow(View view, View popupView) {
        PopupWindow popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        try {
            popupView.setOnTouchListener((v, e) -> {
                        try {
                            if (popupWindow != null) {
                                popupWindow.dismiss();
                            }
                        } catch (Exception ex) {
                        }
                        return true;
                    }
            );
            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        } catch (Exception ex) {
        }
        return popupWindow;
    }

    public static void closeWindow(PopupWindow popupWindow) {
        try {
            if (popupWindow != null) {
                popupWindow.dismiss();
                popupWindow = null;
            }
        } catch (Exception ex) {
        }
    }

    public static boolean runOnUiAndWait(Activity activity, Callable<Void> callable) {
        try {
            FutureTask<Void> task = new FutureTask<>(callable);
            activity.runOnUiThread(task);
            task.get();
        } catch (ExecutionException ex) {
        } catch (InterruptedException ex) {
            return false;
        }
        return true;
    }

    public static Timer scheduleTimer(int period, Runnable task) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                while (true) {
                    try {
                        task.run();
                        Thread.sleep(period);
                    } catch (Exception e) {
                        break;
                    }
                }
            }
        }, 0);
        return timer;
    }

    public static void cancelTimer(Timer timer) {
        if (timer != null) {
            timer.cancel();
        }
    }

    public static int parseInteger(String value, int defaultValue) {
        value = value.trim().replace(",", "");
        try {
            return Integer.parseInt(value);
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    public static boolean isPortrait(Context context) {
        int orientation = context.getResources().getConfiguration().orientation;
        return orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    public static boolean isDirectionRTL(Context context) {
        Configuration configuration = context.getResources().getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return configuration.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL;
        }
        return false;
    }

    public static int dp2px(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
}