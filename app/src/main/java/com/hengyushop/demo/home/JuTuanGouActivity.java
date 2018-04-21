package com.hengyushop.demo.home;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.pub.JuTuanGouAdapter;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.entity.JuTuanGouData;
import com.lglottery.www.widget.PullToRefreshView;
import com.lglottery.www.widget.PullToRefreshView.OnFooterRefreshListener;
import com.lglottery.www.widget.PullToRefreshView.OnHeaderRefreshListener;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.R;

/**
 * 聚团购
 *
 * @author Administrator
 *
 */
public class JuTuanGouActivity extends BaseActivity implements OnClickListener,
		UncaughtExceptionHandler {

	private ImageView iv_fanhui;
	private TextView tv_xiabu;
	EditText ra4;
	String check = "0";
	private DialogProgress progress;
	private SharedPreferences spPreferences;
	private JuTuanGouAdapter Jutuangouadapter;
	private ListView new_list;
	private ArrayList<JuTuanGouData> list = null;
	private List<String> list2 = new ArrayList<String>();
	private PullToRefreshView refresh;
	private int RUN_METHOD = -1;
	int len;
	public static String datetime;
	String category_id, zhuangtai;
	JuTuanGouData data;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_jutuangou);
		// 在此调用下面方法，才能捕获到线程中的异常
		Thread.setDefaultUncaughtExceptionHandler(this);
		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		progress = new DialogProgress(JuTuanGouActivity.this);
		try {
			intren();
			list = new ArrayList<JuTuanGouData>();
			Jutuangouadapter = new JuTuanGouAdapter(getApplicationContext(),
					list);
			new_list.setAdapter(Jutuangouadapter);
			TextView tv_title = (TextView) findViewById(R.id.textView1);

			String title = getIntent().getStringExtra("title");
			System.out.println("=====title=====================" + title);
			if (title != null) {
				tv_title.setText(title);
			}

			// category_id = getIntent().getStringExtra("category_id");
			zhuangtai = getIntent().getStringExtra("zhuangtai");
			System.out.println("=====zhuangtai====================="
					+ zhuangtai);
			// if (shuzi != null) {
			// if(shuzi.equals("4")){
			// zhuangtai = "onebuy";
			// load_list(true, category_id,zhuangtai);
			// }else {
			// zhuangtai = "groupon";
			load_list(true, category_id, zhuangtai);
			// }
			// }
			ImageView iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
			iv_fanhui.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					finish();
				}
			});

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void uncaughtException(Thread arg0, Throwable arg1) {
		// TODO Auto-generated method stub
		// 在此处理异常， arg1即为捕获到的异常
		Log.i("AAA", "uncaughtException   " + arg1);
	}

	public void intren() {
		try {
			refresh = (PullToRefreshView) findViewById(R.id.refresh);
			refresh.setOnHeaderRefreshListener(listHeadListener);
			refresh.setOnFooterRefreshListener(listFootListener);
			new_list = (ListView) findViewById(R.id.new_list);
			// ImageView iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
			// iv_fanhui.setOnClickListener(this);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	private Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
				case 0:
					break;
				case 1:
					System.out.println("1====================" + list.size());
					// list.add(0,"");
					// MyAdapter2 adapter = new MyAdapter2(getApplicationContext(),
					// list);
					// myGridView.setAdapter(adapter);
					break;
				case 2:
					System.out.println("个数是多少2====================" + list.size());
					Jutuangouadapter.putData(list);
					progress.CloseProgress();
					if (list.size() > 0) {
						JuTuanGouAdapter.mAq.clear();
						list = null;
					}
					// Jutuangouadapter = new
					// JuTuanGouAdapter(getApplicationContext(), list);
					// new_list.setAdapter(Jutuangouadapter);
					break;
				default:
					break;
			}
		};
	};

	/**
	 * 上拉列表刷新加载
	 */
	private OnHeaderRefreshListener listHeadListener = new OnHeaderRefreshListener() {

		@Override
		public void onHeaderRefresh(PullToRefreshView view) {
			// TODO Auto-generated method stub
			refresh.postDelayed(new Runnable() {

				@Override
				public void run() {
					refresh.onHeaderRefreshComplete();
				}
			}, 1000);
		}
	};

	/**
	 * 下拉列表刷新加载
	 */
	private OnFooterRefreshListener listFootListener = new OnFooterRefreshListener() {

		@Override
		public void onFooterRefresh(PullToRefreshView view) {
			// TODO Auto-generated method stub
			refresh.postDelayed(new Runnable() {

				@Override
				public void run() {
					try {
						// if (RUN_METHOD == 0) {
						// System.out.println("RUN_METHOD1========="+
						// RUN_METHOD);
						// load_list2(false);
						// } else {
						System.out.println("RUN_METHOD2=========" + RUN_METHOD);
						load_list(false, category_id, zhuangtai);
						// }
						refresh.onFooterRefreshComplete();

					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
			}, 1000);
		}
	};
	/**
	 * 输出所有拼团活动列表
	 */
	private int CURRENT_NUM = 1;
	private final int VIEW_NUM = 10;

	private void load_list(boolean flag, String category_id, String zhuangtai) {
		try {
			progress.CreateProgress();
			if (flag) {
				// 计数和容器清零
				CURRENT_NUM = 1;
				list = new ArrayList<JuTuanGouData>();
			}
			// AsyncHttp.get(RealmName.REALM_NAME_LL +
			// "/get_game_groupon_list?page_size=" + VIEW_NUM + "" +
			// "&page_index=" + CURRENT_NUM +
			// "&channel_name="+zhuangtai+"&category_id="+0+""

			AsyncHttp.get(RealmName.REALM_NAME_LL
							+ "/get_article_page_size_list_2017?channel_name="
							+ zhuangtai + "&category_id=0" + "&page_size=" + VIEW_NUM
							+ "&page_index=" + CURRENT_NUM + "&strwhere=&orderby=",
					new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int arg0, String arg1) {
							// TODO Auto-generated method stub
							super.onSuccess(arg0, arg1);
							System.out.println("输出所有拼团活动列表=========" + arg1);
							try {
								// list = new ArrayList<JuTuanGouData>();
								progress.CloseProgress();
								JSONObject object = new JSONObject(arg1);
								String status = object.getString("status");
								String info = object.getString("info");
								datetime = object.getString("datetime");
								if (status.equals("y")) {
									JSONArray jsonArray = object
											.getJSONArray("data");
									len = jsonArray.length();
									for (int i = 0; i < jsonArray.length(); i++) {
										// JSONObject obj =
										// jsonArray.getJSONObject(i);
										data = new JuTuanGouData();
										// data.setId(obj.getString("id"));
										// data.setTitle(obj.getString("title"));
										// data.setImg_url(obj.getString("img_url"));
										// //
										// data.setArticle_id(obj.getString("article_id"));
										// data.setPrice(obj.getString("price"));
										// data.setPeople(obj.getString("people"));
										// data.setGroupon_price(obj.getString("groupon_price"));
										// data.setAdd_time(obj.getString("add_time"));
										// data.setUpdate_time(obj.getString("update_time"));
										// data.setCategory_id(obj.getString("category_id"));
										// data.setEnd_time(obj.getString("end_time"));
										// list.add(data);

										JSONObject obj = jsonArray
												.getJSONObject(i);
										data.setId(obj.getString("id"));
										data.setTitle(obj.getString("title"));
										data.setImg_url(obj
												.getString("img_url"));
										data.setAdd_time(obj
												.getString("add_time"));
										data.setUpdate_time(obj
												.getString("update_time"));
										data.setCategory_id(obj
												.getString("category_id"));
										data.setEnd_time(obj
												.getString("end_time"));

										JSONObject jsot = obj
												.getJSONObject("default_spec_item");
										data.setArticle_id(jsot
												.getString("article_id"));
										data.setSell_price(jsot
												.getString("sell_price"));

										JSONObject jsoct = jsot
												.getJSONObject("default_activity_price");
										data.setPeople(jsoct
												.getString("people"));
										data.setPrice(jsoct.getString("price"));
										list.add(data);
									}
									System.out.println("---------------------"
											+ list.size());
									// Toast.makeText(JuTuanGouActivity.this,
									// info, 200).show();
									data = null;
									if (len != 0) {
										CURRENT_NUM = CURRENT_NUM + 1;
									}
									handler.sendEmptyMessage(2);
									progress.CloseProgress();
								} else {
									progress.CloseProgress();
									Toast.makeText(JuTuanGouActivity.this,info, 200).show();
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

					}, null);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.fanhui:
				finish();
				break;
			default:
				break;
		}
	}

}
