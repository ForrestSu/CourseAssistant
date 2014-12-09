package com.hunau.util;

import android.content.Context;
import android.content.SharedPreferences;

public class Mysharepreference {

	private Context context;

	public Mysharepreference(Context context) {
		this.context = context;
	}
    //1.存储共享参数的内容
	public boolean saveMessage(String key,String value) {
		boolean flag = false;
		// 建立共享参数的文件，一般不带后缀名，默认会保存成.xml的文件
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"userinfo", Context.MODE_PRIVATE);
		// 对数据进行编辑
		SharedPreferences.Editor editor=sharedPreferences.edit();
		editor.remove(key);
		editor.putString(key, value);
		flag=editor.commit();//将数据持久化到存储介质  可能是存储卡或者sd卡
		return flag;
	}
	//2.获取共享参数的内容
	public  String getMessage(String key)
	{
		SharedPreferences sharedPreferences=context.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
		//获得共享参数
		String name=sharedPreferences.getString(key, null);//null后一个参数表示缺省值
		return name;
	}

}
