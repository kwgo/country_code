package com.jchip.country.code;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SizeF;
import android.widget.RemoteViews;

import androidx.collection.ArrayMap;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

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
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager,  int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged( context,  appWidgetManager,   appWidgetId,  newOptions);

        for(String key : newOptions.keySet()) {
            Log.d("", "key:"+key + "  value:"+ newOptions.get(key));
        }

        RemoteViews remoteViews=
                new RemoteViews(context.getPackageName(), R.layout.fall_widget);
        String msg=
                String.format(Locale.getDefault(),
                        "[%d-%d] x [%d-%d]",
                        newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH),
                        newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_WIDTH),
                        newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT),
                        newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT));

        remoteViews.setTextViewText(R.id.widget_title, "text");

        Intent intent = new Intent(context, FallWidgetProvider.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        new FallWidgetView(context, intent, remoteViews, appWidgetId).setupView();
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.d("", "AppWidgetManager:"+appWidgetIds + "  AppWidgetManager:"+ appWidgetIds);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.fall_widget);
          //ComponentName componentName = new ComponentName(context, MainWidget.class);
        //int[] widgetIds = appWidgetManager.getAppWidgetIds(componentName);
        for (int appWidgetId : appWidgetIds) {
            Intent intent = new Intent(context, FallWidgetProvider.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            new FallWidgetView(context, intent, views, appWidgetId).updateView();
            //appWidgetManager.updateAppWidget(componentName, views);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }
}