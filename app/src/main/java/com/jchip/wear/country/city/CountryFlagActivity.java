package com.jchip.wear.country.city;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.jchip.wear.country.city.util.CountryUtility;

public class CountryFlagActivity extends Activity {
    private String countryCode;

    private int position = 0;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.country_flag_activity);

        Intent intent = this.getIntent();
        this.countryCode = intent.getStringExtra("country");
        if (this.countryCode != null && !this.countryCode.isEmpty()) {
            int sourceId = CountryUtility.getSourceId(this, this.countryCode, "drawable", "good");
            this.imageView = this.findViewById(R.id.country_flag);
            this.imageView.setImageResource(sourceId);

            TextView textView = this.findViewById(R.id.flag_ratio);
            textView.setText(CountryHelper.getISOInfo().get(this.countryCode)[CountryHelper.FLAG_RATIO]);

            this.findViewById(R.id.country_back).setOnClickListener((v) -> this.finish());
            this.findViewById(R.id.country_flip).setOnClickListener((v) -> this.onFlip());
            this.findViewById(R.id.country_rotate).setOnClickListener((v) -> this.onRotate());
        } else {
            startActivity(new Intent(this, CountryContentActivity.class));
        }
    }

    private void onFlip() {
        this.position = (this.position + 2) % 4;
        this.doImage();
    }

    private void onRotate() {
        this.position = (this.position + 1) % 4;
        this.doImage();
    }

    private void doImage() {
        this.imageView.setRotation(0.0f);
        this.imageView.setRotationX(0.0f);
        this.imageView.setRotationY(0.0f);
        if (this.position == 0) {
        } else if (this.position == 1) {
            this.imageView.setRotation(90.0f);
        } else if (this.position == 2) {
            this.imageView.setRotationY(180.0f);
        } else if (this.position == 3) {
            this.imageView.setRotation(90.0f);
            this.imageView.setRotationY(180.0f);
        }
    }
}
