package com.example.wzes.secretmanagement;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

/**
 * Created by WZES on 2016/11/24.
 */

public class AddActivity extends Activity implements View.OnClickListener{
    private Button addSecret, addTxt, addMemo, addMore;
    private Button backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*//无title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
                WindowManager.LayoutParams. FLAG_FULLSCREEN);*/
        setContentView(R.layout.activity_add_select);
        ActivityColletor.addActivity(AddActivity.this);
        backBtn = (Button) findViewById(R.id.AddBackBtn);
        addSecret = (Button) findViewById(R.id.addSecretBtn);
        addTxt = (Button) findViewById(R.id.addTxtBtn);
        addMemo = (Button) findViewById(R.id.addMemoBtn);
        addMore = (Button) findViewById(R.id.addMoreBtn);
        backBtn.setOnClickListener(this);
        addSecret.setOnClickListener(this);
        addTxt.setOnClickListener(this);
        addMemo.setOnClickListener(this);
        addMore.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.addSecretBtn:
                startActivity(new Intent(AddActivity.this, SecretAddActivity.class));
                finish();
                break;
            case R.id.addTxtBtn:
                startActivity(new Intent(AddActivity.this, TxtAddActivity.class));
                Log.i("TAGGG","         addd");
                finish();
                break;
            case R.id.addMemoBtn:
                startActivity(new Intent(AddActivity.this, MemoAddActivity.class));
                finish();
                break;
            case R.id.addMoreBtn:
                startActivity(new Intent(AddActivity.this, MyActivity.class));
                finish();
                break;
            case R.id.AddBackBtn:
                //startActivity(new Intent(AddActivity.this, MyMainActivity.class));
                finish();
                overridePendingTransition(0,R.anim.activity_close);
                break;
        }

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                //startActivity(new Intent(AddActivity.this, MyMainActivity.class));
                finish();

            }
        }
        return false;
    }




}
