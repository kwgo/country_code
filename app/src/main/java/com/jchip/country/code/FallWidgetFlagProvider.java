package com.jchip.country.code;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.Map;

public class FallWidgetFlagProvider extends FallWidgetProvider {
 //   @Override
//    public void onReceive(final Context context, final Intent intent) {
//        final AppWidgetManager mgr = AppWidgetManager.getInstance(context);
//        if (intent.getAction().equals(FallWidgetView.ACTION_TOAST)) {
//            final int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
//            final String item = intent.getStringExtra(FallWidgetView.WIDGET_ITEM);
//            Toast.makeText(context, "Touched view " + item + " (item: " + item + ")", Toast.LENGTH_SHORT).show();
//        }
//        final String item = intent.getStringExtra(FallWidgetView.WIDGET_ITEM);
//        //Toast.makeText(context, "widget get " + item, Toast.LENGTH_SHORT).show();
//        Log.d("", "widget received item= " + intent.getStringExtra(FallWidgetView.WIDGET_ITEM));
//        Log.d("", "widget received id= " + intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID));
//        super.onReceive(context, intent);
//    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
//        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.fall_widget_flag);
 //       for (int appWidgetId : appWidgetIds) {
//            Intent intent = new Intent(context, FallWidgetFlagProvider.class);
//            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
//            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

//            Map<String, String[]> info = MainHelper.getISOInfo();
//            String item = "CA";
//            String[] iso = info.get(item);
//            views.setImageViewResource(R.id.widget_flag_image, R.drawable.flag_ca);
            // views.setBoolean();
            //appWidgetManager.updateAppWidget(appWidgetId, views);
 //       }
    }
}