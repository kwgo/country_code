package com.jchip.wear.country.city;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CityDetailActivity extends Activity {
    private String[] cityInfo;
    private RecyclerView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_detail_activity);

        this.gridView = findViewById(R.id.city_detail_view);
        this.findViewById(R.id.city_back).setOnClickListener((v) -> this.finish());

        Intent intent = this.getIntent();
        this.cityInfo = intent.getStringArrayExtra("city");
        if (this.cityInfo != null && this.cityInfo.length > 0) {
            this.refreshGridView();
        } else {
            startActivity(new Intent(this, CountryContentActivity.class));
        }
    }

    private void refreshGridView() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        this.gridView.setLayoutManager(layoutManager);
        gridView.setAdapter(new CityDetailAdapter(this, this.cityInfo));
    }
}
