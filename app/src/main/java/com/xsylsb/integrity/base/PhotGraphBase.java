package com.xsylsb.integrity.base;

import java.util.List;

public class PhotGraphBase {

    /**
     * model : {"zhuGuanBuMen":0,"levelTag":"项目所在车间意见","statusTag":"待审核","id":78,"type":1,"permitId":54,"targetId":10,"level":2,"status":1,"workerId":35,"workerFullName":null,"opinion":null,"signInTime":"2019-12-11T17:54:34.76","images":null,"permit":null}
     * workShop : [{"value":"13","text":"自动化"},{"value":"19","text":"机动科"}]
     * status : [{"value":2,"text":"审批通过"},{"value":3,"text":"审批不通过"},{"value":4,"text":"终止审核"}]
     */

    private ModelBean model;
    private List<WorkShopBean> workShop;
    private List<StatusBean> status;

    public ModelBean getModel() {
        return model;
    }

    public void setModel(ModelBean model) {
        this.model = model;
    }

    public List<WorkShopBean> getWorkShop() {
        return workShop;
    }

    public void setWorkShop(List<WorkShopBean> workShop) {
        this.workShop = workShop;
    }

    public List<StatusBean> getStatus() {
        return status;
    }

    public void setStatus(List<StatusBean> status) {
        this.status = status;
    }

    public static class ModelBean {
        /**
         * zhuGuanBuMen : 0
         * levelTag : 项目所在车间意见
         * statusTag : 待审核
         * id : 78
         * type : 1
         * permitId : 54
         * targetId : 10
         * level : 2
         * status : 1
         * workerId : 35
         * workerFullName : null
         * opinion : null
         * signInTime : 2019-12-11T17:54:34.76
         * images : null
         * permit : null
         */

        private int zhuGuanBuMen;
        private String levelTag;
        private String statusTag;
        private int id;
        private int type;
        private int permitId;
        private int targetId;
        private int level;
        private int status;
        private int workerId;

        public int getSignRole() {
            return signRole;
        }

        public void setSignRole(int signRole) {
            this.signRole = signRole;
        }

        public String getJobLocationShopId() {
            return jobLocationShopId;
        }

        public void setJobLocationShopId(String jobLocationShopId) {
            this.jobLocationShopId = jobLocationShopId;
        }

        private int signRole;
        private String jobLocationShopId;
        private Object workerFullName;
        private Object opinion;
        private String signInTime;
        private Object images;
        private Object permit;

        public int getZhuGuanBuMen() {
            return zhuGuanBuMen;
        }

        public void setZhuGuanBuMen(int zhuGuanBuMen) {
            this.zhuGuanBuMen = zhuGuanBuMen;
        }

        public String getLevelTag() {
            return levelTag;
        }

        public void setLevelTag(String levelTag) {
            this.levelTag = levelTag;
        }

        public String getStatusTag() {
            return statusTag;
        }

        public void setStatusTag(String statusTag) {
            this.statusTag = statusTag;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getPermitId() {
            return permitId;
        }

        public void setPermitId(int permitId) {
            this.permitId = permitId;
        }

        public int getTargetId() {
            return targetId;
        }

        public void setTargetId(int targetId) {
            this.targetId = targetId;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getWorkerId() {
            return workerId;
        }

        public void setWorkerId(int workerId) {
            this.workerId = workerId;
        }

        public Object getWorkerFullName() {
            return workerFullName;
        }

        public void setWorkerFullName(Object workerFullName) {
            this.workerFullName = workerFullName;
        }

        public Object getOpinion() {
            return opinion;
        }

        public void setOpinion(Object opinion) {
            this.opinion = opinion;
        }

        public String getSignInTime() {
            return signInTime;
        }

        public void setSignInTime(String signInTime) {
            this.signInTime = signInTime;
        }

        public Object getImages() {
            return images;
        }

        public void setImages(Object images) {
            this.images = images;
        }

        public Object getPermit() {
            return permit;
        }

        public void setPermit(Object permit) {
            this.permit = permit;
        }
    }

    public static class WorkShopBean {
        /**
         * value : 13
         * text : 自动化
         */

        private String value;
        private String text;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    public static class StatusBean {
        /**
         * value : 2
         * text : 审批通过
         */

        private int value;
        private String text;
        private boolean is_select;

        public boolean isIs_select() {
            return is_select;
        }

        public void setIs_select(boolean is_select) {
            this.is_select = is_select;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
