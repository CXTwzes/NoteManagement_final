package com.example.wzes.secretmanagement;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
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
import com.example.wzes.util.txtData;


import org.litepal.crud.DataSupport;

import java.util.List;


/**
 * Created by WZES on 2016/12/9.
 */

public class SecretSeeActivity extends Activity {
    private Button backBtn;
    private Button keepBtn;
    private Button deleteBtn;
    private Spinner spinner;  //type
    private EditText titleTxt, usernameTxt, passwordTxt, netTxt, emailTxt,
            phoneTxt, paypasswordTxt,
            questiononeTxt, answeroneTxt,
            questiontwoTxt, answertwoTxt,
            questionthreeTxt, answerthreeTxt,
            memoTxt;
    private List<secretData> myData;
    private secretData myDataChange;
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
    private LinearLayout secretSeeQuestionLayout, secretSeeNetLayout;
    private boolean keep = false;
    private String Title;

    private int[] drawables = {R.drawable.usual, R.drawable.qq, R.drawable.wx , R.drawable.wb ,R.drawable.zfb,
            R.drawable.tc, R.drawable.ws , R.drawable.bdnm ,R.drawable.mm,
            R.drawable.bdnm, R.drawable.wph , R.drawable.aqy ,R.drawable.xc,
            R.drawable.qqyy, R.drawable.mp , R.drawable.aqy ,R.drawable.tb,
            R.drawable.kg, R.drawable.td ,R.drawable.qqyx};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_secret);
        ActivityColletor.addActivity(SecretSeeActivity.this);
        Intent myIntent = getIntent();
        Title = myIntent.getStringExtra("Title");
        init();
        initEdit(false);


    }

    private void init() {

        secretSeeQuestionLayout = (LinearLayout) findViewById(R.id.secretSeeQuestionLayout);
        secretSeeNetLayout = (LinearLayout) findViewById(R.id.secretSeeNetLayout);

        deleteBtn = (Button) findViewById(R.id.secretSeeDeleteBtn);
        backBtn = (Button) findViewById(R.id.secretSeeBackBtn);
        keepBtn = (Button) findViewById(R.id.secretSeeKeepBtn);


        titleTxt = (EditText) findViewById(R.id.secretSeeTitleTxt);
        usernameTxt = (EditText) findViewById(R.id.secretSeeUsername);
        passwordTxt = (EditText) findViewById(R.id.secretSeePassword);
        netTxt = (EditText) findViewById(R.id.secretSeeNet);
        emailTxt = (EditText) findViewById(R.id.secretSeeEmail);

        phoneTxt = (EditText) findViewById(R.id.secretSeePhone);
        paypasswordTxt = (EditText) findViewById(R.id.secretSeePayPassword);

        questiononeTxt = (EditText) findViewById(R.id.secretSeeQuestionOne);
        answeroneTxt = (EditText) findViewById(R.id.secretSeeAnswerOne);
        questiontwoTxt = (EditText) findViewById(R.id.secretSeeQuestionTwo);
        answertwoTxt = (EditText) findViewById(R.id.secretSeeAnswerTwo);
        questionthreeTxt = (EditText) findViewById(R.id.secretSeeQuestionThree);
        answerthreeTxt = (EditText) findViewById(R.id.secretSeeAnswerThree);

        memoTxt = (EditText) findViewById(R.id.secretSeeMemo);
        spinner = (Spinner) findViewById(R.id.secretSeeSpinner);
        //在数据库里面查找输入的用户名
        myData = DataSupport.where("title = ?", Title).find(secretData.class);
        Log.i("ggg", "init: "+ Title );

        if(myData.size() != 0) {
                 Log.i("gggggg", "init: "+ Title );
                titleTxt.setText(myData.get(0).getTitle());
                usernameTxt.setText(myData.get(0).getUsername());
                passwordTxt.setText(myData.get(0).getPassword());
                if(myData.get(0).getNet().equals("NULL")){
                    secretSeeNetLayout.setVisibility(View.GONE);
                }else{
                    netTxt.setText(myData.get(0).getNet());
                }

                emailTxt.setText(myData.get(0).getEmail());
                phoneTxt.setText(myData.get(0).getPhone());
                paypasswordTxt.setText(myData.get(0).getPayPassword());

                memoTxt.setText(myData.get(0).getMemo());

                if (myData.get(0).getQuestion0ne().equals("NULL")){
                    secretSeeQuestionLayout.setVisibility(View.GONE);
                }else{
                    questiononeTxt.setText(myData.get(0).getQuestion0ne());
                    answeroneTxt.setText(myData.get(0).getAnswerOne());
                    questiontwoTxt.setText(myData.get(0).getQuestionTwo());
                    answertwoTxt.setText(myData.get(0).getAnswerTwo());
                    questionthreeTxt.setText(myData.get(0).getQuestionThree());
                    answerthreeTxt.setText(myData.get(0).getAnswerThree());
                }
                Drawable drawable = getResources().getDrawable(drawables[Integer.parseInt(myData.get(0).getType())]);
                spinner.setBackground(drawable);
                spinner.setSelection(Integer.parseInt(myData.get(0).getType()));
        }





        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SecretSeeActivity.this);
                builder.setTitle("确定删除");
                builder.setPositiveButton("删除",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                Toast.makeText(getApplicationContext(), "删除成功", Toast.LENGTH_LONG).show();
                                new Thread() {
                                    public void run() {
                                        try {
                                            Thread.sleep(1500);
                                            DataSupport.deleteAll(secretData.class, "title = ?",Title);
                                            //更新完以后进入显示提示框
                                            Message msg = hand.obtainMessage();
                                            hand.sendMessage(msg);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }.start();
                            }
                        });
                builder.setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.create();
                builder.show();

            }
        });
        keepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!keep) {
                    secretSeeNetLayout.setVisibility(View.VISIBLE);
                    secretSeeQuestionLayout.setVisibility(View.VISIBLE);
                    keep = true;  //进入编辑
                    initEdit(true);
                    spinner.setAdapter(adapter);
                    spinner.setSelection(Integer.parseInt(myData.get(0).getType()));
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        public void onItemSelected(AdapterView<?> parent, View view,
                                                   int positon, long id) {
                            type = positon;
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });

                    Drawable drawable = getResources().getDrawable(R.drawable.keep);
                    keepBtn.setBackground(drawable);
                }else{
                    Drawable drawable = getResources().getDrawable(R.drawable.edit);
                    keepBtn.setBackground(drawable);

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

                    DataSupport.deleteAll(secretData.class, "title = ?",Title);

                    myDataChange = new secretData(myData.get(0).getUser(), String.valueOf(type), title,
                            username, password);
                    if (title.equals("") || title.equals("") || username.equals("")
                            || password.equals("")) {
                        myToast("标题或用户名和密码不能为空");
                    } else {
                        myDataChange = new secretData(myData.get(0).getUser(), String.valueOf(type), title,
                                username, password);

                        if (!net.equals("")) {
                            myDataChange.setNet(net);
                        }
                        if (!memo.equals("")) {
                            myDataChange.setMemo(memo);
                        }
                        if (!memo.equals("")) {
                            myDataChange.setMemo(memo);
                        }
                        if (!email.equals("")) {
                            myDataChange.setEmail(email);
                        }
                        if (!email.equals("")) {
                            myDataChange.setEmail(email);
                        }
                        if (!phone.equals("")) {
                            myDataChange.setPhone(phone);
                        }
                        if (!paypassword.equals("")) {
                            myDataChange.setPayPassword(paypassword);
                        }
                        if (!questionone.equals("")) {
                            myDataChange.setQuestion0ne(questionone);
                        }
                        if (!questiontwo.equals("")) {
                            myDataChange.setQuestionTwo(questiontwo);
                        }
                        if (!questionthree.equals("")) {
                            myDataChange.setQuestionThree(questionthree);
                        }
                        if (!answerone.equals("")) {
                            myDataChange.setAnswerOne(answerone);
                        }
                        if (!answertwo.equals("")) {
                            myDataChange.setAnswerTwo(answertwo);
                        }
                        if (!answerthree.equals("")) {
                            myDataChange.setAnswerThree(answerthree);
                        }
                    }
                    myDataChange.save();
                    new Thread() {
                            public void run() {
                                try {
                                    Thread.sleep(1500);
                                    //更新完以后进入显示提示框
                                    Message msg = hand.obtainMessage();
                                    hand.sendMessage(msg);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                    }.start();
                }
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SecretSeeActivity.this, MyMainActivity.class));
                finish();
            }
        });

    }
    Handler hand = new Handler() {
        public void handleMessage(Message msg) {
            startActivity(new Intent(SecretSeeActivity.this, MyMainActivity.class));
            finish();
            super.handleMessage(msg);
        }
    };
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
            LinearLayout ll = new LinearLayout(SecretSeeActivity.this);
            ll.setOrientation(LinearLayout.HORIZONTAL);
            ll.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));

            ImageView iv = new ImageView(SecretSeeActivity.this);
            iv.setImageResource(drawables[position]);
            ll.addView(iv);

            return ll;
        }
    };

    private void initEdit(boolean bool) {
        titleTxt.setEnabled(bool);
        phoneTxt.setEnabled(bool);
        usernameTxt.setEnabled(bool);
        passwordTxt.setEnabled(bool);
        emailTxt.setEnabled(bool);
        memoTxt.setEnabled(bool);
        netTxt.setEnabled(bool);

        paypasswordTxt.setEnabled(bool);

        questiononeTxt.setEnabled(bool);
        answeroneTxt.setEnabled(bool);
        questiontwoTxt.setEnabled(bool);
        answertwoTxt.setEnabled(bool);
        questionthreeTxt.setEnabled(bool);
        answerthreeTxt.setEnabled(bool);

        memoTxt.setEnabled(bool);
        spinner.setEnabled(bool);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                //startActivity(new Intent(SecretSeeActivity.this, AddActivity.class));
                finish();
            }
        }
        return false;
    }


}
