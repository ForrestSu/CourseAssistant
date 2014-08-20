package com.hunau.fragment;

import java.util.ArrayList;
import java.util.List;

import com.hunau.coursehelper.R;
import com.hunau.db.Course;
import com.hunau.dbOper.DBManager;
import com.hunau.ui.EditCourse;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class PageFragment1 extends Fragment {

	private GridView gridView;
	private DBManager dbManager;
	private List<Course> courses = new ArrayList<Course>();

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.page1, null);
		gridView = (GridView) view.findViewById(R.id.gridviewcourse);
		return view;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		dbManager = new DBManager(getActivity());
		courses = dbManager.query();

		gridView.setAdapter(new BaseInfoAdapter(getActivity(), courses));
		gridView.setOnItemClickListener(new ItemClickListener(courses,getActivity()));
	}

}

class BaseInfoAdapter extends BaseAdapter {

	private Context context;
	private String[] text = new String[35];

	public BaseInfoAdapter(Context mcontext, List<Course> courses) {
		this.context = mcontext;
		for (int i = 0, j = 0; i < 35 && j < courses.size(); i++)
			if (i == courses.get(j).getId()) {
				text[i] = courses.get(j).toString();
				j++;
				while (j<courses.size()&&courses.get(j).getId() == i) {
					text[i] += courses.get(j).toString();
					j++;
				}
			}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 35;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {

		TextView tv;
		if (view == null) {
			tv = new TextView(context);
			tv.setBackgroundColor(Color.WHITE);
			tv.setGravity(Gravity.CENTER);
			tv.setLayoutParams(new GridView.LayoutParams(56, 70));
		} else {
			tv = (TextView) view;
		}

		tv.setText(text[position]);
		return tv;
	}
}
class ItemClickListener implements OnItemClickListener{

	private List<Course> courses;
	private Context context;
	public ItemClickListener(){}
	public ItemClickListener(List<Course> courses,Context context) {
		this.courses=courses;
		this.context=context;
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
		Course cou=null;
		for(int i=0;i<courses.size();i++)
			if(courses.get(i).getId()==position)
			{
				cou=courses.get(i);
			}
		if(cou!=null){
		Intent intent = new Intent(context,EditCourse.class);
        intent.putExtra("value", cou);
        context.startActivity(intent);
		}
		//Log.i("TAG", "ÄúÑ¡ÔñÁË"+position);
		
	}
	
	
}
