package com.xsylsb.integrity.practicemode_recyclview;

/**
 * @author glsite.com
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class PractiBase {
    private boolean trueforfalse;
    private int sum;

    @Override
    public String toString() {
        return "PractiBase{" +
                "trueforfalse='" + trueforfalse + '\'' +
                ", sum=" + sum +
                '}';
    }

    public boolean getTrueforfalse() {
        return trueforfalse;
    }

    public void setTrueforfalse(boolean trueforfalse) {
        this.trueforfalse = trueforfalse;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }
}
