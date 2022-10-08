package com.jchip.country.city;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;

public class FallCountryAdapter extends RecyclerView.Adapter<FallCountryAdapter.ViewHolder> {

    protected Context context;
    private Map<String, String[]> info;
    private List<String> gridInfo;

    private boolean isPortrait;
    private int spanCount;

    public FallCountryAdapter(Context context, Map<String, String[]> info, List<String> gridInfo, boolean isPortrait) {
        this.context = context;
        this.info = info;
        this.gridInfo = gridInfo;
        this.isPortrait = isPortrait;
        this.spanCount = FallCountryViewHelper.getItemCount(this.isPortrait);
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
            int itemIndex = FallCountryViewHelper.getItemIndex(isPortrait, col);
            boolean isTop = col < FallCountryViewHelper.getBottomLineIndex(isPortrait);
            boolean isImage = itemIndex == FallCountryViewHelper.IMAGE;
            if (isImage) {
                int sourceId = FallUtility.getSourceId(context, this.item, "drawable", "flag");
                if (isTop) {
                    topImage.setImageResource(sourceId);
                } else {
                    bottomImage.setImageResource(sourceId);
                }
            } else {
                String text = iso[itemIndex].trim();
                if (itemIndex == FallCountryViewHelper.COUNTRY || itemIndex == FallCountryViewHelper.OFFICIAL || itemIndex == FallCountryViewHelper.CAPITAL) {
                    String prefix = itemIndex == FallCountryViewHelper.CAPITAL ? "capital" : itemIndex == FallCountryViewHelper.OFFICIAL ? "official" : "short";
                    text = FallUtility.getSourceText(context, this.item, "string", prefix);
                }
                if (!isPortrait && itemIndex == FallCountryViewHelper.CALL_CODE) {
                    text = "+" + text;
                }
                if (!isPortrait && itemIndex == FallCountryViewHelper.TIMEZONE) {
                    text = "UTC" + text;
                }
                if (isTop) {
                    topText.setText(text);
                } else {
                    bottomText.setText(text);
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
            this.topImage.setOnClickListener((v) -> ((FallCountryActivity) context).onSelect(item));
            this.bottomImage.setOnClickListener((v) -> ((FallCountryActivity) context).onSelect(item));
            this.topText.setOnClickListener((v) -> ((FallCountryActivity) context).onSelect(item));
            this.bottomText.setOnClickListener((v) -> ((FallCountryActivity) context).onSelect(item));
        }
    }
}