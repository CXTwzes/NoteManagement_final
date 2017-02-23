package com.example.wzes.secretmanagement;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;


import com.example.wzes.adapter.secretAdapter;

import com.example.wzes.util.secretData;


import org.litepal.crud.DataSupport;

import java.util.ArrayList;

import java.util.List;
import java.util.Map;

/**
 * Created by WZES on 2016/12/7.
 */

public class SecretActivity extends Activity {
    private Button FindBtn = null;
    private EditText editTxt = null;
    //private ListView listView = null;
    private String Title;
    private boolean find = false;
    //private secretDataDBAdapter DB = null;
    private Map<String, String> mData;
    //private Map<String, String> mDataType;   //存储数据类型
    private SwipeRefreshLayout mySwipe;
    private RecyclerView myRecyclerView = null;
    private secretAdapter mySecretAdapter = null;
    private List<secretData> myList = new ArrayList<>();
    List<secretData> secretDatas;
    private String lastUsername;

    private int [] drawables = { R.drawable.usual, R.drawable.qq, R.drawable.wx , R.drawable.wb ,R.drawable.zfb,
            R.drawable.tc, R.drawable.ws , R.drawable.bdnm ,R.drawable.mm,
            R.drawable.bdnm, R.drawable.wph , R.drawable.aqy ,R.drawable.xc,
            R.drawable.qqyy, R.drawable.mp , R.drawable.aqy ,R.drawable.tb,
            R.drawable.kg, R.drawable.td,R.drawable.qqyx
    };
    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secret);
        ActivityColletor.addActivity(SecretActivity.this);

        //获取上次登录的用户名
        SharedPreferences sharedPreferences = this.getSharedPreferences(
                "lastUsername", MODE_PRIVATE);
        lastUsername = sharedPreferences.getString("lastUsername", "");
        FindBtn = (Button) findViewById(R.id.secretFindBtn);
        editTxt = (EditText) findViewById(R.id.secretTxt);
        myRecyclerView = (RecyclerView) findViewById(R.id.secretRecyclerView);
        //GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myRecyclerView.setLayoutManager(linearLayoutManager);
        Log.i("GGGGG", "initData: "+ "sssss");
        initData();

        mySecretAdapter = new secretAdapter(myList);
        myRecyclerView.setAdapter(mySecretAdapter);


        mySwipe = (SwipeRefreshLayout) findViewById(R.id.secretSwipe);
        mySwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1500);
                            //myFruitAdapter.notifyDataSetChanged();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                initData();
                                //mySecretAdapter.notifyDataSetChanged();
                                mySwipe.setRefreshing(false);
                            }
                        });


                    }
                }).start();
            }
        });


        FindBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = 0;
                Title = editTxt.getText().toString();
                for(int i = 0; i < secretDatas.size(); i++){
                    if(secretDatas.get(id).getTitle().equals(Title)){
                        find = true;
                        break;
                    }
                    id++;
                }
                if(find){
                    myRecyclerView.scrollToPosition(id);
                }
                else {
                    Toast.makeText(getApplicationContext(), "没找到！", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    protected void onStart() {
        initData();
        //mySecretAdapter.notifyDataSetChanged();
        super.onStart();
    }

    public void initData() {
        myList.clear();
        secretDatas = DataSupport.where("user = ?", lastUsername).find(secretData.class);
        for(secretData secret: secretDatas){
            myList.add(secret);
        }
        mySecretAdapter = new secretAdapter(myList);
        myRecyclerView.setAdapter(mySecretAdapter);
        mySecretAdapter.notifyDataSetChanged();
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SecretActivity.this);
                builder.setTitle("提示");
                builder.setMessage("你确定要退出吗？");
                DialogInterface.OnClickListener dialog = new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // TODO Auto-generated method stub
                        if (arg1 == DialogInterface.BUTTON_POSITIVE) {
                            arg0.cancel();
                        } else if (arg1 == DialogInterface.BUTTON_NEGATIVE) {
                            ActivityColletor.finishAll();
                        }
                    }
                };
                builder.setPositiveButton("取消", dialog);
                builder.setNegativeButton("确定", dialog);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        }
        return false;
    }
}
