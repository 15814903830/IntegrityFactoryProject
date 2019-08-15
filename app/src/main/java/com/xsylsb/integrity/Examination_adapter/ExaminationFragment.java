package com.xsylsb.integrity.Examination_adapter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.xsylsb.integrity.R;
import com.xsylsb.integrity.userbase.TopicBase;
import com.xsylsb.integrity.util.DensityUtil;
import com.xsylsb.integrity.util.RadiusBackgroundColorSpan;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


/**
 * @author glsite.com
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
@SuppressLint("ValidFragment")
public class ExaminationFragment extends Fragment implements AdapterView.OnItemClickListener {
    private String mABCString[]={"A","B","C","D","E","F","G","H","I","J","K","L","M"};
    TextView tvTopicExamintionFragment;
    ImageView ivAExaminationFragment;
    TextView tvATextFragment;
    LinearLayout llAExaminationFragment;
    TextView tvBTextFragment;
    private View mView;
    private MyTopicSum mMyTopicSum;
    private TopicBase mTopicBase;
    private ListView listviwe;
    private MylistviewAdapter mylistviewAdapter;
    private MylistviewInterface mMylistviewInterface;
    private ExamianBase.DataBean mDataBean;
    private List<String> option=new ArrayList<>();
    //声明
    public ExaminationFragment(ExamianBase.DataBean mDataBean) {
        this.mDataBean = mDataBean;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.examinatiofragment, container, false);
        initview();
        initdate();


        mylistviewAdapter=new MylistviewAdapter(getContext(),option);
        listviwe.setOnItemClickListener(this);
        listviwe.setAdapter(mylistviewAdapter);
        mMylistviewInterface= (MylistviewInterface) getContext();
        return mView;
    }

    private void initdate() {
        try {
            option.clear();
            JSONObject object=new JSONObject(mDataBean.getOption());
           Iterator<String> keys= object.keys();
           while (keys.hasNext()){
               String key = keys.next();
               option.add(object.getString(key));

           }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private void initview() {
        listviwe=mView.findViewById(R.id.listview_exmian);
        tvTopicExamintionFragment=mView.findViewById(R.id.tv_topic_examintion_fragment);
        tvATextFragment=mView.findViewById(R.id.tv_a_text_fragment);
        llAExaminationFragment=mView.findViewById(R.id.ll_a_examination_fragment);
         ivAExaminationFragment=mView.findViewById(R.id.iv_a_examination_fragment);
         initspan(mDataBean.getTitle());//设置题目
    }

    private void initspan(String topi) {
        SpannableString spannableString = new SpannableString("单选  "+topi);
        RadiusBackgroundColorSpan radiusBackgroundColorSpan = new RadiusBackgroundColorSpan(
                getResources().getColor(R.color.colorRadio), DensityUtil.dip2px(getContext(), 2));
        ForegroundColorSpan span = new ForegroundColorSpan(getResources().getColor(R.color
                .colortext));//修改文字颜色
        spannableString.setSpan(radiusBackgroundColorSpan, 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(span, 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvTopicExamintionFragment.setText(spannableString);
        tvTopicExamintionFragment.setMovementMethod(LinkMovementMethod.getInstance());  //为TextView设置完Span后，别忘了setMovementMethod
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            mylistviewAdapter.setItem(position);
            mylistviewAdapter.notifyDataSetChanged();

            Log.e("position",""+position);
        Log.e("getAnswer",mDataBean.getAnswer());
        mMylistviewInterface.MylistviewInterface(mDataBean.getAnswer().equals(""+position),""+position);//对比答案

    }



}
