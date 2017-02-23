package com.example.wzes.secretmanagement;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wzes.util.AESHelper;

import com.example.wzes.util.NetUser;
import com.example.wzes.util.User;

import org.litepal.crud.DataSupport;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import static com.example.wzes.util.MD5.getMD5;

/**
 * Created by WZES on 2016/11/25.
 */

public class RegisterActivity extends Activity {
    private Button backBtn, registerBtn;
    private EditText userNameTxt, passwordTxt, passwordAgainTxt;
    private String userName, password, passwordAgain;
    private ProgressDialog dialog;
    private String encryptingCode;  //加密后的密码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userName = userNameTxt.getText().toString();
                password = passwordTxt.getText().toString();
                passwordAgain = passwordAgainTxt.getText().toString();


                //检查输入是否正确
                if(isInputTrue()){
                    dialog = new ProgressDialog(RegisterActivity.this);
                    dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    dialog.setCancelable(false);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.setTitle("Register in...");
                    dialog.setMessage("Please wait...");
                    dialog.show();
                    //用MD5加密存储密码在数据库
                    try {
                        encryptingCode = getMD5(password);
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                    NetUser netUser = new NetUser(userName, encryptingCode, 0 ,"Null","Null","Null","Null");
                    User user = new User(userName, encryptingCode, 0 , "Null","Null","Null","Null");
                    user.save();
                    netUser.save(new SaveListener<String>() {
                        @Override
                        public void done(String objectId,BmobException e) {
                            if(e==null){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        dialog.setMessage("Success......");
                                        try {
                                            Thread.sleep(500);
                                        } catch (InterruptedException e1) {
                                            e1.printStackTrace();
                                        }
                                        dialog.dismiss();
                                    }
                                });
                                finish();
                            }else{
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        dialog.setMessage("Failed......");
                                        dialog.dismiss();
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private boolean isInputTrue() {
        if(TextUtils.isEmpty(userName)){
            Toast.makeText(RegisterActivity.this, "请输入用户名", Toast.LENGTH_LONG).show();
            return false;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!password.equals(passwordAgain)){
            Toast.makeText(this, "两次输入密码不一样，请重新输入", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            List<User> myData = DataSupport.where("userName = ?", userName).find(User.class);
            if (myData.size() != 0){
                Toast.makeText(this, "该用户名已经存在！请重新输入", Toast.LENGTH_SHORT).show();
                //用户名重新输入
                userNameTxt.setFocusable(true);
                return false;
            }
        }
        if(password.length()<6){
            Toast.makeText(this, "密码位数不能低于6位", Toast.LENGTH_SHORT).show();
            passwordTxt.setFocusable(true);
            return false;
        }
        return true;
    }

    private void init() {
        userNameTxt = (EditText) findViewById(R.id.RegisterUsername);
        passwordTxt = (EditText) findViewById(R.id.RegisterPassword);
        passwordAgainTxt = (EditText) findViewById(R.id.RegisterPasswordSure);
        backBtn = (Button) findViewById(R.id.RegisterBack);
        registerBtn = (Button) findViewById(R.id.RegisterRegister);
    }

}
