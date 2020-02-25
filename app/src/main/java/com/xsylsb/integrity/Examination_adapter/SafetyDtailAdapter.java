package com.xsylsb.integrity.Examination_adapter;

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

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.xsylsb.integrity.R;
import com.xsylsb.integrity.base.ProjectBase;

import java.util.List;

public class SafetyDtailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;

    public List<ProjectBase.ChildrenBean> getBookListBase() {
        return bookListBase;
    }

    public void setBookListBase(List<ProjectBase.ChildrenBean> bookListBase) {
        this.bookListBase = bookListBase;
    }

    private List<ProjectBase.ChildrenBean> bookListBase;
    private GetItemid getItemid;

    public String getSend() {
        return send;
    }

    public void setSend(String send) {
        this.send = send;
    }

    private String send;

    public SafetyDtailAdapter(Context context, List<ProjectBase.ChildrenBean> bookListBase, GetItemid getItemid, String send) {
        this.mContext = context;
        this.bookListBase = bookListBase;
        this.getItemid = getItemid;
        this.send = send;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.safety_dtail_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int i) {
        ((ViewHolder) viewHolder).tv_name.setText(bookListBase.get(i).getName());
        if (bookListBase.get(i).getIsselise().equals("ABC")) {
            ((ViewHolder) viewHolder).iv_exit.setSelected(true);
        } else {
            ((ViewHolder) viewHolder).iv_exit.setSelected(false);
        }

        if (send.equals("")) {
            ((ViewHolder) viewHolder).ll_select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (bookListBase.get(i).getIsselise().equals("ABC")) {
                        ((ViewHolder) viewHolder).iv_exit.setSelected(false);
                        bookListBase.get(i).setIsselise("SSS");
                    } else {
                        ((ViewHolder) viewHolder).iv_exit.setSelected(true);
                        bookListBase.get(i).setIsselise("ABC");
                    }
                    getItemid.GetItemid();
                }
            });
        } else {
            if (bookListBase.get(i).getIdno().contains(send) | bookListBase.get(i).getName().contains(send) | bookListBase.get(i).getValue().contains(send)) {
                ((ViewHolder) viewHolder).ll_select.setVisibility(View.VISIBLE);
            }else {
                ((ViewHolder) viewHolder).ll_select.setVisibility(View.GONE);
            }

            ((ViewHolder) viewHolder).ll_select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (bookListBase.get(i).getIsselise().equals("ABC")) {
                        ((ViewHolder) viewHolder).iv_exit.setSelected(false);
                        bookListBase.get(i).setIsselise("SSS");
                    } else {
                        ((ViewHolder) viewHolder).iv_exit.setSelected(true);
                        bookListBase.get(i).setIsselise("ABC");
                    }
                    getItemid.GetItemid();
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return bookListBase.size();
    }

    public interface GetItemid {
        void GetItemid();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_exit;
        TextView tv_name;
        LinearLayout ll_select;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            iv_exit = itemView.findViewById(R.id.iv_exit);
            ll_select = itemView.findViewById(R.id.ll_select);
        }
    }

}
