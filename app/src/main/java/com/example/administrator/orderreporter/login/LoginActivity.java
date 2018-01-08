package com.example.administrator.orderreporter.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.orderreporter.R;
import com.example.administrator.orderreporter.base.BaseActivity;
import com.example.administrator.orderreporter.base.bean.InfoCache;
import com.example.administrator.orderreporter.base.bean.SetStates;
import com.example.administrator.orderreporter.base.bean.UserInfo;
import com.example.administrator.orderreporter.base.view.OnSingleClickListener;
import com.example.administrator.orderreporter.gather.GatherActivity;
import com.example.administrator.orderreporter.login.presenter.ILoginContract;
import com.example.administrator.orderreporter.login.presenter.LoginPresenter;
import com.example.administrator.orderreporter.utils.LogUtils;
import com.example.administrator.orderreporter.utils.ToastUtil;
import com.example.administrator.orderreporter.utils.Utils;

public class LoginActivity extends BaseActivity implements ILoginContract.LoginView{

    private TextView login_login_tv;
    private LoginPresenter loginPresenter;
    private EditText login_user_et,login_psw_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setPresenter();
        initView();

        SharedPreferences preferences = getSharedPreferences(Utils.USER, Context.MODE_PRIVATE);
        InfoCache.verify = preferences.getString(Utils.VERIFY,"");
        InfoCache.compId = preferences.getString(Utils.COMPID,"");
        InfoCache.userid= preferences.getString(Utils.USERID,"");
        InfoCache.compName = preferences.getString(Utils.COMPANYNAME,"");
        InfoCache.account = preferences.getString(Utils.LOGIN_MOBILE,"");
        LogUtils.Debug_E(LoginActivity.class,"mobile:"+preferences.getString(Utils.LOGIN_MOBILE,"")+ ","+preferences.getString(Utils.USERID,""));
        login_user_et.setText(preferences.getString(Utils.LOGIN_MOBILE,""));
        login_psw_et.setText(preferences.getString(Utils.LOGIN_PASSWORD,""));
        SetStates.VOICE = preferences.getBoolean(Utils.VOICE_SET,true);
        SetStates.PRINT = preferences.getBoolean(Utils.PRINT_SET,true);
    }

    private void initView(){
        login_login_tv = (TextView) findViewById(R.id.login_login_tv);
        login_user_et = (EditText) findViewById(R.id.login_user_et);
        login_psw_et = (EditText) findViewById(R.id.login_psw_et);
        login_login_tv.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                LogUtils.Debug_E(LoginActivity.class,"account:"+login_user_et.getText().toString());
            if(login_user_et.getText().toString().equals("")||login_user_et.getText().toString()==null){
                ToastUtil.showToast(LoginActivity.this,"请输入账号");
            }else if(login_psw_et.getText().toString().equals("")||login_psw_et.getText().toString()==null){
                ToastUtil.showToast(LoginActivity.this,"请输入密码");
            }else{
                loginPresenter.get_login(login_user_et.getText().toString(),login_psw_et.getText().toString());
            }
//                Intent intent_gather = new Intent(LoginActivity.this, GatherActivity.class);
//                startActivity(intent_gather);

            }
        });
    }

    @Override
    public void setPresenter() {
        loginPresenter = new LoginPresenter(this);
    }

    @Override
    public void login_Success(UserInfo userInfo) {
        LogUtils.Debug_E(LoginActivity.class,userInfo.getCompName()+":::");
        SharedPreferences.Editor editor = getSharedPreferences(Utils.USER, Context.MODE_PRIVATE).edit();
        editor.putString(Utils.LOGIN_PASSWORD, login_psw_et.getText().toString());
        editor.putString(Utils.LOGIN_MOBILE, login_user_et.getText().toString());
        editor.putString(Utils.VERIFY, userInfo.getVerify());
        editor.putString(Utils.USERID, userInfo.getUserid());
        editor.putString(Utils.COMPID, userInfo.getCompId());
        editor.putString(Utils.COMPANYNAME, userInfo.getCompName());

        InfoCache.userid = userInfo.getUserid();
        InfoCache.verify = userInfo.getVerify();
        InfoCache.compId = userInfo.getCompId();
        InfoCache.compName = userInfo.getCompName();
        InfoCache.account =  login_user_et.getText().toString();
        editor.apply();
        Intent intent_info = new Intent(this,GatherActivity.class);
        startActivity(intent_info);
        finish();
    }

    @Override
    public void login_err(String error) {
        ToastUtil.showToast(LoginActivity.this,error);
    }
}
