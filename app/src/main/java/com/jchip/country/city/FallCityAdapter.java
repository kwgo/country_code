package com.jchip.country.city;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FallCityAdapter extends RecyclerView.Adapter<FallCityAdapter.ViewHolder> {
    public static final String CITY_PRIMARY_TAG = "☆";
    public static final String CITY_ADMIN = "✧";
    public static final String CITY_NORMAL_TAG = "⭑";
    public static final String CITY_MINOR_TAG = "⋆";

    protected Context context;
    private final List<String[]> cities;
    private final List<Integer> gridInfo;

    private final boolean isPortrait;
    private final int spanCount;

    public FallCityAdapter(Context context, List<String[]> cities, List<Integer> gridInfo, boolean isPortrait) {
        this.context = context;
        this.cities = cities;
        this.gridInfo = gridInfo;
        this.isPortrait = isPortrait;
        this.spanCount = FallCityViewHelper.getItemCount(this.isPortrait);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false));
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.fall_city_grid;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(position / this.spanCount, position % this.spanCount);
    }

    @Override
    public int getItemCount() {
        return this.gridInfo.size() * this.spanCount;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public void bind(int row, int col) {
            this.item = gridInfo.get(row);
            String[] city = cities.get(this.item);
            int itemIndex = FallCityViewHelper.getItemIndex(isPortrait, col);
            boolean isTop = col < FallCityViewHelper.getBottomLineIndex(isPortrait);
            boolean isTag = itemIndex == FallCityViewHelper.TAG;
            boolean isBold = itemIndex == FallCityViewHelper.CITY;

            String text = "";
            if (itemIndex == FallCityViewHelper.EMPTY) {
            } else if (itemIndex == FallCityViewHelper.TAG) {
                text = FallCityViewHelper.isPrimary(city) ? CITY_PRIMARY_TAG :
                        FallCityViewHelper.isAdmin(city) ? CITY_ADMIN :
                                FallCityViewHelper.isMinor(city) ? CITY_MINOR_TAG : CITY_NORMAL_TAG;
            } else if (itemIndex == FallCityViewHelper.LAT) {
                if (!FallCityViewHelper.isEmpty(city, FallCityViewHelper.LAT) && !FallCityViewHelper.isEmpty(city, FallCityViewHelper.LNG)) {
                    text = city[FallCityViewHelper.LAT] + ", " + city[FallCityViewHelper.LNG];
                }
            } else if (itemIndex == FallCityViewHelper.CITY) {
                if (!city[FallCityViewHelper.CITY].equals(city[FallCityViewHelper.CITY_ASCII]) && !FallCityViewHelper.isEmpty(city, FallCityViewHelper.CITY)) {
                    text = city[FallCityViewHelper.CITY_ASCII] + "(" + city[FallCityViewHelper.CITY] + ")";
                } else {
                    text = city[FallCityViewHelper.CITY_ASCII];
                }
            } else if (itemIndex == FallCityViewHelper.POPULATION) {
                text = FallCityViewHelper.getNumberItem(city, FallCityViewHelper.POPULATION);
            } else if (itemIndex == FallCityViewHelper.ADMIN_NAME) {
                text = city[itemIndex].trim();
            }

            itemText.setText(text);
            itemText.setTypeface(null, isBold ? Typeface.BOLD : Typeface.NORMAL);
        }

        private int item;
        private final TextView itemText;

        ViewHolder(View itemView) {
            super(itemView);
            this.itemText = itemView.findViewById(R.id.item_text);
        }
    }
}