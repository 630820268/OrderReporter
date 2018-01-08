package com.example.administrator.orderreporter.bill.http;

import android.support.v4.util.ArrayMap;

import com.example.administrator.orderreporter.base.bean.InfoCache;
import com.example.administrator.orderreporter.net.ClientCallback;
import com.example.administrator.orderreporter.net.NetResultCallback;
import com.example.administrator.orderreporter.net.reportnet.NetStates;
import com.example.administrator.orderreporter.net.reportnet.ReportNet;

import java.util.Map;

/**
 * Created by Administrator on 2017/12/28.
 */

public class QueryClient {

    //搜索订单
    private static final String get_search ="MSearchOrderList";

    //搜索订单
    public static void get_search(String page,String begin,String end,final ClientCallback clientCallback) {
        Map<String, String> map = new ArrayMap<>();
        map.put(NetStates.USERID, InfoCache.userid);
        map.put(NetStates.VERIFY, InfoCache.verify);
        map.put(NetStates.DEVICEID,"1");
        map.put(NetStates.COMPID, InfoCache.compId);
        map.put(NetStates.LIMIT, "10");
        map.put(NetStates.PAGE, page);
        map.put(NetStates.BEGINTIME,begin);
        map.put(NetStates.ENDTIME,end);
        ReportNet.doPost(get_search, map, new NetResultCallback() {
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
