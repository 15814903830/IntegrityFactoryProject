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
import com.xsylsb.integrity.Examination_adapter.ExaminationFragment;
import com.xsylsb.integrity.Examination_adapter.MultipleItemAdapter;
import com.xsylsb.integrity.Examination_adapter.MyExaminatioFragmentAdapter;
import com.xsylsb.integrity.Examination_adapter.MyMultipleItem;
import com.xsylsb.integrity.Examination_adapter.MyTopicSum;
import com.xsylsb.integrity.Examination_adapter.MylistviewInterface;
import com.xsylsb.integrity.base.CommitanswerBase;
import com.xsylsb.integrity.base.LoginBase;
import com.xsylsb.integrity.practicemode_recyclview.Listuser;
import com.xsylsb.integrity.userbase.TopicBase;
import com.xsylsb.integrity.util.HttpCallBack;
import com.xsylsb.integrity.util.MyURL;
import com.xsylsb.integrity.util.NoScrollViewPager;
import com.xsylsb.integrity.util.OkHttpUtils;
import com.xsylsb.integrity.util.RequestParams;
import com.xsylsb.integrity.util.dialog.BaseNiceDialog;
import com.xsylsb.integrity.util.dialog.NiceDialog;
import com.xsylsb.integrity.util.dialog.ViewConvertListener;
import com.xsylsb.integrity.util.dialog.ViewHolder;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
public class Examination_Activity extends AppCompatActivity implements MyTopicSum, MylistviewInterface,HttpCallBack {
    @BindView(R.id.iv_return_examination)
    ImageView ivReturnExamination;
    @BindView(R.id.Examination_viewpager)
    NoScrollViewPager ExaminationViewpager;
    @BindView(R.id.ll_submit_examination)
    LinearLayout llSubmitExamination;
    @BindView(R.id.iv_record_examination)
    ImageView ivRecordExamination;
    @BindView(R.id.tv_score_examination)
    TextView tvScoreExamination;
    @BindView(R.id.tv_score_examination2)
    TextView tvScoreExamination2;
    @BindView(R.id.ex_ll)
    LinearLayout exLl;
    @BindView(R.id.iv_commit_examin)
    ImageView ivCommitExamin;
    @BindView(R.id.tv_commit_examin)
    TextView tvCommitExamin;
    private CommitanswerBase mCommitanswerBase;
    private Toast toast;
    private MultipleItemAdapter adapter;
    private List<MyMultipleItem> dast = new ArrayList<>();
    private List<Listuser> mListusers = new ArrayList<>();
    private List<String> mStringList = new ArrayList<>();
    List<ExUserBase> mExUserBaseList = new ArrayList<>();//记录答题数量，答题对错，题目id
    @BindView(R.id.tv_ex_time)
    TextView tvPrTime;
    private CountDownTimer timer;
    List<TopicBase> mTopicBaseList = new ArrayList<>();//假数据
    private int booleansum = 0;
    List<Fragment> mFragmentList = new ArrayList<>();
    private int sum = 0;
    private boolean mBoolean=true;
    private String answer;
    private HttpCallBack mHttpCallBack;
    private ExamianBase mExamianBase;
    private int timesum=0;
    private String url;
    private String id,workerid,classificationId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.examination_activity);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);


        url=getIntent().getStringExtra("url");
        String s=url.split("\\?")[1];
        id=s.split("&")[0].split("=")[1];
        workerid=s.split("&")[1].split("=")[1];
        classificationId=s.split("&")[2].split("=")[1];
        ButterKnife.bind(this);
        mHttpCallBack=this;
        getdata();
        intimode();//模拟数据

        ExaminationViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int i, float v, int i1) {
                Log.e("onPageScrolled","onPageScrolled");
            }

            @Override
            public void onPageSelected(int i) {


            }

            @Override
            public void onPageScrollStateChanged(int i) {
                Log.e("StateChanged","StateChanged");
            }
        });

    }

    private void initfragmnet() {
        tvScoreExamination2.setText("" + mExamianBase.getData().size());//总题数
        for (int i = 0; i < mExamianBase.getData().size(); i++) {//根据题目数量 控制fragmnet个数
            mFragmentList.add(new ExaminationFragment(mExamianBase.getData().get(i)));
        }
        MyExaminatioFragmentAdapter examinatioFragmentAdapter = new MyExaminatioFragmentAdapter(getSupportFragmentManager(), mFragmentList);
        ExaminationViewpager.setAdapter(examinatioFragmentAdapter);
        ExaminationViewpager.setOffscreenPageLimit(mExamianBase.getData().size() - 1);
    }


    @OnClick({R.id.ex_ll, R.id.iv_return_examination, R.id.ll_submit_examination})
    public void MyOnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_return_examination:
                if (mExamianBase.getData().size() > mExUserBaseList.size()) {
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

                }
                //返回
                break;
            case R.id.ex_ll:
                //题目填写情况弹窗
                NiceDialog.init()
                        .setLayoutId(R.layout.recyclview_answer_examination)
                        .setConvertListener(new ViewConvertListener() {
                            @Override
                            protected void convertView(ViewHolder holder, BaseNiceDialog dialog) {
                                RecyclerView recyclerView = holder.getView(R.id.recylcview_answer_rv);
                                TextView textView = holder.getView(R.id.tv_score_examination_item);
                                TextView textView1 = holder.getView(R.id.tv_score_examination_item_sum);
                                textView.setText("" + mExUserBaseList.size());
                                textView1.setText("" +mExamianBase.getData().size());
                                recyclerView.setLayoutManager(new GridLayoutManager(Examination_Activity.this, 6, OrientationHelper.VERTICAL, false));
                                //创建适配器
                                adapter = new MultipleItemAdapter(dast, mListusers);
                                //给RecyclerView设置适配器
                                recyclerView.setAdapter(adapter);
                                adapter.bindToRecyclerView(recyclerView);
                                adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                        if (position - 1 < mExUserBaseList.size())//判断要跳转的viewpager是否小于答题总数量
                                            ExaminationViewpager.setCurrentItem(position);//选择后跳到下一题
                                    }
                                });

                            }
                        })
                        .setDimAmount(0.1f)
                        .setShowBottom(true)
                        .setAnimStyle(R.style.PracticeModeAnimation)
                        .show(getSupportFragmentManager());

                break;
            case R.id.ll_submit_examination:
                //交卷
                commitanswer();
                break;


        }
    }

    private void initcommtian(CommitanswerBase mCommitanswerBase) {
        if (mExUserBaseList.size() != mExamianBase.getData().size()) {
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
        }else if (mCommitanswerBase.isSuc()) {//答对
            startActivity(new Intent(Examination_Activity.this, TranscriptQualifiedActivity.class));
            finish();
            commitanswer();
        } else  {
            startActivity(new Intent(Examination_Activity.this, TranscriptFailActivity.class));
            finish();
        }

    }

    private void commitanswer() {
        int truesum=0;

        for (int i = 0; i <mExUserBaseList.size() ; i++) {
            if (mExUserBaseList.get(i).isBoolean()){
                truesum++;//答对数量
            }else {

            }

        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        commitanswers(simpleDateFormat.format(date),""+timesum,""+truesum,""+(mExUserBaseList.size()-truesum),true,answer);

    }


    public void ViewComment(ExamianBase examianBase) {
        //指定布局和传入数据,在HomeAdapter利用MyMultipleItem.FIRST_TYPE的值判断布局
        for (int i = 0; i < examianBase.getData().size(); i++) {
            Log.e("ViewComment", "ViewComment");
            dast.add(new MyMultipleItem(MyMultipleItem.FIRST_TYPE, false));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    private void intimode() {
    }//假数据题目

    @Override
    public void MyTopicSum(int sum, boolean b) {
        //答案验证   fragment回调b  true为正确   在交卷的时候验证分数
        //                Log.e("boolean", ""+b);
        //                if (b){
        //                    this.booleansum=booleansum+1;
        //                    Log.e("booleansum", ""+this.booleansum);
        //                }
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

    @Override
    public void MylistviewInterface(boolean b,String position) {//fragment返回答题正确与否

        answer=answer+position+",";
        Log.e("getChildCount",""+ExaminationViewpager.getCurrentItem());
        ExUserBase exUserBase=new ExUserBase();
        exUserBase.setBoolean(b);//设置答题正确与否
        exUserBase.setInt(ExaminationViewpager.getCurrentItem());//设置答了第几题
        inititem();//点亮列表，跳转下一题


        if (mExUserBaseList!=null){
            for (int i = 0; i < mExUserBaseList.size(); i++) {
                Log.e("6666getInt",""+mExUserBaseList.get(i).getInt());
                Log.e("6666getCurrentItem",""+ExaminationViewpager.getCurrentItem());
                if (mExUserBaseList.get(i).getInt()==ExaminationViewpager.getCurrentItem()){//如果在mExUserBaseList里面已经存在int=1,那么如果viewpagery也是1，就代表存在
                    mExUserBaseList.set(ExaminationViewpager.getCurrentItem(),exUserBase);//替换
                    mBoolean=false;//如果存在为false
                    Log.e("mBoolean",""+mExUserBaseList.size());
                    tvScoreExamination.setText(""+mExUserBaseList.size());//设置答题数量
                }

            }
            if (mBoolean){//不存在为true
                mExUserBaseList.add(exUserBase);
                tvScoreExamination.setText(""+mExUserBaseList.size());//设置答题数量
            }

            if (mExUserBaseList.size()==mExamianBase.getData().size()){
                ivCommitExamin.setImageResource(R.mipmap.zaixiankaosi4);
                tvCommitExamin.setTextColor(getResources().getColor(R.color.colorcommit));
                Mytoast();
            }


          //  mExUserBaseList  答题正确数量
            mBoolean=true;

        }

    }

    private void inititem() {
       // mListusers.get(ExaminationViewpager.getCurrentItem()).setBoolean(true);
        dast.set(ExaminationViewpager.getCurrentItem(),new MyMultipleItem(MyMultipleItem.FIRST_TYPE, true));//设置是否答过
        ExaminationViewpager.setCurrentItem(ExaminationViewpager.getCurrentItem()+1);//下一题
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


    private void commitanswers(final String startTime,final String takeUpTime,final String truesum,final String falsesumfinal ,final boolean ifqualified,final String answer) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject=new JSONObject();
                    jsonObject.put("courseId","2");//培训id
                    jsonObject.put("workerId",MyURL.id);//登录传来的id
                    jsonObject.put("startTime",startTime);//开始时间
                    jsonObject.put("takeUpTime",takeUpTime);//考试时长
                    jsonObject.put("correctNumber",truesum);//答对数量
                    jsonObject.put("correctNumber",falsesumfinal);//答错数量
                    jsonObject.put("isqualified",ifqualified);//是否合格
                    jsonObject.put("answerCard",answer);//答错数量
                    OkHttpUtils.doPostJson(MyURL.URL+"CourseExamRecord",jsonObject.toString(),mHttpCallBack,1);
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }).start();
    }


    @Override
    public void onResponse(String response, int requestId) {

        try {
            Message message = mHandler.obtainMessage();
            message.what = requestId;
            message.obj = response;
            mHandler.sendMessage(message);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "获取数据失败", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    @Override
    public void onHandlerMessageCallback(String response, int requestId) {
        Log.e("response",response);
        switch (requestId) {
            case 0:
                try {
                    mExamianBase= JSON.parseObject(response, ExamianBase.class);
                    Log.e("mExamianBase",""+mExamianBase.getData().size());
                    ViewComment(mExamianBase);
                    initfragmnet();//参加fragment  传入data数据
                    /** 倒计时60秒，一次1秒 */
                    timer = new CountDownTimer(mExamianBase.getCourse().getTimeLength() * 1000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            // TODO Auto-generated method stub
                            tvPrTime.setText("" + millisUntilFinished / 1000+"s");
                            timesum= (int) (millisUntilFinished / 1000);
                        }

                        @Override
                        public void onFinish() {
                            finish();
                            startActivity(new Intent(Examination_Activity.this, TranscriptFailActivity.class));//跳转到不及格
                        }
                    }.start();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "获取数据失败", Toast.LENGTH_SHORT).show();
                }
                break;
            case 1:
                try {
                    mCommitanswerBase= JSON.parseObject(response, CommitanswerBase.class);
                    Log.e("mCommitanswerBase", mCommitanswerBase.getMsg());
                    initcommtian(mCommitanswerBase);//验证结果
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "提交失败", Toast.LENGTH_SHORT).show();
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
