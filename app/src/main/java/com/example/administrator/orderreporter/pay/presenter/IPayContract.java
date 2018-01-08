package com.example.administrator.orderreporter.pay.presenter;

import com.example.administrator.orderreporter.base.IBasePresenter;
import com.example.administrator.orderreporter.base.IBaseView;
import com.example.administrator.orderreporter.pay.bean.PayBean;

/**
 * Created by Administrator on 2017/12/28.
 */

public interface IPayContract {
    interface PayView extends IBaseView {
        void paySuccess(PayBean payBean);
        void payErr();
        void payRepost();
    }

    interface PayPresenter extends IBasePresenter {
        void get_pay(String money,String authCode,String type);
    }

}