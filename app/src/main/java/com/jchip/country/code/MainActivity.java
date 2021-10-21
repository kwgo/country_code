package com.jchip.country.code;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity {
    private final static int ITEM_NUMBER = 50;
    private final static int TIMER_PERIOD = 100;

    private final static int SORT_COUNTRY = 0;

    private Map<String, String[]> info;
    private List<String> gridInfo = new ArrayList<>();

    private GridLayout gridView;

    private Spinner sortSpinner;
    private EditText searchText;

    private GridLayout.LayoutParams imageParams;
    private GridLayout.LayoutParams boldParams;
    private GridLayout.LayoutParams normalParams;

    private Timer timer;

    private AtomicBoolean isStopShowing = new AtomicBoolean(true);
    private AtomicBoolean isAllShown = new AtomicBoolean(false);
    private AtomicBoolean isFirstLoad = new AtomicBoolean(true);
    private AtomicInteger aboutCounter = new AtomicInteger(0);

    private PopupWindow detailWindow;
    private PopupWindow aboutWindow;
    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.gridView = (GridLayout) this.findViewById(R.id.grid_view);
        this.imageParams = (GridLayout.LayoutParams) this.findViewById(R.id.grid_image).getLayoutParams();
        this.boldParams = (GridLayout.LayoutParams) this.findViewById(R.id.grid_text_bold).getLayoutParams();
        this.normalParams = (GridLayout.LayoutParams) this.findViewById(R.id.grid_text_normal).getLayoutParams();

        final CharSequence[] sortItems = this.getResources().getTextArray(R.array.grid_sort_items);
        sortSpinner = (Spinner) findViewById(R.id.grid_sort);
        sortSpinner.setAdapter(new ArrayAdapter<CharSequence>(this, R.layout.grid_spinner_item, Arrays.asList(sortItems)) {
            @Override
            public boolean isEnabled(int position) {
                return MainHelper.isSortable(position - 1, isPortrait());
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                TextView view = (TextView) super.getDropDownView(position, convertView, parent);
                view.setTextColor(position == 0 ? Color.LTGRAY : (isEnabled(position) ? Color.BLACK : Color.LTGRAY));
                view.setText(position > 0 ? view.getText() : "");
                view.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                view.setPadding(30, 0, 40, position == 0 ? -8 : 10);
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
                onSort();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

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
                int maxLength = isPortrait() ? 12 : 30;
                searchText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLength)});
                onSearch();
            }
        });
        findViewById(R.id.grid_dots).setOnClickListener((v) -> onAbout());

        this.inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        this.info = MainHelper.getISOInfo();
        this.gridInfo = MainHelper.sortCountryInfo(info, new ArrayList(this.info.keySet()), SORT_COUNTRY);
    }

    private void onSort() {
        int sortIndex = this.sortSpinner.getSelectedItemPosition() - 1;
        isStopShowing.set(true);
        isFirstLoad.set(false);
        synchronized (this.gridInfo) {
            this.gridInfo = MainHelper.sortCountryInfo(this.info, this.gridInfo, sortIndex);
            this.gridView.removeAllViewsInLayout();
            isStopShowing.set(false);
            isAllShown.set(false);
        }
    }

    private void onSearch() {
        String searchText = this.searchText.getText().toString().trim().toUpperCase();
        isStopShowing.set(true);
        synchronized (this.gridInfo) {
            if (searchText.isEmpty()) {
                this.gridInfo = MainHelper.sortCountryInfo(info, new ArrayList(info.keySet()), SORT_COUNTRY);
            } else {
                this.gridInfo = MainHelper.searchCountryInfo(this.info, searchText, isPortrait());
            }
        }
        this.onSort();
    }

    private void addTextView(GridLayout view, int row, int col, String text, boolean isBold, GridLayout.LayoutParams layoutParams) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setTypeface(textView.getTypeface(), isBold ? Typeface.BOLD : Typeface.NORMAL);
        textView.setPadding(0, 2, 0, 2);
        GridLayout.LayoutParams params = new GridLayout.LayoutParams(layoutParams);
        params.rowSpec = GridLayout.spec(row);
        view.addView(textView, params);
    }

    private void onDetail(String item) {
        this.isStopShowing.set(true);
        View popupView = this.inflater.inflate(R.layout.activity_detail, null);
        GridLayout detailView = (GridLayout) popupView.findViewById(R.id.grid_view);
        GridLayout.LayoutParams leftParams = (GridLayout.LayoutParams) popupView.findViewById(R.id.grid_text_left).getLayoutParams();
        GridLayout.LayoutParams rightParams = (GridLayout.LayoutParams) popupView.findViewById(R.id.grid_text_right).getLayoutParams();
        String[] info = this.info.get(item);
        String[] header = MainHelper.getISOHeader();
        for (int row = 0; row < header.length; row++) {
            addTextView(detailView, row, 0, header[row], true, leftParams);
            addTextView(detailView, row, 1, info[row], true, rightParams);
        }

        int sourceId = getResources().getIdentifier("flag_" + item.toLowerCase(), "drawable", getPackageName());
        ImageView imageView = (ImageView) popupView.findViewById(R.id.grid_image);
        imageView.setImageResource(sourceId);
        imageView.setClipToOutline(true);

        MainUtility.closeWindow(this.detailWindow);
        this.detailWindow = MainUtility.popupWindow(this.gridView, popupView);

        this.isStopShowing.set(false);
    }

    private void onAbout() {
        View popupView = this.inflater.inflate(R.layout.activity_about, null);
        ImageView imageView = (ImageView) popupView.findViewById(R.id.grid_game);
        imageView.setClipToOutline(true);
        imageView.setOnClickListener((e) -> {
            this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.jchip.boxman")));
        });
        TextView textView = (TextView) popupView.findViewById(R.id.version_code);
        textView.setText(BuildConfig.VERSION_NAME);
        MainUtility.closeWindow(this.aboutWindow);
        this.aboutWindow = MainUtility.popupWindow(this.gridView, popupView);
    }

    private Void showGrid() {
        synchronized (this.gridInfo) {
            int infoCount = this.gridInfo.size();
            int rowCount = (this.gridView.getRowCount() + 1) / 2;
            //Log.d("", "showGrid() =================== rowCount=" + rowCount);
            if (infoCount == rowCount) {
                isAllShown.set(true);
            }
            for (int row = rowCount; row < rowCount + ITEM_NUMBER && row < infoCount; row++) {
                String item = this.gridInfo.get(row);
                String[] iso = this.info.get(item);
                if (this.isPortrait()) {
                    this.addImageView(row * 2 + 0, 0, item);
                    this.addTextView(row * 2 + 0, 1, 1, item, iso[MainHelper.ALPHA_2], true);
                    this.addTextView(row * 2 + 0, 2, 3, item, iso[MainHelper.COUNTRY], true);
                    this.addTextView(row * 2 + 1, 1, 1, item, iso[MainHelper.CURRENCY], false);
                    this.addTextView(row * 2 + 1, 2, 1, item, iso[MainHelper.CALL_CODE], false);
                    this.addTextView(row * 2 + 1, 3, 1, item, iso[MainHelper.TIMEZONE], false);
                    this.addTextView(row * 2 + 1, 4, 1, item, iso[MainHelper.CAPITAL], false);
                } else {
                    this.addImageView(row * 2 + 0, 0, item);
                    this.addTextView(row * 2 + 0, 1, 1, item, iso[MainHelper.ALPHA_2], true);
                    this.addTextView(row * 2 + 0, 2, 0, item, iso[MainHelper.OFFICIAL], true);
                    this.addTextView(row * 2 + 1, 1, 1, item, iso[MainHelper.ALPHA_3], false);
                    this.addTextView(row * 2 + 1, 2, 1, item, iso[MainHelper.NUMERIC], false);
                    this.addTextView(row * 2 + 1, 3, 1, item, iso[MainHelper.CURRENCY], false);
                    this.addTextView(row * 2 + 1, 4, 1, item, iso[MainHelper.SYMBOL], false);
                    this.addTextView(row * 2 + 1, 5, 1, item, iso[MainHelper.CALL_CODE], false);
                    this.addTextView(row * 2 + 1, 6, 1, item, iso[MainHelper.TIMEZONE], false);
                    this.addTextView(row * 2 + 1, 7, 1, item, iso[MainHelper.CAPITAL], false);
                }
            }
        }
        return null;
    }

    private void addImageView(int row, int col, String item) {
        int sourceId = getResources().getIdentifier("flag_" + item.toLowerCase(), "drawable", getPackageName());
        ImageView imageView = (ImageView) this.inflater.inflate(R.layout.grid_image, null);
        imageView.setImageResource(sourceId);
        imageView.setClipToOutline(true);
        imageView.setOnClickListener((v) -> onDetail(item));
        GridLayout.LayoutParams params = new GridLayout.LayoutParams(imageParams);
        params.rowSpec = GridLayout.spec(row, 2);
        params.columnSpec = GridLayout.spec(col, 1);
        this.gridView.addView(imageView, params);
    }

    private void addTextView(int row, int col, int columnSpan, String item, String text, boolean bold) {
        int sourceId = bold ? R.layout.grid_text_bold : R.layout.grid_text_normal;
        TextView textView = (TextView) this.inflater.inflate(sourceId, null);
        textView.setText(text);
        textView.setOnClickListener((v) -> onDetail(item));
        GridLayout.LayoutParams params = new GridLayout.LayoutParams(bold ? boldParams : normalParams);
        params.rowSpec = GridLayout.spec(row);
        params.columnSpec = GridLayout.spec(col, columnSpan);
        this.gridView.addView(textView, params);
    }

    private void showProcessing(boolean isShow) {
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.grid_processing);
        progressBar.setVisibility(isShow ? View.VISIBLE : View.INVISIBLE);
    }

    private boolean isPortrait() {
        int orientation = this.getResources().getConfiguration().orientation;
        return orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!this.isFirstLoad.get()) {
            Log.d("", "counter === " + this.aboutCounter.get());
            if (this.aboutCounter.getAndIncrement() % 3 == 0) {
                Log.d("", "pop about window....+++***");
                this.onAbout();
            }

            this.onSort();
        }

        if (timer == null) {
            timer = MainUtility.scheduleTimer(TIMER_PERIOD, () -> {
                if (!isStopShowing.get() && !isAllShown.get()) {
                    runOnUiThread(() -> showProcessing(true));
                    MainUtility.runOnUiAndWait(MainActivity.this, () -> showGrid());
                    runOnUiThread(() -> showProcessing(false));
                }
            });
        }
    }

    @Override
    protected void onStop() {
        MainUtility.cancelTimer(timer);
        this.gridView.removeAllViewsInLayout();
        timer = null;
        super.onStop();
    }

}