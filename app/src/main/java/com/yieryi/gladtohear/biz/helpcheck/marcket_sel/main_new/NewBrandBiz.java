package com.yieryi.gladtohear.biz.helpcheck.marcket_sel.main_new;
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
 * main 品牌新品
 */
public class NewBrandBiz implements INewBrandBiz {
    private Map<String, String> paramas = new HashMap<>();
    @Override
    public void getMacketList(int page, int userId, int city_id, String keyword, String zixun_category, final RequestListener listener) {
        paramas.put(BaseConsts.APP, CatlogConsts.Brand.params_app);
        paramas.put(BaseConsts.CLASS, CatlogConsts.Brand.params_class);
        paramas.put(BaseConsts.SIGN, CatlogConsts.Brand.params_sign);
        paramas.put("page",String.valueOf(page));
        paramas.put("userid",String.valueOf(userId));
        paramas.put("city_id",String.valueOf(city_id));
        paramas.put("keyword",keyword);
        paramas.put("zixun_category",zixun_category);
        OkHttp.asyncPost(BaseConsts.BASE_URL, paramas, new Callback() {
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
    public void getMacketList(String zixun_category,String tag,final RequestListener listener) {
        paramas.put(BaseConsts.APP, CatlogConsts.Brand.params_app);
        paramas.put(BaseConsts.CLASS, CatlogConsts.Brand.params_class);
        paramas.put(BaseConsts.SIGN, CatlogConsts.Brand.params_sign);
        paramas.put("zixun_category",zixun_category);
        OkHttp.asyncPost(BaseConsts.BASE_URL, paramas,tag, new Callback() {
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
