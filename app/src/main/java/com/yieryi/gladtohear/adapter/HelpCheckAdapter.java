package com.yieryi.gladtohear.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.yieryi.gladtohear.R;
import com.yieryi.gladtohear.listener.OnRecycItemClickListener;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/9/8.
 */
public class HelpCheckAdapter extends RecyclerView.Adapter<HelpCheckAdapter.MyViewHolder>{
    private List<Map<String,String>> list;
    private OnRecycItemClickListener listener;
    public HelpCheckAdapter(List<Map<String,String>> list,OnRecycItemClickListener listener) {
        this.list=list;
        this.listener=listener;
        Log.e("lists===",list.size()+"");
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.helpcheck_recyc_item,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        String url=list.get(position).get("icon");
        String name=list.get(position).get("name");
//        ImageLoader.getInstance().displayImage(url,holder.icon);
        ImageLoader.getInstance().displayImage(url, holder.icon, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {
                ((ImageView)view).setImageResource(R.mipmap.ic_launcher);
            }
            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {

            }
            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                ((ImageView)view).setImageBitmap(bitmap);
            }
            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        });
        holder.tv_name.setText(name);
        holder.tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(view,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView icon;
        private TextView tv_name;
        public MyViewHolder(View itemView) {
            super(itemView);
            icon= (ImageView) itemView.findViewById(R.id.help_check_market_icon);
            tv_name=(TextView)itemView.findViewById(R.id.help_check_market_name);
        }
    }
}
