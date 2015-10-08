package com.yieryi.gladtohear.biz.calculate;
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
public class CommodityBiz implements ICommondityBiz {
    private Map<String,String> paramas = new HashMap<>();
    @Override
    public void getCommodityList(int parentId, String shop_id, String tag,final RequestListener listener) {
        paramas.put(BaseConsts.APP, CatlogConsts.GetCommodity.params_app);
        paramas.put(BaseConsts.CLASS, CatlogConsts.GetCommodity.params_class);
        paramas.put(BaseConsts.SIGN, CatlogConsts.GetCommodity.params_sign);
        paramas.put("parent_id", String.valueOf(parentId));
        paramas.put("shop_id", shop_id);
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
