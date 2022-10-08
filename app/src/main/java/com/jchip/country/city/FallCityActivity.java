package com.jchip.country.city;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridLayout;
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


    private List<Integer> gridInfo;

    private RecyclerView gridView;
    private Spinner sortSpinner;
    private EditText searchText;

    private RecyclerView.ItemDecoration itemDecoration;

    private PopupWindow detailWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fall_country_activity);

        this.gridView = findViewById(R.id.grid_view);

        this.countryCode = this.getIntent().getStringExtra("countryCode");
        this.cities = FallCityHelper.getCities(this, countryCode);
        this.gridInfo = new ArrayList<>();
        for (int index = 0; index < this.cities.size(); index++) {
            this.gridInfo.add(index);
        }
        Log.d("O", "cities:" + cities.size());
        Log.d("O", "gridInfo:" + gridInfo.size());

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

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                TextView view = (TextView) super.getDropDownView(position, convertView, parent);
                view.setTextColor(Color.LTGRAY);
                //  view.setText(position > 0 ? view.getText() : "");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    view.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                }
                view.setPadding(30, 0, 30, position == 0 ? -8 : 8);
                return view;
            }
        });
        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TextView textView = (TextView) adapterView.getChildAt(0);
                if (textView != null) {
                    textView.setTextColor(i == 0 ? searchText.getCurrentHintTextColor() : searchText.getCurrentTextColor());
                }
                FallUtility.runOnUiWorker(FallCityActivity.this, R.id.grid_processing, () -> {
                    if (i == 0) {
                        onSearch();
                    } else {
                        onSort();
                    }
                    refreshGridView();
                });
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
                FallUtility.runOnUiWorker(FallCityActivity.this, R.id.grid_processing, () -> {
                    onSearch();
                    refreshGridView();
                });
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

    @SuppressLint("InflateParams")
    public void onDetail(int item) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.fall_city_view, null);
        GridLayout detailView = popupView.findViewById(R.id.grid_view);
        GridLayout.LayoutParams leftParams = (GridLayout.LayoutParams) popupView.findViewById(R.id.grid_text_left).getLayoutParams();
        GridLayout.LayoutParams rightParams = (GridLayout.LayoutParams) popupView.findViewById(R.id.grid_text_right).getLayoutParams();
        String[] city = this.cities.get(item);
        int[] detailIndexes = FallCountryViewHelper.detailIndexes;
        for (int index = 0; index < detailIndexes.length; index++) {
            String header = getResources().getString(FallCountryViewHelper.getHeaderIndex(detailIndexes[index]));
            addTextView(this, detailView, index, 0, header, leftParams);
            String detailText = city != null ? city[detailIndexes[index]].trim() : "";
//            if (detailIndexes[index] == FallCountryViewHelper.COUNTRY || detailIndexes[index] == FallCountryViewHelper.CAPITAL) {
//                detailText = FallUtility.getSourceText(this, item, "string", detailIndexes[index] == FallCountryViewHelper.CAPITAL ? "capital" : "short");
//                // } else if (detailIndexes[index] == FallCountryViewHelper.OFFICIAL) {
//            } else if (detailIndexes[index] == FallCountryViewHelper.SOVEREIGNTY) {
//                detailText = FallUtility.getSourceText(this, detailText.toLowerCase().replace(" ", "_"), "string", "sovereignty");
//            } else if (detailIndexes[index] == FallCountryViewHelper.CURRENCY) {
//                detailText += " (" + (city != null ? city[FallCountryViewHelper.SYMBOL].trim() : "") + ")";
//            } else if (detailIndexes[index] == FallCountryViewHelper.CALL_CODE) {
//                detailText = "+" + detailText.replace("-", " ");
//            } else if (detailIndexes[index] == FallCountryViewHelper.TIMEZONE) {
//                detailText = "UTC" + detailText;
//            } else if (detailIndexes[index] == FallCountryViewHelper.FRACTION) {
//                if (!"(none)".equals(detailText)) {
//                    detailText += " (" + (city != null ? city[FallCountryViewHelper.BASIC_NUMBER].trim() : "") + ")";
//                }
//            }
            addTextView(this, detailView, index, 1, detailText, rightParams);
        }

//        View contextView = findViewById(R.id.context_view);
//        ImageView fullImageView = popupView.findViewById(R.id.grid_full_image);
//        fullImageView.getLayoutParams().width = contextView.getWidth();
//        fullImageView.getLayoutParams().height = contextView.getHeight();
//        fullImageView.setImageResource(FallUtility.getSourceId(this, item, "drawable", "good"));
//        fullImageView.setOnClickListener((v) -> {
//            popupView.findViewById(R.id.grid_scroll_view).setVisibility(View.VISIBLE);
//            fullImageView.setVisibility(View.GONE);
//        });
//
//        ImageView imageView = popupView.findViewById(R.id.grid_image);
//        imageView.setImageResource(FallUtility.getSourceId(this, item, "drawable", "good"));
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            imageView.setClipToOutline(true);
//        }
//        imageView.setOnClickListener((v) -> {
//            popupView.findViewById(R.id.grid_scroll_view).setVisibility(View.GONE);
//            fullImageView.setVisibility(View.VISIBLE);
//        });
//
//        TextView textView = popupView.findViewById(R.id.grid_text);
//        textView.setText(city != null ? city[FallCountryViewHelper.FLAG_RATIO] : "");

        FallUtility.closeWindow(this.detailWindow);
        this.detailWindow = FallUtility.popupWindow(popupView);
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
