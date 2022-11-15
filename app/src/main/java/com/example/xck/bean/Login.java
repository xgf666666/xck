package com.example.xck.bean;

/**
 * author ： xiaogf
 * time    ： 2022/11/2
 * describe    ：
 */
public class Login {
    /**
     * user_info : {"id":24,"mobile_phone":"13066230849","proof_status":0,"user_type_select":0,"quota_num":2,"complete_status":0}
     * access_token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJtZW1iZXJfaWQiOjI0LCJhcHBpZCI6IiIsImFwcHNlY3JldCI6IiIsImlzcyI6Inh3YmQiLCJhdWQiOiJ4d2JkIiwic2NvcGVzIjoicm9sZV9hY2Nlc3MiLCJleHAiOjE2Njk5NDk1NjB9.WfXA_Ewt3VW3WAPaLTxuhftDooSXqBhShy_8MJUAbh8
     */

    private UserInfoBean user_info;
    private String access_token;

    public UserInfoBean getUser_info() {
        return user_info;
    }

    public void setUser_info(UserInfoBean user_info) {
        this.user_info = user_info;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public static class UserInfoBean {
        /** // 手机号
         "mobile_phone": "15920196136",
         // 头像
         "avatar": "",
         // 昵称
         "nickname": "",
         // 认证状态=[0:未认证,1:认证中,2:认证成功,3:认证失败]
         "proof_status": 1,
         // 真实姓名
         "real_name": "真实姓名",
         // 微信号
         "wechat": "1341234",
         // 用户类型=[0:无,1:创业者,2:投资人]
         "user_type_select": 1,
         // 聊天额度
         "quota_num": 0,
         // 完善角色信息状态 0:否 1:是
         "complete_status": 0,
         // im 用户id
         "im_user_id": "bd6c2799-91e8-5acc-9c8a-c14d90d7c798"
         */

        private int id;
        private String mobile_phone;
        private int proof_status;
        private int user_type_select;
        private int quota_num;
        private int complete_status;
        private String avatar; // 头像
        private String nickname; // 昵称
        private String real_name; // 真实姓名
        private String wechat; // 微信号
        private int im_user_id; // im 用户id

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getReal_name() {
            return real_name;
        }

        public void setReal_name(String real_name) {
            this.real_name = real_name;
        }

        public String getWechat() {
            return wechat;
        }

        public void setWechat(String wechat) {
            this.wechat = wechat;
        }

        public int getIm_user_id() {
            return im_user_id;
        }

        public void setIm_user_id(int im_user_id) {
            this.im_user_id = im_user_id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMobile_phone() {
            return mobile_phone;
        }

        public void setMobile_phone(String mobile_phone) {
            this.mobile_phone = mobile_phone;
        }

        public int getProof_status() {
            return proof_status;
        }

        public void setProof_status(int proof_status) {
            this.proof_status = proof_status;
        }

        public int getUser_type_select() {
            return user_type_select;
        }

        public void setUser_type_select(int user_type_select) {
            this.user_type_select = user_type_select;
        }

        public int getQuota_num() {
            return quota_num;
        }

        public void setQuota_num(int quota_num) {
            this.quota_num = quota_num;
        }

        public int getComplete_status() {
            return complete_status;
        }

        public void setComplete_status(int complete_status) {
            this.complete_status = complete_status;
        }
    }
}
