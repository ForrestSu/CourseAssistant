package com.hunau.db;

public class StuInfo {
	
	 private String stuname;  //学生姓名
	 private String stunumber;//学号
	 private double  grade;//当前选课的学分
	 
	public String getStuname() {
		return stuname;
	}
	public void setStuname(String stuname) {
		this.stuname = stuname;
	}
	public String getStunumber() {
		return stunumber;
	}
	public void setStunumber(String stunumber) {
		this.stunumber = stunumber;
	}
	public double getGrade() {
		return grade;
	}
	public void setGrade(double grade) {
		this.grade = grade;
	}
	
}
