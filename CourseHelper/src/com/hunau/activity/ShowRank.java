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
	    tv.setText("你的排名");
	    //绑定XML中的ListView，作为Item的容器  
	    ListView list = (ListView) findViewById(R.id.showgpre);  
	    db=new DBManager(ShowRank.this); 
	    //生成动态数组，并且转载数据  
	   
	    List<Rank> r=db.queryAllRanks(); ; 
	    
	    ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();  
	    for(Rank o:r)  
	    {  
	        HashMap<String, String> map = new HashMap<String, String>();  
	        map.put("tv1",o.getCourseName() );  
	        map.put("tv2",o.toString());  
	        mylist.add(map);  
	    }  
	    //生成适配器，数组===》ListItem  
	    SimpleAdapter mSchedule = new SimpleAdapter(this, //没什么解释  
	                                                mylist,//数据来源   
	                                                R.layout.showgradepre,//ListItem的XML实现  
	                                                  
	                                                //动态数组与ListItem对应的子项          
	                                                new String[] {"tv1", "tv2"},   
	                                                  
	                                                //ListItem的XML文件里面的两个TextView ID  
	                                                new int[] {R.id.tv1,R.id.tv2});  
	    //添加并且显示  
	    list.setAdapter(mSchedule);  
	}  
	@Override  
    protected void onDestroy() {
		super.onDestroy();
        //应用的这个Activity关闭时应释放DB  
        db.closeDB();  
    } 

}  

