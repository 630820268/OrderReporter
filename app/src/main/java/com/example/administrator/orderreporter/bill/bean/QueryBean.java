package com.example.administrator.orderreporter.bill.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/12/28.
 */

public class QueryBean {
    private ArrayList<Bill> list;
    private String totalMoney;

    public ArrayList<Bill> getList() {
        return list;
    }

    public void setList(ArrayList<Bill> list) {
        this.list = list;
    }

    public String getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }
}
