package com.yieryi.gladtohear.fragment.main.userspace;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.yieryi.gladtohear.R;
import com.yieryi.gladtohear.listener.RequestListener;

import java.io.IOException;

/**
 * Created by Administrator on 2015/9/17 0017.
 */
public class ListRecordFragment extends Fragment implements RequestListener{
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.userspace_list_record_fragment,container,false);

        return view;
    }

    @Override
    public void onResponse(Response response) {

    }

    @Override
    public void onFailue(Request request, IOException e) {

    }
}
