package com.xsylsb.integrity.mianfragment.homepage.personage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.xsylsb.integrity.R;

import java.util.List;

/**
 * 地址列表适配器
 */
public class PersonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private Context mContext;

    public List<SeekBase> getList() {
        return mList;
    }

    public void setList(List<SeekBase> list) {
        mList = list;
    }

    private List<SeekBase> mList;
    private defaultAddress mDefaultAddress;

    public PersonAdapter(Context context, List<SeekBase> list, defaultAddress mDefaultAddress) {
        this.mContext = context;
        this.mList = list;
        this.mDefaultAddress=mDefaultAddress;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.personageitem, viewGroup, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        final SeekBase seekBase = mList.get(i);
        Log.e("onBindViewHolder","-----");
        ((ViewHolder) viewHolder).tv_person_name_item.setText(seekBase.getFullName());
        ((ViewHolder) viewHolder).tv_brand.setText(seekBase.getIdno());
        ((ViewHolder) viewHolder).ll_zonbuju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDefaultAddress.seeki(Integer.parseInt(seekBase.getId()));
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_person_name_item = itemView.findViewById(R.id.tv_person_name_item);
            tv_brand = itemView.findViewById(R.id.tv_brand);
            ll_zonbuju = itemView.findViewById(R.id.ll_zonbuju);

        }
    }

    public interface defaultAddress{
        void seeki(int i);
    }
}
