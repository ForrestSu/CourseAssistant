package com.hunau.util;

import android.content.Context;
import android.content.SharedPreferences;

public class Mysharepreference {

	private Context context;

	public Mysharepreference(Context context) {
		this.context = context;
	}
    //1.�洢�������������
	public boolean saveMessage(String key,String value) {
		boolean flag = false;
		// ��������������ļ���һ�㲻����׺����Ĭ�ϻᱣ���.xml���ļ�
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"userinfo", Context.MODE_PRIVATE);
		// �����ݽ��б༭
		SharedPreferences.Editor editor=sharedPreferences.edit();
		editor.remove(key);
		editor.putString(key, value);
		flag=editor.commit();//�����ݳ־û����洢����  �����Ǵ洢������sd��
		return flag;
	}
	//2.��ȡ�������������
	public  String getMessage(String key)
	{
		SharedPreferences sharedPreferences=context.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
		//��ù������
		String name=sharedPreferences.getString(key, null);//null��һ��������ʾȱʡֵ
		return name;
	}

}
