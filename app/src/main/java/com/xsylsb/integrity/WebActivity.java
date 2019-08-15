package com.xsylsb.integrity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.xsylsb.integrity.mylogin.MyloginActivity;
import com.xsylsb.integrity.util.MyURL;
import com.xsylsb.integrity.util.dialog.BaseNiceDialog;
import com.xsylsb.integrity.util.dialog.NiceDialog;
import com.xsylsb.integrity.util.dialog.ViewConvertListener;
import com.xsylsb.integrity.util.dialog.ViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;


public class WebActivity extends AppCompatActivity {


    @BindView(R.id.wed_activity)
    ImageView wedActivity;
    @BindView(R.id.wed_title_activity)
    TextView wedTitleActivity;
    private ProgressBar progressBar;
    private WebView webView;
    public static final String KEY_URL = "url";
    public static final String KEY_TITLE = "title";
    private String mUrl = "";
    private String mTitle = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        ButterKnife.bind(this);
        initData();
        mUrl = getIntent().getStringExtra("url");
        Log.e("mUrl", mUrl);
        initView();
        webView.loadUrl(mUrl);
        wedActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });







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
                Log.e("rideUrlLoading", url);
                if (url.contains("Account/CourseQuestionBank")&&url.contains("type=1")) {
                    Log.e("urlPersonage考试", url);
                    Intent intent=new Intent(WebActivity.this, Examination_Activity.class);
                    intent.putExtra("url",url);
                    startActivity(intent);
                    //startActivity(new Intent(WebActivity.this, Examination_Activity.class));
                } else if (url.contains("Account/CourseQuestionBank")&&url.contains("type=0")) {
                    Log.e("urlPersonage练习", url);
                    Intent intent=new Intent(WebActivity.this, PracticeMode_Activity.class);
                    intent.putExtra("url",url);
                    startActivity(intent);
                } else if (url.contains("Worker/UpdatePwd")){
                    Toast.makeText(WebActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Log.e("else", "--------else");
                    webView.loadUrl(url);
                }




                return true;
            }
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                int code = errorCode / 100;
                if (code == 2){
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
        webSettings.setDisplayZoomControls(false);
        webSettings.setBuiltInZoomControls(false);
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