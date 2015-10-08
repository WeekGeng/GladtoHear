package com.yieryi.gladtohear.adapter.helpcheck.shop_information;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.yieryi.gladtohear.R;
import com.yieryi.gladtohear.bean.helpcheck.detail.Detail;

import java.util.List;
import java.util.Map;
public class ShopInformationAdapter extends RecyclerView.Adapter<ShopInformationAdapter.MyViewHolder> {
    private List<Detail> list;
    public ShopInformationAdapter(List<Detail> list) {
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.help_check_address_item, parent, false);
        MyViewHolder holder = new MyViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Detail detail= list.get(position);
        holder.name.setText(detail.getShop_name2());
        holder.address.append(detail.getAddres());
        holder.time.append(detail.getBusiness_hours());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name,address,time;
        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.address_name);
            address = (TextView) itemView.findViewById(R.id.address_location);
            time = (TextView) itemView.findViewById(R.id.address_time);
        }
    }
}
