package com.hunau.dao;

public class Score {
	private String courseNo; //�γ̺�
	private String courseName; //�γ�����
	private String xlh;       //�����
	private double credit;   //ѧ��
	private String tim;     //����ʱ��
	private String usualCredit;//ƽʱ�ɼ�
	private String finalGrade; //��ĩ�ɼ�
	private String grade;  //�ܳɼ�
	private String examType;//���Է�ʽ
	
	
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
		return ("�ܳɼ�:"+ grade+"\n"+
  		       "ƽʱ�ɼ�:"+usualCredit+"\n"+
			   "���Գɼ�:"+finalGrade);
	}
}

