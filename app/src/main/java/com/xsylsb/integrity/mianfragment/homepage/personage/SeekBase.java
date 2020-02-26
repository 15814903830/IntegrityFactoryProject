package com.xsylsb.integrity.mianfragment.homepage.personage;

import java.util.List;

public class SeekBase {

    /**
     * id : 24
     * idno : 1
     * fullName : 宋先生01领导
     * gender : 0
     * faceImages : linshirenlian.jpg
     * companyId : null
     * workShopId : 2300
            * workAreaId : 2100000001
            * workAreas : ["2100000001"]
            */

    private String id;
    private String idno;
    private String fullName;
    private String gender;
    private String faceImages;
    private List<String> workAreas;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFaceImages() {
        return faceImages;
    }

    public void setFaceImages(String faceImages) {
        this.faceImages = faceImages;
    }

    public List<String> getWorkAreas() {
        return workAreas;
    }

    public void setWorkAreas(List<String> workAreas) {
        this.workAreas = workAreas;
    }
}
