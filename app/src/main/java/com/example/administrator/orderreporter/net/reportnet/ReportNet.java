package com.example.administrator.orderreporter.net.reportnet;

import com.alibaba.fastjson.JSON;
import com.example.administrator.orderreporter.net.ConnectStates;
import com.example.administrator.orderreporter.net.IHttpCallback;
import com.example.administrator.orderreporter.net.IHttpManager;
import com.example.administrator.orderreporter.net.NetResultCallback;
import com.example.administrator.orderreporter.net.OkHttpManager;
import com.example.administrator.orderreporter.utils.LogUtils;

import java.util.Map;

/**
 * Created by Administrator on 2017/12/27.
 */

public class ReportNet {
    //本地
//    public static final String MASTER_SERVICE ="http://test.xiaozhubiji.com/sj_v2/mobile?methodno=";
    //外网
    public static final String MASTER_SERVICE =" http://101.132.105.4/sj_v2/mobile?methodno=";

    private static long sysTime = 0;

    private static String cacheUrl = "";


    public static void doPost(final String url, Map<String, String> map, final NetResultCallback callback) {
        if (url == null || url.trim().equals("") || map == null) {
            throw new IllegalArgumentException("url or map can not be null");
        }
//        if (url.equals(cacheUrl) && sysTime != 0 && System.currentTimeMillis() - sysTime < 500) {
//            return;
//        }
        cacheUrl = url;
        sysTime = System.currentTimeMillis();
        //       Map<String, String> mapData = new ArrayMap<>();
//        mapData.put(BingNetStates.CUSTOME_CODE, "bsj123456");
//        mapData.put(BingNetStates.BIZ_SOURCE, "123456");
//        mapData.put(BingNetStates.TIMESTAMP, Util.getTimeBIZService());
//        mapData.put(BingNetStates.METHOD, map.get(BingNetStates.METHOD));
//        if (map.get(BingNetStates.REQUEST_DATA) != null) {
//            mapData.put(BingNetStates.REQUEST_DATA, map.get(BingNetStates.REQUEST_DATA));
//        }
//        mapData.put(BingNetStates.SIGN, getSign(mapData));
//        LogUtils.Debug_E(BingstarNet.class, mapData.toString());
        IHttpManager httpManager = OkHttpManager.getInstance();
        LogUtils.Debug_E(ReportNet.class, "update:" +MASTER_SERVICE + url+","+map.toString() );
        httpManager.doPost(MASTER_SERVICE + url, map, new IHttpCallback() {

            @Override
            public void onSuccess(String resultStr) {

                LogUtils.Debug_E(ReportNet.class, "response:" + resultStr);
                ReportResponse response = JSON.parseObject(resultStr, ReportResponse.class);

                if (callback == null) {
                    return;
                }
                if (response==null){
                    callback.onFail(ConnectStates.NET_ERROR,"");
                    return;
                }
                if (response.getErrorCode()==0) {
                    callback.onSuccess(response.getData());
                }
                else {
                    callback.onFail(response.getErrorCode(), response.getErrorMsg());
                }
            }

            @Override
            public void onFail(int code, String message) {
                if (callback != null) {
                    callback.onFail(code, message);
                }
            }



        });
    }
}
