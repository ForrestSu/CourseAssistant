package com.hunau.dao;


public class Rank {
	private String courseNo; //�γ̺�
	private String courseName; //�γ�����
	private String grade ;//��ĳɼ�
	private String num; //ѡ������
	private String high;//��߷�
	private String low; //��ͷ� 
	private String rank; //����
	public String getCourseNo() {
		return courseNo;
	}
	public void setCourseNo(String courseNo) {
		this.courseNo = courseNo;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getHigh() {
		return high;
	}
	public void setHigh(String high) {
		this.high = high;
	}
	public String getLow() {
		return low;
	}
	public void setLow(String low) {
		this.low = low;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public String toString()
	{
		StringBuffer s=new StringBuffer();
		s.append("�������:�� ").append(rank)
        .append(" ��\n��ĳɼ�:").append(grade)
        .append("\nѡ������:").append(num)
        .append("��\n��߷�:").append(high);
		return s.toString();
	}
	
}
