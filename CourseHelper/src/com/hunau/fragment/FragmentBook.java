package com.hunau.fragment;


import com.hunau.activity.MyBookBorrowActivity;
import com.hunau.coursehelper.R;
import com.hunau.util.*;
import com.slidingmenu.main.MainMenuSlidingActivity;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

@SuppressLint("HandlerLeak")
public class FragmentBook extends Fragment {

	private TextView state;
	private EditText et1, et2;
	private Button submit;
	private LinearLayout vLoad_Layout; // 进度条显示
	private Mysharepreference s;
	private Spinner mSpinner;
	String name,pass ;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.user_login, null);
		// 设置菜单按钮
		ImageView broadSideBtn = (ImageView) view.findViewById(R.id.showleft);
		TextView title=(TextView)view.findViewById(R.id.title);
		title.setText("读者查询");
		broadSideBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				MainMenuSlidingActivity ac = (MainMenuSlidingActivity) getActivity();
				ac.toggle();
			}
		});
		return view;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		s = new Mysharepreference(getActivity());
		vLoad_Layout = (LinearLayout)getView().findViewById(R.id.loading);
		vLoad_Layout.setVisibility(View.INVISIBLE); // 进度条不可见
		mSpinner = (Spinner)  getView().findViewById(R.id.spinner1);
		mSpinner.setVisibility(View.GONE);//去除网络选择按钮
		//////////////////////////////
		et1 = (EditText) getView().findViewById(R.id.studentnumber);
		et1.setText(s.getMessage("name"));
		et2 = (EditText) getView().findViewById(R.id.studentpassword);
		String txt=s.getMessage("libpass");
		et2.setText(txt==null?"0000":txt);
		state = (TextView) getView().findViewById(R.id.state);
		// 查询成绩
		submit = (Button) getView().findViewById(R.id.login);
		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(getSendMsg())
				{
					Intent mintent=new Intent(getActivity(),MyBookBorrowActivity.class);
					mintent.putExtra("name", name);
					mintent.putExtra("pass", pass);
					startActivityForResult(mintent, 0);
				}
			}
		});
	}
	public boolean getSendMsg() {
		if (!Connect_Internet.isConnect(getActivity())) {
			state.setText(getResources().getString(R.string.login_no_network));
			return false;
		}
		name = et1.getText().toString();
		pass = et2.getText().toString();
		if (name == null || name.length() < 4) {
			state.setText(getResources().getString(R.string.username_null));
			return false;
		}
		if (pass == null || pass.length() < 2) {
			state.setText(getResources().getString(R.string.userpassword_null));
			return false;
		}
		s.saveMessage("libpass", pass);
		return true ;
	}
}
