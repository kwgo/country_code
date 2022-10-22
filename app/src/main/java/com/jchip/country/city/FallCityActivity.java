package com.jchip.country.city;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FallCityActivity extends AppCompatActivity {

    private String countryCode;

    private List<String[]> cities;

    private List<Integer> indexInfo;
    private List<Integer> gridInfo;

    private RecyclerView gridView;
    private Spinner sortSpinner;
    private EditText searchText;

    private PopupWindow detailWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fall_city_activity);

        this.gridView = findViewById(R.id.grid_city_view);

        this.countryCode = this.getIntent().getStringExtra("countryCode");
        this.cities = FallCityHelper.getCities(this, countryCode);
        this.indexInfo = new ArrayList<>();
        for (int index = 0; index < this.cities.size(); index++) {
            this.indexInfo.add(index);
        }
        this.gridInfo = new ArrayList<>(this.indexInfo);

        this.initSearchText();
        this.initSortSpinner();

        findViewById(R.id.grid_back).setOnClickListener((v) -> this.finish());
    }

    private void refreshGridView() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        this.gridView.setLayoutManager(layoutManager);
        gridView.setAdapter(new FallCityAdapter(this, this.cities, this.gridInfo, FallUtility.isPortrait(this)));
    }

    private void initSortSpinner() {
        final CharSequence[] sortItems = this.getResources().getTextArray(R.array.grid_city_sort_items);
        sortSpinner = (Spinner) findViewById(R.id.grid_sort);
        sortSpinner.setAdapter(new ArrayAdapter<CharSequence>(this, R.layout.grid_spinner_item, Arrays.asList(sortItems)) {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                TextView view = (TextView) super.getDropDownView(position, convertView, parent);
                view.setPadding(40, 0, 40, 6);
                return view;
            }
        });
        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                FallUtility.runOnUiWorker(FallCityActivity.this, R.id.grid_processing, () -> {
                    onSort();
                    onSearch();
                    refreshGridView();
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void initSearchText() {
        searchText = findViewById(R.id.grid_search);
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
                FallUtility.runOnUiWorker(FallCityActivity.this, R.id.grid_processing, () -> {
                    onSearch();
                    refreshGridView();
                });
            }
        });
        searchText.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_GO || actionId == EditorInfo.IME_ACTION_NEXT) {
                textView.clearFocus();
                InputMethodManager imm = (InputMethodManager) textView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);
                return true;
            }
            return false;
        });
    }

    private void onSort() {
        int sortIndex = this.sortSpinner.getSelectedItemPosition();
        this.indexInfo = FallCityHelper.sortCities(this.cities, sortIndex);
        Log.d("pp", "xx after sorted cities indexInfo == " + indexInfo);

        this.gridInfo = new ArrayList<>(this.indexInfo);

        Log.d("pp", "xx after sorted cities gridInfo == " + gridInfo);

    }

    private void onSearch() {
        String searchText = this.searchText.getText().toString().trim().toUpperCase();
        this.gridInfo = FallCityViewHelper.searchCities(this.cities, this.indexInfo, searchText);
    }

    @SuppressLint("InflateParams")
    public void onDetail(View view, String[] city) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.fall_city_view, null);
        int imageId = FallUtility.getSourceId(this, countryCode, "drawable", "flag");
        ImageView imageView = popupView.findViewById(R.id.grid_image);
        imageView.setImageResource(imageId);
        // String text = FallUtility.getSourceText(this, countryCode, "string", "official");
        String text = city[FallCityViewHelper.CITY_ASCII];
        TextView textView = popupView.findViewById(R.id.grid_text);
        textView.setText(text);

        GridLayout detailView = popupView.findViewById(R.id.grid_view);
        GridLayout.LayoutParams leftParams = (GridLayout.LayoutParams) popupView.findViewById(R.id.grid_text_left).getLayoutParams();
        GridLayout.LayoutParams rightParams = (GridLayout.LayoutParams) popupView.findViewById(R.id.grid_text_right).getLayoutParams();
        int[] detailIndexes = FallCityViewHelper.detailIndexes;
        for (int index = 0; index < detailIndexes.length; index++) {
            String header = getResources().getString(FallCityViewHelper.getHeaderIndex(index));
            addTextView(this, detailView, index, 0, header, leftParams);

            int itemIndex = detailIndexes[index];
            String itemText;
            if (itemIndex == FallCityViewHelper.POPULATION) {
                itemText = FallCityViewHelper.getNumberItem(city[FallCityViewHelper.POPULATION]);
            } else if (itemIndex == FallCityViewHelper.CAPITAL) {
                itemText = FallCityViewHelper.getCapitalizeItem(city[itemIndex]);
            } else {
                itemText = city[itemIndex];
            }
            addTextView(this, detailView, index, 1, itemText, rightParams);
        }

        FallUtility.closeWindow(this.detailWindow);
        this.detailWindow = FallUtility.popupWindow(view, popupView);
    }

    private void addTextView(Context context, GridLayout view, int row, int col, String text, GridLayout.LayoutParams layoutParams) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
        textView.setPadding(0, 2, 0, 2);
        textView.setSingleLine(true);
        textView.setEllipsize(FallUtility.isDirectionRTL(context) ? TextUtils.TruncateAt.START : TextUtils.TruncateAt.END);
        textView.setContentDescription("@null");
        GridLayout.LayoutParams params = new GridLayout.LayoutParams(layoutParams);
        params.rowSpec = GridLayout.spec(row);
        view.addView(textView, params);
    }
}
