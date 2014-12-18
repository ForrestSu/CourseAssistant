package com.hunau.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hunau.activity.MyBookBorrowActivity;
import com.hunau.coursehelper.R;
import com.hunau.dao.AssessTeacher;
import com.hunau.dao.BookInfo;
import com.hunau.imp.ParseHtml;
import com.hunau.intface.Parse;
import com.hunau.util.Connect_Internet;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class FragmentAssesssTeacher extends Fragment {

	private Mysharepreference s;
	private List<AssessTeacher> questions=new ArrayList<AssessTeacher>();
	private ListView mlistView;
	private TextView onekeyBorrow;
	private SimuLogin l;
	private LinearLayout vLoad_Layout; // ��������ʾ
	private TextView txt;
	public static final int WRONG = -1;
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
    public static String[] left={"������ʦ","�Ƿ�����"};
    public static String[] TYPE1= {"����", "����","һ��" , "�ϲ�" };
    public static String[] TYPE2= {"����"};
    public static String[] VALUE={"1.0","0.8","0.6","0.4"};
	private  Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case WRONG:
				vLoad_Layout.setVisibility(View.GONE);// ���ؽ�����
				txt.setText(msg.obj.toString());
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
		View view = inflater.inflate(R.layout.my_assess, null);
	 return view;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		View view=getView();
		mlistView = (ListView) view.findViewById(R.id.showbook);

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
			msg.what=WRONG;
			msg.obj="���ȵ�¼";
			handler.sendMessage(msg);
		}else if(Connect_Internet.isConnect(getActivity())==false)
		{
			Message msg=new Message();
			msg.what=WRONG;
			msg.obj="����������";
			handler.sendMessage(msg);
		}
		else{
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
					msg.what=WRONG;
					msg.obj="�˺Ż��������";
					handler.sendMessage(msg);
					return;
			
				}
				l.login(url[0], null, false);//��Ҫ�������session
			    html=l.login(url[1], null, false);//�����õ��ǵ�½��session
				if(html==null){
					msg.what=WRONG;
					msg.obj="�޷������ۺϷ���ϵͳ";
					handler.sendMessage(msg);
					return ;
				}
				Parse ph = new ParseHtml();
				questions = ph.AanalysisHtmlAssessTeacher(html);
				msg.what=Success;
				msg.arg1=(questions==null?0:questions.size());
				handler.sendMessage(msg);
			}
		};
		thread.start();
	}

	private void showBookList() {
		// ��XML�е�ListView����ΪItem������
				if (questions == null || questions.size() == 0)return;
				mlistView.setAdapter(adapter);
	}
	private BaseAdapter adapter = new BaseAdapter() {
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			View view = LayoutInflater.from(getActivity()).inflate(
					R.layout.my_assess_item_layout, null);
		final	AssessTeacher b=questions.get(position);
			((TextView)view.findViewById(R.id.book_name)).setText("  "+b.getCourseName());
			((TextView)view.findViewById(R.id.serial)).setText(""+(position+1));
			 //���ú���
			String[] type=TYPE1;
			if(b.getStatus().contains("��"))type=TYPE2; 
				
			Spinner mspinner=(Spinner)view.findViewById(R.id.value); 
			ArrayAdapter<String> madapter = new ArrayAdapter<String>(getActivity(),
						android.R.layout.simple_spinner_item, type);
				madapter .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				mspinner.setAdapter(madapter);
				mspinner.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> parent, View view,
							int position, long id) {
					 b.setScore(VALUE[position]);
						//Toast.makeText(getActivity(), "ѡ����"+TYPE[position], 0).show();
					}
					@Override
					public void onNothingSelected(AdapterView<?> parent) {}
				});
			if(b.getStatus().contains("��"))mspinner.setEnabled(false);;
			//������������
			GridView title = (GridView) view.findViewById(R.id.left_title);
			ArrayAdapter<String> leftAdapter = new ArrayAdapter<String>(getActivity(),
					R.layout.my_book_contentstyle, left);
			//title.set
			title.setAdapter(leftAdapter);
			// �����Ҳ�����
			GridView content = (GridView) view.findViewById(R.id.right_content);
			String[] right = new String[] {b.getTeacherName(),b.getStatus()};
			ArrayAdapter<String> rightAdapter  = new ArrayAdapter<String>(getActivity(),
					R.layout.my_book_contentstyle, right);
			content.setAdapter(rightAdapter);
			return view;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return questions.size();
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
						if(questions!=null)
						for (int i=0;i<questions.size();i++){
//							// �������ÿ��Ըı�b���ڿռ�Ķ�������
							 b =questions.get(i);
							if (b.getStatus().contains("��")) {
								flag++;
								//��ȡһ�θ��ʾ�
								l.login(HunauURL.URL_ASSESS_TEACHER_SEND_BEFORE[0]+i,null,false);
								String sendmsg =getSendMsg(b.getScore());
								if (null !=l.login(HunauURL.URL_ASSESS_TEACHER_SEND[0]+i,sendmsg, false)) {
									b.setStatus("��");
									real++;
								}
							}
						}
						if (flag == 0)
							text = "���������۵��ʾ�";
						else
							text = flag + "��������," + real + "�����̳ɹ�";
						Message msg = new Message();
						msg.what = 2;
						msg.obj = text;
						handler.sendMessage(msg);
					}
				};
				thread.start();
			}
		});
	}
	public String getSendMsg(String score)
	{
		String msg="";
		for(int i=0;i<array.length;i++)
		{
			msg=msg+array[i]+"="+score+"&";
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
