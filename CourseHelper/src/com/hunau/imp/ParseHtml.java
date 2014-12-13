package com.hunau.imp;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.content.Context;

import com.hunau.dao.AssessTeacher;
import com.hunau.dao.BookInfo;
import com.hunau.dao.Course;
import com.hunau.dao.Rank;
import com.hunau.dao.Score;
import com.hunau.dao.StuInfo;
import com.hunau.db.DBManager;
import com.hunau.intface.Parse;
import com.hunau.util.HunauURL;

public class ParseHtml implements Parse {
	
	
	/********************************************************/
	/* 解析课表********************************************** */
	/********************************************************/
	
	public void AanalysisHtmlCourse(String html, Context context) {
		StuInfo stu = new StuInfo();
		List<Course> courses = new ArrayList<Course>();
		Course cou;
		String teacher[][] = new String[7][7];
		DBManager db = new DBManager(context);
		// ///下面开始解析课表
		Document doc = null;
		doc = Jsoup.parse(html );
		Elements data = doc.select(".td1");
		if(!data.isEmpty())
		{
			String title = data.first().text();
			String[] tokes = title.split("[(]|[)]|[ ]");
			stu.setStuname(tokes[2]);
			stu.setStunumber(tokes[3]);
			title = data.last().text();
			tokes = title.split("[ ]");
			stu.setGrade(Double.parseDouble(tokes[1]));
			
			// 1.首先清空数据库
			db.deleteStuInfo();
			db.addStuInfo(stu);
		}
		/*** 下面开始解析课程 */
		data = doc.select(".td_biaogexian >p");
		String result;
		// 提取49节课
		for (int i = 0; i < 49; i++) {
			result = getResult(data.get(i).text());
			if (result.contains("/")) {
				String[] tokens = result.split("/");
				teacher[i / 7][i % 7] = tokens[1];
			}
		}
		int length = data.size();
		for (int i = 49; i < length; i += 9) {
			cou = new Course();
			result = getResult(data.get(i).text());
			// 设置课程名
			cou.setCourseName(result);
			// 设置type
			result =data.get(i + 5).text();
			cou.setType(0);//默认 为0
			for(int k=0;k<HunauURL.COURSE_TYPE.length;k++)
			{
				if(result.contains(HunauURL.COURSE_TYPE[k]))
				{
					cou.setType(k);break;
				}
			}
			// 设置地点
			cou.setPlace(getResult(data.get(i + 6).text()));

			result = data.get(i + 7).text();
			// 设置周次
			if (result.length() > 2) {
				int zc = result.charAt(0) - '0';// col
				int js = result.charAt(2) - '0';// row
				cou.setCourseWeek(zc);
				cou.setCourseJs(js);
				// 设置teacher
				if (js > 0 && js < 8 && zc > 0 && zc < 8)
					cou.setTeacher(teacher[js - 1][zc - 1]);
			}
			// 设置Zc
			cou.setCourseZc(getResult(data.get(i + 8).text()));
			courses.add(cou);
		}
		if (courses.size() > 0) {
			db.deleteAllCourse();
			db.addAllCourses(courses);
		}
		if (db != null)
			db.closeDB();// //应用的最后应释放DB
	}

	public String getResult(String temp) {
		return temp.substring(0, temp.length() - 1);
	}

	/********************************************************/
	/* 解析图书********************************************** */
	/********************************************************/
	public List<BookInfo> AanalysisHtmlBook(String html) {
		List<BookInfo> books = new ArrayList<BookInfo>();
		BookInfo book;
		Document doc = null;
		doc = Jsoup.parse(html);
		Elements data = doc.getElementsByClass("bordertd");// 解析出class=zt5的标签

		int len = data.size();
		System.out.println(len);
		for (int i = 0; i < len; i += 13) {
			if (len - i < 12)
				break;
			book = new BookInfo();
			String code = data.get(i + 1).text();
			book.setCode(code.substring(0, code.length() - 1));
			book.setName(data.get(i + 2).text());
			book.setAuthor(data.get(i + 3).text());
			book.setLib(data.get(i + 5).text());
			book.setPosition(data.get(i + 6).text());
			book.setType(data.get(i + 7).text());
			book.setBortime(data.get(i + 8).text());// 借阅时间
			book.setRettime(getResult(data.get(i + 9).text()));
			book.setContime(data.get(i + 10).text());
			book.setTimes(data.get(i + 11).text());// 续借次数
			book.setState(data.get(i + 12).text());
			books.add(book);
		}
		return books;
	}

	@Override
	public void AanalysisHtmlRank(String html, Context context) {
		// TODO Auto-generated method stub
		List<Rank> ranks=new ArrayList<Rank>();
		 Rank  sco;
		Document doc=null;
		doc = Jsoup.parse(html);
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
		db.deleteAllRanks();
		db.addAllRanks(ranks);
		if(db!=null)db.closeDB();
	}
	@Override
	public void AanalysisHtmlScore(String html, Context context) {
		// TODO Auto-generated method stub
		List<Score> scores = new ArrayList<Score>();
		Score sco;
		Document doc=null;
		doc = Jsoup.parse(html);
		
		Elements data = doc.select(".td_biaogexian >p");
		String temp;
		int len = data.size();
		for (int i = 0; i < len; i += 11) {
			sco = new Score();
			sco.setCourseNo(data.get(i + 1).text());
			sco.setCourseName(data.get(i + 2).text());
			sco.setXlh(data.get(i + 3).text());
			temp = data.get(i + 4).text();
			sco.setCredit(Double.parseDouble(temp));
			sco.setTim(data.get(i + 5).text());
			sco.setUsualCredit(data.get(i + 6).text());
			sco.setFinalGrade(data.get(i + 7).text());
			sco.setGrade(data.get(i + 8).text());
			sco.setExamType(data.get(i + 11).text());
			scores.add(sco);
		}
		DBManager db = new DBManager(context);
		if (scores.size() > 0)
			db.deleteAllScores();
		db.addAllScores(scores);
		db.closeDB();
	}

	public String deal(List<Score> scores) {
		Score sco;
		String info = null;
		boolean flag = true;
		for (int i = 0; i < scores.size(); i++) {
			sco = scores.get(i);
			if (sco.getGrade().matches("100|[0-9]\\d{0,1}")) {
				String temp = "p_pm=" + sco.getCourseNo() + "++++++++"
						+ (10000 + Integer.parseInt(sco.getXlh()))
						+ sco.getTim() + sco.getGrade() + "++++++++"
						+ sco.getCourseName();
				if (flag) {
					info = temp;
					flag = false;
				} else
					info = info + "&" + temp;
			}
		}
		// System.out.println(info);
		// info="p_pm=20013B1++++++++100002013103188++++++++%B1%E0%D2%EB%D4%AD%C0%ED&p_pm=20022B0++++++++100012013103187++++++++%B2%D9%D7%F7%CF%B5%CD%B3&p_pm=20305B1++++++++100002013103192++++++++%C8%ED%BC%FE%B9%A4%B3%CC&p_pm=20638B3++++++++100002013103196++++++++JAVA%B3%CC%D0%F2%C9%E8%BC%C6%A3%A8%CB%AB%D3%EF%A3%A9&p_pm=40191B1++++++++100002013103181++++++++%CD%F8%C2%E7%B1%E0%B3%CC%BC%BC%CA%F5%BF%CE%B3%CC%C9%E8%BC%C6";
		return info;
	}

	@Override
	public List<AssessTeacher> AanalysisHtmlAssessTeacher(String html ) {
		// TODO Auto-generated method stub
		List<AssessTeacher> list=new ArrayList<AssessTeacher>();
		Document doc=Jsoup.parse(html);
		Elements es_trs=doc.getElementsByAttributeValue("bgcolor", "#F2EDF8");
		 for(int i=0;i<es_trs.size();i++)
		 {
			 Elements es_tds=es_trs.get(i).getElementsByTag("td");
				 AssessTeacher cpj=
				new AssessTeacher(es_tds.get(0).text(),
						es_tds.get(1).text(),
						es_tds.get(2).text(),
						es_tds.get(3).text(),
						es_tds.get(4).getElementsByTag("a").get(0).absUrl("href"), 
						i+"");
				// System.out.println(cpj);
				 list.add(cpj);
		 }
		return list;
	}
}
