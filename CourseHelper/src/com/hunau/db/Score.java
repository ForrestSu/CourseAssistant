package com.hunau.db;

public class Score {
	private String courseNo; //课程号
	private String courseName; //课程名称
	private String xlh;       //课序号
	private double credit;   //学分
	private String tim;     //考试时间
	private String usualCredit;//平时成绩
	private String finalGrade; //期末成绩
	private String grade;  //总成绩
	private String examType;//考试方式
	
	
	public String getTim() {
		return tim;
	}
	public void setTim(String tim) {
		this.tim = tim;
	}
	public String getCourseNo() {
		return courseNo;
	}
	public void setCourseNo(String courseNo) {
		this.courseNo = courseNo;
	}
	
	
	public String getXlh() {
		return xlh;
	}
	public void setXlh(String xlh) {
		this.xlh = xlh;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public double getCredit() {
		return credit;
	}
	public void setCredit(double credit) {
		this.credit = credit;
	}
	public String getUsualCredit() {
		return usualCredit;
	}
	public void setUsualCredit(String usualCredit) {
		this.usualCredit = usualCredit;
	}
	public String getFinalGrade() {
		return finalGrade;
	}
	public void setFinalGrade(String finalGrade) {
		this.finalGrade = finalGrade;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getExamType() {
		return examType;
	}
	public void setExamType(String examType) {
		this.examType = examType;
	}
	public String toString()
	{
		return ("总成绩:"+ grade+"\n"+
  		       "平时成绩:"+usualCredit+"\n"+
			   "考试成绩:"+finalGrade);
	}
}

