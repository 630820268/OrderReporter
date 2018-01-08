package com.example.administrator.orderreporter.net;

/**
 * 功能：
 */
public interface ClientCallback<T>{
    void onSuccess(T t);

    void onFail(int code, String error);
}
