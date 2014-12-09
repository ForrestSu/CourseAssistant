package com.hunau.activity;


import com.hunau.coursehelper.R;
import com.slidingmenu.main.MainSlidingActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class StartActivity extends Activity {
	private Handler handler=new Handler();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start_view);
	}
	//跳转到主页面
	class GotoMainView implements Runnable{
		@Override
		public void run() {
			Intent intent = new Intent(StartActivity.this, MainSlidingActivity.class);
			StartActivity.this.startActivity(intent);
			StartActivity.this.finish();
		}
	}
	
	@Override
	protected void onStart() {
		super.onPause();
			handler.postDelayed(new GotoMainView(), 1500);
		}
}
