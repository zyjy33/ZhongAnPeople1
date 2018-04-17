package com.hengyushop.demo.hotel;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Xml;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ctrip.openapi.java.base.HttpAccessAdapter;
import com.hengyushop.demo.at.BaseActivity;
import com.zams.www.R;

@SuppressWarnings("deprecation")
public class HotelDetailActivity extends BaseActivity {
	private TextView hotel_detail_name;
	private Gallery hor_images;
	private TextView hotel_detail_add, hotel_detail_start;
	private HotelImagesAdapter imagesAdapter;
	private HotelPriceAdapter priceAdapter;
	private ListView hotel_detail_price;
	private ArrayList<HotelDetialPriceDo> priceLists;
	// private HotelDetailDo detailDo;
	private String str = null;
	String tempStart = "2014-05-20";
	String tempEnd = "2014-05-21";
	private Map<String, String> typeMap = null;
	private Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
			case 0:
				str = (String) msg.obj;
				System.out.println(str);
				InputStreamReader reader;
				try {
					reader = new InputStreamReader(new ByteArrayInputStream(
							str.getBytes("UTF-8")));
					HotelDetailDo detailDo = parseDetailHotel(reader);
					hotel_detail_name.setText(detailDo.getHotelName());
					imagesAdapter = new HotelImagesAdapter(
							detailDo.getImageDos(), getApplicationContext(),
							imageLoader);
					hor_images.setAdapter(imagesAdapter);
					loadHotelPrice();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				break;
			case 1:
				// ���ؼ۸���Ϣ

				try {
					String priceResult = (String) msg.obj;
					InputStreamReader reader2;
					reader2 = new InputStreamReader(new ByteArrayInputStream(
							priceResult.getBytes("UTF-8")));
					priceLists = parseDetailPrice(reader2);
					priceAdapter = new HotelPriceAdapter(priceLists,
							getApplicationContext(), imageLoader, handler);
					hotel_detail_price.setAdapter(priceAdapter);
					setListViewHeightBasedOnChildren(hotel_detail_price);
					hotel_detail_price
							.setOnItemClickListener(new OnItemClickListener() {

								@Override
								public void onItemClick(AdapterView<?> arg0,
										View arg1, int arg2, long arg3) {
									try {
										InputStreamReader reader = new InputStreamReader(
												new ByteArrayInputStream(str
														.getBytes("UTF-8")));
										initPopupWindow(reader,
												priceLists.get(arg2).getId(),
												priceLists.get(arg2)
														.getRoomPrice());
										showPopupWindow(arg1);
									} catch (UnsupportedEncodingException e) {
										e.printStackTrace();
									}
								}
							});
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 2:
				// ���Ԥ����ť֮�󴥷����¼�
				String plamId = (String) msg.obj;
				loadCheckPlam(plamId);
				break;
			default:
				break;
			}
		};
	};

	/**
	 * ��鷿���Ƿ��Ԥ��
	 */
	private void loadCheckPlam(final String planId) {
		new Thread() {
			public void run() {
				Bundle bundle = getIntent().getExtras();
				String hotelCode = bundle.getString("id");
				HttpAccessAdapter httpAdapter = new HttpAccessAdapter();
				String request = httpAdapter.createHotelCheckRequestXml(
						hotelCode, tempStart, tempEnd, "2", planId);
				String hotelUrl = "http://openapi.ctrip.com/Hotel/OTA_HotelAvail.asmx?wsdl";
				// String url =
				// "http://crmint.dev.sh.ctriptravel.com/Hotel/OTA_Ping.asmx?wsdl";
				String paraName = "requestXML";
				String response = httpAdapter.SendRequestToUrl(request,
						hotelUrl, paraName);
				Message msg = new Message();
				msg.what = 3;
				msg.obj = response;
				System.out.println("��鷿��:" + response);
				handler.sendMessage(msg);

			};
		}.start();
	}

	/**
	 * @param listView
	 */
	private void setListViewHeightBasedOnChildren(ListView listView) {

		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

	private void init() {
		Bundle bundle = getIntent().getExtras();
		hotel_detail_add = (TextView) findViewById(R.id.hotel_detail_add);
		hotel_detail_start = (TextView) findViewById(R.id.hotel_detail_start);
		hor_images = (Gallery) findViewById(R.id.hor_images);
		hotel_detail_name = (TextView) findViewById(R.id.hotel_detail_name);
		hotel_detail_price = (ListView) findViewById(R.id.hotel_detail_price);
		hotel_detail_start.setText(bundle.getString("start") + "�Ǽ�");
		hotel_detail_add.setText(bundle.getString("add"));
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hotel_detail);
		initBedType();
		init();
		loadHotelDetail();

	}

	/**
	 * ��������
	 */
	private HotelDetailDo parseDetailHotel(InputStreamReader in) {
		XmlPullParser parser = Xml.newPullParser();
		HotelDetailDo detailDo = null;
		HotelImageDo imageDo = null;
		ArrayList<HotelImageDo> images = null;
		try {
			parser.setInput(in);
			int event = parser.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {
				switch (event) {
				case XmlPullParser.START_DOCUMENT:
					images = new ArrayList<HotelImageDo>();
					break;
				case XmlPullParser.START_TAG:
					if ("HotelDescriptiveContents".equals(parser.getName())) {
						detailDo = new HotelDetailDo();

					}
					if (detailDo != null) {
						if ("HotelDescriptiveContent".equals(parser.getName())) {
							detailDo.setHotelName(parser.getAttributeValue(
									null, "HotelName"));
						}

						if ("ImageItem".equals(parser.getName())) {

							// ��ǿ�ʼ��������
							imageDo = new HotelImageDo();

						}
						if ("URL".equals(parser.getName())) {

							String url = parser.nextText();
							imageDo.setUrl(url);
						}
						if ("Description".equals(parser.getName())) {
							imageDo.setTag(parser.getAttributeValue(null,
									"Caption"));
						}

					}
					break;
				case XmlPullParser.END_TAG:
					if ("ImageItem".equals(parser.getName())) {
						images.add(imageDo);
						// imageDo = null;
					}
					if ("MultimediaDescription".equals(parser.getName())) {
						detailDo.setImageDos(images);
						System.out.println(detailDo.getImageDos().size());
						// System.out.println(images.get(0).getUrl());
						// System.out.println(images.get(1).getUrl());
					}
					break;

				default:
					break;
				}
				event = parser.next();
			}

		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return detailDo;
	}

	/**
	 * �����۸�����
	 */
	private ArrayList<HotelDetialPriceDo> parseDetailPrice(InputStreamReader in) {
		XmlPullParser parser = Xml.newPullParser();
		ArrayList<HotelDetialPriceDo> lists = null;
		HotelDetialPriceDo priceDo = null;
		int tag = -1;
		try {
			parser.setInput(in);
			int event = parser.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {
				switch (event) {
				case XmlPullParser.START_DOCUMENT:
					lists = new ArrayList<HotelDetialPriceDo>();
					break;
				case XmlPullParser.START_TAG:
					if ("RatePlan".equals(parser.getName())) {
						// �����ǩ��ʼ����һ�����������
						priceDo = new HotelDetialPriceDo();
						priceDo.setId(parser.getAttributeValue(null,
								"RatePlanCode"));
					}
					if (tag == 0) {
						// ��ʾ�ǽ�������SellableProducts����ı�ǩ
						if ("Description".equals(parser.getName())) {
							priceDo.setRoomName(parser.getAttributeValue(null,
									"Name"));
						}
					}
					if ("BaseByGuestAmt".equals(parser.getName())) {
						priceDo.setRoomPrice(parser.getAttributeValue(null,
								"AmountBeforeTax"));

					}

					break;
				case XmlPullParser.END_TAG:
					if ("SellableProducts".equals(parser.getName())) {
						tag = 0;
					} else {
						tag = -1;
					}
					if ("RatePlan".equals(parser.getName())) {
						// ���������Ľ���
						lists.add(priceDo);
					}
					break;

				default:
					break;
				}
				event = parser.next();
			}

		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lists;
	}

	/**
	 * ��������
	 */
	private void loadHotelDetail() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				Bundle bundle = getIntent().getExtras();
				String id = bundle.getString("id");
				HttpAccessAdapter httpAdapter = new HttpAccessAdapter();
				String request = httpAdapter.createHotelDetailRequestXml(id);
				String hotelUrl = "http://openapi.ctrip.com/Hotel/OTA_HotelDescriptiveInfo.asmx?wsdl";
				// String url =
				// "http://crmint.dev.sh.ctriptravel.com/Hotel/OTA_Ping.asmx?wsdl";
				String paraName = "requestXML";
				String response = httpAdapter.SendRequestToUrl(request,
						hotelUrl, paraName);
				Message msg = new Message();
				msg.what = 0;
				msg.obj = response;
				System.out.println(response);
				handler.sendMessage(msg);
			}
		}).start();
	}

	private LayoutInflater mLayoutInflater;
	private View popView;
	private PopupWindow mPopupWindow;

	private void initPopupWindow(InputStreamReader parseResult, String id,
			String price) {
		mLayoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		popView = mLayoutInflater.inflate(R.layout.hotel_price_item, null);
		mPopupWindow = new PopupWindow(popView, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		// mPopupWindow.setBackgroundDrawable(new
		// BitmapDrawable());//��������background������ʧ
		mPopupWindow.setBackgroundDrawable(getResources().getDrawable(
				R.color.ban_louming));
		mPopupWindow.setOutsideTouchable(true);
		// �Զ��嶯��
		// mPopupWindow.setAnimationStyle(R.style.PopupAnimation);
		// ʹ��ϵͳ����
		mPopupWindow.setAnimationStyle(android.R.style.Animation_Toast);
		mPopupWindow.update();
		mPopupWindow.setTouchable(true);
		mPopupWindow.setFocusable(true);
		HotelPriceItemDo doPrice = parseHotel(parseResult, id);
		TextView v0 = (TextView) popView.findViewById(R.id.v0);
		TextView v1 = (TextView) popView.findViewById(R.id.v1);
		TextView v2 = (TextView) popView.findViewById(R.id.v2);
		TextView v3 = (TextView) popView.findViewById(R.id.v3);
		TextView v4 = (TextView) popView.findViewById(R.id.v4);
		TextView v5 = (TextView) popView.findViewById(R.id.v5);
		TextView v = (TextView) popView.findViewById(R.id.v);
		v1.setText(doPrice.getQuantity() + "��");
		v2.setText(doPrice.getFloor());
		v3.setText(doPrice.getBedType() + " (" + doPrice.getSize() + ")");
		v4.setText(doPrice.getDescribe());
		v5.setText(price);
		v0.setText(doPrice.getStandardOccupancy() + "��");
		v.setText(doPrice.getRoomName());

	}

	/*
	 * 1 ˫�� 2 Futon 3 �� 4 Murphy bed 5 Queen 6 Sofa bed 7 Tatami mats 8 2�ŵ��˴� 9
	 * ���˴� 10 Full 11 Run of the house 12 Dorm bed 501 �󴲻�˫�� 502 �󴲻򵥴� 502 ������˫��
	 */
	private void initBedType() {
		typeMap = new HashMap<String, String>();
		typeMap.put("1", "˫��");
		typeMap.put("2", "Futon");
		typeMap.put("3", "��");
		typeMap.put("4", "Murphy bed");
		typeMap.put("5", "Queen");
		typeMap.put("6", "Sofa bed");
		typeMap.put("7", "Tatami mats");
		typeMap.put("8", "2�ŵ��˴�");
		typeMap.put("9", "���˴�");
		typeMap.put("10", "Full");
		typeMap.put("11", "Run of the house");
		typeMap.put("12", "Dorm bed");
		typeMap.put("501", "�󴲻�˫��");
		typeMap.put("502", "�󴲻򵥴�");
		typeMap.put("503", "������˫��");

	}

	private HotelPriceItemDo parseHotel(InputStreamReader in, String hotelCode) {

		XmlPullParser parser = Xml.newPullParser();
		HotelPriceItemDo priceDo = null;

		try {
			parser.setInput(in);
			int event = parser.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {
				switch (event) {
				case XmlPullParser.START_DOCUMENT:
					priceDo = new HotelPriceItemDo();
					break;
				case XmlPullParser.START_TAG:
					if ("GuestRoom".equals(parser.getName())) {
						priceDo.setRoomName(parser.getAttributeValue(null,
								"RoomTypeName"));
					}
					if ("TypeRoom".equals(parser.getName())) {
						String roomCode = parser.getAttributeValue(null,
								"RoomTypeCode");
						System.out.println(roomCode + "==>" + hotelCode);
						if (roomCode.equals(hotelCode)) {
							System.out.println("???");
							priceDo.setStandardOccupancy(parser
									.getAttributeValue(null,
											"StandardOccupancy"));
							priceDo.setFloor(parser.getAttributeValue(null,
									"Floor"));
							priceDo.setQuantity(parser.getAttributeValue(null,
									"Quantity"));
							String bedType = typeMap.get(parser
									.getAttributeValue(null, "BedTypeCode"));
							priceDo.setBedType(bedType);
							priceDo.setSize(parser.getAttributeValue(null,
									"Size"));

							System.out.println("\\\\" + priceDo.getBedType());
						}
					}
					if ("DescriptiveText".equals(parser.getName())) {
						priceDo.setDescribe(parser.nextText());
					}
					break;
				case XmlPullParser.END_TAG:

					break;

				default:
					break;
				}
				event = parser.next();
			}

		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return priceDo;

	}

	private void showPopupWindow(View view) {
		if (!mPopupWindow.isShowing()) {
			// mPopupWindow.showAsDropDown(view,0,0);
			// ��һ������ָ��PopupWindow��ê��view�����������ĸ�view�ϡ�
			// �ڶ�������ָ����ʼ��Ϊparent�����½ǣ�����������������parent�����½�Ϊԭ�㣬�����ϸ�ƫ��10���ء�
			// int[] location = new int[2];
			// view.getLocationOnScreen(location);
			mPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
		}
	}

	private void loadHotelPrice() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				Bundle bundle = getIntent().getExtras();
				String id = bundle.getString("id");
				HttpAccessAdapter httpAdapter = new HttpAccessAdapter();
				String request = httpAdapter.createHotelPriceXml(tempStart,
						tempEnd, id);
				String hotelUrl = "http://openapi.ctrip.com/Hotel/OTA_HotelRatePlan.asmx?wsdl";
				// String url =
				// "http://crmint.dev.sh.ctriptravel.com/Hotel/OTA_Ping.asmx?wsdl";
				String paraName = "requestXML";
				String response = httpAdapter.SendRequestToUrl(request,
						hotelUrl, paraName);
				Message msg = new Message();
				msg.what = 1;
				msg.obj = response;
				System.out.println(response);
				handler.sendMessage(msg);
			}
		}).start();
	}
}
