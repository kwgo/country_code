package com.jchip.country.code;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FallWidgetSetting extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fall_widget_setting);

        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        ListView settingView;
        String countryList[] = {"India", "China", "australia", "Portugle", "America", "NewZealand"};
        int flags[] = {R.drawable.flag_in, R.drawable.flag_ch, R.drawable.flag_au, R.drawable.flag_bz, R.drawable.flag_us, R.drawable.flag_nz};

        settingView = (ListView) findViewById(R.id.widget_setting_view);
        ListViewAdapter listViewAdapter = new ListViewAdapter(getApplicationContext(), countryList, flags);
        settingView.setAdapter(listViewAdapter);

        settingView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String item = (String) listViewAdapter.getItem(position);
                Toast.makeText(getApplicationContext(), item, Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    public class ListViewAdapter extends BaseAdapter {
        private Context context;
        private LayoutInflater inflater;

        private Map<String, String[]> info;
        private List<String> sortedInfo;

        public ListViewAdapter(Context context, String[] countryList, int[] flags) {
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