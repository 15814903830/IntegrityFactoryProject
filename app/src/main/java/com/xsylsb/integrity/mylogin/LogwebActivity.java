package com.xsylsb.integrity.mylogin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.maning.updatelibrary.InstallUtils;
import com.xsylsb.integrity.MainActivity;
import com.xsylsb.integrity.MainApplication;
import com.xsylsb.integrity.R;
import com.xsylsb.integrity.WebActivity;
import com.xsylsb.integrity.base.VersionBase;
import com.xsylsb.integrity.util.BaseUtils;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.xsylsb.integrity.mylogin.MyloginActivity.verifyStoragePermissions;

public class LogwebActivity extends AppCompatActivity implements HttpCallBack {
    private WebView webView;
    private HttpCallBack mHttpCallBack;
    private VersionBase mVersionBase;
    private TextView updateprogress;
    private String apkDownloadPath = "";
    private Activity context;
    private InstallUtils.DownloadCallBack downloadCallBack;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logweb);//http://liugangapi.gx11.cn/Worker/Credit?id=24
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        initView();
        webView.loadUrl("http://liugangapi.gx11.cn/Worker/Credit?id="+ MainApplication.id);
        MainApplication.isBooleanface=true;
        context = this;
        mHttpCallBack = this;
        if (isNetworkConnected(this)){
            getVersion();
        }
        initCallBack();
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Intent intent=new Intent(LogwebActivity.this,WebActivity.class);
                intent.putExtra("url",url);
                startActivity(intent);
                return true;
            }

        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);

            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                //                progressBar.setProgress(newProgress);
                //                if (newProgress == 100) {
                //                    progressBar.setVisibility(View.INVISIBLE);
                //                } else {
                //                    progressBar.setVisibility(View.VISIBLE);
                //                }
            }
        });
    }

    public void ShowMain(View view) {
        startActivity(new Intent(LogwebActivity.this, MainActivity.class));
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void initView() {
        webView = findViewById(R.id.wv_web);
        initWebSettings();
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void initWebSettings() {
        WebSettings webSettings = webView.getSettings();
        //5.0以上开启混合模式加载
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        //允许js代码
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        //禁用放缩
        webSettings.setDisplayZoomControls(true);
        webSettings.setBuiltInZoomControls(true);
        //禁用文字缩放
        webSettings.setTextZoom(100);
        //自动加载图片
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setDomStorageEnabled(true);
    }

    @Override
    public void onBackPressed() {

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
        mVersionBase = JSON.parseObject(response, VersionBase.class);
        if (BaseUtils.checkVersion(LogwebActivity.this, "" + mVersionBase.getAndroidAppVersion())){
            Log.e("verson", "更新操作");
            NiceDialog.init()
                    .setLayoutId(R.layout.version_dialog)
                    .setConvertListener(new ViewConvertListener() {
                        @Override
                        protected void convertView(ViewHolder holder, final BaseNiceDialog dialog) {
                            TextView update = holder.getView(R.id.version_update);//更新
                            TextView suspendupdate = holder.getView(R.id.version_suspendupdate);//取消更新
                            TextView tv_androidDescription = holder.getView(R.id.tv_androidDescription);//取消更新
                            if (mVersionBase.getAndroidForceUpdate().toString().equals("true")){
                                suspendupdate.setVisibility(View.GONE);
                            }
                            tv_androidDescription.setText("" + mVersionBase.getAndroidDescription());
                            updateprogress = holder.getView(R.id.update_progress);
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
                                        verifyStoragePermissions(LogwebActivity.this);
                                        PermissionUtils.requestSDCardReadPermission(context, 100);
                                    } else {
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
}
