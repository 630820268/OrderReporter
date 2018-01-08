package com.example.administrator.orderreporter.utils;

import android.util.Log;

/**
 *
 */
public class LogUtils {

    private static final boolean DEBUG = true;
//    private static final boolean DEBUG = ;

    private static final boolean OPEN = true;

    public static void I(Class classes, String message){
        if (OPEN){
            Log.i(classes.getSimpleName(),message);
        }
    }

    public static void E(Class classes, String message){
        if (OPEN){
            Log.e(classes.getSimpleName(),message);
        }
    }

    public static void Debug_I(Class classes, String message){
        if (DEBUG){
            Log.i(classes.getSimpleName(),message);
        }
    }

    public static void Debug_E(Class classes, String message){
        if (DEBUG){
            Log.e(classes.getSimpleName(),message);
        }
//        else{
//            Log.e(classes.getSimpleName(),message);
//        }
    }
}
