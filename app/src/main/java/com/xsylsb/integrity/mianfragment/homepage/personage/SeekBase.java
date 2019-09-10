package com.xsylsb.integrity.mianfragment.homepage.personage;

import java.util.List;

public class SeekBase {

    /**
     * id : 1100
     * idno : 362222197911033546
     * fullName : 黄志琴
     * gender : 1
     * faceImages : null
     * companyId : 47
     * workShopId : null
     * workAreaId : 10,12,13
     * workAreas : ["10","12","13"]
     */

    private int id;
    private String idno;
    private String fullName;
    private int gender;
    private Object faceImages;
    private int companyId;
    private Object workShopId;
    private String workAreaId;
    private List<String> workAreas;

    @Override
    public String toString() {
        return "SeekBase{" +
                "id=" + id +
                ", idno='" + idno + '\'' +
                ", fullName='" + fullName + '\'' +
                ", gender=" + gender +
                ", faceImages=" + faceImages +
                ", companyId=" + companyId +
                ", workShopId=" + workShopId +
                ", workAreaId='" + workAreaId + '\'' +
                ", workAreas=" + workAreas +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdno() {
        return idno;
    }

    public void setIdno(String idno) {
        this.idno = idno;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public Object getFaceImages() {
        return faceImages;
    }

    public void setFaceImages(Object faceImages) {
        this.faceImages = faceImages;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public Object getWorkShopId() {
        return workShopId;
    }

    public void setWorkShopId(Object workShopId) {
        this.workShopId = workShopId;
    }

    public String getWorkAreaId() {
        return workAreaId;
    }

    public void setWorkAreaId(String workAreaId) {
        this.workAreaId = workAreaId;
    }

    public List<String> getWorkAreas() {
        return workAreas;
    }

    public void setWorkAreas(List<String> workAreas) {
        this.workAreas = workAreas;
    }
}
