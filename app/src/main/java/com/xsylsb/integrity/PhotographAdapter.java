package com.xsylsb.integrity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

public class PhotographAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;

    public List<String> getList() {
        return mList;
    }

    public void setList(List<String> list) {
        mList = list;
    }

    private List<String> mList;


    private MyClassifyAdapterOnItem myClassifyAdapterOnItem;
    public PhotographAdapter(Context context, List<String> commmentList, MyClassifyAdapterOnItem myClassifyAdapterOnItem) {
        this.mContext = context;
        this.mList = commmentList;
        this.myClassifyAdapterOnItem = myClassifyAdapterOnItem;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.photograph_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int i) {
        if (mList.get(i).equals("88")){
            ((ViewHolder) viewHolder).iv_clear_img.setVisibility(View.GONE);
            Glide.with(mContext).load(R.mipmap.shenpiy13).into(((ViewHolder) viewHolder).iv_img);
            ((ViewHolder) viewHolder).iv_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myClassifyAdapterOnItem.OnItemClickListener(123456);
                }
            });
            return;
        }

        ((ViewHolder) viewHolder).iv_clear_img.setVisibility(View.VISIBLE);
        Glide.with(mContext).load(mList.get(i)).into(((ViewHolder) viewHolder).iv_img);
            ((ViewHolder) viewHolder).iv_clear_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myClassifyAdapterOnItem.OnItemClickListener(i);
                }
            });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public  interface MyClassifyAdapterOnItem{
        void OnItemClickListener(int i);
    }


    private class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_clear_img;
        ImageView iv_img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_clear_img = itemView.findViewById(R.id.iv_clear_img);
            iv_img = itemView.findViewById(R.id.iv_img);
        }
    }

}
