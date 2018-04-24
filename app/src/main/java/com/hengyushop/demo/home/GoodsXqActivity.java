package com.hengyushop.demo.home;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.hengyu.web.RealmName;
import com.hengyushop.demo.airplane.AirPlaneBargainActivity;
import com.hengyushop.demo.airplane.AirPlaneOnLineActivity;
import com.hengyushop.demo.airplane.AirPlaneSelectActivity;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.entity.XiangqingData;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.R;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.TabHost;
import android.widget.TextView;

/**
 * 商品详情
 *
 * @author Administrator
 *
 */
public class GoodsXqActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jyf_goods_infromation);

		loadWeather();

	}

	private void loadWeather() {
		String id = getIntent().getStringExtra("id");
		System.out.println("=========1============" + id);// 5897
		AsyncHttp.get(RealmName.REALM_NAME_LL + "/get_article_id_content?id="
				+ id + "", new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, String arg1) {

				super.onSuccess(arg0, arg1);
				parse(arg1);
			}

		}, null);
	}

	private ArrayList data_id, data1;
	public static ArrayList data_shuzu, data_mrz, data_monney;
	private String id;

	private void parse(String result) {
		data_id = new ArrayList();
		data_shuzu = new ArrayList();
		data_mrz = new ArrayList();
		data_monney = new ArrayList();
		// lists = new ArrayList<XiangqingData>();
		try {
			System.out.println("=======详情数据==" + result);
			JSONObject object = new JSONObject(result);
			JSONObject jobt = object.getJSONObject("data");
			// xqdata = new XiangqingData();
			// xqdata.setTitle(jobt.getString("title"));
			// proName = xqdata.getTitle();
			// JSONObject job = jobt.getJSONObject("fields");
			// JSONObject job = jobt.getJSONObject("spec_item");
			// JSONArray jsonay = jobt.getJSONArray("spec_item");
			// for (int i = 0; i < jsonay.length(); i++) {
			// JSONObject objt= jsonay.getJSONObject(i);
			// // xqdata.setSub_title(job.getString("sub_title"));
			// xqdata.setSell_price(objt.getString("sell_price"));
			// xqdata.setMarket_price(objt.getString("market_price"));
			// xqdata.setCost_price(objt.getString("cost_price"));
			// xqdata.setRebate_price(objt.getString("rebate_price"));
			// xqdata.setSpec_ids(objt.getString("spec_ids"));
			// xqdata.setGoods_id(objt.getString("goods_id"));
			// xqdata.setArticle_id(objt.getString("article_id"));
			//
			// spec_ids = xqdata.getSpec_ids();
			// proTip = xqdata.getSub_title();
			// retailPrice = xqdata.getSell_price();
			// marketPrice = xqdata.getMarket_price();
			// AvailableJuHongBao = xqdata.getCost_price();
			// Atv_integral = xqdata.getRebate_price();
			// goods_id = xqdata.getGoods_id();
			// article_id = xqdata.getArticle_id();
			// // System.out.println("=========解析的数据============"+proTip);
			// String is_default = objt.getString("is_default");
			//
			// data_mrz.add(is_default);
			// data_shuzu.add(spec_ids);
			// data_monney.add(retailPrice);
			// System.out.println("=========数据============"+spec_ids);
			// }
			// JSONArray jsonArray = jobt.getJSONArray("albums");
			// for (int i = 0; i < jsonArray.length(); i++) {
			// JSONObject obj= jsonArray.getJSONObject(i);
			// xqdata.setThumb_path(obj.getString("thumb_path"));
			// xqdata.setOriginal_path(obj.getString("original_path"));
			// // proFaceImg = xqdata.getThumb_path();
			// // proInverseImg = xqdata.getOriginal_path();
			// proFaceImg = obj.getString("thumb_path");
			// System.out.println("图片地址:" + proFaceImg);
			// proInverseImg = obj.getString("original_path");
			//
			// }
			// lists.add(xqdata);
			// handler.sendEmptyMessage(2);
			// progress.CloseProgress();
		} catch (JSONException e) {

			e.printStackTrace();
		}

	}
}
