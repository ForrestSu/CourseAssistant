package com.hunau.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.hunau.coursehelper.R;
import com.hunau.dao.BookInfo;
import com.hunau.imp.ParseHtml;
import com.hunau.intface.Parse;
import com.hunau.util.HunauURL;
import com.hunau.util.Mysharepreference;
import com.hunau.util.SimuLogin;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MyBookBorrowActivity extends Activity {

	private Mysharepreference s;
	private List<BookInfo> books=new ArrayList<BookInfo>();
	private ListView mlistView;
	private TextView onekeyBorrow;
	private SimuLogin l;
	private LinearLayout vLoad_Layout; // 进度条显示
	private TextView txt;
	private String name, pass;
	public static final int Wrong_Name_Pass = -1;
	public static final int Network_Not_Reach = -2;
	public static final int Success = 0;
	public static final String[] left = { "条码号", "责任者", "单册类型", "典藏地", "借阅时间", "应还时间", "续借时间","续借次数","馆藏单位","状态" };
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case Wrong_Name_Pass:
				vLoad_Layout.setVisibility(View.GONE);// 隐藏进度条
				String text = (msg.arg1 == 0) ? "读者账号不存在" : "读者密码错误";
				txt.setText(text);
				break;
			case Network_Not_Reach:
				vLoad_Layout.setVisibility(View.GONE);// 隐藏进度条
				txt.setText("无法访问图书馆网站");
				break;
			case Success:
				vLoad_Layout.setVisibility(View.GONE);// 隐藏进度条
				String text1 = msg.arg1 == 0 ? "无借阅记录" : "已借阅册数：" + msg.arg1
						+ " 本";
				txt.setText(text1);
				if (msg.arg1 > 0) {
					showBookList();// 更新显示
					OneKeyBorrowAble();// 一键续借可用
				}
				break;
			case 2:
				if (msg.arg1 > 0)
					showBookList();// 更新显示
				Toast.makeText(MyBookBorrowActivity.this, msg.obj.toString(),
						Toast.LENGTH_LONG).show();
				break;
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// TODO Auto-generated method stub
		setContentView(R.layout.my_book);
		mlistView = (ListView) findViewById(R.id.showbook);

		vLoad_Layout = (LinearLayout) findViewById(R.id.loading);
		vLoad_Layout.setVisibility(View.INVISIBLE);
		txt = (TextView) findViewById(R.id.booknum);
		onekeyBorrow = (TextView) findViewById(R.id.onekeyborrow);
		onekeyBorrow.setVisibility(View.INVISIBLE);
		ImageView back = (ImageView) findViewById(R.id.showleft);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MyBookBorrowActivity.this.finish();
			}
		});
		s = new Mysharepreference(this);
		name = getIntent().getExtras().getString("name");
		pass = getIntent().getExtras().getString("pass");
		LoginSuccess();
	}

	private void LoginSuccess() {
		vLoad_Layout.setVisibility(View.VISIBLE);
		Thread thread = new Thread() {
			public void run() {
				Message msg = new Message();
				l = new SimuLogin();
				String sendmsg = "dztm=" + name + "&dzmm=" + pass;
				String html = l.login(HunauURL.LIBRARY_LOGIN, sendmsg, true);
				if (l.isLoginFail(html, "请重新输入")) {
					msg.what = Wrong_Name_Pass;
					msg.arg1 = 0;
					if (html.contains("错误"))
						msg.arg1 = 1;
					handler.sendMessage(msg);
					return;
				}
				html = l.login(HunauURL.LIBRARY_LIST, null, false);
				if (html == null) {
					msg.what = Network_Not_Reach;
					handler.sendMessage(msg);
					return;
				}
				Parse ph = new ParseHtml();
				books = ph.AanalysisHtmlBook(html);
				msg.what = Success;
				msg.arg1 = (books == null ? 0 : books.size());
				handler.sendMessage(msg);
			}
		};
		thread.start();
	}

	private void showBookList() {
		// 绑定XML中的ListView，作为Item的容器
		if (books == null || books.size() == 0)return;
		mlistView.setAdapter(adapter);
		

	}

	private BaseAdapter adapter = new BaseAdapter() {
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			View view = LayoutInflater.from(MyBookBorrowActivity.this).inflate(
					R.layout.my_book_item_layout, null);
			BookInfo b=books.get(books.size()-position-1);
			((TextView)view.findViewById(R.id.book_name)).setText("  "+b.getName());
			((TextView)view.findViewById(R.id.serial)).setText(""+(position+1));
			TextView stime=(TextView)view.findViewById(R.id.surplus_time);
		    stime.setText(getReturnDays(b.getRettime()));
			//先设置左侧标题
			GridView title = (GridView) view.findViewById(R.id.left_title);
			ArrayAdapter<String> leftAdapter = new ArrayAdapter<String>(MyBookBorrowActivity.this,
					R.layout.my_book_contentstyle, left);
			//title.set
			title.setAdapter(leftAdapter);
			// 设置右侧内容
			GridView content = (GridView) view.findViewById(R.id.right_content);
			String[] right = new String[] { b.getCode(), b.getAuthor(), b.getType(),b.getPosition()
					,b.getBortime(),b.getRettime(), b.getContime() ,b.getTimes(), b.getLib() ,b.getState()};
			ArrayAdapter<String> rightAdapter  = new ArrayAdapter<String>(MyBookBorrowActivity.this,
					R.layout.my_book_contentstyle, right);
			content.setAdapter(rightAdapter);
			return view;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return books.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}
	};
    public String getReturnDays(String old)
    {
    	String type="yyyy-MM-dd hh:mm:ss";
		if(old.matches("[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}"))
	    {
	    	 SimpleDateFormat df =new SimpleDateFormat(type);
	    	 try {
				Date retDate=df.parse(old);
				Date nowDate=new Date(); 
				if(nowDate.before(retDate))
				{
					return daysBetween(nowDate,retDate)+"天";
				}else {
					return "过期"+daysBetween(retDate,nowDate)+"天";
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
	    }
		return null;
    }
	/**
	 * @param smdate Date  small
	 * @param bdate Date   big
	 * @return 获取两个日期之间相差天数
	 */
	public static int daysBetween(Date smdate,Date bdate) 
    {    
        Calendar cal = Calendar.getInstance();    
        cal.setTime(smdate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(bdate);    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
        return Integer.parseInt(String.valueOf(between_days));           
    }    

	// 一键续借
	public void OneKeyBorrowAble() {
		onekeyBorrow.setVisibility(View.VISIBLE);
		onekeyBorrow.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// /新开一个线程
				Thread thread = new Thread() {
					public void run() {
						int flag = 0, real = 0;
						String text;
						for (BookInfo b : books)
							// 这里引用可以改变b所在空间的对象内容
							if (b.getTimes().contains("0")) {
								flag++;
								// 如果续借成功
								String sendmsg = "dztm=" + name + "&dctm="
										+ b.getCode();
								if (null != l.login(HunauURL.LIBRARY_BORROW,
										sendmsg, false)) {
									b.setTimes("1");
									real++;
								}
							}
						if (flag == 0)
							text = "您当前没有书可以续借";
						else
							text = flag + "本可以续借," + real + "本续借成功";
						Message msg = new Message();
						msg.what = 2;
						msg.arg1 = real;
						msg.obj = text;
						handler.sendMessage(msg);
					}
				};
				thread.start();
			}
		});
	}

}
