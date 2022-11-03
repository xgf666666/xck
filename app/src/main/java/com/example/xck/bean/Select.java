package com.example.xck.bean;

import java.util.List;

/**
 * author ： xiaogf
 * time    ： 2022/10/31
 * describe    ：
 */
public class Select {

    /**
     * id : 1
     * parent_id : 0
     * attr_name : 领域
     * children : [{"id":4,"parent_id":1,"attr_name":"金融"},{"id":5,"parent_id":1,"attr_name":"本地生活"},{"id":6,"parent_id":1,"attr_name":"教育"}]
     */

    private int id;
    private int parent_id;
    private String attr_name;
    private List<ChildrenBean> children;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public String getAttr_name() {
        return attr_name;
    }

    public void setAttr_name(String attr_name) {
        this.attr_name = attr_name;
    }

    public List<ChildrenBean> getChildren() {
        return children;
    }

    public void setChildren(List<ChildrenBean> children) {
        this.children = children;
    }

    public static class ChildrenBean {
        /**
         * id : 4
         * parent_id : 1
         * attr_name : 金融
         */

        private int id;
        private int parent_id;
        private String attr_name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getParent_id() {
            return parent_id;
        }

        public void setParent_id(int parent_id) {
            this.parent_id = parent_id;
        }

        public String getAttr_name() {
            return attr_name;
        }

        public void setAttr_name(String attr_name) {
            this.attr_name = attr_name;
        }
    }
}
