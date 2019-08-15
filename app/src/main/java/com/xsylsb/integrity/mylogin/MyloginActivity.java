package com.xsylsb.integrity.mylogin;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.maning.updatelibrary.InstallUtils;
import com.xsylsb.integrity.MainActivity;
import com.xsylsb.integrity.R;
import com.xsylsb.integrity.base.LoginBase;
import com.xsylsb.integrity.base.VersionBase;
import com.xsylsb.integrity.face.activity.ui.LoginFaceDetectRGBActivity;
import com.xsylsb.integrity.util.HttpCallBack;
import com.xsylsb.integrity.util.MyURL;
import com.xsylsb.integrity.util.OkHttpUtils;
import com.xsylsb.integrity.util.RequestParams;
import com.xsylsb.integrity.util.dialog.BaseNiceDialog;
import com.xsylsb.integrity.util.dialog.NiceDialog;
import com.xsylsb.integrity.util.dialog.ViewConvertListener;
import com.xsylsb.integrity.util.dialog.ViewHolder;
import com.xsylsb.integrity.versionupdating.Constants;
import com.xsylsb.integrity.versionupdating.PermissionUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MyloginActivity extends AppCompatActivity implements HttpCallBack {
    @BindView(R.id.ll_face_login)
    LinearLayout llFaceLogin;
    private Activity context;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.img_password)
    ImageView imgPassword;
    @BindView(R.id.im_face)
    ImageView imFace;
    @BindView(R.id.identitycard)
    EditText identitycard;
    @BindView(R.id.btn_login)
    Button btnLogin;
    private boolean mbDisplayFlg = false;
    private HttpCallBack mHttpCallBack;
    private LoginBase mLoginBase;
    private VersionBase mVersionBase;
    private TextView updateprogress;
    private InstallUtils.DownloadCallBack downloadCallBack;
    private String apkDownloadPath = "http://119.27.173.65:10086/xiaobaisi/picture/liugang.apk";
    private SharedPreferences sp;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};
    private static final int RC_HANDLE_CAMERA_PERM_RGB = 1;

    //第一参数为保存的文件名，第二个为保存的模型，当文件存在就读取，如果不存在就创建
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);

        showthispersondialog();//判断人脸识别是否查无此人
        sp = getSharedPreferences("info", MODE_PRIVATE);

        context = this;
        mHttpCallBack = this;
        getVersion();
        Log.e("codename", getAppVersionName(this));
        initCallBack();
    }

    private void showthispersondialog() {//判断人脸识别是否查无此人
        if (getIntent().getStringExtra("thispersondialog") != null) {
            if (getIntent().getStringExtra("thispersondialog").equals("true")) {
                thispersondialog(getIntent().getStringExtra("msg"));//查无此人弹窗
            }
        }
    }


    @OnClick({R.id.password, R.id.img_password, R.id.btn_login, R.id.ll_face_login})
    public void MyonClick(View view) {
        switch (view.getId()) {
            case R.id.img_password:
                //设置密码是否可见
                if (!mbDisplayFlg) {
                    imgPassword.setImageResource(R.mipmap.denglu5);
                    // display password text, for example "123456"
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    imgPassword.setImageResource(R.mipmap.denglu4);
                    // hide password, display "."
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                mbDisplayFlg = !mbDisplayFlg;
                password.postInvalidate();
                break;
            case R.id.btn_login:
                Login();
                break;
            case R.id.ll_face_login:
                //人脸识别
                int rc = ActivityCompat.checkSelfPermission(MyloginActivity.this, Manifest.permission.CAMERA);
                if (rc == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(MyloginActivity.this, LoginFaceDetectRGBActivity.class);
                    startActivity(intent);
                } else {
                    requestCameraPermission(RC_HANDLE_CAMERA_PERM_RGB);
                }
                //人脸识别
                break;
        }
    }

    private void requestCameraPermission(final int RC_HANDLE_CAMERA_PERM) {

        final String[] permissions = new String[]{Manifest.permission.CAMERA};

        ActivityCompat.requestPermissions(this, permissions, RC_HANDLE_CAMERA_PERM);
    }

    private void Login() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RequestParams> list = new ArrayList<>();
                list.add(new RequestParams("idno", identitycard.getText().toString()));
                list.add(new RequestParams("password", password.getText().toString()));
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("idno", identitycard.getText().toString());
                    jsonObject.put("password", password.getText().toString());
                    Log.e("login", MyURL.URL + "Login");
                    OkHttpUtils.doPostJson(MyURL.URL + "Login", jsonObject.toString(), mHttpCallBack, 0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void getVersion() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RequestParams> list = new ArrayList<>();
                list.add(new RequestParams("id", "1"));
                OkHttpUtils.doGet(MyURL.URL + "AppManage", list, mHttpCallBack, 1);
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

        switch (requestId) {
            case 0:
                Log.e("responsegg",response.toString());
                mLoginBase = JSON.parseObject(response, LoginBase.class);
                Log.e("isSuc", "" + mLoginBase.isSuc());
                if (mLoginBase.isSuc()) {
                    MyURL.id = "" + mLoginBase.getData().getId();
                    //登陆操作
                    //验证账号密码，跳转到主页
//                    Intent intent = new Intent();
//                    intent.putExtra("name", mLoginBase.getData().getFullName());
//                    intent.setClass(this, MainActivity.class);
//                    startActivity(intent);
                     startActivity(new Intent(MyloginActivity.this,LogwebActivity.class));
                    //获取Editor
                    SharedPreferences.Editor editor = sp.edit();
                    //输入内容
                    editor.putString("number", identitycard.getText().toString());
                    editor.putString("password", password.getText().toString());
                    //必须提交才会生效，也可以使用apply
                    editor.commit();
                    finish();
                } else {
                    Toast.makeText(this, "请输入正确的用户信息", Toast.LENGTH_SHORT).show();
                }
                break;
            case 1:
                Log.e("response", response);
                mVersionBase = JSON.parseObject(response, VersionBase.class);
                if (getAppVersionName(this).equals(mVersionBase.getAndroidAppVersion())) {
                    Log.e("verson", "版本相同");
                } else {
                    Log.e("verson", "更新操作");
                    NiceDialog.init()
                            .setLayoutId(R.layout.version_dialog)
                            .setConvertListener(new ViewConvertListener() {
                                @Override
                                protected void convertView(ViewHolder holder, final BaseNiceDialog dialog) {
                                    TextView update = holder.getView(R.id.version_update);//更新
                                    TextView suspendupdate = holder.getView(R.id.version_suspendupdate);//取消更新
                                    TextView tv_androidDescription = holder.getView(R.id.tv_androidDescription);//取消更新
                                    tv_androidDescription.setText("" + mVersionBase.getAndroidDescription());
                                    updateprogress = holder.getView(R.id.update_progress);//取消更新
                                    update.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            //   initCallBack();
                                            Toast.makeText(context, "正在更新请勿退出", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent("com.example.downloadandinstallapk.apk");
                                            //也可以像注释这样写
                                            sendBroadcast(intent);//发送标准广播
                                            sendOrderedBroadcast(intent, null);//发送有序广播
                                            //意思就是发送值为com.example.mymessage的这样一条广播
                                            //申请SD卡权限
                                            if (!PermissionUtils.isGrantSDCardReadPermission(context)) {
                                                Log.e("if", "-----");
                                                verifyStoragePermissions(MyloginActivity.this);
                                                PermissionUtils.requestSDCardReadPermission(context, 100);
                                            } else {
                                                Log.e("apk", "" + mVersionBase.getAndroidDownloadUrl());
                                                InstallUtils.with(context)
                                                        //必须-下载地址
                                                        .setApkUrl("" + mVersionBase.getAndroidDownloadUrl())
                                                        //非必须-下载保存的文件的完整路径+name.apk
                                                        .setApkPath(Constants.APK_SAVE_PATH)
                                                        //非必须-下载回调
                                                        .setCallBack(downloadCallBack)
                                                        //开始下载
                                                        .startDownload();
                                            }
                                        }
                                    });
                                    suspendupdate.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();
                                        }
                                    });

                                }
                            })
                            .setDimAmount(0.3f)
                            .setShowBottom(false)
                            .setAnimStyle(R.style.PracticeModeAnimation)
                            .setOutCancel(false) //触摸外部是否取消
                            .show(getSupportFragmentManager());
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
     * 返回当前程序版本号
     */
    public static String getAppVersionCode(Context context) {
        int versioncode = 0;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            // versionName = pi.versionName;
            versioncode = pi.versionCode;
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versioncode + "";
    }

    /**
     * 返回当前程序版本名
     */
    public static String getAppVersionName(Context context) {
        String versionName = null;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }


    private void initCallBack() {
        downloadCallBack = new InstallUtils.DownloadCallBack() {
            @Override
            public void onStart() {
                //                tv_progress.setText("0%");
                //                tv_info.setText("正在下载...");
                //                btnDownload.setClickable(false);
                //                btnDownload.setBackgroundResource(R.color.colorPrimary);
            }

            @Override
            public void onComplete(String path) {
                apkDownloadPath = path;
                //                tv_progress.setText("100%");
                //                tv_info.setText("下载成功");
                //                btnDownload.setClickable(true);
                //                btnDownload.setBackgroundResource(R.color.colorPrimary);

                //先判断有没有安装权限
                InstallUtils.checkInstallPermission(context, new InstallUtils.InstallPermissionCallBack() {
                    @Override
                    public void onGranted() {
                        //去安装APK
                        installApk(apkDownloadPath);
                    }

                    @Override
                    public void onDenied() {
                        //弹出弹框提醒用户
                        AlertDialog alertDialog = new AlertDialog.Builder(context)
                                .setTitle("温馨提示")
                                .setMessage("必须授权才能安装APK，请设置允许安装")
                                .setNegativeButton("取消", null)
                                .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //打开设置页面
                                        InstallUtils.openInstallPermissionSetting(context, new InstallUtils.InstallPermissionCallBack() {
                                            @Override
                                            public void onGranted() {
                                                //去安装APK
                                                installApk(apkDownloadPath);
                                            }

                                            @Override
                                            public void onDenied() {
                                                //还是不允许咋搞？
                                            }
                                        });
                                    }
                                })
                                .create();
                        alertDialog.show();
                    }
                });
            }

            @Override
            public void onLoading(long total, long current) {
                //内部做了处理，onLoading 进度转回progress必须是+1，防止频率过快
                int progress = (int) (current * 100 / total);
                updateprogress.setText("更新中：" + progress + "%");
            }

            @Override
            public void onFail(Exception e) {
            }

            @Override
            public void cancle() {
            }
        };
    }

    private void installApk(String path) {
        InstallUtils.installAPK(context, path, new InstallUtils.InstallCallBack() {
            @Override
            public void onSuccess() {
                //onSuccess：表示系统的安装界面被打开
                //防止用户取消安装，在这里可以关闭当前应用，以免出现安装被取消
                Toast.makeText(context, "正在安装程序", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(Exception e) {
                //tv_info.setText("安装失败:" + e.toString());
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public static void verifyStoragePermissions(Activity activity) {

        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void thispersondialog(final String msg) {//查无此人
        NiceDialog.init()
                .setLayoutId(R.layout.thisperson_dialog)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    protected void convertView(ViewHolder holder, final BaseNiceDialog dialog) {
                        TextView textView = holder.getView(R.id.tv_roger);//知道了
                        TextView textViewmsg = holder.getView(R.id.tv_thisperson_msg);
                        textViewmsg.setText(msg);
                        textView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                    }
                })
                .setDimAmount(0.3f)
                .setShowBottom(false)
                .setAnimStyle(R.style.PracticeModeAnimation)
                .setOutCancel(false) //触摸外部是否取消
                .show(getSupportFragmentManager());
    }

}
