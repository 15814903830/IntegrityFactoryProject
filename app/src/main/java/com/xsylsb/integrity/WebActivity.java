package com.xsylsb.integrity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.xsylsb.integrity.base.OperativesSignBase;
import com.xsylsb.integrity.face.activity.FaceDetectRGBActivity;
import com.xsylsb.integrity.face.activity.LeadFaceDetectRGBActivity;
import com.xsylsb.integrity.mylogin.MyloginActivity;
import com.xsylsb.integrity.util.MyURL;
import com.xsylsb.integrity.util.dialog.BaseNiceDialog;
import com.xsylsb.integrity.util.dialog.NiceDialog;
import com.xsylsb.integrity.util.dialog.ViewConvertListener;
import com.xsylsb.integrity.util.dialog.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class WebActivity extends AppCompatActivity {


    @BindView(R.id.wed_activity)
    ImageView wedActivity;
    @BindView(R.id.wed_title_activity)
    TextView wedTitleActivity;
    @BindView(R.id.iv_ewm)
    ImageView iv_ewm;

    private static final int RC_HANDLE_CAMERA_PERM_RGB = 1;
    private ProgressBar progressBar;
    private WebView webView;
    public static final String KEY_URL = "url";
    public static final String KEY_TITLE = "title";
    private String mUrl = "";
    private String mTitle = "";
    private boolean mBoolean = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        ButterKnife.bind(this);
        initData();
        mUrl = getIntent().getStringExtra("url");
        Log.e("mUrl", mUrl);

        if (mUrl.contains("Permit/MyJob")) {
            iv_ewm.setVisibility(View.VISIBLE);
        } else if (mUrl.contains("Permit/MySecurityOfficer")) {
            iv_ewm.setVisibility(View.VISIBLE);
        } else if (mUrl.contains("Permit/MyCompanyCustody")) {
            iv_ewm.setVisibility(View.VISIBLE);
        } else if (mUrl.contains("Permit/MyResponsible")) {
            iv_ewm.setVisibility(View.VISIBLE);
        } else if (mUrl.contains("Permit/MyCustody")) {
            iv_ewm.setVisibility(View.VISIBLE);
        }

        initView();
        webView.loadUrl(mUrl);
        wedActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取webView的浏览记录
                WebBackForwardList mWebBackForwardList = webView.copyBackForwardList();
                //这里的判断是为了让页面在有上一个页面的情况下，跳转到上一个html页面，而不是退出当前activity
                if (mWebBackForwardList.getCurrentIndex() > 0) {
                    String historyUrl = mWebBackForwardList.getItemAtIndex(mWebBackForwardList.getCurrentIndex() - 1).getUrl();
                    webView.goBack();
                } else {
                    finish();
                }
            }
        });

        iv_ewm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(WebActivity.this, QRCode2Activity.class), 0);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mBoolean) {
            webView.loadUrl(mUrl);
        }
        mBoolean = true;
    }


    private void initData() {
        mUrl = getIntent().getStringExtra(KEY_URL);
        //mTitle = getIntent().getStringExtra(KEY_TITLE);
    }

    private void initView() {
        progressBar = findViewById(R.id.pb_web);
        webView = findViewById(R.id.wv_web);
        wedTitleActivity.setText(mTitle);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.e("setWebViewClient", url);
                if (url.contains("Account/CourseQuestionBank") && url.contains("type=1")) {
                    iv_ewm.setVisibility(View.GONE);
                    Log.e("urlPersonage考试", url);
                    Intent intent = new Intent(WebActivity.this, Examination_Activity.class);
                    intent.putExtra("url", url);
                    startActivity(intent);
                    //startActivity(new Intent(WebActivity.this, Examination_Activity.class));
                } else if (url.contains("Account/CourseQuestionBank") && url.contains("type=0")) {
                    Log.e("urlPersonage练习", url);
                    iv_ewm.setVisibility(View.GONE);
                    Intent intent = new Intent(WebActivity.this, PracticeMode_Activity.class);
                    intent.putExtra("url", url);
                    startActivity(intent);
                } else if (url.contains("Worker/UpdatePwd")) {
                    iv_ewm.setVisibility(View.GONE);
                    Toast.makeText(WebActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else if (url.contains("Permit/MyJob")) {
                    webView.loadUrl(url);
                    iv_ewm.setVisibility(View.VISIBLE);
                } else if (url.contains("Permit/MySecurityOfficer")) {
                    webView.loadUrl(url);
                    iv_ewm.setVisibility(View.VISIBLE);
                } else if (url.contains("Permit/MyCompanyCustody")) {
                    webView.loadUrl(url);
                    iv_ewm.setVisibility(View.VISIBLE);
                } else if (url.contains("Permit/MyResponsible")) {
                    webView.loadUrl(url);
                    iv_ewm.setVisibility(View.VISIBLE);
                } else if (url.contains("Permit/MyCustody")) {
                    webView.loadUrl(url);
                    iv_ewm.setVisibility(View.VISIBLE);
                } else if (url.contains("OperativesFacesSign")) {
                    if (isNetworkConnected(WebActivity.this)) {
                        //人脸识别
                        int rc = ActivityCompat.checkSelfPermission(WebActivity.this, Manifest.permission.CAMERA);
                        if (rc == PackageManager.PERMISSION_GRANTED) {
                          //  Intent intent = new Intent(WebActivity.this, FaceDetectRGBActivity.class);
                                       Intent intent = new Intent(WebActivity.this, LeadFaceDetectRGBActivity.class);
                            intent.putExtra("url",url);
                            startActivity(intent);
                        } else {
                            requestCameraPermission(RC_HANDLE_CAMERA_PERM_RGB);
                        }
                    } else {
                        Toast.makeText(WebActivity.this, "请检查网络链接", Toast.LENGTH_SHORT).show();
                    }

                    webView.loadUrl(url);
                    iv_ewm.setVisibility(View.VISIBLE);
                }
                else {
                    iv_ewm.setVisibility(View.GONE);
                    Log.e("else", "--------else");
                    webView.loadUrl(url);
                }
                return true;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                int code = errorCode / 100;
                if (code == 2) {
                    super.onReceivedError(view, errorCode, description, failingUrl);
                } else {
                    showEmpty();
                }
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                wedTitleActivity.setText(title);

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
        initWebSettings();
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
    private void requestCameraPermission(final int RC_HANDLE_CAMERA_PERM) {

        final String[] permissions = new String[]{Manifest.permission.CAMERA};

        ActivityCompat.requestPermissions(this, permissions, RC_HANDLE_CAMERA_PERM);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode == RC_HANDLE_CAMERA_PERM_RGB) {
            Intent intent = new Intent(WebActivity.this, LeadFaceDetectRGBActivity.class);
            startActivity(intent);
            return;
        }
    }
    private void showEmpty() {
        webView.setVisibility(View.GONE);
    }

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
    }


    //    @Override
    //    public void onBackPressed() {
    //        if (webView.canGoBack()) {
    //            webView.goBack();
    //        } else {
    //            super.onBackPressed();
    //        }
    //    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            //释放资源
            webView.destroy();
            webView = null;
        }
    }


}