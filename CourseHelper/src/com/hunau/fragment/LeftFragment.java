package com.hunau.fragment;

import com.hunau.coursehelper.R;
import com.hunau.db.StuInfo;
import com.hunau.dbOper.DBManager;
import com.hunau.library.LibLogin;
import com.hunau.ui.EditCourse;
import com.hunau.ui.ShowGrade;
import com.hunau.ui.ShowRank;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class LeftFragment extends Fragment {

	private TextView name;
	private TextView motto;
	private TextView course;
	private TextView eaxm;
	private TextView grade;
	//private TextView all_grade;
	private TextView rank;
	private TextView library;
    private DBManager db;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.left, container, false);
		return view;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		db=new DBManager(getActivity());
		setView();
		setListener();
		
	}

	public void setView() {
		name = (TextView) getView().findViewById(R.id.your_name);
		motto = (TextView) getView().findViewById(R.id.your_motto);
		course = (TextView) getView().findViewById(R.id.your_course);
		eaxm = (TextView) getView().findViewById(R.id.your_exam);
		grade = (TextView) getView().findViewById(R.id.your_grade);
	//	all_grade=(TextView)getView().findViewById(R.id.all_grade);
		rank = (TextView) getView().findViewById(R.id.your_rank);
		library = (TextView) getView().findViewById(R.id.your_library);
		
		StuInfo stu=db.queryStuInfo();		
		if(stu!=null)//设置个人信息
	    {
			name.setText(stu.getStuname()+" "+stu.getStunumber());
			motto.setText("本期学分："+stu.getGrade());
		}
		db.closeDB();

	}
	public  void setListener() {
		
		grade.setOnClickListener(new OnClickListener() {
			  public void onClick(View v) {
				     Intent intent =new Intent(getActivity(), ShowGrade.class);
				     startActivityForResult(intent, 0);
				  }
			});
		library.setOnClickListener(new OnClickListener() {
			  public void onClick(View v) {
			     Intent intent =new Intent(getActivity(), LibLogin.class);
			     startActivityForResult(intent, 0);
			  }
		  });
		//显示课表
		course.setOnClickListener(new OnClickListener() {
			  public void onClick(View v) {
				  ((SlidingActivity) getActivity()).showLeft();
				  }
			  });
		rank.setOnClickListener(new OnClickListener() {
			  public void onClick(View v) {
				  Intent intent =new Intent(getActivity(), ShowRank.class);
	   		       startActivityForResult(intent, 0);
			  }
		  });
	/*	all_grade.setOnClickListener(new OnClickListener() {
			  public void onClick(View v) {
				  Intent intent =new Intent(getActivity(), EditCourse.class);
				     startActivityForResult(intent, 0);
			  }
		});*/

	}

}
