package com.yieryi.gladtohear.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.yieryi.gladtohear.R;
/**
 * Created by Administrator on 2015/8/19.
 */
public class MarketSelAdapter extends RecyclerView.Adapter<MarketSelAdapter.ViewHolder>{
    private String[] markets;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public ImageView mImageView;
        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.tv_market_left);
            mImageView = (ImageView) v.findViewById(R.id.iv_market_sel);
        }
    }
    private OnItemClickListener onItemClickListener;
    public MarketSelAdapter(String[] markets,OnItemClickListener onItemClickListener) {
        this.markets = markets;
        this.onItemClickListener=onItemClickListener;
    }
    @Override
    public MarketSelAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.market_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.mTextView.setText(markets[position]);
        if (!"".equals(markets[position])) {
            holder.mImageView.setImageResource(R.mipmap.market_unsel);
            holder.mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                     onItemClickListener.onClick(view,position);
                }
            });
            holder.mImageView.setVisibility(View.VISIBLE);
        }else{
            holder.mImageView.setVisibility(View.INVISIBLE);
        }
    }
    @Override
    public int getItemCount() {
        return markets.length;
    }
    public interface OnItemClickListener{
        public void onClick(View v, int position);
    }
}
