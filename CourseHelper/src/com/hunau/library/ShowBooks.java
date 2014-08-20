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
	private LinearLayout vLoad_Layout; // ��������ʾ
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
				vLoad_Layout.setVisibility(View.GONE);//���ؽ�����
				Toast.makeText(ShowBooks.this, "ͼ���ϵͳ����ά��...", Toast.LENGTH_SHORT)
				.show();
				break;
			case 2:
				vLoad_Layout.setVisibility(View.GONE);//���ؽ�����
				txt.setText("�ѽ��Ĳ�����" + re + " ��");
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
				// ������������
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
				return 404;// ��¼ʧ��404
			LibParseHtml ph = new LibParseHtml();
			books = ph.AanalysisHtml(in);
			return books.size();// �����鱾��
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
					// �������ÿ��Ըı�b���ڿռ�Ķ�������
					if (b.getTimes().contains("0")) {
						flag++;
						// �������ɹ�
						if (l.borrow(name, b.getCode())) {
							b.setTimes("1");
							real++;
						}
					}
				if (flag == 0)
					text = "����ǰû�����������";
				else
					text = flag + "����������," + real + "������ɹ�";
				if (real > 0)
					setComponent();// ������ʾ
				Toast.makeText(ShowBooks.this, text, Toast.LENGTH_LONG).show();
			}
		});

	}

	public void setComponent() {
		// ��XML�е�ListView����ΪItem������
		ListView list = (ListView) findViewById(R.id.showbook);

		ArrayList<HashMap<String, String>> lists = new ArrayList<HashMap<String, String>>();
		for (BookInfo b : books) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("tv1", b.getName());
			map.put("tv2", "�����룺" + b.getCode() + "\n���ߣ�" + b.getAuthor()
					+ "\n��صأ�" + b.getPosition() + "\n���������" + b.getTimes()
					+ "\n����ʱ�䣺" + b.getBortime() + "\nӦ��ʱ�䣺" + b.getRettime()
					+ "\n����ʱ�䣺" + b.getContime() + "\n�ݲص�λ��" + b.getLib()
					+ "\n״̬��" + b.getState());
			lists.add(map);
		}
		// ����������������===��ListItem
		SimpleAdapter mSchedule = new SimpleAdapter(this, // ûʲô����
				lists,// ������Դ
				R.layout.book_content,// ListItem��XMLʵ��

				// ��̬������ListItem��Ӧ������
				new String[] { "tv1", "tv2" },

				// ListItem��XML�ļ����������TextView ID
				new int[] { R.id.tv1, R.id.tv2 });
		// ��Ӳ�����ʾ
		list.setAdapter(mSchedule);
	}

}
