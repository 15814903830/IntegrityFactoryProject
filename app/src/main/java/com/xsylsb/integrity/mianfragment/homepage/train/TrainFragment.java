package com.xsylsb.integrity.mianfragment.homepage.train;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.xsylsb.integrity.MainApplication;
import com.xsylsb.integrity.R;
import com.xsylsb.integrity.WebActivity;
import com.xsylsb.integrity.mvp.MVPBaseFragment;
import com.xsylsb.integrity.util.MyURL;
import com.xsylsb.integrity.util.SharedPrefUtil;
import com.xsylsb.integrity.util.dialog.BaseNiceDialog;
import com.xsylsb.integrity.util.dialog.NiceDialog;
import com.xsylsb.integrity.util.dialog.ViewConvertListener;
import com.xsylsb.integrity.util.dialog.ViewHolder;

/**
 * 培训
 */

public class TrainFragment extends MVPBaseFragment<TrainContract.View, TrainPresenter> implements TrainContract.View {
    private View mView;
    private ProgressBar progressBar;
    private WebView webView;
    private String mUrl = MyURL.URLL+"Permit/Index";
    private BaseNiceDialog mBaseNiceDialog;
    private boolean showlading=true;
    /**
     * Fragment 的构造函数。
     */
    public TrainFragment() {
    }

    public static TrainFragment newInstance() {
        return new TrainFragment();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView=inflater.inflate(R.layout.homefragment,container,false);
        if (showlading){
            showLoading();
            showlading=false;
        }
        mUrl=mUrl+"?id="+ SharedPrefUtil.getString(SharedPrefUtil.ID)+"&page=1&limit=2147483647";
        Log.e("NOticeurl",mUrl);
        initView();
        webView.loadUrl(mUrl);
        return mView;
    }
    private void initView() {
        progressBar = mView.findViewById(R.id.pb_web);
        webView = mView.findViewById(R.id.wv_web);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //    if (url.contains("Worker/CourseDetail")){
//                    Intent intent=new Intent(getContext(), WebActivity.class);
//                    intent.putExtra("url",url);
//                    startActivity(intent);
//                }else {
//                    webView.loadUrl(url);
//                }
                    Intent intent=new Intent(getContext(), WebActivity.class);
                    intent.putExtra("url",url);
                    startActivity(intent);
                return true;

            }
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                int code = errorCode / 100;
                if (code == 2) {
                    webView.setVisibility(View.VISIBLE);
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
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    mBaseNiceDialog.dismiss();
                }
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

    @Override
    public void onResume() {
        super.onResume();
        webView.reload();
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
    private void showEmpty() {
        webView.setVisibility(View.INVISIBLE);
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
    public void onDestroyView() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onDestroyView();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            //释放资源
            webView.destroy();
            webView = null;
        }
    }
    /**
     * 显示loading
     */
    public void showLoading() {
        NiceDialog.init()
                .setLayoutId(R.layout.dialog_loading_layout)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    protected void convertView(ViewHolder holder, BaseNiceDialog dialog) {
                        mBaseNiceDialog = dialog;
                    }
                })
                .setOutCancel(false)
                .setWidth(48)
                .setHeight(48)
                .setShowBottom(false)
                .show(getChildFragmentManager());
    }
}
