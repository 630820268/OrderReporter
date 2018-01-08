package com.example.administrator.orderreporter.bill.presenter;

import com.example.administrator.orderreporter.base.IBasePresenter;
import com.example.administrator.orderreporter.base.IBaseView;
import com.example.administrator.orderreporter.bill.bean.QueryBean;

/**
 * Created by Administrator on 2017/12/28.
 */

public interface IQueryContract {
    interface QueryView extends IBaseView{
        void showSearch(QueryBean queryBean);
    }

    interface QueryPresenter extends IBasePresenter{
        void get_search(String page,String begin,String end);
    }
}
