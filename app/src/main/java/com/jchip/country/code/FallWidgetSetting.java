package com.jchip.country.code;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class FallWidgetSetting extends AppCompatActivity {

    private int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    private int resultValue = RESULT_CANCELED;

    @Override
    protected void onDestroy() {
        if (resultValue == RESULT_CANCELED) {
            final Intent resultIntent = new Intent();
            resultIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            setResult(RESULT_CANCELED, resultIntent);
        }
        Log.d("", "onDestroy =====================================");
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("", "onStop =====================================");
        if (resultValue == RESULT_CANCELED) {
            final Intent resultIntent = new Intent();
            resultIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            setResult(RESULT_CANCELED, resultIntent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fall_widget_setting);

        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        final Intent intent = getIntent();
        final Bundle extras = intent.getExtras();
        if (extras != null) {
            appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        Log.d("", "activity appWidgetId====================" + appWidgetId);
        if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }

        ListView settingView = (ListView) findViewById(R.id.widget_setting_view);
        ListViewAdapter listViewAdapter = new ListViewAdapter(getApplicationContext());
        settingView.setAdapter(listViewAdapter);

        settingView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String item = (String) listViewAdapter.getItem(position);
                Toast.makeText(getApplicationContext(), item, Toast.LENGTH_SHORT).show();

                final Intent resultIntent = new Intent();
                resultIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
                resultIntent.putExtra(FallWidgetView.WIDGET_ITEM, item);
                setResult(resultValue = RESULT_OK, resultIntent);

                // Request widget update
                //  final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(FallWidgetSetting.this);
                //   FallWidgetFlagProvider.updateAppWidget(this, appWidgetManager, appWidgetId, mRowIDs);

// This is equivalent to your ChecksWidgetProvider.updateAppWidget()
//                appWidgetManager.updateAppWidget(appWidgetId,
//                        ChecksWidgetProvider.buildRemoteViews(getApplicationContext(),
//                                appWidgetId));


//                // Since min and max is usually the same, just take min
//                mWidgetLandWidth = providerInfo.minWidth;
//                mWidgetPortHeight = providerInfo.minHeight;
//                mWidgetPortWidth = providerInfo.minWidth;
//                mWidgetLandHeight = providerInfo.minHeight;


//                String[] iso = MainHelper.getISOInfo().get(item);


                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getApplicationContext());
                AppWidgetProviderInfo providerInfo = appWidgetManager.getAppWidgetInfo(appWidgetId);
                int layoutId = providerInfo.initialLayout;
                Log.d("", "activity layoutId====================" + layoutId);


                RemoteViews remoteViews = new RemoteViews(getPackageName(), layoutId);
                int sourceId = FallUtility.getSourceId(getApplicationContext(), item, "drawable", "good");
                remoteViews.setImageViewResource(R.id.widget_flag_image, sourceId);
                remoteViews.setImageViewResource(R.id.widget_rotate_image, sourceId);

//                Intent intent = new Intent(getApplicationContext(), FallWidgetFlagProvider.class);
////            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
//                Intent intent = new Intent(context, FallWidgetProvider.class);
//                intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
//                intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
//
//
//                intent.setAction(action);
//                PendingIntent pendingIntent = PendingIntent.getBroadcast(context, appWidgetId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//                views.setOnClickPendingIntent(viewId, pendingIntent);


                appWidgetManager.updateAppWidget(appWidgetId, remoteViews);

                finish();
            }
        });
    }

    public class ListViewAdapter extends BaseAdapter {
        private Context context;
        private LayoutInflater inflater;

        private Map<String, String[]> info;
        private List<String> sortedInfo;

        public ListViewAdapter(Context context) {
            this.context = context;
            this.inflater = (LayoutInflater.from(context));

            this.info = MainHelper.getISOInfo();

            this.sortedInfo = new ArrayList(this.info.keySet());

            FallHelper.sortCountryInfo(this.context, this.info, this.sortedInfo, FallHelper.COUNTRY);
        }

        @Override
        public int getCount() {
            return this.info.size();
        }

        @Override
        public Object getItem(int position) {
            return this.sortedInfo.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            view = inflater.inflate(R.layout.fall_widget_setting_item, null);
            TextView textView = view.findViewById(R.id.widget_setting_text);
            ImageView imageView = view.findViewById(R.id.widget_setting_image);

            String item = this.sortedInfo.get(position);
            textView.setText(FallUtility.getSourceText(context, item, "string", "short"));
            imageView.setImageResource(FallUtility.getSourceId(context, item, "drawable", "flag"));
            return view;
        }
    }
}