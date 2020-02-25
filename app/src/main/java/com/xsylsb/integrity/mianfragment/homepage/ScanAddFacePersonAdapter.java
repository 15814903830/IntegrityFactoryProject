package com.xsylsb.integrity.mianfragment.homepage;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xsylsb.integrity.MainActivity;
import com.xsylsb.integrity.MainApplication;
import com.xsylsb.integrity.R;
import com.xsylsb.integrity.base.ScanAddFaceBase;
import com.xsylsb.integrity.face.activity.AddFaceRGBActivity;
import com.xsylsb.integrity.mianfragment.homepage.personage.SeekBase;

import java.util.List;

/**
 * 地址列表适配器
 */
public class ScanAddFacePersonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private Context mContext;

    public List<ScanAddFaceBase.DataBean> getList() {
        return mList;
    }

    public void setList(List<ScanAddFaceBase.DataBean> list) {
        mList = list;
    }

    private List<ScanAddFaceBase.DataBean> mList;
    private AddFacefot addFacefot;

    public ScanAddFacePersonAdapter(Context context, List<ScanAddFaceBase.DataBean> list,AddFacefot addFacefot) {
        this.mContext = context;
        this.mList = list;
        this.addFacefot=addFacefot;
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
        final ScanAddFaceBase.DataBean scanAddFaceBase = mList.get(i);
        Log.e("onBindViewHolder","-----");
        ((ViewHolder) viewHolder).tv_person_name_item.setText(scanAddFaceBase.getFullName());
        ((ViewHolder) viewHolder).tv_brand.setText(scanAddFaceBase.getIdno());
        ((ViewHolder) viewHolder).ll_zonbuju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (scanAddFaceBase.getFaceImages()==null){
                    Toast.makeText(mContext, "添加人脸数据", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(mContext, "修改人脸数据", Toast.LENGTH_SHORT).show();
                }
                addFacefot.AddFacefot(""+scanAddFaceBase.getId());
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

    public interface AddFacefot{
        void AddFacefot(String id);
    }
}
