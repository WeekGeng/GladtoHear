package com.yieryi.gladtohear.biz.regist.regist;
import com.yieryi.gladtohear.listener.RegistResponse;
public interface IRegistBiz {
    void regist(String username,String password,String again_password,String phone_num,String code,RegistResponse response);
}
