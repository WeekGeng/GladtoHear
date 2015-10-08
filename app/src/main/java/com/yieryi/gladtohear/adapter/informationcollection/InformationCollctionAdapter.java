package com.yieryi.gladtohear.adapter.informationcollection;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.yieryi.gladtohear.R;
import com.yieryi.gladtohear.bean.informationcollaction.Description;
import java.util.List;

/**
 * Created by Administrator on 2015/9/9.
 */
public class InformationCollctionAdapter extends RecyclerView.Adapter<InformationCollctionAdapter.MyViewHolder> {
    private List<Description> list;
    public InformationCollctionAdapter(List<Description> list){
        this.list=list;
        android.util.Log.e("list.size()=",list.size()+"");
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.main_brand_item,parent,false);
        MyViewHolder holder = new MyViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(InformationCollctionAdapter.MyViewHolder holder, int position) {
        ImageLoader.getInstance().displayImage(list.get(position).getImage(), holder.imageView, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {
                ((ImageView)view).setImageResource(R.mipmap.ic_launcher);
            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
                Toast.makeText(view.getContext(),"加载失败",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                ((ImageView)view).setImageBitmap(bitmap);
            }

            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public MyViewHolder(View itemView) {
            super(itemView);
            imageView=(ImageView)itemView.findViewById(R.id.main_brand_iv);
        }
    }
}
