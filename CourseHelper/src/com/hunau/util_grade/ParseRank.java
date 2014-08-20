package com.hunau.util_grade;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.content.Context;

import com.hunau.db.Rank;
import com.hunau.dbOper.DBManager;


public class ParseRank {

	private  List<Rank> ranks=new ArrayList<Rank>();
	private Rank  sco;
	public void AanalysisHtml(InputStream in,Context context) throws IOException {

 		Document doc = Jsoup.parse(in,"GBK","http://www.baidu.com");
		
 		Elements data =doc.select(".td_biaogexian >p");
 		int len=data.size();
 		for(int i=0;i<len;i+=7)
 		{
 			sco=new Rank();
 			sco.setCourseNo(data.get(i).text());
 			sco.setCourseName(data.get(i+1).text());
 			sco.setGrade(data.get(i+2).text());
 			sco.setNum(data.get(i+3).text());
 			sco.setHigh(data.get(i+4).text());
 			sco.setLow(data.get(i+5).text());
 			sco.setRank(data.get(i+6).text());
 			ranks.add(sco);
 		}
 		DBManager db=new DBManager(context);
 		db.deleteRank();
 		db.addRank(ranks);
 		db.closeDB();
	}
	
}
