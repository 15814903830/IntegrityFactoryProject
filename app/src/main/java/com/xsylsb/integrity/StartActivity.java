package com.xsylsb.integrity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.xsylsb.integrity.base.LoginBase;
import com.xsylsb.integrity.mylogin.LogwebActivity;
import com.xsylsb.integrity.mylogin.MyloginActivity;
import com.xsylsb.integrity.util.HttpCallBack;
import com.xsylsb.integrity.util.MyURL;
import com.xsylsb.integrity.util.OkHttpUtils;
import com.xsylsb.integrity.util.SharedPrefUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class StartActivity extends AppCompatActivity implements HttpCallBack {
    private CountDownTimer timer;
    private SharedPreferences sp;
    private HttpCallBack mHttpCallBack;
    private String numberStr1, passwordStr2;
    private LoginBase mLoginBase;
    private boolean mBoolean = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        mHttpCallBack = this;
        sp = getSharedPreferences("info", MODE_PRIVATE);
        numberStr1 = sp.getString("number", "");
        passwordStr2 = sp.getString("password", "");
        if (isNetworkConnected(this)) {
            Login();
        }
        /** 倒计时60秒，一次1秒 */
        timer = new CountDownTimer(1 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                if (mBoolean) {
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
        Log.e("mylog", "" + response);
        switch (requestId) {
            case 0:
                try {
                    mLoginBase = JSON.parseObject(response, LoginBase.class);
                    if (mLoginBase.isSuc()) {
                        mBoolean = false;
                        SharedPrefUtil.putString(SharedPrefUtil.ID,"" + mLoginBase.getData().getId());
                        //登陆操作
                        //验证账号密码，跳转到主页
                        startActivity(new Intent(StartActivity.this, LogwebActivity.class));
//                        Intent intent = new Intent();
//                        intent.putExtra("name", mLoginBase.getData().getFullName());
//                        intent.setClass(this, MainActivity.class);
//                        startActivity(intent);
                        finish();
                    } else {
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (isNetworkConnected(this)) {
                    } else {
                        Intent intent = new Intent();
                        intent.setClass(this, MyloginActivity.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(this, "请检查网络连接", Toast.LENGTH_SHORT).show();
                    }
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


    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}
