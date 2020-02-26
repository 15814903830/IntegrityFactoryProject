package com.xsylsb.integrity.Examination_adapter;

import java.util.List;

/**
 * @author glsite.com
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class ExamianBase {

    /**
     * suc : true
     * totalRecord : 7
     * course : {"id":2,"timeLength":50,"title":"柳钢-安全培训"}
     * now : 2019-07-13 14:35:42.861
     * data : [{"options":null,"id":1,"classificationIds":"10","type":0,"title":"出入地下室的时候，存在的安全隐患有哪些？","option":"{\"A\":\"地面油多，容易滑倒\",\"B\":\"试喷导致地下室里的人窒息身亡\"}","answer":"1","describe":null,"status":0,"createTime":"2019-07-08T11:23:16.99"},{"options":null,"id":2,"classificationIds":"10","type":0,"title":"出入地下室的时候，存在的安全隐患有哪些？","option":"{\"A\":\"地面油多，容易滑倒\",\"B\":\"试喷导致地下室里的人窒息身亡\"}","answer":"0","describe":null,"status":0,"createTime":"2019-07-08T11:23:16.99"},{"options":null,"id":3,"classificationIds":"10","type":0,"title":"出入地下室的时候，存在的安全隐患有哪些？","option":"{\"A\":\"地面油多，容易滑倒\",\"B\":\"试喷导致地下室里的人窒息身亡\"}","answer":"1","describe":null,"status":0,"createTime":"2019-07-08T11:23:16.99"},{"options":null,"id":4,"classificationIds":"10","type":0,"title":"出入地下室的时候，存在的安全隐患有哪些？","option":"{\"A\":\"地面油多，容易滑倒\",\"B\":\"试喷导致地下室里的人窒息身亡\"}","answer":"0","describe":null,"status":0,"createTime":"2019-07-08T11:23:16.99"}]
     */

    private boolean suc;
    private int totalRecord;
    private CourseBean course;
    private String now;
    private List<DataBean> data;

    public boolean isSuc() {
        return suc;
    }

    public void setSuc(boolean suc) {
        this.suc = suc;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    public CourseBean getCourse() {
        return course;
    }

    public void setCourse(CourseBean course) {
        this.course = course;
    }

    public String getNow() {
        return now;
    }

    public void setNow(String now) {
        this.now = now;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class CourseBean {
        /**
         * id : 2
         * timeLength : 50
         * title : 柳钢-安全培训
         */

        private int id;
        private int timeLength;
        private String title;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getTimeLength() {
            return timeLength;
        }

        public void setTimeLength(int timeLength) {
            this.timeLength = timeLength;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class DataBean {
        /**
         * options : null
         * id : 1
         * classificationIds : 10
         * type : 0
         * title : 出入地下室的时候，存在的安全隐患有哪些？
         * option : {"A":"地面油多，容易滑倒","B":"试喷导致地下室里的人窒息身亡"}
         * answer : 1
         * describe : null
         * status : 0
         * createTime : 2019-07-08T11:23:16.99
         */

        private Object options;
        private int id;
        private String classificationIds;
        private int type;
        private String title;
        private String option;
        private String answer;
        private Object describe;
        private int status;
        private String createTime;

        public Object getOptions() {
            return options;
        }

        public void setOptions(Object options) {
            this.options = options;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getClassificationIds() {
            return classificationIds;
        }

        public void setClassificationIds(String classificationIds) {
            this.classificationIds = classificationIds;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getOption() {
            return option;
        }

        public void setOption(String option) {
            this.option = option;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public Object getDescribe() {
            return describe;
        }

        public void setDescribe(Object describe) {
            this.describe = describe;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}
