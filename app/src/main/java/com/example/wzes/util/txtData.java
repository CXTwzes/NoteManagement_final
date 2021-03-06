package com.example.wzes.util;

import org.litepal.crud.DataSupport;
/**
 * Created by WZES on 2016/12/17.
 */
public class txtData extends DataSupport{
	private String masterPassword = "123456";
	public txtData(String user, String content, String data, String title) {
		this.user = user;
		this.content = AESHelper.encrypt(content, masterPassword);
		this.data = AESHelper.encrypt(data, masterPassword);
		this.title = AESHelper.encrypt(title, masterPassword);
	}
	public String title;
	public String user;
	public String content;
	public String data;
	public txtData(){

	}
	/*public txtData(txtNetData td){
		user = td.getUser();
		content = td.getContent();
		data = td.getData();
		title = td.getTitle();
	}*/
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getContent() {
		return AESHelper.decrypt(content, masterPassword);
	}

	public void setContent(String content) {
		this.content = AESHelper.encrypt(content, masterPassword);
	}

	public String getData() {
		return AESHelper.decrypt(data, masterPassword);
	}

	public void setData(String data) {
		this.data = AESHelper.encrypt(data, masterPassword);
	}


	public String getTitle() {
		return AESHelper.decrypt(title, masterPassword);
	}

	public void setTitle(String title) {
		this.title = AESHelper.encrypt(title, masterPassword);
	}



}
