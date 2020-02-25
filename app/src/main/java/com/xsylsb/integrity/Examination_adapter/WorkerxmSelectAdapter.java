package com.xsylsb.integrity.Examination_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xsylsb.integrity.R;
import com.xsylsb.integrity.base.ProjectBase;
import com.xsylsb.integrity.base.WorkerSelectBase;

import java.util.List;

public class WorkerxmSelectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;

    public List<WorkerSelectBase> getBookListBase() {
        return bookListBase;
    }

    public void setBookListBase(List<WorkerSelectBase> bookListBase) {
        this.bookListBase = bookListBase;
    }

    private List<WorkerSelectBase> bookListBase;
    private OnItmeClickListener onItmeClickListener;
    public WorkerxmSelectAdapter(Context context, List<WorkerSelectBase> bookListBase, OnItmeClickListener onItmeClickListener) {
        this.mContext = context;
        this.bookListBase = bookListBase;
        this.onItmeClickListener = onItmeClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.workerxm_select_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int i) {

        if (i==bookListBase.size()-1){
            ((ViewHolder) viewHolder).ll_add.setVisibility(View.VISIBLE);
            ((ViewHolder) viewHolder).fl_ase.setVisibility(View.GONE);
            ((ViewHolder) viewHolder).ll_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItmeClickListener.OnItmeClickListener("添加");
                }
            });
        }else {
            ((ViewHolder) viewHolder).ll_add.setVisibility(View.GONE);
            ((ViewHolder) viewHolder).fl_ase.setVisibility(View.VISIBLE);
        }

        ((ViewHolder) viewHolder).tv_name.setText(bookListBase.get(i).getName());
        ((ViewHolder) viewHolder).iv_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItmeClickListener.OnItmeClickListener(bookListBase.get(i).getIdno());
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookListBase.size();
    }

    public interface OnItmeClickListener{
        void OnItmeClickListener(String id);
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_exit;
        TextView tv_name;
        LinearLayout ll_add;
        FrameLayout fl_ase;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            iv_exit= itemView.findViewById(R.id.iv_exit);
            ll_add= itemView.findViewById(R.id.ll_add);
            fl_ase= itemView.findViewById(R.id.fl_ase);
        }
    }

}
