package com.hengyushop.demo.shopcart;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.hengyu.pub.GouWuCheAGoodsAdaper;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.demo.wec.MyGridView;
import com.hengyushop.entity.SpListData;
import com.lglottery.www.widget.PullToRefreshView;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.R;
import com.zams.www.WareInformationActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
/**
 * 推荐商品
 * @author Administrator
 *
 */
public class TuiJianSpListActivity extends BaseActivity {

	private DialogProgress progress;
	private int ID;
	private PullToRefreshView refresh;
	int len;
	private MyGridView myGridView;
	GridView gridView;
	GouWuCheAGoodsAdaper jdhadapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tjsp_list);
		progress = new DialogProgress(TuiJianSpListActivity.this);
		initdata();
		load_list();
	}
	Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
				case 1:
					break;

				default:
					break;
			}
		};
	};
	private void initdata() {
		try {
			myGridView = (MyGridView) findViewById(R.id.gridView);
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


	/**
	 * 热销专区
	 */
	private ArrayList<SpListData> lists = null;
	SpListData spList;
	private void load_list() {
		progress.CreateProgress();
		lists = new ArrayList<SpListData>();
		try {
			AsyncHttp.get(RealmName.REALM_NAME_LL+
							"/get_article_top_list?channel_name=goods&top=100&strwhere=status=0%20and%20is_top=1",
					new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int arg0, String arg1) {
							// TODO Auto-generated method stub
							super.onSuccess(arg0, arg1);
							//								System.out.println("热销专区====================="+arg1);
							try {
								JSONObject jsonObject = new JSONObject(arg1);
								String status = jsonObject.getString("status");
								String info = jsonObject.getString("info");
								if (status.equals("y")) {
									JSONArray jsonArray = jsonObject.getJSONArray("data");
									//									 len = jsonArray.length();
									for(int i=1;i<jsonArray.length();i++){
										spList = new SpListData();
										JSONObject object = jsonArray.getJSONObject(i);
										spList.id = object.getString("id");
										spList.img_url = object.getString("img_url");
										spList.title = object.getString("title");
										spList.market_price = object.getString("market_price");
										spList.sell_price = object.getString("sell_price");
										lists.add(spList);
									}
									spList = null;
								}else {
									progress.CloseProgress();
									Toast.makeText(TuiJianSpListActivity.this, info, 200).show();
								}
								System.out.println("lists.size()====================="+lists.size());
								jdhadapter = new GouWuCheAGoodsAdaper(lists, TuiJianSpListActivity.this);
								myGridView.setAdapter(jdhadapter);
								//									GouWuCheAGoodsAdaper.mAq.clear();
								if (lists.size() > 0) {
									GouWuCheAGoodsAdaper.mAq.clear();//清除内存
									GouWuCheAGoodsAdaper.mAq.recycle(myGridView);
								}
								progress.CloseProgress();
								myGridView.setOnItemClickListener(new OnItemClickListener() {
									@Override
									public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
										System.out.println("====================="+lists.get(arg2).id);
										Intent intent = new Intent(TuiJianSpListActivity.this,WareInformationActivity.class);
										intent.putExtra("id", lists.get(arg2).id);
										startActivity(intent);
									}
								});
								//									progress.CloseProgress();
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

	public void onDestroy() {
		super.onDestroy();
		System.out.println("GouWuCheAGoodsAdaper.type=======1=========="+GouWuCheAGoodsAdaper.type);
		try {

			if (GouWuCheAGoodsAdaper.type == true) {
				GouWuCheAGoodsAdaper.mAq.clear();
				GouWuCheAGoodsAdaper.type = false;
			}

			if (lists.size() > 0) {
				lists.clear();
				lists = null;
			}

			System.out.println("GouWuCheAGoodsAdaper.type=======1=========="+GouWuCheAGoodsAdaper.type);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	};



}
