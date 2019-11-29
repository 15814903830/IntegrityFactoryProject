package com.xsylsb.integrity.mianfragment.homepage.personage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import com.xsylsb.integrity.R;
import java.util.HashMap;
import java.util.List;

/**
 * 地址列表适配器
 */
public class TpyeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private Context mContext;

    public List<TypeBase> getList() {
        return mList;
    }

    public void setList(List<TypeBase> list) {
        mList = list;
    }

    private List<TypeBase> mList;
    private gettpyeid gettpyeid;
    // 用于记录每个RadioButton的状态，并保证只可选一个
    HashMap<String, Boolean> states = new HashMap<String, Boolean>();
    public TpyeAdapter(Context context, List<TypeBase> list, gettpyeid gettpyeid) {
        this.mContext = context;
        this.mList = list;
        this.gettpyeid=gettpyeid;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.typeitem_layout, viewGroup, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        final TypeBase typeBase = mList.get(i);
        ((ViewHolder) viewHolder).tv_text.setText(typeBase.getTitle());
        ((ViewHolder) viewHolder).tv_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 重置，确保最多只有一项被选中
                for (String key : states.keySet()) {
                    states.put(key, false);
                }
                states.put(String.valueOf(i), true);
                TpyeAdapter.this.notifyDataSetChanged();
                gettpyeid.gettpyeid(typeBase.getId());
            }
        });

        ((ViewHolder) viewHolder).radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 重置，确保最多只有一项被选中
                for (String key : states.keySet()) {
                    states.put(key, false);
                }
                states.put(String.valueOf(i), true);
                TpyeAdapter.this.notifyDataSetChanged();
                gettpyeid.gettpyeid(typeBase.getId());
            }
        });
        boolean res = false;
        if (states.get(String.valueOf(i)) == null || states.get(String.valueOf(i)) == false) {
            res = false;
            states.put(String.valueOf(i), false);
        } else
            res = true;
        ((ViewHolder) viewHolder).radioButton.setChecked(res);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onClick(View v) {
    }


    private class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_text;
        RadioButton radioButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_text = itemView.findViewById(R.id.tv_text);
            radioButton = itemView.findViewById(R.id.rb_radio_button);

        }
    }

    public interface gettpyeid{
        void gettpyeid(int i);
    }
}
