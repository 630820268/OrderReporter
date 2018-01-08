package com.example.administrator.orderreporter.gather;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.example.administrator.orderreporter.R;
import com.example.administrator.orderreporter.base.BaseActivity;
import com.example.administrator.orderreporter.base.bean.IntentStates;
import com.example.administrator.orderreporter.bill.BillActivity;
import com.example.administrator.orderreporter.pay.PayActivity;

public class GatherActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout gather_zfb_linear,gather_wx_linear,gather_bill_linear,gather_set_linear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gather);
        initView();
    }

    private void initView(){
        gather_zfb_linear = (LinearLayout) findViewById(R.id.gather_zfb_linear);
        gather_wx_linear = (LinearLayout) findViewById(R.id.gather_wx_linear);
        gather_bill_linear = (LinearLayout) findViewById(R.id.gather_bill_linear);
        gather_set_linear = (LinearLayout) findViewById(R.id.gather_set_linear);
        gather_zfb_linear.setOnClickListener(this);
        gather_wx_linear.setOnClickListener(this);
        gather_bill_linear.setOnClickListener(this);
        gather_set_linear.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.gather_zfb_linear:
                Intent intent_zfb_pay = new Intent(GatherActivity.this, PayActivity.class);
                intent_zfb_pay.putExtra(IntentStates.PAY_TYPE,IntentStates.PAY_ZFB);
                startActivity(intent_zfb_pay);
                break;
            case R.id.gather_wx_linear:
                Intent intent_wx_pay = new Intent(GatherActivity.this, PayActivity.class);
                intent_wx_pay.putExtra(IntentStates.PAY_TYPE,IntentStates.PAY_WX);
                startActivity(intent_wx_pay);
                break;
            case R.id.gather_bill_linear:
                Intent intent_bill_pay = new Intent(GatherActivity.this,BillActivity.class);
                intent_bill_pay.putExtra(IntentStates.PAY_TYPE,IntentStates.PAY_WX);
                startActivity(intent_bill_pay);
                break;
            case R.id.gather_set_linear:
                Intent intent_set = new Intent(GatherActivity.this,SetActivity.class);
                intent_set.putExtra(IntentStates.PAY_TYPE,IntentStates.PAY_WX);
                startActivity(intent_set);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(KeyEvent.KEYCODE_BACK==keyCode)
            return false ;
        return super.onKeyDown(keyCode, event);
    }
}
