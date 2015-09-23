package com.yieryi.gladtohear.fragment.main.userspace;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.yieryi.gladtohear.R;
import com.yieryi.gladtohear.activities.BasicInformationActivity;
import com.yieryi.gladtohear.activities.UserSpaceActivity;
import com.yieryi.gladtohear.listener.RequestListener;

import java.io.IOException;

/**
 * Created by Administrator on 2015/9/17 0017.
 */
public class PrizeExchangeFragment extends Fragment implements RequestListener{
    private EditText prize_exchange_code_ed;
    private TextView prize_exchange_commit_tv;
    private String input_code;
    private String json_code="abcd";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.prize_exchange_fragment,container,false);
        initView(view);
        setListener();
        return view;
    }
    private void setListener() {
        prize_exchange_commit_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input_code=prize_exchange_code_ed.getText().toString();
                if (TextUtils.isEmpty(input_code)){
                    ((UserSpaceActivity)getActivity()).showToast("兑换码不能为空");
                    return;
                }
                if (input_code.equals(json_code)) {
                    ((UserSpaceActivity) getActivity()).startActivity(getActivity(), BasicInformationActivity.class);
                }else {
                    ((UserSpaceActivity)getActivity()).showToast("兑换码错误");
                }
            }
        });
    }
    private void initView(View view) {
        prize_exchange_commit_tv=(TextView)view.findViewById(R.id.prize_exchange_commit_tv);
        prize_exchange_code_ed=(EditText)view.findViewById(R.id.prize_exchange_code_ed);
    }
    @Override
    public void onResponse(Response response) {
        if (response.isSuccessful()){
            Gson gson=new Gson();
            try {
                String json=response.body().string();
            } catch (IOException e) {

            }
        }
    }

    @Override
    public void onFailue(Request request, IOException e) {

    }
}
