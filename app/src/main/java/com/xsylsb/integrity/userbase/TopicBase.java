package com.xsylsb.integrity.userbase;

/**
 * @author glsite.com
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class TopicBase {
    private int  sum;

    public int getSum() {
        return sum;
    }


    public void setSum(int sum) {
        this.sum = sum;
    }

    private String topi;//题目
    private  String a;//选项a
    private String b;//选项b
    private String answer;//答案
    private String analysis;

    @Override
    public String toString() {
        return "TopicBase{" +
                "sum=" + sum +
                ", topi='" + topi + '\'' +
                ", a='" + a + '\'' +
                ", b='" + b + '\'' +
                ", answer='" + answer + '\'' +
                ", analysis='" + analysis + '\'' +
                ", hasAnswer=" + hasAnswer +
                ", right=" + right +
                '}';
    }

    public boolean isHasAnswer() {
        return hasAnswer;
    }

    public void setHasAnswer(boolean hasAnswer) {
        this.hasAnswer = hasAnswer;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    private boolean hasAnswer;//是否已答
    private boolean right;//是否正确

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }


    public String getTopi() {
        return topi;
    }

    public void setTopi(String topi) {
        this.topi = topi;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

}
