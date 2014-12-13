package com.hunau.dao;

public class AssessTeacher {
//41842210 
	private  String comment; //问卷名称
	private String teacherName;//教师姓名
	private String courseName;//评估内容
	private String status;//是否已经评估
	private String url;
	public	String order;
	public AssessTeacher(){}
	public String getCourseName() {
		return courseName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public AssessTeacher(String comment, String teacherName, String courseName,
			String status, String url, String order) {
		this.comment = comment;
		this.teacherName = teacherName;
		this.courseName = courseName;
		this.status = status;
		this.url = url;
		this.order = order;
	}
	
	@Override
	public String toString() {
		return  "任课教师:"+teacherName+"\n"+ "已评:"+status;
	}
}
