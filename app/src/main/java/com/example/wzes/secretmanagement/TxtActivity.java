package com.example.wzes.secretmanagement;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.KeyEvent;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.wzes.adapter.txtAdapter;
import com.example.wzes.util.txtData;


import org.litepal.crud.DataSupport;

public class TxtActivity extends Activity {

	public txtAdapter myTxtAdapter;
	public int number;
	private TextView numberTxt;
	private String lastUsername;
	private SharedPreferences sharedPreferences;
	private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView myRecyclerView;
	private List<txtData> myTxtList = new ArrayList<>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_txt);
		ActivityColletor.addActivity(TxtActivity.this);
		numberTxt = (TextView) findViewById(R.id.txtNumber);
		swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.txtSwipe);
		sharedPreferences = this.getSharedPreferences(
				"lastUsername", MODE_PRIVATE);
		lastUsername = sharedPreferences.getString("lastUsername", "");
		myRecyclerView = (RecyclerView) findViewById(R.id.txtRecyclerView);
		/*StaggeredGridLayoutManager staggeredGridLayoutManager =
				new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);*/
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
		linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		myRecyclerView.setLayoutManager(linearLayoutManager);
		showUpdate();

		swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				showUpdate();
				swipeRefreshLayout.setRefreshing(false);
			}
		});

	}

	public void showUpdate() {
		myTxtList.clear();
		List<txtData>myList = DataSupport.where("user = ?", lastUsername).find(txtData.class);

		if(myList.size() != 0){
			for (txtData txt : myList){
				myTxtList.add(txt);
			}
			number = myTxtList.size();
			numberTxt.setText("(" + this.number + ")");
			Collections.reverse(myTxtList);
			myTxtAdapter = new txtAdapter(myTxtList);
			myRecyclerView.setAdapter(myTxtAdapter);
			//myTxtAdapter.notifyDataSetChanged();
		}
		if (myTxtList.size() == 0 ) {
			number = 0;
			numberTxt.setText("0");
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (keyCode == KeyEvent.KEYCODE_BACK) {
				AlertDialog.Builder builder = new AlertDialog.Builder(TxtActivity.this);
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
