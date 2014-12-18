package com.slidingmenu.main;

import com.hunau.coursehelper.R;
import com.hunau.dao.StuInfo;
import com.hunau.db.DBManager;
import com.hunau.fragment.*;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

import android.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainMenuSlidingActivity extends SlidingFragmentActivity implements
		OnClickListener {

	private SlidingMenu mSlidingMenu;
	private Fragment rightFragment;
	private Fragment fragment;
	private TextView name;
	private TextView motto;
	private ImageView loginstate;

	private DBManager db;
	private RelativeLayout coursePageLayout;
	private RelativeLayout userInfoLayout;
	private RelativeLayout learnedCoursePageLayout;
	private RelativeLayout gradeLayout;
	private RelativeLayout rankLayout;
	private RelativeLayout bookLayout;
	private RelativeLayout aboutusLayout;
	private RelativeLayout funAssessTeacher;
	private RelativeLayout funcOther;
	private RelativeLayout funcQueryPower;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 设置侧边栏效果
		mSlidingMenu = getSlidingMenu();
		mSlidingMenu.setMode(SlidingMenu.LEFT);// 设置是左滑还是右滑，还是左右都可以滑，这里是左滑
		mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);// 设置手势模式，按住边缘滑动
		mSlidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);// 设置阴影宽度
		mSlidingMenu.setFadeDegree(0.35f);// 设置淡入淡出的比例
		mSlidingMenu.setShadowDrawable(R.drawable.shadow);// 设置左菜单阴影图片
		mSlidingMenu.setFadeEnabled(true);// 设置滑动时菜单的是否淡入淡出
		mSlidingMenu.setBehindScrollScale(0.0f);// 设置滑动时拖拽效果
		// mSlidingMenu.setBehindWidth(540); // 设置左侧菜单宽度

		// 设置左边的内容
		setBehindContentView(R.layout.main_left_menu_layout);
		// 生成一个
		rightFragment = new FragmentCourse();
		// 重置右边内容
		getFragmentManager().beginTransaction()
				.replace(R.id.contanior, rightFragment).commit();

		// 初始化本页面内容
		initSlidingMenu();
		// 更新用户信息
		updateUserInfo();
		// 添加右侧容器
		setContentView(R.layout.rightcontent);
	}

	private void initSlidingMenu() {
		// TODO Auto-generated method stub
		name = (TextView) findViewById(R.id.your_name);
		motto = (TextView) findViewById(R.id.your_motto);
		loginstate = (ImageView) findViewById(R.id.login_logout);
         
		loginstate.setOnClickListener(this);
		// 用户信息
		userInfoLayout = (RelativeLayout) findViewById(R.id.mainPageUserInfo);
		// 课程表
		coursePageLayout = (RelativeLayout) findViewById(R.id.mainPageCourse);
		// 已修课程
		learnedCoursePageLayout = (RelativeLayout) findViewById(R.id.mainPageLearnedCourse);
		// 本期成绩
		gradeLayout = (RelativeLayout) findViewById(R.id.mainPageGrade);
		// 本学期成绩排名
		rankLayout = (RelativeLayout) findViewById(R.id.mainPageRank);
		// 图书馆
		bookLayout = (RelativeLayout) findViewById(R.id.mainPageLibrary);
		// 关于我们
		aboutusLayout = (RelativeLayout) findViewById(R.id.mainPageAboutUs);
		//一键评教
		funAssessTeacher=(RelativeLayout) findViewById(R.id.mainFuncAssessTeacher);
		//余电查询
		funcOther=(RelativeLayout) findViewById(R.id.mainFuncOther);
		//其他
		funcQueryPower=(RelativeLayout) findViewById(R.id.mainFunPower);
		
		userInfoLayout.setOnClickListener(this);
		coursePageLayout.setOnClickListener(this);
		learnedCoursePageLayout.setOnClickListener(this);
		gradeLayout.setOnClickListener(this);
		rankLayout.setOnClickListener(this);
		bookLayout.setOnClickListener(this);
		aboutusLayout.setOnClickListener(this);
		funAssessTeacher.setOnClickListener(this);
		funcQueryPower.setOnClickListener(this);
		funcOther.setOnClickListener(this);
	}

	public void updateUserInfo() {
		db = new DBManager(this);
		StuInfo stu = db.queryStuInfo();
		if (stu != null)// 设置个人信息
		{
			name.setText(stu.getStuname() + " " + stu.getStunumber());
			motto.setText("本期学分：" + stu.getGrade());
			loginstate.setImageDrawable(getResources().getDrawable(R.drawable.top_logout));
		}
		if (db != null)
			db.closeDB();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.mainPageUserInfo:
//			fragment = new FragmentCourse();
//			// 重置右边内容
//			getFragmentManager().beginTransaction()
//					.replace(R.id.contanior, fragment).commit();
//			// 收放效果
//			toggle();
			break;
		case R.id.mainPageCourse:
			showCourseTable();
			toggle();// 收放效果
			break;
		case R.id.mainPageLearnedCourse:
			//已修课程
			Toast.makeText(this, "查询已修课程功能正在开发", 0).show();
			
			break;
		case R.id.mainPageGrade:
			//本期成绩
			Toast.makeText(this, "查询本期成绩功能正在开发", 0).show();
			
			break;
		case R.id.mainPageRank:
			//成绩排名
			Toast.makeText(this, "查询成绩排名功能正在开发", 0).show();
			
			break;
		case R.id.mainPageLibrary:
			fragment = new FragmentBook();
			// 图书馆
			getFragmentManager().beginTransaction()
					.replace(R.id.contanior, fragment).commit();
			toggle();// 收放效果
			break;
		case R.id.mainPageAboutUs:
			fragment = new FragmentAbout();
			// 关于我们
			getFragmentManager().beginTransaction()
					.replace(R.id.contanior, fragment).commit();
			toggle();// 收放效果
			break;
			
		////FUNCtion
		case R.id.mainFuncAssessTeacher:
			fragment = new FragmentAssesssTeacher();
			// 重置右边内容
			getFragmentManager().beginTransaction()
					.replace(R.id.contanior, fragment).commit();
			toggle();// 收放效果
			break;
		
	 	case R.id.mainFunPower:
           Toast.makeText(this, "余电查询功能正在开发", 0).show();
			break;
	 	case R.id.mainFuncOther:
	        Toast.makeText(this, "新功能正在开发", 0).show();
				break;
				
		/////////////////////////////////////////////////////		
	 	case R.id.login_logout:
			fragment = new FragmentLogin();
			// 重置右边内容
			getFragmentManager().beginTransaction()
					.replace(R.id.contanior, fragment).commit();
			toggle();// 收放效果
			break;
		}
	}
	public void showCourseTable()
	{
		fragment = new FragmentCourse();
		// 重置右边内容
		getFragmentManager().beginTransaction()
				.replace(R.id.contanior, fragment).commit();
		
	}
}
