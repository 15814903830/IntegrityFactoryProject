package com.xsylsb.integrity;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;


import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;

import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;


public class MainApplication extends Application {
    private static final String TAG = "MainApplication";
    private static MainActivity mainActivity = null;
    public static boolean isBooleanface = true;
    private static MainApplication instance;
    public static MainApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.instance = this;
        initCloudChannel(this);
    }

    /**
     * 初始化云推送通道
     *
     * @param applicationContext
     */
    private void initCloudChannel(final Context applicationContext) {
        // 创建notificaiton channel
        this.createNotificationChannel();

        PushServiceFactory.init(applicationContext);
        final CloudPushService pushService = PushServiceFactory.getCloudPushService();
        pushService.register(applicationContext, new CommonCallback() {
            @SuppressLint("WrongConstant")
            @Override
            public void onSuccess(String response) {
                Log.i(TAG, "init cloudchannel success");
                receiveThePush("init cloudchannel success");

                Intent intent = new Intent();
                intent.setAction("org.agoo.android.intent.8.RECEIVE");
                intent.setPackage("com.xsylsb.integrity");//pack为应用包名
                intent.putExtra("type", "common-push");
                intent.addFlags(32);
                applicationContext.sendBroadcast(intent);

            }

            @Override
            public void onFailed(String errorCode, String errorMessage) {
                Log.e(TAG, "init cloudchannel failed -- errorcode:" + errorCode + " -- errorMessage:" + errorMessage);
                receiveThePush("init cloudchannel failed -- errorcode:" + errorCode + " -- errorMessage:" + errorMessage);
            }
        });

//        MiPushRegister.register(applicationContext, "XIAOMI_ID", "XIAOMI_KEY"); // 初始化小米辅助推送
//        HuaWeiRegister.register(this); // 接入华为辅助推送
//        VivoRegister.register(applicationContext);
//        OppoRegister.register(applicationContext, "OPPO_KEY", "OPPO_SECRET");
//        MeizuRegister.register(applicationContext, "MEIZU_ID", "MEIZU_KEY");
//        GcmRegister.register(applicationContext, "send_id", "application_id"); // 接入FCM/GCM初始化推送
    }

    public static void setMainActivity(MainActivity activity) {
        mainActivity = activity;
    }

    public static void receiveThePush(String text) {
        Log.e(TAG,text);
        if (mainActivity != null && text != null) {
            mainActivity.receiveThePush(text);
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.i(TAG, "手机版本大于26=====");
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            // 通知渠道的id
            String id = "1";
            // 用户可以看到的通知渠道的名字.
            CharSequence name = "notification channel";
            // 用户可以看到的通知渠道的描述
            String description = "notification description";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(id, name, importance);
            // 配置通知渠道的属性
            mChannel.setDescription(description);
            // 设置通知出现时的闪灯（如果 android 设备支持的话）
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            // 设置通知出现时的震动（如果 android 设备支持的话）
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            //最后在notificationmanager中创建该通知渠道
            mNotificationManager.createNotificationChannel(mChannel);
        } else {
            Log.i(TAG, "手机版本小于26=====");
        }
    }
}