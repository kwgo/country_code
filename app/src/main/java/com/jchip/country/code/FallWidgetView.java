package com.jchip.country.code;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RemoteViews;

import java.util.Map;
import java.util.Random;

public class FallWidgetView {

    public static final String ACTION_NEXT = "actionNext";

    private Context context;
    private Intent intent;
    private RemoteViews views;

    private static Random random;

    static {
        random = random != null ? random : new Random();
    }

    public FallWidgetView(Context context, Intent intent) {
        this(context, intent, null);
    }

    public FallWidgetView(Context context, Intent intent, RemoteViews views) {
        this.context = context;
        this.intent = intent;
        this.views = views;
    }

    public void updateView(int widgetId) {
        Map<String, String[]> info = MainHelper.getISOInfo();
        int itemIndex = this.random.nextInt(info.size());
        String item = String.valueOf(info.keySet().toArray()[itemIndex]);
        String[] iso = info.get(item);

        this.setImageView(R.id.widget_image, this.getSourceId(item, "drawable", "flag"));
        this.setTextView(R.id.widget_title, this.getSourceText(item, "string", "short"));
        this.setTextView(R.id.widget_marker, iso[FallHelper.OFFICIAL]);

        StringBuilder detail = new StringBuilder();
        detail.append(item).append(" ");
        detail.append(iso[FallHelper.CURRENCY]).append(" ");
        detail.append(iso[FallHelper.POPULATION]).append(" ");
        detail.append(this.getSourceText(item, "string", "capital"));
        this.setTextView(R.id.widget_detail, detail.toString());

        this.setViewAction(widgetId, R.id.widget_view, ACTION_NEXT);
    }

    public int getSourceId(String item, String type, String prefix) {
        String name = prefix + "_" + item.toLowerCase();
        return context.getResources().getIdentifier(name, type, context.getPackageName());
    }

    public String getSourceText(String item, String type, String prefix) {
        int sourceId = this.getSourceId(item, type, prefix);
        return context.getResources().getString(sourceId);
    }

    public void setViewAction(int widgetId, int viewId, String action) {
        intent.setAction(action);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, widgetId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(viewId, pendingIntent);
    }

    public final void setTextView(int textViewId, String text) {
        if (text != null) {
            this.views.setTextViewText(textViewId, text);
        }
        this.setVisibility(textViewId, text != null);
    }

    public final void setImageView(int imageViewId, int imageSourceId) {
        this.views.setImageViewResource(imageViewId, imageSourceId);
    }

    public void setVisibility(int viewId, boolean isOn) {
        this.views.setViewVisibility(viewId, isOn ? View.VISIBLE : View.GONE);
    }
}
