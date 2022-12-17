package com.jchip.wear.country.city;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.jchip.wear.country.city.util.CountryUtility;

import java.util.List;
import java.util.Map;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> {

    protected Context context;
    private Map<String, String[]> info;
    private List<String> gridInfo;

    private boolean isPortrait;
    private int spanCount;

    public CountryAdapter(Context context, Map<String, String[]> info, List<String> gridInfo, boolean isPortrait) {
        this.context = context;
        this.info = info;
        this.gridInfo = gridInfo;
        this.isPortrait = isPortrait;
        this.spanCount = CountryViewHelper.getItemCount(this.isPortrait);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false));
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.country_grid;
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
            int itemIndex = CountryViewHelper.getItemIndex(isPortrait, col);
            boolean isTop = col < CountryViewHelper.getBottomLineIndex(isPortrait);
            boolean isImage = itemIndex == CountryViewHelper.IMAGE;
            if (isImage) {
                int sourceId = CountryUtility.getSourceId(context, this.item, "drawable", "flag");
                if (isTop) {
                    topImage.setImageResource(sourceId);
                } else {
                    bottomImage.setImageResource(sourceId);
                }
            } else {
                String text = iso[itemIndex].trim();
                if (itemIndex == CountryViewHelper.COUNTRY || itemIndex == CountryViewHelper.OFFICIAL || itemIndex == CountryViewHelper.CAPITAL) {
                    String prefix = itemIndex == CountryViewHelper.CAPITAL ? "capital" : itemIndex == CountryViewHelper.OFFICIAL ? "official" : "short";
                    text = CountryUtility.getSourceText(context, this.item, "string", prefix);
                }
                if (!isPortrait && itemIndex == CountryViewHelper.CALL_CODE) {
                    text = "+" + text;
                }
                if (!isPortrait && itemIndex == CountryViewHelper.TIMEZONE) {
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
            this.topImage.setOnClickListener((v) -> ((CountryActivity) context).onSelect(itemView, item));
            this.bottomImage.setOnClickListener((v) -> ((CountryActivity) context).onSelect(itemView, item));
            this.topText.setOnClickListener((v) -> ((CountryActivity) context).onSelect(itemView, item));
            this.bottomText.setOnClickListener((v) -> ((CountryActivity) context).onSelect(itemView, item));
        }
    }
}