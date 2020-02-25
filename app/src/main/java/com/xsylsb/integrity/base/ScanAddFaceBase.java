package com.xsylsb.integrity.base;

import java.util.List;

public class ScanAddFaceBase {

    /**
     * data : [{"id":1092,"idno":"450205196110111312","fullName":"刘旭东","faceImages":null,"companyId":24},{"id":1101,"idno":"450202198003040619","fullName":"刘奕","faceImages":null,"companyId":23},{"id":1315,"idno":"86600976","fullName":"刘铭铿","faceImages":null,"companyId":65},{"id":1341,"idno":"410402196504233011","fullName":"刘祖云","faceImages":null,"companyId":20},{"id":1391,"idno":"452421196608020234","fullName":"刘国灿","faceImages":null,"companyId":67},{"id":1411,"idno":"452327197108113212","fullName":"刘国庆","faceImages":null,"companyId":61}]
     * suc : true
     * msg : null
     */

    private boolean suc;
    private Object msg;
    private List<DataBean> data;

    public boolean isSuc() {
        return suc;
    }

    public void setSuc(boolean suc) {
        this.suc = suc;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1092
         * idno : 450205196110111312
         * fullName : 刘旭东
         * faceImages : null
         * companyId : 24
         */

        private int id;
        private String idno;
        private String fullName;
        private Object faceImages;
        private int companyId;

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
    }
}
