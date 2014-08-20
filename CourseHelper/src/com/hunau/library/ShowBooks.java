package com.hunau.library;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hunau.coursehelper.R;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ShowBooks extends Activity {

	List<BookInfo> books;
	LibSimuLogin l;
	private LinearLayout vLoad_Layout; // 进度条显示
	TextView txt;
	Button btn;
	String name;
	int re=0;
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				vLoad_Layout.setVisibility(View.GONE);//隐藏进度条
				Toast.makeText(ShowBooks.this, "图书馆系统正在维护...", Toast.LENGTH_SHORT)
				.show();
				break;
			case 2:
				vLoad_Layout.setVisibility(View.GONE);//隐藏进度条
				txt.setText("已借阅册数：" + re + " 本");
				if (re > 0) {
					setComponent();
					setResponse();
				}
				break;
			}
		}
	};
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.book);
		vLoad_Layout = (LinearLayout) this.findViewById(R.id.loading);
		vLoad_Layout.setVisibility(View.VISIBLE);
		txt = (TextView) findViewById(R.id.booknum);
		btn = (Button) findViewById(R.id.btn);
		name = getIntent().getExtras().getString("name");
       // btn.setVisibility(false);
		Thread thread = new Thread() {
			public void run() {
				// 这里下载数据
					re = getSource();
					if (re == 404) {
						Message msg = new Message();
						msg.what = 1;
						handler.sendMessage(msg);
					} else {
						Message msg = new Message();
						msg.what = 2;
						handler.sendMessage(msg);
					}
				}
		};
		thread.start();

	}
	protected int getSource() {
		l = new LibSimuLogin();
		InputStream in = null;
		try {
			in = l.login(name);
			if (in == null)
				return 404;// 登录失败404
			LibParseHtml ph = new LibParseHtml();
			books = ph.AanalysisHtml(in);
			return books.size();// 返回书本数
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public void setResponse() {

		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int flag = 0, real = 0;
				String text;
				for (BookInfo b : books)
					// 这里引用可以改变b所在空间的对象内容
					if (b.getTimes().contains("0")) {
						flag++;
						// 如果续借成功
						if (l.borrow(name, b.getCode())) {
							b.setTimes("1");
							real++;
						}
					}
				if (flag == 0)
					text = "您当前没有书可以续借";
				else
					text = flag + "本可以续借," + real + "本续借成功";
				if (real > 0)
					setComponent();// 更新显示
				Toast.makeText(ShowBooks.this, text, Toast.LENGTH_LONG).show();
			}
		});

	}

	public void setComponent() {
		// 绑定XML中的ListView，作为Item的容器
		ListView list = (ListView) findViewById(R.id.showbook);

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
		SimpleAdapter mSchedule = new SimpleAdapter(this, // 没什么解释
				lists,// 数据来源
				R.layout.book_content,// ListItem的XML实现

				// 动态数组与ListItem对应的子项
				new String[] { "tv1", "tv2" },

				// ListItem的XML文件里面的两个TextView ID
				new int[] { R.id.tv1, R.id.tv2 });
		// 添加并且显示
		list.setAdapter(mSchedule);
	}

}
