package com.xsylsb.integrity.practicemode_recyclview;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.xsylsb.integrity.practicemode_recyclview.Listuser;


/**
 * @author glsite.com
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class MyPracticeModeItem implements MultiItemEntity {

    public static final int FIRST_TYPE = 1;//设置布局编号
    public static final int SECOND_TYPE = 2;
    public static final int NORMAL_TYPE = 3;

    private int itemType;
    private Listuser list;
    private boolean turefotfalse;


    public MyPracticeModeItem(int itemType, boolean turefotfalse) {
        this.itemType = itemType;
        this.turefotfalse = turefotfalse;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public boolean  getData(){
        return  turefotfalse;
    }
}