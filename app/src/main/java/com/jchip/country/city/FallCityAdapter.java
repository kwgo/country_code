package com.jchip.country.city;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FallCityAdapter extends RecyclerView.Adapter<FallCityAdapter.ViewHolder> {
    public static final String CITY_PRIMARY_TAG = "☆";
    public static final String CITY_ADMIN = "✧";
    public static final String CITY_NORMAL_TAG = "";
    public static final String CITY_MINOR_TAG = "⭑";

    protected Context context;
    private final List<String[]> cities;
    private final List<Integer> gridInfo;

    public FallCityAdapter(Context context, List<String[]> cities, List<Integer> gridInfo, boolean isPortrait) {
        this.context = context;
        this.cities = cities;
        this.gridInfo = gridInfo;
    }

    @NonNull
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
        holder.bind(position, 0);
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
        public void bind(int row, int col) {
            this.city = cities.get(row);
            String tag = FallCityViewHelper.isPrimary(city) ? CITY_PRIMARY_TAG :
                    FallCityViewHelper.isAdmin(city) ? CITY_ADMIN :
                            FallCityViewHelper.isMinor(city) ? CITY_MINOR_TAG : CITY_NORMAL_TAG;
            String location = "";
            if (!FallCityViewHelper.isEmpty(city, FallCityViewHelper.LAT) && !FallCityViewHelper.isEmpty(city, FallCityViewHelper.LNG)) {
                location = city[FallCityViewHelper.LAT] + ", " + city[FallCityViewHelper.LNG];
            }
            String cityName;
            if (!city[FallCityViewHelper.CITY].equals(city[FallCityViewHelper.CITY_ASCII]) && !FallCityViewHelper.isEmpty(city, FallCityViewHelper.CITY)) {
                cityName = city[FallCityViewHelper.CITY_ASCII] + "(" + city[FallCityViewHelper.CITY] + ")";
            } else {
                cityName = city[FallCityViewHelper.CITY_ASCII];
            }
            String population = FallCityViewHelper.getNumberItem(city, FallCityViewHelper.POPULATION);
            String adminName = city[FallCityViewHelper.ADMIN_NAME].trim();

            this.textTag.setText(tag);
            this.textCity.setText(cityName);
            this.textPopulation.setText(population);
            this.textAdmin.setText(adminName);
            this.textLocation.setText(location);

            this.textTag.setTextColor(
                    FallCityViewHelper.isPrimary(city) ? Color.rgb(102, 0, 153)
                            : FallCityViewHelper.isAdmin(city) ? Color.rgb(255, 69, 0)
                            : Color.rgb(192, 192, 192));

        }

        private String[] city;
        private final TextView textTag;
        private final TextView textCity;
        private final TextView textPopulation;
        private final TextView textAdmin;
        private final TextView textLocation;

        ViewHolder(View itemView) {
            super(itemView);
            this.textTag = itemView.findViewById(R.id.item_tag);
            this.textCity = itemView.findViewById(R.id.item_city);
            this.textPopulation = itemView.findViewById(R.id.item_population);
            this.textAdmin = itemView.findViewById(R.id.item_admin);
            this.textLocation = itemView.findViewById(R.id.item_location);

            this.textTag.setOnClickListener((v) -> ((FallCityActivity) context).onDetail(city));
            this.textCity.setOnClickListener((v) -> ((FallCityActivity) context).onDetail(city));
            this.textPopulation.setOnClickListener((v) -> ((FallCityActivity) context).onDetail(city));
            this.textAdmin.setOnClickListener((v) -> ((FallCityActivity) context).onDetail(city));
            this.textLocation.setOnClickListener((v) -> ((FallCityActivity) context).onDetail(city));
        }
    }
}