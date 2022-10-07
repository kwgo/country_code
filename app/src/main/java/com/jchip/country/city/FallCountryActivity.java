package com.jchip.country.city;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class FallCountryActivity extends AppCompatActivity {

    private Map<String, String[]> info;
    private List<String> gridInfo;

    private RecyclerView gridView;
    private Spinner sortSpinner;
    private EditText searchText;

    private RecyclerView.ItemDecoration itemDecoration;

    private PopupWindow aboutWindow;
    private PopupWindow detailWindow;
    private PopupWindow selectWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fall_country_activity);
        //FallUtility.setApplicationLanguage(this, "ar");

        this.gridView = findViewById(R.id.grid_view);

        this.info = FallCountryViewHelper.getISOInfo();
        this.gridInfo = new ArrayList(this.info.keySet());

        this.initSearchText();
        this.initSortSpinner();
    }

    private void refreshGridView() {
        int spanCount = FallCountryViewHelper.getSpanCount(FallUtility.isPortrait(FallCountryActivity.this));
        GridLayoutManager layoutManager = new GridLayoutManager(this, spanCount);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return FallCountryViewHelper.getSpanSize(FallUtility.isPortrait(FallCountryActivity.this), position);
            }
        });
        this.gridView.setLayoutManager(layoutManager);
        this.gridView.removeItemDecoration(this.itemDecoration);
        this.gridView.addItemDecoration(this.itemDecoration = new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int position = parent.getChildAdapterPosition(view);
                if (position >= 0) {
                    if (FallUtility.isDirectionRTL(FallCountryActivity.this)) {
                        outRect.right = FallUtility.dp2px(FallCountryActivity.this, FallCountryViewHelper.getItemOffset(FallUtility.isPortrait(FallCountryActivity.this), position));
                    } else {
                        outRect.left = FallUtility.dp2px(FallCountryActivity.this, FallCountryViewHelper.getItemOffset(FallUtility.isPortrait(FallCountryActivity.this), position));
                    }
                }
            }
        });
        gridView.setAdapter(new FallCountryAdapter(this, this.info, this.gridInfo, FallUtility.isPortrait(FallCountryActivity.this)));
    }

    private void initSortSpinner() {
        final CharSequence[] sortItems = this.getResources().getTextArray(R.array.grid_country_sort_items);
        sortSpinner = (Spinner) findViewById(R.id.grid_sort);
        sortSpinner.setAdapter(new ArrayAdapter<CharSequence>(this, R.layout.grid_spinner_item, Arrays.asList(sortItems)) {
            @Override
            public boolean isEnabled(int position) {
                return FallCountryViewHelper.isSortable(position - 1, FallUtility.isPortrait(FallCountryActivity.this));
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                TextView view = (TextView) super.getDropDownView(position, convertView, parent);
                view.setTextColor(position == 0 ? Color.LTGRAY : (isEnabled(position) ? Color.BLACK : Color.LTGRAY));
                view.setText(position > 0 ? view.getText() : "");
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
                FallUtility.runOnUiWorker(FallCountryActivity.this, () -> {
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
                int maxLength = FallCountryViewHelper.getInputCount(FallUtility.isPortrait(FallCountryActivity.this));
                searchText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
                FallUtility.runOnUiWorker(FallCountryActivity.this, () -> {
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
        findViewById(R.id.grid_dots).setOnClickListener((v) -> onAbout());
    }

    public void onDetail(String item) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.fall_country_view, null);
        GridLayout detailView = (GridLayout) popupView.findViewById(R.id.grid_view);
        GridLayout.LayoutParams leftParams = (GridLayout.LayoutParams) popupView.findViewById(R.id.grid_text_left).getLayoutParams();
        GridLayout.LayoutParams rightParams = (GridLayout.LayoutParams) popupView.findViewById(R.id.grid_text_right).getLayoutParams();
        String[] info = this.info.get(item);
        int[] detailIndexes = FallCountryViewHelper.detailIndexes;
        for (int index = 0; index < detailIndexes.length; index++) {
            String header = getResources().getString(FallCountryViewHelper.getHeaderIndex(detailIndexes[index]));
            addTextView(this, detailView, index, 0, header, leftParams);
            String detailText = info[detailIndexes[index]].trim();
            if (detailIndexes[index] == FallCountryViewHelper.COUNTRY || detailIndexes[index] == FallCountryViewHelper.CAPITAL) {
                detailText = FallUtility.getSourceText(this, item, "string", detailIndexes[index] == FallCountryViewHelper.CAPITAL ? "capital" : "short");
            } else if (detailIndexes[index] == FallCountryViewHelper.OFFICIAL) {
            } else if (detailIndexes[index] == FallCountryViewHelper.SOVEREIGNTY) {
                detailText = FallUtility.getSourceText(this, detailText.toLowerCase().replace(" ", "_"), "string", "sovereignty");
            } else if (detailIndexes[index] == FallCountryViewHelper.CURRENCY) {
                detailText += " (" + info[FallCountryViewHelper.SYMBOL].trim() + ")";
            } else if (detailIndexes[index] == FallCountryViewHelper.CALL_CODE) {
                detailText = "+" + detailText.replace("-", " ");
            } else if (detailIndexes[index] == FallCountryViewHelper.TIMEZONE) {
                detailText = "UTC" + detailText;
            } else if (detailIndexes[index] == FallCountryViewHelper.FRACTION) {
                if (!"(none)".equals(detailText)) {
                    detailText += " (" + info[FallCountryViewHelper.BASIC_NUMBER].trim() + ")";
                }
            }
            addTextView(this, detailView, index, 1, detailText, rightParams);
        }

        ImageView fullImageView = (ImageView) popupView.findViewById(R.id.grid_full_image);
        fullImageView.getLayoutParams().width = findViewById(R.id.context_view).getWidth();
        fullImageView.getLayoutParams().height = findViewById(R.id.context_view).getHeight();
        fullImageView.setImageResource(FallUtility.getSourceId(this, item, "drawable", "good"));
        fullImageView.setOnClickListener((v) -> {
            popupView.findViewById(R.id.grid_scroll_view).setVisibility(View.VISIBLE);
            fullImageView.setVisibility(View.GONE);
        });

        ImageView imageView = (ImageView) popupView.findViewById(R.id.grid_image);
        imageView.setImageResource(FallUtility.getSourceId(this, item, "drawable", "good"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageView.setClipToOutline(true);
        }
        imageView.setOnClickListener((v) -> {
            popupView.findViewById(R.id.grid_scroll_view).setVisibility(View.GONE);
            fullImageView.setVisibility(View.VISIBLE);
        });

        TextView textView = (TextView) popupView.findViewById(R.id.grid_text);
        textView.setText(info[FallCountryViewHelper.FLAG_RATIO]);

        FallUtility.closeWindow(this.detailWindow);
        this.detailWindow = FallUtility.popupWindow(popupView);
    }

    private void onSort() {
        int sortIndex = this.sortSpinner.getSelectedItemPosition() - 1;
        this.gridInfo = FallCountryViewHelper.sortCountryInfo(this, this.info, this.gridInfo, sortIndex);
    }

    private void onSearch() {
        String searchText = this.searchText.getText().toString().trim().toUpperCase();
        if (searchText.isEmpty()) {
            this.gridInfo = FallCountryViewHelper.sortCountryInfo(info, new ArrayList(info.keySet()), FallCountryViewHelper.SORT_COUNTRY);
        } else {
            this.gridInfo = FallCountryViewHelper.searchCountryInfo(this, this.info, searchText, FallUtility.isPortrait(this));
        }
        this.onSort();
    }

    public void onSelect(String item) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.fall_select_view, null);
        ImageView countryImageView = (ImageView) popupView.findViewById(R.id.grid_country);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            countryImageView.setClipToOutline(true);
        }
        countryImageView.setOnClickListener((e) -> this.onDetail(item));

        FallUtility.closeWindow(this.selectWindow);
        this.selectWindow = FallUtility.popupWindow(popupView);
    }

    private void onAbout() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.fall_about_view, null);
        View linkView = popupView.findViewById(R.id.grid_link);
        linkView.setOnClickListener((e) -> this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=pub:JChip+Games"))));
        ImageView imageView = (ImageView) popupView.findViewById(R.id.grid_game);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageView.setClipToOutline(true);
        }
        imageView.setOnClickListener((e) -> this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.jchip.album"))));
        TextView textView = (TextView) popupView.findViewById(R.id.version_code);
        textView.setText(BuildConfig.VERSION_NAME);

        TextView ownedView = popupView.findViewById(R.id.grid_owned);
        ownedView.setText(R.string.about_owned);
        imageView.setImageResource(R.drawable.grid_about);
        imageView.setOnClickListener((e) -> this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.jchip.sokomon"))));
        TextView clickView = popupView.findViewById(R.id.grid_click);
        clickView.setText(R.string.about_play);

        FallUtility.closeWindow(this.aboutWindow);
        this.aboutWindow = FallUtility.popupWindow(popupView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        String text = this.getIntent().getStringExtra(FallWidgetView.WIDGET_TEXT);
        if (text != null && !text.trim().isEmpty()) {
            this.getIntent().replaceExtras((Bundle) null);
            this.searchText.setText(text);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        FallUtility.closeWindow(this.aboutWindow);
        FallUtility.closeWindow(this.detailWindow);
        FallUtility.closeWindow(this.selectWindow);
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

    public void showProcessing(boolean isShow) {
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.grid_processing);
        progressBar.setVisibility(isShow ? View.VISIBLE : View.INVISIBLE);
    }
}
