package com.hunau.fragment;


import com.hunau.coursehelper.R;
import com.hunau.imp.ParseHtml;
import com.hunau.intface.Parse;
import com.hunau.util.*;
import com.slidingmenu.main.MainMenuSlidingActivity;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

@SuppressLint("HandlerLeak")
public class FragmentLogin extends Fragment {

	private static final String[] NETWORK = { "校园网", "公共网络" };
	private TextView state;
	private EditText et1, et2;
	private Button submit;
	private SimuLogin l;
	private LinearLayout vLoad_Layout; // 进度条显示
	private Mysharepreference s;
	private int UrlIndex;
	private Spinner mSpinner;
	private String sendmsg;
	private Handler hander = new Handler() {
		@Override
		public void handleMessage(Message msg) {

			vLoad_Layout.setVisibility(View.INVISIBLE); // 进度条不可见
			if (msg.what == 0) {
				// success
				state.setText(getResources().getString(R.string.login_success));
				state.setTextColor(Color.BLACK);
				MainMenuSlidingActivity ac = (MainMenuSlidingActivity) getActivity();
				ac.updateUserInfo();
				ac.showCourseTable();//直接跳转到课程表
			} else if (msg.what == 1) {
				// 密码错误
				state.setText(getResources().getString(
						R.string.login_password_wrong));
			} else {
				// 网站在维护，可能无法通过公共网络访问
				if(UrlIndex==1)state.setText(getResources().getString(R.string.login_fail_schoolnet));
				else state.setText(getResources().getString(R.string.login_fail));
			}
		}
	};

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.user_login, null);
		// 设置菜单按钮
		ImageView broadSideBtn = (ImageView) view.findViewById(R.id.showleft);
		broadSideBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				MainMenuSlidingActivity ac = (MainMenuSlidingActivity) getActivity();
				ac.toggle();
			}
		});
		vLoad_Layout = (LinearLayout) view.findViewById(R.id.loading);
		vLoad_Layout.setVisibility(View.INVISIBLE); // 进度条不可见
		return view;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		s = new Mysharepreference(getActivity());
		// 初始化UrlIndex
		String net = s.getMessage("network");
		UrlIndex = 0;
		if (net != null && net.equals("1"))
			UrlIndex = 1;
		init();
	}

	public void init() {
		et1 = (EditText) getView().findViewById(R.id.studentnumber);
		et1.setText(s.getMessage("name"));
		et2 = (EditText) getView().findViewById(R.id.studentpassword);
		et2.setText(s.getMessage("pass"));
		state = (TextView) getView().findViewById(R.id.state);
		// 添加事件监听
		mSpinner = (Spinner) getView().findViewById(R.id.spinner1);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, NETWORK);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSpinner.setAdapter(adapter);
		mSpinner.setSelection(UrlIndex, true);
		mSpinner.setVisibility(UrlIndex);
		// 添加事件Spinner事件监听 ,保存下标即可
		mSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				UrlIndex = position;
				s.saveMessage("network", String.valueOf(position));
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
		// 查询成绩
		submit = (Button) getView().findViewById(R.id.login);
		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				/** 获取课表-------------------------------- */
				sendmsg = getSendMsg();
				if (sendmsg == null)
					return;
				state.setText("");
				vLoad_Layout.setVisibility(View.VISIBLE); // 进度条可见
				Thread thread = new Thread() {
					public void run() {
						Message msg = new Message();
						String html = null;
						String[] url = new String[] {
								HunauURL.COURSE_LIST[UrlIndex],
								HunauURL.COURSE_TABLE[UrlIndex] };
						l = new SimuLogin();
						html = l.login(HunauURL.LOGIN[UrlIndex], sendmsg,true);
						if (l.isLoginFail(html,"错误")) {
							msg.what = 1;//登录失败
							hander.sendMessage(msg);return;
						}
						l.login(url[0], null, true);
						html = l.login(url[1], null, false);
						if (html == null) {
							msg.what = 2;//网站内部正在维护....
							hander.sendMessage(msg);return;
						}
						Parse ph = new ParseHtml();
						ph.AanalysisHtmlCourse(html, getActivity());
						msg.what = 0;
						hander.sendMessage(msg);
					}
				};
				thread.start();
			}
		});
	}

	public String getSendMsg() {
		if (!Connect_Internet.isConnect(getActivity())) {
			state.setText("亲，未连接网络");
			return null;
		}
		String name = et1.getText().toString();
		String pass = et2.getText().toString();
		if (name == null || name.length() < 4) {
			state.setText(getResources().getString(R.string.username_null));
			return null;
		}
		if (pass == null || pass.length() < 2) {
			state.setText(getResources().getString(R.string.userpassword_null));
			return null;
		}
		s.saveMessage("name", name);
		s.saveMessage("pass", pass);
		String sendmsg = "userName=" + name + "&userPass=" + pass;
		return sendmsg;
	}
}
