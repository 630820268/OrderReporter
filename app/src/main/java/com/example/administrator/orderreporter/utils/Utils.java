package com.example.administrator.orderreporter.utils;

import android.content.Context;
import android.content.SharedPreferences;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/11/6.
 */

public class Utils {

    private final static boolean DEBUG = true;
    public final static String USER = "user";

    public final static String LOGIN_USERCODE = "userCode";
    public final static String LOGIN_MOBILE = "mobile";
    public final static String LOGIN_PASSWORD = "password";
    public final static String USERID = "userid";
    public final static String VERIFY = "verify";
    public final static String COMPID = "compId";
    public final static String COMPANYNAME = "companyName";
    public final static String VOICE_SET = "voice";
    public final static String PRINT_SET = "print";

    public static boolean isPhoneNumberValid(String phoneNumber) {
//        boolean isValid = false;
//
//        String expression = "((^(13|15|18)[0-9]{9}$)|(^0[1,2]{1}\\d{1}-?\\d{8}$)|(^0[3-9] {1}\\d{2}-?\\d{7,8}$)|(^0[1,2]{1}\\d{1}-?\\d{8}-(\\d{1,4})$)|(^0[3-9]{1}\\d{2}-? \\d{7,8}-(\\d{1,4})$))";
//        CharSequence inputStr = phoneNumber;
//
//        Pattern pattern = Pattern.compile(expression);
//
//        Matcher matcher = pattern.matcher(inputStr);
//
//        if (matcher.matches() ) {
//            isValid = true;
//        }
//
//        return isValid;

        Pattern p = Pattern
                .compile("^(0|86|17951)?(13[0-9]|15[0-9]|17[0-9]|18[0-9]|14[0-9])[0-9]{8}$");

        Matcher m = p.matcher(phoneNumber);

        return m.matches();

    }


//    public static void saveUser(Context context, UserInfo info) {
//        SharedPreferences.Editor editor = context.getSharedPreferences(Utils.USER, Context.MODE_PRIVATE).edit();
//        editor.putString(Utils.LOGIN_USERCODE,info.getUserCode());
//        editor.putString(Utils.LOGIN_MOBILE, info.getMobile());
//        //editor.putString(Utils.LOGIN_PASSWORD, password);
//        editor.putString(Utils.LOGIN_TEAMCODE, info.getTeamCode());
//        InfoCache.userCode = info.getUserCode();
//        InfoCache.mobile = info.getMobile();
//        InfoCache.teamCode = info.getTeamCode();
//        editor.apply();
//    }

    public static void getUser(Context context) {
        // UserInfo info = new UserInfo();
        SharedPreferences preferences = context.getSharedPreferences(USER, Context.MODE_PRIVATE);
//        InfoCache.accessToken ="xNuhT6fgWYNyb0hTBvZRJWfZwT_hPbez";
//        InfoCache.userCode = "1508892566917001";
//        InfoCache.accessToken =preferences.getString(LOGIN_ACCESSTOKEN,"");
//        InfoCache.userable =preferences.getInt(LOGIN_USEABLE,0);
//        InfoCache.createTime =preferences.getString(CREATETIME,"");
//        InfoCache.userCode =preferences.getString(LOGIN_USERCODE,"");
//        InfoCache.mobile = preferences.getString(LOGIN_MOBILE,"");
//        InfoCache.password = preferences.getString(LOGIN_PASSWORD,"");
//        InfoCache.savePassword = preferences.getBoolean(LOGIN_SAVE,false);
//        InfoCache.realName = preferences.getString(LOGIN_REALNAME,"");
    }

    public static void clearUser(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(USER, Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
    }
}
