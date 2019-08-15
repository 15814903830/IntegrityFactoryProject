package com.xsylsb.integrity.practicemode_recyclview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xsylsb.integrity.R;

import java.util.List;

/**
 * @author glsite.com
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class MyPractilistviewAdapter extends BaseAdapter {

    private List<String> list;
    private  Context context;
    private int item=50;
    private boolean mBoolean;
    public boolean isBoolean() {
        return mBoolean;
    }

    public void setBoolean(boolean aBoolean) {
        mBoolean = aBoolean;
    }

    private String mABCString[]={"A","B","C","D","E","F","G","H","I","J","K","L","M"};
    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public MyPractilistviewAdapter(Context context, List<String> list){
        this.context=context;
        this.list=list;
    }
    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        VH vh=null;
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.mypractilistviewadapter,null);
            vh=new VH();
            vh.mTextView2=convertView.findViewById(R.id.tv_a_practi_fragment);
            vh.mTextView=convertView.findViewById(R.id.tv_a_text_fragment_practi);
            vh.mImageViewtrue=convertView.findViewById(R.id.iv_a_practi_fragment_true);
            vh.mImageViewfalse=convertView.findViewById(R.id.iv_a_practi_fragment_false);
            convertView.setTag(vh);
        }else {
            vh = (VH) convertView.getTag();
        }

        vh.mTextView.setText(list.get(position));
        vh.mTextView2.setText(mABCString[position]);
        Log.e("+++","sss");
        if (item==50){

        }else if (item==position){
//                vh.mImageViewtrue.setVisibility(View.VISIBLE);
//                vh.mImageViewfalse.setVisibility(View.VISIBLE);
            if (mBoolean){
                Log.e("mBooleanif","sss");
                vh.mTextView2.setVisibility(View.GONE);
                vh.mImageViewtrue.setVisibility(View.VISIBLE);
                vh.mImageViewfalse.setVisibility(View.GONE);
            }else {
                Log.e("mBooleanelse","sss");
                vh.mTextView2.setVisibility(View.GONE);
                vh.mImageViewtrue.setVisibility(View.GONE);
                vh.mImageViewfalse.setVisibility(View.VISIBLE);
            }

        }
        return convertView;
    }


    class VH{
        TextView mTextView2;
        TextView mTextView;
        ImageView mImageViewtrue;
        ImageView mImageViewfalse;
    }
}
