package com.hunau.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hunau.coursehelper.R;
import com.hunau.dao.Rank;
import com.hunau.db.DBManager;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class ShowRank extends Activity {

	
	private DBManager db;
	TextView  tv;
	public void onCreate(Bundle savedInstanceState) {  
	    super.onCreate(savedInstanceState);  
	    setContentView(R.layout.showgrade);  
	    tv=(TextView)findViewById(R.id.bar2);
	    tv.setText("�������");
	    //��XML�е�ListView����ΪItem������  
	    ListView list = (ListView) findViewById(R.id.showgpre);  
	    db=new DBManager(ShowRank.this); 
	    //���ɶ�̬���飬����ת������  
	   
	    List<Rank> r=db.queryAllRanks(); ; 
	    
	    ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();  
	    for(Rank o:r)  
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

