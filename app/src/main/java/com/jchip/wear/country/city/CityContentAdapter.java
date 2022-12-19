package com.jchip.wear.country.city;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CityContentAdapter extends RecyclerView.Adapter<CityContentAdapter.ViewHolder> {

    private Context context;
    private List<String[]> gridInfo;


    public CityContentAdapter(Context context, List<String[]> gridInfo) {
        this.context = context;
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
            this.city= gridInfo.get(row);

            this.cityName.setText(this.getItemText(CityHelper.CITY_ASCII));
            this.cityAdmin.setText(this.getItemText(CityHelper.ADMIN_NAME));

            int resourceId = R.drawable.image_city_normal;
            if (CityViewHelper.isPrimary(city)) {
                resourceId = R.drawable.image_city_primary;
            } else if (CityViewHelper.isAdmin(city)) {
                resourceId = R.drawable.image_city_admin;
            } else if (CityViewHelper.isMinor(city)) {
                resourceId = R.drawable.image_city_minor;
            }
            cityType.setBackgroundResource(resourceId);
        }

        private String[] city;
        private View cityType;
        private TextView cityName;
        private TextView cityAdmin;

        ViewHolder(View itemView) {
            super(itemView);
            this.cityType = itemView.findViewById(R.id.city_type);
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