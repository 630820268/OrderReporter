package com.example.administrator.orderreporter.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/2.
 */

public class ReportApplication extends Application {

    private static List<Activity> activities ;
    private Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        activities = new ArrayList<>();
    }

    public static void addActivity(Activity activity){
        activities.add(activity);
    }

    public static void removeFromList(Activity activity){
        activities.remove(activity);
    }

    public static void removeActivities(){
        for (Activity activity:activities){
            activity.finish();
        }
    }

    public static int listSize(){
        return activities.size();
    }
}
