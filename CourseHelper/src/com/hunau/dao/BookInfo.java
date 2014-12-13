package com.hunau.dao;

import java.io.Serializable;

public class BookInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code;   // 单册条码
	private String name;   //正 题 名
	private String author; //作者
	private String type;   //单册类型
	private String position;//典藏地
	private String bortime;//借书时间
	private String rettime;//应还时间
	private String contime;//续借时间
	private String times;//续借次数
	private String lib;//馆藏单位
	private String state;//状态
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getBortime() {
		return bortime;
	}
	public void setBortime(String bortime) {
		this.bortime = bortime;
	}
	public String getRettime() {
		return rettime;
	}
	public void setRettime(String rettime) {
		this.rettime = rettime;
	}
	public String getContime() {
		return contime;
	}
	public void setContime(String contime) {
		this.contime = contime;
	}
	public String getTimes() {
		return times;
	}
	public void setTimes(String times) {
		this.times = times;
	}
	public String getLib() {
		return lib;
	}
	public void setLib(String lib) {
		this.lib = lib;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	/*public String toString()
	{
		return code+"  "+name;
	}
    */
}
