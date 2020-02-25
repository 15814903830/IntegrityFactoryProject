package com.xsylsb.integrity.base;

import java.util.List;

public class ProjectBase {

    /**
     * id : 23
     * name : 敢想专用一级单位
     * children : [{"value":"25","name":"彭丁","idno":"340102199305011502"},{"value":"648","name":"信息部一号员工","idno":"gx1648"},{"value":"835","name":"宋先生2号","idno":"gx1835"},{"value":"1232","name":"敢想单位 1.0人员","idno":"gx1001"}]
     */

    private String id;
    private String name;
    private List<ChildrenBean> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ChildrenBean> getChildren() {
        return children;
    }

    public void setChildren(List<ChildrenBean> children) {
        this.children = children;
    }

    public static class ChildrenBean {
        /**
         * value : 25
         * name : 彭丁
         * idno : 340102199305011502
         */

        private String value;
        private String name;
        private String idno;

        public String getIsselise() {
            return isselise;
        }

        public void setIsselise(String isselise) {
            this.isselise = isselise;
        }

        private String isselise;

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
}
