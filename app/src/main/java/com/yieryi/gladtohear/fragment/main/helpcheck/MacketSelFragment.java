package com.yieryi.gladtohear.fragment.main.helpcheck;
import android.app.ProgressDialog;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.yieryi.gladtohear.R;
import com.yieryi.gladtohear.activities.HelpCheckActivity;
import com.yieryi.gladtohear.adapter.HelpCheckAdapter;
import com.yieryi.gladtohear.bean.market_address.Description;
import com.yieryi.gladtohear.bean.market_address.Root;
import com.yieryi.gladtohear.biz.helpcheck.marcket_sel.MacketSelBiz;
import com.yieryi.gladtohear.listener.OnRecycItemClickListener;
import com.yieryi.gladtohear.listener.RequestListener;
import com.yieryi.gladtohear.view.LoadingDialog;

import java.io.IOException;
import java.util.List;
/**
 * 超市选择
 */
public class MacketSelFragment extends Fragment implements RequestListener,HelpCheckAdapter.OnItemClickListener{
        private final String TAG=MacketSelFragment.class.getSimpleName();
        private RecyclerView marcket_sel_fragment_recycle;
        private HelpCheckAdapter adapter;
        private MacketSelBiz biz;
        private List<Description> list;
        private LoadingDialog dialog;
        private Handler handler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        switch (msg.what){
                                case 0:
                                        dialog.dismiss();
                                        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
                                        marcket_sel_fragment_recycle.setLayoutManager(manager);
                                        marcket_sel_fragment_recycle.setAdapter(adapter);
                                        break;
                                case 1:
                                        Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();
                                        break;
                        }
                }
        };
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                if (dialog==null) {
                        dialog = new LoadingDialog(getActivity(), R.style.dialog);
                        biz = new MacketSelBiz();
                        biz.getMacketList(0, TAG,this);
                        dialog.show();
                }
        }
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
                View view=inflater.inflate(R.layout.marcket_sel_fragment, container, false);
                marcket_sel_fragment_recycle=(RecyclerView)view.findViewById(R.id.marcket_sel_fragment_recycle);
                return view;
        }
        @Override
        public void onResponse(Response response) {
                if (response.isSuccessful()) {
                        Gson gson = new Gson();
                        try {
                                String json = response.body().string();
                                String jsons = "{\n" +
                                        "    \"status\": 0, \n" +
                                        "    \"error\": \"\", \n" +
                                        "    \"data\": {\n" +
                                        "        \"list\": [\n" +
                                        "            {\n" +
                                        "                \"shop_id\": \"195\", \n" +
                                        "                \"shop_name\": \"万宁\", \n" +
                                        "                \"shop_logo\": \"\", \n" +
                                        "                \"initial\": \"\", \n" +
                                        "                \"city\": \"0\", \n" +
                                        "                \"parent_id\": \"0\", \n" +
                                        "                \"hid\": \"0:0195\", \n" +
                                        "                \"shop_name2\": \"\", \n" +
                                        "                \"addres\": \"\", \n" +
                                        "                \"route\": \"\", \n" +
                                        "                \"business_hours\": \"\", \n" +
                                        "                \"phone\": \"\", \n" +
                                        "                \"baidu_jing\": \"\", \n" +
                                        "                \"baidu_wei\": \"\", \n" +
                                        "                \"gaode_jing\": \"\", \n" +
                                        "                \"gaode_wei\": \"\", \n" +
                                        "                \"ctime\": \"2015-09-21 16:39:32\"\n" +
                                        "            }, \n" +
                                        "            {\n" +
                                        "                \"shop_id\": \"194\", \n" +
                                        "                \"shop_name\": \"迪亚天天\", \n" +
                                        "                \"shop_logo\": \"\", \n" +
                                        "                \"initial\": \"\", \n" +
                                        "                \"city\": \"0\", \n" +
                                        "                \"parent_id\": \"0\", \n" +
                                        "                \"hid\": \"0:0194\", \n" +
                                        "                \"shop_name2\": \"\", \n" +
                                        "                \"addres\": \"\", \n" +
                                        "                \"route\": \"\", \n" +
                                        "                \"business_hours\": \"\", \n" +
                                        "                \"phone\": \"\", \n" +
                                        "                \"baidu_jing\": \"\", \n" +
                                        "                \"baidu_wei\": \"\", \n" +
                                        "                \"gaode_jing\": \"\", \n" +
                                        "                \"gaode_wei\": \"\", \n" +
                                        "                \"ctime\": \"2015-09-21 13:59:02\"\n" +
                                        "            }, \n" +
                                        "            {\n" +
                                        "                \"shop_id\": \"193\", \n" +
                                        "                \"shop_name\": \"吉买盛\", \n" +
                                        "                \"shop_logo\": \"\", \n" +
                                        "                \"initial\": \"\", \n" +
                                        "                \"city\": \"0\", \n" +
                                        "                \"parent_id\": \"0\", \n" +
                                        "                \"hid\": \"0:0193\", \n" +
                                        "                \"shop_name2\": \"\", \n" +
                                        "                \"addres\": \"\", \n" +
                                        "                \"route\": \"\", \n" +
                                        "                \"business_hours\": \"\", \n" +
                                        "                \"phone\": \"\", \n" +
                                        "                \"baidu_jing\": \"\", \n" +
                                        "                \"baidu_wei\": \"\", \n" +
                                        "                \"gaode_jing\": \"\", \n" +
                                        "                \"gaode_wei\": \"\", \n" +
                                        "                \"ctime\": \"2015-09-18 14:11:56\"\n" +
                                        "            }, \n" +
                                        "            {\n" +
                                        "                \"shop_id\": \"192\", \n" +
                                        "                \"shop_name\": \"华联超市\", \n" +
                                        "                \"shop_logo\": \"\", \n" +
                                        "                \"initial\": \"\", \n" +
                                        "                \"city\": \"0\", \n" +
                                        "                \"parent_id\": \"0\", \n" +
                                        "                \"hid\": \"0:0192\", \n" +
                                        "                \"shop_name2\": \"\", \n" +
                                        "                \"addres\": \"\", \n" +
                                        "                \"route\": \"\", \n" +
                                        "                \"business_hours\": \"\", \n" +
                                        "                \"phone\": \"\", \n" +
                                        "                \"baidu_jing\": \"\", \n" +
                                        "                \"baidu_wei\": \"\", \n" +
                                        "                \"gaode_jing\": \"\", \n" +
                                        "                \"gaode_wei\": \"\", \n" +
                                        "                \"ctime\": \"2015-09-17 15:14:56\"\n" +
                                        "            }, \n" +
                                        "            {\n" +
                                        "                \"shop_id\": \"191\", \n" +
                                        "                \"shop_name\": \"屈臣氏\", \n" +
                                        "                \"shop_logo\": \"\", \n" +
                                        "                \"initial\": \"\", \n" +
                                        "                \"city\": \"0\", \n" +
                                        "                \"parent_id\": \"0\", \n" +
                                        "                \"hid\": \"0:0191\", \n" +
                                        "                \"shop_name2\": \"\", \n" +
                                        "                \"addres\": \"\", \n" +
                                        "                \"route\": \"\", \n" +
                                        "                \"business_hours\": \"\", \n" +
                                        "                \"phone\": \"\", \n" +
                                        "                \"baidu_jing\": \"\", \n" +
                                        "                \"baidu_wei\": \"\", \n" +
                                        "                \"gaode_jing\": \"\", \n" +
                                        "                \"gaode_wei\": \"\", \n" +
                                        "                \"ctime\": \"2015-09-16 15:10:15\"\n" +
                                        "            }, \n" +
                                        "            {\n" +
                                        "                \"shop_id\": \"184\", \n" +
                                        "                \"shop_name\": \"易买得\", \n" +
                                        "                \"shop_logo\": \"\", \n" +
                                        "                \"initial\": \"\", \n" +
                                        "                \"city\": \"50\", \n" +
                                        "                \"parent_id\": \"0\", \n" +
                                        "                \"hid\": \"0:0184\", \n" +
                                        "                \"shop_name2\": \"\", \n" +
                                        "                \"addres\": \"\", \n" +
                                        "                \"route\": \"\", \n" +
                                        "                \"business_hours\": \"\", \n" +
                                        "                \"phone\": \"\", \n" +
                                        "                \"baidu_jing\": \"\", \n" +
                                        "                \"baidu_wei\": \"\", \n" +
                                        "                \"gaode_jing\": \"\", \n" +
                                        "                \"gaode_wei\": \"\", \n" +
                                        "                \"ctime\": \"2015-09-11 16:49:26\"\n" +
                                        "            }, \n" +
                                        "            {\n" +
                                        "                \"shop_id\": \"174\", \n" +
                                        "                \"shop_name\": \"欧尚\", \n" +
                                        "                \"shop_logo\": \"\", \n" +
                                        "                \"initial\": \"\", \n" +
                                        "                \"city\": \"0\", \n" +
                                        "                \"parent_id\": \"0\", \n" +
                                        "                \"hid\": \"0:0174\", \n" +
                                        "                \"shop_name2\": \"\", \n" +
                                        "                \"addres\": \"\", \n" +
                                        "                \"route\": \"\", \n" +
                                        "                \"business_hours\": \"\", \n" +
                                        "                \"phone\": \"\", \n" +
                                        "                \"baidu_jing\": \"\", \n" +
                                        "                \"baidu_wei\": \"\", \n" +
                                        "                \"gaode_jing\": \"\", \n" +
                                        "                \"gaode_wei\": \"\", \n" +
                                        "                \"ctime\": \"2015-09-11 16:41:22\"\n" +
                                        "            }, \n" +
                                        "            {\n" +
                                        "                \"shop_id\": \"169\", \n" +
                                        "                \"shop_name\": \"乐天玛特\", \n" +
                                        "                \"shop_logo\": \"\", \n" +
                                        "                \"initial\": \"\", \n" +
                                        "                \"city\": \"0\", \n" +
                                        "                \"parent_id\": \"0\", \n" +
                                        "                \"hid\": \"0:0169\", \n" +
                                        "                \"shop_name2\": \"\", \n" +
                                        "                \"addres\": \"\", \n" +
                                        "                \"route\": \"\", \n" +
                                        "                \"business_hours\": \"\", \n" +
                                        "                \"phone\": \"\", \n" +
                                        "                \"baidu_jing\": \"\", \n" +
                                        "                \"baidu_wei\": \"\", \n" +
                                        "                \"gaode_jing\": \"\", \n" +
                                        "                \"gaode_wei\": \"\", \n" +
                                        "                \"ctime\": \"2015-09-11 16:31:43\"\n" +
                                        "            }, \n" +
                                        "            {\n" +
                                        "                \"shop_id\": \"168\", \n" +
                                        "                \"shop_name\": \"麦德龙\", \n" +
                                        "                \"shop_logo\": \"\", \n" +
                                        "                \"initial\": \"\", \n" +
                                        "                \"city\": \"0\", \n" +
                                        "                \"parent_id\": \"0\", \n" +
                                        "                \"hid\": \"0:0168\", \n" +
                                        "                \"shop_name2\": \"\", \n" +
                                        "                \"addres\": \"\", \n" +
                                        "                \"route\": \"\", \n" +
                                        "                \"business_hours\": \"\", \n" +
                                        "                \"phone\": \"\", \n" +
                                        "                \"baidu_jing\": \"\", \n" +
                                        "                \"baidu_wei\": \"\", \n" +
                                        "                \"gaode_jing\": \"\", \n" +
                                        "                \"gaode_wei\": \"\", \n" +
                                        "                \"ctime\": \"2015-09-11 15:00:01\"\n" +
                                        "            }, \n" +
                                        "            {\n" +
                                        "                \"shop_id\": \"98\", \n" +
                                        "                \"shop_name\": \"卜蜂莲花\", \n" +
                                        "                \"shop_logo\": \"\", \n" +
                                        "                \"initial\": \"\", \n" +
                                        "                \"city\": \"0\", \n" +
                                        "                \"parent_id\": \"0\", \n" +
                                        "                \"hid\": \"0:0098\", \n" +
                                        "                \"shop_name2\": \"\", \n" +
                                        "                \"addres\": \"\", \n" +
                                        "                \"route\": \"\", \n" +
                                        "                \"business_hours\": \"\", \n" +
                                        "                \"phone\": \"\", \n" +
                                        "                \"baidu_jing\": \"\", \n" +
                                        "                \"baidu_wei\": \"\", \n" +
                                        "                \"gaode_jing\": \"\", \n" +
                                        "                \"gaode_wei\": \"\", \n" +
                                        "                \"ctime\": \"2015-09-10 16:44:06\"\n" +
                                        "            }, \n" +
                                        "            {\n" +
                                        "                \"shop_id\": \"37\", \n" +
                                        "                \"shop_name\": \"沃尔玛\", \n" +
                                        "                \"shop_logo\": \"\", \n" +
                                        "                \"initial\": \"\", \n" +
                                        "                \"city\": \"0\", \n" +
                                        "                \"parent_id\": \"0\", \n" +
                                        "                \"hid\": \"0:0037\", \n" +
                                        "                \"shop_name2\": \"\", \n" +
                                        "                \"addres\": \"\", \n" +
                                        "                \"route\": \"\", \n" +
                                        "                \"business_hours\": \"\", \n" +
                                        "                \"phone\": \"\", \n" +
                                        "                \"baidu_jing\": \"\", \n" +
                                        "                \"baidu_wei\": \"\", \n" +
                                        "                \"gaode_jing\": \"\", \n" +
                                        "                \"gaode_wei\": \"\", \n" +
                                        "                \"ctime\": \"2015-09-10 15:37:27\"\n" +
                                        "            }, \n" +
                                        "            {\n" +
                                        "                \"shop_id\": \"8\", \n" +
                                        "                \"shop_name\": \"农工商\", \n" +
                                        "                \"shop_logo\": \"\", \n" +
                                        "                \"initial\": \"\", \n" +
                                        "                \"city\": \"0\", \n" +
                                        "                \"parent_id\": \"0\", \n" +
                                        "                \"hid\": \"0:0008\", \n" +
                                        "                \"shop_name2\": \"\", \n" +
                                        "                \"addres\": \"\", \n" +
                                        "                \"route\": \"\", \n" +
                                        "                \"business_hours\": \"\", \n" +
                                        "                \"phone\": \"\", \n" +
                                        "                \"baidu_jing\": \"\", \n" +
                                        "                \"baidu_wei\": \"\", \n" +
                                        "                \"gaode_jing\": \"\", \n" +
                                        "                \"gaode_wei\": \"\", \n" +
                                        "                \"ctime\": \"2015-09-06 11:23:48\"\n" +
                                        "            }, \n" +
                                        "            {\n" +
                                        "                \"shop_id\": \"7\", \n" +
                                        "                \"shop_name\": \"大润发\", \n" +
                                        "                \"shop_logo\": \"\", \n" +
                                        "                \"initial\": \"\", \n" +
                                        "                \"city\": \"0\", \n" +
                                        "                \"parent_id\": \"0\", \n" +
                                        "                \"hid\": \"0:0007\", \n" +
                                        "                \"shop_name2\": \"\", \n" +
                                        "                \"addres\": \"\", \n" +
                                        "                \"route\": \"\", \n" +
                                        "                \"business_hours\": \"\", \n" +
                                        "                \"phone\": \"\", \n" +
                                        "                \"baidu_jing\": \"\", \n" +
                                        "                \"baidu_wei\": \"\", \n" +
                                        "                \"gaode_jing\": \"\", \n" +
                                        "                \"gaode_wei\": \"\", \n" +
                                        "                \"ctime\": \"2015-09-06 11:23:40\"\n" +
                                        "            }, \n" +
                                        "            {\n" +
                                        "                \"shop_id\": \"6\", \n" +
                                        "                \"shop_name\": \"乐购\", \n" +
                                        "                \"shop_logo\": \"\", \n" +
                                        "                \"initial\": \"\", \n" +
                                        "                \"city\": \"0\", \n" +
                                        "                \"parent_id\": \"0\", \n" +
                                        "                \"hid\": \"0:0006\", \n" +
                                        "                \"shop_name2\": \"\", \n" +
                                        "                \"addres\": \"\", \n" +
                                        "                \"route\": \"\", \n" +
                                        "                \"business_hours\": \"\", \n" +
                                        "                \"phone\": \"\", \n" +
                                        "                \"baidu_jing\": \"\", \n" +
                                        "                \"baidu_wei\": \"\", \n" +
                                        "                \"gaode_jing\": \"\", \n" +
                                        "                \"gaode_wei\": \"\", \n" +
                                        "                \"ctime\": \"2015-09-06 11:23:33\"\n" +
                                        "            }, \n" +
                                        "            {\n" +
                                        "                \"shop_id\": \"4\", \n" +
                                        "                \"shop_name\": \"家乐福\", \n" +
                                        "                \"shop_logo\": \"\", \n" +
                                        "                \"initial\": \"\", \n" +
                                        "                \"city\": \"0\", \n" +
                                        "                \"parent_id\": \"0\", \n" +
                                        "                \"hid\": \"0:0004\", \n" +
                                        "                \"shop_name2\": \"\", \n" +
                                        "                \"addres\": \"\", \n" +
                                        "                \"route\": \"\", \n" +
                                        "                \"business_hours\": \"\", \n" +
                                        "                \"phone\": \"\", \n" +
                                        "                \"baidu_jing\": \"\", \n" +
                                        "                \"baidu_wei\": \"\", \n" +
                                        "                \"gaode_jing\": \"\", \n" +
                                        "                \"gaode_wei\": \"\", \n" +
                                        "                \"ctime\": \"2015-09-06 11:23:12\"\n" +
                                        "            }\n" +
                                        "        ]\n" +
                                        "    }\n" +
                                        "}";
                                Log.e("jsons=", json);
                                final Root root = gson.fromJson(jsons, Root.class);
                                if ("0".equals(String.valueOf(root.getStatus()))) {
                                        list = root.getData().getList();
                                        adapter = new HelpCheckAdapter(list,this);
                                        handler.sendEmptyMessage(0);
                                } else {
                                        handler.sendEmptyMessage(1);
                                }
                        } catch (IOException e) {
                                e.printStackTrace();
                        }
                }
        }

        @Override
        public void onFailue(Request request, IOException e) {
        }

        @Override
        public void onClick(View view) {

        }
}
