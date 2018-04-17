package com.zams.www;

import java.text.ParseException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.hengyu.web.RealmName;
import com.hengyushop.airplane.adapter.ComboOtherAdapter;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.demo.at.Common;
import com.hengyushop.json.HttpUtils;
import com.lglottery.www.common.SharedUtils;
import com.lglottery.www.domain.ComboDetailDomain;
import com.lglottery.www.domain.ComboOtherItem;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zams.www.R;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

public class ComboDetalActivity extends BaseActivity {
	private SharedUtils utils;
	private ComboDetailDomain detailDomain = null;
	private ImageView combo_detail_img;
	private TextView title, describe, current_price, list_price, is_seals,
			purchase_deadline, detail_tips, detail_des;
	private ImageView is_req;
	private ScrollView combo_scrool;
	private ListView combo_other;
	private ComboOtherAdapter otherAdapter;
	private Handler handler = new Handler() {
		public void dispatchMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				title.setText(detailDomain.getTitle());
				describe.setText(detailDomain.getDescription());
				current_price.setText(detailDomain.getCurrent_price() + "元");
				list_price.setText(detailDomain.getList_price() + "元");
				imageLoader.displayImage(detailDomain.getImage_url(),
						combo_detail_img);
				is_seals.setText("已售" + detailDomain.getPurchase_count());
				if (detailDomain.getIs_required().equals("1")) {
					is_req.setVisibility(View.VISIBLE);
				} else {
					is_req.setVisibility(View.INVISIBLE);
				}
				try {
					purchase_deadline.setText(HttpUtils.getSimpleTime(
							detailDomain.getPurchase_deadline(), "yyyy-MM-dd"));

				} catch (ParseException e) {
					e.printStackTrace();
				}
				detail_tips.setText(detailDomain.getTips());
				detail_des.setText(detailDomain.getDetails());
				otherAdapter = new ComboOtherAdapter(getApplicationContext(),
						detailDomain.getItems(), imageLoader, handler);
				combo_other.setAdapter(otherAdapter);
				combo_scrool.scrollTo(0, 0);

				break;

			default:
				break;
			}
		};
	};

	private void init() {
		combo_scrool = (ScrollView) findViewById(R.id.combo_scrool);

		combo_other = (ListView) findViewById(R.id.combo_other);
		detail_des = (TextView) findViewById(R.id.detail_des);
		detail_tips = (TextView) findViewById(R.id.detail_tips);
		is_req = (ImageView) findViewById(R.id.is_req);
		is_seals = (TextView) findViewById(R.id.is_seals);
		purchase_deadline = (TextView) findViewById(R.id.purchase_deadline);
		current_price = (TextView) findViewById(R.id.current_price);
		list_price = (TextView) findViewById(R.id.list_price);
		combo_detail_img = (ImageView) findViewById(R.id.combo_detail_img);
		title = (TextView) findViewById(R.id.title);
		describe = (TextView) findViewById(R.id.describe);
		title.setFocusable(true);
		title.setFocusableInTouchMode(true);
		title.requestFocus();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.combo_detail);
		utils = new SharedUtils(getApplicationContext(), "locals");
		init();
		String deal_id = getIntent().getStringExtra("deal_id");
		loadData(deal_id);
	}

	/**
	 * 
	 * @param id
	 */
	private void loadData(String id) {
		// latitude=22.515314&longitude=113.932174&deal_id=6-6016818
		RequestParams params = new RequestParams();
		params.put("latitude", utils.getStringValue("lat"));
		params.put("longitude", utils.getStringValue("log"));
		params.put("deal_id", id);
		AsyncHttp.post(RealmName.REALM_NAME
				+ "/mi/DianPing_Handler.ashx?act=GetOneDealDetail_ForMobile",
				params, new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						super.onSuccess(arg0, arg1);
						parse(arg1);
						handler.sendEmptyMessage(0);
					}
				}, getApplicationContext());
	}

	private void parse(String result) {
		detailDomain = new ComboDetailDomain();
		try {
			JSONObject jsonObject = new JSONObject(result);
			detailDomain.setTitle(jsonObject.getString("title"));
			detailDomain
					.setCurrent_price(jsonObject.getString("current_price"));
			detailDomain.setPurchase_count(jsonObject
					.getString("purchase_count"));
			detailDomain.setIs_required(jsonObject
					.getString("is_reservation_required"));
			detailDomain.setList_price(jsonObject.getString("list_price"));
			detailDomain.setPurchase_count(jsonObject
					.getString("purchase_count"));
			detailDomain.setReview_url(jsonObject.getString("review_list_url"));
			detailDomain.setTips(jsonObject.getString("special_tips"));
			detailDomain.setTotal(jsonObject.getString("reviewTotalCount"));
			detailDomain.setDescription(jsonObject.getString("description"));
			detailDomain.setImage_url(jsonObject.getString("image_url"));
			detailDomain
					.setIs_refundable(jsonObject.getString("is_refundable"));
			detailDomain.setPurchase_deadline(jsonObject
					.getString("purchase_deadline"));
			detailDomain.setDetails(jsonObject.getString("details"));
			JSONArray jsonArray = jsonObject.getJSONArray("BrandRelatedDeals");
			int jen = jsonArray.length();
			ArrayList<ComboOtherItem> items = new ArrayList<ComboOtherItem>();
			for (int j = 0; j < jen; j++) {
				JSONObject object = jsonArray.getJSONObject(j);
				ComboOtherItem otherItem = new ComboOtherItem();
				otherItem.setText1(object.getString("description"));
				otherItem.setText2(object.getString("list_price"));
				otherItem.setText3(object.getString("current_price"));
				items.add(otherItem);
			}
			detailDomain.setItems(items);
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}
}