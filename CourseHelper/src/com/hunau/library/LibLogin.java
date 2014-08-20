package com.hunau.library;




import com.hunau.accessinternet.isConnect_Internet;
import com.hunau.coursehelper.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LibLogin extends Activity {
	
	  
	  private EditText tv;
	  private Button submit;
	  private Mysharepreference s;
		@Override
	  protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.login_lib);
			
			tv=(EditText)findViewById(R.id.lib_name);
			s=new  Mysharepreference(LibLogin.this);
			String text=s.getMessage("stuno");
			tv.setText(text);
			submit=(Button)findViewById(R.id.submit);
			//person=(Button)findViewById(R.id.person);
			//info=(TextView)findViewById(R.id.info);

			submit.setOnClickListener(new OnClickListener() {  
	            @Override  
	            public void onClick(View v) {
	            	 String name=tv.getText().toString();//如果text为空，那么返回值不为null，是一个空字符串
	         		  s.saveMessage("stuno",name);//存储
	            	 if(!isConnect_Internet.isConnect(LibLogin.this)){
	     	        	Toast.makeText(LibLogin.this, "亲,请连接网络!",Toast.LENGTH_SHORT).show(); 
	            	 }
	     	         else if(name.length()==0)
	     	        	Toast.makeText(LibLogin.this,"学号不能为空",Toast.LENGTH_SHORT).show();
	     	         else 
	     	         {
	     	        	 Intent intent =new Intent(LibLogin.this, ShowBooks.class);
	 		             intent.putExtra("name", name);
	 			   	     startActivityForResult(intent, 0);
	     	         }
	     	       
	            } 
	        });
		
		}
		
		

}
