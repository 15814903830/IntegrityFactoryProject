package com.xsylsb.integrity.mianfragment.homepage.personage;


import android.content.Intent;
import android.content.IntentFilter;
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
import android.widget.TextView;

import com.xsylsb.integrity.Examination_Activity;
import com.xsylsb.integrity.MainActivity;
import com.xsylsb.integrity.PracticeMode_Activity;
import com.xsylsb.integrity.R;
import com.xsylsb.integrity.WebActivity;
import com.xsylsb.integrity.mianfragment.homepage.homepage.HomepageFragment;
import com.xsylsb.integrity.mianfragment.homepage.notice.NoticeFragment;
import com.xsylsb.integrity.mvp.MVPBaseFragment;
import com.xsylsb.integrity.mylogin.MyloginActivity;
import com.xsylsb.integrity.util.MyURL;
import com.xsylsb.integrity.util.StowMainInfc;
import com.xsylsb.integrity.util.dialog.BaseNiceDialog;
import com.xsylsb.integrity.util.dialog.NiceDialog;
import com.xsylsb.integrity.util.dialog.ViewConvertListener;
import com.xsylsb.integrity.util.dialog.ViewHolder;

/**
 * 个人
 */

public class PersonageFragment extends MVPBaseFragment<PersonageContract.View, PersonagePresenter> implements PersonageContract.View {

    private static StowMainInfc stowMainInfcss;
    private View mView;
    private ProgressBar progressBar;
    private WebView webView;
    private String mUrl = MyURL.URLL+"Worker/PersonalCenter";
    private BaseNiceDialog mBaseNiceDialog;
    public static final String KEY_URL = "url";
    public static final String KEY_TITLE = "title";
    private String mTitle = "";
    private boolean showlading=true;
    /**
     * Fragment 的构造函数。
     */
    public PersonageFragment() {
    }

    public static PersonageFragment newInstance(StowMainInfc stowMainInfc) {
        stowMainInfcss=stowMainInfc;
        return new PersonageFragment();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView=inflater.inflate(R.layout.homefragment,container,false);
        if (showlading){
            showLoading();
            showlading=false;
        }
        mUrl=mUrl+"?id="+MyURL.id;
        Log.e("Personageurl",mUrl);

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
                Log.e("urlPersonagessss",url);
              if(url.contains("Worker/Logout")){//退出
                    NiceDialog.init()
                            .setLayoutId(R.layout.exit_dialog)
                            .setConvertListener(new ViewConvertListener() {
                                @Override
                                protected void convertView(ViewHolder holder, final BaseNiceDialog dialog) {
                                    TextView hesitate = holder.getView(R.id.tv_hesitate);
                                    TextView exit = holder.getView(R.id.tv_exit);
                                    hesitate.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();
                                        }
                                    });

                                    exit.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            startActivity(new Intent(getContext(), MyloginActivity.class));
                                            stowMainInfcss.StowMainInfc();


                                        }
                                    });
                                }
                            })
                            .setDimAmount(0.3f)
                            .setShowBottom(false)
                            .setAnimStyle(R.style.PracticeModeAnimation)
                            .setOutCancel(false) //触摸外部是否取消
                            .show(getChildFragmentManager());
                }else if (url.contains("Worker/MyCourse")){
                  Log.e("urlssss",url);
                  Intent intent=new Intent(getContext(), WebActivity.class);
                  intent.putExtra(KEY_URL,url);
                  intent.putExtra(KEY_TITLE,mTitle);
                  startActivity(intent);
              }  else {
                  Log.e("else",url);
                    Intent intent=new Intent(getContext(), WebActivity.class);
                    intent.putExtra(KEY_URL,url);
                    intent.putExtra(KEY_TITLE,mTitle);
                    startActivity(intent);
              }

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
                mTitle=title;

            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {//加载网页完毕
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

    private void showEmpty() {
        webView.setVisibility(View.INVISIBLE);
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
