package com.hunau.library;


public class Person {
	private String number;
	private String name;
	private String sex;
	private String xueyuan;
	private String year;
	private String cla;
	
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getXueyuan() {
		return xueyuan;
	}
	public void setXueyuan(String xueyuan) {
		this.xueyuan = xueyuan;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getCla() {
		return cla;
	}
	public void setCla(String cla) {
		this.cla = cla;
	}
	public String toString() {
		StringBuffer s=new StringBuffer();
		s.append("ÄúµÄ").append(number)
		 .append("\n").append(name).append(sex)
		 .append("\n").append(xueyuan)
		 .append("\n").append(cla).append(year);
		return s.toString();
	}
}
