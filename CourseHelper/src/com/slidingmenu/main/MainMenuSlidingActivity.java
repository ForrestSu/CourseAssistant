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

		// ���ò����Ч��
		mSlidingMenu = getSlidingMenu();
		mSlidingMenu.setMode(SlidingMenu.LEFT);// �������󻬻����һ����������Ҷ����Ի�����������
		mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);// ��������ģʽ����ס��Ե����
		mSlidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);// ������Ӱ���
		mSlidingMenu.setFadeDegree(0.35f);// ���õ��뵭���ı���
		mSlidingMenu.setShadowDrawable(R.drawable.shadow);// ������˵���ӰͼƬ
		mSlidingMenu.setFadeEnabled(true);// ���û���ʱ�˵����Ƿ��뵭��
		mSlidingMenu.setBehindScrollScale(0.0f);// ���û���ʱ��קЧ��
		// mSlidingMenu.setBehindWidth(540); // �������˵����

		// ������ߵ�����
		setBehindContentView(R.layout.main_left_menu_layout);
		// ����һ��
		rightFragment = new FragmentCourse();
		// �����ұ�����
		getFragmentManager().beginTransaction()
				.replace(R.id.contanior, rightFragment).commit();

		// ��ʼ����ҳ������
		initSlidingMenu();
		// �����û���Ϣ
		updateUserInfo();
		// ����Ҳ�����
		setContentView(R.layout.rightcontent);
	}

	private void initSlidingMenu() {
		// TODO Auto-generated method stub
		name = (TextView) findViewById(R.id.your_name);
		motto = (TextView) findViewById(R.id.your_motto);
		loginstate = (ImageView) findViewById(R.id.login_logout);
         
		loginstate.setOnClickListener(this);
		// �û���Ϣ
		userInfoLayout = (RelativeLayout) findViewById(R.id.mainPageUserInfo);
		// �γ̱�
		coursePageLayout = (RelativeLayout) findViewById(R.id.mainPageCourse);
		// ���޿γ�
		learnedCoursePageLayout = (RelativeLayout) findViewById(R.id.mainPageLearnedCourse);
		// ���ڳɼ�
		gradeLayout = (RelativeLayout) findViewById(R.id.mainPageGrade);
		// ��ѧ�ڳɼ�����
		rankLayout = (RelativeLayout) findViewById(R.id.mainPageRank);
		// ͼ���
		bookLayout = (RelativeLayout) findViewById(R.id.mainPageLibrary);
		// ��������
		aboutusLayout = (RelativeLayout) findViewById(R.id.mainPageAboutUs);
		//һ������
		funAssessTeacher=(RelativeLayout) findViewById(R.id.mainFuncAssessTeacher);
		//����ѯ
		funcOther=(RelativeLayout) findViewById(R.id.mainFuncOther);
		//����
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
		if (stu != null)// ���ø�����Ϣ
		{
			name.setText(stu.getStuname() + " " + stu.getStunumber());
			motto.setText("����ѧ�֣�" + stu.getGrade());
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
//			// �����ұ�����
//			getFragmentManager().beginTransaction()
//					.replace(R.id.contanior, fragment).commit();
//			// �շ�Ч��
//			toggle();
			break;
		case R.id.mainPageCourse:
			showCourseTable();
			toggle();// �շ�Ч��
			break;
		case R.id.mainPageLearnedCourse:
			//���޿γ�
			Toast.makeText(this, "��ѯ���޿γ̹������ڿ���", 0).show();
			
			break;
		case R.id.mainPageGrade:
			//���ڳɼ�
			Toast.makeText(this, "��ѯ���ڳɼ��������ڿ���", 0).show();
			
			break;
		case R.id.mainPageRank:
			//�ɼ�����
			Toast.makeText(this, "��ѯ�ɼ������������ڿ���", 0).show();
			
			break;
		case R.id.mainPageLibrary:
			fragment = new FragmentBook();
			// ͼ���
			getFragmentManager().beginTransaction()
					.replace(R.id.contanior, fragment).commit();
			toggle();// �շ�Ч��
			break;
		case R.id.mainPageAboutUs:
			fragment = new FragmentAbout();
			// ��������
			getFragmentManager().beginTransaction()
					.replace(R.id.contanior, fragment).commit();
			toggle();// �շ�Ч��
			break;
			
		////FUNCtion
		case R.id.mainFuncAssessTeacher:
			fragment = new FragmentAssesssTeacher();
			// �����ұ�����
			getFragmentManager().beginTransaction()
					.replace(R.id.contanior, fragment).commit();
			toggle();// �շ�Ч��
			break;
		
	 	case R.id.mainFunPower:
           Toast.makeText(this, "����ѯ�������ڿ���", 0).show();
			break;
	 	case R.id.mainFuncOther:
	        Toast.makeText(this, "�¹������ڿ���", 0).show();
				break;
				
		/////////////////////////////////////////////////////		
	 	case R.id.login_logout:
			fragment = new FragmentLogin();
			// �����ұ�����
			getFragmentManager().beginTransaction()
					.replace(R.id.contanior, fragment).commit();
			toggle();// �շ�Ч��
			break;
		}
	}
	public void showCourseTable()
	{
		fragment = new FragmentCourse();
		// �����ұ�����
		getFragmentManager().beginTransaction()
				.replace(R.id.contanior, fragment).commit();
		
	}
}
