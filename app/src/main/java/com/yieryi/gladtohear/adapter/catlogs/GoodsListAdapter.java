package com.yieryi.gladtohear.adapter.catlogs;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.yieryi.gladtohear.R;
import com.yieryi.gladtohear.listener.OnRecycItemClickListener;
import java.util.List;

/**
 * Created by Administrator on 2015/9/15 0015.
 */
public class GoodsListAdapter extends RecyclerView.Adapter<GoodsListAdapter.MyViewHolder> {
    private List<String> list;
    private OnRecycItemClickListener listener;
    public GoodsListAdapter(List<String> list,OnRecycItemClickListener listener) {
        this.list = list;
        this.listener=listener;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.goods_list_item,parent,false);
        MyViewHolder holder=new MyViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv.setText(list.get(position));
        listener.onItemClick(holder.tv,position);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv=(TextView)itemView.findViewById(R.id.goods_sel_item_tv);
        }
    }
}
