package com.example.xck.bean;

import java.util.List;

/**
 * author ： xiaogf
 * time    ： 2022/11/3
 * describe    ：
 */
public class Project {

    /**
     * id : 4
     * project_name : 项目名称
     * logo_image : 1234
     * found_time : 2022-10-01 00:00:00
     * introduction : 12431234
     * wait_finance : 1234123412342
     * operation : 1324214352345
     * advantage : 123412l;3n4asdfasd
     * history_financice : 1234asdfgasfasd
     * project_file : 1234lhljlkjh134
     * team_member : luhaflajsdflajhsdf
     * create_time : 2022-10-09 23:48:49
     * update_time : 2022-10-09 23:48:49
     * user_id : 1
     * weight : 0
     * delete_time : 0
     * attr_list : [{"project_id":4,"attr_id":4,"attr_parent_id":1,"attr_name":"金融"},{"project_id":4,"attr_id":5,"attr_parent_id":1,"attr_name":"本地生活"},{"project_id":4,"attr_id":9,"attr_parent_id":3,"attr_name":"未知轮次"},{"project_id":4,"attr_id":7,"attr_parent_id":2,"attr_name":"广州"}]
     */

    private int id;
    private String project_name;
    private String logo_image;
    private String found_time;
    private String introduction;
    private String wait_finance;
    private String operation;
    private String advantage;
    private String history_financice;
    private String project_file;
    private String team_member;
    private String create_time;
    private String update_time;
    private int user_id;
    private int weight;
    private int delete_time;
    private List<AttrListBean> attr_list;
    private Login.UserInfoBean user_info;

    public Login.UserInfoBean getUser_info() {
        return user_info;
    }

    public void setUser_info(Login.UserInfoBean user_info) {
        this.user_info = user_info;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getLogo_image() {
        return logo_image;
    }

    public void setLogo_image(String logo_image) {
        this.logo_image = logo_image;
    }

    public String getFound_time() {
        return found_time;
    }

    public void setFound_time(String found_time) {
        this.found_time = found_time;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getWait_finance() {
        return wait_finance;
    }

    public void setWait_finance(String wait_finance) {
        this.wait_finance = wait_finance;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getAdvantage() {
        return advantage;
    }

    public void setAdvantage(String advantage) {
        this.advantage = advantage;
    }

    public String getHistory_financice() {
        return history_financice;
    }

    public void setHistory_financice(String history_financice) {
        this.history_financice = history_financice;
    }

    public String getProject_file() {
        return project_file;
    }

    public void setProject_file(String project_file) {
        this.project_file = project_file;
    }

    public String getTeam_member() {
        return team_member;
    }

    public void setTeam_member(String team_member) {
        this.team_member = team_member;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getDelete_time() {
        return delete_time;
    }

    public void setDelete_time(int delete_time) {
        this.delete_time = delete_time;
    }

    public List<AttrListBean> getAttr_list() {
        return attr_list;
    }

    public void setAttr_list(List<AttrListBean> attr_list) {
        this.attr_list = attr_list;
    }

    public static class AttrListBean {
        /**
         * project_id : 4
         * attr_id : 4
         * attr_parent_id : 1
         * attr_name : 金融
         */

        private int project_id;
        private int attr_id;
        private int attr_parent_id;
        private String attr_name;

        public int getProject_id() {
            return project_id;
        }

        public void setProject_id(int project_id) {
            this.project_id = project_id;
        }

        public int getAttr_id() {
            return attr_id;
        }

        public void setAttr_id(int attr_id) {
            this.attr_id = attr_id;
        }

        public int getAttr_parent_id() {
            return attr_parent_id;
        }

        public void setAttr_parent_id(int attr_parent_id) {
            this.attr_parent_id = attr_parent_id;
        }

        public String getAttr_name() {
            return attr_name;
        }

        public void setAttr_name(String attr_name) {
            this.attr_name = attr_name;
        }
    }
}
