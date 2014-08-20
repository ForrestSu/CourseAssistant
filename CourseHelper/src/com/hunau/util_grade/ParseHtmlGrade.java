package com.hunau.util_grade;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


import android.content.Context;

import com.hunau.db.Score;
import com.hunau.dbOper.DBManager;

public class ParseHtmlGrade {

	private  List<Score> scores=new ArrayList<Score>();
	private Score sco;
	public void AanalysisHtml(InputStream in,Context context) throws IOException {

 		Document doc = Jsoup.parse(in,"gb2312","http://www.baidu.com");
		
 		Elements data =doc.select(".td_biaogexian >p");
 		String temp;
 		int len=data.size();
 		for(int i=0;i<len;i+=13)
 		{
 			sco=new Score();
 			sco.setCourseNo(data.get(i+1).text());
 			sco.setCourseName(data.get(i+2).text());
 			sco.setXlh(data.get(i+3).text());
 			temp=data.get(i+4).text();
 			sco.setCredit(Double.parseDouble(temp));
 			sco.setTim(data.get(i+5).text());
 			sco.setUsualCredit(data.get(i+6).text());
 			sco.setFinalGrade(data.get(i+7).text());
 			sco.setGrade(data.get(i+8).text());
 			sco.setExamType(data.get(i+11).text());
 			scores.add(sco);
 		}
 		DBManager db=new DBManager(context);
 		db.deleteScores();
 		db.addScores(scores);
 		db.closeDB();
	}
	 public String deal()throws Exception
	  {
	    	Score sco;
		    String info=null;
		    boolean flag=true;
		    for(int i=0;i<scores.size();i++)
		    {
		    	sco=scores.get(i);
		    	if(sco.getGrade().matches("100|[0-9]\\d{0,1}"))
		    	{
		    		String temp="p_pm="+sco.getCourseNo()+"++++++++"+(10000+Integer.parseInt(sco.getXlh()))+sco.getTim()+sco.getGrade()+
		    				"++++++++"+sco.getCourseName();
		    		if(flag){info=temp;flag=false;}
		    		else info=info+"&"+temp;
		    	}
		    }
		   // System.out.println(info);
		   // info="p_pm=20013B1++++++++100002013103188++++++++%B1%E0%D2%EB%D4%AD%C0%ED&p_pm=20022B0++++++++100012013103187++++++++%B2%D9%D7%F7%CF%B5%CD%B3&p_pm=20305B1++++++++100002013103192++++++++%C8%ED%BC%FE%B9%A4%B3%CC&p_pm=20638B3++++++++100002013103196++++++++JAVA%B3%CC%D0%F2%C9%E8%BC%C6%A3%A8%CB%AB%D3%EF%A3%A9&p_pm=40191B1++++++++100002013103181++++++++%CD%F8%C2%E7%B1%E0%B3%CC%BC%BC%CA%F5%BF%CE%B3%CC%C9%E8%BC%C6";
		   return info;
	  }

	
}
