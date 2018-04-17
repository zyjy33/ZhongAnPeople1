package com.hengyushop.demo.my;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.pub.MyJuDuiHuanOrderAdapter;
import com.android.hengyu.pub.MyOrderllAdapter;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.android.pliay.PayResult;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.demo.home.ZhiFuOKActivity;
import com.hengyushop.entity.MyOrderData;
import com.hengyushop.entity.OrderBean;
import com.hengyushop.entity.UserRegisterllData;
import com.lglottery.www.widget.PullToRefreshView;
import com.lglottery.www.widget.PullToRefreshView.OnFooterRefreshListener;
import com.lglottery.www.widget.PullToRefreshView.OnHeaderRefreshListener;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.R;

/**
 * 
 * �ҵľ۶һ�
 * 
 * @author Administrator
 * 
 */
public class MyJuDuiHuanActivity extends BaseActivity implements
		OnClickListener {
	private ImageView iv_fanhui, cursor1, cursor2, cursor3, cursor4, cursor5;
	private Button fanhui, btn_chongzhi;
	private LinearLayout index_item0, index_item1, index_item2, index_item3,
			index_item4;
	private SharedPreferences spPreferences;
	private PullToRefreshView refresh;
	private ListView my_list;
	private MyJuDuiHuanOrderAdapter madapter;
	private ArrayList<MyOrderData> list;
	// private List<OrderBean> lists;
	MyOrderData md;
	OrderBean mb;
	private DialogProgress progress;
	String user_name, user_id, login_sign, order_no;
	int len;
	ImageView imageView1;
	// String strwhere = "datatype=2";
	String strwhere = "datatype%20in(2)";
	String recharge_no, total_c;
	LinearLayout no_data_no;
	String payment_status;
	public static boolean teby = false;
	public static String notify_url;
	public static boolean zhuangtai = false;
	public static String province, city, area, user_address, name, user_mobile;
	public static String datetime, sell_price, give_pension, article_id;
	TextView textView1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_my_order_list);
		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		user_name = spPreferences.getString("user", "");
		user_id = spPreferences.getString("user_id", "");
		progress = new DialogProgress(MyJuDuiHuanActivity.this);
		Initialize();
		list = new ArrayList<MyOrderData>();
		madapter = new MyJuDuiHuanOrderAdapter(list, MyJuDuiHuanActivity.this,
				handler);
		my_list.setAdapter(madapter);

		// strwhere = "datatype=1";
		// load_list(true, strwhere);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		try {
			System.out.println("״̬==============" + zhuangtai);
			// ���֧������
			if (teby == true) {
				// userloginqm();
				// finish();
			}

			// ΢��֧���ɹ���رմ˽���
			// if (zhuangtai == true) {
			// userloginqm();
			// }
			String type = getIntent().getStringExtra("num");
			if (type.equals("1")) {
				textView1.setText("��������");
			} else if (type.equals("2")) {
				textView1.setText("�ҵĶһ�");
			} else if (type.equals("3")) {
				textView1.setText("�ҵ�ƴ��");
			}

			// �ж�״̬������
			String status = getIntent().getStringExtra("status");
			if (status != null) {
				if (status.equals("0")) {
					item1();
					strwhere = "datatype=2";
					// list.clear();
					load_list(true, strwhere);
				} else if (status.equals("1")) {
					item2();
					strwhere = "payment_status=1%20and%20datatype=2";
					// list.clear();
					load_list(true, strwhere);
				} else if (status.equals("2")) {
					item3();
					strwhere = "payment_status=2%20and%20express_status=1%20and%20datatype=2";
					// list.clear();
					load_list(true, strwhere);
				} else if (status.equals("3")) {
					item4();
					strwhere = "payment_status=2%20and%20express_status=2%20and%20status=2%20and%20datatype=2";
					// list.clear();
					load_list(true, strwhere);
				} else if (status.equals("4")) {
					item5();
					strwhere = "payment_status=2%20and%20express_status=2%20and%20status=3%20and%20datatype=2";
					// list.clear();
					load_list(true, strwhere);
				}
			} else {
				load_list(true, strwhere);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
    //��Activity������ʱ�����onDestory����
    @Override  
    protected void onDestroy() {  
        super.onDestroy(); 
    	BitmapDrawable bd = (BitmapDrawable)imageView1.getBackground();
		imageView1.setBackgroundResource(0);//�����˰ѱ�����Ϊnull������onDrawˢ�±���ʱ�����used a recycled bitmap����
		bd.setCallback(null);
		bd.getBitmap().recycle();
		
    	if (MyJuDuiHuanOrderAdapter.type == true) {
    		MyJuDuiHuanOrderAdapter.mAq.clear();
    		MyJuDuiHuanOrderAdapter.type = false;
		}
    	
    	if (list.size() > 0) {
    		list.clear();
    		list = null;
		}
    } 

	/**
	 * �ؼ���ʼ��
	 */
	private void Initialize() {

		try {
			imageView1 =  (ImageView) findViewById(R.id.iv_no_data);
			Bitmap bm = BitmapFactory.decodeResource(this.getResources(), R.drawable.no_data);
			BitmapDrawable bd = new BitmapDrawable(this.getResources(), bm);
			imageView1.setBackgroundDrawable(bd);
			
			textView1 = (TextView) findViewById(R.id.textView1);
			refresh = (PullToRefreshView) findViewById(R.id.refresh);
			refresh.setOnHeaderRefreshListener(listHeadListener);
			refresh.setOnFooterRefreshListener(listFootListener);
			my_list = (ListView) findViewById(R.id.new_list);
			no_data_no = (LinearLayout) findViewById(R.id.no_data_no);
			index_item0 = (LinearLayout) findViewById(R.id.index_item0);
			index_item1 = (LinearLayout) findViewById(R.id.index_item1);
			index_item2 = (LinearLayout) findViewById(R.id.index_item2);
			index_item3 = (LinearLayout) findViewById(R.id.index_item3);
			index_item4 = (LinearLayout) findViewById(R.id.index_item4);
			cursor1 = (ImageView) findViewById(R.id.cursor1);
			cursor2 = (ImageView) findViewById(R.id.cursor2);
			cursor3 = (ImageView) findViewById(R.id.cursor3);
			cursor4 = (ImageView) findViewById(R.id.cursor4);
			cursor5 = (ImageView) findViewById(R.id.cursor5);
			index_item0.setOnClickListener(this);
			index_item1.setOnClickListener(this);
			index_item2.setOnClickListener(this);
			index_item3.setOnClickListener(this);
			index_item4.setOnClickListener(this);

			ImageView imageView = (ImageView) findViewById(R.id.iv_no_data);
			imageView.setBackgroundResource(R.drawable.no_data);
			imageView.clearAnimation();

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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.index_item0:
			// List<Double> list0 = MyOrderllAdapter.list_monney;
			// if (list0.size() > 0) {
			// list0.clear();//�����ܼ��б����
			// }
			item1();
			list = new ArrayList<MyOrderData>();
			madapter = new MyJuDuiHuanOrderAdapter(list,
					MyJuDuiHuanActivity.this, handler);
			my_list.setAdapter(madapter);
			strwhere = "datatype=2";
			System.out.println("=========11============" + strwhere);
			load_list(true, strwhere);
			break;
		case R.id.index_item1:
			// List<Double> list1 = MyOrderllAdapter.list_monney;
			// if (list1.size() > 0) {
			// list1.clear();//�����ܼ��б����
			// }
			item2();
			list = new ArrayList<MyOrderData>();
			madapter = new MyJuDuiHuanOrderAdapter(list,
					MyJuDuiHuanActivity.this, handler);
			my_list.setAdapter(madapter);
			strwhere = "payment_status=1%20and%20datatype=2";
			System.out.println("=========22============" + strwhere);
			load_list(true, strwhere);
			break;
		case R.id.index_item2:
			// List<Double> list2 = MyOrderllAdapter.list_monney;
			// if (list2.size() > 0) {
			// list2.clear();//�����ܼ��б����
			// }
			item3();
			list = new ArrayList<MyOrderData>();
			madapter = new MyJuDuiHuanOrderAdapter(list,
					MyJuDuiHuanActivity.this, handler);
			my_list.setAdapter(madapter);
			strwhere = "payment_status=2%20and%20express_status=1%20and%20datatype=2";
			System.out.println("=========33============" + strwhere);
			load_list(true, strwhere);
			break;
		case R.id.index_item3:
			// List<Double> list3 = MyOrderllAdapter.list_monney;
			// if (list3.size() > 0) {
			// list3.clear();//�����ܼ��б����
			// }
			item4();
			list = new ArrayList<MyOrderData>();
			strwhere = "payment_status=2%20and%20express_status=2%20and%20status=2%20and%20datatype=2";
			System.out.println("=========55============" + strwhere);
			load_list(true, strwhere);
			break;
		case R.id.index_item4:
			// List<Double> list4 = MyOrderllAdapter.list_monney;
			// if (list4.size() > 0) {
			// list4.clear();//�����ܼ��б����
			// }
			item5();
			list = new ArrayList<MyOrderData>();
			strwhere = "payment_status=2%20and%20express_status=2%20and%20status=3%20and%20datatype=2";
			System.out.println("=========66============" + strwhere);
			load_list(true, strwhere);
			break;

		default:
			break;
		}
	}

	private void item1() {
		cursor1.setVisibility(View.VISIBLE);
		cursor2.setVisibility(View.INVISIBLE);
		cursor3.setVisibility(View.INVISIBLE);
		cursor4.setVisibility(View.INVISIBLE);
		cursor5.setVisibility(View.INVISIBLE);
	}

	private void item2() {
		cursor1.setVisibility(View.INVISIBLE);
		cursor2.setVisibility(View.VISIBLE);
		cursor3.setVisibility(View.INVISIBLE);
		cursor4.setVisibility(View.INVISIBLE);
		cursor5.setVisibility(View.INVISIBLE);
	}

	private void item3() {
		cursor1.setVisibility(View.INVISIBLE);
		cursor2.setVisibility(View.INVISIBLE);
		cursor3.setVisibility(View.VISIBLE);
		cursor4.setVisibility(View.INVISIBLE);
		cursor5.setVisibility(View.INVISIBLE);
	}

	private void item4() {
		cursor1.setVisibility(View.INVISIBLE);
		cursor2.setVisibility(View.INVISIBLE);
		cursor3.setVisibility(View.INVISIBLE);
		cursor4.setVisibility(View.VISIBLE);
		cursor5.setVisibility(View.INVISIBLE);
	}

	private void item5() {
		cursor1.setVisibility(View.INVISIBLE);
		cursor2.setVisibility(View.INVISIBLE);
		cursor3.setVisibility(View.INVISIBLE);
		cursor4.setVisibility(View.INVISIBLE);
		cursor5.setVisibility(View.VISIBLE);
	}

	/**
	 * �����б�ˢ�¼���
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
	 * �����б�ˢ�¼���
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
						System.out.println("strwhere=========" + strwhere);

						load_list(false, strwhere);
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
	 * ��1���б����ݽ���
	 */
	private int CURRENT_NUM = 1;
	private final int VIEW_NUM = 10;

	private void load_list(boolean flag, String strwhere) {
		// progress.CreateProgress();
		if (flag) {
			// ��������������
			CURRENT_NUM = 1;
			list = new ArrayList<MyOrderData>();
			// System.out.println("=========list11============"+list.size());
		}
		System.out.println("====1=====list============" + list.size());// 5897
		System.out.println("=========strwhere============" + strwhere);// 5897

		AsyncHttp.get(RealmName.REALM_NAME_LL
				+ "/get_order_page_size_list?user_id=" + user_id + ""
				+ "&page_size=" + VIEW_NUM + "&page_index=" + CURRENT_NUM
				+ "&strwhere=" + strwhere + "&datatype=&orderby=",
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						// TODO Auto-generated method stub
						super.onSuccess(arg0, arg1);
						System.out.println("=========���ݽӿ�============" + arg1);
						try {

							JSONObject object = new JSONObject(arg1);
							String status = object.getString("status");
							String info = object.getString("info");
							if (status.equals("y")) {
								JSONArray jsonArray = object
										.getJSONArray("data");
								len = jsonArray.length();
								for (int i = 0; i < jsonArray.length(); i++) {
									md = new MyOrderData();
									JSONObject obj = jsonArray.getJSONObject(i);
									md.setId(obj.getString("id"));
									md.setOrder_no(obj.getString("order_no"));
									md.setTrade_no(obj.getString("trade_no"));
									md.setCompany_name(obj
											.getString("company_name"));
									md.setPayment_status(obj
											.getString("payment_status"));
									md.setAccept_name(obj
											.getString("accept_name"));
									md.setExpress_status(obj
											.getString("express_status"));
									md.setExpress_fee(obj
											.getString("express_fee"));
									md.setStatus(obj.getString("status"));
									md.setProvince(obj.getString("province"));
									md.setCashing_packet(obj
											.getString("cashing_packet_total"));
									md.setExchange_price_total(obj
											.getString("exchange_price_total"));
									md.setExchange_point_total(obj
											.getString("exchange_point_total"));
									md.setAddress(obj.getString("address"));
									md.setUser_name(obj.getString("user_name"));
									md.setPayment_time(obj
											.getString("payment_time"));
									md.setPayable_amount(obj
											.getString("payable_amount"));
									md.setAdd_time(obj.getString("add_time"));
									md.setComplete_time(obj
											.getString("complete_time"));
									md.setRebate_time(obj
											.getString("rebate_time"));
									md.setMobile(obj.getString("mobile"));
									md.setCity(obj.getString("city"));
									md.setArea(obj.getString("area"));
									// String getPayable_amount =
									// md.getPayable_amount();
									// System.out.println("============="+getPayable_amount);

									String order_groupon = obj
											.getString("order_goods");
									// System.out.println("=====order_groupon====================="+order_groupon);
									// if (order_groupon.equals("null")) {
									// System.out.println("=====order_groupon=========1============"+order_groupon);
									// } else {
									md.setList(new ArrayList<OrderBean>());
									JSONArray ja = new JSONArray(order_groupon);
									// JSONObject jo = new
									// JSONObject(order_groupon);
									List<OrderBean> lists = new ArrayList<OrderBean>();
									for (int j = 0; j < ja.length(); j++) {
										JSONObject jo = ja.getJSONObject(j);
										mb = new OrderBean();
										mb.setPoint_title(jo
												.getString("article_title"));
										mb.setPoint_price(jo
												.getString("exchange_price"));
										mb.setPoint_value(jo
												.getString("exchange_point"));
										mb.setImg_url(jo.getString("img_url"));
										// mb.setGoods_title(jo.getString("goods_title"));
										// mb.setSell_price(jo.getString("sell_price"));
										// mb.setMarket_price(jo.getString("market_price"));
										// mb.setReal_price(jo.getString("real_price"));
										// mb.setQuantity(jo.getInt("quantity"));
										mb.setArticle_id(jo
												.getString("article_id"));
										md.getList().add(mb);
										lists.add(mb);
									}
									md = null;
									mb = null;
									list.add(md);
								}
								progress.CloseProgress();
								no_data_no.setVisibility(View.GONE);
							} else {
								System.out.println("====list.size()========="
										+ list.size());
								progress.CloseProgress();
								if (list.size() == 0) {
									no_data_no.setVisibility(View.VISIBLE);
									// Toast.makeText(MyJuDuiHuanActivity.this,
									// info, 200).show();
								} else {
									Toast.makeText(MyJuDuiHuanActivity.this,
											"û�ж�����", 200).show();
								}
							}

							System.out.println("==2==list.size()========="
									+ list.size());

							if (len != 0) {
								CURRENT_NUM = CURRENT_NUM + 1;
							}
							Message msg = new Message();
							msg.what = 0;
							msg.obj = list;
							handler.sendMessage(msg);
							// handler.sendEmptyMessage(0);
							progress.CloseProgress();
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
					}

				}, MyJuDuiHuanActivity.this);
	}

	Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
			case 0:
				// list = (ArrayList<MyOrderData>) msg.obj;
				madapter.putData(list);
				// madapter.notifyDataSetChanged();
				// madapter = new
				// MyOrderllAdapter(list,MyOrderActivity.this,handler);
				// my_list.setAdapter(madapter);
				// progress.CloseProgress();
				break;
			case 1:
				break;
			case 2:
				order_no = (String) msg.obj;
				dialog2();
				break;
			case 3:
				order_no = (String) msg.obj;
				dialog3();
				break;
			case 4:
				order_no = (String) msg.obj;
				// Intent intent = new Intent(MyOrderActivity.this,
				// MyOrderZFActivity.class);
				// intent.putExtra("order_no",order_no);
				// startActivity(intent);

				// CommomConfrim.showSheet(MyOrderActivity.this,new
				// onDeleteSelect() {
				//
				// @Override
				// public void onClick(int resID) {
				// // TODO Auto-generated method stub
				// switch (resID) {
				// case R.id.item0:
				// // ���֧��
				// break;
				// case R.id.item1:
				// break;
				// case R.id.item2:// ֧����
				// loadzhidu(order_no);
				// break;
				// case R.id.item3:// ΢��
				// break;
				// case R.id.item4:
				//
				// break;
				// default:
				// break;
				// }
				// }
				//
				// }, cancelListener, null);

				break;
			case 5:// ֧����
				PayResult payResult = new PayResult((String) msg.obj);

				// ֧�������ش˴�֧���������ǩ�������֧����ǩ����Ϣ��ǩԼʱ֧�����ṩ�Ĺ�Կ����ǩ
				String resultInfo = payResult.getResult();

				String resultStatus = payResult.getResultStatus();
				System.out.println(resultInfo + "---" + resultStatus);
				// �ж�resultStatus Ϊ��9000�������֧���ɹ�������״̬�������ɲο��ӿ��ĵ�
				if (TextUtils.equals(resultStatus, "9000")) {
					Toast.makeText(MyJuDuiHuanActivity.this, "֧���ɹ�",
							Toast.LENGTH_SHORT).show();
					userloginqm();
				} else {
					// �ж�resultStatus Ϊ�ǡ�9000����������֧��ʧ��
					// ��8000������֧�������Ϊ֧������ԭ�����ϵͳԭ���ڵȴ�֧�����ȷ�ϣ����ս����Ƿ�ɹ��Է�����첽֪ͨΪ׼��С����״̬��
					if (TextUtils.equals(resultStatus, "8000")) {
						Toast.makeText(MyJuDuiHuanActivity.this, "֧�����ȷ����",
								Toast.LENGTH_SHORT).show();

					} else {
						// ����ֵ�Ϳ����ж�Ϊ֧��ʧ�ܣ������û�����ȡ��֧��������ϵͳ���صĴ���
						Toast.makeText(MyJuDuiHuanActivity.this, "֧��ʧ��",
								Toast.LENGTH_SHORT).show();

					}
				}
				break;

			default:
				break;
			}
		};
	};

	OnCancelListener cancelListener = new OnCancelListener() {

		@Override
		public void onCancel(DialogInterface dialog) {
		}
	};

	protected void dialog2() {
		AlertDialog.Builder builder = new Builder(MyJuDuiHuanActivity.this);
		builder.setMessage("�Ƿ�ȷ��ȡ������?");
		builder.setTitle("��ʾ");
		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				fukuanok2(order_no);
			}
		});

		builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});

		builder.create().show();
	}

	protected void dialog3() {
		AlertDialog.Builder builder = new Builder(MyJuDuiHuanActivity.this);
		builder.setMessage("�Ƿ�ȷ��ɾ������?");
		builder.setTitle("��ʾ");
		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				fukuanok3(order_no);
			}
		});

		builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});

		builder.create().show();
	}

	/**
	 * ȷ�ϸ���
	 * 
	 * @param order_no
	 * @param payment_id
	 */
	public void fukuanok(String order_no2) {
		progress.CreateProgress();
		order_no = order_no2;
		System.out.println("order_no================================="
				+ order_no);
		String login_sign = spPreferences.getString("login_sign", "");
		System.out.println("login_sign================================="
				+ login_sign);
		AsyncHttp.get(RealmName.REALM_NAME_LL
				+ "/update_order_payment?user_id=" + user_id + "&user_name="
				+ user_name + "" + "&trade_no=" + order_no + "&sign="
				+ login_sign + "", new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, String arg1) {
				super.onSuccess(arg0, arg1);
				try {
					JSONObject object = new JSONObject(arg1);
					System.out.println("ȷ�ϸ���================================="
							+ arg1);
					String status = object.getString("status");
					String info = object.getString("info");
					if (status.equals("y")) {
						progress.CloseProgress();
						Toast.makeText(MyJuDuiHuanActivity.this, info, 200)
								.show();
					} else {
						progress.CloseProgress();
						Toast.makeText(MyJuDuiHuanActivity.this, info, 200)
								.show();
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

		}, MyJuDuiHuanActivity.this);

	}

	/**
	 * ȡ������
	 * 
	 * @param order_no
	 * @param payment_id
	 */
	public void fukuanok2(String order_no2) {
		progress.CreateProgress();
		order_no = order_no2;
		System.out.println("order_no================================="
				+ order_no);
		String login_sign = spPreferences.getString("login_sign", "");
		System.out.println("login_sign================================="
				+ login_sign);
		AsyncHttp.get(RealmName.REALM_NAME_LL + "/update_order_cancel?user_id="
				+ user_id + "&user_name=" + user_name + "" + "&trade_no="
				+ order_no + "&sign=" + login_sign + "",
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						super.onSuccess(arg0, arg1);
						try {
							JSONObject object = new JSONObject(arg1);
							System.out
									.println("ȡ������================================="
											+ arg1);
							String status = object.getString("status");
							String info = object.getString("info");
							if (status.equals("y")) {
								progress.CloseProgress();
								Toast.makeText(MyJuDuiHuanActivity.this, info,
										200).show();
								load_list(true, strwhere);
								madapter.notifyDataSetChanged();
							} else {
								progress.CloseProgress();
								Toast.makeText(MyJuDuiHuanActivity.this, info,
										200).show();
							}

						} catch (JSONException e) {
							e.printStackTrace();
						}
					}

				}, MyJuDuiHuanActivity.this);

	}

	/**
	 * ɾ������
	 * 
	 * @param order_no
	 * @param payment_id
	 */
	public void fukuanok3(String order_no2) {
		progress.CreateProgress();
		order_no = order_no2;
		System.out.println("order_no================================="
				+ order_no);
		String login_sign = spPreferences.getString("login_sign", "");
		System.out.println("login_sign================================="
				+ login_sign);
		AsyncHttp.get(RealmName.REALM_NAME_LL + "/delete_order?user_id="
				+ user_id + "&user_name=" + user_name + "" + "&trade_no="
				+ order_no + "&sign=" + login_sign + "",
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						super.onSuccess(arg0, arg1);
						try {
							JSONObject object = new JSONObject(arg1);
							System.out
									.println("ȡ������================================="
											+ arg1);
							String status = object.getString("status");
							String info = object.getString("info");
							if (status.equals("y")) {
								progress.CloseProgress();
								Toast.makeText(MyJuDuiHuanActivity.this, info,
										200).show();
								load_list(true, strwhere);
								madapter.notifyDataSetChanged();
							} else {
								progress.CloseProgress();
								Toast.makeText(MyJuDuiHuanActivity.this, info,
										200).show();
							}

						} catch (JSONException e) {
							e.printStackTrace();
						}
					}

				}, MyJuDuiHuanActivity.this);

	}

	/**
	 * ��ȡ��¼ǩ��
	 */
	private void userloginqm() {
		try {
			SharedPreferences spPreferences = getSharedPreferences(
					"longuserset", MODE_PRIVATE);
			String user_name = spPreferences.getString("user", "");
			String strUrlone = RealmName.REALM_NAME_LL
					+ "/get_user_model?username=" + user_name + "";
			System.out.println("======11=============" + strUrlone);
			AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
				public void onSuccess(int arg0, String arg1) {
					try {
						JSONObject object = new JSONObject(arg1);
						String status = object.getString("status");
						JSONObject obj = object.getJSONObject("data");
						if (status.equals("y")) {
							UserRegisterllData data = new UserRegisterllData();
							data.login_sign = obj.getString("login_sign");
							login_sign = data.login_sign;
							System.out.println("======order_no============="
									+ order_no);
							loadguanggaoll(order_no, login_sign);
						} else {
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				};
			}, MyJuDuiHuanActivity.this);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * ���¶���
	 * 
	 * @param login_sign
	 * @param payment_id
	 */
	private void loadguanggaoll(String recharge_noll, String login_sign) {
		try {
			// recharge_no = recharge_noll;
			System.out.println("recharge_no================================="
					+ recharge_noll);
			System.out.println("login_sign================================="
					+ login_sign);
			AsyncHttp.get(RealmName.REALM_NAME_LL
					+ "/update_order_payment?user_id=" + user_id
					+ "&user_name=" + user_name + "" + "&trade_no="
					+ recharge_noll + "&sign=" + login_sign + "",

			new AsyncHttpResponseHandler() {
				@Override
				public void onSuccess(int arg0, String arg1) {
					super.onSuccess(arg0, arg1);
					try {
						JSONObject object = new JSONObject(arg1);
						System.out
								.println("���¶���================================="
										+ arg1);
						String status = object.getString("status");
						String info = object.getString("info");
						if (status.equals("y")) {
							progress.CloseProgress();
							JSONObject jsonObject = object
									.getJSONObject("data");
							JSONArray jay = jsonObject.getJSONArray("orders");
							for (int j = 0; j < jay.length(); j++) {
								JSONObject objc = jay.getJSONObject(j);
								name = objc.getString("accept_name");
								province = objc.getString("province");
								city = objc.getString("city");
								area = objc.getString("area");
								user_mobile = objc.getString("mobile");
								user_address = objc.getString("address");
								recharge_no = objc.getString("order_no");
								datetime = objc.getString("add_time");
								JSONArray jsonArray = objc
										.getJSONArray("order_goods");
								for (int i = 0; i < jsonArray.length(); i++) {
									JSONObject json = jsonArray
											.getJSONObject(i);
									article_id = json.getString("article_id");
									sell_price = json.getString("sell_price");
									give_pension = json
											.getString("give_pension");
								}
							}
							teby = false;
							// finish();
							Toast.makeText(MyJuDuiHuanActivity.this, info, 200)
									.show();
							Intent intent = new Intent(
									MyJuDuiHuanActivity.this,
									ZhiFuOKActivity.class);
							startActivity(intent);
						} else {
							progress.CloseProgress();
							teby = false;
							Toast.makeText(MyJuDuiHuanActivity.this, info, 200)
									.show();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}

				@Override
				public void onFailure(Throwable arg0, String arg1) {
					// TODO Auto-generated method stub
					super.onFailure(arg0, arg1);
					System.out.println("11================================="
							+ arg0);
					System.out.println("22================================="
							+ arg1);
					Toast.makeText(MyJuDuiHuanActivity.this, "���¶������糬ʱ�쳣", 200)
							.show();
				}

			}, null);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
