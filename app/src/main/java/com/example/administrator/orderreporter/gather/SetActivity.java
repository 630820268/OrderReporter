package com.example.administrator.orderreporter.gather;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.orderreporter.R;
import com.example.administrator.orderreporter.base.BaseActivity;
import com.example.administrator.orderreporter.base.bean.InfoCache;
import com.example.administrator.orderreporter.base.bean.SetStates;
import com.example.administrator.orderreporter.utils.ToastUtil;
import com.example.administrator.orderreporter.utils.Utils;

public class SetActivity extends BaseActivity implements View.OnClickListener{

    private ImageView set_voice_iv,set_print_iv;
    private RelativeLayout set_quit_rela;
    private TextView set_phone_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        setTitle("设置", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        },null);
        SharedPreferences preferences = getSharedPreferences(Utils.USER, Context.MODE_PRIVATE);
        SetStates.VOICE = preferences.getBoolean(Utils.VOICE_SET,true);
        SetStates.PRINT = preferences.getBoolean(Utils.PRINT_SET,true);
        initView();
    }

    private void initView(){
        set_voice_iv = (ImageView) findViewById(R.id.set_voice_iv);
        set_print_iv = (ImageView) findViewById(R.id.set_print_iv);
        set_phone_tv = (TextView) findViewById(R.id.set_phone_tv);
        set_phone_tv.setText(InfoCache.account);

        if(SetStates.VOICE){
            set_voice_iv.setImageResource(R.drawable.set_btn_on);
        }else{
            set_voice_iv.setImageResource(R.drawable.set_btn_off);
        }

        if(SetStates.PRINT){
            set_print_iv.setImageResource(R.drawable.set_btn_on);
        }else{
            set_print_iv.setImageResource(R.drawable.set_btn_off);
        }

        set_quit_rela = (RelativeLayout) findViewById(R.id.set_quit_rela);
        set_voice_iv.setOnClickListener(this);
        set_print_iv.setOnClickListener(this);
        set_quit_rela.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        SharedPreferences.Editor editor = getSharedPreferences(Utils.USER, Context.MODE_PRIVATE).edit();
        switch (v.getId()){
            case R.id.set_voice_iv:
                if(SetStates.VOICE){
                    set_voice_iv.setImageResource(R.drawable.set_btn_off);
                    SetStates.VOICE = false;
                    ToastUtil.showToast(SetActivity.this,"当前语音播报状态：关闭");
                }else{
                    set_voice_iv.setImageResource(R.drawable.set_btn_on);
                    SetStates.VOICE = true;
                    ToastUtil.showToast(SetActivity.this,"当前语音播报状态：开启");
                }

                editor.putBoolean(Utils.VOICE_SET,  SetStates.VOICE);
                editor.apply();
                break;
            case R.id.set_print_iv:
                if(SetStates.PRINT){
                    set_print_iv.setImageResource(R.drawable.set_btn_off);
                    SetStates.PRINT = false;
                    ToastUtil.showToast(SetActivity.this,"当前打印机状态：关闭");
                }else{
                    set_print_iv.setImageResource(R.drawable.set_btn_on);
                    SetStates.PRINT = true;
                    ToastUtil.showToast(SetActivity.this,"当前打印机状态：开启");
                }

                editor.putBoolean(Utils.PRINT_SET,  SetStates.PRINT);
                editor.apply();
                break;
            case R.id.set_quit_rela:
                Intent intent_quit = new Intent(SetActivity.this,QuitActivity.class);
                startActivity(intent_quit);
                break;
        }
    }
}
