package com.example.administrator.orderreporter.pay;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.orderreporter.R;
import com.example.administrator.orderreporter.base.BaseActivity;
import com.example.administrator.orderreporter.base.bean.InfoCache;
import com.example.administrator.orderreporter.base.bean.IntentStates;
import com.example.administrator.orderreporter.pay.bean.PayBean;
import com.example.administrator.orderreporter.pay.presenter.IPayContract;
import com.example.administrator.orderreporter.pay.presenter.PayPresenter;
import com.example.administrator.orderreporter.utils.LogUtils;
import com.example.administrator.orderreporter.utils.ToastUtil;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class PayActivity extends BaseActivity implements View.OnClickListener,IPayContract.PayView {

    private EditText pay_price_et;
    private LinearLayout pay_sure_linear;
    private final int START_SCAN = 1;
    private TextView pay_one_tv,pay_two_tv,pay_three_tv,pay_four_tv,pay_five_tv,pay_six_tv,pay_seven_tv,pay_eight_tv,pay_nine_tv,pay_zero_tv,pay_dot_tv;
    private RelativeLayout pay_delete_rela;
    private StringBuilder stringBuilder;
    private MyHandler myHandler;
    private MyThread myThread;
    private Boolean isTouch = false;//长按delete
    private Boolean zeroStart = false;//0开头判断
    private int dotSize = 0;
    private PayPresenter payPresenter;
    private String payType;
    private TextView pay_name_tv;
    private Dialog progressDialog;
    private String code;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        setTitle("收款", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        },null);
        payType = getIntent().getStringExtra(IntentStates.PAY_TYPE);
        setPresenter();
        initView();

    }

    public void initView(){
        myThread= new MyThread();
        stringBuilder = new StringBuilder();
        myHandler = new MyHandler();
        pay_sure_linear = (LinearLayout) findViewById(R.id.pay_sure_linear);
        pay_price_et = (EditText) findViewById(R.id.pay_price_et);


        progressDialog = new Dialog(PayActivity.this);
        progressDialog.setContentView(R.layout.view_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.setCancelable(false);

        pay_one_tv = (TextView) findViewById(R.id.pay_one_tv);
        pay_two_tv = (TextView) findViewById(R.id.pay_two_tv);
        pay_three_tv = (TextView) findViewById(R.id.pay_three_tv);
        pay_four_tv = (TextView) findViewById(R.id.pay_four_tv);
        pay_five_tv = (TextView) findViewById(R.id.pay_five_tv);
        pay_six_tv = (TextView) findViewById(R.id.pay_six_tv);
        pay_seven_tv = (TextView) findViewById(R.id.pay_seven_tv);
        pay_eight_tv = (TextView) findViewById(R.id.pay_eight_tv);
        pay_nine_tv = (TextView) findViewById(R.id.pay_nine_tv);
        pay_dot_tv = (TextView) findViewById(R.id.pay_dot_tv);
        pay_zero_tv = (TextView) findViewById(R.id.pay_zero_tv);
        pay_delete_rela = (RelativeLayout) findViewById(R.id.pay_delete_rela);
        pay_name_tv = (TextView) findViewById(R.id.pay_name_tv);
        pay_name_tv.setText(InfoCache.compName);

        pay_one_tv.setOnClickListener(this);
        pay_two_tv.setOnClickListener(this);
        pay_three_tv.setOnClickListener(this);
        pay_four_tv.setOnClickListener(this);
        pay_five_tv.setOnClickListener(this);
        pay_six_tv.setOnClickListener(this);
        pay_seven_tv.setOnClickListener(this);
        pay_eight_tv.setOnClickListener(this);
        pay_nine_tv.setOnClickListener(this);
        pay_zero_tv.setOnClickListener(this);

        pay_dot_tv.setOnClickListener(this);
       // pay_delete_rela.setOnClickListener(this);

        pay_delete_rela.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){

                    case MotionEvent.ACTION_DOWN:
                      ;//  启动删除字符的线程
                        isTouch = true;
                        new Thread(myThread).start();

                        break;
                    case MotionEvent.ACTION_UP:
                        isTouch = false;
                        break;
                             }
                return false;
            }
        });

        pay_delete_rela.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                return true;
            }
        });

        pay_price_et.addTextChangedListener(watcher);
        pay_price_et.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                int inType = pay_price_et.getInputType(); // backup the input type
                pay_price_et.setInputType(InputType.TYPE_NULL); // disable soft input
                pay_price_et.onTouchEvent(arg1); // call native handler
                pay_price_et.setInputType(inType); // restore input type
                return true;
            }
        });
        pay_sure_linear.setOnClickListener(this);
        disableShowInput();

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pay_sure_linear:
                if(pay_price_et.getText().toString()!=null&&!pay_price_et.getText().toString().equals("")){
                    Double money =Double.valueOf(pay_price_et.getText().toString());
                    if(BigDecimal.valueOf(money).compareTo(new BigDecimal(0))==1){
                        Intent intent = new Intent("com.summi.scan");
                        intent.setPackage("com.sunmi.sunmiqrcodescanner");
                        startActivityForResult(intent, START_SCAN);
                    }else{
                        ToastUtil.showToast(PayActivity.this,"金额错误");
                    }
                }else{
                    ToastUtil.showToast(PayActivity.this,"请输入金额");
                }
                break;
            case R.id.pay_one_tv:
                if(!isMax()){
                    stringBuilder.append(1);
                    pay_price_et.setText(stringBuilder);
                }
                break;
            case R.id.pay_two_tv:
                if(!isMax()){
                    stringBuilder.append(2);
                    pay_price_et.setText(stringBuilder);
                }
                break;
            case R.id.pay_three_tv:
                if(!isMax()) {
                    stringBuilder.append(3);
                    pay_price_et.setText(stringBuilder);
                }
                break;
            case R.id.pay_four_tv:
                if(!isMax()) {
                    stringBuilder.append(4);
                    pay_price_et.setText(stringBuilder);
                }
                break;
            case R.id.pay_five_tv:
                if(!isMax()) {
                    stringBuilder.append(5);
                    pay_price_et.setText(stringBuilder);
                }
                break;
            case R.id.pay_six_tv:
                if(!isMax()) {
                    stringBuilder.append(6);
                    pay_price_et.setText(stringBuilder);
                }
                break;
            case R.id.pay_seven_tv:
                if(!isMax()) {
                    stringBuilder.append(7);
                    pay_price_et.setText(stringBuilder);
                }
                break;
            case R.id.pay_eight_tv:
                if(!isMax()) {
                    stringBuilder.append(8);
                    pay_price_et.setText(stringBuilder);
                }
                break;
            case R.id.pay_nine_tv:
                if(!isMax()) {
                    stringBuilder.append(9);
                    pay_price_et.setText(stringBuilder);
                }
                break;
            case R.id.pay_zero_tv:
                if(!isMax()) {
                    if(stringBuilder.length()==0){
                        stringBuilder.append(0);
                        stringBuilder.append(".");
                        dotSize = stringBuilder.length();
                        zeroStart = true;
                    }else {
                        stringBuilder.append(0);
                    }

                        pay_price_et.setText(stringBuilder);
                }
                break;
            case R.id.pay_dot_tv:
                Boolean hasDot = false;
                if(!isMax()) {
                    for (int i = 0; i < stringBuilder.length(); i++) {
                        if (String.valueOf(stringBuilder.charAt(i)).equals(".")) {
                            hasDot = true;
                        }
                    }
                    if (!hasDot&&stringBuilder.length()!=0) {
                        stringBuilder.append(".");
                        pay_price_et.setText(stringBuilder);
                        dotSize = stringBuilder.length();
                    }

                }
                break;
            case R.id.pay_delete_rela:

//                int  keyCode = KeyEvent.KEYCODE_DEL;
//                KeyEvent keyEventDown = new KeyEvent(KeyEvent.ACTION_DOWN, keyCode);
//                KeyEvent keyEventUp = new KeyEvent(KeyEvent.ACTION_UP, keyCode);
//                pay_price_et.onKeyDown(keyCode, keyEventDown);
//                pay_price_et.onKeyUp(keyCode, keyEventUp);
                if(stringBuilder.length()>0){
                    LogUtils.Debug_E(PayActivity.class,"Click");
                        stringBuilder.deleteCharAt(stringBuilder.length()-1);
                        pay_price_et.setText(stringBuilder);

                }
                break;
        }
    }

    @Override
    public void setPresenter() {
        payPresenter = new PayPresenter(this);
    }

    @Override
    public void paySuccess(PayBean payBean) {

        progressDialog.dismiss();
        Intent intent_success = new Intent(PayActivity.this,SuccessActivity.class);
        intent_success.putExtra(IntentStates.PAY_TYPE,payType);
        intent_success.putExtra(IntentStates.PAY_MONEY,pay_price_et.getText().toString());
        intent_success.putExtra(IntentStates.PAY_ORDER,payBean.getOrderNo());
        startActivity(intent_success);
        finish();
    }

    @Override
    public void payErr() {
        Intent intent_err = new Intent(PayActivity.this,PayErrActivity.class);
        startActivity(intent_err);
        finish();
    }

    @Override
    public void payRepost() {
        //payPresenter.get_pay(pay_price_et.getText().toString(), code, payType);
        ToastUtil.showToast(PayActivity.this,"网络异常，请检查网络！");
    }


    class MyThread implements Runnable {

        public void run() {
            do{
                try {
                    Thread.sleep(200);
                } catch (Exception e) {
                }
                if(stringBuilder.length()>0) {

                    stringBuilder = stringBuilder.deleteCharAt(stringBuilder.length() - 1);
//                    LogUtils.Debug_E(PayActivity.class,stringBuilder.length()+"longClick:"+stringBuilder.charAt(stringBuilder.length()-1));
                    if(stringBuilder.length()==1&&zeroStart){
                        stringBuilder = stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                    }
                }
                Message msg = new Message();
                Bundle b = new Bundle();// 存放数据
                b.putString("str", String.valueOf(stringBuilder));

                msg.setData(b);
                myHandler.sendMessage(msg); // 向Handler发送消息,更新UI
            }while (isTouch);

        }
    }

    class MyHandler extends Handler {
        public MyHandler() {
        }
        public MyHandler(Looper L) {
            super(L);
        }
        // 子类必须重写此方法,接受数据
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            // 此处可以更新UI
            Bundle b = msg.getData();
            String str = b.getString("str");

            pay_price_et.setText(str);// 设置输入框内容为空
        }
    }

    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == START_SCAN && data != null) {

            Bundle bundle = data.getExtras();

            ArrayList<HashMap<String, String>> result = (ArrayList<HashMap<String, String>>) bundle

                    .getSerializable("data");


            Iterator<HashMap<String, String>> it = result.iterator();

            while (it.hasNext()) {

                HashMap<String, String> hashMap = it.next();

                code = hashMap.get("VALUE");
                LogUtils.Debug_E(PayActivity.class, hashMap.get("TYPE"));//这个是扫码的类型

                LogUtils.Debug_E(PayActivity.class, hashMap.get("VALUE"));//这个是扫码的结果
                progressDialog.show();
                payPresenter.get_pay(pay_price_et.getText().toString(), hashMap.get("VALUE"), payType);


            }

        }

        super.onActivityResult(requestCode, resultCode, data);

    }

    public void disableShowInput(){
        if (android.os.Build.VERSION.SDK_INT <= 10){
            pay_price_et.setInputType(InputType.TYPE_NULL);
        }else {
            Class<EditText> cls = EditText.class;
            Method method;
            try {
                method = cls.getMethod("setShowSoftInputOnFocus",boolean.class);
                method.setAccessible(true);
                method.invoke(pay_price_et,false);
            }catch (Exception e) {//TODO: handle exception
            }
            try {
                method = cls.getMethod("setSoftInputShownOnFocus",boolean.class);
                method.setAccessible(true);
                method.invoke(pay_price_et,false);
            }catch (Exception e) {//TODO: handle exception
            }
        }
    }


    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            if(stringBuilder.length() == 0){
                zeroStart = false;
                dotSize = 0;
            }
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }
        @Override
        public void afterTextChanged(Editable s) {
            pay_price_et.setSelection(pay_price_et.length(),pay_price_et.length());
        }
    };

    private boolean isMax(){
        LogUtils.Debug_E(PayActivity.class,"dotsize:"+dotSize);
        if(stringBuilder.length() == 12){
            return  true;
        }
        if(dotSize!=0){
            if(stringBuilder.length() == dotSize+2){
                return  true;
            }
        }

        return false;
    }

}
