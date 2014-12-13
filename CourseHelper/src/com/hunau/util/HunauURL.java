package com.hunau.util;

public interface HunauURL {
	
	/* URL数组格式：
	 *  内网
	 * 	外网
	 */
	//课程
	public static final String[] LOGIN={
		"http://172.28.89.32/loginAction.do",
		"http://220.169.45.174/loginAction.do"
	};	
	public static final String[] COURSE_LIST={
		"http://172.28.89.32/roamingAction.do?appId=BKS_XK",
		"http://220.169.45.174/roamingAction.do?appId=BKS_XK"
	};
	public static final String[] COURSE_TABLE = {
		"http://172.28.89.30:7777/pls/wwwbks/xk.CourseView",
		"http://220.169.45.173:7777/pls/wwwbks/xk.CourseView"
	};
	//成绩
	public static final String[] GRADE_LIST = {
		"http://172.28.89.30:7777/pls/wwwbks/bkscjcx.yxkc",
		"http://220.169.45.173:7777/pls/wwwbks/bkscjcx.yxkc"
	};
	public static final String[] GRADE_TABLE = {
		"http://172.28.89.30:7777/pls/wwwbks/bkscjcx.curscopre",
		"http://220.169.45.173:7777/pls/wwwbks/bkscjcx.curscopre"
	};
	public static final String[] GRADE_RANK ={
		"http://172.28.89.30:7777/pls/wwwbks/bkscjcx.cursco",
		"http://220.169.45.173:7777/pls/wwwbks/bkscjcx.cursco"
	};
	//评教
	public static final String[] URL_ASSESS_TEACHER_LIST={
		"http://172.28.89.32/roamingAction.do?appId=BKS_JXPG"
	};
	public static final String[] URL_ASSESS_TEACHER_TABLE={
		"http://172.28.89.30:8088/jxpg/list_wj.jsp"
	};
	//一键评教
	public static final String[] URL_ASSESS_TEACHER_SEND_BEFORE={
		"http://172.28.89.30:8088/jxpg/pg.jsp?wj_num="
	};
	public static final String[] URL_ASSESS_TEACHER_SEND={
		"http://172.28.89.30:8088/jxpg/answer.jsp?wj_num="
	};
	
	
	// 公网ip：220.169.45.174
//	public static final String url_grade_list = "http://220.169.45.174/roamingAction.do?appId=BKS_CJCX";
//	public static final String url_grade_table = "http://220.169.45.173:7777/pls/wwwbks/bkscjcx.curscopre";
//	public static final String url_allgrade_table="http://220.169.45.173:7777/pls/wwwbks/bkscjcx.yxkc";
//	public static final String url_grade_rank = "http://220.169.45.173:7777/pls/wwwbks/bkscjcx.cursco";
	
	//图书馆 外网
	public static final String LIBRARY_LOGIN = "http://61.187.55.41:8090/opac/dzjsjg.jsp";
	public static final String LIBRARY_LIST = "http://61.187.55.41:8090/opac/index_wdtsg.jsp";
	public static final String LIBRARY_BORROW="http://61.187.55.41:8090/opac/dzxj.jsp";
	
	public static final String url_getcontent = "http://222.240.147.182:8080/sztz/stud_home.php";
	public static final String url_getmoney = "http://172.28.180.94/homeLogin.action";
	
	
	public static final String[] COURSE_TYPE={"必修","选修" ,"限选","其他"};
	
	//public static final String[] COURSE_TYPE={"必修","选修" ,"限选","其他"};
	
	
}
