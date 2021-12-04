package com.jchip.country.code;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public abstract class MainUtility {
    public static PopupWindow popupWindow(View popupView) {
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
            popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
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
}