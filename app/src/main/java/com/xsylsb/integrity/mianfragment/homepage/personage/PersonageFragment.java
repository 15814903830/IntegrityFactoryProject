package com.xsylsb.integrity.mianfragment.homepage.personage;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.xsylsb.integrity.ControlActivity;
import com.xsylsb.integrity.MainActivity;
import com.xsylsb.integrity.MainApplication;
import com.xsylsb.integrity.PracticeMode_Activity2;
import com.xsylsb.integrity.R;
import com.xsylsb.integrity.WebActivity;
import com.xsylsb.integrity.base.ScanAddFaceBase;
import com.xsylsb.integrity.face.activity.AddFaceRGBActivity;
import com.xsylsb.integrity.mianfragment.homepage.ScanAddFacePersonAdapter;
import com.xsylsb.integrity.mvp.MVPBaseFragment;
import com.xsylsb.integrity.mylogin.MyloginActivity;
import com.xsylsb.integrity.util.HttpCallBack;
import com.xsylsb.integrity.util.KeyBoardUtils;
import com.xsylsb.integrity.util.MyURL;
import com.xsylsb.integrity.util.OkHttpUtils;
import com.xsylsb.integrity.util.RequestParams;
import com.xsylsb.integrity.util.SharedPrefUtil;
import com.xsylsb.integrity.util.StowMainInfc;
import com.xsylsb.integrity.util.dialog.BaseNiceDialog;
import com.xsylsb.integrity.util.dialog.NiceDialog;
import com.xsylsb.integrity.util.dialog.ViewConvertListener;
import com.xsylsb.integrity.util.dialog.ViewHolder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * 个人
 */

public class PersonageFragment extends MVPBaseFragment<PersonageContract.View, PersonagePresenter> implements PersonageContract.View, HttpCallBack, PersonAdapter.defaultAddress, TpyeAdapter.gettpyeid, ScanAddFacePersonAdapter.AddFacefot {

    private String TAG = "PersonageFragment";
    private static StowMainInfc stowMainInfcss;
    private View mView;
    private ProgressBar progressBar;
    private WebView webView;
    private String mUrl = MyURL.URLL + "Worker/PersonalCenter";
    private BaseNiceDialog mBaseNiceDialog;
    public static final String KEY_URL = "url";
    public static final String KEY_TITLE = "title";
    private String mTitle = "";
    private boolean showlading = true;
    private HttpCallBack mHttpCallBack;
    private String id = "";
    TextView onpersonal;
    private LinearLayout llzhongjiang;
    private List<TypeBase> typelist;
    LinearLayout Immediately;
    RecyclerView recyclerView;
    LinearLayout searchagain;
    LinearLayout personal;
    TextView tvfullName;
    EditText etnoid;
    LinearLayout search;
    TextView tvsearch;
    TextView tvsearchagain;
    TextView tv_idno;
    String idno = "";
    private SharedPreferences sp;
    private String tpyeid = "";
    private static final int RC_HANDLE_CAMERA_PERM_RGB = 1;

    /**
     * Fragment 的构造函数。
     */
    public PersonageFragment() {
    }

    public static PersonageFragment newInstance(StowMainInfc stowMainInfc) {
        stowMainInfcss = stowMainInfc;
        return new PersonageFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.homefragment, container, false);
        if (showlading) {
            showLoading();
            showlading = false;
        }
        mUrl = mUrl + "?id=" + SharedPrefUtil.getString(SharedPrefUtil.ID);
        Log.e(TAG, mUrl);
        initView();
        webView.loadUrl(mUrl);
        mHttpCallBack = this;
        sp = getActivity().getSharedPreferences("info", MODE_PRIVATE);
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        webView.reload();
    }

    private void initView() {
        progressBar = mView.findViewById(R.id.pb_web);
        webView = mView.findViewById(R.id.wv_web);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                Log.e(TAG, url);
                if (url.contains("Worker/Logout")) {//退出
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
                                            sp.edit().clear().commit();

                                        }
                                    });
                                }
                            })
                            .setDimAmount(0.3f)
                            .setShowBottom(false)
                            .setAnimStyle(R.style.PracticeModeAnimation)
                            .setOutCancel(false) //触摸外部是否取消
                            .show(getChildFragmentManager());
                } else if (url.contains("Worker/MyCourse")) {
                    Log.e(TAG, url);
                    Intent intent = new Intent(getContext(), WebActivity.class);
                    intent.putExtra(KEY_URL, url);
                    intent.putExtra(KEY_TITLE, mTitle);
                    startActivity(intent);
                } else if (url.contains("Worker/SearchSubordinate")) {
                    searchid(0);//搜索下属
                } else if (url.contains("Worker/InputWorker")) {
                    searchid(1);//搜索下属
                } else if (url.contains("FirstQuestionClassify")) {
                    getpracticeclassify();//获取练习分类
                    Log.e(TAG, "FirstQuestionClassify");
                } else if (url.contains("Home/ChangingOver")) {

                    startActivity(new Intent(getContext(), ControlActivity.class));
                } else {
                    Log.e(TAG, url);
                    Intent intent = new Intent(getContext(), WebActivity.class);
                    intent.putExtra(KEY_URL, url);
                    intent.putExtra(KEY_TITLE, mTitle);
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
                mTitle = title;

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
        webSettings.setDisplayZoomControls(true);
        webSettings.setBuiltInZoomControls(true);
        //禁用文字缩放
        webSettings.setTextZoom(100);
        //自动加载图片
        webSettings.setLoadsImagesAutomatically(true);
    }


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


    private void getmessageid(final String idno, int type) {
        if (type == 0) {//搜索下属
            new Thread(new Runnable() {
                @Override
                public void run() {
                    List<RequestParams> list = new ArrayList<>();
                    list.add(new RequestParams("idNo", idno));
                    list.add(new RequestParams("more", "true"));
                    OkHttpUtils.doGet(MyURL.URL + "SearchSubordinate/" + SharedPrefUtil.getString(SharedPrefUtil.ID), list, mHttpCallBack, 0);
                }
            }).start();
        } else {//搜索人员添加人脸或替换
            new Thread(new Runnable() {
                @Override
                public void run() {
                    List<RequestParams> list = new ArrayList<>();
                    list.add(new RequestParams("q", idno));
                    OkHttpUtils.doGet(MyURL.URL + "SearchInputWorker/" + SharedPrefUtil.getString(SharedPrefUtil.ID), list, mHttpCallBack, 2);
                }
            }).start();

        }
    }

    private void getpracticeclassify() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpUtils.doGet(MyURL.URL + "FirstQuestionClassify", mHttpCallBack, 1);
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
        Log.e(TAG, response);
        switch (requestId) {
            case 0:
                onHandler0(response, 0);
                break;
            case 1:
                typelist = JSON.parseArray(response, TypeBase.class);
                selectType(typelist);
                break;
            case 2:
                onHandler0(response, 1);
                break;
        }
    }

    private void onHandler0(String response, int tpye) {
        JSONObject jsonObject = null;
        try {
            if (tpye == 0) {
                Log.e(TAG, "else");
                List<SeekBase> mlist = JSON.parseArray(response, SeekBase.class);
                tv_idno.setText("" + mlist.size());
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                        LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(layoutManager);
                PersonAdapter personAdapter = new PersonAdapter(getContext(), mlist, this);
                recyclerView.setAdapter(personAdapter);
                personAdapter.notifyDataSetChanged();
                Immediately.setVisibility(View.VISIBLE);
                personal.setVisibility(View.VISIBLE);
                llzhongjiang.setVisibility(View.VISIBLE);
                searchagain.setVisibility(View.GONE);
                onpersonal.setVisibility(View.GONE);
                search.setVisibility(View.GONE);
                etnoid.setVisibility(View.GONE);

            } else {
                ScanAddFaceBase scanAddFaceBase = JSON.parseObject(response, ScanAddFaceBase.class);
                tv_idno.setText("" + scanAddFaceBase.getData().size());
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                        LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(layoutManager);
                ScanAddFacePersonAdapter personAdapter = new ScanAddFacePersonAdapter(getContext(), scanAddFaceBase.getData(), this);
                recyclerView.setAdapter(personAdapter);
                personAdapter.notifyDataSetChanged();
                Immediately.setVisibility(View.VISIBLE);
                personal.setVisibility(View.VISIBLE);
                llzhongjiang.setVisibility(View.VISIBLE);
                searchagain.setVisibility(View.GONE);
                onpersonal.setVisibility(View.GONE);
                search.setVisibility(View.GONE);
                etnoid.setVisibility(View.GONE);
            }
            {
                jsonObject = new JSONObject(response);
                if (!jsonObject.getBoolean("suc")) {
                    //没有找到您的下属人员信息
                    Log.e(TAG, "false");
                    onpersonal.setText(jsonObject.getString("msg"));
                    onpersonal.setVisibility(View.VISIBLE);
                    llzhongjiang.setVisibility(View.VISIBLE);
                    etnoid.setVisibility(View.GONE);
                    personal.setVisibility(View.GONE);
                    search.setVisibility(View.GONE);
                    searchagain.setVisibility(View.VISIBLE);
                }
            }
        } catch (JSONException e) {
            Log.e(TAG, "response" + e.toString());
            e.printStackTrace();
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

    @Override
    public void seeki(int i) {
        Intent intent = new Intent(getContext(), PersonagewebActivity.class);
        intent.putExtra("id", i + "");
        startActivity(intent);
    }

    /**
     * 查询人员下属名单
     */
    public void searchid(final int type) {
        NiceDialog.init()
                .setLayoutId(R.layout.dialog_search)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    protected void convertView(ViewHolder holder, final BaseNiceDialog dialog) {
                        llzhongjiang = holder.getView(R.id.ll_zhongjiang);//中部
                        search = holder.getView(R.id.ll_search);//搜索
                        searchagain = holder.getView(R.id.ll_search_again);//重新搜索
                        Immediately = holder.getView(R.id.ll_Immediately_check);//立即查看和重新搜索
                        onpersonal = holder.getView(R.id.tv_onpersonal);//您不是此人上级,无法查看此人信息
                        etnoid = holder.getView(R.id.et_search_noid);//输入内容
                        personal = holder.getView(R.id.ll_personal);//是否查看
                        tvfullName = holder.getView(R.id.tv_fullName);//名字
                        recyclerView = holder.getView(R.id.dialog_recycl);
                        tvsearch = holder.getView(R.id.tv_search);
                        tvsearchagain = holder.getView(R.id.tv_search_again);
                        tv_idno = holder.getView(R.id.tv_idno);
                        tvsearch.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {//搜索
                                Log.e("search", "search");
                                if (etnoid.getText().toString().equals("")) {
                                    Toast.makeText(getContext(), "请输入信息", Toast.LENGTH_SHORT).show();
                                } else {
                                    KeyBoardUtils.closeKeybord(etnoid, getContext());
                                    idno = etnoid.getText().toString();
                                    getmessageid(etnoid.getText().toString(), type);//调起接口查询
                                }
                            }
                        });


                        tvsearchagain.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {//重新搜索
                                llzhongjiang.setVisibility(View.GONE);
                                etnoid.setVisibility(View.VISIBLE);
                                search.setVisibility(View.VISIBLE);
                                searchagain.setVisibility(View.GONE);
                                etnoid.setText("");
                            }
                        });
                        TextView tvImmediatelysearch = holder.getView(R.id.tv_Immediately_again);//重新搜索
                        tvImmediatelysearch.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                llzhongjiang.setVisibility(View.GONE);
                                etnoid.setVisibility(View.VISIBLE);
                                search.setVisibility(View.VISIBLE);
                                searchagain.setVisibility(View.GONE);
                                Immediately.setVisibility(View.GONE);
                                etnoid.setText("");
                            }
                        });
                    }
                })
                .setDimAmount(0.1f)
                .setShowBottom(false)
                .setOutCancel(true)
                .setAnimStyle(R.style.PracticeModeAnimation)
                .show(getChildFragmentManager());
    }

    private void addface(String noid) {//添加人脸
        //人脸识别
        int rc = ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA);
        if (rc == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(getContext(), AddFaceRGBActivity.class);//noid
            intent.putExtra("noid", noid);
            getContext().startActivity(intent);
        } else {
            requestCameraPermission(RC_HANDLE_CAMERA_PERM_RGB);
        }
    }

    private void requestCameraPermission(final int RC_HANDLE_CAMERA_PERM) {
        final String[] permissions = new String[]{Manifest.permission.CAMERA};
        ActivityCompat.requestPermissions(getActivity(), permissions, RC_HANDLE_CAMERA_PERM);
    }

    /**
     * 选择分类列表
     */
    public void selectType(final List<TypeBase> typelist) {
        NiceDialog.init()
                .setLayoutId(R.layout.dialog_selectype)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    protected void convertView(ViewHolder holder, final BaseNiceDialog dialog) {
                        RecyclerView recyclerView = holder.getView(R.id.recyclerview_type);
                        LinearLayout ll_search = holder.getView(R.id.ll_search);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                        recyclerView.setLayoutManager(layoutManager);
                        TpyeAdapter tpyeAdapter = new TpyeAdapter(getContext(), typelist, PersonageFragment.this);
                        recyclerView.setAdapter(tpyeAdapter);

                        ll_search.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (tpyeid.equals("")) {
                                    Toast.makeText(getContext(), "请选择分类", Toast.LENGTH_SHORT).show();
                                } else {
                                    Intent intent = new Intent(getContext(), PracticeMode_Activity2.class);
                                    intent.putExtra("tpyeid", tpyeid);
                                    startActivity(intent);
                                    dialog.dismiss();
                                }
                            }
                        });


                    }
                })
                .setDimAmount(0.1f)
                .setShowBottom(false)
                .setOutCancel(true)
                .setAnimStyle(R.style.PracticeModeAnimation)
                .show(getChildFragmentManager());
    }

    @Override
    public void gettpyeid(int i) {
        tpyeid = "" + i;
    }

    @Override
    public void AddFacefot(String id) {
        addface(id);
    }
}
