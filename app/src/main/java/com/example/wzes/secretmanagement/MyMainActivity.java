package com.example.wzes.secretmanagement;

import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WZES on 2016/12/7.
 */

public class MyMainActivity extends ActivityGroup {

    private LinearLayout mMyBottemSecretBtn, mMyBottemTxtBtn,
            mMyBottemMemoBtn, mMyBottemMyBtn;
    private ImageView mMyBottemSecretImg, mMyBottemTxtImg,
            mMyBottemMemoImg, mMyBottemMyImg;
    private TextView mMyBottemSecretTxt, mMyBottemTxtTxt, mMyBottemMemoTxt,
            mMyBottemMyTxt;
    private Button addBtn;

    private List<View> list = new ArrayList<View>();
    private View view = null;
    private View view1 = null;
    private View view2 = null;
    private View view3 = null;
    private int item = 0;
    private android.support.v4.view.ViewPager mViewPager;
    private PagerAdapter pagerAdapter = null;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        item = intent.getIntExtra("item",0);
        Log.i("ITEM", "onCreate: "+ item);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main_new);
        ActivityColletor.addActivity(MyMainActivity.this);
        initView();
    }
    public void initView(){
        addBtn = (Button) findViewById(R.id.MyBottemAddBtn);

        mViewPager = (ViewPager) findViewById(R.id.FramePager);

        mMyBottemSecretBtn = (LinearLayout) findViewById(R.id.MyBottemSecretBtn);
        mMyBottemTxtBtn = (LinearLayout) findViewById(R.id.MyBottemTxtBtn);
        mMyBottemMemoBtn = (LinearLayout) findViewById(R.id.MyBottemMemoBtn);
        mMyBottemMyBtn = (LinearLayout) findViewById(R.id.MyBottemMyBtn);
        // 查找linearlayout中的imageview
        mMyBottemSecretImg = (ImageView) findViewById(R.id.MyBottemSecretImg);
        mMyBottemTxtImg = (ImageView) findViewById(R.id.MyBottemTxtImg);
        mMyBottemMemoImg = (ImageView) findViewById(R.id.MyBottemMemoImg);
        mMyBottemMyImg = (ImageView) findViewById(R.id.MyBottemMyImg);
        // 查找linearlayout中的textview
        mMyBottemSecretTxt = (TextView) findViewById(R.id.MyBottemSecretTxt);
        mMyBottemTxtTxt = (TextView) findViewById(R.id.MyBottemTxtTxt);
        mMyBottemMemoTxt = (TextView) findViewById(R.id.MyBottemMemoTxt);
        mMyBottemMyTxt = (TextView) findViewById(R.id.MyBottemMyTxt);

        pagerAdapter = new PagerAdapter() {
            /*必须重写四个方法*/
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }
            public int getCount() {
                return list.size();
            }
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                container.removeView(list.get(position));
            }
            public Object instantiateItem(ViewGroup container, int position) {
                View v = list.get(position);
                container.addView(v);
                return v;
            }
        };

        createView();


        // 设置我们的adapter
        mViewPager.setAdapter(pagerAdapter);

        MyBtnOnclick mytouchlistener = new MyBtnOnclick();
        addBtn.setOnClickListener(mytouchlistener);
        mMyBottemSecretBtn.setOnClickListener(mytouchlistener);
        mMyBottemTxtBtn.setOnClickListener(mytouchlistener);
        mMyBottemMemoBtn.setOnClickListener(mytouchlistener);
        mMyBottemMyBtn.setOnClickListener(mytouchlistener);

        mViewPager.setCurrentItem(item);
        // 设置viewpager界面切换监听,监听viewpager切换第几个界面以及滑动的
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            // arg0 获取 viewpager里面的界面切换到第几个的
            @Override
            public void onPageSelected(int arg0) {
                // 先清除按钮样式
                initBottemBtn();
                // 按照对应的view的tag来判断到底切换到哪个界面。
                // 更改对应的button状态
                int flag = (Integer) list.get((arg0)).getTag();
                if (flag == 0) {
                    mMyBottemSecretImg
                            .setImageResource(R.drawable.login_pressed);
                    mMyBottemSecretTxt.setTextColor(Color.parseColor("#3F51B5"));
                } else if (flag == 1) {
                    mMyBottemTxtImg
                            .setImageResource(R.drawable.day_pressed);
                    mMyBottemTxtTxt.setTextColor(Color.parseColor("#3F51B5"));
                } else if (flag == 2) {
                    mMyBottemMemoImg
                            .setImageResource(R.drawable.memo_pressed);
                    mMyBottemMemoTxt.setTextColor(Color
                            .parseColor("#3F51B5"));
                } else if (flag == 3) {
                    mMyBottemMyImg
                            .setImageResource(R.drawable.my_pressed);
                    mMyBottemMyTxt.setTextColor(Color.parseColor("#3F51B5"));
                }
            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }
    // 把viewpager里面要显示的view实例化出来，并且把相关的view添加到一个list当中
    private void createView(){
        view = this
                .getLocalActivityManager()
                .startActivity("secret",
                        new Intent(MyMainActivity.this, SecretActivity.class))
                .getDecorView();
        view.setTag(0);
        list.add(view);

        view1 = this
                .getLocalActivityManager()
                .startActivity("txt",
                        new Intent(MyMainActivity.this, TxtActivity.class))
                .getDecorView();
        view1.setTag(1);
        list.add(view1);
        view2 = this
                .getLocalActivityManager()
                .startActivity("memo",
                        new Intent(MyMainActivity.this, MemoActivity.class))
                .getDecorView();
        view2.setTag(2);
        list.add(view2);
        view3 = this
                .getLocalActivityManager()
                .startActivity("my",
                        new Intent(MyMainActivity.this, MyActivity.class))
                .getDecorView();
        view3.setTag(3);
        list.add(view3);
    }

    /**
     * 用linearlayout作为按钮的监听事件
     * */
    private class MyBtnOnclick implements View.OnClickListener {

        @Override
        public void onClick(View arg0) {
            int mBtnid = arg0.getId();
            switch (mBtnid) {
                case R.id.MyBottemSecretBtn:
                    // //设置viewpager跳转那个界面0这个参数和我们的list相关,相当于list里面的下标
                    mViewPager.setCurrentItem(0);
                    initBottemBtn();
                    mMyBottemSecretImg
                            .setImageResource(R.drawable.login_pressed);
                    mMyBottemSecretTxt.setTextColor(getResources().getColor(
                            R.color.colorPrimary));
                    break;
                case R.id.MyBottemTxtBtn:
                    mViewPager.setCurrentItem(1);
                    initBottemBtn();
                    mMyBottemTxtImg
                            .setImageResource(R.drawable.day_pressed);
                    mMyBottemTxtTxt.setTextColor(getResources().getColor(
                            R.color.colorPrimary));
                    break;
                case R.id.MyBottemMemoBtn:
                    mViewPager.setCurrentItem(2);
                    initBottemBtn();
                    mMyBottemMemoImg
                            .setImageResource(R.drawable.memo_pressed);
                    mMyBottemMemoTxt.setTextColor(getResources().getColor(
                            R.color.colorPrimary));
                    break;
                case R.id.MyBottemMyBtn:
                    mViewPager.setCurrentItem(3);
                    initBottemBtn();
                    mMyBottemMyImg
                            .setImageResource(R.drawable.my_pressed);
                    mMyBottemMyTxt.setTextColor(getResources().getColor(
                            R.color.colorPrimary));
                    break;
                case R.id.MyBottemAddBtn:
                    startActivity(new Intent(MyMainActivity.this, AddActivity.class));
                    overridePendingTransition(R.anim.activity_open,0);
            }

        }

    }
    /**
     * 初始化控件的颜色
     * */
    private void initBottemBtn() {
        mMyBottemSecretImg.setImageResource(R.drawable.login_normal);
        mMyBottemTxtImg.setImageResource(R.drawable.day_normal);
        mMyBottemMemoImg.setImageResource(R.drawable.memo_normal);
        mMyBottemMyImg.setImageResource(R.drawable.my_normal);

        mMyBottemSecretTxt.setTextColor(getResources().getColor(
                R.color.silver));
        mMyBottemTxtTxt.setTextColor(getResources().getColor(
                R.color.silver));
        mMyBottemMemoTxt.setTextColor(getResources().getColor(
                R.color.silver));
        mMyBottemMyTxt.setTextColor(getResources().getColor(
                R.color.silver));

    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MyMainActivity.this);
                builder.setTitle("提示");
                builder.setMessage("你确定要退出吗？");

                DialogInterface.OnClickListener dialog = new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // TODO Auto-generated method stub
                        if (arg1 == DialogInterface.BUTTON_POSITIVE) {
                            arg0.cancel();
                        } else if (arg1 == DialogInterface.BUTTON_NEGATIVE) {
                            MyMainActivity.this.finish();
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
