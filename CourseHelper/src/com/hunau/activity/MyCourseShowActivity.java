package com.hunau.activity;

import com.hunau.coursehelper.R;
import com.hunau.dao.Course;
import com.hunau.db.DBManager;

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
import android.widget.TextView;
import android.widget.Toast;

public class MyCourseShowActivity extends Activity implements OnClickListener {

	private static final String[] DAJIE = { "第一大节", "第二大节", "第三大节", "第四大节",
			"第五大节" };
	private static final String[] WEEK = { "星期一", "星期二", "星期三", "星期四", "星期五",
			"星期六", "星期日" };
	private static final String[] TYPE = { "必修", "选修", "限选", "其他" };
	private int week, dajie, type,id;
	private EditText courseName;
	private EditText teacherName;
	private EditText coursePlace;
	private EditText courseZC;
	private Spinner spinnerWeek;
	private Spinner spinnerFirstCourse;
	private Spinner spinnerType;
	private DBManager dbManager;
	private boolean canEdit=true;
	ImageView courseAddCancel;
	ImageView courseAddOk;
	Course savedCourse;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.courseaddlayout);
		// 初始化控件
		init();
	}

	private void init() {
		// 找到菜单上的按钮，并监听
		Course c=(Course) getIntent().getSerializableExtra("course");
		TextView title=(TextView)findViewById(R.id.title);
		title.setText(getResources().getString(R.string.title_update_course));
		courseAddCancel = (ImageView) findViewById(R.id.courseAddCancel);
		courseAddCancel.setImageDrawable(getResources().getDrawable(R.drawable.top_back));
		courseAddOk = (ImageView) findViewById(R.id.courseAddOk);
		
		courseAddCancel.setOnClickListener(this);
		courseAddOk.setOnClickListener(this);
		// 获取内容

		courseName = (EditText) findViewById(R.id.courseName);
		teacherName = (EditText) findViewById(R.id.teacherName);
		coursePlace = (EditText) findViewById(R.id.coursePlace);
		courseZC = (EditText) findViewById(R.id.courseZC);
         //填充数据
		id=c.getId();
		courseName.setText(c.getCourseName());
		teacherName.setText(c.getTeacher());
		coursePlace.setText(c.getPlace());
		courseZC.setText(c.getCourseZc());
		type=c.getType();
		week=c.getCourseWeek();
		dajie=c.getCourseJs();
		//
		spinnerType = (Spinner) findViewById(R.id.spinnerType);
		spinnerWeek = (Spinner) findViewById(R.id.spinnerWeek);
		spinnerFirstCourse = (Spinner) findViewById(R.id.spinnerFirstCourse);
        
		// 设置考试类型
		ArrayAdapter<String> adapter0 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, TYPE);
		adapter0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerType.setAdapter(adapter0);
		spinnerType.setSelection(type, true);
		spinnerType.setVisibility(type);
		spinnerType.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				type = position;
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {}
		});
		// 设置星期几
		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, WEEK);
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerWeek.setAdapter(adapter1);
		spinnerWeek.setSelection(week, true);
		spinnerWeek.setVisibility(week);
		spinnerWeek.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				week = position + 1;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
		// 设置第几大节
		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, DAJIE);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerFirstCourse.setAdapter(adapter2);
		spinnerWeek.setSelection(dajie, true);
		spinnerWeek.setVisibility(dajie);
		spinnerFirstCourse.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						dajie = position + 1;
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {

					}
				});
		//最后设置为不可编辑
		spinnerWeek .setEnabled(false);
		spinnerFirstCourse .setEnabled(false);
		canEdit=reverseControlsState(canEdit);
		//
				//
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.courseAddCancel:
			//有数据的话，带数据回去
			if(savedCourse!=null){
				Intent	ret=new Intent();
				ret.putExtra("course", savedCourse);
				setResult(2, ret);
			}
			this.finish();
			// 设置动画
			overridePendingTransition(R.anim.layout_in_fromleft,
					R.anim.layout_out_fromleft);
			break;
		case R.id.courseAddOk:
			
			canEdit=reverseControlsState(canEdit);	//变为不可用
			if(canEdit==true)break;
			//当前点击之后，为false,不可编辑，说明需要保存
			String cname = courseName.getText().toString();
			if (cname == null || cname.length() < 2) {
				Toast.makeText(this,getResources().getString(R.string.add_course_name_null),Toast.LENGTH_LONG).show();courseName.setFocusable(true);
				break;
			}
			String tname = teacherName.getText().toString();
			if (tname == null || tname.length() < 2) {
				Toast.makeText(this,getResources().getString(R.string.add_course_teacher_null),Toast.LENGTH_LONG).show();
				teacherName.setFocusable(true);
				break;
			}
			String place = coursePlace.getText().toString();
			String cZC = courseZC.getText().toString();
			if(savedCourse==null)savedCourse= new Course();
			// 设置课程名
			savedCourse.setId(id);
			savedCourse.setCourseName(cname);
			savedCourse.setTeacher(tname);
			savedCourse.setPlace(place);
			savedCourse.setType(type);
			savedCourse.setCourseWeek(week);
			savedCourse.setCourseJs(dajie);
			savedCourse.setCourseZc(cZC);
			// 添加一节课
			dbManager = new DBManager(this);
			dbManager.UpdateCourse(savedCourse);
			if (dbManager != null)dbManager.closeDB();
			
		    Toast.makeText(this, "课程更新成功", Toast.LENGTH_LONG).show();
			break;
		}
	}

	
	public boolean reverseControlsState(boolean canedit)
	{
		canedit=!canedit;
		if(canedit){
			courseAddOk.setImageDrawable(getResources().getDrawable(R.drawable.top_ok));
		}
		else {
			courseAddOk.setImageDrawable(getResources().getDrawable(R.drawable.top_edit));
		}
		courseName.setEnabled(canedit);
		teacherName.setEnabled(canedit);
		coursePlace.setEnabled(canedit);
		courseZC .setEnabled(canedit);
		spinnerType .setEnabled(canedit);
		//spinnerWeek .setEnabled(canedit);
		//spinnerFirstCourse .setEnabled(canedit);
		return canedit;
	}
}
