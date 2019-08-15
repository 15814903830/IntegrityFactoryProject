package com.xsylsb.integrity.base;

/**
 * @author glsite.com
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class AddFaceBase {

    /**
     * suc : true
     * msg : 添加人脸认证成功
     */

    private boolean suc;
    private String msg;

    public boolean getSuc() {
        return suc;
    }

    public void setSuc(boolean suc) {
        this.suc = suc;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
