package com.xsylsb.integrity.face.adapter;

import android.content.Context;
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
public class MyFacelistviewAdapter extends BaseAdapter {

    private List<String> list;
    private  Context context;

    public void setList(List<String> list) {
        this.list = list;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public MyFacelistviewAdapter(Context context, List<String> list){
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
            convertView= LayoutInflater.from(context).inflate(R.layout.facelistviewitem,null);
            vh=new VH();
            vh.text=convertView.findViewById(R.id.tv_face_text);
            convertView.setTag(vh);
        }else {
            vh = (VH) convertView.getTag();
        }

        vh.text.setText(list.get(position));

        return convertView;
    }


    class VH{
        TextView text;
    }
}
