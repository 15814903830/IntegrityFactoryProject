package com.xsylsb.integrity.Examination_adapter;

import android.util.Log;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xsylsb.integrity.R;
import com.xsylsb.integrity.practicemode_recyclview.Listuser;

import java.util.List;

/**
 * @author glsite.com
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class MultipleItemAdapter extends BaseMultiItemQuickAdapter<MyMultipleItem, BaseViewHolder> {
    public void setList(List<Listuser> list) {
        this.list = list;
    }

    List<Listuser> list;
    private int id=0;

    public MultipleItemAdapter(List data, List<Listuser> list) {
        super(data);
        //必须绑定type和layout的关系
        this.list = list;
        addItemType(MyMultipleItem.FIRST_TYPE, R.layout.item_question);//给对应编号布局绑定xml
    }

    @Override
    protected void convert(BaseViewHolder helper, MyMultipleItem item) {
        switch (helper.getItemViewType()) {
            case MyMultipleItem.FIRST_TYPE:
                                helper.setText(R.id.recyclview_item_tv,""+id++);
                TextView textView=helper.itemView.findViewById(R.id.recyclview_item_tv);
                textView.setSelected(item.getData());
                                //                        .setText(R.id.tv_title, item.getData().getTitle())
                                //                        .setText(R.id.tv_content, item.getData().getContent());
                                // helper.addOnClickListener(R.id.first_type_img);//给子空间添加监听
                break;
//            case MyMultipleItem.SECOND_TYPE:
//
//                break;
//            case MyMultipleItem.NORMAL_TYPE:
//
//                break;
        }
    }
}