package com.yieryi.gladtohear.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.yieryi.gladtohear.bean.informationcollaction.Description;
import com.yieryi.gladtohear.tools.log.Log;

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
    public InformationCollctionAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(InformationCollctionAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
