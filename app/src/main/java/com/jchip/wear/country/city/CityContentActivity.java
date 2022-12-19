package com.jchip.wear.country.city;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CityContentActivity extends Activity {

    private String countryCode;

    private List<String[]> cities;

    //private List<Integer> indexInfo;
    private List<String[]> gridInfo;

    private RecyclerView gridView;
    private TextView searchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_content_activity);

        this.gridView = findViewById(R.id.city_grid_view);
        this.searchText = this.findViewById(R.id.city_search);

        Intent intent = this.getIntent();
        this.countryCode = intent.getStringExtra("country");
        Log.d("xx", "country code =" + countryCode);
        if (this.countryCode != null && !this.countryCode.isEmpty()) {
            this.cities = CityHelper.getCities(this, this.countryCode);
//            this.gridInfo = new ArrayList<>();
//            for (int index = 0; index < this.cities.size(); index++) {
//                this.gridInfo.add(index);
//            }
           // this.gridInfo = new ArrayList<>(this.indexInfo);


            //      String search = intent.getStringExtra("search");
            //    this.searchText.setText(search);
            this.findViewById(R.id.city_title).setOnClickListener((v) -> loadKeyboard());
            this.findViewById(R.id.city_back).setOnClickListener((v) -> this.finish());

            this.onSearch();
        } else {
            startActivity(new Intent(this, CountryContentActivity.class));
        }
    }

    private void refreshGridView() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        this.gridView.setLayoutManager(layoutManager);
        gridView.setAdapter(new CityContentAdapter(this, this.gridInfo));
    }

    private void onSearch() {
        String searchText = this.searchText.getText().toString().trim().toUpperCase();
        this.gridInfo = CityViewHelper.searchCities(this.cities, searchText);
        this.gridInfo = CityHelper.sortCities(gridInfo, 0);
        this.refreshGridView();
    }

    private void loadKeyboard() {
        TextView searchText = this.findViewById(R.id.city_search);
        Intent intent = new Intent(this, SearchKeyboardActivity.class);
        intent.putExtra("text", searchText.getText().toString());
        this.startActivityForResult(intent, 100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 100) {
            String search = data.getStringExtra("search");
            this.searchText.setText(search);
            this.findViewById(R.id.city_hint).setVisibility(search == null || search.isEmpty() ? View.VISIBLE : View.INVISIBLE);
            this.onSearch();
        }
    }
}
