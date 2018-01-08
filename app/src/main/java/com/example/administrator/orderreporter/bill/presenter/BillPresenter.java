package com.example.administrator.orderreporter.bill.presenter;

import com.alibaba.fastjson.JSON;
import com.example.administrator.orderreporter.base.IBasePresenter;
import com.example.administrator.orderreporter.bill.bean.Bill;
import com.example.administrator.orderreporter.bill.bean.BillBean;
import com.example.administrator.orderreporter.bill.http.BillClient;
import com.example.administrator.orderreporter.net.ClientCallback;
import com.example.administrator.orderreporter.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/12/27.
 */

public class BillPresenter implements IBillContract.BillPresebter {

    private IBillContract.BillView billView;

    public BillPresenter(IBillContract.BillView billView) {
        this.billView = billView;
    }

    @Override
    public void unBind() {
        billView = null;
    }


    @Override
    public void get_list(String pageNum) {
        BillClient.get_list(pageNum, new ClientCallback() {
            @Override
            public void onSuccess(Object o) {
                LogUtils.Debug_E(BillPresenter.class,":"+o);
                try {
                    JSONObject jsonObject = new JSONObject(o.toString());
                    BillBean billBean = JSON.parseObject(jsonObject.get("result").toString(),BillBean.class);
                    billView.show_list(billBean);
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
