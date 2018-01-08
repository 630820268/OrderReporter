package com.example.administrator.orderreporter.pay.http;

import android.support.v4.util.ArrayMap;

import com.example.administrator.orderreporter.base.bean.InfoCache;
import com.example.administrator.orderreporter.net.ClientCallback;
import com.example.administrator.orderreporter.net.NetResultCallback;
import com.example.administrator.orderreporter.net.reportnet.NetStates;
import com.example.administrator.orderreporter.net.reportnet.ReportNet;
import com.example.administrator.orderreporter.utils.MD5;

import java.util.Map;

/**
 * Created by Administrator on 2017/12/28.
 */

public class PayClient {
    //支付
   private static final String get_pay ="MQrPay";
    //MToAliPay
    //private static final String get_pay ="MToAliPay";

    //支付
        public static void get_pay(String money,String authCode,String type,final ClientCallback clientCallback) {
        Map<String, String> map = new ArrayMap<>();
        map.put(NetStates.USERID, InfoCache.userid);
        map.put(NetStates.VERIFY, InfoCache.verify);
        map.put(NetStates.DEVICEID,"1");
        map.put(NetStates.COMPID, InfoCache.compId);
        map.put(NetStates.BODY, InfoCache.compName);
        map.put(NetStates.MONEY, money);
        map.put(NetStates.AUTHCODE,authCode);
        map.put(NetStates.TYPE, type);
        ReportNet.doPost(get_pay, map, new NetResultCallback() {
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
