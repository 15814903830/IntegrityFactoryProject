package com.xsylsb.integrity.Examination_adapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.xsylsb.integrity.practicemode_recyclview.Listuser;


/**
 * @author glsite.com
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class MyMultipleItem implements MultiItemEntity {

    public static final int FIRST_TYPE = 1;//设置布局编号
    public static final int SECOND_TYPE = 2;
    public static final int NORMAL_TYPE = 3;

    private int itemType;
    private boolean trueforfalse;


    public MyMultipleItem(int itemType, boolean trueforfalse) {
        this.itemType = itemType;
        this.trueforfalse = trueforfalse;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public boolean  getData(){
        return  trueforfalse;
    }
}