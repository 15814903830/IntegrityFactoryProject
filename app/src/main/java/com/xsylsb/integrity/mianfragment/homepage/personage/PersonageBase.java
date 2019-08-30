package com.xsylsb.integrity.mianfragment.homepage.personage;

import java.util.List;

public class PersonageBase {

    /**
     * data : {"age":47,"dengJiTag":"车间/科室级","statusTag":"在岗","workShop":null,"company":null,"workArea":null,"workAreaNames":null,"workJob":null,"securityClassHour":0,"sanJiAnQuanPeiXunClassHour":0,"zaiJiaoYuPeiXunClassHour":0,"badRecord":null,"jurisdictionValues":null,"id":36,"role":0,"bossId":2,"userName":null,"idno":"81301014","idstarTime":null,"idendTime":null,"faceImages":"UploadFiles/TenCentFaces/201908//Member36/a0430253-7fb1-4374-97dd-8f49114a178b.png","password":"EEE6856C870DE22D0F975E35ECA55E50","fullName":"彭仕军","gender":1,"birthday":"1972-02-19T00:00:00","companyId":null,"workShopId":11,"workAreaId":"11","jobId":26,"dengJi":2,"safetyExamination":false,"isCompanyHead":false,"isSpecialOperator":false,"isSafetyManager":false,"totalBadRecordScore":0,"totalViolationRecord":0,"totalCompanyHour":0,"status":0,"leaveTime":null,"createTime":"2019-07-15T16:26:33.023","blacklist":null,"workerJurisdiction":null,"courseExamRecord":[],"courseSignIn":[],"forceCourseExamRecord":[],"jobRecord":[],"notice":[],"operationCertificate":[],"workAreaBoss":[]}
     * suc : true
     * msg : /Worker/Credit/36
     */

    private DataBean data;
    private boolean suc;
    private String msg;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public boolean isSuc() {
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

    public static class DataBean {
        /**
         * age : 47
         * dengJiTag : 车间/科室级
         * statusTag : 在岗
         * workShop : null
         * company : null
         * workArea : null
         * workAreaNames : null
         * workJob : null
         * securityClassHour : 0
         * sanJiAnQuanPeiXunClassHour : 0
         * zaiJiaoYuPeiXunClassHour : 0
         * badRecord : null
         * jurisdictionValues : null
         * id : 36
         * role : 0
         * bossId : 2
         * userName : null
         * idno : 81301014
         * idstarTime : null
         * idendTime : null
         * faceImages : UploadFiles/TenCentFaces/201908//Member36/a0430253-7fb1-4374-97dd-8f49114a178b.png
         * password : EEE6856C870DE22D0F975E35ECA55E50
         * fullName : 彭仕军
         * gender : 1
         * birthday : 1972-02-19T00:00:00
         * companyId : null
         * workShopId : 11
         * workAreaId : 11
         * jobId : 26
         * dengJi : 2
         * safetyExamination : false
         * isCompanyHead : false
         * isSpecialOperator : false
         * isSafetyManager : false
         * totalBadRecordScore : 0
         * totalViolationRecord : 0
         * totalCompanyHour : 0
         * status : 0
         * leaveTime : null
         * createTime : 2019-07-15T16:26:33.023
         * blacklist : null
         * workerJurisdiction : null
         * courseExamRecord : []
         * courseSignIn : []
         * forceCourseExamRecord : []
         * jobRecord : []
         * notice : []
         * operationCertificate : []
         * workAreaBoss : []
         */

        private int age;
        private String dengJiTag;
        private String statusTag;
        private Object workShop;
        private Object company;
        private Object workArea;
        private Object workAreaNames;
        private Object workJob;
        private int securityClassHour;
        private int sanJiAnQuanPeiXunClassHour;
        private int zaiJiaoYuPeiXunClassHour;
        private Object badRecord;
        private Object jurisdictionValues;
        private int id;
        private int role;
        private int bossId;
        private Object userName;
        private String idno;
        private Object idstarTime;
        private Object idendTime;
        private String faceImages;
        private String password;
        private String fullName;
        private int gender;
        private String birthday;
        private Object companyId;
        private int workShopId;
        private String workAreaId;
        private int jobId;
        private int dengJi;
        private boolean safetyExamination;
        private boolean isCompanyHead;
        private boolean isSpecialOperator;
        private boolean isSafetyManager;
        private int totalBadRecordScore;
        private int totalViolationRecord;
        private int totalCompanyHour;
        private int status;
        private Object leaveTime;
        private String createTime;
        private Object blacklist;
        private Object workerJurisdiction;
        private List<?> courseExamRecord;
        private List<?> courseSignIn;
        private List<?> forceCourseExamRecord;
        private List<?> jobRecord;
        private List<?> notice;
        private List<?> operationCertificate;
        private List<?> workAreaBoss;

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getDengJiTag() {
            return dengJiTag;
        }

        public void setDengJiTag(String dengJiTag) {
            this.dengJiTag = dengJiTag;
        }

        public String getStatusTag() {
            return statusTag;
        }

        public void setStatusTag(String statusTag) {
            this.statusTag = statusTag;
        }

        public Object getWorkShop() {
            return workShop;
        }

        public void setWorkShop(Object workShop) {
            this.workShop = workShop;
        }

        public Object getCompany() {
            return company;
        }

        public void setCompany(Object company) {
            this.company = company;
        }

        public Object getWorkArea() {
            return workArea;
        }

        public void setWorkArea(Object workArea) {
            this.workArea = workArea;
        }

        public Object getWorkAreaNames() {
            return workAreaNames;
        }

        public void setWorkAreaNames(Object workAreaNames) {
            this.workAreaNames = workAreaNames;
        }

        public Object getWorkJob() {
            return workJob;
        }

        public void setWorkJob(Object workJob) {
            this.workJob = workJob;
        }

        public int getSecurityClassHour() {
            return securityClassHour;
        }

        public void setSecurityClassHour(int securityClassHour) {
            this.securityClassHour = securityClassHour;
        }

        public int getSanJiAnQuanPeiXunClassHour() {
            return sanJiAnQuanPeiXunClassHour;
        }

        public void setSanJiAnQuanPeiXunClassHour(int sanJiAnQuanPeiXunClassHour) {
            this.sanJiAnQuanPeiXunClassHour = sanJiAnQuanPeiXunClassHour;
        }

        public int getZaiJiaoYuPeiXunClassHour() {
            return zaiJiaoYuPeiXunClassHour;
        }

        public void setZaiJiaoYuPeiXunClassHour(int zaiJiaoYuPeiXunClassHour) {
            this.zaiJiaoYuPeiXunClassHour = zaiJiaoYuPeiXunClassHour;
        }

        public Object getBadRecord() {
            return badRecord;
        }

        public void setBadRecord(Object badRecord) {
            this.badRecord = badRecord;
        }

        public Object getJurisdictionValues() {
            return jurisdictionValues;
        }

        public void setJurisdictionValues(Object jurisdictionValues) {
            this.jurisdictionValues = jurisdictionValues;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getRole() {
            return role;
        }

        public void setRole(int role) {
            this.role = role;
        }

        public int getBossId() {
            return bossId;
        }

        public void setBossId(int bossId) {
            this.bossId = bossId;
        }

        public Object getUserName() {
            return userName;
        }

        public void setUserName(Object userName) {
            this.userName = userName;
        }

        public String getIdno() {
            return idno;
        }

        public void setIdno(String idno) {
            this.idno = idno;
        }

        public Object getIdstarTime() {
            return idstarTime;
        }

        public void setIdstarTime(Object idstarTime) {
            this.idstarTime = idstarTime;
        }

        public Object getIdendTime() {
            return idendTime;
        }

        public void setIdendTime(Object idendTime) {
            this.idendTime = idendTime;
        }

        public String getFaceImages() {
            return faceImages;
        }

        public void setFaceImages(String faceImages) {
            this.faceImages = faceImages;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
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

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public Object getCompanyId() {
            return companyId;
        }

        public void setCompanyId(Object companyId) {
            this.companyId = companyId;
        }

        public int getWorkShopId() {
            return workShopId;
        }

        public void setWorkShopId(int workShopId) {
            this.workShopId = workShopId;
        }

        public String getWorkAreaId() {
            return workAreaId;
        }

        public void setWorkAreaId(String workAreaId) {
            this.workAreaId = workAreaId;
        }

        public int getJobId() {
            return jobId;
        }

        public void setJobId(int jobId) {
            this.jobId = jobId;
        }

        public int getDengJi() {
            return dengJi;
        }

        public void setDengJi(int dengJi) {
            this.dengJi = dengJi;
        }

        public boolean isSafetyExamination() {
            return safetyExamination;
        }

        public void setSafetyExamination(boolean safetyExamination) {
            this.safetyExamination = safetyExamination;
        }

        public boolean isIsCompanyHead() {
            return isCompanyHead;
        }

        public void setIsCompanyHead(boolean isCompanyHead) {
            this.isCompanyHead = isCompanyHead;
        }

        public boolean isIsSpecialOperator() {
            return isSpecialOperator;
        }

        public void setIsSpecialOperator(boolean isSpecialOperator) {
            this.isSpecialOperator = isSpecialOperator;
        }

        public boolean isIsSafetyManager() {
            return isSafetyManager;
        }

        public void setIsSafetyManager(boolean isSafetyManager) {
            this.isSafetyManager = isSafetyManager;
        }

        public int getTotalBadRecordScore() {
            return totalBadRecordScore;
        }

        public void setTotalBadRecordScore(int totalBadRecordScore) {
            this.totalBadRecordScore = totalBadRecordScore;
        }

        public int getTotalViolationRecord() {
            return totalViolationRecord;
        }

        public void setTotalViolationRecord(int totalViolationRecord) {
            this.totalViolationRecord = totalViolationRecord;
        }

        public int getTotalCompanyHour() {
            return totalCompanyHour;
        }

        public void setTotalCompanyHour(int totalCompanyHour) {
            this.totalCompanyHour = totalCompanyHour;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public Object getLeaveTime() {
            return leaveTime;
        }

        public void setLeaveTime(Object leaveTime) {
            this.leaveTime = leaveTime;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public Object getBlacklist() {
            return blacklist;
        }

        public void setBlacklist(Object blacklist) {
            this.blacklist = blacklist;
        }

        public Object getWorkerJurisdiction() {
            return workerJurisdiction;
        }

        public void setWorkerJurisdiction(Object workerJurisdiction) {
            this.workerJurisdiction = workerJurisdiction;
        }

        public List<?> getCourseExamRecord() {
            return courseExamRecord;
        }

        public void setCourseExamRecord(List<?> courseExamRecord) {
            this.courseExamRecord = courseExamRecord;
        }

        public List<?> getCourseSignIn() {
            return courseSignIn;
        }

        public void setCourseSignIn(List<?> courseSignIn) {
            this.courseSignIn = courseSignIn;
        }

        public List<?> getForceCourseExamRecord() {
            return forceCourseExamRecord;
        }

        public void setForceCourseExamRecord(List<?> forceCourseExamRecord) {
            this.forceCourseExamRecord = forceCourseExamRecord;
        }

        public List<?> getJobRecord() {
            return jobRecord;
        }

        public void setJobRecord(List<?> jobRecord) {
            this.jobRecord = jobRecord;
        }

        public List<?> getNotice() {
            return notice;
        }

        public void setNotice(List<?> notice) {
            this.notice = notice;
        }

        public List<?> getOperationCertificate() {
            return operationCertificate;
        }

        public void setOperationCertificate(List<?> operationCertificate) {
            this.operationCertificate = operationCertificate;
        }

        public List<?> getWorkAreaBoss() {
            return workAreaBoss;
        }

        public void setWorkAreaBoss(List<?> workAreaBoss) {
            this.workAreaBoss = workAreaBoss;
        }
    }
}
