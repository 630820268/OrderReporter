package com.example.administrator.orderreporter.base;

import android.support.annotation.StringRes;

/**
 * Created by JINAN on 2017/10/12.
 */

public interface IBaseView {

    /**
     * 设置Presenter
     */
    void setPresenter();

    /**
     * 弹出toast
     * @param str str
     */
    void showToast(String str);


    /**
     * 显示Toast
     * @param strId 资源ID
     */
    void showToast(@StringRes int strId);

//    void onViewError();
}
