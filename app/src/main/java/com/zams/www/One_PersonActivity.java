package com.zams.www;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.widget.ExpandableListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.android.hengyu.web.RealmName;
import com.hengyushop.dao.WareDao;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.entity.UserRegisterData;
import com.hengyushop.movie.adapter.OnePersonAdapter;
import com.hengyushop.movie.adapter.One_PersonChld;
import com.hengyushop.movie.adapter.One_Person_Bean;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class One_PersonActivity extends BaseActivity {
	private ExpandableListView persons;
	private OnePersonAdapter adapter;
	private RadioButton order_item0, order_item1, order_item2;
	private RadioGroup orders;
	private Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
				case 1:
					final ArrayList<One_Person_Bean> lists = (ArrayList<One_Person_Bean>) msg.obj;
					adapter = new OnePersonAdapter(One_PersonActivity.this, lists,
							imageLoader);
					persons.setAdapter(adapter);

					orders.setOnCheckedChangeListener(new OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(RadioGroup arg0, int arg1) {

							switch (arg1) {
								case R.id.order_item0:
									adapter.putData(lists);
									break;
								case R.id.order_item1:
									ArrayList<One_Person_Bean> lists0 = new ArrayList<One_Person_Bean>();
									for(int i=0;i<lists.size();i++){
										if(lists.get(i).getCurrentGameDone().equals("1")){
											lists0.add(lists.get(i));
										}
									}
									adapter.putData(lists0);
									break;
								case R.id.order_item2:
									ArrayList<One_Person_Bean> lists1 = new ArrayList<One_Person_Bean>();
									for(int i=0;i<lists.size();i++){
										if(lists.get(i).getCurrentGameDone().equals("0")){
											lists1.add(lists.get(i));
										}
									}
									adapter.putData(lists1);
									break;
								default:
									break;
							}
						}
					});
					break;

				default:
					break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.one_person_layout);
		persons = (ExpandableListView) findViewById(R.id.persons);
		order_item0 = (RadioButton) findViewById(R.id.order_item0);
		order_item1 = (RadioButton) findViewById(R.id.order_item1);
		order_item2 = (RadioButton) findViewById(R.id.order_item2);
		orders = (RadioGroup) findViewById(R.id.orders);
		order_item0.setChecked(true);

		/*
		 * mi/getData.ashx?act=GetMyLuckYiYuanGameSerialList&yth=当前登陆人的恒誉号
		 * 返回字段：HaveTatalJuGouMa表示“该期该商品，你总购买获得了几次聚卡号”
		 */
		WareDao wareDao = new WareDao(One_PersonActivity.this);
		UserRegisterData registerData = wareDao.findIsLoginHengyuCode();
		String yth = registerData.getHengyuCode();
		Map<String, String> params = new HashMap<String, String>();
		params.put("yth", yth);
		params.put("act", "GetMyLuckYiYuanGameSerialList");
		/*
		 * http://www.ju918.com/mi/getData.ashx?act=GetMyLuckYiYuanGameSerialList
		 * &appkey=0762222540&sign=606B63333181FF8D7B9B5C8081EE5D3F579D15E8&yth=
		 * 114514799
		 */
		AsyncHttp.post_1(RealmName.REALM_NAME + "/mi/getData.ashx", params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						super.onSuccess(arg0, arg1);
						try {
							JSONObject jsonObject = new JSONObject(arg1);
							JSONArray jsonArray = jsonObject
									.getJSONArray("items");
							int len = jsonArray.length();
							ArrayList<One_Person_Bean> lists = new ArrayList<One_Person_Bean>();

							for (int i = 0; i < len; i++) {
								JSONObject object = jsonArray.getJSONObject(i);
								One_Person_Bean bean = new One_Person_Bean();
								bean.setCurrentGameDone(object
										.getString("CurrentGameDone"));
								bean.setHasJoinedNum(object
										.getString("HasJoinedNum"));
								bean.setHaveTatalJuGouMa(object
										.getString("HaveTatalJuGouMa"));
								bean.setLuckDrawBatchOrderNumber(object
										.getString("LuckDrawBatchOrderNumber"));
								bean.setNeedGameUserNum(object
										.getString("NeedGameUserNum"));
								bean.setProName(object.getString("proName"));
								bean.setProductItemId(object
										.getString("ProductItemId"));
								bean.setProFaceImg(object
										.getString("proFaceImg"));
								bean.setYiYuanID(object.getString("YiYuanID"));
								JSONArray array = object
										.getJSONArray("ItemDetail");
								ArrayList<One_PersonChld> childs = new ArrayList<One_PersonChld>();
								int jen = array.length();
								for (int j = 0; j < jen; j++) {
									JSONObject obj = array.getJSONObject(j);
									One_PersonChld child = new One_PersonChld();
									child.setCount(obj.getString("count"));
									child.setNum(obj
											.getString("ActualLuckNumber"));
									child.setTime(obj.getString("LuckDrawTime"));
									childs.add(child);
								}
								bean.setChild(childs);
								lists.add(bean);
							}
							Message msg = new Message();
							msg.what = 1;
							msg.obj = lists;
							handler.sendMessage(msg);

						} catch (JSONException e) {

							e.printStackTrace();
						}

					}
				});
	}
}
