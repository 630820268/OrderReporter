package com.example.administrator.orderreporter.bill.presenter;

import com.alibaba.fastjson.JSON;
import com.example.administrator.orderreporter.bill.bean.QueryBean;
import com.example.administrator.orderreporter.bill.http.QueryClient;
import com.example.administrator.orderreporter.net.ClientCallback;
import com.example.administrator.orderreporter.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/12/28.
 */

public class QueryPresenter implements IQueryContract.QueryPresenter {

    private IQueryContract.QueryView queryView;

    public QueryPresenter(IQueryContract.QueryView queryView) {
        this.queryView = queryView;
    }

    @Override
    public void unBind() {
        queryView = null;
    }

    @Override
    public void get_search(String page, String begin, String end) {
        QueryClient.get_search(page, begin, end, new ClientCallback() {
            @Override
            public void onSuccess(Object o) {
                LogUtils.Debug_E(QueryPresenter.class,"query:"+o);
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(o.toString());
                    QueryBean queryBean = JSON.parseObject(jsonObject.get("result").toString(),QueryBean.class);
                    queryView.showSearch(queryBean);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFail(int code, String error) {

            }
        });
    }
}
