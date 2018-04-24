package com.zams.www;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.android.hengyu.web.RealmName;
import com.hengyushop.dao.WareDao;
import com.hengyushop.demo.at.AppManager;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.entity.RadioData;
import com.hengyushop.entity.ShopCartData;
import com.hengyushop.entity.UserRegisterData;
import com.lglottery.www.widget.MyPosterView;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.R;

public class OneBuyInformation extends BaseActivity {
	private String strUrl;
	private ArrayList<String> style_name;
	private int productItemId;
	private String proName, proTip;
	private String retailPrice;
	private String marketPrice;
	private String proFaceImg;
	private String proDoDetailImg;
	private String proInverseImg;
	private String proSupplementImg;
	private String proComputerInfo;
	private String proDesignImg;
	private String releaseBossUid;
	private String AvailableJuHongBao;
	private Button enter_shop;
	private ProgressBar item2;
	private int AvailableIntegral;
	private LinearLayout market_information_describe,
			market_information_describe0, add_shop;
	private MyPosterView market_information_images;
	private Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
				case -1:
					Toast.makeText(getApplicationContext(), "添加失败", 200).show();
					break;
				case 1:
					Toast.makeText(getApplicationContext(), "成功加入购物车", 200).show();
					break;
				case 0:
					tv_hengyu_money.setText("￥" + retailPrice);
					tv_market_money.setText("￥" + marketPrice);
					//
					market_information_title.setText(proName + "");
					market_information_tip.setText(proTip);
					tv_ware_market_jifen.setText(AvailableJuHongBao);
					market_information_images.setData(getData(), imageLoader);

					item2.setProgress((int) (Double.parseDouble(HasJoinedNum)
							/ Double.parseDouble(NeedGameUserNum) * 1000));
					item3.setText(HasJoinedNum);
					item4.setText(NeedGameUserNum);
					item5.setText(""
							+ (Integer.parseInt(NeedGameUserNum) - Integer
							.parseInt(HasJoinedNum)));
					LinearLayout ll_style = (LinearLayout) findViewById(R.id.ll_style);
					ll_style.removeAllViews();
					addMyLine((RadioData[]) msg.obj, ll_style);

					// 计算期数
					old_one.removeAllViews();
					int old_Number = Integer.parseInt(LuckDrawBatchOrderNumber);
					for (int i = old_Number; i > 0; i--) {
						TextView textView = new TextView(getApplicationContext());
						textView.setText("第" + i + "期");
						textView.setPadding(7, 5, 7, 5);
						textView.setTextSize(17);
						old_one.addView(textView);
						final int idex = i;
						if (i != old_Number) {
							textView.setOnClickListener(new OnClickListener() {
								@Override
								public void onClick(View arg0) {

									Intent intent = new Intent(
											OneBuyInformation.this,
											One_JiexiaoActivity.class);
									intent.putExtra("id", getIntent()
											.getStringExtra("id"));
									intent.putExtra(
											"LuckDrawBatchOrderNumber",
											String.valueOf(Integer
													.parseInt(LuckDrawBatchOrderNumber) - 1));
									intent.putExtra("idex", idex + "");
									System.out.println("期数：" + idex + "---"
											+ getIntent().getStringExtra("id"));
									startActivity(intent);

								}
							});
						} else {

						}
					}

					break;

				default:
					break;
			}
		};
	};
	private TextView tv_ware_market_jifen;
	private TextView tv_market_money;
	private TextView tv_hengyu_money;
	private TextView market_information_title, market_information_tip;
	private String NeedGameUserNum;
	private String HasJoinedNum;
	private TextView item3, item4, item5;
	private WareDao wareDao;
	private String yth, str2;
	private String key;
	private int productItemType;
	private LinearLayout images_layout;
	private LinearLayout old_one;
	private LinearLayout order_shop_now;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.market_information_layout);
		wareDao = new WareDao(getApplicationContext());
		UserRegisterData registerData = wareDao.findIsLoginHengyuCode();
		yth = registerData.getHengyuCode();
		key = registerData.getUserkey();
		productItemType = 6;
		item3 = (TextView) findViewById(R.id.item3);
		item4 = (TextView) findViewById(R.id.item4);
		item5 = (TextView) findViewById(R.id.item5);
		old_one = (LinearLayout) findViewById(R.id.old_one);
		add_shop = (LinearLayout) findViewById(R.id.add_shop);
		order_shop_now = (LinearLayout) findViewById(R.id.order_shop_now);

		market_information_describe0 = (LinearLayout) findViewById(R.id.market_information_describe0);
		item2 = (ProgressBar) findViewById(R.id.item2);
		market_information_describe = (LinearLayout) findViewById(R.id.market_information_describe);
		market_information_images = (MyPosterView) findViewById(R.id.market_information_images);
		images_layout = (LinearLayout) findViewById(R.id.images_layout);
		enter_shop = (Button) findViewById(R.id.enter_shop);
		enter_shop.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				System.out.println("点击");
				// Intent intent = new
				// Intent(OneBuyInformation.this,ShoppingCart1Activity.class);
				// startActivity(intent);
			}
		});
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int widthPixels = dm.widthPixels;// 宽度height = dm.heightPixels ;
		images_layout.setLayoutParams(new FrameLayout.LayoutParams(widthPixels,
				widthPixels));
		tv_ware_market_jifen = (TextView) findViewById(R.id.tv_ware_market_jifen);
		tv_market_money = (TextView) findViewById(R.id.tv_ware_market_money);
		market_information_tip = (TextView) findViewById(R.id.market_information_tip);
		market_information_title = (TextView) findViewById(R.id.market_information_title);
		tv_hengyu_money = (TextView) findViewById(R.id.tv_ware_hengyu_money);
		strUrl = RealmName.REALM_NAME + "/mi/getdata.ashx";
		if (getIntent().hasExtra("id")) {
			Map<String, String> params = new HashMap<String, String>();
			params.put("act", "OneProductItemInfo");
			params.put("yth", "test");
			params.put("key", "test");
			params.put("productItemType", "6");
			System.out.println("详细:" + getIntent().getStringExtra("id"));
			params.put("productItemId", getIntent().getStringExtra("id"));
			AsyncHttp.post_1(strUrl, params, new AsyncHttpResponseHandler() {

				public void onSuccess(int arg0, String arg1) {
					Message msg = handler.obtainMessage();
					msg.what = 0;
					msg.obj = parse(arg1);
					handler.sendMessage(msg);

				};
			});
			market_information_describe
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {

							Intent intent = new Intent(OneBuyInformation.this,
									WareInformationDetailsActivity.class);
							Bundle bundle = new Bundle();
							bundle.putInt("wareid", Integer
									.parseInt(getIntent().getStringExtra("id")));
							intent.putExtras(bundle);
							startActivity(intent);
						}
					});
			market_information_describe0
					.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View arg0) {

							Intent intent = new Intent(OneBuyInformation.this,
									OneAllActivity.class);
							Bundle bundle = new Bundle();
							bundle.putString("item_id", "" + productItemId);
							bundle.putString("LuckDrawBatchOrderNumber",
									LuckDrawBatchOrderNumber);
							intent.putExtras(bundle);
							startActivity(intent);
						}
					});
			add_shop.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					addShop();
				}
			});
			order_shop_now.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {


					addNow();

				}
			});
		}
	}

	/**
	 * 立即购买
	 */
	private void addNow() {
		List<UserRegisterData> username = wareDao.findisLogin();
		Log.v("number", username.size() + "");
		if (username.size() != 0) {
			if (style_name.size() >= 2) { // 当商品只有两条属性时
				if (propits[0] == null || propits[1] == null) {
					Toast.makeText(getApplicationContext(), "请选择商品的属性", 200)
							.show();
				} else {
					stylename1 = style_name.get(0).toString();
					stylename2 = style_name.get(1).toString();
					stylenature1 = propits[0].toString();
					stylenature2 = propits[1].toString();
				}
			} else if (style_name.size() == 0) { // 当商品没有属性时
				stylename1 = "";
				stylename2 = "";
				stylenature1 = "";
				stylenature2 = "";
			} else if (style_name.size() == 1) { // 当商品没有属性时
				stylename1 = style_name.get(0).toString();
				stylename2 = "";
				stylenature1 = propits[0].toString();
				stylenature2 = "";
				// style_name = "";
			}

			Intent intent = new Intent(OneBuyInformation.this,
					OrderConfrimActivity.class);
			intent.putExtra("isNow", true);
			intent.putExtra("Id",
					String.valueOf(iSLING == 0 ? CID : productItemId));
			intent.putExtra("stylename1", stylename1);
			intent.putExtra("stylename2", stylename2);
			intent.putExtra("stylenature1", stylenature1);
			intent.putExtra("stylenature2", stylenature2);
			intent.putExtra("retailPrice", retailPrice);
			intent.putExtra("productItemType", productItemType);
			intent.putExtra("AvailableIntegral", AvailableIntegral);
			intent.putExtra("totalPrice", retailPrice);
			intent.putExtra("imgurl", proFaceImg);
			intent.putExtra("number", "1");
			intent.putExtra("warename", proName);
			startActivity(intent);

		} else {
			int index = 2;
			Intent intent = new Intent(OneBuyInformation.this,
					UserLoginActivity.class);
			intent.putExtra("login", index);
			intent.putExtra("wareid", getIntent().getStringExtra("id"));
			startActivity(intent);
			AppManager.getAppManager().finishActivity();
		}
	}

	private RadioGroup groups[];
	private String propits[];
	private ArrayList<RadioButton[]> listButtons;
	private TextView[] textViews;
	private String stylename1;
	private String stylename2;
	private String stylenature1;
	private String stylenature2;
	private String str1, str5, str3, str4, str6;

	private void addMyLine(RadioData item[], LinearLayout root) {
		propits = null;
		textViews = null;
		root.removeAllViews();
		if (item != null && item.length > 0) {
			// 保证数据完整
			LinearLayout parentLayout = new LinearLayout(
					getApplicationContext());
			parentLayout.setOrientation(LinearLayout.VERTICAL);

			if (listButtons != null) {
				listButtons.clear();
			}
			listButtons = new ArrayList<RadioButton[]>();
			int len = item.length;
			System.out.println("长度" + len);
			groups = new RadioGroup[len];
			propits = new String[len];
			textViews = new TextView[len];
			for (int i = 0; i < len; i++) {
				RadioData advrt = item[i];
				LinearLayout childLayout = new LinearLayout(
						getApplicationContext());
				childLayout.removeAllViews();
				childLayout.setOrientation(LinearLayout.HORIZONTAL);
				childLayout.setLayoutParams(new LinearLayout.LayoutParams(
						android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
						0, 1));
				HorizontalScrollView scrollView = new HorizontalScrollView(
						getApplicationContext());
				TextView tagView = new TextView(getApplicationContext());
				textViews[i] = tagView;
				textViews[i].setText(advrt.getName() + ":");
				textViews[i].setTextColor(Color.rgb(1, 1, 1));
				textViews[i].setTextSize(16);
				LinearLayout.LayoutParams viewParams = new LinearLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				viewParams.setMargins(10, 0, 25, 0);
				textViews[i].setLayoutParams(viewParams);
				childLayout.addView(textViews[i]);

				int jen = advrt.getValues().size();
				groups[i] = new RadioGroup(getApplicationContext());
				groups[i].setId(i);
				groups[i].setOrientation(RadioGroup.HORIZONTAL);
				groups[i].setGravity(Gravity.CENTER_VERTICAL);
				RadioButton[] buttons = new RadioButton[jen];
				for (int j = 0; j < jen; j++) {
					RadioButton button = (RadioButton) LinearLayout.inflate(
							getApplicationContext(), R.layout.detail_radiobtn,
							null).findViewById(R.id.btn);

					buttons[j] = button;
					buttons[j].setId(100 * (i + 1) + j);
					buttons[j].setEms(3);
					buttons[j].setSingleLine(true);
					buttons[j].setGravity(Gravity.CENTER);
					buttons[j].setTextSize(15);
					buttons[j].setTextColor(Color.BLACK);
					buttons[j].setText(advrt.getValues().get(j));

					groups[i].addView(buttons[j]);

					View view = new View(getApplicationContext());
					view.setLayoutParams(new LayoutParams(10, 0));
					groups[i].addView(view);

				}
				listButtons.add(buttons);
				childLayout.setGravity(Gravity.CENTER_VERTICAL);
				scrollView.addView(groups[i]);
				childLayout.addView(scrollView);
				parentLayout.addView(childLayout);
			}
			for (int i = 0; i < len; i++) {

				groups[i]
						.setOnCheckedChangeListener(new OnCheckedChangeListener() {

							@Override
							public void onCheckedChanged(RadioGroup arg0,
														 int arg1) {

								int index = arg0.getId();
								for (int o = 0; o < listButtons.get(index).length; o++) {
									if (listButtons.get(index)[o].getId() == arg1) {
										propits[index] = listButtons.get(index)[o]
												.getText().toString();

									}
								}
							}
						});
			}
			parentLayout
					.setLayoutParams(new LinearLayout.LayoutParams(
							android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
							android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
							len));
			root.addView(parentLayout);

		}
	}

	private int iSLING = -1;
	private int CID;

	private String processParam(String temp)
			throws UnsupportedEncodingException {
		return URLEncoder.encode(temp, "UTF-8");
	}

	private int index = 1;
	private int ischecked = 1;
	private int Orderid;
	private String LuckDrawBatchOrderNumber;

	private void addShop() {

		List<UserRegisterData> username = wareDao.findisLogin();
		Log.v("number", username.size() + "");
		if (username.size() != 0) {
			if (style_name.size() >= 2) { // 当商品只有两条属性时
				if (propits[0] == null || propits[1] == null) {
					Toast.makeText(getApplicationContext(), "请选择商品的属性", 200)
							.show();
				} else {
					stylename1 = style_name.get(0).toString();
					stylename2 = style_name.get(1).toString();
					stylenature1 = propits[0].toString();
					stylenature2 = propits[1].toString();
					ShopCartData shopData = wareDao.findTwoStyle(productItemId
									+ "", stylename1, stylenature1, stylename2,
							stylenature2);
					int number = shopData.getNumber();
					String orderid = shopData.getOrderid() + "";
					if (number != 0) {
						ShopCartData data = new ShopCartData();
						data.setNumber(number + 1);
						wareDao.updateByOrderid(orderid, data);
						str1 = RealmName.REALM_NAME
								+ "/mi/receiveOrderInfo.ashx?"
								+ "act=UpdateCartInfoNum&yth=" + yth
								+ "&ProductOrderItemId=" + orderid
								+ "&productCount=" + (number + 1)
								+ "&totalProductPrice=" + retailPrice;

						AsyncHttp.get(str1, new AsyncHttpResponseHandler() {
							@Override
							public void onSuccess(int arg0, String arg1) {

								super.onSuccess(arg0, arg1);
								Message message = new Message();
								message.what = 1;
								handler.sendMessage(message);
							}
						}, getApplicationContext());

					} else {
						new Thread() {

							public void run() {
								try {

									str2 = RealmName.REALM_NAME
											+ "/mi/receiveOrderInfo.ashx?"
											+ "act=InsertOneProductOrderItem&yth="
											+ yth
											+ "&productItemId="
											+ (iSLING == 0 ? CID
											: productItemId)
											+ "&productCount=1"
											+ "&oneProductPrice=" + retailPrice
											+ "&totalProductPrice="
											+ retailPrice
											+ "&sellPropertyName1="
											+ processParam(stylename1)
											+ "&sellPropertyValue1="
											+ processParam(stylenature1)
											+ "&sellPropertyName2="
											+ processParam(stylename2)
											+ "&sellPropertyValue2="
											+ processParam(stylenature2)
											+ "&productItemType="
											+ productItemType;

									AsyncHttp.get(str2,
											new AsyncHttpResponseHandler() {
												public void onSuccess(int arg0,
																	  String arg1) {
													try {
														JSONObject object = new JSONObject(
																arg1);
														System.out
																.println("销售属性2"
																		+ str2);
														int status = object
																.getInt("status");
														if (status == 1) {
															Orderid = object
																	.getInt("ProductOrderItemId");
															ShopCartData data = new ShopCartData();
															data.setWareid(productItemId);
															data.setImgurl(proFaceImg);
															data.setWarename(proName);
															data.setRetailprice(retailPrice);
															data.setMarketprice(marketPrice);
															data.setStylenameone(stylename1);
															data.setStylenatureone(stylenature1);
															data.setStylenametwo(stylename2);
															data.setStylenaturetwo(stylenature2);
															data.setNumber(index);
															data.setIschecked(ischecked);
															data.setOrderid(Orderid);
															data.setJf(AvailableIntegral);
															wareDao.insertShopCartTwoStyle(data);
															Message message = new Message();
															message.what = 1;
															handler.sendMessage(message);
														}
													} catch (JSONException e) {

														// catch block
														e.printStackTrace();
													}
												};
											}, getApplicationContext());

								} catch (Exception e) {

									e.printStackTrace();
								}
							};
						}.start();
					}
				}

			} else if (style_name.size() == 0) { // 当商品没有属性时
				ShopCartData shopData = wareDao.findNoStyle(productItemId + "");
				int number = shopData.getNumber();
				String orderid = shopData.getOrderid() + "";
				if (number != 0) {
					ShopCartData data = new ShopCartData();
					data.setNumber(number + 1);
					wareDao.updateByOrderid(orderid, data);

					str3 = RealmName.REALM_NAME + "/mi/receiveOrderInfo.ashx?"
							+ "act=UpdateCartInfoNum&yth=" + yth
							+ "&ProductOrderItemId=" + orderid
							+ "&productCount=" + (number + 1)
							+ "&totalProductPrice=" + retailPrice;

					AsyncHttp.get(str3, new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int arg0, String arg1) {

							super.onSuccess(arg0, arg1);
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
						}
					}, getApplicationContext());
				} else {
					new Thread() {
						public void run() {

							str4 = RealmName.REALM_NAME
									+ "/mi/receiveOrderInfo.ashx?"
									+ "act=InsertOneProductOrderItem&yth="
									+ yth
									+ "&productItemId="
									+ (iSLING == 0 ? CID : productItemId)
									+ "&productCount=1"
									+ "&oneProductPrice="
									+ retailPrice
									+ "&totalProductPrice="
									+ retailPrice
									+ "&sellPropertyName1=&sellPropertyValue1=&sellPropertyName2=&sellPropertyValue2="
									+ "&productItemType=" + productItemType;
							try {
								AsyncHttp.get(str4,
										new AsyncHttpResponseHandler() {
											@Override
											public void onSuccess(int arg0,
																  String arg1) {
												  method
												// stub
												super.onSuccess(arg0, arg1);
												try {
													JSONObject object = new JSONObject(
															arg1);
													int status = object
															.getInt("status");
													if (status == 1) {
														Orderid = object
																.getInt("ProductOrderItemId");
														ShopCartData data = new ShopCartData();
														data.setWareid(productItemId);
														data.setImgurl(proFaceImg);
														data.setWarename(proName);
														data.setRetailprice(retailPrice);
														data.setMarketprice(marketPrice);
														data.setNumber(index);
														data.setIschecked(ischecked);
														data.setOrderid(Orderid);
														data.setJf(AvailableIntegral);
														wareDao.insertShopCartNoStyle(data);
														Message message = new Message();
														message.what = 1;
														handler.sendMessage(message);
													}
												} catch (JSONException e) {
													  catch
													// block
													e.printStackTrace();
												}
											}
										}, getApplicationContext());

							} catch (Exception e) {

								e.printStackTrace();
							}
						}
					}.start();
				}
			} else if (style_name.size() == 1) { // 当商品只有一条属性时
				if (propits[0] == null) {
					Toast.makeText(getApplicationContext(), "请选择商品的属性", 200)
							.show();
				} else {
					stylename1 = style_name.get(0).toString();
					stylenature1 = propits[0].toString();
					ShopCartData shopData = wareDao.findOneStyle(productItemId
							+ "", stylename1, stylenature1);
					int number = shopData.getNumber();
					String orderid = shopData.getOrderid() + "";
					if (number != 0) {
						ShopCartData data = new ShopCartData();
						data.setNumber(number + 1);
						wareDao.updateByOrderid(orderid, data);
						str5 = RealmName.REALM_NAME
								+ "/mi/receiveOrderInfo.ashx?"
								+ "act=UpdateCartInfoNum&yth=" + yth
								+ "&ProductOrderItemId=" + orderid
								+ "&productCount=" + (number + 1)
								+ "&totalProductPrice=" + retailPrice;
						System.out.println("销售属性1" + str5);

						AsyncHttp.get(str5, new AsyncHttpResponseHandler() {
							@Override
							public void onSuccess(int arg0, String arg1) {

								super.onSuccess(arg0, arg1);
								Message message = new Message();
								message.what = 1;
								handler.sendMessage(message);
							}
						}, getApplicationContext());

					} else {
						new Thread() {
							@Override
							public void run() {

								super.run();
								try {
									str6 = RealmName.REALM_NAME
											+ "/mi/receiveOrderInfo.ashx?"
											+ "act=InsertOneProductOrderItem&yth="
											+ yth
											+ "&productItemId="
											+ (iSLING == 0 ? CID
											: productItemId)
											+ "&productCount=1"
											+ "&oneProductPrice="
											+ retailPrice
											+ "&totalProductPrice="
											+ retailPrice
											+ "&sellPropertyName1="
											+ processParam(stylename1)
											+ "&sellPropertyValue1="
											+ processParam(stylenature1)
											+ "&sellPropertyName2=&sellPropertyValue2="
											+ "&productItemType="
											+ productItemType;
									AsyncHttp.get(str6,
											new AsyncHttpResponseHandler() {
												@Override
												public void onSuccess(int arg0,
																	  String arg1) {

													// method stub
													super.onSuccess(arg0, arg1);
													try {
														JSONObject object = new JSONObject(
																arg1);
														int status = object
																.getInt("status");
														if (status == 1) {
															Orderid = object
																	.getInt("ProductOrderItemId");
															ShopCartData data = new ShopCartData();
															data.setWareid(productItemId);
															data.setImgurl(proFaceImg);
															data.setWarename(proName);
															data.setRetailprice(retailPrice);
															data.setMarketprice(marketPrice);
															data.setStylenameone(stylename1);
															data.setStylenatureone(stylenature1);
															data.setNumber(index);
															data.setIschecked(ischecked);
															data.setOrderid(Orderid);
															data.setJf(AvailableIntegral);
															wareDao.insertShopCartOneStyle(data);

															handler.sendEmptyMessage(1);
														}
													} catch (JSONException e) {

														// catch block
														e.printStackTrace();
													}
												}
											}, getApplicationContext());

								} catch (Exception e) {

									e.printStackTrace();
								}
							}
						}.start();
					}
				}
			}
			// Intent intent = new Intent(WareInformationActivity.this,
			// ShoppingCartActivity.class);
			// startActivity(intent);
		} else {
			int index = 2;
			Intent intent = new Intent(OneBuyInformation.this,
					UserLoginActivity.class);
			intent.putExtra("login", index);
			intent.putExtra("wareid", getIntent().getStringExtra("id"));
			startActivity(intent);
			AppManager.getAppManager().finishActivity();
		}

	}

	private RadioData[] parse(String st) {
		RadioData advrt[] = null;
		try {
			JSONObject jsonObject = new JSONObject(st);
			JSONArray jsonArray = jsonObject.getJSONArray("items");

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject object = jsonArray.getJSONObject(i);
				productItemId = object.getInt("productItemId");
				proName = object.getString("proName");
				proTip = object.getString("commendMessage");
				System.out.println("=" + getIntent().getStringExtra("tag"));
				if (getIntent().getStringExtra("tag") == null) {
					retailPrice = object.getString("retailPrice");
				} else {
					if (getIntent().getStringExtra("tag").equals("0")) {
						// 零价商城
						retailPrice = "0";
					} else if (getIntent().getStringExtra("tag").equals("1")) {
						retailPrice = "1";
					} else if (getIntent().getStringExtra("tag").equals("6")) {
						retailPrice = getIntent().getStringExtra("price");
					}
				}
				System.out.println(object.getString("proName") + "....."
						+ object.getString("marketPrice"));

				marketPrice = object.getString("marketPrice");
				proFaceImg = object.getString("proFaceImg");
				System.out.println("图片地址:" + proFaceImg);
				proInverseImg = object.getString("proInverseImg");
				proDoDetailImg = object.getString("proDoDetailImg");
				proDesignImg = object.getString("proDesignImg");
				proSupplementImg = object.getString("proSupplementImg");
				proComputerInfo = object.getString("proComputerInfo");
				releaseBossUid = object.getString("releaseBossUid");
				AvailableJuHongBao = object.getString("AvailableJuHongBao");
				NeedGameUserNum = object.getString("NeedGameUserNum");
				HasJoinedNum = object.getString("HasJoinedNum");
				LuckDrawBatchOrderNumber = object
						.getString("LuckDrawBatchOrderNumber");
				// "NeedGameUserNum": "90",
				// "HasJoinedNum": "0",

				String abe = object.getString("AvailableIntegral");

				if (getIntent().hasExtra("fen")) {
					AvailableIntegral = Integer.parseInt(getIntent()
							.getStringExtra("fen"));
				} else {
					if (abe.length() == 0) {
						AvailableIntegral = 0;
					} else {
						AvailableIntegral = (int) Double.parseDouble(object
								.getString("AvailableIntegral"));
					}
				}
				// AvailableIntegral = 2;//测试
				JSONArray jsonArray2 = object.getJSONArray("sellPropertyName");
				System.out.println("jsonArray2" + jsonArray2.length());
				advrt = new RadioData[jsonArray2.length()];
				style_name = new ArrayList<String>();
				System.out.println("属性" + jsonArray2.length());
				for (int j = 0; j < jsonArray2.length(); j++) {
					RadioData data = new RadioData();
					JSONObject object2 = jsonArray2.getJSONObject(j);
					String name = object2.getString("sellPropertyName");
					style_name.add(name);
					data.setName(name);

					JSONArray jsonArray3 = object2
							.getJSONArray("sellPropertyValue");
					ArrayList<String> value = new ArrayList<String>();
					for (int k = 0; k < jsonArray3.length(); k++) {
						JSONObject object3 = jsonArray3.getJSONObject(k);
						String str = object3.getString("sellPropertyValue");
						value.add(str);
						data.setValues(value);
					}
					advrt[j] = data;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return advrt;
	}

	private ArrayList<String> getData() {
		ArrayList<String> list = new ArrayList<String>();
		if (!"".equals(proFaceImg)) {
			list.add(RealmName.REALM_NAME + "/admin/" + proFaceImg);
		}
		if (!"".equals(proInverseImg)) {
			list.add(RealmName.REALM_NAME + "/admin/" + proInverseImg);
		}
		if (!"".equals(proDoDetailImg)) {
			list.add(RealmName.REALM_NAME + "/admin/" + proDoDetailImg);
		}
		if (!"".equals(proDesignImg)) {
			list.add(RealmName.REALM_NAME + "/admin/" + proDesignImg);
		}
		if (!"".equals(proSupplementImg)) {
			list.add(RealmName.REALM_NAME + "/admin/" + proSupplementImg);
		}
		return list;
	}
}
