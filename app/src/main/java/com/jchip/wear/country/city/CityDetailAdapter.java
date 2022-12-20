package com.jchip.wear.country.city;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.jchip.wear.country.city.util.CountryUtility;

public class CityDetailAdapter extends RecyclerView.Adapter<CityDetailAdapter.ViewHolder> {

    protected Context context;
    private String[] cityInfo;

    private static final String[] headerItems = {
            // "head_city_ascii",
            "head_city_name",
            "head_city_capital",
            "head_city_admin",
            "head_city_lat",
            "head_city_lng",
            "head_city_population"
    };

    private static final int[] itemIndexes = {
            /*CityHelper.CITY_ASCII,*/ CityHelper.CITY,
            CityHelper.CAPITAL, CityHelper.ADMIN_NAME,
            CityHelper.LAT, CityHelper.LNG, CityHelper.POPULATION
    };

    public CityDetailAdapter(Context context, String[] cityInfo) {
        this.context = context;
        this.cityInfo = cityInfo;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false));
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.city_detail_grid;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return CityDetailAdapter.itemIndexes.length;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public void bind(int row) {
            int itemIndex = CityDetailAdapter.itemIndexes[row];
            if (itemIndex == CityHelper.CITY) {
                int resourceId = R.drawable.image_city_normal;
                if (CityHelper.isPrimary(cityInfo)) {
                    resourceId = R.drawable.image_city_primary;
                } else if (CityHelper.isAdmin(cityInfo)) {
                    resourceId = R.drawable.image_city_admin;
                } else if (CityHelper.isMinor(cityInfo)) {
                    resourceId = R.drawable.image_city_minor;
                }
                this.cityType.setBackgroundResource(resourceId);
                this.cityType.setVisibility(View.VISIBLE);
            } else {
                this.cityType.setVisibility(View.INVISIBLE);
            }
            this.cityHeader.setText(CountryUtility.getSourceText(context, headerItems[row], "string", null));
            this.cityDetail.setText(this.getItemText(itemIndex));
        }

        private View cityType;
        private TextView cityHeader;
        private TextView cityDetail;

        ViewHolder(View itemView) {
            super(itemView);
            this.cityType = itemView.findViewById(R.id.city_type);
            this.cityHeader = itemView.findViewById(R.id.city_header);
            this.cityDetail = itemView.findViewById(R.id.city_detail);
        }

        private String getItemText(int itemIndex) {
            String itemText = cityInfo[itemIndex];
            if (itemIndex == CityHelper.POPULATION) {
                itemText = CityHelper.getNumberItem(itemText);
            } else if (itemIndex == CityHelper.CAPITAL) {
                itemText = CountryUtility.getSourceText(context, itemText.trim(), "string", "city_type");
            }
            return itemText == null || itemText.isEmpty() ? "-" : itemText;
        }
    }
}