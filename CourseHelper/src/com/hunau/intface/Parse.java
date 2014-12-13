package com.hunau.intface;

import java.util.List;

import com.hunau.dao.AssessTeacher;
import com.hunau.dao.BookInfo;

import android.content.Context;

public interface Parse {
	//�����α�
	public void AanalysisHtmlCourse(String html,Context context);
	//����ͼ��
	public List<BookInfo> AanalysisHtmlBook(String  html);
	//�����ɼ�
	public void AanalysisHtmlScore(String html,Context context);
	//��������
	public void AanalysisHtmlRank(String html,Context context);
	//��������
	public List<AssessTeacher> AanalysisHtmlAssessTeacher(String html);
}
