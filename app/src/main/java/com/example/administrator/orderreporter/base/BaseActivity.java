package com.example.administrator.orderreporter.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.administrator.orderreporter.R;
import com.zhy.autolayout.AutoLayoutActivity;

import java.lang.reflect.Field;


public class BaseActivity extends AutoLayoutActivity {

    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ReportApplication application = (ReportApplication) getApplication();
        application.addActivity(this);
    }

    public void showToast(String str) {
        if (toast == null) {
            toast = Toast.makeText(this.getApplicationContext(), str, Toast.LENGTH_SHORT);
            setToastTextSize(toast);
            toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText(str);
        }
        toast.show();
    }

    public void setTitle(String title) {
        ((TextView) findViewById(R.id.title_tv)).setText(title);
    }

    public void setTitle(String title, @Nullable View.OnClickListener leftListener, @Nullable View.OnClickListener rightListener) {
        setTitle(title);
        if (leftListener != null) {
            ImageView left = (ImageView) findViewById(R.id.title_left_iv);
            left.setVisibility(View.VISIBLE);
            left.setOnClickListener(leftListener);
        }
//        if (rightListener != null) {
//            View right =  findViewById(R.id.head_view_right);
//            right.setVisibility(View.VISIBLE);
//            right.setOnClickListener(rightListener);
//        }
    }

    public void showToast(@StringRes int strId) {
        if (toast == null) {
            toast = Toast.makeText(this.getApplicationContext(), strId, Toast.LENGTH_SHORT);
            setToastTextSize(toast);
            toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText(strId);
        }
        toast.show();
    }

    private void setToastTextSize(Toast toast) {
        try {
            Field f = toast.getClass().getDeclaredField("mNextView");
            f.setAccessible(true);
            ViewGroup view = (ViewGroup) f.get(toast);
            if (view == null) {
                return;
            }
            TextView tv = (TextView) view.getChildAt(0);
            if (tv == null) {
                return;
            }
            tv.setTextSize(50);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }


}
