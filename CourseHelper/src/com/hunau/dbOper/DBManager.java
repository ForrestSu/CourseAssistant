package com.hunau.dbOper;

import java.util.ArrayList;
import java.util.List;

import com.hunau.db.Course;
import com.hunau.db.DBHelper;
import com.hunau.db.Score;
import com.hunau.db.StuInfo;
import com.hunau.db.Rank;

import android.R.integer;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class DBManager {
    
	 private DBHelper helper;
	 private SQLiteDatabase db; 
	 public DBManager(Context context) {  
	        helper = new DBHelper(context,"hunau.db",1);  
	        db = helper.getWritableDatabase();  
	    }  
	 public void addCourses(List<Course> courses) {  
	        db.beginTransaction();  //开始事务  
	        try {  
	            for (Course c : courses) {  
	                db.execSQL("INSERT INTO course VALUES(null,?,?, ?,?,?,?,?)", 
	                		new Object[]{c.getCourseName(), c.getTeacher(),
	                		c.getType(),c.getPlace(),c.getCourseWeek(),
	                		c.getCourseJs(),c.getCourseZc()});  
	            }  
	            db.setTransactionSuccessful();  //设置事务成功完成  
	        } finally {  
	            db.endTransaction();    //结束事务  
	        }  
	    }
	
	
	 //查询所有课程数据
	 public List<Course> query() {  
		 List<Course> courses=new ArrayList<Course>(); 
		 Course cou;
	     Cursor c = db.query("course", null, null, null, null, null, "courseJs asc,courseWeek asc"); 
	     while(c.moveToNext()) {  
	        	cou = new Course();  
	        	cou.setCourseName(c.getString(c.getColumnIndex("courseName"))); 
	        	cou.setTeacher(c.getString(c.getColumnIndex("teacher")));
	            cou.setCourseJs(c.getInt(c.getColumnIndex("courseJs")) );
	            cou.setCourseWeek(c.getInt(c.getColumnIndex("courseWeek")));
	            cou.setCourseZc(c.getString(c.getColumnIndex("courseZc")));
	            cou.setPlace(c.getString(c.getColumnIndex("place")));
	            int id=(cou.getCourseJs()-1)*7+cou.getCourseWeek()-1;
		        cou.setId(id);
	            courses.add(cou);
	        }
	     c.close();  
	   	return courses;
    } 
	 
/************************************************************************/
	 public void addScores(List<Score> scores) {  
	        db.beginTransaction();  //开始事务  
	        try {  
	            for (Score s : scores) {  
	                db.execSQL("INSERT INTO score VALUES(null,?,?,?,?,?,?,?,?,?)", 
	                		new Object[]{s.getCourseNo(),s.getCourseName(), s.getXlh(),s.getCredit(),s.getTim(),
	                		s.getUsualCredit(),s.getFinalGrade(),s.getGrade(),s.getExamType()});  
	            }  
	            db.setTransactionSuccessful();  //设置事务成功完成  
	        } finally {  
	            db.endTransaction();    //结束事务  
	        }  
	    }
	 //查询所有成绩
	 public List<Score> queryScore(){
		 List<Score> scos=new ArrayList<Score>();
		 Score sco=null;
		 Cursor c = db.query("score", null, null, null, null, null, "id asc");
		 while(c.moveToNext())
		 { 
			 sco=new Score();
			 sco.setCourseNo(c.getString(c.getColumnIndex("courseNo")));
			 sco.setCourseName(c.getString(c.getColumnIndex("courseName")));
			 sco.setXlh(c.getString(c.getColumnIndex("xlh")));
			 sco.setTim(c.getString(c.getColumnIndex("tim")));
			 sco.setGrade(c.getString(c.getColumnIndex("grade")));
			 sco.setUsualCredit(c.getString(c.getColumnIndex("usualCredit")));
			 sco.setFinalGrade(c.getString(c.getColumnIndex("finalGrade")));
			 scos.add(sco);
		 }
		c.close();
		
		return scos;		 
	 }
	 public void deleteScores(){
		 db.delete("score", null,null );
	 }
	 
/************************************************************************/
	 
	 /************************************************************************/
	 public void addRank(List<Rank> ranks) {  
	        db.beginTransaction();  //开始事务  
	        try {  
	            for (Rank s : ranks) {  
	                db.execSQL("INSERT INTO ranks VALUES(null,?,?,?,?,?,?,?)", 
	                		new Object[]{s.getCourseNo(),s.getCourseName(), s.getGrade(),s.getNum(),s.getHigh(),
	                		s.getLow(),s.getRank()});  
	            }  
	            db.setTransactionSuccessful();  //设置事务成功完成  
	        } finally {  
	            db.endTransaction();    //结束事务  
	        }  
	    }
	 //查询所有成绩
	 public List<Rank> queryRank(){
		 List<Rank> scos=new ArrayList<Rank>();
		 Rank sco=null;
		 Cursor c = db.query("ranks", null, null, null, null, null, "id asc");
		 while(c.moveToNext())
		 { 
			 sco=new Rank();
			 sco.setCourseNo(c.getString(c.getColumnIndex("courseNo")));
			 sco.setCourseName(c.getString(c.getColumnIndex("courseName")));
			 sco.setGrade(c.getString(c.getColumnIndex("grade")));
			 sco.setNum(c.getString(c.getColumnIndex("num")));
			 sco.setHigh(c.getString(c.getColumnIndex("high")));
			 sco.setLow(c.getString(c.getColumnIndex("low")));
			 sco.setRank(c.getString(c.getColumnIndex("rank")));
			 scos.add(sco);
		 }
		c.close();
		return scos;		 
	 }
	 public void deleteRank(){
		 db.delete("ranks", null,null );
	 }
	 
/************************************************************************/
	 public void addStuInfo(StuInfo s) {  
         db.execSQL("INSERT INTO stuinfo VALUES(null,?,?,?)", 
         new Object[]{s.getStuname(), s.getStunumber(),s.getGrade()});  	      
	 } 
	 //返回学生的信息
	 public StuInfo queryStuInfo(){
		 StuInfo stu=null;
		 Cursor c = db.query("stuinfo", null, null, null, null, null, "id asc");
		 if(c.moveToNext())
		 { 
		   stu=new StuInfo();
		   stu.setStuname(c.getString(c.getColumnIndex("stuname")));
		   stu.setStunumber(c.getString(c.getColumnIndex("stunumber")));
		   stu.setGrade(c.getDouble(c.getColumnIndex("grade")));
		 }
		c.close();
		
		return stu;		 
	 }
	 
/*********************************************************/
/*********************************************************/
	//删除表中所有数据
	 public void deleteAll() {
		 
		 db.delete("course", null,null );
		 db.delete("stuinfo", null,null );
	 }
	 public void closeDB() {  
	        db.close();  
	    }  
}
