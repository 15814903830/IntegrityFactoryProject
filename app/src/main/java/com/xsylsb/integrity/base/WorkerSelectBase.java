package com.xsylsb.integrity.base;

public class WorkerSelectBase {
    /**
     * value : 28
     * name : 敢想 8.0号人员
     * idno : gx1028
     */
    private String value;
    private String name;
    private String idno;

    public boolean isSelsecr() {
        return selsecr;
    }

    public void setSelsecr(boolean selsecr) {
        this.selsecr = selsecr;
    }

    private boolean selsecr;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdno() {
        return idno;
    }

    public void setIdno(String idno) {
        this.idno = idno;
    }
}
