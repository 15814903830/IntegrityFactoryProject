package com.xsylsb.integrity.mianfragment.homepage.personage;

import java.util.List;

public class TypeBase {

    /**
     * id : 10
     * pid : 0
     * title : 三级安全培训
     * isFixed : true
     * course : []
     */

    private int id;
    private int pid;
    private String title;
    private boolean isFixed;
    private List<?> course;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isIsFixed() {
        return isFixed;
    }

    public void setIsFixed(boolean isFixed) {
        this.isFixed = isFixed;
    }

    public List<?> getCourse() {
        return course;
    }

    public void setCourse(List<?> course) {
        this.course = course;
    }
}
