package com.example.administrator.orderreporter.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/12/28.
 */

public class DateUtils {

    public static String getDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate=new Date(System.currentTimeMillis());//获取当前时间
        String str=formatter.format(curDate);
        return str;
    }

    public static String getMonth(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        Date curDate=new Date(System.currentTimeMillis());
        String str=formatter.format(curDate);
        return str;
    }

    public static String getTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }
}
