package com.hunau.db;

import java.util.ArrayList;
import java.util.List;

import com.hunau.dao.Course;
import com.hunau.dao.Rank;
import com.hunau.dao.Score;
import com.hunau.dao.StuInfo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {

	private DBHelper helper;
	private SQLiteDatabase db;

	public DBManager(Context context) {
		helper = new DBHelper(context, "hunau.db", 1);
		db = helper.getWritableDatabase();
	}

	/************************************************************************/
	/** 1.课程操作 */
	/************************************************************************/
	// 添加所有课程数据
	public void addAllCourses(List<Course> courses) {
		db.beginTransaction(); // 开始事务
		try {
			for (Course c : courses) {
				db.execSQL(
						"INSERT INTO course VALUES(null,?,?, ?,?,?,?,?)",
						new Object[] { c.getCourseName(), c.getTeacher(),
								c.getType(), c.getPlace(), c.getCourseWeek(),
								c.getCourseJs(), c.getCourseZc() });
			}
			db.setTransactionSuccessful(); // 设置事务成功完成
		} finally {
			db.endTransaction(); // 结束事务
		}
	}

	// 查询所有课程数据
	public List<Course> queryAllCourses() {
		List<Course> courses = new ArrayList<Course>();
		Course cou;
		Cursor c = db.query("course", null, null, null, null, null,
				"coursejs asc,courseweek asc");
		while (c.moveToNext()) {
			cou = new Course();
		    cou.setId(c.getInt(c.getColumnIndex("id")));
			cou.setCourseName(c.getString(c.getColumnIndex("coursename")));
			cou.setTeacher(c.getString(c.getColumnIndex("teacher")));
			cou.setType(c.getInt(c.getColumnIndex("type")));
			cou.setCourseJs(c.getInt(c.getColumnIndex("coursejs")));
			cou.setCourseWeek(c.getInt(c.getColumnIndex("courseweek")));
			cou.setCourseZc(c.getString(c.getColumnIndex("coursezc")));
			cou.setPlace(c.getString(c.getColumnIndex("place")));
			courses.add(cou);
		}
		c.close();
		return courses;
	}
	public boolean UpdateCourse(Course c) {
		db.execSQL(
				"UPDATE course set coursename=?,teacher=?,type=?,place=?,"
				+ "courseweek =?,coursejs=?,coursezc=? where id=?",
				new Object[] { c.getCourseName(), c.getTeacher(),
						c.getType(), c.getPlace(), c.getCourseWeek(),
						c.getCourseJs(), c.getCourseZc(),c.getId() });
	   return true;
	}
	public boolean addCourse(Course c)
	{
		db.execSQL(
				"INSERT INTO course VALUES(null,?,?, ?,?,?,?,?)",
				new Object[] { c.getCourseName(), c.getTeacher(),
						c.getType(), c.getPlace(), c.getCourseWeek(),
						c.getCourseJs(), c.getCourseZc() });
		return true;
	}
	// 删除所有课程
	public void deleteAllCourse() {
		db.delete("course", null, null);
	}

	/************************************************************************/
	/** 2.成绩 操作 */
	/************************************************************************/
	// 添加所有成绩得分记录
	/************************************************************************/
	public void addAllScores(List<Score> scores) {
		db.beginTransaction(); // 开始事务
		try {
			for (Score s : scores) {
				db.execSQL(
						"INSERT INTO score VALUES(null,?,?,?,?,?,?,?,?,?)",
						new Object[] { s.getCourseNo(), s.getCourseName(),
								s.getXlh(), s.getCredit(), s.getTim(),
								s.getUsualCredit(), s.getFinalGrade(),
								s.getGrade(), s.getExamType() });
			}
			db.setTransactionSuccessful(); // 设置事务成功完成
		} finally {
			db.endTransaction(); // 结束事务
		}
	}

	// 查询所有成绩
	public List<Score> queryAllScores() {
		List<Score> scos = new ArrayList<Score>();
		Score sco = null;
		Cursor c = db.query("score", null, null, null, null, null, "id asc");
		while (c.moveToNext()) {
			sco = new Score();
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

	// 删除所有成绩
	public void deleteAllScores() {
		db.delete("score", null, null);
	}

	/************************************************************************/
	/** 3.成绩排名 */
	/************************************************************************/
	public void addAllRanks(List<Rank> ranks) {
		db.beginTransaction(); // 开始事务
		try {
			for (Rank s : ranks) {
				db.execSQL(
						"INSERT INTO ranks VALUES(null,?,?,?,?,?,?,?)",
						new Object[] { s.getCourseNo(), s.getCourseName(),
								s.getGrade(), s.getNum(), s.getHigh(),
								s.getLow(), s.getRank() });
			}
			db.setTransactionSuccessful(); // 设置事务成功完成
		} finally {
			db.endTransaction(); // 结束事务
		}
	}

	// 查询所有成绩
	public List<Rank> queryAllRanks() {
		List<Rank> scos = new ArrayList<Rank>();
		Rank sco = null;
		Cursor c = db.query("ranks", null, null, null, null, null, "id asc");
		while (c.moveToNext()) {
			sco = new Rank();
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

	// 删除所有排名
	public void deleteAllRanks() {
		db.delete("ranks", null, null);
	}

	/*********************************************************/
	/* 学生信息操作 */
	/*********************************************************/
	/************************************************************************/
	public void addStuInfo(StuInfo s) {
		db.execSQL("INSERT INTO stuinfo VALUES(null,?,?,?)",
				new Object[] { s.getStuname(), s.getStunumber(), s.getGrade() });
	}

	// 返回学生的信息
	public StuInfo queryStuInfo() {
		if (!db.isOpen()) {
			db = helper.getReadableDatabase();
		}
		StuInfo stu = null;
		Cursor c = db.query("stuinfo", null, null, null, null, null, "id asc");
		if (c.moveToNext()) {
			stu = new StuInfo();
			stu.setStuname(c.getString(c.getColumnIndex("stuname")));
			stu.setStunumber(c.getString(c.getColumnIndex("stunumber")));
			stu.setGrade(c.getDouble(c.getColumnIndex("grade")));
		}
		c.close();
		return stu;
	}

	public void deleteStuInfo() {
		db.delete("stuinfo", null, null);
	}

	/*********************************************************/
	/*********************************************************/
	// 5.关闭数据库
	public void closeDB() {
		if(db!=null)db.close();
	}
}
