package com.example.xck.bean;

import java.util.List;

/**
 * author ： xiaogf
 * time    ： 2022/11/3
 * describe    ：
 */
public class Capitalist {

    /**
     * id : 3
     * capitalist_name : 机构名称
     * contact_name : 1234
     * position : 2022-10
     * single_amount : 12431234
     * avatar : 1234123412342
     * introduction : 1324214352345
     * cases : 123412l;3n4asdfasd
     * business_card_img : 1234asdfgasfasd
     * weight : 0
     * status : 1
     * create_time : 2022-10-19 19:44:06
     * update_time : 2022-10-19 19:44:06
     * user_id : 3
     * attr_list : [{"capitalist_id":3,"attr_id":4,"attr_parent_id":1,"attr_name":"金融"},{"capitalist_id":3,"attr_id":5,"attr_parent_id":1,"attr_name":"本地生活"},{"capitalist_id":3,"attr_id":9,"attr_parent_id":3,"attr_name":"未知轮次"},{"capitalist_id":3,"attr_id":7,"attr_parent_id":2,"attr_name":"广州"}]
     */

    private int id;
    private String capitalist_name;
    private String contact_name;
    private String position;
    private String single_amount;
    private String avatar;
    private String introduction;
    private String cases;
    private String business_card_img;
    private int weight;
    private int status;
    private String create_time;
    private String update_time;
    private int user_id;
    private List<AttrListBean> attr_list;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCapitalist_name() {
        return capitalist_name;
    }

    public void setCapitalist_name(String capitalist_name) {
        this.capitalist_name = capitalist_name;
    }

    public String getContact_name() {
        return contact_name;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getSingle_amount() {
        return single_amount;
    }

    public void setSingle_amount(String single_amount) {
        this.single_amount = single_amount;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getCases() {
        return cases;
    }

    public void setCases(String cases) {
        this.cases = cases;
    }

    public String getBusiness_card_img() {
        return business_card_img;
    }

    public void setBusiness_card_img(String business_card_img) {
        this.business_card_img = business_card_img;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public List<AttrListBean> getAttr_list() {
        return attr_list;
    }

    public void setAttr_list(List<AttrListBean> attr_list) {
        this.attr_list = attr_list;
    }

    public static class AttrListBean {
        /**
         * capitalist_id : 3
         * attr_id : 4
         * attr_parent_id : 1
         * attr_name : 金融
         */

        private int capitalist_id;
        private int attr_id;
        private int attr_parent_id;
        private String attr_name;

        public int getCapitalist_id() {
            return capitalist_id;
        }

        public void setCapitalist_id(int capitalist_id) {
            this.capitalist_id = capitalist_id;
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
