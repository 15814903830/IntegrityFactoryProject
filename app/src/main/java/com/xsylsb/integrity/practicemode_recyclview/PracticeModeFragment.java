package com.xsylsb.integrity.practicemode_recyclview;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import com.xsylsb.integrity.Examination_adapter.ExamianBase;
import com.xsylsb.integrity.R;
import com.xsylsb.integrity.userbase.TopicBase;
import com.xsylsb.integrity.util.DensityUtil;
import com.xsylsb.integrity.util.MyListView;
import com.xsylsb.integrity.util.RadiusBackgroundColorSpan;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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
public class PracticeModeFragment extends Fragment implements AdapterView.OnItemClickListener{

    private View mView;
    private TextView tvTopicExamintionPrFragment;
    private TextView tvAnswerPrFragment;
    private TextView tvAnalysisPrFragment;
    private MyListView mListView;
    private boolean listvieonclcone=true;
    private MyPractilistviewAdapter adapter;
    private LinearLayout practicemodellanswerprfragment;
    private MyPractilistviewInterface mMyPractilistviewInterface;
    private ExamianBase.DataBean mDataBean;
    private List<String> option=new ArrayList<>();
    public PracticeModeFragment(ExamianBase.DataBean mDataBean) {
        this.mDataBean=mDataBean;
    }
    private String mABCString[]={"A","B","C","D","E","F","G","H","I","J","K","L","M"};
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.practicemode_fragmenrt, container, false);
        initdate();
        initView(mView);
        Log.e("mDataBean",""+mDataBean.getTitle());
         adapter=new MyPractilistviewAdapter(getContext(),option);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(this);
        mMyPractilistviewInterface= (MyPractilistviewInterface) getContext();

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

    private void initspan(String topi) {
        SpannableString spannableString = new SpannableString("单选  "+ topi);
        RadiusBackgroundColorSpan radiusBackgroundColorSpan = new RadiusBackgroundColorSpan(
                getResources().getColor(R.color.colorRadio), DensityUtil.dip2px(getContext(), 2));
        ForegroundColorSpan span = new ForegroundColorSpan(getResources().getColor(R.color
                .colortext));//修改文字颜色
        spannableString.setSpan(radiusBackgroundColorSpan, 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(span, 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvTopicExamintionPrFragment.setText(spannableString);
        tvTopicExamintionPrFragment.setMovementMethod(LinkMovementMethod.getInstance());  //为TextView设置完Span后，别忘了setMovementMethod
    }


    private void initView(View mView) {
        tvTopicExamintionPrFragment = mView.findViewById(R.id.tv_topic_examintion_pr_fragment);
        practicemodellanswerprfragment=mView.findViewById(R.id.practicemode_ll_answer_pr_fragment);
        mListView=mView.findViewById(R.id.practi_listview);
        tvAnswerPrFragment = mView.findViewById(R.id.tv_answer_pr_fragment);
        tvAnalysisPrFragment = mView.findViewById(R.id.tv_analysis_pr_fragment);
        initspan(mDataBean.getTitle());
       tvAnswerPrFragment.setText(mABCString[Integer.parseInt(mDataBean.getAnswer())]);
       tvAnalysisPrFragment.setText(""+mDataBean.getDescribe());//题目解析


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        if (listvieonclcone){
            adapter.setItem(position);
            String p=""+position;
            if (p.equals(mDataBean.getAnswer())){
                Log.e("equals",mDataBean.getAnswer());
                Log.e("equals",""+position);
                adapter.setBoolean(true);//根据答案是否正确
            }else {

                adapter.setBoolean(false);//根据答案是否正确
            }
            adapter.notifyDataSetChanged();
            listvieonclcone=false;
            //根据是否正确来判断是否显示答案
            practicemodellanswerprfragment.setVisibility(View.VISIBLE);
            //practicemodellanswerprfragment.setText("A");//正确答案
        }
        mMyPractilistviewInterface.MyPractilistviewInterface(mDataBean.getAnswer().equals(""+position),""+position);

    }
}
