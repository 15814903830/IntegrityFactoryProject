package com.xsylsb.integrity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.xsylsb.integrity.base.OperativesSignBase;
import com.xsylsb.integrity.base.ScanCodeBase;
import com.xsylsb.integrity.face.activity.FaceDetectRGBActivity;
import com.xsylsb.integrity.mianfragment.homepage.homepage.HomepageFragment;
import com.xsylsb.integrity.mianfragment.homepage.notice.NoticeFragment;
import com.xsylsb.integrity.mianfragment.homepage.personage.PersonageFragment;
import com.xsylsb.integrity.mianfragment.homepage.scan.ScanFragment;
import com.xsylsb.integrity.mianfragment.homepage.train.TrainFragment;
import com.xsylsb.integrity.util.DataCleanManagerUtils;
import com.xsylsb.integrity.util.HttpCallBack;
import com.xsylsb.integrity.util.MyURL;
import com.xsylsb.integrity.util.OkHttpUtils;
import com.xsylsb.integrity.util.SharedPrefUtil;
import com.xsylsb.integrity.util.StowMainInfc;
import com.xsylsb.integrity.util.dialog.BaseNiceDialog;
import com.xsylsb.integrity.util.dialog.NiceDialog;
import com.xsylsb.integrity.util.dialog.ViewConvertListener;
import com.xsylsb.integrity.util.dialog.ViewHolder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements HttpCallBack, StowMainInfc {

    @BindView(R.id.main)
    FrameLayout main;
    @BindView(R.id.main_tv_home)
    TextView mainTvHome;
    @BindView(R.id.main_tv_train)
    TextView mainTvTrain;
    @BindView(R.id.main_tv_scan)
    TextView mainTvScan;
    @BindView(R.id.main_tv_notice)
    TextView mainTvNotice;
    @BindView(R.id.tv_notice_dot)
    public TextView tvNoticeDot;
    @BindView(R.id.main_tv_personage)
    TextView mainTvPersonage;
    @BindView(R.id.main_ll_home)
    LinearLayout mainLlHome;
    @BindView(R.id.main_ll_train)
    LinearLayout mainLlTrain;
    @BindView(R.id.main_ll_scan)
    LinearLayout mainLlScan;
    @BindView(R.id.main_ll_notice)
    LinearLayout mainLlNotice;
    @BindView(R.id.main_ll_personage)
    LinearLayout mainLlPersonage;
    private static final int RC_HANDLE_CAMERA_PERM_RGB = 1;
    @BindView(R.id.iv_api_test)
    TextView ivApiTest;
    @BindView(R.id.btn_zhengshi)
    Button btnZhengshi;
    @BindView(R.id.btn_ceisi)
    Button btnCeisi;
    private HomepageFragment homepageFragment;
    private NoticeFragment mNoticeFragment;
    private PersonageFragment mPersonageFragment;
    private ScanFragment mScanFragment;
    private TrainFragment mTrainFragment;
    private Fragment mContent;//定义了当前页面所在的fragmnet
    private String Title;
    private HttpCallBack mHttpCallBack;
    private ScanCodeBase mScanCodeBase;
    private FragmentManager fm = getSupportFragmentManager();
    private Fragment mFragment;
    private String mtag;
    private Context mContext;
    private CloudPushService mPushService;
    private static final int CROP_PHOTO = 2;
    private static final int PICK_PIC = 3;

    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE1 = 4;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE2 = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        ButterKnife.bind(this);
        MainApplication.setMainActivity(this);
        mPushService = PushServiceFactory.getCloudPushService();
        mContext = this;
        mHttpCallBack = this;
        Title = "欢迎您，" + getIntent().getStringExtra("name");
        mainTvHome.setSelected(true);
        ivApiTest.setText(""+MyURL.URL);
        //首页
//        if (homepageFragment == null) {
//            homepageFragment = HomepageFragment.newInstance();
//        }
//        showFragment(homepageFragment,"HOME");

        homepageFragment = HomepageFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, homepageFragment)
                .commit();
        DataCleanManagerUtils.clearAllCache(this);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat
                    .requestPermissions(
                            this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_CALL_PHONE2);

        } else {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat
                        .requestPermissions(
                                this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST_CALL_PHONE2);

            } else {
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        bindAccount();
        getImei();
        // 1. 实例化BroadcastReceiver子类 &  IntentFilter
        LocatiopnBroadcast locatiopnBroadcast = new LocatiopnBroadcast();
        IntentFilter intentFilter = new IntentFilter();
        // 2. 设置接收广播的类型
        intentFilter.addAction("ADD_CREATIONG_GROUP");// 只有持有相同的action的接受者才能接收此广播
        // 3. 动态注册：调用Context的registerReceiver（）方法
        registerReceiver(locatiopnBroadcast, intentFilter);
    }

    //收到推送
    public void receiveThePush(String text) {
        tvNoticeDot.setVisibility(View.VISIBLE);
    }

    /**
     * 绑定账户接口:CloudPushService.bindAccount调用示例
     * 1. 绑定账号后,可以在服务端通过账号进行推送
     * 2. 一个设备只能绑定一个账号
     */
    private void bindAccount() {
        mPushService.bindAccount(SharedPrefUtil.getString(SharedPrefUtil.ID), new CommonCallback() {
            @Override
            public void onSuccess(String s) {
                Log.i("MainActivity", "绑定账号成功：" + SharedPrefUtil.getString(SharedPrefUtil.ID));
            }

            @Override
            public void onFailed(String errorCode, String errorMsg) {

            }
        });
    }

    private void getImei() {
        TelephonyManager TelephonyMgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "请允许获取设备信息权限", Toast.LENGTH_SHORT);
            return;
        }
        String szImei = TelephonyMgr.getDeviceId();
        Log.i("MainActivity", "设备ID：" + szImei);
    }

    @OnClick({R.id.main_ll_home, R.id.main_ll_train, R.id.main_ll_scan, R.id.main_ll_notice, R.id.main_ll_personage,R.id.btn_zhengshi,R.id.btn_ceisi})
    public void MyOnClick(View view) {
        switch (view.getId()) {
            case R.id.main_ll_home:
                //首页
                homepageFragment = HomepageFragment.newInstance();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main, homepageFragment)
                        .commit();

                // showFragment(homepageFragment,"HOME");
                mainTvHome.setSelected(true);
                mainTvTrain.setSelected(false);
                mainTvScan.setSelected(false);
                mainTvNotice.setSelected(false);
                mainTvPersonage.setSelected(false);
                break;
            case R.id.main_ll_train:
//                //培训
//                if (mTrainFragment == null) {
//                    mTrainFragment = TrainFragment.newInstance();
//                }
//
//                showFragment(mTrainFragment,"TRAIN");
                mTrainFragment = TrainFragment.newInstance();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main, mTrainFragment)
                        .commit();
                mainTvHome.setSelected(false);
                mainTvTrain.setSelected(true);
                mainTvScan.setSelected(false);
                mainTvNotice.setSelected(false);
                mainTvPersonage.setSelected(false);
                break;
            case R.id.main_ll_scan:
                //扫一扫
                scan();
                break;
            case R.id.main_ll_notice:
//                //通知
//                if (mNoticeFragment == null) {
//                    mNoticeFragment = NoticeFragment.newInstance();
//                }
//                showFragment(mNoticeFragment,"NOTICE");
//                tvNoticeDot.setVisibility(View.GONE);
                mNoticeFragment = NoticeFragment.newInstance();
                mNoticeFragment.setMainActivity(this);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main, mNoticeFragment)
                        .commit();
                mainTvHome.setSelected(false);
                mainTvTrain.setSelected(false);
                mainTvScan.setSelected(false);
                mainTvNotice.setSelected(true);
                mainTvPersonage.setSelected(false);
                break;

            case R.id.main_ll_personage:
                //个人
                //                                if (mPersonageFragment==null){
                //                                    mPersonageFragment = PersonageFragment.newInstance(this);
                //                                }
                //
                //                showFragment(mPersonageFragment,"PERSINAGE");
                mPersonageFragment = PersonageFragment.newInstance(this);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main, mPersonageFragment)
                        .commit();
                mainTvHome.setSelected(false);
                mainTvTrain.setSelected(false);
                mainTvScan.setSelected(false);
                mainTvNotice.setSelected(false);
                mainTvPersonage.setSelected(true);
                break;
            case R.id.btn_zhengshi:
                MyURL.URL="http://api.liugang.gx11.gx11.cn/Api/Account/";
                MyURL.URLL = "http://api.liugang.gx11.gx11.cn/";
                ivApiTest.setText(""+ MyURL.URL);
                break;
            case R.id.btn_ceisi:
                MyURL.URL="http://testapi.liugang.gx11.cn/Api/Account/";
                MyURL.URLL = "http://testapi.liugang.gx11.cn/";
                ivApiTest.setText(""+ MyURL.URL);
                break;
            default:
                break;
        }
    }


    /**
     * 扫一扫和人脸识别
     */
    public void scan() {
        NiceDialog.init()
                .setLayoutId(R.layout.sanc_dialog)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    protected void convertView(ViewHolder holder, final BaseNiceDialog dialog) {
                        LinearLayout linearLayout = holder.getView(R.id.sanc_ll_code);
                        LinearLayout linearLayout1 = holder.getView(R.id.sanc_ll_face);
                        TextView textView = holder.getView(R.id.sanc_ll_close);
                        textView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });

                        linearLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (isNetworkConnected(MainActivity.this)) {
                                    //二维吗
                                    startActivityForResult(new Intent(MainActivity.this, QRCodeActivity.class), 0);
                                    dialog.dismiss();
                                } else {
                                    Toast.makeText(mContext, "请检查网络链接", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            }
                        });

                        linearLayout1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (isNetworkConnected(MainActivity.this)) {
                                    //人脸识别
                                    int rc = ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA);
                                    if (rc == PackageManager.PERMISSION_GRANTED) {
                                        Intent intent = new Intent(mContext, FaceDetectRGBActivity.class);
                                        //             Intent intent = new Intent(mContext, LeadFaceDetectRGBActivity.class);
                                        startActivity(intent);
                                    } else {
                                        requestCameraPermission(RC_HANDLE_CAMERA_PERM_RGB);
                                    }
                                    dialog.dismiss();
                                } else {
                                    Toast.makeText(mContext, "请检查网络链接", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            }
                        });

                    }
                })
                .setDimAmount(0.1f)
                .setShowBottom(false)
                .setAnimStyle(R.style.PracticeModeAnimation)
                .show(getSupportFragmentManager());
        mainTvHome.setSelected(false);
        mainTvTrain.setSelected(false);
        mainTvScan.setSelected(true);
        mainTvNotice.setSelected(false);
        mainTvPersonage.setSelected(false);
    }

    private void showFragment(Fragment fragment, String tag) {

        if (tag.equals(mtag)) {
            return;
        }
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        if (mFragment != null) {
            fragmentTransaction.hide(mFragment);
        }
        if (fragment.isAdded()) {
            fragmentTransaction.show(fragment);
        } else {
            fragmentTransaction.add(R.id.main, fragment);
        }

        fragmentTransaction.commit();
        mFragment = fragment;
        mtag = tag;
    }

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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            //扫码获得的信息
            String mycodedata = toURLDecoder(data.getStringExtra(QRCodeActivity.RESULT));
            Log.e("datacodes", mycodedata);
            if (mycodedata.contains("Account/OperativesSign")) {
                List<OperativesSignBase> list = new ArrayList<>();
                String[] split = mycodedata.split("\\?")[1].split("&");
                for (int i = 0; i < split.length; i++) {
                    OperativesSignBase operativesSignBase = new OperativesSignBase();
                    operativesSignBase.setKey(split[i].split("=")[0]);
                    operativesSignBase.setValue(split[i].split("=")[1]);
                    if (operativesSignBase.getKey().equals("workerId")) {
                        operativesSignBase.setValue("" + SharedPrefUtil.getString(SharedPrefUtil.ID));
                    }
                    list.add(operativesSignBase);
                }
                OperativesSign(mycodedata.split("\\?")[0], list);
            } else if (mycodedata.contains("Account/OperativesFacesSign")) {
                List<OperativesSignBase> list = new ArrayList<>();
                String[] split = mycodedata.split("\\?")[1].split("&");
                for (int i = 0; i < split.length; i++) {
                    OperativesSignBase operativesSignBase = new OperativesSignBase();
                    operativesSignBase.setKey(split[i].split("=")[0]);
                    operativesSignBase.setValue(split[i].split("=")[1]);
                    if (operativesSignBase.getKey().equals("workerId")) {
                        operativesSignBase.setValue("" + SharedPrefUtil.getString(SharedPrefUtil.ID));
                    }
                    list.add(operativesSignBase);
                }
                OperativesFacesSign(mycodedata.split("\\?")[0], list);
            } else if (mycodedata.contains("Permit/MyVerifyDetial")) {
                Intent intent = new Intent(MainActivity.this, WebActivity.class);
                intent.putExtra("url", mycodedata + "&workerId=" + SharedPrefUtil.getString(SharedPrefUtil.ID));
                startActivity(intent);
                Log.e("MyVerifyDetial", mycodedata + "&workerId=" + SharedPrefUtil.getString(SharedPrefUtil.ID));
            } else {
                ScanCodes(data.getStringExtra(QRCodeActivity.RESULT));
            }


        }
    }

    /**
     * URLDecoder解码
     */
    public static String toURLDecoder(String paramString) {
        if (paramString == null || paramString.equals("")) {
            return "";
        }
        try {
            String url = new String(paramString.getBytes(), "UTF-8");
            url = URLDecoder.decode(url, "UTF-8");
            return url;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    private void ScanCodes(final String courseId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("courseId", courseId);
                    jsonObject.put("workerId", SharedPrefUtil.getString(SharedPrefUtil.ID));
                    OkHttpUtils.doPostJson(MyURL.URL + "CourseSignIn", jsonObject.toString(), mHttpCallBack, 0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private void OperativesFacesSign(final String url, final List<OperativesSignBase> list) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject();
                    for (int i = 0; i < list.size(); i++) {
                        jsonObject.put(list.get(i).getKey(), list.get(i).getValue());
                    }
                    OkHttpUtils.doPostJson(url, jsonObject.toString(), mHttpCallBack, 2);
                    Log.e("OperativesFacesSign", "jsonObject:" + jsonObject.toString());
                    Log.e("OperativesFacesSign", "url:" + url);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void OperativesSign(final String url, final List<OperativesSignBase> list) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject();
                    for (int i = 0; i < list.size(); i++) {
                        jsonObject.put(list.get(i).getKey(), list.get(i).getValue());
                    }
                    jsonObject.put("workerId", "" + SharedPrefUtil.getString(SharedPrefUtil.ID));

                    OkHttpUtils.doPostJson(url, jsonObject.toString(), mHttpCallBack, 1);
                    Log.e("OperativesSign", "jsonObject:" + jsonObject.toString());
                    Log.e("OperativesSign", "url:" + url);
                } catch (JSONException e) {
                    e.printStackTrace();
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

        Log.e("response", "requestId:" + requestId + "--response:" + response);
        switch (requestId) {
            case 0:
                try {
                    mScanCodeBase = JSON.parseObject(response, ScanCodeBase.class);
                    Log.e("mScanCodeBase", "" + mScanCodeBase.isSuc());
                    if (mScanCodeBase.isSuc()) {//签到成功
                        Toast.makeText(this, "签到成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, mScanCodeBase.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 1:
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
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

    /**
     * 获取当前网络类型
     *
     * @return 0：没有网络 1：WIFI网络 2：WAP网络 3：NET网络
     */
    public int getNetworkType(Context mContext) {
        int netType = 0;
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return netType;
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_MOBILE) {
            Log.e("networkInfo", "-------------");
            main.setVisibility(View.GONE);
            String extraInfo = networkInfo.getExtraInfo();
            if (!TextUtils.isEmpty(extraInfo)) {
                if (extraInfo.toLowerCase().equals("cmnet")) {
                    netType = 3;
                } else {
                    netType = 2;
                }
            }
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            netType = 1;
        }

        return netType;
    }

    private void requestCameraPermission(final int RC_HANDLE_CAMERA_PERM) {

        final String[] permissions = new String[]{Manifest.permission.CAMERA};

        ActivityCompat.requestPermissions(this, permissions, RC_HANDLE_CAMERA_PERM);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode == RC_HANDLE_CAMERA_PERM_RGB) {
            Intent intent = new Intent(mContext, FaceDetectRGBActivity.class);
            startActivity(intent);
            return;
        }
    }


    @Override
    public void StowMainInfc() {
        finish();
    }

    @Override
    public void onBackPressed() {

    }


    //广播接收者
    public class LocatiopnBroadcast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //收到广播后的操作
            Toast.makeText(MainActivity.this, intent.getStringExtra("msg"), Toast.LENGTH_SHORT).show();
        }
    }
}
