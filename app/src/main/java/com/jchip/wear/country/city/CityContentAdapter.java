package com.jchip.wear.country.city;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.jchip.wear.country.city.util.CountryUtility;

import java.util.List;

public class CityContentAdapter extends RecyclerView.Adapter<CityContentAdapter.ViewHolder> {

    private Context context;
    private List<String[]> cities;
    private List<Integer> gridInfo;


    public CityContentAdapter(Context context, List<String[]> cities, List<Integer> gridInfo) {
        this.context = context;
        this.cities = cities;
        this.gridInfo = gridInfo;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false));
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.city_content_grid;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
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
            int index = gridInfo.get(row);
            this.city = cities.get(index);

            this.cityName.setText(this.getItemText(CityHelper.CITY));
            this.cityAdmin.setText(this.getItemText(CityHelper.ADMIN_NAME));
        }

        private String[] city;
        private TextView cityName;
        private TextView cityAdmin;

        ViewHolder(View itemView) {
            super(itemView);
            this.cityName = itemView.findViewById(R.id.city_name);
            this.cityAdmin = itemView.findViewById(R.id.city_admin);
            itemView.setOnClickListener((v) -> this.onSelect());
        }

        private String getItemText(int itemIndex) {
            String itemText = this.city[itemIndex];
            return itemText == null || itemText.isEmpty() ? "-" : itemText;
        }

        private void onSelect() {
//        Intent intent = new Intent(context, CityDetailActivity.class);
//        intent.putExtra("city", this.city);
//        context.startActivity(intent);
        }
    }
}