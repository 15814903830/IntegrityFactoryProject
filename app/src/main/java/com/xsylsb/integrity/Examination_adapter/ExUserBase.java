package com.xsylsb.integrity.Examination_adapter;

/**
 * @author glsite.com
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class ExUserBase {

    private int sumtrue;//答题正确数量
    private boolean mBoolean;//正确与否
    private int mInt;//记录item是否答过，答过则修改，否则创建

    public int getSumtrue() {
        return sumtrue;
    }

    public void setSumrue(int sumtrue) {
        this.sumtrue = sumtrue;
    }

    public boolean isBoolean() {
        return mBoolean;
    }

    public void setBoolean(boolean aBoolean) {
        mBoolean = aBoolean;
    }

    public int getInt() {
        return mInt;
    }

    public void setInt(int anInt) {
        mInt = anInt;
    }
}
