package com.jchip.country.city;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FallCityActivity extends AppCompatActivity {

    private List<String[]> cities;


    private List<Integer> gridInfo;

    private RecyclerView gridView;
    private Spinner sortSpinner;
    private EditText searchText;

    private RecyclerView.ItemDecoration itemDecoration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fall_country_activity);

        Intent intent = this.getIntent();
        String countryCode = intent.getStringExtra("countryCode");
        this.cities = FallCityHelper.getCities(this, countryCode);


        this.gridView = findViewById(R.id.grid_view);

        this.gridInfo = new ArrayList<>();
        for (int index = 0; index < this.cities.size(); index++) {
            this.gridInfo.add(index);
        }

        this.initSearchText();
        this.initSortSpinner();
    }

    private void refreshGridView() {
        int spanCount = FallCityViewHelper.getSpanCount(FallUtility.isPortrait(this));
        GridLayoutManager layoutManager = new GridLayoutManager(this, spanCount);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return FallCityViewHelper.getSpanSize(FallUtility.isPortrait(FallCityActivity.this), position);
            }
        });
        this.gridView.setLayoutManager(layoutManager);
        this.gridView.removeItemDecoration(this.itemDecoration);
        this.gridView.addItemDecoration(this.itemDecoration = new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int position = parent.getChildAdapterPosition(view);
                if (position >= 0) {
                    if (FallUtility.isDirectionRTL(FallCityActivity.this)) {
                        outRect.right = FallUtility.dp2px(FallCityActivity.this, FallCityViewHelper.getItemOffset(FallUtility.isPortrait(FallCityActivity.this), position));
                    } else {
                        outRect.left = FallUtility.dp2px(FallCityActivity.this, FallCityViewHelper.getItemOffset(FallUtility.isPortrait(FallCityActivity.this), position));
                    }
                }
            }
        });
        gridView.setAdapter(new FallCityAdapter(this, this.cities, this.gridInfo, FallUtility.isPortrait(this)));
    }

    private void initSortSpinner() {
        final CharSequence[] sortItems = this.getResources().getTextArray(R.array.grid_city_sort_items);
        sortSpinner = (Spinner) findViewById(R.id.grid_sort);
        sortSpinner.setAdapter(new ArrayAdapter<CharSequence>(this, R.layout.grid_spinner_item, Arrays.asList(sortItems)) {
//            @Override
//            public boolean isEnabled(int position) {
//                return FallCityViewHelper.isSortable(position - 1, isPortrait());
//            }

//            @Override
//            public View getDropDownView(int position, View convertView, ViewGroup parent) {
//                TextView view = (TextView) super.getDropDownView(position, convertView, parent);
//                view.setTextColor(position == 0 ? Color.LTGRAY : (isEnabled(position) ? Color.BLACK : Color.LTGRAY));
//                view.setText(position > 0 ? view.getText() : "");
//                view.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
//                view.setPadding(30, 0, 30, position == 0 ? -8 : 8);
//                return view;
//            }
        });
        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TextView textView = (TextView) adapterView.getChildAt(0);
                if (textView != null) {
                    textView.setTextColor(i == 0 ? searchText.getCurrentHintTextColor() : searchText.getCurrentTextColor());
                }
//                FallUtility.runOnUiWorker(FallCityActivity.this, () -> {
//                    if (i == 0) {
//                        onSearch();
//                    } else {
//                        onSort();
//                    }
//                    refreshGridView();
//                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
    }

    private void initSearchText() {
        searchText = (EditText) findViewById(R.id.grid_search);
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int maxLength = FallCityViewHelper.getInputCount(FallUtility.isPortrait(FallCityActivity.this));
                searchText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
//                FallUtility.runOnUiWorker(FallCityActivity.this, () -> {
//                    onSearch();
//                    refreshGridView();
//                });
            }
        });
        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_GO || actionId == EditorInfo.IME_ACTION_NEXT) {
                    textView.clearFocus();
                    InputMethodManager imm = (InputMethodManager) textView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
        //findViewById(R.id.grid_dots).setOnClickListener((v) -> onAbout());
    }


    private void onSort() {
        int sortIndex = this.sortSpinner.getSelectedItemPosition() - 1;
        //this.gridInfo = FallCityViewHelper.sortCountryInfo(this, this.info, this.gridInfo, sortIndex);
    }

    private void onSearch() {
        String searchText = this.searchText.getText().toString().trim().toUpperCase();
//        if (searchText.isEmpty()) {
//            this.gridInfo = FallCityViewHelper.sortCountryInfo(info, new ArrayList(info.keySet()), FallCityViewHelper.SORT_COUNTRY);
//        } else {
//            this.gridInfo = FallCityViewHelper.searchCountryInfo(this, this.info, searchText, isPortrait());
//        }
        this.onSort();
    }
}
