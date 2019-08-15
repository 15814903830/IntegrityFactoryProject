package com.xsylsb.integrity.base;

import java.util.List;

/**
 * @author glsite.com
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class LoginBase {

    /**
     * data : {"age":"39","dengJiTag":null,"workShop":null,"boss":null,"id":7,"role":0,"bossId":1,"userName":null,"idno":"11010519800101137","idstarTime":null,"idendTime":null,"password":"91BFD3AD08D66D6E","fullName":"孙七","gender":1,"birthday":"1980-01-01T00:00:00","companyId":null,"workShopId":null,"workAreaId":null,"jobId":null,"dengJi":null,"safetyExamination":false,"isCompanyHead":false,"isSpecialOperator":false,"isSafetyManager":false,"totalBadRecordScore":0,"totalViolationRecord":0,"totalCompanyHour":0,"status":0,"leaveTime":null,"createTime":"2019-07-05T22:57:59.91","blacklist":null,"badRecordItem":[],"courseExamRecord":[],"courseSignIn":[],"jobRecord":[],"notice":[],"operationCertificate":[],"violationRecordWhistleblowerNavigation":[],"violationRecordWorker":[]}
     * suc : true
     * msg : 登录成功
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
         * age : 39
         * dengJiTag : null
         * workShop : null
         * boss : null
         * id : 7
         * role : 0
         * bossId : 1
         * userName : null
         * idno : 11010519800101137
         * idstarTime : null
         * idendTime : null
         * password : 91BFD3AD08D66D6E
         * fullName : 孙七
         * gender : 1
         * birthday : 1980-01-01T00:00:00
         * companyId : null
         * workShopId : null
         * workAreaId : null
         * jobId : null
         * dengJi : null
         * safetyExamination : false
         * isCompanyHead : false
         * isSpecialOperator : false
         * isSafetyManager : false
         * totalBadRecordScore : 0
         * totalViolationRecord : 0
         * totalCompanyHour : 0
         * status : 0
         * leaveTime : null
         * createTime : 2019-07-05T22:57:59.91
         * blacklist : null
         * badRecordItem : []
         * courseExamRecord : []
         * courseSignIn : []
         * jobRecord : []
         * notice : []
         * operationCertificate : []
         * violationRecordWhistleblowerNavigation : []
         * violationRecordWorker : []
         */

        private String age;
        private Object dengJiTag;
        private Object workShop;
        private Object boss;
        private int id;
        private int role;
        private int bossId;
        private Object userName;
        private String idno;
        private Object idstarTime;
        private Object idendTime;
        private String password;
        private String fullName;
        private int gender;
        private String birthday;
        private Object companyId;
        private Object workShopId;
        private Object workAreaId;
        private Object jobId;
        private Object dengJi;
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
        private List<?> badRecordItem;
        private List<?> courseExamRecord;
        private List<?> courseSignIn;
        private List<?> jobRecord;
        private List<?> notice;
        private List<?> operationCertificate;
        private List<?> violationRecordWhistleblowerNavigation;
        private List<?> violationRecordWorker;

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public Object getDengJiTag() {
            return dengJiTag;
        }

        public void setDengJiTag(Object dengJiTag) {
            this.dengJiTag = dengJiTag;
        }

        public Object getWorkShop() {
            return workShop;
        }

        public void setWorkShop(Object workShop) {
            this.workShop = workShop;
        }

        public Object getBoss() {
            return boss;
        }

        public void setBoss(Object boss) {
            this.boss = boss;
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

        public Object getWorkShopId() {
            return workShopId;
        }

        public void setWorkShopId(Object workShopId) {
            this.workShopId = workShopId;
        }

        public Object getWorkAreaId() {
            return workAreaId;
        }

        public void setWorkAreaId(Object workAreaId) {
            this.workAreaId = workAreaId;
        }

        public Object getJobId() {
            return jobId;
        }

        public void setJobId(Object jobId) {
            this.jobId = jobId;
        }

        public Object getDengJi() {
            return dengJi;
        }

        public void setDengJi(Object dengJi) {
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

        public List<?> getBadRecordItem() {
            return badRecordItem;
        }

        public void setBadRecordItem(List<?> badRecordItem) {
            this.badRecordItem = badRecordItem;
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

        public List<?> getViolationRecordWhistleblowerNavigation() {
            return violationRecordWhistleblowerNavigation;
        }

        public void setViolationRecordWhistleblowerNavigation(List<?> violationRecordWhistleblowerNavigation) {
            this.violationRecordWhistleblowerNavigation = violationRecordWhistleblowerNavigation;
        }

        public List<?> getViolationRecordWorker() {
            return violationRecordWorker;
        }

        public void setViolationRecordWorker(List<?> violationRecordWorker) {
            this.violationRecordWorker = violationRecordWorker;
        }
    }
}
