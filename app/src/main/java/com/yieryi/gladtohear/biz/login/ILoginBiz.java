package com.yieryi.gladtohear.biz.login;

import com.yieryi.gladtohear.listener.RequestListener;

/**
 * Created by Administrator on 2015/9/23 0023.
 */
public interface ILoginBiz {
    void login(String username,String password,String device_no,RequestListener listener);
}
