package com.hunau.activity;

import com.hunau.coursehelper.R;
import com.hunau.dao.Course;
import com.hunau.db.DBManager;
import com.hunau.fragment.FragmentCourse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class MyCourseAddActivity extends Activity implements OnClickListener {
	
	private static final String[] DAJIE = {"第一大节","第二大节","第三大节","第四大节","第五大节"};
	private static final String[] WEEK = {"星期一","星期二","星期三","星期四","星期五","星期六","星期日"};
	private int week,dajie,type;
	private EditText courseName;
	private EditText teacherName;
	private EditText coursePlace;
	private EditText courseZC;
	private Spinner spinnerWeek;
	private Spinner spinnerFirstCourse;
	private DBManager dbManager;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.courseaddlayout);
		//初始化控件
		dbManager=new DBManager(this);
		init();
	}

	private void init() {
		// 找到菜单上的按钮，并监听
		ImageView courseAddCancel = (ImageView) findViewById(R.id.courseAddCancel);
		ImageView courseAddOk = (ImageView) findViewById(R.id.courseAddOk);
		courseAddCancel.setOnClickListener(this);
		courseAddOk.setOnClickListener(this);
		// 获取内容
		
		courseName = (EditText) findViewById(R.id.courseName);
		teacherName = (EditText) findViewById(R.id.teacherName);
		coursePlace = (EditText) findViewById(R.id.coursePlace);
		courseZC=(EditText) findViewById(R.id.courseZC);
		spinnerWeek = (Spinner) findViewById(R.id.spinnerWeek);
		spinnerFirstCourse = (Spinner) findViewById(R.id.spinnerFirstCourse);
	   // 设置星期几
		ArrayAdapter<String> adapter1= new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,WEEK);
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerWeek.setAdapter(adapter1);
		spinnerWeek.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				   week=position+1;
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}
		});
		//设置第几大节
		ArrayAdapter<String> adapter2= new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,DAJIE);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerFirstCourse.setAdapter(adapter2);
		spinnerFirstCourse.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				   dajie=position+1;
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}
		});
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.courseAddCancel:
			this.finish();
			break;
		case R.id.courseAddOk:
			String cname=courseName.getText().toString();
			if(cname==null||cname.length()<2)
			{
				Toast.makeText(this,getResources().getString(R.string.add_course_name_null), Toast.LENGTH_LONG).show();
				courseName.setFocusable(true);
				break;
			}
			String tname=teacherName.getText().toString();
			if(tname==null||tname.length()<2)
			{
				Toast.makeText(this,getResources().getString(R.string.add_course_teacher_null), Toast.LENGTH_LONG).show();
				teacherName.setFocusable(true);
				break;
			}
			String place=coursePlace.getText().toString();
			String cZC=courseZC.getText().toString();
			Course c=new Course();
			 //设置课程名
			c.setCourseName(cname);
			c.setTeacher(tname);
			c.setPlace(place);
			c.setCourseWeek(week);
			c.setCourseJs(dajie);
			c.setCourseZc(cZC);
			//添加一节课
			dbManager.addCourse(c);
			if(dbManager!=null)dbManager.closeDB();
			Intent ret=new Intent();
			ret.putExtra("course", c);
			setResult(1, ret);
			this.finish();
			break;
		}
	}
   
	public interface courseContent {
		public void setCourseContent(int week, int firstCourseNum,
				int lastCourseNum, String content);
	}

}
