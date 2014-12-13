package com.hunau.fragment;

import com.hunau.coursehelper.R;
import com.slidingmenu.main.MainMenuSlidingActivity;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class FragmentAbout extends Fragment {
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.about_us, null);
		// …Ë÷√≤Àµ•∞¥≈•
		ImageView broadSideBtn = (ImageView) view.findViewById(R.id.showleft);
		broadSideBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				MainMenuSlidingActivity ac = (MainMenuSlidingActivity) getActivity();
				ac.toggle();
			}
		});
		return view;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
}
