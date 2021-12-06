package com.jchip.country.code;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.os.Bundle;

public class FallWidgetRatioProvider extends FallWidgetFlagProvider {
    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
    }

    @Override
    protected boolean isRotatedImage(String item) {
        return false;
    }

    @Override
    protected int getWidgetLayoutId() {
        return R.layout.fall_widget_ratio;
    }
}