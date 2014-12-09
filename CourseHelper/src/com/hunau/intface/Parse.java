package com.hunau.intface;

import java.io.InputStream;
import java.util.List;

import com.hunau.dao.BookInfo;

import android.content.Context;

public interface Parse {
	//�����α�
	public void AanalysisHtmlCourse(InputStream in,Context context);
	//����ͼ��
	public List<BookInfo> AanalysisHtmlBook(String  html);
	//�����ɼ�
	public void AanalysisHtmlScore(InputStream in,Context context);
	//��������
	public void AanalysisHtmlRank(InputStream in,Context context);
}
