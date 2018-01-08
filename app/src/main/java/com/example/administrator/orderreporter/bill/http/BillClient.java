package com.example.administrator.orderreporter.bill.http;

import android.icu.text.IDNA;
import android.support.v4.util.ArrayMap;

import com.example.administrator.orderreporter.base.bean.InfoCache;
import com.example.administrator.orderreporter.net.ClientCallback;
import com.example.administrator.orderreporter.net.NetResultCallback;
import com.example.administrator.orderreporter.net.reportnet.NetStates;
import com.example.administrator.orderreporter.net.reportnet.ReportNet;
import com.example.administrator.orderreporter.utils.MD5;

import java.util.Map;

/**
 * Created by Administrator on 2017/12/27.
 */

public class BillClient {

    //订单列表
    private static final String get_list ="MGetOrderList";

    //订单列表
    public static void get_list(String page,final ClientCallback clientCallback) {
        Map<String, String> map = new ArrayMap<>();
        map.put(NetStates.USERID, InfoCache.userid);
        map.put(NetStates.VERIFY, InfoCache.verify);
        map.put(NetStates.DEVICEID,"1");
        map.put(NetStates.COMPID, InfoCache.compId);
        map.put(NetStates.LIMIT, "10");
        map.put(NetStates.PAGE, page);
        ReportNet.doPost(get_list, map, new NetResultCallback() {
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
