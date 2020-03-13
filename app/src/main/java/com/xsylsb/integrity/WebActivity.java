package com.xsylsb.integrity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.xsylsb.integrity.base.OperativesSignBase;
import com.xsylsb.integrity.base.ScanCodeBase;
import com.xsylsb.integrity.face.activity.LeadFaceDetectRGBActivity;
import com.xsylsb.integrity.util.HttpCallBack;
import com.xsylsb.integrity.util.MyURL;
import com.xsylsb.integrity.util.OkHttpUtils;
import com.xsylsb.integrity.util.SharedPrefUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class WebActivity extends AppCompatActivity implements HttpCallBack {


    /**
     * 不同方式的请求码
     */
    public static final int REQUEST_SELECT_FILE = 100;
    public final static int FILECHOOSER_RESULTCODE = 1;
    /**
     * 接收安卓5.0以上的
     */
    public ValueCallback<Uri[]> uploadMessage;
    /**
     * 接收5.0以下的
     */
    private ValueCallback<Uri> mUploadMessage;
    @BindView(R.id.wed_activity)
    ImageView wedActivity;
    @BindView(R.id.wed_title_activity)
    TextView wedTitleActivity;
    @BindView(R.id.iv_ewm)
    ImageView iv_ewm;
    private HttpCallBack mHttpCallBack;
    private static final int RC_HANDLE_CAMERA_PERM_RGB = 1;
    private ProgressBar progressBar;
    private WebView webView;
    public static final String KEY_URL = "url";
    public static final String KEY_TITLE = "title";
    private String mUrl = "";
    private String mTitle = "";
    private boolean mBoolean = false;
    private ScanCodeBase mScanCodeBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        mHttpCallBack = this;
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
        if (mUploadMessage != null) {
            //取消之后要告诉WebView不要再等待返回结果，设置为空就等于重置了状态,也是避免只能选择一次图片的原因
            mUploadMessage.onReceiveValue(null);
            mUploadMessage = null;
        }

        if (mBoolean) {
            webView.loadUrl(mUrl);
        }
        mBoolean = true;

        webView.reload();
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
                url=translation(url);
                if (url.contains("Permit/VerifyDetial")) {
                    String[] split = url.split("\\?")[0].split("/");
                    Log.e("setWebViewClient", split[split.length - 1]);
                    Intent intent = new Intent(WebActivity.this, PhotographActivity.class);
                    intent.putExtra("id", split[split.length - 1]);
                    startActivity(intent);
                } else if (url.contains("Account/CourseQuestionBank") && url.contains("type=1")) {
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
                } else if (url.contains("OperativesFacesSign")||url.contains("CourseFacesSign")) {

                    if (isNetworkConnected(WebActivity.this)) {
                        //人脸识别
                        int rc = ActivityCompat.checkSelfPermission(WebActivity.this, Manifest.permission.CAMERA);
                        if (rc == PackageManager.PERMISSION_GRANTED) {
                            //  Intent intent = new Intent(WebActivity.this, FaceDetectRGBActivity.class);
                            Intent intent = new Intent(WebActivity.this, LeadFaceDetectRGBActivity.class);
                            intent.putExtra("url", url);
                            startActivity(intent);
                        } else {
                            requestCameraPermission(RC_HANDLE_CAMERA_PERM_RGB);
                        }
                    } else {
                        Toast.makeText(WebActivity.this, "请检查网络链接", Toast.LENGTH_SHORT).show();
                    }
                    iv_ewm.setVisibility(View.VISIBLE);

                } else {
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

    private String translation(String content) {
        String replace = content.replace("&lt;", "<");
        String replace1 = replace.replace("&gt;", ">");
        String replace2 = replace1.replace("&amp;", "&");
        String replace3 = replace2.replace("&quot;", "\"");
        return replace3.replace("&copy;", "©");
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
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //缓存模式
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        //支持内容重新布局
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //将图片调整到适合webview的大小
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //支持自动加载图片
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


}