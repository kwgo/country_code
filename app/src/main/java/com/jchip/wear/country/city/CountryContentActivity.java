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
import java.util.Map;

public class CountryContentActivity extends Activity {

    private Map<String, String[]> info;
    private List<String> gridInfo;

    private TextView searchText;
    private RecyclerView gridView;
    private RecyclerView.ItemDecoration itemDecoration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.country_content_activity);

        // FallUtility.setApplicationLanguage(this, "ar");

        this.gridView = findViewById(R.id.country_grid_view);

        this.info = CountryViewHelper.getISOInfo();
        this.gridInfo = new ArrayList<>(this.info.keySet());

        this.findViewById(R.id.country_title).setOnClickListener((v) -> loadKeyboard());

        this.searchText = this.findViewById(R.id.country_search);

        Intent intent = this.getIntent();
        String searchText = intent.getStringExtra("searchText");
        this.searchText.setText(searchText);
        this.findViewById(R.id.country_hint).setVisibility(searchText == null || searchText.isEmpty() ? View.VISIBLE : View.INVISIBLE);
        
        this.onSearch();
    }

    private void loadKeyboard() {
        TextView searchText = this.findViewById(R.id.country_search);
        Intent intent = new Intent(this, SearchKeyboardActivity.class);
        intent.putExtra("text", searchText.getText().toString());
        intent.putExtra("class", this.getClass().getName());
        startActivity(intent);
    }

    private void refreshGridView() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        this.gridView.setLayoutManager(layoutManager);
        this.gridView.removeItemDecoration(this.itemDecoration);
        gridView.setAdapter(new CountryContentAdapter(this, this.info, this.gridInfo));
    }

    private void initSearchText() {
//        searchText = findViewById(R.id.country_search);
//        searchText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void afterTextChanged(Editable s) {
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                //CountryUtility.runOnUiWorker(CountryContentActivity.this, R.id.grid_processing, () -> {
//                    onSearch();
//               // });
//            }
//        });
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
        Log.d("xx", "searchText=" + searchText);
        this.gridInfo = CountryViewHelper.searchCountryInfo(this, this.info, searchText);
        Log.d("xx", "info size=" + this.gridInfo.size());
        CountryViewHelper.sortCountryInfo(this, this.info, this.gridInfo, 0);
        refreshGridView();
    }

    public void onSelect(View view, String item) {
//             Intent intent = new Intent(this, CountryDetailActivity.class);
//            intent.putExtra("countryCode", item);
//            startActivity(intent);
    }
}
