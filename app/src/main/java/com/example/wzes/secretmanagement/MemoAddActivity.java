package com.example.wzes.secretmanagement;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.wzes.util.Date;
import com.example.wzes.util.memoData;

import java.util.Calendar;

import static java.lang.Math.round;

public class MemoAddActivity extends Activity {

    private RatingBar ratingBar;
    private EditText editText;
    private Button backBtn, keepBtn;
    private TextView dateTxt;
    private TextView timeTxt;
    private Calendar calendar;
    private memoData myMemoData;
    private String Username;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_add);
        ActivityColletor.addActivity(MemoAddActivity.this);
        ratingBar = (RatingBar) findViewById(R.id.memoAddStarNumber);
        editText = (EditText) findViewById(R.id.memoAddContext);
        backBtn = (Button) findViewById(R.id.memoAddBackBtn);
        keepBtn = (Button) findViewById(R.id.memoAddKeepBtn);
        dateTxt = (TextView) findViewById(R.id.memoAddDate);
        timeTxt = (TextView) findViewById(R.id.memoAddTime);

        //获取上次登录的用户名
        SharedPreferences sharedPreferences = this.getSharedPreferences(
                "lastUsername", MODE_PRIVATE);
        Username = sharedPreferences.getString("lastUsername", "");
        initDate();
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        keepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                date = dateTxt.getText().toString()+ " " +timeTxt.getText().toString();
                myMemoData = new memoData(Username, round(ratingBar.getRating()),
                        date, editText.getText().toString());
                Log.i("GG", "onClick: "+ round(ratingBar.getRating()));
                myMemoData.save();
                Intent intent = new Intent(MemoAddActivity.this, MyMainActivity.class);
                intent.putExtra("item",2);
                startActivity(intent);
                //startActivity(new Intent(MemoAddActivity.this, MyMainActivity.class));
                finish();
            }
        });
    }
    private void initDate() {
        calendar= Calendar.getInstance();
        String myDate = new Date().getDate();
        dateTxt.setText(myDate.substring(0, 10));
        //timeTxt.setText(myDate.substring(24,28));
        Log.i("GGGGG", "initDate: "+ myDate);
        dateTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(MemoAddActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        // TODO Auto-generated method stub
                        int mYear = year;
                        int mMonth = month;
                        int mDay = day;
                        //更新EditText控件日期 小于10加0
                        dateTxt.setText(new StringBuilder().append(mYear).append("-")
                                .append((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1))
                                .append("-")
                                .append((mDay < 10) ? "0" + mDay : mDay) );
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH) ).show();

            }
        });
        //timeTxt.setText(new Date().getDate().substring(13, 17));
        timeTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(MemoAddActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hour, int minute) {
                                // TODO Auto-generated method stub
                                int mHour = hour;
                                int mMinute = minute;
                                //更新EditText控件时间 小于10加0
                                timeTxt.setText(new StringBuilder()
                                        .append(mHour < 10 ? "0" + mHour : mHour).append(":")
                                .append(mMinute < 10 ? "0" + mMinute : mMinute));
                            }
                        }, calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE), true).show();
            }
        });
    }

}
