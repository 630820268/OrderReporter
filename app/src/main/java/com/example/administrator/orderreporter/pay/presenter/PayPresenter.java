package com.example.administrator.orderreporter.pay.presenter;

import com.alibaba.fastjson.JSON;
import com.example.administrator.orderreporter.net.ClientCallback;
import com.example.administrator.orderreporter.net.ConnectStates;
import com.example.administrator.orderreporter.net.reportnet.NetStates;
import com.example.administrator.orderreporter.pay.bean.PayBean;
import com.example.administrator.orderreporter.pay.http.PayClient;
import com.example.administrator.orderreporter.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/12/28.
 */

public class PayPresenter implements IPayContract.PayPresenter{

    private IPayContract.PayView payView;

    public PayPresenter(IPayContract.PayView payView) {
        this.payView = payView;
    }

    @Override
    public void unBind() {
        payView = null;
    }

    @Override
    public void get_pay(String money,String authCode, String type) {
        PayClient.get_pay(money,authCode,type, new ClientCallback() {
            @Override
            public void onSuccess(Object o) {
                LogUtils.Debug_E(PayPresenter.class,"pay:"+o);

                try {
                    JSONObject jsonObject = new JSONObject(o.toString());
                    String code = jsonObject.getString(NetStates.CODE);
                    LogUtils.Debug_E(PayPresenter.class,"pay:::"+code);
                    if(code.equals("1")){
                        PayBean payBean = JSON.parseObject(jsonObject.getString("result"),PayBean.class);
                        LogUtils.Debug_E(PayPresenter.class,"pay::::::"+payBean);
                        payView.paySuccess(payBean);
                    }else{
                        payView.payErr();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFail(int code, String error) {
                LogUtils.Debug_E(PayPresenter.class,"error:"+code+","+error);
                if(code == ConnectStates.REPOST){
                    payView.payRepost();
                }else{
                    payView.payErr();
                }
            }
        });
    }
}
