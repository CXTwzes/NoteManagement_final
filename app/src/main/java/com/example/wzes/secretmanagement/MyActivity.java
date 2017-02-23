package com.example.wzes.secretmanagement;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wzes.util.AESHelper;
import com.example.wzes.util.User;
import com.example.wzes.util.memoData;
import com.example.wzes.util.memoNetData;
import com.example.wzes.util.secretData;
import com.example.wzes.util.secretNetData;
import com.example.wzes.util.txtData;
import com.example.wzes.util.txtNetData;

import org.litepal.crud.ClusterQuery;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by WZES on 2016/12/7.
 */

public class MyActivity extends Activity implements View.OnClickListener{
    private Button outBtn, smBtn, toolBtn,aboutBtn, netBtn, deleteBtn, recoverBtn,
                netTxtBtn, recoverTxtBtn,netMemoBtn, recoverMemoBtn;
    private TextView userName;
    private ImageView image;
    private TextView name;
    private String masterPassword = "123456";
    private String lastUsername;
    private List<secretData> mySList = new ArrayList<>();
    private List<secretNetData> mySNetList = new ArrayList<>();
    private List<secretData> secretDatas;
    private List<txtData> myTList = new ArrayList<>();
    private List<txtNetData> myTNetList = new ArrayList<>();
    private List<txtData> txtDatas;
    private List<memoData> myMList = new ArrayList<>();
    private List<memoNetData> myMNetList = new ArrayList<>();
    private List<memoData> memoDatas;
    private int[] drawables = {R.drawable.usual, R.drawable.qq, R.drawable.wx , R.drawable.wb ,R.drawable.zfb,
            R.drawable.tc, R.drawable.ws , R.drawable.bdnm ,R.drawable.mm,
            R.drawable.bdnm, R.drawable.wph , R.drawable.aqy ,R.drawable.xc,
            R.drawable.qqyy, R.drawable.mp , R.drawable.aqy ,R.drawable.tb,
            R.drawable.kg, R.drawable.td ,R.drawable.qqyx };
    private boolean First = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_my);
        ActivityColletor.addActivity(MyActivity.this);
        //获取上次登录的用户名
        SharedPreferences sharedPreferences = this.getSharedPreferences(
                "lastUsername", MODE_PRIVATE);
        lastUsername = sharedPreferences.getString("lastUsername", "");

        userName = (TextView) findViewById(R.id.myUsername);
        userName.setText(lastUsername);

        image = (ImageView) findViewById(R.id.myImg);
        name = (TextView) findViewById(R.id.myName);
        List<User> datas = DataSupport.where("userName = ?", lastUsername).find(User.class);
        if(datas.size()!=0){
            image.setBackgroundDrawable(getResources().getDrawable(
                    drawables[datas.get(0).getType()]));
            name.setText(datas.get(0).getName());
        }


        outBtn = (Button) findViewById(R.id.myOutBtn);
        outBtn.setOnClickListener(this);
        smBtn = (Button) findViewById(R.id.mySmBtn);
        smBtn.setOnClickListener(this);
        aboutBtn = (Button) findViewById(R.id.myAboutBtn);
        aboutBtn.setOnClickListener(this);
        netBtn = (Button) findViewById(R.id.myNetSecretBtn);
        netBtn.setOnClickListener(this);
        deleteBtn = (Button) findViewById(R.id.myDeleteBtn);
        deleteBtn.setOnClickListener(this);
        recoverBtn = (Button) findViewById(R.id.myRecoverBtn);
        recoverBtn.setOnClickListener(this);
        toolBtn = (Button) findViewById(R.id.myTool);
        toolBtn.setOnClickListener(this);
        netTxtBtn = (Button) findViewById(R.id.myNetTxtBtn);
        netTxtBtn.setOnClickListener(this);
        netMemoBtn = (Button) findViewById(R.id.myNetMemoBtn);
        netMemoBtn.setOnClickListener(this);
        recoverTxtBtn = (Button) findViewById(R.id.myRecoverTxtBtn);
        recoverTxtBtn.setOnClickListener(this);
        recoverMemoBtn = (Button) findViewById(R.id.myRecoverMemoBtn);
        recoverMemoBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.myOutBtn:
                startActivity(new Intent(MyActivity.this, LoginActivity.class));
                ActivityColletor.finishAll();
                finish();
                break;
            case R.id.mySmBtn:
                startActivity(new Intent(MyActivity.this, SmActivity.class));
                break;
            case R.id.myAboutBtn:
                break;
            case R.id.myDeleteBtn:
                AlertDialog.Builder builderDelete = new AlertDialog.Builder(MyActivity.this);
                builderDelete.setTitle("提示");
                builderDelete.setMessage("你确定要清空备份吗？" +
                        "不可恢复");
                DialogInterface.OnClickListener dialogDelete = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // TODO Auto-generated method stub
                        if (arg1 == DialogInterface.BUTTON_POSITIVE) {
                            arg0.cancel();
                        } else if (arg1 == DialogInterface.BUTTON_NEGATIVE) {
                            Log.i("DELETE", "onClick: ");
                            deleteMAll();
                            deleteSAll();
                            deleteTAll();
                        }
                    }
                };
                builderDelete.setPositiveButton("取消", dialogDelete);
                builderDelete.setNegativeButton("确定", dialogDelete);
                AlertDialog alertDialogDelete = builderDelete.create();
                alertDialogDelete.show();
                break;
            case R.id.myTool:
                startActivity(new Intent(MyActivity.this, SetActivity.class));
                break;
            case R.id.myRecoverBtn:
                AlertDialog.Builder builderRecover = new AlertDialog.Builder(MyActivity.this);
                builderRecover.setTitle("提示");
                builderRecover.setMessage("你确定要从网络恢复密码吗？");
                DialogInterface.OnClickListener dialogRecover = new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // TODO Auto-generated method stub
                        if (arg1 == DialogInterface.BUTTON_POSITIVE) {
                            arg0.cancel();
                        } else if (arg1 == DialogInterface.BUTTON_NEGATIVE) {
                            recoversecretAll();
                        }
                    }
                };
                builderRecover.setPositiveButton("取消", dialogRecover);
                builderRecover.setNegativeButton("确定", dialogRecover);
                AlertDialog alertDialogRecover = builderRecover.create();
                alertDialogRecover.show();

                break;
            case R.id.myRecoverTxtBtn:
                AlertDialog.Builder builderRecoverTxt = new AlertDialog.Builder(MyActivity.this);
                builderRecoverTxt.setTitle("提示");
                builderRecoverTxt.setMessage("你确定要从网络恢复记事本吗？");
                DialogInterface.OnClickListener dialogRecoverTxt = new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // TODO Auto-generated method stub
                        if (arg1 == DialogInterface.BUTTON_POSITIVE) {
                            arg0.cancel();
                        } else if (arg1 == DialogInterface.BUTTON_NEGATIVE) {
                            recovertxtAll();
                        }
                    }
                };
                builderRecoverTxt.setPositiveButton("取消", dialogRecoverTxt);
                builderRecoverTxt.setNegativeButton("确定", dialogRecoverTxt);
                AlertDialog alertDialogRecoverTxt = builderRecoverTxt.create();
                alertDialogRecoverTxt.show();

                break;
            case R.id.myRecoverMemoBtn:
                AlertDialog.Builder builderRecoverMemo = new AlertDialog.Builder(MyActivity.this);
                builderRecoverMemo.setTitle("提示");
                builderRecoverMemo.setMessage("你确定要从网络恢复待办吗？");
                DialogInterface.OnClickListener dialogRecoverMemo = new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // TODO Auto-generated method stub
                        if (arg1 == DialogInterface.BUTTON_POSITIVE) {
                            arg0.cancel();
                        } else if (arg1 == DialogInterface.BUTTON_NEGATIVE) {
                            recovermemoAll();
                        }
                    }
                };
                builderRecoverMemo.setPositiveButton("取消", dialogRecoverMemo);
                builderRecoverMemo.setNegativeButton("确定", dialogRecoverMemo);
                AlertDialog alertDialogRecoverMemo = builderRecoverMemo.create();
                alertDialogRecoverMemo.show();

                break;
            case R.id.myNetSecretBtn:
                AlertDialog.Builder builderNet = new AlertDialog.Builder(MyActivity.this);
                builderNet.setTitle("提示");
                builderNet.setMessage("你确定要备份吗？");
                DialogInterface.OnClickListener dialogNet = new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // TODO Auto-generated method stub
                        if (arg1 == DialogInterface.BUTTON_POSITIVE) {
                            arg0.cancel();
                        } else if (arg1 == DialogInterface.BUTTON_NEGATIVE) {
                            netSecret();
                        }
                    }
                };
                builderNet.setPositiveButton("取消", dialogNet);
                builderNet.setNegativeButton("确定", dialogNet);
                AlertDialog alertDialogNet = builderNet.create();
                alertDialogNet.show();

                //加载完以后进入main
                break;
            case R.id.myNetMemoBtn:
                AlertDialog.Builder builderNetMemo = new AlertDialog.Builder(MyActivity.this);
                builderNetMemo.setTitle("提示");
                builderNetMemo.setMessage("你确定要备份吗？" );
                DialogInterface.OnClickListener dialogNetMemo = new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // TODO Auto-generated method stub
                        if (arg1 == DialogInterface.BUTTON_POSITIVE) {
                            arg0.cancel();
                        } else if (arg1 == DialogInterface.BUTTON_NEGATIVE) {
                            netMemo();
                        }
                    }
                };
                builderNetMemo.setPositiveButton("取消", dialogNetMemo);
                builderNetMemo.setNegativeButton("确定", dialogNetMemo);
                AlertDialog alertDialogNetMemo = builderNetMemo.create();
                alertDialogNetMemo.show();

                //加载完以后进入main
                break;
            case R.id.myNetTxtBtn:
                AlertDialog.Builder builderNetTxt = new AlertDialog.Builder(MyActivity.this);
                builderNetTxt.setTitle("提示");
                builderNetTxt.setMessage("你确定要备份吗？");
                DialogInterface.OnClickListener dialogNetTxt = new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // TODO Auto-generated method stub
                        if (arg1 == DialogInterface.BUTTON_POSITIVE) {
                            arg0.cancel();
                        } else if (arg1 == DialogInterface.BUTTON_NEGATIVE) {
                            netTxt();
                        }
                    }
                };
                builderNetTxt.setPositiveButton("取消", dialogNetTxt);
                builderNetTxt.setNegativeButton("确定", dialogNetTxt);
                AlertDialog alertDialogNetTxt = builderNetTxt.create();
                alertDialogNetTxt.show();

                //加载完以后进入main
                break;
        }
    }


    private void netSecret() {
        //密码上传
        secretDatas = DataSupport.where("user = ?", lastUsername).find(secretData.class);
        for(secretData secret: secretDatas){
            secretNetData SND = new secretNetData(secret.getTitle(),secret.getType(),secret.getUser(),secret.getUsername(),
                    secret.getPassword(),secret.getEmail(),secret.getNet(),secret.getPhone(),secret.getPayPassword(),
                    secret.getQuestion0ne(),secret.getAnswerOne(),secret.getQuestionTwo(),secret.getAnswerTwo(),
                    secret.getQuestionThree(),secret.getAnswerThree(),secret.getMemo());
            SND.save(new SaveListener<String>() {
                @Override
                public void done(String objectId,BmobException e) {
                    if(e==null){

                    }else{
                        Toast.makeText(MyActivity.this, "备份失败", Toast.LENGTH_SHORT).show();
                    }

                }
            });
            Toast.makeText(MyActivity.this, "备份成功", Toast.LENGTH_SHORT).show();
        }
    }

    private void netMemo() {
        //memo上传
        memoDatas = DataSupport.where("user = ?", lastUsername).find(memoData.class);
        for(memoData memo: memoDatas){
            memoNetData SND = new memoNetData(memo.getUser(),memo.getPriority(), memo.getContext(), memo.getDate()
                    , memo.isFinish());
            SND.save(new SaveListener<String>() {
                @Override
                public void done(String objectId,BmobException e) {
                    if(e==null){
                        Message msg = hand.obtainMessage();
                        hand.sendMessage(msg);
                    }else{
                        Toast.makeText(MyActivity.this, "备份失败", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void netTxt() {
        //txt上传
        txtDatas = DataSupport.where("user = ?", lastUsername).find(txtData.class);
        for(txtData txt: txtDatas){
            txtNetData TND = new txtNetData(txt.getUser(), txt.getContent(), txt.getData(), txt.getTitle());
            TND.save(new SaveListener<String>() {
                @Override
                public void done(String objectId,BmobException e) {
                    if(e==null){

                    }else{
                        Toast.makeText(MyActivity.this, "备份失败", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            Toast.makeText(MyActivity.this, "备份记事本成功", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteMAll(){
        BmobQuery<memoNetData> bmobQuery = new BmobQuery<memoNetData>();
        bmobQuery.setLimit(1000);
        bmobQuery.findObjects(new FindListener<memoNetData>() {
            @Override
            public void done(List<memoNetData> list, BmobException e) {
                if (e == null) {
                    List<memoNetData> d = new ArrayList<memoNetData>();
                    for(memoNetData memoNData : list){
                        d.add(memoNData);
                    }
                    for(memoNetData n:d){
                        if(n.getUser().equals(lastUsername)){
                            n.setObjectId(n.getObjectId());
                            n.delete(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if(e==null){

                                    }else{
                                        Toast.makeText(MyActivity.this, "清除数据失败", Toast.LENGTH_SHORT).show();
                                    }
                                }

                            });
                        }
                    }
                    Toast.makeText(MyActivity.this, "清除Memo数据成功", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void deleteTAll(){
        BmobQuery<txtNetData> bmobQuery = new BmobQuery<txtNetData>();
        bmobQuery.setLimit(1000);
        bmobQuery.findObjects(new FindListener<txtNetData>() {
            @Override
            public void done(List<txtNetData> list, BmobException e) {
                if (e == null) {
                    List<txtNetData> t = new ArrayList<txtNetData>();
                    for(txtNetData txtNData : list){
                        t.add(txtNData);
                    }
                    for(txtNetData n:t){
                        if(n.getUser().equals(lastUsername)){
                            n.setObjectId(n.getObjectId());
                            n.delete(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if(e==null){
                                    }else{
                                        Toast.makeText(MyActivity.this, "清除数据失败", Toast.LENGTH_SHORT).show();
                                    }

                                }

                            });
                        }
                    }
                    Toast.makeText(MyActivity.this, "清除Txt数据成功", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void recovermemoAll(){
        BmobQuery<memoNetData> bmobQuery = new BmobQuery<memoNetData>();
        bmobQuery.setLimit(1000);
        bmobQuery.findObjects(new FindListener<memoNetData>() {
            @Override
            public void done(List<memoNetData> list, BmobException e) {
                if (e == null) {
                    //创建数据库
                    for(memoNetData memoNData : list){
                        myMNetList.add(memoNData);
                    }
                    for(memoNetData memoNData:myMNetList){
                        myMList = DataSupport.where("context = ? and user = ?",
                                memoNData.getContext(), lastUsername).find(memoData.class);
                        if(myMList.size()!=0){
                            //已存在
                        }else{
                            new memoData(memoNData.getUser(),memoNData.getPriority(), memoNData.getDate(),
                                    memoNData.getContext()).save();
                        }
                    }
                    Toast.makeText(MyActivity.this, "恢复备忘录成功", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void recoversecretAll(){
        BmobQuery<secretNetData> bmobQuery = new BmobQuery<secretNetData>();
        bmobQuery.setLimit(100);
        bmobQuery.findObjects(new FindListener<secretNetData>() {
            @Override
            public void done(List<secretNetData> list, BmobException e) {
                if (e == null) {
                    //创建数据库
                    for(secretNetData secretNData : list){
                        mySNetList.add(secretNData);
                        Log.i("TTTT", "secretNdata: "+secretNData.getTitle()+"--2--"+lastUsername);
                    }
                    for(secretNetData secretNData :mySNetList){
                        mySList = DataSupport.where("title = ? and user = ?",
                                secretNData.getTitle(), lastUsername).find(secretData.class);

                        if(mySList.size()!=0){
                            //已存在
                            Log.i("TTTT", "done: "+secretNData.getTitle()+"1"+lastUsername);
                        }else{
                            Log.i("TTTT", "done: "+secretNData.getTitle()+"0"+lastUsername);
                            secretData s = new secretData(secretNData.getTitle(),secretNData.getType(),secretNData.getUser(),secretNData.getUsername(),
                                    secretNData.getPassword(),secretNData.getEmail(),secretNData.getNet(),secretNData.getPhone(),secretNData.getPayPassword(),
                                    secretNData.getQuestion0ne(),secretNData.getAnswerOne(),secretNData.getQuestionTwo(),secretNData.getAnswerTwo(),
                                    secretNData.getQuestionThree(),secretNData.getAnswerThree(),secretNData.getMemo());
                            s.save();
                        }
                    }
                    Toast.makeText(MyActivity.this, "恢复密码成功", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void recovertxtAll(){
        BmobQuery<txtNetData> bmobQuery = new BmobQuery<txtNetData>();
        bmobQuery.setLimit(1000);
        bmobQuery.findObjects(new FindListener<txtNetData>() {
            @Override
            public void done(List<txtNetData> list, BmobException e) {
                if (e == null) {
                    //创建数据库
                    for(txtNetData txtNData : list){
                        myTNetList.add(txtNData);
                    }
                    for(txtNetData txtNData:myTNetList){
                        myTList = DataSupport.where("content = ? and user = ?",
                                AESHelper.encrypt(txtNData.getContent(), masterPassword), lastUsername).find(txtData.class);
                        if(myTList.size()!=0){
                            //已存在
                        }else{
                            new txtData(txtNData.getUser(),txtNData.getContent(), txtNData.getData(),
                                    txtNData.getTitle()).save();
                        }
                    }
                    Toast.makeText(MyActivity.this, "恢复记事本成功", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void deleteSAll(){
        BmobQuery<secretNetData> bmobQuery = new BmobQuery<secretNetData>();
        bmobQuery.setLimit(1000);
        bmobQuery.findObjects(new FindListener<secretNetData>() {
            @Override
            public void done(List<secretNetData> list, BmobException e) {
                if (e == null) {
                    List<secretNetData> s = new ArrayList<secretNetData>();
                    for(secretNetData secretNData : list){
                        s.add(secretNData);
                    }
                    for(secretNetData n:s){
                        if(n.getUser().equals(lastUsername)){
                            n.setObjectId(n.getObjectId());
                            n.delete(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if(e==null){

                                    }else{
                                        Toast.makeText(MyActivity.this, "清除数据失败", Toast.LENGTH_SHORT).show();
                                    }
                                }

                            });
                        }
                    }
                    Toast.makeText(MyActivity.this, "清除Secret数据成功", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    Handler hand = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Toast.makeText(MyActivity.this, "备份成功", Toast.LENGTH_SHORT).show();
        }
    };
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MyActivity.this);
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
