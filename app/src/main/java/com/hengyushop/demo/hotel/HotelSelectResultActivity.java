package com.hengyushop.demo.hotel;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Xml;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.ctrip.openapi.java.base.HttpAccessAdapter;
import com.hengyushop.demo.at.BaseActivity;
import com.zams.www.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class HotelSelectResultActivity extends BaseActivity {

	private ListView hotel_items;
	private HotelItemAdapter itemAdapter;
	private TextView total, start;
	ArrayList<HotelItem> lists;
	private Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
				case 0:

					String str = (String) msg.obj;
					System.out.println(str);
					InputStreamReader reader;
					try {
						reader = new InputStreamReader(new ByteArrayInputStream(
								str.getBytes("UTF-8")));
						lists = parseHotel(reader);
						itemAdapter = new HotelItemAdapter(getApplicationContext(),
								lists, imageLoader);
						hotel_items.setAdapter(itemAdapter);
						hotel_items
								.setOnItemClickListener(new OnItemClickListener() {
									@Override
									public void onItemClick(AdapterView<?> arg0,
															View arg1, int arg2, long arg3) {
										Intent intent = new Intent(
												HotelSelectResultActivity.this,
												HotelDetailActivity.class);
										Bundle bundle = new Bundle();
										bundle.putString("start", lists.get(arg2)
												.getStart());
										bundle.putString("add", lists.get(arg2)
												.getAddress());
										bundle.putString("id", lists.get(arg2)
												.getId());
										intent.putExtras(bundle);
										startActivity(intent);
									}

								});
						int length = lists.size();
						total.setText("共" + length + "家");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}

					break;

				default:
					break;
			}
		};
	};

	private ArrayList<HotelItem> parseHotel(InputStreamReader in) {
		ArrayList<HotelItem> hotelItems = null;
		HotelItem item = null;
		XmlPullParser parser = Xml.newPullParser();
		try {
			parser.setInput(in);
			int event = parser.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {
				switch (event) {
					case XmlPullParser.START_DOCUMENT:
						hotelItems = new ArrayList<HotelItem>();
						break;
					case XmlPullParser.START_TAG:

						if ("Property".equals(parser.getName())) {
							item = new HotelItem();
							item.setId(parser.getAttributeValue(null, "HotelCode"));
							item.setName(parser
									.getAttributeValue(null, "HotelName"));

						}
						if (item != null) {
							if ("Text".equals(parser.getName())) {
								item.setImg(parser.nextText());
							}
							if ("AddressLine".equals(parser.getName())) {
								item.setAddress(parser.nextText());
							}
							if ("Award".equals(parser.getName())
									&& "HotelStarRate".equals(parser
									.getAttributeValue(null, "Provider"))) {
								String s = parser.getAttributeValue(null, "Rating");
								item.setStart(s.equals("0") ? "无" : s);
							}
							if ("Position".equals(parser.getName())) {
								item.setWeidu(parser.getAttributeValue(null,
										"Latitude"));
								item.setJingdu(parser.getAttributeValue(null,
										"Longitude"));
							}
						}
						break;
					case XmlPullParser.END_TAG:
						if ("Property".equals(parser.getName())) {// 判断结束标签元素是否是book

							hotelItems.add(item);
							item = null;
						}

						break;
					default:
						break;
				}

				event = parser.next();
			}
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hotelItems;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hotel_select_result);
		// tabHost = this.getTabHost();
		// createTab2();
		// createTab3();
		initdata();
	}

	private void initdata() {
		start = (TextView) findViewById(R.id.start);
		total = (TextView) findViewById(R.id.total);
		hotel_items = (ListView) findViewById(R.id.hotel_items);
		loadHotel();

	}

	/**
	 * 关于访问查看酒店信息的数据接口
	 */
	private void loadHotel() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Bundle bundle = getIntent().getExtras();
				String city = bundle.getString("city");
				HttpAccessAdapter httpAdapter = new HttpAccessAdapter();
				String request = httpAdapter.createHotelRequestXml(city, "汉庭");
				String hotelUrl = "http://openapi.ctrip.com/Hotel/OTA_HotelSearch.asmx?wsdl";
				// String url =
				// "http://crmint.dev.sh.ctriptravel.com/Hotel/OTA_Ping.asmx?wsdl";
				String paraName = "requestXML";
				String response = httpAdapter.SendRequestToUrl(request,
						hotelUrl, paraName);
				Message msg = new Message();
				msg.what = 0;
				msg.obj = response;
				handler.sendMessage(msg);
			}
		}).start();
	}

}
