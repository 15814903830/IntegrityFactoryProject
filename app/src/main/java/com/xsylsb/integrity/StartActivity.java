package com.xsylsb.integrity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.xsylsb.integrity.base.LoginBase;
import com.xsylsb.integrity.mylogin.MyloginActivity;
import com.xsylsb.integrity.util.HttpCallBack;
import com.xsylsb.integrity.util.MyURL;
import com.xsylsb.integrity.util.OkHttpUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class StartActivity extends AppCompatActivity implements HttpCallBack {
    private CountDownTimer timer;
    private SharedPreferences sp;
    private HttpCallBack mHttpCallBack;
    private String numberStr1,passwordStr2;
    private LoginBase mLoginBase;
    private boolean mBoolean=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        mHttpCallBack=this;
        sp = getSharedPreferences("info",MODE_PRIVATE);
         numberStr1 = sp.getString("number","");
         passwordStr2 = sp.getString("password","");
        Login();
        /** 倒计时60秒，一次1秒 */
        timer = new CountDownTimer(1* 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                if (mBoolean){
                    startActivity(new Intent(StartActivity.this, MyloginActivity.class));
                }
                finish();
            }
        }.start();
    }




    private void Login() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("idno", numberStr1);
                    jsonObject.put("password", passwordStr2);
                    Log.e("login", MyURL.URL + "Login");
                    OkHttpUtils.doPostJson(MyURL.URL + "Login", jsonObject.toString(), mHttpCallBack, 0);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("JSONException", e.toString());
                }
            }
        }).start();
    }



    @Override
    public void onResponse(String response, int requestId) {
        Message message = mHandler.obtainMessage();
        message.what = requestId;
        message.obj = response;
        mHandler.sendMessage(message);
    }

    @Override
    public void onHandlerMessageCallback(String response, int requestId) {
        Log.e("mylog",""+response);
        switch (requestId) {
            case 0:
                try {
                    mLoginBase = JSON.parseObject(response, LoginBase.class);
                    if (mLoginBase.isSuc()) {
                        mBoolean=false;
                        MyURL.id = "" + mLoginBase.getData().getId();
                        //登陆操作
                        //验证账号密码，跳转到主页
                        Intent intent = new Intent();
                        intent.putExtra("name", mLoginBase.getData().getFullName());
                        intent.setClass(this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("mylog","服务器异常");
                    Toast.makeText(this, "服务器异常", Toast.LENGTH_SHORT).show();
                }
                break;

        }


    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int requestId = msg.what;
            String response = (String) msg.obj;
            onHandlerMessageCallback(response, requestId);
        }
    };













    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}
