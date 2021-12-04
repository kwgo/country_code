package com.jchip.country.code;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class FallWidgetSetting extends AppCompatActivity {
    ListView listView;
    TextView textView;
    String[] listItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fall_widget_setting);

        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        ListView simpleList;
        String countryList[] = {"India", "China", "australia", "Portugle", "America", "NewZealand"};
        int flags[] = {R.drawable.flag_in, R.drawable.flag_ch, R.drawable.flag_au, R.drawable.flag_bz, R.drawable.flag_us, R.drawable.flag_nz};

        simpleList = (ListView) findViewById(R.id.widget_setting_view);
        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), countryList, flags);
        simpleList.setAdapter(customAdapter);

//        listItem = getResources().getStringArray(R.array.grid_sort_items);
//        listView = (ListView) findViewById(R.id.widget_setting_view);
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.fall_widget_setting_item, R.id.widget_setting_item, listItem);
//        listView.setAdapter(arrayAdapter);


//        listView=(ListView)findViewById(R.id.widget_setting_view);
//        textView=(TextView)findViewById(R.id.widget_setting_item);
//        listItem = getResources().getStringArray(R.array.grid_sort_items);
//        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, R.id.widget_setting_item, listItem);
//        listView.setAdapter(adapter);

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                // TODO Auto-generated method stub
//                String value = arrayAdapter.getItem(position);
//                Toast.makeText(getApplicationContext(), value, Toast.LENGTH_SHORT).show();
//
//            }
//        });
    }

    public class CustomAdapter extends BaseAdapter {
        Context context;
        String countryList[];
        int flags[];
        LayoutInflater inflter;

        public CustomAdapter(Context applicationContext, String[] countryList, int[] flags) {
            this.context = context;
            this.countryList = countryList;
            this.flags = flags;
            inflter = (LayoutInflater.from(applicationContext));
        }

        @Override
        public int getCount() {
            return countryList.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = inflter.inflate(R.layout.fall_widget_setting_item, null);
            TextView country = view.findViewById(R.id.widget_setting_text);
            ImageView icon = view.findViewById(R.id.widget_setting_image);
            country.setText(countryList[i]);
            icon.setImageResource(flags[i]);
            return view;
        }
    }
}