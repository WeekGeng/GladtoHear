package com.yieryi.gladtohear.biz.helpcheck.marcket_sel.brand_sel;

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
 * 品类选择
 */
public class BrandSelBiz implements IBrandSelBiz {
    /**
     * 获取品牌或者分类的热门选择
     *
     * @param search_type
     */
    private Map<String, String> paramas = new HashMap<>();
    @Override
    public void getHotSearch(int search_type,String tag,final RequestListener listener) {
        paramas.put(BaseConsts.APP, CatlogConsts.HelpCheckHot.params_app);
        paramas.put(BaseConsts.CLASS, CatlogConsts.HelpCheckHot.params_class);
        paramas.put(BaseConsts.SIGN, CatlogConsts.HelpCheckHot.params_sign);
        paramas.put("search_type", String.valueOf(search_type));
        OkHttp.asyncPost(BaseConsts.BASE_URL, paramas, tag, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                listener.onFailue(request, e);
            }

            @Override
            public void onResponse(Response response) {
                listener.onResponse(response);
            }
        });
    }
}
