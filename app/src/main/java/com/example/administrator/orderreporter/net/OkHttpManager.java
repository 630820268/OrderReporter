package com.example.administrator.orderreporter.net;


import com.example.administrator.orderreporter.net.reportnet.ReportNet;
import com.example.administrator.orderreporter.utils.LogUtils;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 功能：
 */
public class OkHttpManager implements IHttpManager {
    private static OkHttpManager instance;
    private OkHttpClient httpClient;

    private OkHttpManager() {
        httpClient = new OkHttpClient.Builder().readTimeout(20000, TimeUnit.MILLISECONDS)//设置读取超时时间
                .writeTimeout(20000, TimeUnit.MILLISECONDS)//设置写的超时时间
                .connectTimeout(20000, TimeUnit.MILLISECONDS)//设置连接超时时间
                .build();
    }

    public static OkHttpManager getInstance() {
        if (instance == null) {
            synchronized (OkHttpManager.class) {
                if (instance == null) {
                    instance = new OkHttpManager();
                }
            }
        }
        return instance;
    }


    @Override
    public void doPost(final String urlStr, final Map<String, String> map, final IHttpCallback callback) {

        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                RequestBody requestBody = buildBody(map);

                Request request = new Request.Builder().url(urlStr).post(requestBody).build();
                httpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        subscriber.onError(e);
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.code() == 200) {
                            String responseStr = response.body().string();
                            subscriber.onNext(responseStr);
                        } else {
                            subscriber.onNext("error:" + response.code());
                        }
                        subscriber.onCompleted();
                    }
                });
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.Debug_E(OkHttpManager.class,e.getMessage()+","+e.getCause()+","+e+","+e);
                        try {
                            if (e.getCause() instanceof SocketTimeoutException) {
                                callback.onFail(ConnectStates.TIME_OUT, "net connect time out!");
                            } else {
                                if(urlStr.equals(ReportNet.MASTER_SERVICE+"MQrPay")){
                                    if(e.getMessage() == null){
                                        callback.onFail(ConnectStates.REPOST,"");
                                    }
                                }else{
                                    callback.onFail(ConnectStates.NET_ERROR, e.getMessage() == null ? "net error" : e.getMessage());
                                }
                            }
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }

                    @Override
                    public void onNext(String s) {
                        try {
                            if (s.startsWith("error")) {
                                callback.onFail(Integer.parseInt(s.split(":")[1]), parseCode(Integer.parseInt(s.split(":")[1])));
                            } else {
                                callback.onSuccess(s);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });


    }

    @Override
    public void doHead(final String urlStr) {
    }

    private RequestBody buildBody(Map<String, String> map) {
        FormBody.Builder formBody = new FormBody.Builder();
        if (map == null || map.size() == 0) {
            return formBody.build();
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            formBody.add(entry.getKey(), entry.getValue());
        }
        return formBody.build();
    }

    private String parseCode(int code) {
        if (code < 200 || code > 599) {
            return "未知异常";
        } else if (code > 399 && code < 600) {
            return "连接服务器异常";
        } else {
            return "服务器响应异常";
        }
    }

}
