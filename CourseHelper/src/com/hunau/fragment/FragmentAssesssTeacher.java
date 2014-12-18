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
	private LinearLayout vLoad_Layout; // 进度条显示
	private TextView txt;
	public static final int WRONG = -1;
	public static final int Success = 0;
	private static final String[] array={
		"0000000003","0000000007","0000000011","0000000040","0000000048",
		"0000000043","0000000046","0000000056","0000000035","0000000041"
	};
    public static String[] zgpjs=new String[]{
		"老师很认真,知识点讲解很到位。",
		"老师上课有时非常幽默，有时非常严格，不过还是非常有教授风度的，不妨自己来听听嘛!",
		"课堂内容充实，简单明了，使学生能够轻轻松松掌握知识。",
		"老师授课有条理，有重点，对同学既热情又严格，是各位老师学习的榜样。",
		"老师讲课突出重点，内容详细，条理清晰，细致入微。",
		"老师授课的方式非常适合我们，他根据本课程知识结构的特点，重点突出，层次分明。",
		"讲授认真，兼有课堂教学，内容丰富以最好的方式使学生接受，吸收知识，教学方式独特，很有吸引力。"
	};
    public static String[] left={"被评教师","是否已评"};
    public static String[] TYPE1= {"优秀", "良好","一般" , "较差" };
    public static String[] TYPE2= {"已评"};
    public static String[] VALUE={"1.0","0.8","0.6","0.4"};
	private  Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case WRONG:
				vLoad_Layout.setVisibility(View.GONE);// 隐藏进度条
				txt.setText(msg.obj.toString());
				break;
			case Success:
				vLoad_Layout.setVisibility(View.GONE);// 隐藏进度条
				String text1 = msg.arg1 == 0 ? "无评教记录" : "评教问卷:" + msg.arg1+ "条";
				txt.setText(text1);
				if (msg.arg1 > 0) {
					showBookList();// 更新显示
					OneKeyBorrowAble();// 一键续借可用
				}
				break;
			case 2:
				if (msg.arg1 > 0)
					showBookList();// 更新显示
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
			msg.obj="请先登录";
			handler.sendMessage(msg);
		}else if(Connect_Internet.isConnect(getActivity())==false)
		{
			Message msg=new Message();
			msg.what=WRONG;
			msg.obj="请连接网络";
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
				if(l.isLoginFail(html, "错误"))
				{
					msg.what=WRONG;
					msg.obj="账号或者密码错";
					handler.sendMessage(msg);
					return;
			
				}
				l.login(url[0], null, false);//不要保存这个session
			    html=l.login(url[1], null, false);//这里用的是登陆的session
				if(html==null){
					msg.what=WRONG;
					msg.obj="无法访问综合服务系统";
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
		// 绑定XML中的ListView，作为Item的容器
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
			 //设置好评
			String[] type=TYPE1;
			if(b.getStatus().contains("是"))type=TYPE2; 
				
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
						//Toast.makeText(getActivity(), "选择了"+TYPE[position], 0).show();
					}
					@Override
					public void onNothingSelected(AdapterView<?> parent) {}
				});
			if(b.getStatus().contains("是"))mspinner.setEnabled(false);;
			//先设置左侧标题
			GridView title = (GridView) view.findViewById(R.id.left_title);
			ArrayAdapter<String> leftAdapter = new ArrayAdapter<String>(getActivity(),
					R.layout.my_book_contentstyle, left);
			//title.set
			title.setAdapter(leftAdapter);
			// 设置右侧内容
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
						AssessTeacher b=new AssessTeacher();
						if(questions!=null)
						for (int i=0;i<questions.size();i++){
//							// 这里引用可以改变b所在空间的对象内容
							 b =questions.get(i);
							if (b.getStatus().contains("否")) {
								flag++;
								//获取一次该问卷
								l.login(HunauURL.URL_ASSESS_TEACHER_SEND_BEFORE[0]+i,null,false);
								String sendmsg =getSendMsg(b.getScore());
								if (null !=l.login(HunauURL.URL_ASSESS_TEACHER_SEND[0]+i,sendmsg, false)) {
									b.setStatus("是");
									real++;
								}
							}
						}
						if (flag == 0)
							text = "暂无需评价的问卷";
						else
							text = flag + "项需评教," + real + "项评教成功";
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
		return msg+"zgpj=+"+getPY()+"&kg=是";
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
