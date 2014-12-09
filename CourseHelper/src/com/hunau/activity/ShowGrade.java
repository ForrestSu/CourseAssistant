package com.hunau.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hunau.coursehelper.R;
import com.hunau.dao.Score;
import com.hunau.db.DBManager;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;


public class ShowGrade extends Activity {

	
	private DBManager db;
	public void onCreate(Bundle savedInstanceState) {  
	    super.onCreate(savedInstanceState);  
	    setContentView(R.layout.showgrade);  
	    //��XML�е�ListView����ΪItem������  
	    ListView list = (ListView) findViewById(R.id.showgpre);  
	    db=new DBManager(ShowGrade.this); 
	    //���ɶ�̬���飬����ת������  
	    List<Score> score=db.queryAllScores(); 
	    
	    ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();  
	    for(Score o:score)  
	    {  
	        HashMap<String, String> map = new HashMap<String, String>();  
	        map.put("tv1",o.getCourseName() );  
	        map.put("tv2",o.toString());  
	        mylist.add(map);  
	    }  
	    //����������������===��ListItem  
	    SimpleAdapter mSchedule = new SimpleAdapter(this, //ûʲô����  
	                                                mylist,//������Դ   
	                                                R.layout.showgradepre,//ListItem��XMLʵ��  
	                                                  
	                                                //��̬������ListItem��Ӧ������          
	                                                new String[] {"tv1", "tv2"},   
	                                                  
	                                                //ListItem��XML�ļ����������TextView ID  
	                                                new int[] {R.id.tv1,R.id.tv2});  
	    //��Ӳ�����ʾ  
	    list.setAdapter(mSchedule);  
	}  
	@Override  
    protected void onDestroy() {
		super.onDestroy();
        //Ӧ�õ����Activity�ر�ʱӦ�ͷ�DB  
        db.closeDB();  
    } 

}  

