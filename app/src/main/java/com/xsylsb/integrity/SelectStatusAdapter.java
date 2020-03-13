package com.xsylsb.integrity;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xsylsb.integrity.base.PhotGraphBase;

import java.util.List;

public class SelectStatusAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;

    public List<PhotGraphBase.StatusBean> getList() {
        return mList;
    }

    public void setList(List<PhotGraphBase.StatusBean> list) {
        mList = list;
    }

    private List<PhotGraphBase.StatusBean> mList;
    private StatusOnItemClickListener voiceSupperzzleAdapter;

    public SelectStatusAdapter(Context context, List<PhotGraphBase.StatusBean> list, StatusOnItemClickListener voiceSupperzzleAdapter) {
        this.mContext = context;
        this.mList = list;
        this.voiceSupperzzleAdapter = voiceSupperzzleAdapter;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.select_status_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int i) {
        if (mList.get(i).isIs_select()) {
            ((ViewHolder) viewHolder).iv_shenghe1.setImageResource(R.mipmap.xuanzheyes);
        } else {
            ((ViewHolder) viewHolder).iv_shenghe1.setImageResource(R.mipmap.xuanzhetrue);
        }
        ((ViewHolder) viewHolder).tv_title.setText(mList.get(i).getText());
        ((ViewHolder) viewHolder).ll_shenghe1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                voiceSupperzzleAdapter.StatusOnItemClickListener(mList.get(i).getValue());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public interface StatusOnItemClickListener {
        void StatusOnItemClickListener(int id);
    }


    private class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title;
        ImageView iv_shenghe1;
        LinearLayout ll_shenghe1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            iv_shenghe1 = itemView.findViewById(R.id.iv_shenghe1);
            ll_shenghe1 = itemView.findViewById(R.id.ll_shenghe1);
        }
    }

}
