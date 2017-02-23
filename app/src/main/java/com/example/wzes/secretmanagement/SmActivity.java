package com.example.wzes.secretmanagement;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SmActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sm);
        ActivityColletor.addActivity(SmActivity.this);
        ActivityColletor.addActivity(SmActivity.this);
    }
}
