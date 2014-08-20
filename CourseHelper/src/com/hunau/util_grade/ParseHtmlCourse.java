package com.hunau.util_grade;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.content.Context;

import com.hunau.db.Course;
import com.hunau.db.StuInfo;
import com.hunau.dbOper.DBManager;


public class ParseHtmlCourse {
	
	    
	    StuInfo stu=new StuInfo();
	    private  List<Course> courses=new ArrayList<Course>();
	    private  Course cou;
	    private HashMap<String, String> hm=new HashMap<String, String>();
     	public void AanalysisHtml(InputStream in,Context context) throws IOException {
     		
     		
     		Document doc = Jsoup.parse(in,"gb2312","http://www.baidu.com");
			
     		Elements data =doc.select(".td1");
     		String title=data.first().text();
     		String []tokes=title.split("[(]|[)]|[ ]");
     		
     		stu.setStuname(tokes[2]);
     		stu.setStunumber(tokes[3]);
     		title=data.last().text();
     		tokes=title.split("[ ]");
     		stu.setGrade(Double.parseDouble(tokes[1]));
     		DBManager db=new DBManager(context);
     		//1.����������ݿ�
     		db.deleteAll();
     		db.addStuInfo(stu);
     		
     		/***���濪ʼ�����γ�  */
     		 data =doc.select(".td_biaogexian >p");
     		String result;
     		//��ȡ49�ڿ�
      		for (int i=0;i<49;i++) {
      			 result=getResult( data.get(i).text() );
      			if(result.contains("/"))
      			 parse(result);
      		}
      		int length=data.size();
      		for(int i=49;i<length;i+=9)
      		{
      			cou=new Course();
      			result=getResult( data.get(i).text() );
      			//���ÿγ���
      			cou.setCourseName(result);
      			//����teacher
      			if(hm.containsKey(result))
      				cou.setTeacher(hm.get(result) );
      			else break;
      			//����type
      			result=getResult( data.get(i+5).text() );
      			cou.setType(getResult( data.get(i+5).text()) );
      			//���õص�
      			cou.setPlace(getResult( data.get(i+6).text()));
      			
      			result=data.get(i+7).text();
      			//�����ܴ�
      			if(result.length()>2)
      			{
      				int zc=result.charAt(0)-'0';
      				int js=result.charAt(2)-'0';
      				cou.setCourseWeek(zc);
      				cou.setCourseJs(js);
      			}
      			//����Zc
      			cou.setCourseZc(getResult( data.get(i+8).text()));
      			courses.add(cou);
      		}
     		db.addCourses(courses);
     		db.closeDB();////Ӧ�õ����Ӧ�ͷ�DB 
	    }
     	public String getResult(String temp)
     	{
     		return temp.substring(0, temp.length()-1);
     	}

    	public  void parse(String result){
     		if(result.contains(" "))
  			{
  				String []tokens=result.split("[ ]");
  				for(String one:tokens)
  				{
  					String[] t=one.split("/");
  					String a=t[0].substring(0,t[0].length()-4);
  					if(hm.containsKey(a)==false)
  					{
  						//System.out.println(a+"-->"+t[1]);
  					   hm.put(a, t[1]);
  					}
  				}
  		  }
       }
		
}

