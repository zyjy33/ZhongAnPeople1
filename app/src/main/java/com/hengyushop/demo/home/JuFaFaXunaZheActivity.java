package com.hengyushop.demo.home;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.pub.MyListViewAdapter;
import com.android.hengyu.pub.MyListViewAdapter.ViewHolder;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.androidquery.AQuery;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.lglottery.www.domain.TuiGuangBean;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.R;
import com.zams.www.UserLoginActivity;
/**
 * 选择
 * @author Administrator
 *
 */
public class JuFaFaXunaZheActivity extends BaseActivity implements OnClickListener,OnItemClickListener {

	private ArrayList<TuiGuangBean> list;
	//	private ArrayList<TuiGuangBean> list_ll;
	private SharedPreferences spPreferences;
	String user_name,user_id;
	private DialogProgress progress;
	private ListView listView;
	MyListViewAdapter adapter;
	int len = 1;
	String id,exam_id,title,title_id,datatype;
	String id2 = "1726";
	AQuery aQuery;
	private TextView tv_zhuti,tv_ware_name,tv_timu;
	private ImageView iv_tupian;
	private TextView tv_shangyiti,tv_shangyitill,tv_xiayiti,tv_xiayiti_ll;
	private ArrayList<String> list_name = new ArrayList<String>();
	private ArrayList<String> list_id;
	ArrayList<Group> groups;
	String str1="";
	String danxuanti = "";
	LinearLayout ll_shangyiti;
	StringBuffer str;
	//	private ListView listview;
	private MyAdapter myAdapter;
	private ArrayList<Boolean> checkList = new ArrayList<Boolean>(); // 判断listview单选位置
	//	private ArrayList<String> list1 = new ArrayList<String>() { // listView的内容
//		{
//			add("item1");
//			add("item2");
//			add("item3");
//			add("item4");
//			add("item5");
//			add("item6");
//			add("item7");
//			add("item8");
//			add("item9");
//			add("item10");
//		}
//	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acticity_ceshi_xuanzhe);
		progress = new DialogProgress(JuFaFaXunaZheActivity.this);
		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		user_name = spPreferences.getString("user", "");
		user_id = spPreferences.getString("user_id", "");
//		progress.CreateProgress();
		aQuery = new AQuery(this);
		tv_zhuti = (TextView) findViewById(R.id.tv_zhuti);
		tv_ware_name = (TextView) findViewById(R.id.tv_ware_name);
		iv_tupian = (ImageView) findViewById(R.id.iv_tupian);
		exam_id = getIntent().getStringExtra("exam_id");
//		init();
		initdata();
		loadCate(exam_id);
	}

	/**
	 * 单选
	 */
	//设置选中的位置，将其他位置设置为未选
	public void checkPosition(int position) {
		for (int i = 0; i < checkList.size(); i++) {
			if (position == i) {// 设置已选位置
				checkList.set(i, true);
				String str = list.get(i).title;
//					System.out.println("---"+str.replaceAll(" ",""));
//					System.out.println(str.replaceAll("\\s*","")+"/");
				String area1 = str.replaceAll("\\s*", "");
//					System.out.println("==============="+area1+"/");
				danxuanti = area1+"_"+list.get(i).name;
				System.out.println("==============="+danxuanti);
//					Toast.makeText(getApplicationContext(), danxuanti, 200).show();
			} else {
				checkList.set(i, false);
			}
		}
		myAdapter.notifyDataSetChanged();
	}

	public void init() {
		listView = (ListView) findViewById(R.id.new_list);
		for (int i = 0; i < list.size(); i++) {
			checkList.add(false); // 均为未选
		}
		myAdapter = new MyAdapter(JuFaFaXunaZheActivity.this, list);
		listView.setAdapter(myAdapter);
	}

	private void initdata() {
		tv_timu = (TextView) findViewById(R.id.textView1);
//		tv_timu.setText("推广传播");
		listView = (ListView) findViewById(R.id.new_list);
		tv_shangyiti = (TextView) findViewById(R.id.tv_shangyiti);
		tv_shangyitill = (TextView) findViewById(R.id.tv_shangyitill);
		tv_xiayiti = (TextView) findViewById(R.id.tv_xiayiti);
		tv_xiayiti_ll = (TextView) findViewById(R.id.tv_xiayiti_ll);
		ll_shangyiti = (LinearLayout) findViewById(R.id.ll_shangyiti);
		ll_shangyiti.setVisibility(View.GONE);
		tv_xiayiti_ll.setVisibility(View.GONE);
		tv_xiayiti.setOnClickListener(this);
		tv_xiayiti_ll.setOnClickListener(this);
		tv_shangyiti.setOnClickListener(this);
//		tv_shangyitill.setOnClickListener(this);

		listView.setOnItemClickListener(this);
		ImageView iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
		iv_fanhui.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialoglogin();
			}
		});
	}

	//商品列表
	private void loadCate(String exam_id2){
		progress.CreateProgress();
		AsyncHttp.get(RealmName.REALM_NAME_LL+"/get_test_exam_model?" +
				"exam_id="+exam_id2+"", new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(int arg0, String arg1) {
				// TODO Auto-generated method stub
				super.onSuccess(arg0, arg1);
				try {
//						System.out.println("arg1=========="+arg1);
					JSONObject jsonObject = new JSONObject(arg1);
					String status = jsonObject.getString("status");
					if (status.equals("y")) {
						JSONObject obj = jsonObject.getJSONObject("data");
						String summary = obj.getString("summary");
						String img_url = obj.getString("img_url");

						tv_zhuti.setText(summary);
						aQuery.id(iv_tupian).image(RealmName.REALM_NAME + img_url);
					} else {

					}

					load_list(exam_id);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(Throwable arg0, String arg1) {
				// TODO Auto-generated method stub
				super.onFailure(arg0, arg1);
				Toast.makeText(JuFaFaXunaZheActivity.this, "加载异常", 200).show();
				progress.CloseProgress();
			}
		}, null);
	}

	Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
				case 0:
					System.out.println("=================5="+list.size());

					if (len == 1) {
						System.out.println("=====================1");
						tv_shangyiti.setVisibility(View.GONE);
						tv_shangyitill.setVisibility(View.VISIBLE);
					}

					if (list_id.size() == len) {
						System.out.println("=====================2");
						tv_xiayiti.setVisibility(View.VISIBLE);
						tv_xiayiti_ll.setVisibility(View.GONE);
					}


					adapter = new MyListViewAdapter(list, getApplicationContext());
					listView.setAdapter(adapter);
					progress.CloseProgress();
					break;

				case 1:
					try {
						if (len == 1) {
							System.out.println("=====================1");
							tv_shangyiti.setVisibility(View.GONE);
							tv_shangyitill.setVisibility(View.VISIBLE);
						}

						if (list_id.size() == len) {
							System.out.println("=====================2");
							tv_xiayiti.setVisibility(View.VISIBLE);
							tv_xiayiti_ll.setVisibility(View.GONE);
						}

						for (int i = 0; i < list.size(); i++) {
							checkList.add(false); // 均为未选
						}

						myAdapter = new MyAdapter(JuFaFaXunaZheActivity.this, list);
						listView.setAdapter(myAdapter);

					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
					break;

				default:
					break;
			}
		};
	};


	/**
	 * 第1个列表数据解析
	 */
	private void load_list(String exam_id) {
		System.out.println("==len==================="+len);
		AsyncHttp.get(RealmName.REALM_NAME_LL+"/get_test_question_list?" +
				"exam_id="+exam_id+"", new AsyncHttpResponseHandler(){

			@Override
			public void onSuccess(int arg0, String arg1) {
				// TODO Auto-generated method stub
				super.onSuccess(arg0, arg1);
//								System.out.println("=====================二级值1"+arg1);
				try {
					JSONObject jsonObject = new JSONObject(arg1);
					String status = jsonObject.getString("status");
					String data_ll = jsonObject.getString("data");
					if (data_ll.equals("null")) {
//										tv_xiayiti_ll.setVisibility(View.GONE);
						Toast.makeText(JuFaFaXunaZheActivity.this, "数据为空", 200).show();
						progress.CloseProgress();
					}else {
						tv_xiayiti_ll.setVisibility(View.VISIBLE);
						if (status.equals("y")) {
							JSONArray jsonArray = jsonObject.getJSONArray("data");
							for (int i = 0; i < jsonArray.length(); i++) {
								JSONObject object = jsonArray.getJSONObject(i);
//										data.id = object.getString("id");
//										title = object.getString("title");
								String child = object.getString("child");
//										System.out.println("=====================child=="+child);
//										if (child == null) {
//											System.out.println("=======================null==/"+child);
//										}
//										else {
								JSONArray ja = new JSONArray(child);
								list_id = new ArrayList<String>();
//										System.out.println("=====================list_id.size()=="+list_id.size());
								for (int j = 0; j < ja.length(); j++) {
									JSONObject jo = ja.getJSONObject(j);
									title_id = jo.getString("title");
									list_id.add(title_id);
								}
								for (int j = 0; j < len; j++) { //ja.length(); j++) {
									JSONObject jo = ja.getJSONObject(j);
									title = jo.getString("title");
									datatype = jo.getString("datatype");
									String item = jo.getString("item");
									JSONArray jaot = new JSONArray(item);
									list = new ArrayList<TuiGuangBean>();
									for (int k = 0; k < jaot.length(); k++) {
										JSONObject jot = jaot.getJSONObject(k);
										TuiGuangBean data = new TuiGuangBean();
										data.question_id = jot.getString("question_id");
										data.title = jot.getString("title");
										data.name = jot.getString("name");
										list.add(data);
									}
								}

//										}
							}
							tv_ware_name.setText(title+"("+datatype+")");
							tv_timu.setText("共"+list_id.size()+"题"+"/"+"第"+len+"题");
							if (datatype.contains("多选题")) {
								handler.sendEmptyMessage(0);
							}else {
								handler.sendEmptyMessage(1);
							}
						}else {

						}
						progress.CloseProgress();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			@Override
			public void onFailure(Throwable arg0, String arg1) {
				// TODO Auto-generated method stub
				super.onFailure(arg0, arg1);
				Toast.makeText(JuFaFaXunaZheActivity.this, "加载异常", 200).show();
				progress.CloseProgress();
			}
		}, null);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.tv_shangyiti:
//			if (datatype.contains("多选题")) {
//
//			System.out.println("=====================二级值=="+len);
//			System.out.println("=====================list_id.size()=="+list_id.size());
//			HashMap<Integer, Boolean> map1 = MyListViewAdapter.getIsSelected();
//			System.out.println("=====map.size()================"+map1.size());
//			str1="";
//			for (int i = 0; i < map1.size(); i++) {
//				if (map1.get(i)) {
//					String fegefu = str1.length()>0?"":"";
//					str1 += fegefu+list.get(i).question_id+"_"+list.get(i).name;
//					System.out.println("=====str1================"+str1);
//				}
//			}
//			MyListViewAdapter.getIsSelected().get("");
//			len -= 1;
//			if (list_id.size() > len) {
//				load_list(exam_id);
//				tv_xiayiti.setVisibility(View.GONE);
//				tv_xiayiti_ll.setVisibility(View.VISIBLE);
//			}
//			}else {
//				MyListViewAdapter.getIsSelected().get("");
////				str1 +=	danxuanti;
//				String fegefu = danxuanti.length()>0?",":"";
//				str1 +=	fegefu+danxuanti;
//				Toast.makeText(getApplicationContext(),"已选中了" + str1 + "", Toast.LENGTH_SHORT).show();
//				list_name.add(str1);
////				for (int i = 0; i < checkList.size(); i++) {
////						checkList.set(i, true);
////				}
//				danxuanti="";
//				len -= 1;
//				if (list_id.size() > len) {
//					load_list(exam_id);
//					tv_xiayiti.setVisibility(View.GONE);
//					tv_xiayiti_ll.setVisibility(View.VISIBLE);
//				}
//			}
				break;
			case R.id.tv_xiayiti_ll:
				if (datatype.contains("多选题")) {
					HashMap<Integer, Boolean> map = MyListViewAdapter.getIsSelected();
					str1="";
					for (int i = 0; i < map.size(); i++) {
						if (map.get(i)) {
							String fegefu = str1.length()>0?",":"";
							str1 += fegefu+list.get(i).question_id+"_"+list.get(i).name;
//					System.out.println("=====str1================"+str1);
						}
					}
					if (str1.equals("")) {
						Toast.makeText(getApplicationContext(),"请选择选项", Toast.LENGTH_SHORT).show();
					}else {
						MyListViewAdapter.getIsSelected().get("");
//			Toast.makeText(getApplicationContext(),"已选中了" + str1 + "", Toast.LENGTH_SHORT).show();
						list_name.add(str1);

						if (list_id.size() > len) {
							len += 1;
							load_list(exam_id);
//				tv_shangyiti.setVisibility(View.GONE);
//				tv_shangyitill.setVisibility(View.VISIBLE);
						}
					}

				}else {
					if (danxuanti.equals("")) {
						Toast.makeText(getApplicationContext(),"请选择选项", Toast.LENGTH_SHORT).show();
					}else {
//				String fegefu = danxuanti.length()>0?",":"";
//				str1 +=	danxuanti+fegefu+danxuanti;
//				str1 +=	danxuanti;
//				Toast.makeText(getApplicationContext(),"已选中了" + danxuanti + "", Toast.LENGTH_SHORT).show();
						list_name.add(danxuanti);
						for (int i = 0; i < checkList.size(); i++) {
							checkList.set(i, false);
						}
						danxuanti="";
						if (list_id.size() > len) {
							len += 1;
							load_list(exam_id);
//					tv_shangyiti.setVisibility(View.GONE);
//					tv_shangyitill.setVisibility(View.VISIBLE);
						}
					}
				}
				break;
			case R.id.tv_xiayiti:
				myPrice();
				break;

			default:
				break;
		}
	}

	/**
	 * 提交
	 * */
	public void myPrice() {
		if (datatype.contains("多选题")) {
			HashMap<Integer, Boolean> map = MyListViewAdapter.getIsSelected();
			str1="";
			for (int i = 0; i < map.size(); i++) {
				if (map.get(i)) {
					String fegefu = str1.length()>0?",":"";
//				str1 += fegefu+(i);
					str1 += fegefu+list.get(i).question_id+"_"+list.get(i).name;
				}
			}

			if (str1.equals("")) {
				Toast.makeText(getApplicationContext(),"您还未选择答案", Toast.LENGTH_SHORT).show();
			}else {
				MyListViewAdapter.getIsSelected().get("");
//		Toast.makeText(getApplicationContext(),"已选中了" + str1 + "", Toast.LENGTH_SHORT).show();
				Toast.makeText(getApplicationContext(), str1, 200).show();

				list_name.add(str1);
//		System.out.println("=====list_name.size()================"+list_name.size());
				str = new StringBuffer();
				for(String s:list_name){
					str.append(s+",");
				}
				str.delete(str.lastIndexOf(","),str.length());
//        Toast.makeText(getApplicationContext(), str, 500).show();
				System.out.println("id拼接之后---------------"+str);

				loadtijiao();
			}

		}else {
			try {
				if (danxuanti.equals("")) {
					Toast.makeText(getApplicationContext(),"请选择选项", Toast.LENGTH_SHORT).show();
				}else {
//			MyListViewAdapter.getIsSelected().get("");
//			String fegefu = danxuanti.length()>0?",":"";
//			str1 +=	fegefu+danxuanti;
//			Toast.makeText(getApplicationContext(),"已选中了" + str1 + "", Toast.LENGTH_SHORT).show();
					System.out.println("list_name---------------"+list_name.size());
					list_name.add(danxuanti);
					str = new StringBuffer();
					for(String s:list_name){
						str.append(s+",");
					}
					str.delete(str.lastIndexOf(","),str.length());
					System.out.println("id拼接之后---------------"+str);
//	        Toast.makeText(getApplicationContext(), str, 500).show();
					loadtijiao();
				}

			} catch (Exception e) {
				// TODO: handle exception
			}
		}

	}

	/**
	 * 提交在线定制题目
	 * @param exam_id2
	 */
	public void loadtijiao(){
		try {

			AsyncHttp.get(RealmName.REALM_NAME_LL+"/post_test_question?" +
					"answer_list="+str+"&exam_id="+exam_id+"&user_id="+user_id+"&user_name="+user_name+"", new AsyncHttpResponseHandler(){
				@Override
				public void onSuccess(int arg0, String arg1) {
					// TODO Auto-generated method stub
					super.onSuccess(arg0, arg1);
					try {
						System.out.println("arg1=========="+arg1);
						JSONObject jsonObject = new JSONObject(arg1);
						String status = jsonObject.getString("status");
						String info = jsonObject.getString("info");
						if (status.equals("y")) {
							Toast.makeText(getApplicationContext(), info, 200).show();
						} else {
							Toast.makeText(getApplicationContext(), info, 200).show();
						}

						finish();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}, null);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * 提示是否取消
	 */
	protected void dialoglogin() {
		AlertDialog.Builder builder = new Builder(JuFaFaXunaZheActivity.this);
		builder.setMessage("是否确认取消?");
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		});

		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});

		builder.create().show();
	}

	/**
	 * listview的item的选择的方法
	 * */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
							long id) {
		// 取得ViewHolder对象，这样就省去了通过层层的findViewById去实例化我们需要的cb实例的步骤
		ViewHolder holder = (ViewHolder) view.getTag();
		// 改变CheckBox的状态
		holder.cb.toggle();
		// 将CheckBox的选中状况记录下来
		MyListViewAdapter.getIsSelected().put(position, holder.cb.isChecked());
	}


	//自定义adapter
	private class MyAdapter extends BaseAdapter {
		private LayoutInflater inflater;
		ArrayList<TuiGuangBean> myList;

		public MyAdapter(Context context, ArrayList<TuiGuangBean> myList) {
			this.inflater = LayoutInflater.from(context);
			this.myList = myList;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return myList.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return myList.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(final int position, View convertView,
							ViewGroup parent) {
			try {

				Log.i("aaa", "getview");
				ViewHolder holder = null;
				if (convertView == null) {
					convertView = inflater.inflate(R.layout.ceshi_item, null);//layout_item
					holder = new ViewHolder();
					holder.txt = (TextView) convertView.findViewById(R.id.food_name);
					holder.checkBox = (CheckBox) convertView.findViewById(R.id.check_box);
					convertView.setTag(holder);
				} else {
					holder = (ViewHolder) convertView.getTag();
				}
				holder.txt.setText(myList.get(position).title);
				holder.checkBox.setChecked(checkList.get(position));
				holder.checkBox
						.setOnCheckedChangeListener(new OnCheckedChangeListener() {//单击checkbox实现单选，根据状态变换进行单选设置

							@Override
							public void onCheckedChanged(CompoundButton buttonView,
														 boolean isChecked) {
								// TODO Auto-generated method stub
								if (isChecked) {
									checkPosition(position);
								} else {
									checkList.set(position, false);//将已选择的位置设为选择
								}
							}
						});
				convertView.setOnClickListener(new OnClickListener() {//item单击进行单选设置

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						checkPosition(position);
					}
				});
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return convertView;
		}

		public final class ViewHolder {
			public TextView txt;
			public CheckBox checkBox;
		}
	}

}
