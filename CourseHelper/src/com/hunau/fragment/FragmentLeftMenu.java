package com.hunau.fragment;

import com.hunau.activity.ShowGrade;
import com.hunau.activity.ShowRank;
import com.hunau.coursehelper.R;
import com.hunau.dao.StuInfo;
import com.hunau.db.DBManager;
import com.slidingmenu.main.MainSlidingActivity;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FragmentLeftMenu extends Fragment {

	private TextView name;
	private TextView motto;
	private RelativeLayout courseList;
	private TextView eaxm;
	private TextView loginstate;
	private RelativeLayout grade;
	// private TextView all_grade;
	private TextView rank;
	private RelativeLayout library;
	private DBManager db;
	

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.left, container, false);
		return view;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		db = new DBManager(getActivity());
		Init();
		updateUserInfo();
		setListener();
	}

	public void Init() {
		name = (TextView) getView().findViewById(R.id.your_name);
		motto = (TextView) getView().findViewById(R.id.your_motto);
        loginstate=(TextView)getView().findViewById(R.id.login_logout);
		courseList = (RelativeLayout) getView().findViewById(
				R.id.mainListMyCourse);
		eaxm = (TextView) getView().findViewById(R.id.your_exam);
		grade = (RelativeLayout) getView().findViewById(R.id.mainListGrade);
		// all_grade=(TextView)getView().findViewById(R.id.all_grade);
		rank = (TextView) getView().findViewById(R.id.your_rank);
		library = (RelativeLayout) getView().findViewById(R.id.mainListLibrary);
		
	}

	public void updateUserInfo() {
		StuInfo stu = db.queryStuInfo();
		if (stu != null)// 设置个人信息
		{
			name.setText(stu.getStuname() + " " + stu.getStunumber());
			motto.setText("本期学分：" + stu.getGrade());
			loginstate.setText("注销");
		}
	}

	public void setListener() {
		// 显示课表
		courseList.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				FragmentCourse pf = new FragmentCourse();
				((MainSlidingActivity) getActivity())
						.changeContent(pf);
			}
		});
		//登陆状态
		loginstate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FragmentLogin pf = new FragmentLogin();

				((MainSlidingActivity) getActivity())
						.changeContent(pf);
			}
		});

		grade.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), ShowGrade.class);
				startActivityForResult(intent, 0);
			}
		});
		library.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// Intent intent =new Intent(getActivity(), LibLogin.class);
				// startActivityForResult(intent, 0);
				FragmentBook pfFragment1 = new FragmentBook();

				((MainSlidingActivity) getActivity())
						.changeContent(pfFragment1);

			}
		});

		rank.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), ShowRank.class);
				startActivityForResult(intent, 0);
			}
		});
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		db.closeDB();
	}
}
