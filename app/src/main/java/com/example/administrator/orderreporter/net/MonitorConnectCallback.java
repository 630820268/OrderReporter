package com.example.administrator.orderreporter.net;

/**
 * Created by qianhechen on 16/10/24.
 */
public interface MonitorConnectCallback {
    void onSuccess(String str, int code);
    void onError(String str, int code);
}
