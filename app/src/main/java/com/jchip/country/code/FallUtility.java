package com.jchip.country.code;

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
}