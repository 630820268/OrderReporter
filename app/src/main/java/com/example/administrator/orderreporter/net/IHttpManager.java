package com.example.administrator.orderreporter.net;

import java.util.Map;

/**
 *
 */
public interface IHttpManager{

    /**
     * post请求
     * @param urlStr url
     * @param map 参数
     * @param callback 回调
     */
    void doPost(String urlStr, Map<String, String> map, IHttpCallback callback);

    void doHead(String urlStr);
}
