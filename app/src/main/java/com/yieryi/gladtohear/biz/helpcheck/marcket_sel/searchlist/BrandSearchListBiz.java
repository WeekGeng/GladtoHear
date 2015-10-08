package com.yieryi.gladtohear.biz.helpcheck.marcket_sel.searchlist;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.yieryi.gladtohear.constans.BaseConsts;
import com.yieryi.gladtohear.constans.CatlogConsts;
import com.yieryi.gladtohear.listener.RequestListener;
import com.yieryi.gladtohear.network.OkHttp;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/10/7 0007.
 */
public class BrandSearchListBiz implements IBrandSearchListBiz {
    private Map<String, String> paramas = new HashMap<>();
    @Override
    public void getSearchList(String keyword, String search_type, String page, String local,final RequestListener listener) {
        paramas.put(BaseConsts.APP, CatlogConsts.GetMarketSearchList.params_app);
        paramas.put(BaseConsts.CLASS, CatlogConsts.GetMarketSearchList.params_class);
        paramas.put(BaseConsts.SIGN, CatlogConsts.GetMarketSearchList.params_sign);
        paramas.put("keyword", keyword);
        paramas.put("search_type", search_type);
        paramas.put("page", page);
        paramas.put("local", local);
        OkHttp.asyncPost(BaseConsts.BASE_URL, paramas, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                listener.onFailue(request,e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                listener.onResponse(response);
            }
        });
    }
}
