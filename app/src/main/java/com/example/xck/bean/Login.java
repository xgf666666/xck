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
        /**
         * id : 24
         * mobile_phone : 13066230849
         * proof_status : 0
         * user_type_select : 0
         * quota_num : 2
         * complete_status : 0
         */

        private int id;
        private String mobile_phone;
        private int proof_status;
        private int user_type_select;
        private int quota_num;
        private int complete_status;

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
