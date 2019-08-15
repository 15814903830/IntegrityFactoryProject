package com.xsylsb.integrity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xsylsb.integrity.Examination_adapter.ExUserBase;
import com.xsylsb.integrity.Examination_adapter.ExamianBase;
import com.xsylsb.integrity.Examination_adapter.MyExaminatioFragmentAdapter;
import com.xsylsb.integrity.Examination_adapter.MyMultipleItem;
import com.xsylsb.integrity.base.CommitanswerBase;
import com.xsylsb.integrity.practicemode_recyclview.Listuser;
import com.xsylsb.integrity.practicemode_recyclview.MyPrTopicSum;
import com.xsylsb.integrity.practicemode_recyclview.MyPracticeModeItem;
import com.xsylsb.integrity.practicemode_recyclview.MyPractilistviewInterface;
import com.xsylsb.integrity.practicemode_recyclview.PractiBase;
import com.xsylsb.integrity.practicemode_recyclview.PracticeModeAdapter;
import com.xsylsb.integrity.practicemode_recyclview.PracticeModeFragment;
import com.xsylsb.integrity.userbase.TopicBase;
import com.xsylsb.integrity.util.HttpCallBack;
import com.xsylsb.integrity.util.MyURL;
import com.xsylsb.integrity.util.OkHttpUtils;
import com.xsylsb.integrity.util.RequestParams;
import com.xsylsb.integrity.util.dialog.BaseNiceDialog;
import com.xsylsb.integrity.util.dialog.NiceDialog;
import com.xsylsb.integrity.util.dialog.ViewConvertListener;
import com.xsylsb.integrity.util.dialog.ViewHolder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author glsite.com
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class PracticeMode_Activity extends AppCompatActivity implements  MyPractilistviewInterface,HttpCallBack {
    LinearLayout mLinearLayout;
    @BindView(R.id.practicemode_ll)
    LinearLayout mLinearLayoutll;
    RecyclerView homeRecyclerview;
    @BindView(R.id.tv_pr_time)
    TextView tvPrTime;
    @BindView(R.id.pr_ll)
    LinearLayout getLinearLayout;
    @BindView(R.id.iv_return_examination)
    ImageView ivReturnExamination;
    @BindView(R.id.practicemode_viewpager)
    ViewPager practicemodeViewpager;
    @BindView(R.id.ll_submit_examination_pr)
    LinearLayout mLinearLayoutpr;
    @BindView(R.id.iv_record_examination)
    ImageView ivRecordExamination;
    @BindView(R.id.tv_score_examination_pr1)
    TextView tvScoreExaminationPr1;
    @BindView(R.id.tv_score_examination_pr2)
    TextView tvScoreExaminationPr2;
    @BindView(R.id.tv_sum_true)
    TextView tvSumTrue;
    @BindView(R.id.tv_sum_false)
    TextView tvSumFalse;
    public int vpitem = 0;
    @BindView(R.id.iv_commit_pract)
    ImageView ivCommitPract;
    @BindView(R.id.tv_commit_pract)
    TextView tvCommitPract;
    private CommitanswerBase mCommitanswerBase;
    private int sumtrue=0;
    private int sumfalse=0;
    private int falsesum = 0;
    private int sumfor = 0;
    private PracticeModeAdapter adapter;
    private Toast toast;
    private List<MyPracticeModeItem> dast = new ArrayList<>();
    private List<Listuser> mListusers = new ArrayList<>();
    private CountDownTimer timer;
    private Listuser listuser;
    private List<String> mStringList = new ArrayList<>();
    List<TopicBase> mTopicBaseList = new ArrayList<>();//假数据
    private int booleansum = 0;
    List<Fragment> mFragmentList = new ArrayList<>();
    private int sum = 0;
    private int vpi=0;
    private int viewp=0;
    private HttpCallBack mHttpCallBack;
    private ExamianBase mExamianBase;
    private boolean mBoolean=true;
    List<ExUserBase> mExUserBaseList = new ArrayList<>();//记录答题数量，答题对错，题目id
    private int truesum=0;
    private String url;
    private String id,workerid,classificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.practicemode_activity);
        url=getIntent().getStringExtra("url");
        String s=url.split("\\?")[1];
        id=s.split("&")[0].split("=")[1];
        workerid=s.split("&")[1].split("=")[1];
        classificationId=s.split("&")[2].split("=")[1];


        Log.e("getIntent",url);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        homeRecyclerview = View.inflate(this, R.layout.recyclview_answer_sheet, null).findViewById(R.id.recylcview_answer_rv);
        ButterKnife.bind(this);
        mHttpCallBack=this;
        getdata();
        for (int i = 0; i < 5; i++) {
            mStringList.add("A" + i);//模拟5个选项
        }

       // Log.e("time",""+ mExamianBase.getCourse().getTimeLength());



        practicemodeViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {

                if (viewp<i){
                    viewp++;
                vpitem = i;
                vpi=i;
                if (sum < i) {
                    sum++;
                    tvScoreExaminationPr1.setText("" + sum);
                }
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });


    }
    private void initfragmnet(){
        tvScoreExaminationPr2.setText("" + mExamianBase.getData().size());//总题数
        Log.e("getData",""+mExamianBase.getData().size());
        for (int i = 0; i < mExamianBase.getData().size(); i++) {
            mFragmentList.add(new PracticeModeFragment(mExamianBase.getData().get(i)));
        }
        MyExaminatioFragmentAdapter examinatioFragmentAdapter = new MyExaminatioFragmentAdapter(getSupportFragmentManager(), mFragmentList);
        practicemodeViewpager.setAdapter(examinatioFragmentAdapter);
        practicemodeViewpager.setOffscreenPageLimit(mExamianBase.getData().size() - 1);

    }
    @Override
    protected void onStart() {
        super.onStart();
    }

    @OnClick({R.id.pr_ll, R.id.iv_return_examination, R.id.ll_submit_examination_pr})
    public void MyOnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_return_examination:
                //返回
                    NiceDialog.init()
                            .setLayoutId(R.layout.examination_continue_and_out_dialog)
                            .setConvertListener(new ViewConvertListener() {
                                @Override
                                protected void convertView(ViewHolder holder, final BaseNiceDialog dialog) {
                                    TextView textView = holder.getView(R.id.tv_exit_out);//退出
                                    TextView textView2 = holder.getView(R.id.tv_hesitate);//继续考试
                                    TextView textView1 = holder.getView(R.id.tv_sum_out);//剩下题目数量
                                    textView1.setText("" + (mExamianBase.getData().size() - mExUserBaseList.size()));
                                    textView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();
                                            finish();
                                           // startActivity(new Intent(PracticeMode_Activity.this, TranscriptFailActivity.class));//跳转到不及格
                                        }
                                    });
                                    textView2.setOnClickListener(new View.OnClickListener() {
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

                break;
            case R.id.pr_ll:
                //题目填写情况弹窗
                NiceDialog.init()
                        .setLayoutId(R.layout.recyclview_answer_sheet)
                        .setConvertListener(new ViewConvertListener() {
                            @Override
                            protected void convertView(ViewHolder holder, BaseNiceDialog dialog) {
                                RecyclerView recyclerView = holder.getView(R.id.recylcview_answer_rv);
                                TextView textView=holder.getView(R.id.tv_score_prcati1);
                                textView.setText("" + sum);
                                TextView textView2=holder.getView(R.id.tv_score__prcati2);
                                textView2.setText("" + mExamianBase.getData().size());//总题数
                                TextView textView4=holder.getView(R.id.tv_recyle_true);
                                textView4.setText(""+sumtrue);//答对
                                TextView textView5=holder.getView(R.id.tv_recyle_flase);
                                textView5.setText(""+sumfalse);//答错
                                mListusers.clear();

                                Log.e("getData",""+mExamianBase.getData().size());
                                for (int i = 0; i < mExamianBase.getData().size(); i++) {
                                    listuser = new Listuser();
                                    listuser.setSelected(i);
                                    mListusers.add(listuser);
                                }


                                recyclerView.setLayoutManager(new GridLayoutManager(PracticeMode_Activity.this, 6, OrientationHelper.VERTICAL, false));
                                //创建适配器
                                adapter = new PracticeModeAdapter(dast, mListusers);
                                //给RecyclerView设置适配器
                                recyclerView.setAdapter(adapter);
                                adapter.bindToRecyclerView(recyclerView);
                                adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                            practicemodeViewpager.setCurrentItem(position);//选择后跳到下一题
                                    }
                                });


                            }
                        })
                        .setDimAmount(0.1f)
                        .setShowBottom(true)
                        .setAnimStyle(R.style.PracticeModeAnimation)
                        .show(getSupportFragmentManager());
                break;
            case R.id.ll_submit_examination_pr:
                //交卷
                if (mExamianBase.getData().size() == mExUserBaseList.size() & truesum >= (mExamianBase.getData().size() * 0.6)) {//答对等于或者大于百分之60
                    startActivity(new Intent(PracticeMode_Activity.this, TranscriptQualifiedActivity.class));
                    finish();
                } else if (mExamianBase.getData().size() == mExUserBaseList.size() & truesum <= (mExamianBase.getData().size() * 0.6)){
                    startActivity(new Intent(PracticeMode_Activity.this, TranscriptFailActivity.class));
                    finish();
                }

                else {
                    NiceDialog.init()
                            .setLayoutId(R.layout.examination_continue_dialog)
                            .setConvertListener(new ViewConvertListener() {
                                @Override
                                protected void convertView(ViewHolder holder, final BaseNiceDialog dialog) {
                                    TextView textView = holder.getView(R.id.tv_exit_dialog);
                                    TextView textView1 = holder.getView(R.id.tv_remain_sum);
                                    textView1.setText("" + (mExamianBase.getData().size() - mExUserBaseList.size()));
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
                break;


        }
    }

    private void Mytoast() {
        //题目答完toast提示
        if (this != null) {
            toast = new Toast(this);
            //设置自定义Toast的位置
            View layout = View.inflate(this, R.layout.over_dialog, null);
            toast.setView(layout);
            //设置Toast的位置在屏幕中间
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void ViewComment(ExamianBase examianBase) {
        //指定布局和传入数据,在HomeAdapter利用MyMultipleItem.FIRST_TYPE的值判断布局
        for (int i = 0; i < examianBase.getData().size(); i++) {
            Log.e("getData",""+i);
            this.dast.add(new MyPracticeModeItem(MyPracticeModeItem.NORMAL_TYPE, false));//待答题
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }



    @Override
    public void MyPractilistviewInterface( boolean b,String trueforfalse) {//fragment接口回调

        ExUserBase exUserBase = new ExUserBase();
        exUserBase.setBoolean(b);//设置答题正确与否
        exUserBase.setInt(practicemodeViewpager.getCurrentItem());//设置答了第几题
        // inititem();//点亮列表，跳转下一题


        if (mExUserBaseList != null) {
            for (int i = 0; i < mExUserBaseList.size(); i++) {
                if (mExUserBaseList.get(i).getInt() == practicemodeViewpager.getCurrentItem()) {//如果在mExUserBaseList里面已经存在int=1,那么如果viewpagery也是1，就代表存在
                    exUserBase.setBoolean(mExUserBaseList.get(i).isBoolean());//设置回原来的答案，练习答案不能改变
                    mExUserBaseList.set(practicemodeViewpager.getCurrentItem(), exUserBase);//替换
                    mBoolean = false;//如果存在为false
                    tvScoreExaminationPr1.setText("" + mExUserBaseList.size());//设置答题数量
                }

            }
            if (mBoolean) {//不存在为true
                mExUserBaseList.add(exUserBase);
                tvScoreExaminationPr1.setText("" + mExUserBaseList.size());//设置答题数量
                if (mExUserBaseList.get(practicemodeViewpager.getCurrentItem()).isBoolean()){
                    dast.set(practicemodeViewpager.getCurrentItem(),new MyPracticeModeItem(MyPracticeModeItem.FIRST_TYPE, mExUserBaseList.get(practicemodeViewpager.getCurrentItem()).isBoolean()));
                }else {
                    dast.set(practicemodeViewpager.getCurrentItem(),new MyPracticeModeItem(MyPracticeModeItem.SECOND_TYPE, mExUserBaseList.get(practicemodeViewpager.getCurrentItem()).isBoolean()));
                }
            }

            if (mExUserBaseList.size() == mExamianBase.getData().size()) {
                initwc();
            }

            //  mExUserBaseList  答题正确数量
            mBoolean = true;


            inittruefoflase();//对错
        }
    }

    private void inittruefoflase() {
        truesum=0;
        for (int i = 0; i <mExUserBaseList.size() ; i++) {
            if (mExUserBaseList.get(i).isBoolean()){
                truesum++;//答对数量
            }else {

            }
        }
        tvSumTrue.setText(""+truesum);
        tvSumFalse.setText(""+(mExUserBaseList.size()-truesum));
    }

    private void initwc() {//完成交卷后点亮交卷
        ivCommitPract.setImageResource(R.mipmap.zaixiankaosi4);
        tvCommitPract.setTextColor(getResources().getColor(R.color.colorcommit));
        Mytoast();
    }

    private void getdata() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RequestParams> list=new ArrayList<>();
                list.add(new RequestParams("id",id));
                list.add(new RequestParams("workerId",workerid));
                list.add(new RequestParams("classificationId",classificationId));
                OkHttpUtils.doGet(MyURL.URL+"CourseQuestionBank",list,mHttpCallBack,0);
            }
        }).start();
    }


    @Override
    public void onResponse(String response, int requestId) {
            Message message = mHandler.obtainMessage();
            message.what = requestId;
            message.obj = response;
            mHandler.sendMessage(message);
           // Toast.makeText(this, "获取数据失败", Toast.LENGTH_SHORT).show();
          //  finish();

    }

    @Override
    public void onHandlerMessageCallback(String response, int requestId) {
        switch (requestId) {
            case 0:
                Log.e("Practirequest",response);
                    mExamianBase= JSON.parseObject(response, ExamianBase.class);
                    ViewComment(mExamianBase);
                    initfragmnet();
                   // Toast.makeText(this, "获取数据失败", Toast.LENGTH_SHORT).show();
                  //  finish();
                /** 倒计时60秒，一次1秒 */
                timer = new CountDownTimer(mExamianBase.getCourse().getTimeLength() * 1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        // TODO Auto-generated method stub
                        tvPrTime.setText("" + millisUntilFinished / 1000+"s");
                    }

                    @Override
                    public void onFinish() {
                        finish();
                        startActivity(new Intent(PracticeMode_Activity.this, TranscriptFailActivity.class));//跳转到不及格
                    }
                }.start();
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
