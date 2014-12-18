package com.hunau.dao;

public class AssessTeacher {
//41842210 
	private  String comment; //问卷名称
	private String teacherName;//教师姓名
	private String courseName;//评估内容
	private String status;//是否已经评估
	private String url;
	private	String order;
	private  String score;//评分值 1.0, 0.8 etc
	public AssessTeacher(){score="1.0";}
	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getCourseName() {
		return courseName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTeacherName() {
		return teacherName;
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
}
