package com.example.administrator.orderreporter.net;

public interface NetResultCallback {
    void onSuccess(String str);
    void onFail(int code, String error);


}
