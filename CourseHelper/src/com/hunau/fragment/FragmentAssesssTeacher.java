package com.hunau.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hunau.coursehelper.R;
import com.hunau.dao.AssessTeacher;
import com.hunau.imp.ParseHtml;
import com.hunau.intface.Parse;
import com.hunau.util.HunauURL;
import com.hunau.util.Mysharepreference;
import com.hunau.util.SimuLogin;
import com.slidingmenu.main.MainMenuSlidingActivity;

import android.R.integer;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentAssesssTeacher extends Fragment {

	private Mysharepreference s;
	private List<AssessTeacher> books;
	private ListView list;
	private TextView onekeyBorrow;
	private SimuLogin l;
	private LinearLayout vLoad_Layout; // ��������ʾ
	private TextView txt;
	public static final int No_Name_Pass = -2;
	public static final int Network_Not_Reach = -1;
	public static final int Success = 0;
	private static final String[] array={
		"0000000003","0000000007","0000000011","0000000040","0000000048",
		"0000000043","0000000046","0000000056","0000000035","0000000041"
	};
    public static String[] zgpjs=new String[]{
		"��ʦ������,֪ʶ�㽲��ܵ�λ��",
		"��ʦ�Ͽ���ʱ�ǳ���Ĭ����ʱ�ǳ��ϸ񣬲������Ƿǳ��н��ڷ�ȵģ������Լ���������!",
		"�������ݳ�ʵ�������ˣ�ʹѧ���ܹ�������������֪ʶ��",
		"��ʦ�ڿ����������ص㣬��ͬѧ���������ϸ��Ǹ�λ��ʦѧϰ�İ�����",
		"��ʦ����ͻ���ص㣬������ϸ������������ϸ����΢��",
		"��ʦ�ڿεķ�ʽ�ǳ��ʺ����ǣ������ݱ��γ�֪ʶ�ṹ���ص㣬�ص�ͻ������η�����",
		"�������棬���п��ý�ѧ�����ݷḻ����õķ�ʽʹѧ�����ܣ�����֪ʶ����ѧ��ʽ���أ�������������"
	};
	private  Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case No_Name_Pass:
				vLoad_Layout.setVisibility(View.GONE);// ���ؽ�����
				txt.setText((String)msg.obj);
				break;
			case Network_Not_Reach:
				vLoad_Layout.setVisibility(View.GONE);// ���ؽ�����
				txt.setText("�޷������ۺϷ���ϵͳ");
				break;
			case Success:
				vLoad_Layout.setVisibility(View.GONE);// ���ؽ�����
				String text1 = msg.arg1 == 0 ? "�����̼�¼" : "�����ʾ�:" + msg.arg1+ "��";
				txt.setText(text1);
				if (msg.arg1 > 0) {
					showBookList();// ������ʾ
					OneKeyBorrowAble();// һ���������
				}
				break;
			case 2:
				if (msg.arg1 > 0)
					showBookList();// ������ʾ
				Toast.makeText(getActivity(), msg.obj.toString(),Toast.LENGTH_LONG).show();
				break;
			}
		}
	};
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.assessteacher, null);
	 return view;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		View view=getView();
		list = (ListView) view.findViewById(R.id.showbook);

		vLoad_Layout = (LinearLayout) view.findViewById(R.id.loading);
		vLoad_Layout.setVisibility(View.INVISIBLE);
		txt = (TextView) view.findViewById(R.id.booknum);
		onekeyBorrow = (TextView) view.findViewById(R.id.onekeyborrow);
		onekeyBorrow.setVisibility(View.INVISIBLE);
        ImageView back=(ImageView)view.findViewById(R.id.showleft);
        back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				((MainMenuSlidingActivity) getActivity()).toggle();
			}
		});
		s = new Mysharepreference(getActivity());
		String name= s.getMessage("name");
		String pass=s.getMessage("pass");
		if(name==null||pass==null)
		{
			Message msg=new Message();
			msg.what=No_Name_Pass;
			msg.obj="�˺Ż��������";
			handler.sendMessage(msg);
		}else {
			LoginSuccess(name,pass);
		}
	}
	private void LoginSuccess(final String name ,final String pass) {
		vLoad_Layout.setVisibility(View.VISIBLE);
		Thread thread = new Thread() {
			public void run() {
				Message msg=new Message();
				l = new SimuLogin();
				String[] url = new String[] {
						HunauURL.URL_ASSESS_TEACHER_LIST[0],
						HunauURL.URL_ASSESS_TEACHER_TABLE[0] };
				String sendmsg = "userName=" +name + "&userPass=" +pass;
				String html = l.login(HunauURL.LOGIN[0], sendmsg,true);
				if(l.isLoginFail(html, "����"))
				{
					msg.what=No_Name_Pass;
					msg.obj="�˺Ż��������";
					handler.sendMessage(msg);
				}
				l.login(url[0], null, false);//��Ҫ�������session
			    html=l.login(url[1], null, false);//�����õ��ǵ�½��session
				if(html==null){
					msg.what=Network_Not_Reach;
					handler.sendMessage(msg);
					return ;
				}
				Parse ph = new ParseHtml();
				books = ph.AanalysisHtmlAssessTeacher(html);
				msg.what=Success;
				msg.arg1=(books==null?0:books.size());
				handler.sendMessage(msg);
			}
		};
		thread.start();
	}

	private void showBookList() {
		// ��XML�е�ListView����ΪItem������

		ArrayList<HashMap<String, String>> lists = new ArrayList<HashMap<String, String>>();
		for (AssessTeacher b : books) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("tv1", b.getCourseName());
			map.put("tv2", b.toString());
			lists.add(map);
		}
		// ����������������===��ListItem
		SimpleAdapter mSchedule = new SimpleAdapter(getActivity(), // ûʲô����
				lists,// ������Դ
				R.layout.book_content,// ListItem��XMLʵ��

				// ��̬������ListItem��Ӧ������
				new String[] { "tv1", "tv2" },

				// ListItem��XML�ļ����������TextView ID
				new int[] { R.id.tv1, R.id.tv2 });
		// ��Ӳ�����ʾ
		list.setAdapter(mSchedule);
	}

	// һ������
	public void OneKeyBorrowAble() {
		onekeyBorrow.setVisibility(View.VISIBLE);
		onekeyBorrow.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// /�¿�һ���߳�
				Thread thread = new Thread() {
					public void run() {
						int flag = 0, real = 0;
						String text;
						AssessTeacher b=new AssessTeacher();
						if(books!=null)
						for (int i=0;i<books.size();i++){
//							// �������ÿ��Ըı�b���ڿռ�Ķ�������
							 b =books.get(i);
							if (b.getStatus().contains("��")) {
								flag++;
								//��ȡһ�θ��ʾ�
								l.login(HunauURL.URL_ASSESS_TEACHER_SEND_BEFORE[0]+i,null,false);
								String sendmsg =getSendMsg("1.0");
								if (null !=l.login(HunauURL.URL_ASSESS_TEACHER_SEND[0]+i,sendmsg, false)) {
									b.setStatus("��");
									real++;
								}
							}
						}
						if (flag == 0)
							text = "��ȫ���������";
						else
							text = flag + "��������," + real + "�����̳ɹ�";
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
	public String getSendMsg(String r)
	{
		String msg="";
		for(int i=0;i<array.length;i++)
		{
			msg=msg+array[i]+"="+r+"&";
		}
		return msg+"zgpj=+"+getPY()+"&kg=��";
	}

	public static String getPY()
	{
		
		int x=(int) (Math.random()*zgpjs.length);
		if(x<0 || x>=zgpjs.length)
			return zgpjs[0];
		else
			return zgpjs[x];
	}

}
