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

    public static PopupWindow popupWindow(View parent, View popupView) {
        final PopupWindow popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupView.setOnTouchListener((v, e) -> {
                    popupWindow.dismiss();
                    return true;
                }
        );
        popupWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);
        return popupWindow;
    }

    public static void closeWindow(PopupWindow popupWindow) {
        if (popupWindow != null) {
            popupWindow.dismiss();
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
}