package com.example.administrator.orderreporter.login.presenter;

import com.alibaba.fastjson.JSON;
import com.example.administrator.orderreporter.base.bean.UserInfo;
import com.example.administrator.orderreporter.login.http.LoginClient;
import com.example.administrator.orderreporter.net.ClientCallback;
import com.example.administrator.orderreporter.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/12/27.
 */

public class LoginPresenter implements ILoginContract.LoginPresenter {

    private ILoginContract.LoginView loginView;

    public LoginPresenter(ILoginContract.LoginView loginView) {
        this.loginView = loginView;
    }

    @Override
    public void unBind() {
        loginView = null;
    }

    @Override
    public void get_login(String phone, String psw) {
        LoginClient.get_login(phone,psw,new ClientCallback() {
            @Override
            public void onSuccess(Object o) {
                LogUtils.Debug_E(LoginPresenter.class,":"+o);
                try {
                    JSONObject jsonObject = new JSONObject(o.toString());
                    UserInfo userInfo = JSON.parseObject(jsonObject.get("result").toString(),UserInfo.class);
                    loginView.login_Success(userInfo);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(int code, String error) {
                loginView.login_err(error);
            }
        });
    }
}
