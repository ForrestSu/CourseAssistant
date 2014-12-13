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

	private static final String[] DAJIE = { "��һ���", "�ڶ����", "�������", "���Ĵ��",
			"������" };
	private static final String[] WEEK = { "����һ", "���ڶ�", "������", "������", "������",
			"������", "������" };
	private static final String[] TYPE = { "����", "ѡ��", "��ѡ", "����" };
	private int week, dajie, type;
	private EditText courseName;
	private EditText teacherName;
	private EditText coursePlace;
	private EditText courseZC;
	private Spinner spinnerWeek;
	private Spinner spinnerFirstCourse;
	private Spinner spinnerType;
	private DBManager dbManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.courseaddlayout);
		// ��ʼ���ؼ�
		dbManager = new DBManager(this);
		init();
	}

	private void init() {
		// �ҵ��˵��ϵİ�ť��������
		ImageView courseAddCancel = (ImageView) findViewById(R.id.courseAddCancel);
		ImageView courseAddOk = (ImageView) findViewById(R.id.courseAddOk);
		courseAddCancel.setOnClickListener(this);
		courseAddOk.setOnClickListener(this);
		// ��ȡ����

		courseName = (EditText) findViewById(R.id.courseName);
		teacherName = (EditText) findViewById(R.id.teacherName);
		coursePlace = (EditText) findViewById(R.id.coursePlace);
		courseZC = (EditText) findViewById(R.id.courseZC);

		spinnerType = (Spinner) findViewById(R.id.spinnerType);
		spinnerWeek = (Spinner) findViewById(R.id.spinnerWeek);
		spinnerFirstCourse = (Spinner) findViewById(R.id.spinnerFirstCourse);

		// ���ÿ�������
		ArrayAdapter<String> adapter0 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, TYPE);
		adapter0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerType.setAdapter(adapter0);
		spinnerType.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				type = position;
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {}
		});
		// �������ڼ�
		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, WEEK);
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerWeek.setAdapter(adapter1);
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
		// ���õڼ����
		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, DAJIE);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerFirstCourse.setAdapter(adapter2);
		spinnerFirstCourse
				.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						dajie = position + 1;
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
			// ���ö���
			overridePendingTransition(R.anim.layout_in_fromleft,
								R.anim.layout_out_fromleft);
			break;
		case R.id.courseAddOk:
			String cname = courseName.getText().toString();
			if (cname == null || cname.length() < 2) {
				Toast.makeText(
						this,
						getResources().getString(R.string.add_course_name_null),
						Toast.LENGTH_LONG).show();
				courseName.setFocusable(true);
				break;
			}
			String tname = teacherName.getText().toString();
			if (tname == null || tname.length() < 2) {
				Toast.makeText(
						this,
						getResources().getString(
								R.string.add_course_teacher_null),
						Toast.LENGTH_LONG).show();
				teacherName.setFocusable(true);
				break;
			}
			String place = coursePlace.getText().toString();
			String cZC = courseZC.getText().toString();
			Course c = new Course();
			// ���ÿγ���
			c.setCourseName(cname);
			c.setTeacher(tname);
			c.setPlace(place);
			c.setType(type);
			c.setCourseWeek(week);
			c.setCourseJs(dajie);
			c.setCourseZc(cZC);
			// ���һ�ڿ�
			dbManager.addCourse(c);
			if (dbManager != null)
				dbManager.closeDB();
			Intent ret = new Intent();
			ret.putExtra("course", c);
			setResult(1, ret);
			this.finish();
			break;
		}
	}

	public interface courseContent {
		public void setCourseContent(int week, int firstCourseNum,
				int lastCourseNum, Course c);
	}

}
