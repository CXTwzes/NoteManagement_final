package com.example.wzes.secretmanagement;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.example.wzes.util.AESHelper;
import com.example.wzes.util.Date;
import com.example.wzes.util.txtData;

import org.litepal.crud.DataSupport;

public class TxtEditActivity extends Activity {
	private String masterPassword = "123456";
	private Button cancelButton;
	private String content;
	private Context context = this;
	private String date;
	private Date dateNow;
	private EditText editText;
	private Button sureButton;
	private TextView textView;
	private String lastUsername;
	@Override
	protected void onCreate(Bundle paramBundle) {

		super.onCreate(paramBundle);
		setContentView(R.layout.activity_txt_edit);
		ActivityColletor.addActivity(TxtEditActivity.this);
		//获取上次登录的用户名
		SharedPreferences sharedPreferences = this.getSharedPreferences(
				"lastUsername", MODE_PRIVATE);
		lastUsername = sharedPreferences.getString("lastUsername", "");
		textView = ((TextView) findViewById(R.id.editdate));
		editText = ((EditText) findViewById(R.id.edittexttwo));
		cancelButton = ((Button) findViewById(R.id.editbutton));
		sureButton = ((Button) findViewById(R.id.editbutton2));
		date = getIntent().getStringExtra("dateItem");
		content = getIntent().getStringExtra("contentItem");

		editText.setSelection(this.editText.length());
		editText.setText(this.content);
		textView.setText(this.date);
		dateNow = new Date();
		date = this.dateNow.getDate();
		textView.setText(this.date);
		sureButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				DataSupport.deleteAll(txtData.class, "content = ?", AESHelper.encrypt(content, masterPassword));

				String strContent = TxtEditActivity.this.editText.getText()
						.toString();
				if (strContent.equals("")) {
					Toast.makeText(TxtEditActivity.this.context, "不能为空",
							Toast.LENGTH_SHORT).show();
					return;
				}
				String strTitle = strContent.length() > 50 ? " "
						+ strContent.substring(0, 50) : strContent;
				txtData localTxtData = new txtData();
				localTxtData.setUser(lastUsername);
				localTxtData.setContent(strContent);
				localTxtData.setTitle(strTitle);
				localTxtData.setData(date);
				localTxtData.save();
				Intent intent = new Intent(TxtEditActivity.this, MyMainActivity.class);
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
