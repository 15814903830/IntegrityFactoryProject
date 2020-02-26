package com.xsylsb.integrity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airsaid.pickerviewlibrary.OptionsPickerView;
import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.jph.takephoto.model.TResult;
import com.xsylsb.integrity.Examination_adapter.SafetyDtailAdapter;
import com.xsylsb.integrity.Examination_adapter.WorkerxmSelectAdapter;
import com.xsylsb.integrity.base.FileBase;
import com.xsylsb.integrity.base.PhotGraphBase;
import com.xsylsb.integrity.base.ProjectBase;
import com.xsylsb.integrity.base.WorkerSelectBase;
import com.xsylsb.integrity.mianfragment.homepage.personage.PersonAdapter;
import com.xsylsb.integrity.mianfragment.homepage.personage.ScanPersonAdapter;
import com.xsylsb.integrity.util.FileToBase64;
import com.xsylsb.integrity.util.HttpCallBack;
import com.xsylsb.integrity.util.KeyBoardUtils;
import com.xsylsb.integrity.util.MyURL;
import com.xsylsb.integrity.util.OkHttpUtils;
import com.xsylsb.integrity.util.RequestParams;
import com.xsylsb.integrity.util.SharedPrefUtil;
import com.xsylsb.integrity.util.dialog.BaseNiceDialog;
import com.xsylsb.integrity.util.dialog.NiceDialog;
import com.xsylsb.integrity.util.dialog.ViewConvertListener;
import com.xsylsb.integrity.util.dialog.ViewHolder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PhotographActivity extends TakePhotoActivity implements PhotographAdapter.MyClassifyAdapterOnItem, HttpCallBack, WorkerxmSelectAdapter.OnItmeClickListener, SafetyDtailAdapter.GetItemid {

    @BindView(R.id.iv_return)
    ImageView ivReturn;
    @BindView(R.id.iv_shenghe1)
    ImageView ivShenghe1;
    @BindView(R.id.ll_shenghe1)
    LinearLayout llShenghe1;
    @BindView(R.id.iv_shenghe2)
    ImageView ivShenghe2;
    @BindView(R.id.ll_shenghe2)
    LinearLayout llShenghe2;
    @BindView(R.id.iv_shenghe3)
    ImageView ivShenghe3;
    @BindView(R.id.ll_shenghe3)
    LinearLayout llShenghe3;
    @BindView(R.id.et_yijiang)
    EditText etYijiang;
    @BindView(R.id.rv_tupian)
    RecyclerView rvTupian;
    @BindView(R.id.btn_lijisp)
    Button btnLijisp;
    PhotographAdapter groupAdapter;
    @BindView(R.id.tv_xmzg_text)
    TextView tvXmzgText;
    @BindView(R.id.ll_xmzg)
    LinearLayout llXmzg;
    @BindView(R.id.rv_getworkerxm_select)
    RecyclerView rvGetworkerxmSelect;
    @BindView(R.id.ll_getworkerxm_select)
    LinearLayout llGetworkerxmSelect;
    @BindView(R.id.ll_safetyworkpermitsign_dtail)
    LinearLayout llSafetyworkpermitsignDtail;
    @BindView(R.id.ll_shudidanwei_xuanzhe)
    LinearLayout ll_shudidanwei_xuanzhe;

    @BindView(R.id.tv_shudiname)
    TextView tv_shudiname;


    private String xuanzhe = "2";
    private List<String> imglist = new ArrayList<>();
    ArrayList<String> listvolume = new ArrayList<>();
    ArrayList<String> listvolume2 = new ArrayList<>();
    private String id;
    private HttpCallBack mHttpCallBack;
    private PhotGraphBase photGraphBase = null;
    private String xuanmuzhugan = "";
    private String imgs = "";
    int anInt = 0;
    private BaseNiceDialog mBaseNiceDialog;
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
    List<ProjectBase> projectBaseList = null;
    List<ProjectBase.ChildrenBean> safetylist = new ArrayList<>();
    private SafetyDtailAdapter safetyDtailAdapter;
    private WorkerxmSelectAdapter workerxmSelectAdapter = null;
    private List<WorkerSelectBase> workerSelectBaseList = null;
    private List<WorkerSelectBase> workerSelectBaseList2 = new ArrayList<>();
    private List<WorkerSelectBase> workerSelectBaseList3 = new ArrayList<>();
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
    TextView onpersonal;
    private LinearLayout llzhongjiang;
    ScanPersonAdapter personAdapter;
    private String sceneGuardianId = "";
    private String sceneGuardianFullName = "";
    private String projectLocationLeaderId = "";
    private String projectLocationLeaderFullName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photograph);
        ButterKnife.bind(this);
        id = getIntent().getStringExtra("id");

        Log.e("idid:", "" + id);
        SafetyWorkPermitSignDtail();
        mHttpCallBack = this;
        imglist.add("88");
        listvolume2.add("拍照");
        listvolume2.add("相册");
        GridLayoutManager linearLayoutManager = new GridLayoutManager(PhotographActivity.this, 4);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvTupian.setLayoutManager(linearLayoutManager);
        groupAdapter = new PhotographAdapter(PhotographActivity.this, imglist, PhotographActivity.this);
        rvTupian.setAdapter(groupAdapter);
    }


    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        for (int i = 0; i < result.getImages().size(); i++) {
            groupAdapter.getList().add(0, result.getImages().get(i).getOriginalPath());
        }
        groupAdapter.notifyDataSetChanged();

    }

    private void SafetyWorkPermitSignDtail() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RequestParams> list = new ArrayList<>();
                list.add(new RequestParams("id", id));
                list.add(new RequestParams("workerId", "" + SharedPrefUtil.getString(SharedPrefUtil.ID)));
                OkHttpUtils.doGet(MyURL.URL + "SafetyWorkPermitSignDtail", list, mHttpCallBack, 0);
            }
        }).start();
    }

    //施工单位现场监护人（搜索）
    private void GetWorkerXmSelectJson() {
        llGetworkerxmSelect.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpUtils.doGet(MyURL.URL + "WorkerXmSelectJson?id=" + id, mHttpCallBack, 3);
                Log.e("GetWorkerXmSelectJson:", MyURL.URL + "WorkerXmSelectJson?id=" + id);
            }
        }).start();
    }

    //属地单位监护人
    private void GetSafetyWorkPermitSignDtail(final String LocationShopId) {
        llSafetyworkpermitsignDtail.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RequestParams> list = new ArrayList<>();
                list.add(new RequestParams("id", LocationShopId));
                OkHttpUtils.doGet(MyURL.URL + "ProjectLocationWorkder", list, mHttpCallBack, 4);
            }
        }).start();
    }


    private void UploadFile(final String fileName, final String fileData) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("fileName", fileName);
                    jsonObject.put("fileData", fileData);
                    OkHttpUtils.doPostJson(MyURL.URL + "UploadFile", jsonObject.toString(), mHttpCallBack, 1);
                    Log.e("OkHttpUtils:", "fileName;" + fileName);
                    Log.e("OkHttpUtils:", "fileData:" + fileData);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public void SafetyWorkPermitSign() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject();

                    if (workerxmSelectAdapter != null) {
                        //施工单位
                        if (workerxmSelectAdapter.getBookListBase().size() > 1) {
                            jsonObject.put("sceneGuardianId", sceneGuardianId);
                            jsonObject.put("sceneGuardianFullName", sceneGuardianFullName);
                        }
                    }


                    if (projectLocationLeaderId.length() > 0) {
                        //属地单位
                        jsonObject.put("projectLocationLeaderId", projectLocationLeaderId);
                        jsonObject.put("projectLocationLeaderFullName", projectLocationLeaderFullName);
                    }


                    jsonObject.put("PermitId", "" + photGraphBase.getModel().getPermitId());
                    jsonObject.put("workerId", "" + "" + SharedPrefUtil.getString(SharedPrefUtil.ID));
                    jsonObject.put("id", "" + photGraphBase.getModel().getId());
                    jsonObject.put("targetId", "" + photGraphBase.getModel().getTargetId());
                    jsonObject.put("status", "" + xuanzhe);
                    if (!xuanmuzhugan.equals("")) {
                        jsonObject.put("ZhuGuanBuMen", "" + xuanmuzhugan);
                    }

                    if (!imgs.equals("")) {
                        jsonObject.put("images", "" + imgs);
                    }
                    if (!etYijiang.getText().toString().equals("")) {
                        jsonObject.put("Opinion", "" + etYijiang.getText().toString());
                    }
                  OkHttpUtils.doPostJson(MyURL.URL + "SafetyWorkPermitSign", jsonObject.toString(), mHttpCallBack, 2);
                    Log.e("jsonObject", MyURL.URL + "SafetyWorkPermitSign");
                    Log.e("jsonObject", jsonObject.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    @OnClick({R.id.btn_lijisp, R.id.ll_shenghe1, R.id.ll_shenghe2, R.id.ll_shenghe3, R.id.iv_return, R.id.ll_xmzg, R.id.ll_shudidanwei_xuanzhe})
    public void MyOnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_lijisp:

                sceneGuardianId = "";
                sceneGuardianFullName = "";
                projectLocationLeaderId = "";
                projectLocationLeaderFullName = "";

                if (llXmzg.getVisibility() == View.VISIBLE) {
                    if (tvXmzgText.getText().toString().equals("") & xuanzhe.equals("2")) {
                        Toast.makeText(this, "请选择项目主管科室", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                if (photGraphBase.getModel().getSignRole() == 3) {
                    //signRole=3的时候，两个同时显示
                    //属地单位监护人
                    if (safetyDtailAdapter!=null){
                        if (safetyDtailAdapter.getBookListBase() != null) {
                            for (int j = 0; j < safetyDtailAdapter.getBookListBase().size(); j++) {
                                if (safetyDtailAdapter.getBookListBase().get(j).getIsselise().equals("ABC")) {
                                    projectLocationLeaderId = projectLocationLeaderId + safetyDtailAdapter.getBookListBase().get(j).getValue() + ",";
                                    projectLocationLeaderFullName = projectLocationLeaderFullName + safetyDtailAdapter.getBookListBase().get(j).getName().split("----")[1] + ",";
                                }
                            }
                        }
                    }


                    if (projectLocationLeaderId.length() > 0) {
                        //属地单位
                    } else {
                        Toast.makeText(PhotographActivity.this, "请选择属地单位监护人", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (workerxmSelectAdapter.getBookListBase().size() > 1 & workerxmSelectAdapter.getBookListBase() != null) {
                        for (int i = 0; i < workerxmSelectAdapter.getBookListBase().size() - 1; i++) {
                            if (i == workerxmSelectAdapter.getBookListBase().size() - 2) {
                                sceneGuardianId = sceneGuardianId + workerxmSelectAdapter.getBookListBase().get(i).getValue();
                                sceneGuardianFullName = sceneGuardianFullName + workerxmSelectAdapter.getBookListBase().get(i).getName();
                            } else {
                                sceneGuardianId = sceneGuardianId + workerxmSelectAdapter.getBookListBase().get(i).getValue() + ",";
                                sceneGuardianFullName = sceneGuardianFullName + workerxmSelectAdapter.getBookListBase().get(i).getName() + ",";
                            }
                        }

                    } else {
                        Toast.makeText(this, "请选择施工单位负责人", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                if (photGraphBase.getModel().getSignRole() == 1) {
                    //signRole&1==1显示属地单位监护人的选项
                    //属地单位监护人
                    if (safetyDtailAdapter != null) {
                        for (int j = 0; j < safetyDtailAdapter.getBookListBase().size(); j++) {
                            if (safetyDtailAdapter.getBookListBase().get(j).getIdno().equals("ABC")) {
                                projectLocationLeaderId = projectLocationLeaderId + safetyDtailAdapter.getBookListBase().get(j).getValue() + ",";
                                projectLocationLeaderFullName = projectLocationLeaderFullName + safetyDtailAdapter.getBookListBase().get(j).getName() + ",";
                            }
                        }
                    }
                    if (projectLocationLeaderId.length() > 0) {
                        //属地单位
                    } else {
                        Toast.makeText(PhotographActivity.this, "请选择属地单位监护人", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if (photGraphBase.getModel().getSignRole() == 2) {
                    //signRole&2==2显示施工单位现场监护人的选项
                    if (workerxmSelectAdapter != null) {
                        if (workerxmSelectAdapter.getBookListBase().size() > 0 & workerxmSelectAdapter.getBookListBase() != null) {
                            for (int i = 0; i < workerxmSelectAdapter.getBookListBase().size() - 1; i++) {
                                if (i == workerxmSelectAdapter.getBookListBase().size() - 2) {
                                    sceneGuardianId = sceneGuardianId + workerxmSelectAdapter.getBookListBase().get(i).getValue();
                                    sceneGuardianFullName = sceneGuardianFullName + workerxmSelectAdapter.getBookListBase().get(i).getName();
                                } else {
                                    sceneGuardianId = sceneGuardianId + workerxmSelectAdapter.getBookListBase().get(i).getValue() + ",";
                                    sceneGuardianFullName = sceneGuardianFullName + workerxmSelectAdapter.getBookListBase().get(i).getName() + ",";
                                }
                            }

                        }
                    } else {
                        Toast.makeText(this, "请选择施工单位负责人", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if (imglist.size() == 1) {
                    SafetyWorkPermitSign();
                } else {
                    for (int i = 0; i < imglist.size() - 1; i++) {
                        UploadFile(new File(imglist.get(i)).getName(), FileToBase64.best64yimg(imglist.get(i)));
                    }
                }
                showLoading();
                break;
            case R.id.ll_xmzg:
                OptionsPickerView<String> mOptionsPickerView = new OptionsPickerView<>(PhotographActivity.this);
                // 设置数据
                mOptionsPickerView.setPicker(listvolume);
                mOptionsPickerView.setOnOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int option1, int option2, int option3) {
                        for (int i = 0; i < photGraphBase.getWorkShop().size(); i++) {
                            if (photGraphBase.getWorkShop().get(i).getText().equals(listvolume.get(option1))) {
                                xuanmuzhugan = photGraphBase.getWorkShop().get(i).getValue();
                            }
                        }
                        tvXmzgText.setText(listvolume.get(option1));


                    }
                });
                mOptionsPickerView.show();
                break;
            case R.id.ll_shenghe1:
                xuanzhe = "2";
                Glide.with(PhotographActivity.this).load(R.mipmap.xuanzheyes).into(ivShenghe1);
                Glide.with(PhotographActivity.this).load(R.mipmap.xuanzhetrue).into(ivShenghe2);
                Glide.with(PhotographActivity.this).load(R.mipmap.xuanzhetrue).into(ivShenghe3);
                break;
            case R.id.ll_shenghe2:
                xuanzhe = "3";
                Glide.with(PhotographActivity.this).load(R.mipmap.xuanzhetrue).into(ivShenghe1);
                Glide.with(PhotographActivity.this).load(R.mipmap.xuanzheyes).into(ivShenghe2);
                Glide.with(PhotographActivity.this).load(R.mipmap.xuanzhetrue).into(ivShenghe3);
                break;
            case R.id.ll_shenghe3:
                xuanzhe = "4";
                Glide.with(PhotographActivity.this).load(R.mipmap.xuanzhetrue).into(ivShenghe1);
                Glide.with(PhotographActivity.this).load(R.mipmap.xuanzhetrue).into(ivShenghe2);
                Glide.with(PhotographActivity.this).load(R.mipmap.xuanzheyes).into(ivShenghe3);
                break;
            case R.id.ll_shudidanwei_xuanzhe:
                //属地单位监护人弹窗
                searchidshigon();
                break;
            case R.id.iv_return:
                finish();
                break;


        }
    }

    @Override
    public void OnItemClickListener(int i) {
        if (i == 123456) {
            OptionsPickerView<String> mOptionsPickerView = new OptionsPickerView<>(PhotographActivity.this);
            // 设置数据
            mOptionsPickerView.setPicker(listvolume2);
            mOptionsPickerView.setOnOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int option1, int option2, int option3) {
                    if (listvolume2.get(option1).equals("拍照")) {
                        takePhoto.onPickFromCapture(uri);
                    } else {
                        takePhoto.onPickMultiple(9);
                    }
                }
            });
            mOptionsPickerView.show();
        } else {
            groupAdapter.getList().remove(i);
            groupAdapter.notifyDataSetChanged();
        }


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
        Log.e("response", response);
        switch (requestId) {
            case 0:
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean suc = jsonObject.getBoolean("suc");
                    if (suc == false) {
                        Toast.makeText(this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                        finish();
                        return;
                    }
                    photGraphBase = JSON.parseObject(response, PhotGraphBase.class);
                    if (photGraphBase.getModel().getSignRole() == 3) {
                        //signRole=3的时候，两个不就是同时显示
                        GetWorkerXmSelectJson();//施工单位现场监护人
                        GetSafetyWorkPermitSignDtail(photGraphBase.getModel().getJobLocationShopId());//属地单位监护人
                    }

                    if (photGraphBase.getModel().getSignRole() == 1) {
                        //signRole&1==1显示属地单位监护人的选项
                        GetSafetyWorkPermitSignDtail(photGraphBase.getModel().getJobLocationShopId());//属地单位监护人
                    }
                    if (photGraphBase.getModel().getSignRole() == 2) {
                        //signRole&2==2显示施工单位现场监护人的选项
                        GetWorkerXmSelectJson();//施工单位现场监护人
                    }


                    if (photGraphBase.getWorkShop().size() > 0) {
                        llXmzg.setVisibility(View.VISIBLE);
                        for (int i = 0; i < photGraphBase.getWorkShop().size(); i++) {
                            listvolume.add(photGraphBase.getWorkShop().get(i).getText());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 1:
                //  SafetyWorkPermitSign();
                try {
                    Log.e("FileBase", "anInt:" + anInt);
                    Log.e("FileBase", "size:" + imglist.size());
                    FileBase base = JSON.parseObject(response, FileBase.class);
                    if (anInt < (imglist.size() - 2)) {
                        imgs = imgs + base.getFilePath() + "|";
                    } else {
                        imgs = imgs + base.getFilePath();
                        SafetyWorkPermitSign();
                        mBaseNiceDialog.dismiss();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                anInt++;
                break;

            case 2:
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("suc").equals("true")) {
                        finish();
                    }
                    Toast.makeText(this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                    Log.e("response", "222222=====" + response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                mBaseNiceDialog.dismiss();
                break;

            case 3:
                //施工单位现场监护人
                try {
                    Log.e("施工单位现场监护人:", response);
                    workerSelectBaseList = JSON.parseArray(response, WorkerSelectBase.class);
                    workerSelectBaseList2.add(new WorkerSelectBase());
                    GridLayoutManager linearLayoutManager = new GridLayoutManager(PhotographActivity.this, 4);
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    rvGetworkerxmSelect.setLayoutManager(linearLayoutManager);
                    workerxmSelectAdapter = new WorkerxmSelectAdapter(PhotographActivity.this, workerSelectBaseList2, PhotographActivity.this);
                    rvGetworkerxmSelect.setAdapter(workerxmSelectAdapter);
                    rvGetworkerxmSelect.setHasFixedSize(true);
                    rvGetworkerxmSelect.setNestedScrollingEnabled(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 4:
                //属地单位监护人
                try {
                    projectBaseList = JSON.parseArray(response, ProjectBase.class);
                    for (int i = 0; i < projectBaseList.size(); i++) {
                        for (int j = 0; j < projectBaseList.get(i).getChildren().size(); j++) {
                            ProjectBase.ChildrenBean p = new ProjectBase.ChildrenBean();
                            p.setIdno(projectBaseList.get(i).getChildren().get(j).getIdno());
                            p.setValue(projectBaseList.get(i).getChildren().get(j).getValue());
                            p.setName(projectBaseList.get(i).getName() + "----" + projectBaseList.get(i).getChildren().get(j).getName());
                            p.setIsselise("");
                            safetylist.add(p);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
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
                .show(getSupportFragmentManager());
    }

    /**
     * 查询属地单位现场监护人
     */
    public void searchidshigon() {
        NiceDialog.init()
                .setLayoutId(R.layout.dialog_shudi)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    protected void convertView(final ViewHolder holder, final BaseNiceDialog dialog) {
                        final EditText ed_send = holder.getView(R.id.ed_send);
                        final String send="";

                        final RecyclerView rvSafetyworkpermitsignDtail = holder.getView(R.id.rv_safetyworkpermitsign_dtail);
                        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(PhotographActivity.this);
                        linearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
                        rvSafetyworkpermitsignDtail.setLayoutManager(linearLayoutManager2);
                        safetyDtailAdapter = new SafetyDtailAdapter(PhotographActivity.this, safetylist, PhotographActivity.this,send);
                        rvSafetyworkpermitsignDtail.setAdapter(safetyDtailAdapter);
                        rvSafetyworkpermitsignDtail.setHasFixedSize(true);
                        rvSafetyworkpermitsignDtail.setNestedScrollingEnabled(false);
                        KeyBoardUtils.closeKeybord(ed_send, PhotographActivity.this);
                        ed_send.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                            @Override
                            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                                if (null != keyEvent && KeyEvent.KEYCODE_ENTER == keyEvent.getKeyCode()) {
                                    switch (keyEvent.getAction()) {
                                        case KeyEvent.ACTION_UP:
                                            //搜索
                                            LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(PhotographActivity.this);
                                            linearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
                                            rvSafetyworkpermitsignDtail.setLayoutManager(linearLayoutManager2);
                                            safetyDtailAdapter = new SafetyDtailAdapter(PhotographActivity.this, safetylist, PhotographActivity.this,ed_send.getText().toString());
                                            rvSafetyworkpermitsignDtail.setAdapter(safetyDtailAdapter);
                                            rvSafetyworkpermitsignDtail.setHasFixedSize(true);
                                            rvSafetyworkpermitsignDtail.setNestedScrollingEnabled(false);
                                            KeyBoardUtils.closeKeybord(ed_send, PhotographActivity.this);
                                            return true;
                                        default:
                                            return true;
                                    }
                                }
                                return false;
                            }
                        });
                    }
                })
                .setDimAmount(0.1f)
                .setShowBottom(false)
                .setOutCancel(true)
                .setAnimStyle(R.style.PracticeModeAnimation)
                .show(getSupportFragmentManager());
    }

    /**
     * 查询属地单位现场监护人
     */
    public void searchid() {
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
                                    Toast.makeText(PhotographActivity.this, "请输入施工单位现场监护人信息", Toast.LENGTH_SHORT).show();
                                } else {
                                    workerSelectBaseList3.clear();
                                    KeyBoardUtils.closeKeybord(etnoid, PhotographActivity.this);
                                    String idno = etnoid.getText().toString();

                                    for (int i = 0; i < workerSelectBaseList.size(); i++) {
                                        if (workerSelectBaseList.get(i).getIdno().contains(idno) | workerSelectBaseList.get(i).getName().contains(idno) | workerSelectBaseList.get(i).getValue().contains(idno)) {
                                            workerSelectBaseList3.add(0, workerSelectBaseList.get(i));
                                        }
                                    }
                                    tv_idno.setText("" + (workerSelectBaseList2.size() - 1));
                                    if (workerSelectBaseList3.size() > 0) {
                                        LinearLayoutManager layoutManager = new LinearLayoutManager(PhotographActivity.this,
                                                LinearLayoutManager.VERTICAL, false);
                                        recyclerView.setLayoutManager(layoutManager);
                                        personAdapter = new ScanPersonAdapter(PhotographActivity.this, workerSelectBaseList3);
                                        recyclerView.setAdapter(personAdapter);
                                        personAdapter.notifyDataSetChanged();
                                        tvsearch.setText("确定");

                                        Immediately.setVisibility(View.VISIBLE);
                                        personal.setVisibility(View.VISIBLE);
                                        llzhongjiang.setVisibility(View.VISIBLE);
                                        searchagain.setVisibility(View.GONE);
                                        onpersonal.setVisibility(View.GONE);
                                        search.setVisibility(View.GONE);
                                        etnoid.setVisibility(View.GONE);
                                    } else {
                                        onpersonal.setText("没有找到对应现场监护人信息");
                                        onpersonal.setVisibility(View.VISIBLE);
                                        llzhongjiang.setVisibility(View.VISIBLE);
                                        etnoid.setVisibility(View.GONE);
                                        personal.setVisibility(View.GONE);
                                        search.setVisibility(View.GONE);
                                        searchagain.setVisibility(View.VISIBLE);
                                    }

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

                        TextView tv_qudin = holder.getView(R.id.tv_qudin);
                        tv_qudin.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                Toast.makeText(PhotographActivity.this, "确定", Toast.LENGTH_SHORT).show();
                                workerSelectBaseList2.clear();
                                for (int i = 0; i < personAdapter.getList().size(); i++) {
                                    if (personAdapter.getList().get(i).isSelsecr()) {
                                        workerSelectBaseList2.add(0, personAdapter.getList().get(i));
                                    }
                                }
                                workerSelectBaseList2.add(new WorkerSelectBase());
                                workerxmSelectAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                })
                .setDimAmount(0.1f)
                .setShowBottom(false)
                .setOutCancel(true)
                .setAnimStyle(R.style.PracticeModeAnimation)
                .show(getSupportFragmentManager());
    }

    //选择施工单位监护人
    @Override
    public void OnItmeClickListener(String id) {
        if (id.equals("添加")) {
            searchid();
        } else {
            for (int i = 0; i < workerxmSelectAdapter.getBookListBase().size()-1; i++) {
                if (workerxmSelectAdapter.getBookListBase().get(i) != null) {
                    if (workerxmSelectAdapter.getBookListBase().get(i).getIdno().equals(id)) {
                        for (int j = 0; j < personAdapter.getList().size(); j++) {
                            if (personAdapter.getList().get(j).getIdno().equals( workerxmSelectAdapter.getBookListBase().get(i).getIdno())) {
                                personAdapter.getList().get(j).setSelsecr(false);
                                personAdapter.notifyDataSetChanged();
                                workerxmSelectAdapter.getBookListBase().remove(i);
                            }
                        }
                    }

                }

            }
            workerxmSelectAdapter.notifyDataSetChanged();
        }
    }


    //选择属地单位监护人
    @Override
    public void GetItemid() {
        String names = "";
        if (safetyDtailAdapter.getBookListBase() != null) {
            for (int j = 0; j < safetyDtailAdapter.getBookListBase().size(); j++) {
                if (safetyDtailAdapter.getBookListBase().get(j).getIsselise().equals("ABC")) {
                    //   projectLocationLeaderId=projectLocationLeaderId+safetyDtailAdapter.getBookListBase().get(j).getValue()+",";
                    if (names.length()>0){
                        names = names +","+ safetyDtailAdapter.getBookListBase().get(j).getName().split("----")[1];
                    }else {
                        names = names + safetyDtailAdapter.getBookListBase().get(j).getName().split("----")[1] ;
                    }
                }
            }
            if (names.length() == 0) {
                tv_shudiname.setText("请选择属地单位监护人");
            } else {
                tv_shudiname.setText(names);
            }

        }
    }

}
