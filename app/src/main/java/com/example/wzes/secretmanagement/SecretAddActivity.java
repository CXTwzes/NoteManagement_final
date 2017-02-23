package com.example.wzes.secretmanagement;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.wzes.util.secretData;
/**
 * Created by WZES on 2016/12/9.
 */

public class SecretAddActivity extends Activity{
    private LinearLayout phoneLayout, questionLayout, netLayout;


    private Button backBtn;
    private Button keepBtn;
    private Spinner spinner;  //type
    private EditText titleTxt, usernameTxt, passwordTxt, netTxt, emailTxt,
            phoneTxt, paypasswordTxt,
            questiononeTxt, answeroneTxt,
            questiontwoTxt, answertwoTxt,
            questionthreeTxt, answerthreeTxt,
            memoTxt;
    private secretData myData;
    private int type;
    private String title;
    private String username;
    private String password;
    private String net;
    private String email;
    private String phone;
    private String paypassword;
    private String questionone;
    private String answerone;
    private String questiontwo;
    private String answertwo;
    private String questionthree;
    private String answerthree;
    private String memo;
    private String Username;

    private int[] drawables = {R.drawable.usual, R.drawable.qq, R.drawable.wx , R.drawable.wb ,R.drawable.zfb,
            R.drawable.tc, R.drawable.ws , R.drawable.bdnm ,R.drawable.mm,
            R.drawable.bdnm, R.drawable.wph , R.drawable.aqy ,R.drawable.xc,
            R.drawable.qqyy, R.drawable.mp , R.drawable.aqy ,R.drawable.tb,
            R.drawable.kg, R.drawable.td ,R.drawable.qqyx };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secret_add);
        ActivityColletor.addActivity(SecretAddActivity.this);
        //获取上次登录的用户名
        SharedPreferences sharedPreferences = this.getSharedPreferences(
                "lastUsername", MODE_PRIVATE);
        Username = sharedPreferences.getString("lastUsername", "");

        init();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "添加密码问题", Snackbar.LENGTH_LONG)
                        .setAction("点击添加", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                questionLayout.setVisibility(View.VISIBLE);
                            }
                        })
                        .show();
            }
        });

    }

    private void init() {
        questionLayout = (LinearLayout) findViewById(R.id.secretAddQuestionLayout);

        backBtn = (Button) findViewById(R.id.secretAddBackBtn);
        keepBtn = (Button) findViewById(R.id.secretAddKeepBtn);

        titleTxt = (EditText) findViewById(R.id.secretAddTitleTxt);
        usernameTxt = (EditText) findViewById(R.id.secretAddUsername);
        passwordTxt = (EditText) findViewById(R.id.secretAddPassword);
        netTxt = (EditText) findViewById(R.id.secretAddNet);
        emailTxt = (EditText) findViewById(R.id.secretAddEmail);

        phoneTxt = (EditText) findViewById(R.id.secretAddPhone);
        paypasswordTxt = (EditText) findViewById(R.id.secretAddPayPassword);

        questiononeTxt = (EditText) findViewById(R.id.secretAddQuestionOne);
        answeroneTxt = (EditText) findViewById(R.id.secretAddAnswerOne);
        questiontwoTxt = (EditText) findViewById(R.id.secretAddQuestionTwo);
        answertwoTxt = (EditText) findViewById(R.id.secretAddAnswerTwo);
        questionthreeTxt = (EditText) findViewById(R.id.secretAddQuestionThree);
        answerthreeTxt = (EditText) findViewById(R.id.secretAddAnswerThree);

        memoTxt = (EditText) findViewById(R.id.secretAddMemo);
        spinner = (Spinner) findViewById(R.id.secretSpinner);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int positon, long id) {
                type = positon;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        keepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = titleTxt.getText().toString();
                username = usernameTxt.getText().toString();
                password = passwordTxt.getText().toString();
                net = netTxt.getText().toString();
                email = emailTxt.getText().toString();
                phone = phoneTxt.getText().toString();
                paypassword = paypasswordTxt.getText().toString();
                questionone = questiononeTxt.getText().toString();
                answerone = answeroneTxt.getText().toString();
                questiontwo = questiontwoTxt.getText().toString();
                answertwo = answertwoTxt.getText().toString();
                questionthree = questionthreeTxt.getText().toString();
                answerthree = answerthreeTxt.getText().toString();

                memo = memoTxt.getText().toString();

                if(title.equals("") || titleTxt.equals("") || username.equals("")
                        || password.equals("")){
                    myToast("标题或用户名和密码不能为空");
                }else{
                    myData = new secretData(Username, String.valueOf(type), title, username, password);
                    if(!net.equals("")){
                        myData.setNet(net);
                    }
                    if(!memo.equals("")){
                        myData.setMemo(memo);
                    }
                    if(!email.equals("")){
                        myData.setEmail(email);
                    }
                    if(!email.equals("")){
                        myData.setEmail(email);
                    }
                    if(!phone.equals("")){
                        myData.setPhone(phone);
                    }
                    if(!paypassword.equals("")){
                        myData.setPayPassword(paypassword);
                    }
                    if(!questionone.equals("")){
                        myData.setQuestion0ne(questionone);
                    }
                    if(!questiontwo.equals("")){
                        myData.setQuestionTwo(questiontwo);
                    }
                    if(!questionthree.equals("")){
                        myData.setQuestionThree(questionthree);
                    }
                    if(!answerone.equals("")){
                        myData.setAnswerOne(answerone);
                    }
                    if(!answertwo.equals("")){
                        myData.setAnswerTwo(answertwo);
                    }
                    if(!answerthree.equals("")){
                        myData.setAnswerThree(answerthree);
                    }
                    if (!memo.equals("")){
                        myData.setMemo(memo);
                    }
                    myData.setUser(Username);
                    myData.save();
                    startActivity(new Intent(SecretAddActivity.this, MyMainActivity.class));
                    finish();

                }

            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(SecretAddActivity.this, MyMainActivity.class));
                finish();
            }
        });

    }
    private void myToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    private BaseAdapter adapter = new BaseAdapter() {
        public int getCount() {
            return drawables.length;
        }


        public Object getItem(int position) {
            return drawables[position];
        }


        public long getItemId(int position) {
            return position;
        }


        public View getView(int position, View convertView, ViewGroup parent) {
            LinearLayout ll = new LinearLayout(SecretAddActivity.this);
            ll.setOrientation(LinearLayout.HORIZONTAL);
            ll.setLayoutParams( new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));

            ImageView iv = new ImageView(SecretAddActivity.this);
            iv.setImageResource(drawables[position]);
            ll.addView(iv);

            return ll;
        }
    };

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                startActivity(new Intent(SecretAddActivity.this, AddActivity.class));
                finish();
            }
        }
        return false;
    }
}
