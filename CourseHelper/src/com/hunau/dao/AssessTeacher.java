package com.hunau.dao;

public class AssessTeacher {
//41842210 
	private  String comment; //�ʾ�����
	private String teacherName;//��ʦ����
	private String courseName;//��������
	private String status;//�Ƿ��Ѿ�����
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
		return  "�ον�ʦ:"+teacherName+"\n"+ "����:"+status;
	}
}
