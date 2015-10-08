package com.yieryi.gladtohear.biz.maininformation;
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
public class InformationDetailBiz implements IInfomationDetailBiz {
    private Map<String, String> paramas = new HashMap<>();
    @Override
    public void getDetailInformation(String content_id, String userid, String device_type, String tag,final RequestListener listener) {
        paramas.put(BaseConsts.APP, CatlogConsts.InformationDetail.params_app);
        paramas.put(BaseConsts.CLASS, CatlogConsts.InformationDetail.params_class);
        paramas.put(BaseConsts.SIGN, CatlogConsts.InformationDetail.params_sign);
        paramas.put("content_id", content_id);
        paramas.put("userid", userid);
        paramas.put("device_type", device_type);
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
