package com.example.administrator.orderreporter.bill.bean;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/12/27.
 */

public class BillBean {
    private BigDecimal totalMoney;
    private ArrayList<Bill> yesList;
    private ArrayList<Bill> todayList;

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public ArrayList<Bill> getYesList() {
        return yesList;
    }

    public void setYesList(ArrayList<Bill> yesList) {
        this.yesList = yesList;
    }

    public ArrayList<Bill> getTodayList() {
        return todayList;
    }

    public void setTodayList(ArrayList<Bill> todayList) {
        this.todayList = todayList;
    }
}
