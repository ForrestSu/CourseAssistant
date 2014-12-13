package com.hunau.intface;

import java.util.List;

import com.hunau.dao.AssessTeacher;
import com.hunau.dao.BookInfo;

import android.content.Context;

public interface Parse {
	//解析课表
	public void AanalysisHtmlCourse(String html,Context context);
	//解析图书
	public List<BookInfo> AanalysisHtmlBook(String  html);
	//解析成绩
	public void AanalysisHtmlScore(String html,Context context);
	//解析排名
	public void AanalysisHtmlRank(String html,Context context);
	//解析评教
	public List<AssessTeacher> AanalysisHtmlAssessTeacher(String html);
}
