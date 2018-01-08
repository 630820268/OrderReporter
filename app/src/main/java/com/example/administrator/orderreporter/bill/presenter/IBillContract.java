package com.example.administrator.orderreporter.bill.presenter;

import com.example.administrator.orderreporter.base.IBasePresenter;
import com.example.administrator.orderreporter.base.IBaseView;
import com.example.administrator.orderreporter.bill.bean.BillBean;

/**
 * Created by Administrator on 2017/12/27.
 */

public interface IBillContract {

    interface BillView extends IBaseView{
        void show_list(BillBean billBean);
    }

    interface BillPresebter extends IBasePresenter{
        void get_list(String pageNum);
    }
}
