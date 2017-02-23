package com.example.wzes.secretmanagement;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wzes.util.NetUser;
import com.example.wzes.util.User;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import static com.example.wzes.util.MD5.getMD5;

/**
 * Created by WZES on 2016/11/25.
 */

public class LoginActivity extends AppCompatActivity {

    private Button loginBtn=null;
    private Button registerBtn = null;
    private EditText userNameTxt, passwordTxt;
    private String userName, password;
    private String lastUsername;
    private String lastPassword;
    private SharedPreferences sharedPreferencesN;
    private SharedPreferences sharedPreferencesP;
    private CheckBox loginRemember;
    private boolean find = false;
    private String encryptingCode;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //第一：默认初始化
        Bmob.initialize(this, "aab269fbafe12d447bedf19f054ba46a");


        loginBtn = (Button) findViewById(R.id.login);
        Connector.getDatabase();
        init();
    }

    private void init() {
        loginRemember = (CheckBox) findViewById(R.id.loginRemember);

        loginBtn = (Button) findViewById(R.id.login);
        registerBtn = (Button) findViewById(R.id.register);
        userNameTxt = (EditText) findViewById(R.id.userName);
        passwordTxt = (EditText) findViewById(R.id.passWord);

        //获取上次登录的用户名
        sharedPreferencesN = this.getSharedPreferences(
                "lastUsername", MODE_PRIVATE);
        lastUsername = sharedPreferencesN.getString("lastUsername", "");
        userNameTxt.setText(lastUsername);

        if(lastUsername.equals(userNameTxt.getText().toString())){
            //获取上次登录的密码
            sharedPreferencesP = this.getSharedPreferences(
                        "lastPassword", MODE_PRIVATE);
            lastPassword = sharedPreferencesP.getString("lastPassword", "");
            if(!lastPassword.equals("NULL")){
                passwordTxt.setText(lastPassword);
            }
            passwordTxt.setText(lastPassword);
        }
        userNameTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!lastUsername.equals(userNameTxt.getText().toString())){
                    passwordTxt.setText("");
                }else{
                    passwordTxt.setText(lastPassword);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userName = userNameTxt.getText().toString();
                password = passwordTxt.getText().toString();

                if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)){
                    Toast.makeText(LoginActivity.this, "不能为空", Toast.LENGTH_SHORT).show();
                }else {
                    dialog = new ProgressDialog(LoginActivity.this);
                    dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    dialog.setCancelable(false);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.setTitle("Logging in...");
                    dialog.setMessage("Please wait...");
                    dialog.show();

                    //得到输入密码的MD5
                    try {
                        encryptingCode = getMD5(password);
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                    //在数据库里面查找输入的用户名
                    List<User> myData = DataSupport.where("userName = ?", userName).find(User.class);
                    if(myData.size() != 0){
                        if(encryptingCode.equals(myData.get(0).getPassword())){
                            //保存登录信息
                            SharedPreferences.Editor editorN = sharedPreferencesN.edit();
                            editorN.putString("lastUsername", userName);

                            Log.i("TAGGG", "onClick: "+ userName);
                            editorN.apply();
                            //记住密码
                            SharedPreferences.Editor editorP = sharedPreferencesP.edit();

                            if(loginRemember.isChecked()){
                                editorP.putString("lastPassword", password);
                            }else{
                                editorP.putString("lastPassword", "");
                            }
                            editorP.apply();

                            //进入main
                            Message msg = hand.obtainMessage();
                            hand.sendMessage(msg);
                        }
                        else{
                            dialog.dismiss();
                            Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                            passwordTxt.setFocusable(true);
                        }
                    }else{
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                //
                                findNet(userName);
                            }
                        }).start();
                    }
                }
            }
        });
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }
    public void findNet(final String Name){
        BmobQuery<NetUser> bmobQuery = new BmobQuery<NetUser>();
        bmobQuery.setLimit(1000);
        bmobQuery.findObjects(new FindListener<NetUser>() {
            @Override
            public void done(List<NetUser> list, BmobException e) {
                if (e == null) {
                    for(NetUser netUser : list){
                        if(netUser.getUserName().equals(Name)){
                            find = true;
                            if(encryptingCode.equals(netUser.getPassword())) {
                                //保存登录信息

                                SharedPreferences.Editor editorN = sharedPreferencesN.edit();
                                editorN.putString("lastUsername", userName);
                                Log.i("TAGGG", "onClick: " + userName);
                                editorN.apply();
                                //记住密码
                                SharedPreferences.Editor editorP = sharedPreferencesP.edit();

                                if (loginRemember.isChecked()) {
                                    editorP.putString("lastPassword", password);
                                } else {
                                    editorP.putString("lastPassword", "");
                                }
                                //加入到本地数据库中
                                //用MD5加密存储密码在数据库
                                try {
                                    encryptingCode = getMD5(password);
                                } catch (NoSuchAlgorithmException r) {
                                    r.printStackTrace();
                                }
                                User user = new User(userName, encryptingCode,netUser.getType(),
                                        netUser.getMen(),netUser.getBirth(),netUser.getName(),
                                        netUser.getEmail());
                                user.save();
                                editorP.apply();

                                //进入main
                                Message msg = hand.obtainMessage();
                                hand.sendMessage(msg);

                                break;
                            }
                            else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        dialog.dismiss();
                                        Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                                        passwordTxt.setFocusable(true);
                                    }
                                });

                            }
                        }
                    }
                    if(!find){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                                Toast.makeText(LoginActivity.this, "不存在该用户名", Toast.LENGTH_SHORT).show();
                                userNameTxt.setFocusable(true);
                            }
                        });

                    }
                }
                else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();
                            Toast.makeText(LoginActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                            userNameTxt.setFocusable(true);
                        }
                    });

                }
            }
        });
    }
    Handler hand = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            dialog.setMessage("Success......");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(500);
                        dialog.dismiss();
                        Intent intent = new Intent(LoginActivity.this,
                                MyMainActivity.class);
                        startActivity(intent);
                        finish();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    };
}
