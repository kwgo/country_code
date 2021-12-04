package com.jchip.country.code;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.Map;

public class FallWidgetFlagProvider extends AppWidgetProvider {
    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.fall_widget_flag);
        for (int appWidgetId : appWidgetIds) {
//            Intent intent = new Intent(context, FallWidgetFlagProvider.class);
//            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
//            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

            Map<String, String[]> info = MainHelper.getISOInfo();
            String item = "CA";
            String[] iso = info.get(item);
            views.setImageViewResource(R.id.widget_flag_image, R.drawable.flag_ca);
           // views.setBoolean();
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }
}