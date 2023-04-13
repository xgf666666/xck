package com.hyphenate.easeui.constants;

/**
 * author ： xiaogf
 * time    ： 2023/4/9
 * describe    ：
 */
public class EaseCommom {
    private static EaseCommom instance;
    private boolean isProject;
    private UserMessage userMessage;

    public UserMessage getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(UserMessage userMessage) {
        this.userMessage = userMessage;
    }

    public boolean isProject() {
        return isProject;
    }

    public void setProject(boolean project) {
        isProject = project;
    }

    private  EaseCommom(){
    }
    public synchronized static EaseCommom getInstance(){
        if (instance==null){
            instance=new EaseCommom();
        }
        return instance;
    }
}
