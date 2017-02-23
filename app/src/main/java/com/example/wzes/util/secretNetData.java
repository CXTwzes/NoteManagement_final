package com.example.wzes.util;



import cn.bmob.v3.BmobObject;

/**
 * Created by WZES on 2016/12/9.
 */

public class secretNetData extends BmobObject{
    private String masterPassword = "123456";
    //private String originalText;
    //private String encryptingCode;
    //private String decryptingCode;
    //encryptingCode = AESHelper.encrypt(password, masterPassword);
    //decryptingCode = AESHelper.decrypt(encryptingCode,masterPassword);
    public secretNetData(){

    }
    public secretNetData(String title,
                      String type, String user, String username,
                      String password, String email, String net,
                      String phone, String payPassword,
                      String question0ne, String answerOne,
                      String questionTwo, String answerTwo,
                      String questionThree, String answerThree,
                      String memo) {
        this.title = title;
        this.type = type;
        this.user = user;
        this.username = username;
        this.password = AESHelper.encrypt(password, masterPassword);
        this.email = email;
        this.net = net;
        this.phone = AESHelper.encrypt(phone, masterPassword);
        this.payPassword = AESHelper.encrypt(payPassword, masterPassword);

        this.question0ne = AESHelper.encrypt(question0ne, masterPassword);
        this.answerOne = AESHelper.encrypt(answerOne, masterPassword);
        this.questionTwo = AESHelper.encrypt(questionTwo, masterPassword);
        this.answerTwo = AESHelper.encrypt(answerTwo, masterPassword);
        this.questionThree = AESHelper.encrypt(questionThree, masterPassword);
        this.answerThree = AESHelper.encrypt(answerThree, masterPassword);
        this.memo = memo;
    }
    /*public secretNetData(secretData sD){
        this.user = sD.getUser();
        this.type = sD.getType();
        this.title = sD.getTitle();
        this.username = sD.getUsername();
        this.password = sD.getPassword();
        email = sD.getEmail();
        net = sD.getNet();

        phone = sD.getPhone();
        payPassword = sD.getPayPassword();

        question0ne = sD.getQuestion0ne();
        answerOne = sD.getAnswerOne();
        questionTwo = sD.getQuestionTwo();
        answerTwo = sD.getAnswerTwo();
        questionThree = sD.getQuestionThree();
        answerThree = sD.getAnswerThree();

        memo = sD.getMemo();
    }*/
    public secretNetData(String user, String type, String title, String username, String password){
        this.user = user;
        this.type = type;
        this.title = title;
        this.username = username;
        this.password = AESHelper.encrypt(password, masterPassword);
        email = "NULL";
        net = "NULL";

        phone = AESHelper.encrypt("NULL", masterPassword);
        payPassword = AESHelper.encrypt("NULL", masterPassword);

        question0ne = AESHelper.encrypt("NULL", masterPassword);
        answerOne = AESHelper.encrypt("NULL", masterPassword);
        questionTwo = AESHelper.encrypt("NULL", masterPassword);
        answerTwo = AESHelper.encrypt("NULL", masterPassword);
        questionThree = AESHelper.encrypt("NULL", masterPassword);
        answerThree = AESHelper.encrypt("NULL", masterPassword);

        memo = "NULL";
    }
    private String title;   //标题

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return AESHelper.decrypt(password,masterPassword);
    }

    public void setPassword(String password) {
        this.password = AESHelper.encrypt(password, masterPassword);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNet() {
        return net;
    }

    public void setNet(String net) {
        this.net = net;
    }

    public String getPhone() {
        return AESHelper.decrypt(phone,masterPassword);
    }

    public void setPhone(String phone) {
        this.phone = AESHelper.encrypt(phone, masterPassword);
    }

    public String getPayPassword() {
        return AESHelper.decrypt(payPassword,masterPassword);
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = AESHelper.encrypt(payPassword, masterPassword);
    }

    public String getQuestion0ne() {
        return AESHelper.decrypt(question0ne, masterPassword);
    }

    public void setQuestion0ne(String question0ne) {
        this.question0ne = AESHelper.encrypt(question0ne, masterPassword);
    }

    public String getAnswerOne() {
        return AESHelper.decrypt(answerOne,masterPassword);
    }

    public void setAnswerOne(String answerOne) {
        this.answerOne = AESHelper.encrypt(answerOne, masterPassword);
    }

    public String getQuestionTwo() {
        return AESHelper.decrypt(questionTwo,masterPassword);
    }

    public void setQuestionTwo(String questionTwo) {
        this.questionTwo = AESHelper.encrypt(questionTwo, masterPassword);
    }

    public String getAnswerTwo() {
        return AESHelper.decrypt(answerTwo,masterPassword);
    }

    public void setAnswerTwo(String answerTwo) {
        this.answerTwo = AESHelper.encrypt(answerTwo, masterPassword);
    }

    public String getQuestionThree() {
        return AESHelper.decrypt(questionThree, masterPassword);
    }

    public void setQuestionThree(String questionThree) {
        this.questionThree = AESHelper.encrypt(questionThree, masterPassword);
    }

    public String getAnswerThree() {
        return AESHelper.decrypt(answerThree, masterPassword);
    }

    public void setAnswerThree(String answerThree) {
        this.answerThree = AESHelper.encrypt(answerThree, masterPassword);
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    private String type;    //图片
    private String user;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    private String username;
    private String password;
    private String email;
    private String net;

    private String phone;
    private String payPassword;

    private String question0ne;
    private String answerOne;
    private String questionTwo;
    private String answerTwo;
    private String questionThree;
    private String answerThree;

    private String memo;
}
