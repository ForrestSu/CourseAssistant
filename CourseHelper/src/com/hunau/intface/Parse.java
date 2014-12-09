package com.hunau.intface;

import java.io.InputStream;
import java.util.List;

import com.hunau.dao.BookInfo;

import android.content.Context;

public interface Parse {
	//解析课表
	public void AanalysisHtmlCourse(InputStream in,Context context);
	//解析图书
	public List<BookInfo> AanalysisHtmlBook(String  html);
	//解析成绩
	public void AanalysisHtmlScore(InputStream in,Context context);
	//解析排名
	public void AanalysisHtmlRank(InputStream in,Context context);
}
