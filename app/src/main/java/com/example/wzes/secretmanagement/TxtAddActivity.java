package com.example.wzes.secretmanagement;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.example.wzes.util.Date;
import com.example.wzes.util.txtData;

public class TxtAddActivity extends Activity {
	  private Button cancelButton;
	  private Context context = this;
	  private String date;
	  private EditText editText;
	  private Date getDate;
	  private Button sureButton;
	  private TextView textView;

	  private String lastUsername;
	  protected void onCreate(Bundle paramBundle) {
		  super.onCreate(paramBundle);
		  setContentView(R.layout.activity_txt_add);
		  ActivityColletor.addActivity(TxtAddActivity.this);

		  //获取上次登录的用户名
		  SharedPreferences sharedPreferences = this.getSharedPreferences(
				  "lastUsername", MODE_PRIVATE);
		  lastUsername = sharedPreferences.getString("lastUsername", "");
		  textView = ((TextView)findViewById(R.id.writedate));
		  editText = ((EditText) findViewById(R.id.edittext));
		  cancelButton = ((Button)findViewById(R.id.TxtAddBackBtn));
		  sureButton = ((Button)findViewById(R.id.TxtAddKeepBtn));

		  getDate = new Date();
		  date = getDate.getDate();
		  textView.setText(this.date);

		  sureButton.setOnClickListener(new View.OnClickListener() {
			 @Override
			 public void onClick(View v) {
				 txtData localTxtData = new txtData();
				 String strContent = TxtAddActivity.this.editText.getText().toString();
				 if (strContent.equals("")) {
					 Toast.makeText(TxtAddActivity.this, "内容不能为空", Toast.LENGTH_SHORT).show();
					 return;
				 }
				 String strTitle = strContent.length() > 50?" "+strContent.substring(0, 50):strContent;
				 localTxtData.setUser(lastUsername);
				 localTxtData.setContent(strContent);
				 localTxtData.setTitle(strTitle);
				 localTxtData.setData(date);
				 localTxtData.save();
				 Intent intent = new Intent(TxtAddActivity.this, MyMainActivity.class);
				 intent.putExtra("item",1);
				 startActivity(intent);
				 finish();
			 }
		});
	    cancelButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	  }
}





