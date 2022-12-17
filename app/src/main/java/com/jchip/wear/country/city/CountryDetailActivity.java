package com.jchip.wear.country.city;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.Activity;


import com.jchip.wear.country.city.util.CountryUtility;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CountryDetailActivity extends Activity {

    private Map<String, String[]> info;
    private List<String> gridInfo;

    private EditText searchText;
    private RecyclerView gridView;
    private RecyclerView.ItemDecoration itemDecoration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.country_content_activity);
Log.d("xx", "11111");
        // FallUtility.setApplicationLanguage(this, "ar");

        this.gridView = findViewById(R.id.country_grid_view);

        this.info = CountryViewHelper.getISOInfo();
        this.gridInfo = new ArrayList<>(this.info.keySet());

        this.initSearchText();
        Log.d("xx", "xx init info size == " + this.gridInfo.size());

        this.onSearch();
    }

    private void refreshGridView() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        this.gridView.setLayoutManager(layoutManager);
        this.gridView.removeItemDecoration(this.itemDecoration);
        gridView.setAdapter(new CountryContentAdapter(this, this.info, this.gridInfo));
    }

    private void initSearchText() {
        searchText = findViewById(R.id.country_search);
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int maxLength = CountryViewHelper.getInputCount(CountryUtility.isPortrait(CountryDetailActivity.this));
                searchText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
                CountryUtility.runOnUiWorker(CountryDetailActivity.this, R.id.grid_processing, () -> {
                    onSearch();
                });
            }
        });
//        searchText.setOnEditorActionListener((textView, actionId, keyEvent) -> {
//            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_GO || actionId == EditorInfo.IME_ACTION_NEXT) {
//                textView.clearFocus();
//                InputMethodManager imm = (InputMethodManager) textView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);
//                return true;
//            }
//            return false;
//        });
    }

    private void onSearch() {
        String searchText = this.searchText.getText().toString().trim().toUpperCase();
        this.gridInfo = CountryViewHelper.searchCountryInfo(this, this.info, searchText);
        Log.d("xx", "info size == " + this.gridInfo.size());
        CountryViewHelper.sortCountryInfo(this, this.info, this.gridInfo, 0);
        this.refreshGridView();
    }

    public void onSelect(View view, String item) {
//             Intent intent = new Intent(this, CountryDetailActivity.class);
//            intent.putExtra("countryCode", item);
//            startActivity(intent);
    }
}
