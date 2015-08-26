package com.yieryi.gladtohear.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.yieryi.gladtohear.R;
/**
 * Created by Administrator on 2015/8/26.
 */
public class ShopingCarAdapter extends RecyclerView.Adapter<ShopingCarAdapter.MyHolder>{
    LayoutInflater inflater;
    public ShopingCarAdapter(Context context){
        inflater=LayoutInflater.from(context);
    }
    @Override
    public ShopingCarAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if (viewType==1){
            v=LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.marketsel_content_item, parent, false);
        }else {
            v=LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.marketsel_name_item, parent, false);
        }
        MyHolder vh = new MyHolder(v);
        return vh;
    }

    @Override
    public int getItemViewType(int position) {
        return position==0?0:1;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

    }
    @Override
    public int getItemCount() {
        return 10;
    }
    class MyHolder extends RecyclerView.ViewHolder{
        public MyHolder(View itemView) {
            super(itemView);
        }
    }
}
