package com.example.administrator.orderreporter.pay;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.orderreporter.R;
import com.example.administrator.orderreporter.base.BaseActivity;
import com.example.administrator.orderreporter.bill.QueryActivity;

public class PayErrActivity extends BaseActivity implements View.OnClickListener {

    private TextView err_sure_tv,err_query_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_err);

        err_sure_tv = (TextView) findViewById(R.id.err_sure_tv);
        err_query_tv = (TextView) findViewById(R.id.err_query_tv);
        err_sure_tv.setOnClickListener(this);
        err_query_tv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.err_sure_tv:
                finish();
                break;
            case R.id.err_query_tv:
                Intent intent_query = new Intent(PayErrActivity.this, QueryActivity.class);
                startActivity(intent_query);
                finish();
                break;

        }
    }
}
