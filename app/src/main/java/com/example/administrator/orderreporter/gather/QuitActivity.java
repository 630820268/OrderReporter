package com.example.administrator.orderreporter.gather;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.orderreporter.R;
import com.example.administrator.orderreporter.base.BaseActivity;
import com.example.administrator.orderreporter.base.ReportApplication;
import com.example.administrator.orderreporter.base.bean.InfoCache;
import com.example.administrator.orderreporter.login.LoginActivity;
import com.example.administrator.orderreporter.utils.Utils;

public class QuitActivity extends BaseActivity {

    private TextView quit_tv,quit_name_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quit);
        setTitle("退出登录", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        },null);
        quit_tv = (TextView) findViewById(R.id.quit_tv);
        quit_name_tv = (TextView) findViewById(R.id.quit_name_tv);
        quit_name_tv.setText(InfoCache.account);
        quit_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.clearUser(QuitActivity.this);
                Intent intent_login = new Intent(QuitActivity.this, LoginActivity.class);
                startActivity(intent_login);
                ReportApplication.removeActivities();
                finish();
            }
        });
    }
}
