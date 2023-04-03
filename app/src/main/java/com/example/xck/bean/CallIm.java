package com.example.xck.bean;

import java.util.List;

/**
 * author ： xiaogf
 * time    ： 2023/4/3
 * describe    ：
 */
public class CallIm {

    private List<ActiveBean> active;
    private List<ActiveBean> receive;

    public List<ActiveBean> getActive() {
        return active;
    }

    public void setActive(List<ActiveBean> active) {
        this.active = active;
    }

    public List<ActiveBean> getReceive() {
        return receive;
    }

    public void setReceive(List<ActiveBean> receive) {
        this.receive = receive;
    }

    public static class ActiveBean {
        /**
         * id : 7
         * user_id : 36
         * from_user_id : 24
         * create_time : 2023-04-01 14:27:40
         * update_time : 2023-04-01 14:27:40
         * msg_type : 1
         */

        private int id;
        private int user_id;
        private int from_user_id;
        private String create_time;
        private String update_time;
        private int msg_type;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getFrom_user_id() {
            return from_user_id;
        }

        public void setFrom_user_id(int from_user_id) {
            this.from_user_id = from_user_id;
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

        public int getMsg_type() {
            return msg_type;
        }

        public void setMsg_type(int msg_type) {
            this.msg_type = msg_type;
        }
    }
}
