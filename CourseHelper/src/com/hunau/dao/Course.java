package com.hunau.dao;

import java.io.Serializable;


public class Course implements Serializable{
	 
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int  id;
     private String courseName; //课程名称
     private String teacher;   //教师
     private int type;      //考试类型
     private String place;     //上课地点
	 private  int  courseWeek;   //上课星期几
     private  int courseJs;   //上课第几节
     private String courseZc;   //上课周次
     public String toTag()
     {
    	 return courseWeek+""+courseJs;
     }
     
     public int getId() {
 		return id;
 	}
 	public void setId(int id) {
 		this.id = id;
 	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getTeacher() {
		return teacher;
	}
	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public int getCourseWeek() {
		return courseWeek;
	}
	public void setCourseWeek(int courseWeek) {
		this.courseWeek = courseWeek;
	}
	public int getCourseJs() {
		return courseJs;
	}
	public void setCourseJs(int courseJs) {
		this.courseJs = courseJs;
	}
	public String getCourseZc() {
		return courseZc;
	}
	public void setCourseZc(String courseZc) {
		this.courseZc = courseZc;
	}
    
	public String toString()
	{
		return courseName+"/"+teacher;
	}
}

