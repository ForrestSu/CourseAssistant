package com.hunau.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
	
	private static final int VERSION = 1;
	public DBHelper(Context context, String dbname, CursorFactory factory,int version) {
		super(context, dbname, factory, version);
	}
	public DBHelper(Context context,String dbname){
		this(context,dbname,VERSION);
	}
	public DBHelper(Context context,String dbname,int version){
		this(context, dbname,null,version);
	}

	
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("DROP TABLE IF EXISTS course");
		db.execSQL("DROP TABLE IF EXISTS stuinfo");
		db.execSQL("DROP TABLE IF EXISTS score");
		db.execSQL("DROP TABLE IF EXISTS rank");
		db.execSQL("CREATE TABLE course (id INTEGER PRIMARY KEY AUTOINCREMENT,coursename varchar,teacher varchar," +
				"type  INTEGER,place varchar,courseweek INTEGER,coursejs INTEGER,coursezc varchar)");
		db.execSQL("CREATE TABLE stuinfo (id INTEGER PRIMARY KEY AUTOINCREMENT,stuname varchar, stunumber varchar,grade REAL)");
		db.execSQL("CREATE TABLE score (id INTEGER PRIMARY KEY AUTOINCREMENT,courseNo varchar,courseName varchar,xlh varchar, credit REAL,tim varchar," +
				"usualCredit varchar,finalGrade varchar, grade varchar,examType varchar)");
		db.execSQL("CREATE TABLE ranks (id INTEGER PRIMARY KEY AUTOINCREMENT,courseNo varchar,courseName varchar,grade varchar, num varchar,high varchar," +
				"low varchar,rank varchar)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.i("数据库", "版本更新..."); 
	}

}