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
		mSlidingMenu.setMode(SlidingMenu.LEFT);// 设置是左滑还是右滑，还是左右都可以滑，我这里只做了左滑
		mSlidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);// 设置阴影宽度
		mSlidingMenu.setFadeDegree(0.35f);// 设置淡入淡出的比例
		mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);//设置手势模式
		mSlidingMenu.setShadowDrawable(R.drawable.shadow);// 设置左菜单阴影图片
		mSlidingMenu.setFadeEnabled(true);// 设置滑动时菜单的是否淡入淡出
		mSlidingMenu.setBehindScrollScale(0.0f);// 设置滑动时拖拽效果
		//mSlidingMenu.setBehindWidth(540); // 设置左侧菜单宽度
	}
	
	
	public void changeContent(Fragment fragment)
	{
	
		if(fragment instanceof FragmentBook)
		{
			mTitle.setText("读者借阅");
		}
		
		if(fragment instanceof FragmentCourse)
		{
			mTitle.setText("第14周");
		}
		if(fragment instanceof FragmentLogin)
		{
			mTitle.setText("用户登录");
		}
		getFragmentManager().beginTransaction()
		.replace(R.id.contanior, fragment).commit();
		//收放效果
		toggle();
	}

	public void updateUserInfo() {
		// TODO Auto-generated method stub
		mMenuFragment.updateUserInfo();
	}
	
}
