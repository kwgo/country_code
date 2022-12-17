package com.jchip.wear.country.city;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.jchip.wear.country.city.util.CountryUtility;

import java.util.List;
import java.util.Map;

public class CountryContentAdapter extends RecyclerView.Adapter<CountryContentAdapter.ViewHolder> {

    protected Context context;
    private Map<String, String[]> info;
    private List<String> gridInfo;

    public CountryContentAdapter(Context context, Map<String, String[]> info, List<String> gridInfo) {
        this.context = context;
        this.info = info;
        this.gridInfo = gridInfo;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false));
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.country_content_grid;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d("xx", "bind position=" + position);
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return this.gridInfo.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public void bind(int row) {
            this.item = gridInfo.get(row);
            String[] iso = info.get(this.item);

            Log.d("XX", "item == " + item);

            int sourceId = CountryUtility.getSourceId(context, this.item, "drawable", "flag");
            Log.d("XX", "sourceId == " + sourceId);

            this.countryFlag.setImageResource(sourceId);

            this.countryCode.setText(iso[CountryViewHelper.ALPHA_2]);
            this.countryCurrency.setText(iso[CountryViewHelper.CURRENCY]);

            this.countryName.setText(CountryUtility.getSourceText(context, this.item, "string", "short"));
            this.countryCapital.setText(CountryUtility.getSourceText(context, this.item, "string", "capital"));
        }

        private String item;
        private ImageView countryFlag;
        private TextView countryCode;
        private TextView countryCurrency;
        private TextView countryName;
        private TextView countryCapital;

        ViewHolder(View itemView) {
            super(itemView);
            this.countryFlag = itemView.findViewById(R.id.country_flag);
            this.countryCode = itemView.findViewById(R.id.country_code);
            this.countryCurrency = itemView.findViewById(R.id.country_currency);
            this.countryName = itemView.findViewById(R.id.country_name);
            this.countryCapital = itemView.findViewById(R.id.country_capital);
            itemView.setOnClickListener((v) -> ((CountryContentActivity) context).onSelect(itemView, item));
        }
    }
}