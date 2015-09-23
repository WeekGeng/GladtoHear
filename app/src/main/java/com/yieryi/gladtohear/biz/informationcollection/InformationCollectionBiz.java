package com.yieryi.gladtohear.biz.informationcollection;

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
 *
 */
public class InformationCollectionBiz implements IInformationCollectionBiz {
    private Map<String, String> paramas = new HashMap<>();
    @Override
    public void getInformation(int city_id, int category_id, int page, int perPage, String tag,final RequestListener listener) {
        paramas.put(BaseConsts.APP, CatlogConsts.GetInfomation.params_app);
        paramas.put(BaseConsts.CLASS, CatlogConsts.GetInfomation.params_class);
        paramas.put(BaseConsts.SIGN, CatlogConsts.GetInfomation.params_sign);
        paramas.put("city_id", String.valueOf(city_id));
        paramas.put("category_id", String.valueOf(category_id));
        paramas.put("page",String.valueOf(page));
        paramas.put("perpage",String.valueOf(perPage));
        OkHttp.asyncPost(BaseConsts.BASE_URL, paramas,tag, new Callback() {
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
