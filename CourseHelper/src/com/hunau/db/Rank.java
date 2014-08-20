package com.hunau.db;


public class Rank {
	private String courseNo; //课程号
	private String courseName; //课程名称
	private String grade ;//你的成绩
	private String num; //选课人数
	private String high;//最高分
	private String low; //最低分 
	private String rank; //排名
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
		s.append("你的排名:第 ").append(rank)
        .append(" 名\n你的成绩:").append(grade)
        .append("\n选课人数:").append(num)
        .append("人\n最高分:").append(high);
		return s.toString();
	}
	
}
