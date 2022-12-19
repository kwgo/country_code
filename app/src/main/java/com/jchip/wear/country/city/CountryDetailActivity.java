package com.jchip.wear.country.city;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jchip.wear.country.city.util.CountryUtility;
import com.jchip.wear.country.city.util.GestureDetector;

public class CountryDetailActivity extends Activity {

    private String countryCode;
    private RecyclerView gridView;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.country_detail_activity);

        this.gestureDetector = new GestureDetector(this) {
            @Override
            public boolean onDoubleTap(MotionEvent event) {
                onFlag();
                return true;
            }
        };

        this.gridView = findViewById(R.id.country_detail_view);

        Intent intent = this.getIntent();
        this.countryCode = intent.getStringExtra("country");
        if (this.countryCode != null && !this.countryCode.isEmpty()) {
            int sourceId = CountryUtility.getSourceId(this, this.countryCode, "drawable", "flag");
            ImageView imageView = this.findViewById(R.id.country_icon);
            imageView.setImageResource(sourceId);
            imageView.setOnTouchListener((v, e) -> gestureDetector.onTouchEvent(v, e));

            this.findViewById(R.id.country_back).setOnClickListener((v) -> this.finish());
            this.findViewById(R.id.country_city).setOnClickListener((v) -> this.onSelect());

            this.refreshGridView();
        } else {
            startActivity(new Intent(this, CountryContentActivity.class));
        }
    }

    private void refreshGridView() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        this.gridView.setLayoutManager(layoutManager);
        gridView.setAdapter(new CountryDetailAdapter(this, CountryViewHelper.getISOInfo().get(countryCode)));
    }

    private void onSelect() {
        Intent intent = new Intent(this, CityContentActivity.class);
        intent.putExtra("country", this.countryCode);
        this.startActivity(intent);
    }

    public void onFlag() {
        Intent intent = new Intent(this, CountryFlagActivity.class);
        intent.putExtra("country", this.countryCode);
        this.startActivity(intent);
    }
}
