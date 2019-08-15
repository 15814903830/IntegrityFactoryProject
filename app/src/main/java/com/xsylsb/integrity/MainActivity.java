package com.xsylsb.integrity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
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
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.xsylsb.integrity.base.ScanCodeBase;
import com.xsylsb.integrity.face.activity.FaceDetectRGBActivity;
import com.xsylsb.integrity.mianfragment.homepage.homepage.HomepageFragment;
import com.xsylsb.integrity.mianfragment.homepage.notice.NoticeFragment;
import com.xsylsb.integrity.mianfragment.homepage.personage.PersonageFragment;
import com.xsylsb.integrity.mianfragment.homepage.scan.ScanFragment;
import com.xsylsb.integrity.mianfragment.homepage.train.TrainFragment;
import com.xsylsb.integrity.util.HttpCallBack;
import com.xsylsb.integrity.util.MyURL;
import com.xsylsb.integrity.util.OkHttpUtils;
import com.xsylsb.integrity.util.StowMainInfc;
import com.xsylsb.integrity.util.dialog.BaseNiceDialog;
import com.xsylsb.integrity.util.dialog.NiceDialog;
import com.xsylsb.integrity.util.dialog.ViewConvertListener;
import com.xsylsb.integrity.util.dialog.ViewHolder;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements HttpCallBack , StowMainInfc {

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        ButterKnife.bind(this);
        mContext=this;
        mHttpCallBack = this;
        Title = "欢迎您，" + getIntent().getStringExtra("name");
        mainTvHome.setSelected(true);
        //首页
        if (homepageFragment == null) {
            homepageFragment = HomepageFragment.newInstance();
        }
        showFragment(homepageFragment,"HOME");

    }

    @OnClick({R.id.main_ll_home, R.id.main_ll_train, R.id.main_ll_scan, R.id.main_ll_notice, R.id.main_ll_personage})
    public void MyOnClick(View view) {
        switch (view.getId()) {
            case R.id.main_ll_home:
                //首页
                if (homepageFragment == null) {
                    homepageFragment = HomepageFragment.newInstance();
                }
                showFragment(homepageFragment,"HOME");
                mainTvHome.setSelected(true);
                mainTvTrain.setSelected(false);
                mainTvScan.setSelected(false);
                mainTvNotice.setSelected(false);
                mainTvPersonage.setSelected(false);
                break;
            case R.id.main_ll_train:
                //培训
                if (mTrainFragment == null) {
                    mTrainFragment = TrainFragment.newInstance();
                }

                showFragment(mTrainFragment,"TRAIN");
                mainTvHome.setSelected(false);
                mainTvTrain.setSelected(true);
                mainTvScan.setSelected(false);
                mainTvNotice.setSelected(false);
                mainTvPersonage.setSelected(false);
                break;
            case R.id.main_ll_scan:
                //扫一扫
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
                                        //二维吗
                                        startActivityForResult(new Intent(MainActivity.this, QRCodeActivity.class), 0);
                                        dialog.dismiss();
                                    }
                                });

                                linearLayout1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //人脸识别
                                        int rc = ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA);
                                        if (rc == PackageManager.PERMISSION_GRANTED) {
                                            Intent intent = new Intent(mContext, FaceDetectRGBActivity.class);
                                            startActivity(intent);
                                        } else {
                                            requestCameraPermission(RC_HANDLE_CAMERA_PERM_RGB);
                                        }
                                        dialog.dismiss();
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
                break;
            case R.id.main_ll_notice:
                //通知
                if (mNoticeFragment == null) {
                    mNoticeFragment = NoticeFragment.newInstance();
                }
                showFragment(mNoticeFragment,"NOTICE");
                mainTvHome.setSelected(false);
                mainTvTrain.setSelected(false);
                mainTvScan.setSelected(false);
                mainTvNotice.setSelected(true);
                mainTvPersonage.setSelected(false);
                break;

            case R.id.main_ll_personage:
                //个人
                                if (mPersonageFragment==null){
                                    mPersonageFragment = PersonageFragment.newInstance(this);
                                }
                showFragment(mPersonageFragment,"PERSINAGE");
                mainTvHome.setSelected(false);
                mainTvTrain.setSelected(false);
                mainTvScan.setSelected(false);
                mainTvNotice.setSelected(false);
                mainTvPersonage.setSelected(true);
                break;
            default:
                break;
        }
    }


        private void showFragment(Fragment fragment,String tag){

        if (tag.equals(mtag)){
            return;
        }
                  FragmentTransaction fragmentTransaction=fm.beginTransaction();
            if (mFragment!=null){
                fragmentTransaction.hide(mFragment);
            }
            if (fragment.isAdded()){
                fragmentTransaction.show(fragment);
            }else {
                fragmentTransaction.add(R.id.main,fragment);
            }

            fragmentTransaction.commit();
            mFragment=fragment;
            mtag=tag;
        }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            //扫码获得的信息
            Log.e("data", data.getStringExtra(QRCodeActivity.RESULT));
            ScanCodes(data.getStringExtra(QRCodeActivity.RESULT));
        }
    }

    private void ScanCodes(final String courseId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("courseId", courseId);
                    jsonObject.put("workerId", MyURL.id);
                    OkHttpUtils.doPostJson(MyURL.URL + "CourseSignIn", jsonObject.toString(), mHttpCallBack, 0);
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
        mScanCodeBase = JSON.parseObject(response, ScanCodeBase.class);
        Log.e("mScanCodeBase", "" + mScanCodeBase.isSuc());
        if (mScanCodeBase.isSuc()) {//签到成功
            Toast.makeText(this, "签到成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, mScanCodeBase.getMsg(), Toast.LENGTH_SHORT).show();
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
}
