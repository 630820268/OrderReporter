package com.example.administrator.orderreporter.pay;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.orderreporter.R;
import com.example.administrator.orderreporter.base.BaseActivity;
import com.example.administrator.orderreporter.base.bean.InfoCache;
import com.example.administrator.orderreporter.base.bean.IntentStates;
import com.example.administrator.orderreporter.base.bean.SetStates;
import com.example.administrator.orderreporter.bill.QueryActivity;
import com.example.administrator.orderreporter.print.BasePrintActivity;
import com.example.administrator.orderreporter.utils.BluetoothUtil;
import com.example.administrator.orderreporter.utils.LogUtils;
import com.example.administrator.orderreporter.utils.PrintUtil;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;

public class SuccessActivity extends BasePrintActivity implements View.OnClickListener{

    private TextView success_sure_tv,success_query_tv;
    final static int TASK_TYPE_CONNECT = 1;
    final static int TASK_TYPE_PRINT = 2;
    private String type,money,orderNo;
    private ImageView title_left_iv;
    private TextView title_tv;

    @Override
    public void onConnected(BluetoothSocket socket, int taskType) {
        switch (taskType){
            case TASK_TYPE_PRINT:
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pay_delete_icon);
                PrintUtil.printTest(socket, InfoCache.compName,orderNo,money,bitmap);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
//        setTitle("收款成功", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        },null);
        LogUtils.Debug_E(SuccessActivity.class,SetStates.VOICE+","+SetStates.PRINT+","+money+","+type);
        type = getIntent().getStringExtra(IntentStates.PAY_TYPE);
        money = getIntent().getStringExtra(IntentStates.PAY_MONEY);
        orderNo = getIntent().getStringExtra(IntentStates.PAY_ORDER);
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=5a13e7af");
        initView();
        connectDevice(TASK_TYPE_CONNECT);
        if(SetStates.PRINT){
           connectDevice(TASK_TYPE_PRINT);
        }
        if(SetStates.VOICE){
            speekText(money,type);
        }
    }

    private void initView(){
        success_sure_tv = (TextView) findViewById(R.id.success_sure_tv);
        success_query_tv = (TextView) findViewById(R.id.success_query_tv);
        title_left_iv = (ImageView) findViewById(R.id.title_left_iv);
        title_tv = (TextView) findViewById(R.id.title_tv);
        title_tv.setText("收款成功");
        success_sure_tv.setOnClickListener(this);
        success_query_tv.setOnClickListener(this);
        title_left_iv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.success_sure_tv:
                finish();
                break;
            case R.id.success_query_tv:
                Intent intent_query = new Intent(SuccessActivity.this, QueryActivity.class);
                startActivity(intent_query);
                finish();
                break;
            case R.id.title_left_iv:
                finish();
                break;
        }
    }

    private void connectDevice(int taskType){
        BluetoothAdapter btAdapter = BluetoothUtil.getBTAdapter();
        BluetoothDevice device = BluetoothUtil.getDevice(btAdapter);

            if(device!= null)
                super.connectDevice(device, taskType);

    }

    private void speekText(String money,String type) {
        //1. 创建 SpeechSynthesizer 对象 , 第二个参数： 本地合成时传 InitListener
        SpeechSynthesizer mTts = SpeechSynthesizer.createSynthesizer( this, null);
//2.合成参数设置，详见《 MSC Reference Manual》 SpeechSynthesizer 类
//设置发音人（更多在线发音人，用户可参见 附录 13.2
        mTts.setParameter(SpeechConstant. VOICE_NAME, "xiaoqi" ); // 设置发音人
        mTts.setParameter(SpeechConstant. SPEED, "50" );// 设置语速
        mTts.setParameter(SpeechConstant. VOLUME, "80" );// 设置音量，范围 0~100
        mTts.setParameter(SpeechConstant. ENGINE_TYPE, SpeechConstant. TYPE_CLOUD); //设置云端
//设置合成音频保存位置（可自定义保存位置），保存在 “./sdcard/iflytek.pcm”
//保存在 SD 卡需要在 AndroidManifest.xml 添加写 SD 卡权限
//仅支持保存为 pcm 和 wav 格式， 如果不需要保存合成音频，注释该行代码
        mTts.setParameter(SpeechConstant. TTS_AUDIO_PATH, "./sdcard/iflytek.pcm" );
//3.开始合成
        if(type.equals(IntentStates.PAY_WX)){
            mTts.startSpeaking( "微信到账，"+money+"元!", new MySynthesizerListener()) ;
        }else{
            mTts.startSpeaking( "支付宝到账，"+money+"元!", new MySynthesizerListener()) ;
        }


    }

    class MySynthesizerListener implements SynthesizerListener {

        @Override
        public void onSpeakBegin() {

        }

        @Override
        public void onSpeakPaused() {

        }

        @Override
        public void onSpeakResumed() {

        }

        @Override
        public void onBufferProgress(int percent, int beginPos, int endPos ,
                                     String info) {
            // 合成进度
        }

        @Override
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
            // 播放进度
        }

        @Override
        public void onCompleted(SpeechError error) {
            if (error == null) {

            } else if (error != null ) {

            }
        }

        @Override
        public void onEvent(int eventType, int arg1 , int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话 id，当业务出错时将会话 id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话 id为null
            //if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //     String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //     Log.d(TAG, "session id =" + sid);
            //}
        }
    }
}
