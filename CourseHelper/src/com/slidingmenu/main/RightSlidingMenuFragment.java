package com.slidingmenu.main;

import com.hunau.coursehelper.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class RightSlidingMenuFragment extends Fragment implements OnClickListener{
  
	
     @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	
    }
     
     @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
    	 View view = inflater.inflate(R.layout.left, container,
 				false);
 
    	 
 		System.out.println();
    	return view;
    }

	@Override
	public void onClick(View v) {
		Fragment newContent = null;

	//	if (newContent != null)
		//switchFragment(newContent);
		
	}
	private void switchFragment(Fragment fragment) {
		if (getActivity() == null)
			return;	
		MainSlidingActivity ra = (MainSlidingActivity) getActivity();
		//	ra.switchContent(fragment);
		
	}
}
