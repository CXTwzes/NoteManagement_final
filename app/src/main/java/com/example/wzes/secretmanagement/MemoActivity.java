package com.example.wzes.secretmanagement;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.wzes.adapter.memoAdapter;
import com.example.wzes.adapter.secretAdapter;
import com.example.wzes.util.memoData;
import com.example.wzes.util.secretData;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static android.R.attr.x;
import static android.R.attr.y;
import static com.example.wzes.secretmanagement.R.id.memoFinishBtn;

/**
 * Created by WZES on 2016/12/7.
 */

public class MemoActivity extends Activity {
    private SwipeRefreshLayout mySwipe;
    private RecyclerView myRecyclerView = null;
    private memoAdapter myMemoAdapter = null;
    private List<memoData> myList = new ArrayList<>();
    private List<memoData> memoDatas;
    private Button finishBtn, unFinishBtn;
    private String lastUsername;
    private View unFView, FView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_memo);
        ActivityColletor.addActivity(MemoActivity.this);
        unFView = findViewById(R.id.memoNotFinish);
        FView = findViewById(R.id.memoFinish);
        //获取上次登录的用户名
        SharedPreferences sharedPreferences = this.getSharedPreferences(
                "lastUsername", MODE_PRIVATE);
        lastUsername = sharedPreferences.getString("lastUsername", "");
        myRecyclerView = (RecyclerView) findViewById(R.id.memoRecyclerView);
        mySwipe = (SwipeRefreshLayout) findViewById(R.id.memoSwipe);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        //myRecyclerView.setLayoutManager(layoutManager);
        myRecyclerView.setLayoutManager(linearLayoutManager);
        finishBtn = (Button) findViewById(R.id.memoFinishBtn);
        unFinishBtn = (Button) findViewById(R.id.memoNotFinishBtn);


        initData();

        sortList();

        myMemoAdapter = new memoAdapter(myList);
        myRecyclerView.setAdapter(myMemoAdapter);

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
                                //sortList();
                                myMemoAdapter.notifyDataSetChanged();
                                mySwipe.setRefreshing(false);
                            }
                        });


                    }
                }).start();
            }
        });
        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishBtn.setTextColor(getResources().getColor(R.color.colorPrimary));
                unFinishBtn.setTextColor(getResources().getColor(R.color.silver));
                unFView.setBackgroundColor(getResources().getColor(R.color.white));
                FView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                initUnData();

            }
        });

        unFinishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishBtn.setTextColor(getResources().getColor(R.color.silver));
                unFinishBtn.setTextColor(getResources().getColor(R.color.colorPrimary));
                FView.setBackgroundColor(getResources().getColor(R.color.white));
                unFView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                initData();

            }
        });
    }

    private void sortList() {
        Collections.sort(myList,new Comparator<memoData>(){
            public int compare(memoData arg0, memoData arg1) {
                return arg0.getPriority() < arg1.getPriority() ? 1 : -1;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
        initUnData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initData();
        initUnData();
    }


    public void initData() {
        Intent intent = new Intent(MemoActivity.this, MyService.class);
        myList.clear();
        memoDatas = DataSupport.where("user = ? and finish = ?", lastUsername , "false").find(memoData.class);
        for(memoData memo: memoDatas){
            myList.add(memo);
        }
        intent.putExtra("lastUsername",lastUsername);
        startService(intent);
        sortList();
        myMemoAdapter = new memoAdapter(myList);
        myRecyclerView.setAdapter(myMemoAdapter);
        myMemoAdapter.notifyDataSetChanged();
    }
    public void initUnData() {
        myList.clear();
        memoDatas = DataSupport.where("user = ? and finish = ?", lastUsername , "true").find(memoData.class);
        for(memoData memo: memoDatas){
            myList.add(memo);
        }
        sortList();
        finishBtn.setTextColor(getResources().getColor(R.color.colorPrimary));
        unFinishBtn.setTextColor(getResources().getColor(R.color.silver));
        unFView.setBackgroundColor(getResources().getColor(R.color.white));
        FView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        //finishBtn.setFocusable(true);
        myMemoAdapter = new memoAdapter(myList);
        myRecyclerView.setAdapter(myMemoAdapter);
        myMemoAdapter.notifyDataSetChanged();
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MemoActivity.this);
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
