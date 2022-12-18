package com.jchip.wear.country.city;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.jchip.wear.country.city.util.CountryUtility;

public class CountryDetailAdapter extends RecyclerView.Adapter<CountryDetailAdapter.ViewHolder> {

    protected Context context;
    private String[] countryInfo;

    private static final String[] headerItems = {
            "head_country_name",
            "head_official_name",
            "head_sovereignty",
            "head_alpha2_code",
            "head_alpha3_code",
            "head_numeric_code",
            "head_internet_cctld",
            "head_capital",
            "head_timezone",
            "head_call_code",
            "head_currency",
            "head_symbol",
            "head_currency_name",
            "head_fractional_unit",
            "head_basic_number",
            "head_country_short",
            "head_flag_ratio",
            "head_population"
    };

    private static final int[] itemIndexes = {
            CountryHelper.COUNTRY, CountryHelper.OFFICIAL, CountryHelper.SOVEREIGNTY,
            CountryHelper.CAPITAL, CountryHelper.FLAG_RATIO, CountryHelper.ALPHA_2, CountryHelper.ALPHA_3,
            CountryHelper.NUMERIC, CountryHelper.INTERNET, CountryHelper.TIMEZONE, CountryHelper.CALL_CODE,
            CountryHelper.CURRENCY, CountryHelper.CURRENCY_NAME, CountryHelper.FRACTION, CountryHelper.POPULATION
    };

    public CountryDetailAdapter(Context context, String[] countryInfo) {
        this.context = context;
        this.countryInfo = countryInfo;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false));
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.country_detail_grid;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return CountryDetailAdapter.itemIndexes.length;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public void bind(int row) {
            int sourceId = CountryUtility.getSourceId(context, this.item, "drawable", "flag");
            this.countryFlag.setImageResource(sourceId);

            int itemIndex = CountryDetailAdapter.itemIndexes[row];
            this.countryHeader.setText(CountryUtility.getSourceText(context, headerItems[itemIndex], "string", null));
            this.countryDetail.setText(this.getItemText(itemIndex));
        }

        private String item;
        private ImageView countryFlag;
        private TextView countryHeader;
        private TextView countryDetail;

        ViewHolder(View itemView) {
            super(itemView);
            this.item = countryInfo[CountryHelper.ALPHA_2];
            this.countryFlag = itemView.findViewById(R.id.country_flag);
            this.countryHeader = itemView.findViewById(R.id.country_header);
            this.countryDetail = itemView.findViewById(R.id.country_detail);
        }

        private String getItemText(int itemIndex) {
            String itemText = countryInfo[itemIndex];
            if (itemIndex == CountryHelper.COUNTRY || itemIndex == CountryHelper.OFFICIAL || itemIndex == CountryHelper.CAPITAL) {
                String text = CountryUtility.getSourceText(context, item, "string", itemIndex == CountryHelper.CAPITAL ? "capital" : itemIndex == CountryHelper.OFFICIAL ? "official" : "short");
                itemText = text + (isEnglish() ? "" : "\n" + itemText);
            }
            return itemText == null || itemText.isEmpty() ? "-" : itemText;
        }
    }

    private boolean isEnglish() {
        return true;
    }
}