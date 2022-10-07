package com.jchip.country.city;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FallCityAdapter extends RecyclerView.Adapter<FallCityAdapter.ViewHolder> {

    protected Context context;
    private List<String[]> cities;
    private List<Integer> gridInfo = new ArrayList<>();

    private boolean isPortrait;
    private int spanCount;

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
        return R.layout.fall_country_grid;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(position / this.spanCount, position % this.spanCount);
    }

    @Override
    public int getItemCount() {
        return this.cities.size() * this.spanCount;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    
    public class ViewHolder extends RecyclerView.ViewHolder {
        public void bind(int row, int col) {
            this.item = gridInfo.get(row);
            String[] iso = cities.get(this.item);
            int itemIndex = FallCityViewHelper.getItemIndex(isPortrait, col);
            boolean isTop = col < FallCityViewHelper.getBottomLineIndex(isPortrait);
            boolean isTag = itemIndex == FallCityViewHelper.TAG;
            if (isTag) {
                int sourceId = this.item;
                if (isTop) {
                    topImage.setImageResource(sourceId);
                } else {
                    bottomImage.setImageResource(sourceId);
                }
            } else {
                String text = iso[itemIndex].trim();
//                if (itemIndex == FallCityViewHelper.COUNTRY || itemIndex == FallCityViewHelper.OFFICIAL || itemIndex == FallCityViewHelper.CAPITAL) {
//                    String prefix = itemIndex == FallCityViewHelper.CAPITAL ? "capital" : itemIndex == FallCityViewHelper.OFFICIAL ? "official" : "short";
//                    text = FallUtility.getSourceText(context, this.item, "string", prefix);
//                }
//                if (!isPortrait && itemIndex == FallCityViewHelper.CALL_CODE) {
//                    text = "+" + text;
//                }
//                if (!isPortrait && itemIndex == FallCityViewHelper.TIMEZONE) {
//                    text = "UTC" + text;
//                }
                if (isTop) {
                    topText.setText(text);
                } else {
                    bottomText.setText(text);
                }
            }
            topImage.setVisibility(isTag && isTop ? View.VISIBLE : View.GONE);
            bottomImage.setVisibility(isTag && !isTop ? View.VISIBLE : View.GONE);
            topText.setVisibility(!isTag && isTop ? View.VISIBLE : View.GONE);
            bottomText.setVisibility(!isTag && !isTop ? View.VISIBLE : View.GONE);
        }

        private int item;
        private ImageView topImage;
        private TextView topText;
        private ImageView bottomImage;
        private TextView bottomText;

        ViewHolder(View itemView) {
            super(itemView);
            this.topImage = itemView.findViewById(R.id.top_image);
            this.bottomImage = itemView.findViewById(R.id.bottom_image);
            this.topText = itemView.findViewById(R.id.top_text);
            this.bottomText = itemView.findViewById(R.id.bottom_text);
//            this.topImage.setOnClickListener((v) -> ((FallCityActivity) context).onDetail(item));
//            this.bottomImage.setOnClickListener((v) -> ((FallCityActivity) context).onDetail(item));
//            this.topText.setOnClickListener((v) -> ((FallCityActivity) context).onDetail(item));
//            this.bottomText.setOnClickListener((v) -> ((FallCityActivity) context).onDetail(item));
        }
    }
}