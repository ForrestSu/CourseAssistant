package com.hunau.activity;


import com.hunau.coursehelper.R;
import com.hunau.dao.Course;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class EditCourse extends Activity{
	
	
	private TextView name;
	private TextView time;
	private TextView place;
	private TextView teacher;
	private TextView zc;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.courseaddlayout);
		
		Intent intent=getIntent();
		Course course=(Course)intent.getSerializableExtra("value"); 
//		
//		name=(TextView)this.findViewById(R.id.course_name);
//		time=(TextView)this.findViewById(R.id.course_time);
//		place=(TextView)this.findViewById(R.id.course_place);
//		teacher=(TextView)this.findViewById(R.id.course_teacher);
//		zc=(TextView)this.findViewById(R.id.course_zc);
//		
		if(course!=null){
			name.setText(course.getCourseName());
			place.setText(course.getPlace());
			teacher.setText(course.getTeacher());
			zc.setText(course.getCourseZc());
		}
		
		
	}
	
	
    protected void onDestroy() {
		super.onDestroy();
    }

}
