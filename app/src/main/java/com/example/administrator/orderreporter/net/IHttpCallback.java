package com.example.administrator.orderreporter.net;

/**
 * 功能：
 */
public interface IHttpCallback {

    void onSuccess(String resultStr);
    void onFail(int code, String message);

}
