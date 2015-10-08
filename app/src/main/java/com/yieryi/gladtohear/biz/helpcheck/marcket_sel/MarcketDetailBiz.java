package com.yieryi.gladtohear.biz.helpcheck.marcket_sel;
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
public class MarcketDetailBiz implements IMarcketDetail {
    private Map<String, String> paramas = new HashMap<>();
    @Override
    public void getMacketList(String keyword, String search_type, String page, String local, String pid, String tag,final RequestListener listener) {
        paramas.put(BaseConsts.APP, CatlogConsts.GetMarketSearchList.params_app);
        paramas.put(BaseConsts.CLASS, CatlogConsts.GetMarketSearchList.params_class);
        paramas.put(BaseConsts.SIGN,"bb28a82b860ca10c420934463d267666");
        paramas.put("keyword", keyword);
        paramas.put("search_type", search_type);
        paramas.put("page", page);
        paramas.put("local", local);
        paramas.put("pid", pid);
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
