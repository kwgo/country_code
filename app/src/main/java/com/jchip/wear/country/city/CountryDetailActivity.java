package com.jchip.wear.country.city;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CountryDetailActivity extends Activity {

    private String[] countryInfo;
    private RecyclerView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.country_detail_activity);
        Log.d("xx", "222");

        this.gridView = findViewById(R.id.country_detail_view);


        Intent intent = this.getIntent();
        String item = intent.getStringExtra("country");
        if (item != null) {
            this.countryInfo = CountryViewHelper.getISOInfo().get(item);
        }

        if (this.countryInfo.length > 0) {
            this.refreshGridView();
        } else {
            this.finish();
        }
    }

    private void refreshGridView() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        this.gridView.setLayoutManager(layoutManager);
        gridView.setAdapter(new CountryDetailAdapter(this, this.countryInfo));
    }
}
