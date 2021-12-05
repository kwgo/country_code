package com.jchip.country.code;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import java.util.Locale;

public class FallWidgetFlagProvider extends FallWidgetProvider {
    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);

        String msg = String.format(Locale.getDefault(), "[%d-%d] x [%d-%d]",
                newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH),
                newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_WIDTH),
                newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT),
                newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT));
        Log.d("", "msg:" + msg + "  value:" + newOptions.get(msg));

        float width = newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_WIDTH);
        float height = newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT);
        boolean landscape = width / height >= 0.85f;
        Log.d("", "ratio:" + (width / height));
        Log.d("", "landscape:" + landscape);

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.fall_widget_flag);
        remoteViews.setViewVisibility(R.id.widget_image_landscape, landscape ? View.VISIBLE : View.GONE);
        remoteViews.setViewVisibility(R.id.widget_image_portrait, landscape ? View.GONE : View.VISIBLE);

        appWidgetManager.partiallyUpdateAppWidget(appWidgetId, remoteViews);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    }
}