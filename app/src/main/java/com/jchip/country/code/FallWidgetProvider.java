package com.jchip.country.code;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

public class FallWidgetProvider extends AppWidgetProvider {
    @Override
    public void onReceive(final Context context, Intent intent) {
        try {
            if (FallWidgetView.ACTION_NEXT.equals(intent.getAction())) {
                int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);
                this.onUpdate(context, AppWidgetManager.getInstance(context), new int[]{appWidgetId});
            } else {
                super.onReceive(context, intent);
            }
        } catch (Exception ex) {
            Log.e("widget", "widget action " + intent.getAction() + " error:", ex);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.fall_widget);
        //ComponentName componentName = new ComponentName(context, MainWidget.class);
        //int[] widgetIds = appWidgetManager.getAppWidgetIds(componentName);
        for (int appWidgetId : appWidgetIds) {
            Intent intent = new Intent(context, FallWidgetProvider.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            new FallWidgetView(context, intent, views).updateView(appWidgetId);
            //appWidgetManager.updateAppWidget(componentName, views);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }
}