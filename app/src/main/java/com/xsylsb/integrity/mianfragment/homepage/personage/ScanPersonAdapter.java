package com.xsylsb.integrity.mianfragment.homepage.personage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.xsylsb.integrity.R;
import com.xsylsb.integrity.base.WorkerSelectBase;

import java.util.List;

/**
 * 地址列表适配器
 */
public class ScanPersonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private Context mContext;

    public List<WorkerSelectBase> getList() {
        return mList;
    }

    public void setList(List<WorkerSelectBase> list) {
        mList = list;
    }

    private List<WorkerSelectBase> mList;
    public ScanPersonAdapter(Context context, List<WorkerSelectBase> list) {
        this.mContext = context;
        this.mList = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.scan_personageitem, viewGroup, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int i) {
        final WorkerSelectBase seekBase = mList.get(i);

        if ( mList.get(i).isSelsecr()){
            ((ViewHolder) viewHolder).iv_select.setSelected(true);
        }else {
            ((ViewHolder) viewHolder).iv_select.setSelected(false);
        }
        ((ViewHolder) viewHolder).tv_person_name_item.setText(seekBase.getName());
        ((ViewHolder) viewHolder).tv_brand.setText(seekBase.getIdno());
        ((ViewHolder) viewHolder).ll_zonbuju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((ViewHolder) viewHolder).iv_select.isSelected()){
                    mList.get(i).setSelsecr(false);
                    ((ViewHolder) viewHolder).iv_select.setSelected(false);
                }else {
                    mList.get(i).setSelsecr(true);
                    ((ViewHolder) viewHolder).iv_select.setSelected(true);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onClick(View v) {
    }


    private class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_person_name_item;
        TextView tv_brand;
        LinearLayout ll_zonbuju;

        ImageView iv_select;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_select = itemView.findViewById(R.id.iv_select);
            tv_person_name_item = itemView.findViewById(R.id.tv_person_name_item);
            tv_brand = itemView.findViewById(R.id.tv_brand);
            ll_zonbuju = itemView.findViewById(R.id.ll_zonbuju);

        }
    }

}
