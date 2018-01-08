package com.example.administrator.orderreporter.login.http;

import android.support.v4.util.ArrayMap;

import com.example.administrator.orderreporter.net.ClientCallback;
import com.example.administrator.orderreporter.net.NetResultCallback;
import com.example.administrator.orderreporter.net.reportnet.NetStates;
import com.example.administrator.orderreporter.net.reportnet.ReportNet;
import com.example.administrator.orderreporter.utils.MD5;

import java.util.Map;

/**
 * Created by Administrator on 2017/12/27.
 */

public class LoginClient {
    //登录
    private static final String get_login ="MLogin ";

    //登录
    public static void get_login(String phone,String psw,final ClientCallback clientCallback) {
        Map<String, String> map = new ArrayMap<>();
        map.put(NetStates.PHONE,phone);
        map.put(NetStates.PASSWORD, MD5.md5(psw));
        map.put(NetStates.DEVICE,"android");
        map.put(NetStates.DEVICEID,"1");
        ReportNet.doPost(get_login, map, new NetResultCallback() {
            @Override
            public void onSuccess(String str) {
//                ProductInfos productInfos = JSON.parseObject(str, ProductInfos.class);

                clientCallback.onSuccess(str);


            }

            @Override
            public void onFail(int code, String error) {
                clientCallback.onFail(code, error);
            }
        });
    }
}
