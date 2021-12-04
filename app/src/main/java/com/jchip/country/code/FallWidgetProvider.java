package com.jchip.country.code;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

public class FallWidgetProvider extends AppWidgetProvider {
    @Override
    public void onReceive(final Context context, Intent intent) {
        try {
            if (FallWidgetView.ACTION_NEXT.equals(intent.getAction())) {
                int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);
                this.onUpdate(context, AppWidgetManager.getInstance(context), new int[]{appWidgetId});
            } else if (FallWidgetView.ACTION_APP.equals(intent.getAction())) {
                this.activeApp(context, intent);
            } else {
                super.onReceive(context, intent);
            }
        } catch (Exception ex) {
            Log.e("widget", "widget action " + intent.getAction() + " error:", ex);
        }
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
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
            new FallWidgetView(context, intent, views, appWidgetId).updateView();
            //appWidgetManager.updateAppWidget(componentName, views);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    private void activeApp(Context context, Intent intent) {
        Intent activityIntent = new Intent(context, FallActivity.class);
        activityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //activityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        activityIntent.putExtra(FallWidgetView.WIDGET_ITEM, intent.getStringExtra(FallWidgetView.WIDGET_ITEM));
        activityIntent.putExtra(FallWidgetView.WIDGET_TEXT, intent.getStringExtra(FallWidgetView.WIDGET_TEXT));
        context.startActivity(activityIntent);
    }

}