package com.jchip.country.code;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FallViewAdapter extends RecyclerView.Adapter<FallViewAdapter.ViewHolder> {

    protected Activity activity;
    private Map<String, String[]> info;
    private List<String> gridInfo = new ArrayList<>();

    private boolean isPortrait;
    private int spanCount;

    public FallViewAdapter(Activity activity, Map<String, String[]> info, List<String> gridInfo, boolean isPortrait) {
        this.activity = activity;
        this.info = info;
        this.gridInfo = gridInfo;
        this.isPortrait = isPortrait;
        this.spanCount = FallHelper.getItemCount(this.isPortrait);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false));
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.fall_grid_item;
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
            String[] iso = info.get(this.item);
            int itemIndex = FallHelper.getItemIndex(isPortrait, col);
            boolean isTop = col < FallHelper.BOTTOM_COLUMN_INDEX;
            boolean isImage = itemIndex == FallHelper.IMAGE;
            if (isImage) {
                String imageName = "flag_" + iso[FallHelper.ALPHA_2].toLowerCase();
                int sourceId = activity.getResources().getIdentifier(imageName, "drawable", activity.getPackageName());
                if (isTop) {
                    topImage.setImageResource(sourceId);
                } else {
                    bottomImage.setImageResource(sourceId);
                }
            } else {
                if (isTop) {
                    topText.setText(iso[itemIndex]);
                } else {
                    bottomText.setText(iso[itemIndex]);
                }
            }
            topImage.setVisibility(isImage && isTop ? View.VISIBLE : View.GONE);
            bottomImage.setVisibility(isImage && !isTop ? View.VISIBLE : View.GONE);
            topText.setVisibility(!isImage && isTop ? View.VISIBLE : View.GONE);
            bottomText.setVisibility(!isImage && !isTop ? View.VISIBLE : View.GONE);
        }

        private String item;
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
            this.topImage.setOnClickListener((v) -> ((FallActivity) activity).onDetail(item));
            this.bottomImage.setOnClickListener((v) -> ((FallActivity) activity).onDetail(item));
            this.topText.setOnClickListener((v) -> ((FallActivity) activity).onDetail(item));
            this.bottomText.setOnClickListener((v) -> ((FallActivity) activity).onDetail(item));
        }
    }
}