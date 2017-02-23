package com.example.wzes.util;

import org.litepal.crud.DataSupport;

/**
 * Created by WZES on 2016/11/27.
 */

public class User extends DataSupport{
    private String masterPassword = "123456";
    private String userName;
    private String password;
    private int type;
    private String men;
    private String birth;
    private String name;
    private String email;

    public User(String userName, String password, int type,
                String men, String birth,
                String name, String email) {
        this.userName = userName;
        this.password = password;
        this.type = type;
        this.men = men;
        this.birth = birth;
        this.name = name;
        this.email = AESHelper.encrypt(email, masterPassword);
    }

    public int getType() {

        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getMen() {
        return men;
    }

    public void setMen(String men) {
        this.men = men;
    }

    public String getEmail() {
        return AESHelper.decrypt(email, masterPassword);
    }

    public void setEmail(String email) {
        this.email = AESHelper.encrypt(email, masterPassword);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User(String user, String password) {
        this.userName = user;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
