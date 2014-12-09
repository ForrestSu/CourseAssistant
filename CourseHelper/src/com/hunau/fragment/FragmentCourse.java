package com.hunau.fragment;

import java.util.ArrayList;
import java.util.List;

import com.hunau.activity.MyCourseAddActivity;
import com.hunau.coursehelper.R;
import com.hunau.dao.Course;
import com.hunau.db.DBManager;
import com.hunau.imp.SyncHorizonScrollView;
import com.hunau.imp.SyncScrollView;

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
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.mycourselayout, null);

		// 获取数据
		dbManager = new DBManager(getActivity());
		courses = dbManager.queryAllCourses();

		// 设置底部添加按钮
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
		// 头部星期几
		GridView head = (GridView) view.findViewById(R.id.gridWeek);
		String[] weeks = { "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日" };
		ArrayAdapter<String> headAdap = new ArrayAdapter<String>(getActivity(),
				R.layout.weekstyle, weeks);
		head.setAdapter(headAdap);

		// 第几节课
		GridView courseNum = (GridView) view.findViewById(R.id.gridCourseNum);
		String[] courseNums = { "1", "2", "3", "4", "5", "6", "7", "8", "9",
				"10", "11", "12" };
		ArrayAdapter<String> leftAdap = new ArrayAdapter<String>(getActivity(),
				R.layout.coursenumstyle, courseNums);
		courseNum.setAdapter(leftAdap);

		courseContent = (RelativeLayout) view
				.findViewById(R.id.relativeCourseContent);
		ShowCourse();
		// Button course = createCourse(3, 1, 2, "高等数学@35-102");
		// Button course2 = createCourse(1, 2, 3, "android@36-111");
		// Button course3 = createCourse(5, 5, 7, "毛泽东思想与社会主义概论@34-101");
		// courseContent.addView(course);
		// courseContent.addView(course2);
		// courseContent.addView(course3);

		// 设置水平滑动
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
						c.getCourseJs() * 2, c.toString());
			}
	}

	// 创建课程
	private Button createCourse(int week, int firstCourseNum,
			int lastCourseNum, String content) {
		// 左边距
		int leftMargin = 0;
		// 上边距
		int topMargin = 0;
		// 课程的长度
		int courseLength = 0;
		// 一节课的宽度
		int courseWidth = (int) (getResources()
				.getDimension(R.dimen.course_week_item_width));
		// 一节课的高度
		int courseHeight = (int) (getResources()
				.getDimension(R.dimen.course_num_item_height));
		// week的高度
		int week_item_height = (int) (getResources()
				.getDimension(R.dimen.course_week_height));
		// courseNum的宽度
		int courseNum_width = (int) (getResources()
				.getDimension(R.dimen.course_num_width));
		// 行(列)间距
		int spacing = (int) (getResources().getDimension(R.dimen.spacing));
		// 算出左边距=左边有几节课*（课程宽度+1px的间隔）+左侧编号宽度
		leftMargin = (week - 1) * (courseWidth + spacing) + courseNum_width;
		// 上边距=上边有几节课*（课程高度+1px的间隔）+顶部星期的宽度
		topMargin = (firstCourseNum - 1) * (courseHeight + spacing)
				+ week_item_height;
		// 课程长度=课程节数*(课程高度+1px的间隔)-1个像素
		courseLength = (lastCourseNum - firstCourseNum + 1)
				* (courseHeight + spacing) - spacing;

		// 创建课程
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
		course.setBackgroundColor(getResources().getColor(R.color.courseNow));
		course.setText(content);
		course.setTextSize(15);
		course.setTextColor(Color.WHITE);
		return course;
	}

	@Override
	public void setCourseContent(int week, int firstCourseNum,
			int lastCourseNum, String content) {
		// TODO Auto-generated method stub
		Button course = createCourse(week, firstCourseNum, lastCourseNum,
				content);
		courseContent.addView(course);
		
	}
	//courseContent.removeView(view);

	public void Update(Course c) {

		View view = courseContent.findViewWithTag(c.toTag());
		if (view == null) {
			setCourseContent(c.getCourseWeek(), c.getCourseJs() * 2 - 1,
					c.getCourseJs() * 2, c.toString());
		} else {
			if (view instanceof Button) {
				Button bt = (Button) view;
				bt.setText(c.toString());
			}
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
	//	super.onActivityResult(requestCode, resultCode, data);
		if(resultCode==1)
		{
		Course course = (Course) data.getSerializableExtra("course");
		Update(course);
		
		}
	}

}
