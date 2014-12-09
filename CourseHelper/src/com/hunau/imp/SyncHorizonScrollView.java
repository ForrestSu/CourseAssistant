package com.hunau.imp;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;

public class SyncHorizonScrollView extends HorizontalScrollView {

	 private View mView;
	public SyncHorizonScrollView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public SyncHorizonScrollView(Context context, AttributeSet attrs) {  
		        super(context, attrs);  
		        // TODO Auto-generated constructor stub  
	 }
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		// TODO Auto-generated method stub
		super.onScrollChanged(l, t, oldl, oldt);
		if(mView!=null)
		{
			mView.scrollTo(l, t);  
		}
	}
	public void setAnotherView(View v)
	{
		mView=v;
	}
}
