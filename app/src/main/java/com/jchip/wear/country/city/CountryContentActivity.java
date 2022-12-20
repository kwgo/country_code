package com.jchip.wear.country.city;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jchip.wear.country.city.util.CountryUtility;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CountryContentActivity extends Activity {

    private Map<String, String[]> info;
    private List<String> gridInfo;

    private TextView searchText;
    private RecyclerView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.country_content_activity);
        // CountryUtility.setApplicationLanguage(this, "zh");

        this.gridView = findViewById(R.id.country_grid_view);

        this.info = CountryViewHelper.getISOInfo();
        this.gridInfo = new ArrayList<>(this.info.keySet());

        this.findViewById(R.id.country_title).setOnClickListener((v) -> loadKeyboard());

        this.searchText = this.findViewById(R.id.country_search);

        this.onSearch();
    }

    private void refreshGridView() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        this.gridView.setLayoutManager(layoutManager);
        gridView.setAdapter(new CountryContentAdapter(this, this.info, this.gridInfo));
    }

    private void onSearch() {
        String searchText = this.searchText.getText().toString().trim().toUpperCase();
        this.gridInfo = CountryViewHelper.searchCountryInfo(this, this.info, searchText);
        CountryViewHelper.sortCountryInfo(this, this.info, this.gridInfo, 0);
        refreshGridView();
    }

    private void loadKeyboard() {
        TextView searchText = this.findViewById(R.id.country_search);
        Intent intent = new Intent(this, SearchKeyboardActivity.class);
        intent.putExtra("text", searchText.getText().toString());
        this.startActivityForResult(intent, 100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 100) {
            String search = data.getStringExtra("search");
            this.searchText.setText(search);
            this.findViewById(R.id.country_hint).setVisibility(search == null || search.isEmpty() ? View.VISIBLE : View.INVISIBLE);
            this.onSearch();
        }
    }
}
