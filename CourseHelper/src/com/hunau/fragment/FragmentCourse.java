package com.hunau.fragment;

import java.util.ArrayList;
import java.util.List;

import com.hunau.activity.MyCourseAddActivity;
import com.hunau.activity.MyCourseShowActivity;
import com.hunau.coursehelper.R;
import com.hunau.dao.Course;
import com.hunau.db.DBManager;
import com.hunau.imp.SyncHorizonScrollView;
import com.hunau.imp.SyncScrollView;
import com.slidingmenu.main.MainMenuSlidingActivity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;


public class FragmentCourse extends Fragment implements
		MyCourseAddActivity.courseContent {

	private RelativeLayout courseSetting;
	private ImageView addCourse;
	private RelativeLayout courseContent;
	private DBManager dbManager;
	private List<Course> courses = new ArrayList<Course>();
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.mycourselayout, null);
		// ���ò˵���ť
		ImageView broadSideBtn = (ImageView) view.findViewById(R.id.showleft);
		broadSideBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				MainMenuSlidingActivity ac = (MainMenuSlidingActivity) getActivity();
				ac.toggle();
			}
		});
		// ��ȡ����
		dbManager = new DBManager(getActivity());
		courses = dbManager.queryAllCourses();

		// ���õײ���Ӱ�ť
		addCourse = (ImageView) view.findViewById(R.id.addcourse);
		addCourse.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),
						MyCourseAddActivity.class);
				startActivityForResult(intent, 1);
			}
		});
		// ͷ�����ڼ�
		GridView head = (GridView) view.findViewById(R.id.gridWeek);
		String[] weeks = { "����һ", "���ڶ�", "������", "������", "������", "������", "������" };
		ArrayAdapter<String> headAdap = new ArrayAdapter<String>(getActivity(),
				R.layout.weekstyle, weeks);
		head.setAdapter(headAdap);

		// �ڼ��ڿ�
		GridView courseNum = (GridView) view.findViewById(R.id.gridCourseNum);
		String[] courseNums = { "1", "2", "3", "4", "5", "6", "7", "8", "9",
				"10", "11", "12" };
		ArrayAdapter<String> leftAdap = new ArrayAdapter<String>(getActivity(),
				R.layout.coursenumstyle, courseNums);
		courseNum.setAdapter(leftAdap);

		courseContent = (RelativeLayout) view.findViewById(R.id.relativeCourseContent);
		ShowCourse();
		// Button course = createCourse(3, 1, 2, "�ߵ���ѧ@35-102");
		// Button course2 = createCourse(1, 2, 3, "android@36-111");
		// Button course3 = createCourse(5, 5, 7, "ë��˼��������������@34-101");
		// courseContent.addView(course);
		// courseContent.addView(course2);
		// courseContent.addView(course3);

		// ����ˮƽ����
		SyncHorizonScrollView hCourseScoll = (SyncHorizonScrollView) view
				.findViewById(R.id.horizonScollviewOfCourse);
		SyncHorizonScrollView hWeekSColl = (SyncHorizonScrollView) view
				.findViewById(R.id.week);
		hCourseScoll.setAnotherView(hWeekSColl);
		hWeekSColl.setAnotherView(hCourseScoll);
		// sync vertical move
		SyncScrollView vCoursecoll = (SyncScrollView) view
				.findViewById(R.id.scollviewOfCourse);
		SyncScrollView vnumScoll = (SyncScrollView) view
				.findViewById(R.id.courseNum);
		vCoursecoll.setAnotherView(vnumScoll);
		vnumScoll.setAnotherView(vCoursecoll);
		return view;
	}

	private void ShowCourse() {
		if (courses != null)
			for (Course c : courses) {
				setCourseContent(c.getCourseWeek(), c.getCourseJs() * 2 - 1,
						c.getCourseJs() * 2, c );
			}
	}

	// �����γ�
	private Button createCourse(int week, int firstCourseNum,
			int lastCourseNum,final Course c) {
		// ��߾�
		int leftMargin = 0;
		// �ϱ߾�
		int topMargin = 0;
		// �γ̵ĳ���
		int courseLength = 0;
		// һ�ڿεĿ��
		int courseWidth = (int) (getResources()
				.getDimension(R.dimen.course_week_item_width));
		// һ�ڿεĸ߶�
		int courseHeight = (int) (getResources()
				.getDimension(R.dimen.course_num_item_height));
		// week�ĸ߶�
		int week_item_height = (int) (getResources()
				.getDimension(R.dimen.course_week_height));
		// courseNum�Ŀ��
		int courseNum_width = (int) (getResources()
				.getDimension(R.dimen.course_num_width));
		// ��(��)���
		int spacing = (int) (getResources().getDimension(R.dimen.spacing));
		// �����߾�=����м��ڿ�*���γ̿��+1px�ļ����+����ſ��
		leftMargin = (week - 1) * (courseWidth + spacing) + courseNum_width;
		// �ϱ߾�=�ϱ��м��ڿ�*���γ̸߶�+1px�ļ����+�������ڵĿ��
		topMargin = (firstCourseNum - 1) * (courseHeight + spacing)
				+ week_item_height;
		// �γ̳���=�γ̽���*(�γ̸߶�+1px�ļ��)-1������
		courseLength = (lastCourseNum - firstCourseNum + 1)
				* (courseHeight + spacing) - spacing;

		// �����γ�
		Button course = new Button(getActivity());
		RelativeLayout.LayoutParams marginParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		marginParams.setMargins(leftMargin, topMargin, 0, 0);
		course.setTag(week + "" + (lastCourseNum / 2));
		course.setLayoutParams(marginParams);
		course.setGravity(Gravity.CENTER);
		course.setWidth(courseWidth);
		course.setHeight(courseLength);
		course.setBackgroundColor(getResources().getColor(R.color.courseColor));
		course.setText(c.toString());
		course.setTextSize(15);
		course.setTextColor(Color.WHITE);
		course.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(getActivity(), MyCourseShowActivity.class);
				intent.putExtra("course", c);
				startActivityForResult(intent,2);	 
			}
		});
		return course;
	}
	


	@Override
	public void setCourseContent(int week, int firstCourseNum,
			int lastCourseNum,Course c) {
		// TODO Auto-generated method stub
		Button course = createCourse(week, firstCourseNum, lastCourseNum,c );
		courseContent.addView(course);
		//courseContent.invalidate(true);
	
	}

	// courseContent.removeView(view);

	public void Update(Course c) {

		View view = courseContent.findViewWithTag(c.toTag());
		if (view !=null) courseContent.removeView(view);
		setCourseContent(c.getCourseWeek(), c.getCourseJs() * 2 - 1,c.getCourseJs() * 2, c);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		// super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 1||resultCode == 2) {//�����γ�
			Course course = (Course) data.getSerializableExtra("course");
			Update(course);
		}
	}

}
