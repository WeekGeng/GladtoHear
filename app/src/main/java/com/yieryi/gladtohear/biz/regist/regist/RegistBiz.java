package com.yieryi.gladtohear.biz.regist.regist;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.yieryi.gladtohear.constans.BaseConsts;
import com.yieryi.gladtohear.constans.CatlogConsts;
import com.yieryi.gladtohear.listener.RegistResponse;
import com.yieryi.gladtohear.network.OkHttp;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/9/23 0023.
 */
public class RegistBiz implements IRegistBiz {
    private Map<String, String> paramas = new HashMap<>();
    @Override
    public void regist(String username, String password, String again_password, String phone_num, String code, final RegistResponse registResponse) {
        paramas.put(BaseConsts.APP, CatlogConsts.Regist.params_app);
        paramas.put(BaseConsts.CLASS, CatlogConsts.Regist.params_class);
        paramas.put(BaseConsts.SIGN, CatlogConsts.Regist.params_sign);
        paramas.put("username", username);
        paramas.put("password", password);
        paramas.put("password1",again_password);
        paramas.put("phone",phone_num);
        paramas.put("code", code);
        OkHttp.asyncPost(BaseConsts.BASE_URL, paramas, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                registResponse.onFailueRequess(request, e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                registResponse.onResponseSuccess(response);
            }
        });
    }
}
