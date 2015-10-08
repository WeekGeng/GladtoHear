package com.yieryi.gladtohear.fragment.main.userspace;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.yieryi.gladtohear.R;
import com.yieryi.gladtohear.activities.ListRecordDetailActivity;
import com.yieryi.gladtohear.activities.LoginActivity;
import com.yieryi.gladtohear.activities.UserSpaceActivity;
import com.yieryi.gladtohear.adapter.userspace.ListRecordAdapter;
import com.yieryi.gladtohear.base.TApplication;
import com.yieryi.gladtohear.bean.userspace.listrecord.Orders;
import com.yieryi.gladtohear.bean.userspace.listrecord.Root;
import com.yieryi.gladtohear.biz.userspace.listrecord.ListRecordBiz;
import com.yieryi.gladtohear.listener.AccumulateShopItemClickListener;
import com.yieryi.gladtohear.listener.RequestListener;
import com.yieryi.gladtohear.network.OkHttp;
import com.yieryi.gladtohear.view.LoadingDialog;
import java.io.IOException;
import java.util.List;
import cn.jpush.android.api.JPushInterface;
public class ExchangePrizeRecordFragment extends Fragment implements RequestListener,AccumulateShopItemClickListener {
    private final String TAG = ExchangePrizeRecordFragment.class.getSimpleName();
    private ListRecordBiz biz;
    private RecyclerView userspace_list_record_recycle;
    private LoadingDialog dialog;
    private List<Orders> list;
    private ListRecordAdapter adapter;
    private LinearLayoutManager manager;
    private RelativeLayout not_login;
    private TextView list_record_login;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    if (dialog!=null){
                        dialog.dismiss();
                    }
                    manager = new LinearLayoutManager(getActivity());
                    userspace_list_record_recycle.setLayoutManager(manager);
                    userspace_list_record_recycle.setAdapter(adapter);
                    break;
                case 1:
                    ((UserSpaceActivity)getActivity()).showToast("请求出错，请检查网络异常");
                    break;
            }
        }
    };
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!TextUtils.isEmpty(TApplication.user_id)) {
            if (dialog==null) {
                dialog = new LoadingDialog(getActivity(), R.style.dialog);
                dialog.show();
                biz = new ListRecordBiz();
                biz.getListRecord(String.valueOf(1),TApplication.user_id, JPushInterface.getRegistrationID(getActivity()),TAG,this);
            }
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == getActivity().RESULT_OK) {
            if (!TextUtils.isEmpty(TApplication.user_id)) {
                not_login.setVisibility(View.INVISIBLE);
                if (dialog == null) {
                    dialog = new LoadingDialog(getActivity(), R.style.dialog);
                    dialog.show();
                    biz = new ListRecordBiz();
                    biz.getListRecord(String.valueOf(1), TApplication.user_id, JPushInterface.getRegistrationID(getActivity()), TAG, ExchangePrizeRecordFragment.this);
                }
            }
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.userspace_list_record_fragment,container,false);
        userspace_list_record_recycle = (RecyclerView) view.findViewById(R.id.userspace_list_record_recycle);
        not_login = (RelativeLayout) view.findViewById(R.id.not_login);
        list_record_login = (TextView) view.findViewById(R.id.list_record_login);
        if (!TextUtils.isEmpty(TApplication.user_id)) {
            not_login.setVisibility(View.INVISIBLE);
        }else {
            list_record_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    intent.putExtra("login", true);
                    startActivityForResult(intent, 200);
                }
            });
        }
        return view;
    }

    @Override
    public void onResponse(Response response) {
        if (response.isSuccessful()) {
            Gson gson = new Gson();
//                String json = response.body().string();
            String json="{\n" +
                    "    \"status\": 0, \n" +
                    "    \"error\": \"\", \n" +
                    "    \"data\": {\n" +
                    "        \"count\": \"20\", \n" +
                    "        \"orders\": [\n" +
                    "            {\n" +
                    "                \"onumber\": \"201509238669\", \n" +
                    "                \"o_date\": \"2015-09-23 14:17:56\", \n" +
                    "                \"user_id\": \"543\", \n" +
                    "                \"t_price\": \"281.40\", \n" +
                    "                \"s_price\": \"-181.20\", \n" +
                    "                \"device_no\": \"00119a2730c\"\n" +
                    "            }, \n" +
                    "            {\n" +
                    "                \"onumber\": \"201509231341\", \n" +
                    "                \"o_date\": \"2015-09-23 14:15:51\", \n" +
                    "                \"user_id\": \"543\", \n" +
                    "                \"t_price\": \"224.40\", \n" +
                    "                \"s_price\": \"-182.40\", \n" +
                    "                \"device_no\": \"00119a2730c\"\n" +
                    "            }, \n" +
                    "            {\n" +
                    "                \"onumber\": \"201509235744\", \n" +
                    "                \"o_date\": \"2015-09-23 14:12:27\", \n" +
                    "                \"user_id\": \"543\", \n" +
                    "                \"t_price\": \"616.80\", \n" +
                    "                \"s_price\": \"-405.60\", \n" +
                    "                \"device_no\": \"00119a2730c\"\n" +
                    "            }, \n" +
                    "            {\n" +
                    "                \"onumber\": \"201509203797\", \n" +
                    "                \"o_date\": \"2015-09-20 10:24:32\", \n" +
                    "                \"user_id\": \"543\", \n" +
                    "                \"t_price\": \"577.20\", \n" +
                    "                \"s_price\": \"-514.20\", \n" +
                    "                \"device_no\": \"04140c56357\"\n" +
                    "            }, \n" +
                    "            {\n" +
                    "                \"onumber\": \"201509206337\", \n" +
                    "                \"o_date\": \"2015-09-20 00:12:31\", \n" +
                    "                \"user_id\": \"543\", \n" +
                    "                \"t_price\": \"347.40\", \n" +
                    "                \"s_price\": \"-236.10\", \n" +
                    "                \"device_no\": \"04140c56357\"\n" +
                    "            }, \n" +
                    "            {\n" +
                    "                \"onumber\": \"201509202150\", \n" +
                    "                \"o_date\": \"2015-09-20 00:10:18\", \n" +
                    "                \"user_id\": \"543\", \n" +
                    "                \"t_price\": \"12720.00\", \n" +
                    "                \"s_price\": \"-8322.60\", \n" +
                    "                \"device_no\": \"04140c56357\"\n" +
                    "            }, \n" +
                    "            {\n" +
                    "                \"onumber\": \"201509207209\", \n" +
                    "                \"o_date\": \"2015-09-20 00:08:04\", \n" +
                    "                \"user_id\": \"543\", \n" +
                    "                \"t_price\": \"3906.60\", \n" +
                    "                \"s_price\": \"-1865.10\", \n" +
                    "                \"device_no\": \"04140c56357\"\n" +
                    "            }, \n" +
                    "            {\n" +
                    "                \"onumber\": \"201509195339\", \n" +
                    "                \"o_date\": \"2015-09-19 21:41:42\", \n" +
                    "                \"user_id\": \"543\", \n" +
                    "                \"t_price\": \"200.00\", \n" +
                    "                \"s_price\": \"100.00\", \n" +
                    "                \"device_no\": \"04140c56357\"\n" +
                    "            }, \n" +
                    "            {\n" +
                    "                \"onumber\": \"201509194586\", \n" +
                    "                \"o_date\": \"2015-09-19 21:39:55\", \n" +
                    "                \"user_id\": \"543\", \n" +
                    "                \"t_price\": \"200.00\", \n" +
                    "                \"s_price\": \"100.00\", \n" +
                    "                \"device_no\": \"04140c56357\"\n" +
                    "            }, \n" +
                    "            {\n" +
                    "                \"onumber\": \"201509197751\", \n" +
                    "                \"o_date\": \"2015-09-19 21:34:52\", \n" +
                    "                \"user_id\": \"543\", \n" +
                    "                \"t_price\": \"200.00\", \n" +
                    "                \"s_price\": \"100.00\", \n" +
                    "                \"device_no\": \"04140c56357\"\n" +
                    "            }, \n" +
                    "            {\n" +
                    "                \"onumber\": \"201509193903\", \n" +
                    "                \"o_date\": \"2015-09-19 21:22:05\", \n" +
                    "                \"user_id\": \"543\", \n" +
                    "                \"t_price\": \"200.00\", \n" +
                    "                \"s_price\": \"100.00\", \n" +
                    "                \"device_no\": \"04140c56357\"\n" +
                    "            }, \n" +
                    "            {\n" +
                    "                \"onumber\": \"201509192774\", \n" +
                    "                \"o_date\": \"2015-09-19 16:43:07\", \n" +
                    "                \"user_id\": \"543\", \n" +
                    "                \"t_price\": \"200.00\", \n" +
                    "                \"s_price\": \"100.00\", \n" +
                    "                \"device_no\": \"04140c56357\"\n" +
                    "            }, \n" +
                    "            {\n" +
                    "                \"onumber\": \"201509199536\", \n" +
                    "                \"o_date\": \"2015-09-19 16:42:51\", \n" +
                    "                \"user_id\": \"543\", \n" +
                    "                \"t_price\": \"200.00\", \n" +
                    "                \"s_price\": \"100.00\", \n" +
                    "                \"device_no\": \"04140c56357\"\n" +
                    "            }, \n" +
                    "            {\n" +
                    "                \"onumber\": \"201509198142\", \n" +
                    "                \"o_date\": \"2015-09-19 16:42:40\", \n" +
                    "                \"user_id\": \"543\", \n" +
                    "                \"t_price\": \"200.00\", \n" +
                    "                \"s_price\": \"100.00\", \n" +
                    "                \"device_no\": \"04140c56357\"\n" +
                    "            }, \n" +
                    "            {\n" +
                    "                \"onumber\": \"201509199356\", \n" +
                    "                \"o_date\": \"2015-09-19 16:42:17\", \n" +
                    "                \"user_id\": \"543\", \n" +
                    "                \"t_price\": \"200.00\", \n" +
                    "                \"s_price\": \"100.00\", \n" +
                    "                \"device_no\": \"04140c56357\"\n" +
                    "            }\n" +
                    "        ]\n" +
                    "    }\n" +
                    "}";
            Root root = gson.fromJson(json, Root.class);
            if (root.getStatus() == OkHttp.NET_STATE) {
                list=root.getData().getOrders();
                adapter=new ListRecordAdapter(list,this);
                handler.sendEmptyMessage(0);
            }else {
                handler.sendEmptyMessage(1);
            }
        }
    }

    @Override
    public void onFailue(Request request, IOException e) {

    }

    @Override
    public void onItemClick(View view, String oNumber) {
        ((UserSpaceActivity)getActivity()).startActivity(getActivity(), ListRecordDetailActivity.class);
    }
}
