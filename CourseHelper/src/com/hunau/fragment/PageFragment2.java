package com.hunau.fragment;

import java.io.IOException;
import java.io.InputStream;

import com.hunau.accessinternet.isConnect_Internet;
import com.hunau.coursehelper.HUNAU;
import com.hunau.coursehelper.R;
import com.hunau.library.Mysharepreference;
import com.hunau.util_course.SimuLogin;
import com.hunau.util_grade.ParseHtmlCourse;
import com.hunau.util_grade.ParseHtmlGrade;
import com.hunau.util_grade.ParseRank;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PageFragment2 extends Fragment implements OnClickListener {

	TextView tv;
	EditText et1, et2;
	private Button submit;
	private Button grade;// 获取成绩
	SimuLogin l;
	private LinearLayout vLoad_Layout; // 进度条显示
	private Mysharepreference s;
	private Handler hander = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				vLoad_Layout.setVisibility(View.INVISIBLE); // 进度条不可见
				tv.setText("密码错or教务系统正在维护...");

				break;
			case 1:
				vLoad_Layout.setVisibility(View.INVISIBLE); // 进度条不可见
				tv.setText("导入课表成功");
				break;
			case 2:
				vLoad_Layout.setVisibility(View.INVISIBLE); // 进度条不可见
				tv.setText("导入成绩成功");
				break;
			}
		}
	};

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.page2, null);

		vLoad_Layout = (LinearLayout) view.findViewById(R.id.loading);
		vLoad_Layout.setVisibility(View.INVISIBLE); // 进度条不可见

		return view;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		s = new Mysharepreference(getActivity());
		init();
	}

	public void init() {
		et1 = (EditText) getView().findViewById(R.id.name);
		et1.setText(s.getMessage("stuno"));
		et2 = (EditText) getView().findViewById(R.id.pass);
		tv = (TextView) getView().findViewById(R.id.result);
		// 添加事件监听
		submit = (Button) getView().findViewById(R.id.submit);
		submit.setOnClickListener(this);
		// 添加事件监听
		grade = (Button) getView().findViewById(R.id.grade);
		grade.setOnClickListener(this);
	}

	/** 获取成绩-------------------------------- */
	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.submit:
			if (!isConnect_Internet.isConnect(getActivity())) {
				tv.setText("亲，请检查网络");
				break;
			}
			tv.setText("");
			vLoad_Layout.setVisibility(View.VISIBLE); // 进度条可见
			Thread thread = new Thread() {
				public void run() {
					InputStream in = null;
					in = getContent(new String[] { HUNAU.url_course_list,
							HUNAU.url_course_table });
					if (in == null) {
						Message msg = new Message();
						msg.what = 0;
						hander.sendMessage(msg);
					} else {
						ParseHtmlCourse ph = new ParseHtmlCourse();
						try {
							ph.AanalysisHtml(in, getActivity());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Message msg = new Message();
						msg.what = 1;
						hander.sendMessage(msg);
					}

				}
			};
			thread.start();

			break;

		case R.id.grade:
			/******** 获取排名 ****/
			if (!isConnect_Internet.isConnect(getActivity())) {
				tv.setText("亲，请检查网络");
				break;
			}
			tv.setText("");
			vLoad_Layout.setVisibility(View.VISIBLE); // 进度条可见
			Thread thread2 = new Thread() {
				public void run() {
					InputStream in = null;
					in = getContent(new String[] { HUNAU.url_grade_list,
							HUNAU.url_grade_rank });
					if (in == null) {
						Message msg = new Message();
						msg.what = 0;
						hander.sendMessage(msg);
					} else {
						ParseHtmlGrade pg = new ParseHtmlGrade();
						try {
							pg.AanalysisHtml(in, getActivity());
							String info = pg.deal();
							if (info != null) {
								ParseRank pr = new ParseRank();
								in = l.getRank(info);
								pr.AanalysisHtml(in, getActivity());
							}
						} catch (IOException e) {
							e.printStackTrace();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Message msg = new Message();
						msg.what = 2;
						hander.sendMessage(msg);
					}

				}
			};
			thread2.start();
			break;
		}

	}

	public InputStream getContent(String[] url) {
		String name = et1.getText().toString();
		String pass = et2.getText().toString();
		InputStream in = null;
		s.saveMessage("stuno", name);
		l = new SimuLogin();
		try {
			l.login(name, pass);
			in = l.getPage(url);
		} catch (Exception e) {
			e.printStackTrace();
			Log.i("TAG", "访问网络失败");
		}
		return in;
	}
}
