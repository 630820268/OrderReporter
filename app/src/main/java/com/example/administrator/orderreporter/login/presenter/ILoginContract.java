package com.example.administrator.orderreporter.login.presenter;

import com.example.administrator.orderreporter.base.IBasePresenter;
import com.example.administrator.orderreporter.base.IBaseView;
import com.example.administrator.orderreporter.base.bean.UserInfo;

/**
 * Created by Administrator on 2017/12/27.
 */

public interface ILoginContract {

    interface LoginView extends IBaseView{
        void login_Success(UserInfo userInfo);

        void login_err(String error);
    }

    interface LoginPresenter extends IBasePresenter{
        void get_login(String phone,String psw);
    }
}
