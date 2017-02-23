package com.example.wzes.util;

import org.litepal.crud.DataSupport;

import cn.bmob.v3.BmobObject;

/**
 * Created by WZES on 2016/12/17.
 */

public class memoNetData extends BmobObject {
    private String user;

    private int priority;
    private String context;
    private String date;
    private String finish;
    public memoNetData(String user, int priority, String context, String date, String finish) {
        this.user = user;
        this.priority = priority;
        this.context = context;
        this.date = date;
        this.finish = finish;
    }
    public memoNetData(){

    }
    public void setPriority(int priority) {
        this.priority = priority;
    }

    /*public memoNetData(memoData md){
        this.user = md.getUser();
        this.priority = md.getPriority();
        this.context = md.getContext();
        this.date = md.getDate();
        finish = md.isFinish();
    }*/
    public memoNetData(String user, int priority, String date, String context) {
        this.user = user;
        this.priority = priority;
        this.context = context;
        this.date = date;
        finish = "false";
    }

    public String getUser() {

        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String isFinish() {
        return finish;
    }

    public void setFinish(String finish) {
        this.finish = finish;
    }

    public int getPriority() {

        return priority;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
