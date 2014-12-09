package com.hunau.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.hunau.coursehelper.R;
import com.hunau.dao.BookInfo;
import com.hunau.imp.ParseHtml;
import com.hunau.intface.Parse;
import com.hunau.util.HunauURL;
import com.hunau.util.Mysharepreference;
import com.hunau.util.SimuLogin;


public class FragmentBook extends Fragment {

 
	private Activity mActivity;

	List<BookInfo> books;
	SimuLogin l;
	private LinearLayout vLoad_Layout; // 进度条显示
	TextView txt;
	Button btn;
	String name, pass;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				vLoad_Layout.setVisibility(View.GONE);// 隐藏进度条
				Toast.makeText(mActivity, "Login Failed!", Toast.LENGTH_SHORT)
						.show();
				break;
			case 2:
				vLoad_Layout.setVisibility(View.GONE);// 隐藏进度条
				String text= msg.arg1==0?"无借阅记录":"已借阅册数：" + msg.arg1 + " 本";
				txt.setText(text);
				if (msg.arg1 > 0) {
					setComponent();
					setResponse();
				}
				break;
			case 3:
				if (msg.arg1 > 0)
					setComponent();// 更新显示
				Toast.makeText(mActivity, msg.obj.toString(), Toast.LENGTH_LONG)
						.show();
				break;
			}
		}
	};

	private Mysharepreference s;

	public void onAttach(android.app.Activity activity) {

		mActivity = activity;
		super.onAttach(activity);
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.book, container, false);
		list = (ListView) view.findViewById(R.id.showbook);

		vLoad_Layout = (LinearLayout) view.findViewById(R.id.loading);
		vLoad_Layout.setVisibility(View.VISIBLE);
		txt = (TextView) view.findViewById(R.id.booknum);
		btn = (Button) view.findViewById(R.id.btn);

		s = new Mysharepreference(getActivity());
		String stuno = s.getMessage("stuno");
		String password = s.getMessage("libpassword");
		if (stuno == null || password == null) {
			LibLoginDialog ldDialog=new LibLoginDialog(getActivity());
			ldDialog.show();
		}
		else {
			name = stuno;
			pass = password;
			// btn.setVisibility(false);
			Thread thread = new Thread() {
				public void run() {
					// 这里下载数据
					int re = getSource();// 获得图书本数
					if (re == 404) {
						Message msg = new Message();
						msg.what = 1;
						handler.sendMessage(msg);
					} else {
						Message msg = new Message();
						msg.what = 2;
						msg.arg1 = re;// 将图书本数传回去
						handler.sendMessage(msg);
					}
				}
			};
			thread.start();
		}
		return view;
	}

	public void setResponse() {

		btn.setOnClickListener(new OnClickListener() {
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
								String sendmsg="dztm="+name+"&dctm="+b.getCode();
								if (null!=l.login(HunauURL.LIBRARY_BORROW,sendmsg, false)) {
									b.setTimes("1");
									real++;
								}
							}
						if (flag == 0)
							text = "您当前没有书可以续借";
						else
							text = flag + "本可以续借," + real + "本续借成功";
						Message msg = new Message();
						msg.what = 3;
						msg.arg1 = real;
						msg.obj = text;
						handler.sendMessage(msg);
					}
				};
				thread.start();
			}
		});
	}

	ListView list;

	public void setComponent() {
		// 绑定XML中的ListView，作为Item的容器

		ArrayList<HashMap<String, String>> lists = new ArrayList<HashMap<String, String>>();
		for (BookInfo b : books) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("tv1", b.getName());
			map.put("tv2", "条形码：" + b.getCode() + "\n作者：" + b.getAuthor()
					+ "\n典藏地：" + b.getPosition() + "\n续借次数：" + b.getTimes()
					+ "\n借阅时间：" + b.getBortime() + "\n应还时间：" + b.getRettime()
					+ "\n续借时间：" + b.getContime() + "\n馆藏单位：" + b.getLib()
					+ "\n状态：" + b.getState());
			lists.add(map);
		}
		// 生成适配器，数组===》ListItem
		SimpleAdapter mSchedule = new SimpleAdapter(mActivity, // 没什么解释
				lists,// 数据来源
				R.layout.book_content,// ListItem的XML实现

				// 动态数组与ListItem对应的子项
				new String[] { "tv1", "tv2" },

				// ListItem的XML文件里面的两个TextView ID
				new int[] { R.id.tv1, R.id.tv2 });
		// 添加并且显示
		list.setAdapter(mSchedule);
	}

	protected int getSource() {
		l = new SimuLogin();
		String in = null;
		try {
			
			String msg="dztm="+name+"&dzmm="+pass;
			l.login(HunauURL.LIBRARY_LOGIN,msg);
			in=l.getContent(l.login(HunauURL.LIBRARY_LIST,null,false));
			if (in == null)return 404;// 登录失败404
			Parse ph = new ParseHtml();
			books = ph.AanalysisHtmlBook(in);
			return books.size();// 返回书本数
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	class LibLoginDialog extends Dialog{
		
	public 	LibLoginDialog(Context context)
		{
			super(context);
		}
	
	@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			
			setContentView(R.layout.user_login_lib);
			
			final EditText Ename = (EditText) this.findViewById(R.id.name);
			final EditText Epass = (EditText) this.findViewById(R.id.pass);
			s = new Mysharepreference(mActivity);
			String textname = s.getMessage("name");
			String textpass = s.getMessage("libpassword");
			Ename.setText(textname);
			Epass.setText(textpass);
			// 添加监听事件
			Button submit = (Button) this.findViewById(R.id.submit);

			submit.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					pass = Epass.getEditableText().toString();
					name = Ename.getEditableText().toString();
					s.saveMessage("stuno", name);
					s.saveMessage("libpassword", pass);
					
					Thread thread = new Thread() {
						public void run() {
							// 这里下载数据
							int re = getSource();// 获得图书本数
							if (re == 404) {
								Message msg = new Message();
								msg.what = 1;
								handler.sendMessage(msg);
							} else {
								Message msg = new Message();
								msg.what = 2;
								msg.arg1 = re;// 将图书本数传回去
								handler.sendMessage(msg);
							}
						}
					};
					thread.start();
					LibLoginDialog.this.dismiss();
				}
			});
		}
		
	}
}
