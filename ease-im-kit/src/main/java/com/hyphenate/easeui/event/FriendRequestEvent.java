package com.hyphenate.easeui.event;

/**
 * author ： xiaogf
 * time    ： 2023/4/1
 * describe    ：
 */
public class FriendRequestEvent {
    private int id;
    public FriendRequestEvent(int id){
        this.id=id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
