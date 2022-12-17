package com.jchip.wear.country.city;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {
    public static final String CITY_PRIMARY_TAG = "☆";
    public static final String CITY_ADMIN = "✧";
    public static final String CITY_NORMAL_TAG = "✦";
    public static final String CITY_MINOR_TAG = "";

    protected Context context;
    private final List<String[]> cities;
    private final List<Integer> gridInfo;

    public CityAdapter(Context context, List<String[]> cities, List<Integer> gridInfo, boolean isPortrait) {
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
        return R.layout.city_grid;
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
            int index = gridInfo.get(row);
            this.city = cities.get(index);
            String tag = CityViewHelper.isPrimary(city) ? CITY_PRIMARY_TAG :
                    CityViewHelper.isAdmin(city) ? CITY_ADMIN :
                            CityViewHelper.isMinor(city) ? CITY_MINOR_TAG : CITY_NORMAL_TAG;
            String location = "";
            if (!CityViewHelper.isEmpty(city, CityViewHelper.LAT) && !CityViewHelper.isEmpty(city, CityViewHelper.LNG)) {
                location = city[CityViewHelper.LAT] + ", " + city[CityViewHelper.LNG];
            }
            String cityName;
            if (!city[CityViewHelper.CITY].equals(city[CityViewHelper.CITY_ASCII]) && !CityViewHelper.isEmpty(city, CityViewHelper.CITY)) {
                cityName = city[CityViewHelper.CITY_ASCII] + "(" + city[CityViewHelper.CITY] + ")";
            } else {
                cityName = city[CityViewHelper.CITY_ASCII];
            }
            String population = CityViewHelper.getNumberItem(city[CityViewHelper.POPULATION]);
            String adminName = city[CityViewHelper.ADMIN_NAME];

            this.textTag.setText(tag);
            this.textCity.setText(cityName);
            this.textPopulation.setText(population);
            this.textAdmin.setText(adminName);
            this.textLocation.setText(location);

            this.textTag.setTextColor(
                    CityViewHelper.isPrimary(city) ? Color.rgb(102, 0, 153)
                            : CityViewHelper.isAdmin(city) ? Color.rgb(255, 69, 0)
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

            this.textTag.setOnClickListener((v) -> ((CityActivity) context).onDetail(itemView, city));
            this.textCity.setOnClickListener((v) -> ((CityActivity) context).onDetail(itemView, city));
            this.textPopulation.setOnClickListener((v) -> ((CityActivity) context).onDetail(itemView, city));
            this.textAdmin.setOnClickListener((v) -> ((CityActivity) context).onDetail(itemView, city));
            this.textLocation.setOnClickListener((v) -> ((CityActivity) context).onDetail(itemView, city));
        }
    }
}