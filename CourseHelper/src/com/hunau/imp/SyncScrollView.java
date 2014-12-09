package com.hunau.imp;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

public class SyncScrollView extends ScrollView {
    private View mView;
	public SyncScrollView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public SyncScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public SyncScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
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
