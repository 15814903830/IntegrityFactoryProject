package com.xsylsb.integrity.practicemode_recyclview;

import android.util.Log;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xsylsb.integrity.Examination_adapter.MyMultipleItem;
import com.xsylsb.integrity.R;

import java.util.List;

/**
 * @author glsite.com
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class PracticeModeAdapter extends BaseMultiItemQuickAdapter<MyPracticeModeItem, BaseViewHolder> {
    List<Listuser> list;
    private int id=0;
    public PracticeModeAdapter(List data, List<Listuser> list) {
        super(data);
        //必须绑定type和layout的关系
        this.list = list;
        addItemType(MyPracticeModeItem.FIRST_TYPE, R.layout.item_correct);//给对应编号布局绑定
        addItemType(MyPracticeModeItem.SECOND_TYPE, R.layout.item_error);//给对应编号布局绑定xml
        addItemType(MyPracticeModeItem.NORMAL_TYPE, R.layout.item_question);//给对应编号布局绑定xml
    }

    @Override
    protected void convert(BaseViewHolder helper, MyPracticeModeItem item) {
        switch (helper.getItemViewType()) {
            case MyMultipleItem.FIRST_TYPE:
                //正确
                helper.setText(R.id.recyclview_item_tv_correct,""+id++);
                break;
            case MyMultipleItem.SECOND_TYPE:
                //错误
                helper.setText(R.id.recyclview_item_tv_error,""+id++);
                break;
            case MyMultipleItem.NORMAL_TYPE:
                //待答题
                helper.setText(R.id.recyclview_item_tv,""+id++);
                break;
        }
    }
}