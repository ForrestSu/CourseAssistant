package com.slidingmenu.main;

 

import com.hunau.coursehelper.R;
import com.hunau.fragment.*;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

import android.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;


public class MainSlidingActivity extends SlidingFragmentActivity {

	private SlidingMenu mSlidingMenu;
	private ImageView Iv;
	private TextView mTitle;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		initSlidingMenu();
		setContentView(R.layout.activity_main);
		Iv=(ImageView) findViewById(R.id.showleft);
		
		Iv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mSlidingMenu.showMenu(true);
			}
		});
		
		FragmentCourse fragment = new FragmentCourse();
		getFragmentManager().beginTransaction()
		.replace(R.id.contanior, fragment).commit();
		mTitle=(TextView)findViewById(R.id.title);
		mSlidingMenu.showMenu(true);
	}
	private FragmentLeftMenu mMenuFragment;

	private void initSlidingMenu() {
		// TODO Auto-generated method stub
	  setBehindContentView(R.layout.main_left_layout);
	  mMenuFragment = new FragmentLeftMenu();
	  getFragmentManager().beginTransaction().
	  replace(R.id.main_left_fragment, mMenuFragment).commit();
		mSlidingMenu=getSlidingMenu();		
		mSlidingMenu.setMode(SlidingMenu.LEFT);// �������󻬻����һ����������Ҷ����Ի���������ֻ������
		mSlidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);// ������Ӱ���
		mSlidingMenu.setFadeDegree(0.35f);// ���õ��뵭���ı���
		mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);//��������ģʽ
		mSlidingMenu.setShadowDrawable(R.drawable.shadow);// ������˵���ӰͼƬ
		mSlidingMenu.setFadeEnabled(true);// ���û���ʱ�˵����Ƿ��뵭��
		mSlidingMenu.setBehindScrollScale(0.0f);// ���û���ʱ��קЧ��
		//mSlidingMenu.setBehindWidth(540); // �������˵����
	}
	
	
	public void changeContent(Fragment fragment)
	{
	
		if(fragment instanceof FragmentBook)
		{
			mTitle.setText("���߽���");
		}
		
		if(fragment instanceof FragmentCourse)
		{
			mTitle.setText("��14��");
		}
		if(fragment instanceof FragmentLogin)
		{
			mTitle.setText("�û���¼");
		}
		getFragmentManager().beginTransaction()
		.replace(R.id.contanior, fragment).commit();
		//�շ�Ч��
		toggle();
	}

	public void updateUserInfo() {
		// TODO Auto-generated method stub
		mMenuFragment.updateUserInfo();
	}
	
}
