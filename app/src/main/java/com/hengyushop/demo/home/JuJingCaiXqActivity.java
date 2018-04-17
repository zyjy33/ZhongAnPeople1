package com.hengyushop.demo.home;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.post.CommomConfrim;
import com.android.hengyu.post.CommomConfrim.onDeleteSelect;
import com.android.hengyu.pub.MyJutuanMxAdapter;
import com.android.hengyu.pub.TuanchengyuanAdapterll;
import com.android.hengyu.pub.ZhongAnYlAdapter;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.androidquery.AQuery;
import com.ctrip.openapi.java.utils.GetImgUtil;
import com.example.taobaohead.MyCountdownTimer;
import com.hengyushop.airplane.adapter.CanTuanAdapter;
import com.hengyushop.airplane.adapter.JuTuanAdapter;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.demo.my.TishiWxBangDingActivity;
import com.hengyushop.demo.wec.MyGridView;
import com.hengyushop.entity.JuTuanGouData;
import com.lglottery.www.widget.InScrollListView;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.R;
import com.zams.www.UserLoginActivity;
import com.zams.www.WareInformationActivity;

/**
 * 
 * ���̾�
 * @author Administrator
 * 
 */
public class JuJingCaiXqActivity extends BaseActivity implements OnClickListener{

	private ImageView iv_img;
	private DialogProgress progress;
//	private SharedPreferences spPreferences_user;
	private SharedPreferences spPreferences;
	private ListView listview_01;
	private ArrayList<JuTuanGouData> list = null;
	private ArrayList<JuTuanGouData> list_ll = null;
	private ArrayList<JuTuanGouData> list_tx = null;
	private ArrayList<JuTuanGouData> list_cy;
	List<Integer> list_num;
	private ZhongAnYlAdapter zaylaAdapter;
	private TextView tv_titel,tv_price,tv_yuanjia,tv_tuangoujia,tv_tuanshu,tv_yq_cantuan,tv_kaituan_ts;
	JuTuanGouData data;
	JuTuanGouData bean;
	private GridView gridView;
	private MyGridView gridView2;
	private ListView new_list,list_tuanjia;
	JuTuanAdapter arrayadapter;
	CanTuanAdapter cantuanadapter;
	private WebView webview;
	private LinearLayout ll_dianping,ll_lijigoumai,ll_tuangou;
	public static String item_id,goumai_id,tuangoujia,tuanshu;
	String user_id,user_name,choujiang,sp_id,ct_id,pt_fx,groupon_no;
//	String province,city,area,user_address,user_mobile,name,shopping_address_id;
	private InScrollListView list_shop_cart;
	String zhuangtai = "100";
	MyJutuanMxAdapter adapter;
	private TextView txt_time,tv_hd_time,tv_pt_gz;
	public static boolean fanhui_type = false;
	View iv_view;
	String type;
	int num,groupon_item_people;
	String id,orders_no;
	private static SimpleDateFormat sf = null;
	public static String datetime,end_time,timer_time,start_time;
	public static String foreman_id,foreman_name,tuan_id;
	public static String ct_tuanshu,foreman_id_pt;
//	public static String people;
	private LinearLayout ll_qu_kaituan,ll_kaituan;
	public AQuery mAq;
	public static long day,hour;
	public static String fx_canshu = "";
	private TextView tv_tuanshu_ll,tv_anniu1,tv_anniu2;
	private long minute = 0; 
	private long second = 0; 
	private long time = 0;//����Ϊ��λ
	private long current_time = 0;
	private long interval = 10*1000;//ÿ�����ӻ���ٵĶ��Ϊ10��
	private MyCount count;//��ʱ����� 
	java.util.Date now_1;
	java.util.Date date_1;
	/**����ʱ������Դ**/
	private List<Date> listData;
    /**��ǰʱ��**/
    private long time_Current;
	/**ListView�ؼ�**/
	private ListView listView;
	
    long hourl,min,s,zongxs;
	 /**������**/
	private MyCountAdapter myCountAdapter;
		java.util.Date now;
		java.util.Date date;
		//�������Ҫ��ʹ��Handler����ʱЧ����ÿ��һ��ˢ��һ�����������Դ˲�������ʱЧ��
	    private Handler handler_timeCurrent = new Handler(){
	        @Override
	        public void handleMessage(Message msg) {
	            time_Current = time_Current+1000;
				myCountAdapter.notifyDataSetChanged();				
	            handler_timeCurrent.sendEmptyMessageDelayed(0,1000);
	        }
	    };
	    
		    String weixin = "";
			String qq = "";
			String user_name_phone = "";
			String user_name_3_wx = "";
			String user_name_3_qq = "";
			String user_name_key = "";
			String oauth_name;
			String datall;
			String nickname = "";
		public static int fangshi = 0;
		public static boolean type_xq = false;//���������������Բ���ʾ
		public static boolean type_spec_item = false;//���������������Բ���ʾ
		public static int spec_text_list = 0;//�����ײ��ж�Ϊ0
		public static boolean taocan_type = false;//�ж���Ʒ�ײͼ۸�
		@Override
		public void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
			JuTuanGouXqActivity.type_xq = false;//���������������Բ���ʾ
			JuTuanGouXqActivity.type_spec_item = false;//���������������Բ���ʾ
			JuTuanGouXqActivity.fx_canshu = "";
			WareInformationActivity.fangshi = 0;//����0���ж�Ϊ��Ʒ����
			WareInformationActivity.taocan_type = false;//�ж���Ʒ�ײͼ۸�
			
			WareInformationActivity.spec_text_list = 0;//�����ײ��ж�Ϊ0
			JuTuanGouXqActivity.spec_text_list = 0;//�����ײ��ж�Ϊ0
			
//			spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
//			user_name_phone = spPreferences.getString("user", "");
//			user_id = spPreferences.getString("user_id", "");
//			user_name = spPreferences.getString("user_name", "");
//			type = getIntent().getStringExtra("type");//�۾���״̬
			
			System.out.println("user_name_phone================"+user_name_phone);
			
			if (JuTuanConfrimActivity.fanhui_type == true){
				JuTuanConfrimActivity.fanhui_type = false;
				String groupon_id = getIntent().getStringExtra("id");
				System.out.println("groupon_id=====2=====�۾���=========="+groupon_id);
				loadWeatherxq(groupon_id);
			}
			
		}  
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jutuangou_title);//activity_jutuangou_title activity_jutuangou_xq
		progress = new DialogProgress(JuJingCaiXqActivity.this);
//		JuTuanGouXqActivity.type_xq = false;//���������������Բ���ʾ
//		JuTuanGouXqActivity.type_spec_item = false;//���������������Բ���ʾ
	    fx_canshu = getIntent().getStringExtra("fx_shuzi");
		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		user_name_phone = spPreferences.getString("user", "");
		user_id = spPreferences.getString("user_id", "");
		user_name = spPreferences.getString("user_name", "");
		type = getIntent().getStringExtra("type");//�۾���״̬
		try {
		
			
		choujiang = getIntent().getStringExtra("choujiang");
		System.out.println("choujiang===================="+choujiang);
		mAq = new AQuery(this);
		intren();
		        
		String groupon_id = getIntent().getStringExtra("id");
		System.out.println("groupon_id=====2=====�۾���=========="+groupon_id);
		loadWeatherxq(groupon_id);
		
//		Button fanhui = (Button) findViewById(R.id.fanhui);
//		fanhui.setOnClickListener(this);
		
		
		Button iv_fanhui = (Button) findViewById(R.id.fanhui);
		iv_fanhui.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				System.out.println("iv_fanhui====================");
				finish();
			}
		});
		
		ImageView img_shared = (ImageView) findViewById(R.id.img_shared);
		img_shared.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				try {
					if (user_name_phone.equals("")) {
						Intent intent = new Intent(JuJingCaiXqActivity.this,UserLoginActivity.class);
						startActivity(intent);
						progress.CloseProgress();
					}else {
						if (UserLoginActivity.wx_fanhui == false) {
							Intent intent = new Intent(JuJingCaiXqActivity.this,UserLoginActivity.class);
							startActivity(intent);
							progress.CloseProgress();
						}else {
						System.out.println("pt_fx===========1========="+pt_fx);
						System.out.println("ct_id===================="+ct_id);
						Intent intentll = new Intent(JuJingCaiXqActivity.this,DBFengXiangActivity.class);
						if (ct_id != null) {
							intentll.putExtra("ct_id",ct_id);
							System.out.println("ct_id========1============"+ct_id);
						}else {
							intentll.putExtra("pt_id",pt_fx);
							System.out.println("pt_fx=========2==========="+pt_fx);
						}
//						intentll.putExtra("pt_id",pt_fx);
						intentll.putExtra("title",data.getTitle());
						intentll.putExtra("fx_shuzi", "groupon");
//						intentll.putExtra("img_url", data.share_img_url);
						intentll.putExtra("subtitle",data.getSubtitle());
						intentll.putExtra("company_id",data.getCompany_id());
						intentll.putExtra("fx_shuzi","groupon");
						intentll.putExtra("img_url", "");
//						intentll.putExtra("subtitle","");
						startActivity(intentll);
						}
					}
//				}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		});
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	//��ֹ��ǰActivity�����Ժ�,   handler��Ȼ����ѭ���˷���Դ
	@Override
	protected void onDestroy() {
		handler_timeCurrent.removeCallbacksAndMessages(null);
		super.onDestroy();
	}
	
	/**
	 * �ؼ���ʼ��
	 */
	public void intren() {
		try {
//		listview_01=(ListView) findViewById(R.id.listview_01);
			list_tuanjia = (ListView) findViewById(R.id.list_tuanjia);
			gridView2=(MyGridView)findViewById(R.id.gridview2);
			txt_time = (TextView) findViewById(R.id.tvshowtime); 
			tv_hd_time = (TextView) findViewById(R.id.tv_hd_time); 
			tv_pt_gz = (TextView) findViewById(R.id.tv_pt_kaituan_gz); 
		tv_anniu1 = (TextView) findViewById(R.id.tv_anniu1);
		tv_anniu2 = (TextView) findViewById(R.id.tv_anniu2);
		tv_tuanshu_ll = (TextView) findViewById(R.id.tv_tuanshu_ll);
		ll_dianping = (LinearLayout) findViewById(R.id.ll_dianping);
		ll_lijigoumai = (LinearLayout) findViewById(R.id.ll_lijigoumai);
		ll_tuangou = (LinearLayout) findViewById(R.id.ll_tuangou);
		list_shop_cart = (InScrollListView) findViewById(R.id.list_shop_cart);
		ll_qu_kaituan = (LinearLayout) findViewById(R.id.ll_qu_kaituan);
		ll_kaituan = (LinearLayout) findViewById(R.id.ll_kaituan);
		new_list = (ListView) findViewById(R.id.new_list);
		gridView = (GridView) findViewById(R.id.gridView);
		iv_img = (ImageView) findViewById(R.id.img);
		tv_titel = (TextView) findViewById(R.id.tv_titel);
		tv_price = (TextView) findViewById(R.id.tv_price);
		tv_yuanjia = (TextView) findViewById(R.id.tv_yuanjia);
		tv_tuangoujia = (TextView) findViewById(R.id.tv_tuangoujia);
		tv_tuanshu = (TextView) findViewById(R.id.tv_tuanshu);
		tv_yq_cantuan = (TextView) findViewById(R.id.tv_yq_cantuan);
		tv_kaituan_ts = (TextView) findViewById(R.id.tv_kaituan_ts);
		iv_view = findViewById(R.id.iv_view);
//		listview_01.setFocusable(false);
		tv_anniu1.setOnClickListener(this);
		ll_dianping.setOnClickListener(this);
		ll_lijigoumai.setOnClickListener(this);
		ll_tuangou.setOnClickListener(this);
		
		webview = (WebView) findViewById(R.id.webview);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.addJavascriptInterface(new JavascriptHandler(), "handler");
		webview.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
			}
		});
		
		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
	class JavascriptHandler {
		@JavascriptInterface
		public void getContent(String htmlContent) {
		}
	}
	
	Handler handler = new Handler(){
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
			case 0:
				final ArrayList<JuTuanGouData> carts = (ArrayList<JuTuanGouData>) msg.obj;
				tuangoujia = JuTuanAdapter.tuangoujia;
				tuanshu = JuTuanAdapter.tuanshu;
//				String tuangoujia = (String) msg.obj;
				tv_tuangoujia.setText("��"+tuangoujia);
				tv_tuanshu.setText(tuanshu+"����");
				break;
			case 1:
//				System.out.println("list�����Ƕ���===================="+list.size());
				arrayadapter = new JuTuanAdapter(list,getApplicationContext(),handler);
				list_tuanjia.setAdapter(arrayadapter);
				setListViewHeightBasedOnChildren(list_tuanjia);  
				list_tuanjia.setOnItemClickListener(new OnItemClickListener() {
		            @Override
		            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//		            	 flag = false;
//		            	 String id = listll.get(arg2).getId();
//		            	 System.out.println("=====�ڶ�������1====================="+INDX);
		            	 arrayadapter.setSeclection(arg2);
		            	 arrayadapter.notifyDataSetChanged();
		            }
		        });
				
				break;
			case 2:
				try {
				System.out.println("list�����Ƕ���2===================="+list_ll.size());
				if(list_ll.size() == 0){
					iv_view.setVisibility(View.GONE);
				}else{
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					try {
//						String end_time = "2004-03-26 13:31:40";
						System.out.println("timer_time=========2==========="+timer_time);
						System.out.println("datetime===========2========="+datetime);
						String time = timer_time;
						now = df.parse(time);
					} catch (java.text.ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					try {
//						String datetime = "2004-03-26 13:31:30";
						String time = datetime;
						date = df.parse(time);
						time_Current = date.getTime();
					} catch (java.text.ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
							myCountAdapter = new MyCountAdapter();
							new_list.setAdapter(myCountAdapter);
							setListViewHeightBasedOnChildren(new_list);  
					        handler_timeCurrent.sendEmptyMessageDelayed(0,1000);
				
				}
				
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				break;
//			case 3://ƴ�ŷ���
//				break;	
              case 4:
				try {
					
				adapter = new MyJutuanMxAdapter(list_ll,JuJingCaiXqActivity.this);
				new_list.setAdapter(adapter);
				setListViewHeightBasedOnChildren(new_list);  
				
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				break;
				
              case 5:
//  					new_list.setVisibility(View.GONE);
  					iv_view.setVisibility(View.GONE);
  				break;
//              case 6://���ŷ���
//				break;
      		case 7:
      			 ct_id = (String) msg.obj;
				 System.out.println("ct_id=========7==========="+ct_id);
				break;
      		case 8:
      			 ct_tuanshu = (String) msg.obj;
				 System.out.println("ct_tuanshu=========8==========="+ct_tuanshu);
				break;
			default:
				break;
			}
		};
	};
	
	

	/**
	 * �����������
	 * @param groupon_id 
	 * @param category_id 
	 */
	public static String img_url,title,sell_price,article_id,goods_id,price,spec_text;
	public static ArrayList data_shuzu,data_monney,data_goods_id,data_market_price
	,data_people,data_goods_id_1,data_people_1,data_price,data_spec_text,data_exchange_price,data_exchange_point;
	ArrayList list_data1,list_data2;
	private void loadWeatherxq(String groupon_id) {
		progress.CreateProgress();
		 data_shuzu = new ArrayList();
		 data_monney = new ArrayList();
		 data_goods_id = new ArrayList();
		 data_market_price = new ArrayList();
		 data_people = new ArrayList();
		 data_goods_id_1 = new ArrayList();
		 data_people_1 = new ArrayList();
		 data_price = new ArrayList();
		 data_spec_text = new ArrayList();
		 data_exchange_price = new ArrayList();
		 data_exchange_point = new ArrayList();
		 list_data2 = new ArrayList();
		list = new ArrayList<JuTuanGouData>();
		list_ll = new ArrayList<JuTuanGouData>();
//		AsyncHttp.get(RealmName.REALM_NAME_LL + "/get_article_model?id="+groupon_id+""
		AsyncHttp.get(RealmName.REALM_NAME_LL + "/get_article_myselft_list?user_id="+user_id+"&user_name="+user_name+"&article_id="+groupon_id+"&datatype=5&top=1"
				, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, String arg1) {
				// TODO Auto-generated method stub
				super.onSuccess(arg0, arg1);
				System.out.println("�����������========="+arg1);
				try {
					JSONObject object = new JSONObject(arg1);
					String status = object.getString("status");
					String info = object.getString("info");
					datetime = object.getString("datetime");
					if (status.equals("y")) {
//						JSONArray jsonArray = object.getJSONArray("data");
						JSONObject jsonobt= object.getJSONObject("data");
						data = new JuTuanGouData();
//						for (int i = 0; i < jsonArray.length(); i++) {
//							JSONObject obj = jsonArray.getJSONObject(i);
						JSONObject obj= jsonobt.getJSONObject("article_model");
							data.setId(obj.getString("id"));
							data.setTitle(obj.getString("title"));
							data.setImg_url(obj.getString("img_url"));
							data.setAdd_time(obj.getString("add_time"));
							data.setStart_time(obj.getString("start_time"));
							data.setUpdate_time(obj.getString("update_time"));
							data.setCategory_id(obj.getString("category_id"));
							data.setEnd_time(obj.getString("end_time"));
							data.setSubtitle(obj.getString("subtitle"));
							data.setImgs_url(obj.getString("imgs_url"));
							data.setCompany_id(obj.getString("company_id"));
							item_id = data.getId();
							img_url = data.img_url;
							title = data.title;
							pt_fx = data.getId();
							JSONObject jsot = obj.getJSONObject("default_spec_item");
							data.setGoods_id(jsot.getString("goods_id"));
							data.setArticle_id(jsot.getString("article_id"));
							data.setSell_price(jsot.getString("sell_price"));
							data.setSpec_text(jsot.getString("spec_text"));
							spec_text = jsot.getString("spec_text");
							sell_price = data.sell_price;
							article_id = data.article_id;
							goods_id = data.goods_id;
							JSONObject jsoct = jsot.getJSONObject("default_activity_price");
							data.setPeople(jsoct.getString("people"));
							data.setPrice(jsoct.getString("price"));
							price = data.price;
							JSONArray jsonArray = jsot.getJSONArray("activity_price");
							for (int k = 0; k < jsonArray.length(); k++) {
								JSONObject objet2 = jsonArray.getJSONObject(k);
								JuTuanGouData data = new JuTuanGouData();
								data.setGoods_id(objet2.getString("goods_id"));
								data.setPeople(objet2.getString("people"));
								data.setPrice(objet2.getString("price"));
								list.add(data);
								data_people.add(data.people);
					        }
							
							JSONArray jsonay = obj.getJSONArray("spec_item");
				    		for (int i = 0; i < jsonay.length(); i++) {
				    		JSONObject objt= jsonay.getJSONObject(i);
				    		JuTuanGouData data = new JuTuanGouData();
//				    		data.setSpec_text(objt.getString("spec_text"));
				    		data.setSell_price(objt.getString("sell_price"));
				    		data.setMarket_price(objt.getString("market_price"));
				    		data.setSpec_ids(objt.getString("spec_ids"));
				    		data.setGoods_id(objt.getString("goods_id"));
				    		data.setArticle_id(objt.getString("article_id"));
				    		data.setSpec_text(objt.getString("spec_text"));
				    		data.setExchange_point(objt.getString("exchange_point"));
				    		data.setExchange_price(objt.getString("exchange_price"));
				    		
							data_shuzu.add(data.spec_ids);
							data_monney.add(data.sell_price);
							data_market_price.add(data.market_price);
							data_goods_id.add(data.goods_id);
							data_spec_text.add(data.spec_text);
							data_exchange_point.add(data.exchange_point);
						    data_exchange_price.add(data.exchange_price);
							JSONArray jsonArray2 = objt.getJSONArray("activity_price");
							for (int k = 0; k < jsonArray2.length(); k++) {
							JSONObject objet2 = jsonArray2.getJSONObject(k);
							JuTuanGouData data1 = new JuTuanGouData();
							data1.setGoods_id(objet2.getString("goods_id"));
							data1.setPeople(objet2.getString("people"));
							data1.setPrice(objet2.getString("price"));
							data_goods_id_1.add(data1.goods_id);
							data_people_1.add(data1.people);
							data_price.add(data1.price);
				            }
						    }
				    		
							JSONObject jsocet = obj.getJSONObject("activity");
							data.setActivity_rule(jsocet.getString("activity_rule"));
//							JSONArray jsonArray = obj.getJSONArray("activity_item");
//							for (int i = 0; i < jsonArray.length(); i++) {
//								JuTuanGouData data = new JuTuanGouData();
//								JSONObject objet = jsonArray.getJSONObject(i);
//								data.setPeople(objet.getString("people"));
//								data.setPrice_discount(objet.getString("price_discount"));
//								
//								JSONArray jsonArray1 = objet.getJSONArray("group_price");
//								for (int k = 0; k < jsonArray1.length(); k++) {
//									JSONObject objet1 = jsonArray.getJSONObject(k);
//									data.setGoods_id(objet1.getString("goods_id"));
//									data.setPrice(objet1.getString("price"));
//						        }
//								JSONArray jsonArray2 = objet.getJSONArray("activity_price");
//								for (int k = 0; k < jsonArray2.length(); k++) {
//									JSONObject objet2 = jsonArray.getJSONObject(k);
//									data.setGoods_id(objet2.getString("goods_id"));
//									data.setPeople(objet2.getString("people"));
//									data.setPrice(objet2.getString("price"));
//						        }
//								list.add(data);
//					        }
//						}
							JSONArray jsot_ll = jsonobt.getJSONArray("foreman_list");
							for (int k = 0; k < jsot_ll.length(); k++) {
							JSONObject obj1 = jsot_ll.getJSONObject(k);
//							data = new JuTuanGouData();
//							data.setOrder_no(obj1.getString("order_no"));
//							data.setOrder_no(obj1.getString("order_no"));
							data.setCompany_id(obj1.getString("company_id"));
							data.setUser_avatar(obj1.getString("user_avatar"));
							list_data2.add(data.getUser_avatar());
//							company_id = data.getCompany_id();
							JSONArray jsot1 = obj1.getJSONArray("order_goods");
							for (int q = 0; q < jsot1.length(); q++) {
								JSONObject jsont = jsot1.getJSONObject(q);
//								data = new JuTuanGouData();
								data.setArticle_id(jsont.getString("article_id"));
								data.setOrder_id(jsont.getString("order_id"));
								data.setGoods_id(jsont.getString("goods_id"));
								data.setQuantity(jsont.getString("quantity"));
////							data.setShare_img_url(jsont.getString("share_img_url"));
								data.setArticle_title(jsont.getString("article_title"));
								data.setImg_url(jsont.getString("img_url"));
								data.setForeman_id(jsont.getString("foreman_id"));
								data.setForeman_name(jsont.getString("foreman_name"));
								data.setTimer_time(jsont.getString("timer_time"));
								data.setEnd_time(jsont.getString("end_time"));
								data.setStart_time(jsont.getString("start_time"));
								data.setActivity_people(jsont.getInt("activity_people"));
								data.setActivity_member(jsont.getInt("activity_member"));
								data.setActivity_price(jsont.getString("activity_price"));
								data.setSell_price(jsont.getString("sell_price"));
					    		data.setMarket_price(jsont.getString("market_price"));
//								data.setGroupon_no(jsont.getString("groupon_no"));
//								data.setGroupon_item_id(jsont.getString("groupon_item_id"));
////							data.setOrder_no(jsont.getString("order_no"));
//					    		end_time = data.getEnd_time();
					    		timer_time = data.getTimer_time();
								foreman_id = data.getForeman_id();
								foreman_name = data.getForeman_name();
//								share_img_url = data.getShare_img_url();
//								timer_time = data.getTimer_time();
								ct_id = data.getOrder_id();
//								groupon_item_people = data.getActivity_people();
								ct_tuanshu = String.valueOf(data.getActivity_people()- data.getActivity_member());
								list_ll.add(data);
								//�жϵ�ǰ�û����ž�ִ��
								if (user_id.equals(data.getForeman_id())) {
									timer_time = data.getTimer_time();
									String yq_people = String.valueOf(data.getActivity_people()- data.getActivity_member());
									 System.out.println("yq_people---------------------"+yq_people);
									tv_yq_cantuan.setText("֧�����Ų�����"+yq_people+"�˲��ţ����������Զ��˿�");
									tv_kaituan_ts.setText("���Ѿ������Ź��������Է���������Ѳ���");
									tv_tuanshu_ll.setText(yq_people);
									ll_qu_kaituan.setVisibility(View.VISIBLE);
									
									
									new_list.setVisibility(View.GONE);
									getCantuantime();//��ȡ���ŵĵ���ʱ
//									groupon_item_member(groupon_no);
//									handler.sendEmptyMessage(2);
								}
								}
							}
					}else {
						Toast.makeText(JuJingCaiXqActivity.this, info, 200).show();
					}
//					 System.out.println("list.size()---------------------"+list.size());
//					 System.out.println("data.getPrice()---------------------"+data.getPrice());
					 try {
					 System.out.println("=====data.getActivity_people()====================="+data.getActivity_people());
//						
						for (int i = 0; i < data.getActivity_people(); i++) {
							System.out.println("=====i============"+i);
							System.out.println("=====list_data2====================="+list_data2.size());
							if (list_data2.size() <= i) {
								list_data2.add("");
							}
							System.out.println("=====list_data2=========11============"+list_data2.size());
							TuanchengyuanAdapterll adapter = new TuanchengyuanAdapterll(list_data2,JuJingCaiXqActivity.this);
							gridView2.setAdapter(adapter);
						}
					 } catch (Exception e) {
							// TODO: handle exception
						 e.printStackTrace();
						}
					intrendata();
					handler.sendEmptyMessage(1);
					webview.loadUrl(RealmName.REALM_NAME_HTTP+"/mobile/goods/conent-"+data.article_id+".html");//��Ʒ����
					progress.CloseProgress();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}, null);
	}
	/**
	 * ��ȡֵ���ؼ�
	 */
	public void intrendata() {
		try {
        ImageLoader imLoader = ImageLoader.getInstance();
        System.out.println("---------------------"+data.getImg_url());
        imLoader.displayImage(RealmName.REALM_NAME_HTTP+data.getImg_url(), iv_img);
        imLoader.clearMemoryCache();//����ڴ滺��
//      mAq.id(iv_img).image(RealmName.REALM_NAME_HTTP+data.getImg_url());
        tv_hd_time.setVisibility(View.VISIBLE);
        tv_hd_time.setText("�ʱ�䣺 "+data.getStart_time()+" ~ "+data.end_time);
        System.out.println("data.activity_rule====================="+data.activity_rule);
        if (data.activity_rule.equals("null")) {
        	 tv_pt_gz.setVisibility(View.GONE);
        	 tv_pt_gz.setText("");
		}else {
			tv_pt_gz.setVisibility(View.VISIBLE);
			tv_pt_gz.setText(data.activity_rule);
		}
		
		tv_titel.setText(data.getTitle());
		tv_price.setText("ԭ�ۣ�"+data.getSell_price());
//		tv_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
		tv_yuanjia.setText("��"+data.getSell_price());
		tv_tuangoujia.setText("��"+data.getPrice());
		tv_tuanshu.setText(data.getPeople()+"����");
		
		tv_price.getPaint().setFlags(
				Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // �����г������ֵ��л���
		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
//	ArrayList list_data1,list_data2;
		
		/**
		 * ����ų�Ա
		 * @param groupon_id
		 */
		private void groupon_item_member(String groupon_no) {
			list_data2 = new ArrayList();
			AsyncHttp.get(RealmName.REALM_NAME_LL + "/get_game_groupon_item_member?groupon_no="+groupon_no+""
					, new AsyncHttpResponseHandler() {
				@Override
				public void onSuccess(int arg0, String arg1) {
					// TODO Auto-generated method stub
					super.onSuccess(arg0, arg1);
					System.out.println("=======����ų�Ա================================"+arg1);
					try {
						JSONObject object = new JSONObject(arg1);
						String status = object.getString("status");
						String info = object.getString("info");
						datetime = object.getString("datetime");
						if (status.equals("y")) {
							list_cy = new ArrayList<JuTuanGouData>();
							list_tx = new ArrayList<JuTuanGouData>();
								try {
									JSONArray jay = object.getJSONArray("data");
									for (int k = 0; k < jay.length(); k++) {
										JuTuanGouData data = new JuTuanGouData();
										JSONObject obt = jay.getJSONObject(k);
										data.setId(obt.getString("id"));
										data.setTimer_time(obt.getString("timer_time"));
										timer_time = data.getTimer_time();
										data.setAvatar(obt.getString("avatar"));
										list_data2.add(data.getAvatar());
									   }
									
//									String data_tx = "http://wx.qlogo.cn/mmopen/Zw5SzXToEzuCtHFRb2IVVZemJzJx4cLibMpDIE2y4kA1lgPfbhe2rO851s5G72B2U1Wz6cGe8Eb7B4AbtibiaUaSRBeH1XqqMiam/0";
//									list_data2.add(data_tx);
//									list_data2.add("/upload/phone/112293399/20170421173504531.jpg");
//									list_data2.add("");
									
									System.out.println("=====groupon_item_people====================="+groupon_item_people);
									
									for (int i = 0; i < groupon_item_people; i++) {
										System.out.println("=====i============"+i);
										System.out.println("=====list_data2====================="+list_data2.size());
										if (list_data2.size() <= i) {
											list_data2.add("null");
										}
										System.out.println("=====list_data2=========11============"+list_data2.size());
										
										TuanchengyuanAdapterll adapter = new TuanchengyuanAdapterll(list_data2,JuJingCaiXqActivity.this);
										gridView2.setAdapter(adapter);
										
									}
									
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
							
						}else {
//							Toast.makeText(JuTuanGouXq2Activity.this, info, 200).show();
						}
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}, getApplicationContext());
		}
		
//		private void getzhou() {
//			try {
//				list_data2 = new ArrayList();
//			ll_qu_kaituan.setVisibility(View.VISIBLE);
//			new_list.setVisibility(View.GONE);
//			System.out.println("=====================ģ�����===============");
//			// TODO Auto-generated method stub
//			String data_tx = "http://wx.qlogo.cn/mmopen/Zw5SzXToEzuCtHFRb2IVVZemJzJx4cLibMpDIE2y4kA1lgPfbhe2rO851s5G72B2U1Wz6cGe8Eb7B4AbtibiaUaSRBeH1XqqMiam/0";
////			if (data_tx.contains("http")) {
////				list_data2.add(data_tx);
////			}else {
////				new Thread(getPicByUrl).start();
////				list_data2.add("/upload/phone/113875199/20170217164544307.jpg");
////				list_data2.add(data_tx);
////				list_data2.add("");
////				list_data2.add("/upload/phone/112293399/20170421173504531.jpg");
////			}
//			
////			list_data2.add("/upload/phone/113875199/20170217164544307.jpg");
//			list_data2.add("");
//			for (int i = 0; i < 10; i++) {
////				String num = String.valueOf(i);
////				list_data1.add(num);
//				System.out.println("=====i============"+i);
//				System.out.println("=====list_data2====================="+list_data2.size());
//				if (list_data2.size() <= i) {
////					list_data2.add("/upload/201608/04/201608041952577479.png");
////					list_data2.add("/upload/phone/113875199/20170217164544307.jpg");
//					list_data2.add("null");
//				}
//				System.out.println("=====list_data2=========11============"+list_data2.size());
//				
//				TuanchengyuanAdapterll adapter = new TuanchengyuanAdapterll(list_data2,JuJingCaiXqActivity.this);
//				gridView2.setAdapter(adapter);
//			}
//			} catch (Exception e) {
//				// TODO: handle exception
//				e.printStackTrace();
//			}
//			
//		}
		
		 Runnable getPicByUrl = new Runnable() {
				@Override
				public void run() {
					try {
//						String img_url2 = "http://183.62.138.31:1010/upload/phone/113875199/20170217164544307.jpg";
						String img_url2 = RealmName.REALM_NAME_HTTP + "/upload/phone/113875199/20170217164544307.jpg";
						System.out.println("img_url2=============="+img_url2);
						Bitmap bmp = GetImgUtil.getImage(img_url2);// BitmapFactory��ͼƬ������
//						Bitmap bitMap_tx = Utils.toRoundBitmap(bmp,null);// ���ʱ���ͼƬ�Ѿ��������Բ�ε���
//						System.out.println("bitMap_tx=============="+bitMap_tx);
						System.out.println("bmp=============="+bmp);
						list_data2.add(bmp);
					} catch (Exception e) {
						Log.i("ggggg", e.getMessage());
					}
				}
			};
		
	public void setListViewHeightBasedOnChildren(ListView listView) {   
        // ��ȡListView��Ӧ��Adapter   
        ListAdapter listAdapter = listView.getAdapter();   
        if (listAdapter == null) {   
            return;   
        }   
   
        int totalHeight = 0;   
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {   
            // listAdapter.getCount()�������������Ŀ   
            View listItem = listAdapter.getView(i, null, listView);   
            // ��������View �Ŀ��   
            listItem.measure(0, 0);    
            // ͳ������������ܸ߶�   
            totalHeight += listItem.getMeasuredHeight();    
        }   
   
        ViewGroup.LayoutParams params = listView.getLayoutParams();   
        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));   
        // listView.getDividerHeight()��ȡ�����ָ���ռ�õĸ߶�   
        // params.height���õ�����ListView������ʾ��Ҫ�ĸ߶�   
        listView.setLayoutParams(params);   
    }   

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.fanhui:
			finish();
			break;
		case R.id.webview:
//			loadWeather();
//			webview.loadUrl("http://183.62.138.31:1010/mobile/goods/conent-"+data.article_id+".html");//��Ʒ����
			webview.loadUrl(RealmName.REALM_NAME_HTTP+"/mobile/goods/conent-"+data.article_id+".html");//��Ʒ����
			break;
		case R.id.tv_anniu1://��Ա�����������
			  System.out.println("=====1======"+ct_id);
				if (UserLoginActivity.wx_fanhui == false) {
					Intent intent = new Intent(JuJingCaiXqActivity.this,UserLoginActivity.class);
					startActivity(intent);
				}else {
				Intent intentll = new Intent(JuJingCaiXqActivity.this,DBFengXiangActivity.class);
				intentll.putExtra("ct_id",ct_id);
				intentll.putExtra("title",data.getTitle());
				intentll.putExtra("fx_shuzi", "groupon");
//				intentll.putExtra("img_url", data.share_img_url);
				intentll.putExtra("subtitle",data.getSubtitle());
				intentll.putExtra("company_id",data.getCompany_id());
				intentll.putExtra("fx_shuzi","groupon");
				intentll.putExtra("img_url", "");
//				intentll.putExtra("subtitle","");
				startActivity(intentll);
				}
			break;
		case R.id.ll_dianping://�ղ�
//			progress.CreateProgress();
//			spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
//			String user_name = spPreferences.getString("user", "");
//			String user_id = spPreferences.getString("user_id", "");
			System.out.println("user_name_phone=======�ղ�========="+user_name_phone);
			System.out.println("nickname========"+nickname);
			if (!nickname.equals("")) {
				if (!user_name_phone.equals("")) {
					System.out.println("2================"+user_name_phone);
					AsyncHttp.get(RealmName.REALM_NAME_LL+ "/user_favorite?article_id="+data.article_id+"&user_name="+user_name_phone+"" +
							"&user_id="+user_id+"&tags=", new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int arg0, String arg1) {
							// TODO Auto-generated method stub
							super.onSuccess(arg0, arg1);
							try {
								JSONObject jsonObject = new JSONObject(arg1);
								System.out.println("�ղ�================"+arg1);
								String status = jsonObject.getString("status");
								String info = jsonObject.getString("info");
								if (status.equals("y")) {
									progress.CloseProgress();
								Toast.makeText(getApplicationContext(), info, 200).show();
								}else {
									progress.CloseProgress();
									Toast.makeText(getApplicationContext(), info, 200).show();
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
					 }, getApplicationContext());
				} else {
				Intent intent = new Intent(JuJingCaiXqActivity.this, TishiWxBangDingActivity.class);
				startActivity(intent);
				progress.CloseProgress();
				}
			}else {
				try {
					
			if (!user_name_phone.equals("")) {
				System.out.println("2================"+user_name_phone);
				AsyncHttp.get(RealmName.REALM_NAME_LL+ "/user_favorite?article_id="+data.article_id+"&user_name="+user_name_phone+"" +
						"&user_id="+user_id+"&tags=", new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						// TODO Auto-generated method stub
						super.onSuccess(arg0, arg1);
						try {
							JSONObject jsonObject = new JSONObject(arg1);
							System.out.println("�ղ�================"+arg1);
							String status = jsonObject.getString("status");
							String info = jsonObject.getString("info");
							if (status.equals("y")) {
								progress.CloseProgress();
							Toast.makeText(getApplicationContext(), info, 200).show();
							}else {
								progress.CloseProgress();
								Toast.makeText(getApplicationContext(), info, 200).show();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				 }, getApplicationContext());
			} else {
				Intent intent = new Intent(JuJingCaiXqActivity.this,UserLoginActivity.class);
				startActivity(intent);
				progress.CloseProgress();
			}
			
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			}
			break;
		case R.id.ll_lijigoumai://��������
//			Intent intent = new Intent(JuJingCaiXqActivity.this, JuTuanXqTiShiActivity.class);
//			startActivity(intent);
			fangshi = 3;
			type_xq = true;
			type_spec_item = false;//������������������ʾ
			spec_text_list = 2;//�����ײ��ж�Ϊ2
			taocan_type = true;//�ж���Ʒ�ײͼ۸�
//			JuJingCaiXqActivity.type_spec_item = false;
			CommomConfrim.showSheet(JuJingCaiXqActivity.this, new onDeleteSelect() {
				@Override
				public void onClick(String resID) {
					// TODO Auto-generated method stub
					
					  }
			}, data.id);
//			Intent intent = new Intent(JuJingCaiXqActivity.this,XiaoShouShuXingActivity.class);
//			intent.putExtra("id", data.id);
//			intent.putExtra("type_xq", getIntent().getStringExtra("type_xq"));
//			startActivity(intent);
//			if (!nickname.equals("")) {
//				if (!user_name_phone.equals("")) {
//					loadgouwuche();
//				} else {
//				Intent intent = new Intent(JuJingCaiXqActivity.this, TishiWxBangDingActivity.class);
//				startActivity(intent);
//				progress.CloseProgress();
//				}
//			} else {
//			if (!user_name_phone.equals("")) {
//			loadgouwuche();
//			} else {
//				Intent intent = new Intent(JuJingCaiXqActivity.this,UserLoginActivity.class);
//				startActivity(intent);
//				progress.CloseProgress();
//			}
//			}
			break;
		case R.id.ll_tuangou://�Ź�
			fangshi = 4;
			type_xq = true;//���������������Բ���ʾ
			type_spec_item = true;//������������������ʾ
			spec_text_list = 2;//�����ײ��ж�Ϊ2
			taocan_type = false;//�����һ��ж��ײͼ۸���ʾ
			CommomConfrim.showSheet(JuJingCaiXqActivity.this, new onDeleteSelect() {
				@Override
				public void onClick(String resID) {
					// TODO Auto-generated method stub
					
					  }
			     }, data.id);
			
//			Intent intent1 = new Intent(JuJingCaiXqActivity.this,XiaoShouShuXingActivity.class);
//			intent1.putExtra("id", data.id);
//			intent1.putExtra("type_xq", getIntent().getStringExtra("type_xq"));
//			startActivity(intent1);
			
//			if (!nickname.equals("")) {
//				if (!user_name_phone.equals("")) {
//					Intent intent = new Intent(JuJingCaiXqActivity.this,JuTuanConfrimActivity.class);
//					choujiang = getIntent().getStringExtra("choujiang");
//					System.out.println("choujiang===================="+choujiang);
//					if (choujiang != null) {
////						zhuangtai = "110";
//						intent.putExtra("type_wx","type_wx");//֧����ʽ
//					}
//					String groupon_id = getIntent().getStringExtra("id");
//					System.out.println("zhuangtai================"+zhuangtai);
////				 	if (foreman_id == null) {
////				 		foreman_id = user_id;
////				 		foreman_name = user_name_phone;
////					}
////				 	System.out.println("foreman_id================================="+foreman_id);
////				 	System.out.println("foreman_name================================="+foreman_name);
//					intent.putExtra("title", data.title);
//					intent.putExtra("price", data.price);
//					intent.putExtra("img_url", data.img_url);
//					intent.putExtra("groupon_price", tuangoujia);
//					intent.putExtra("item_id", item_id);
//					intent.putExtra("fx_key", "fx_key");
//					intent.putExtra("ct_id", ct_id);
////					intent.putExtra("foreman_id", data.user_id);
////					intent.putExtra("foreman_name", data.user_name);
//					intent.putExtra("foreman_id",user_id);
//					intent.putExtra("foreman_name",user_name_phone);
//					intent.putExtra("id",groupon_id);
//					intent.putExtra("groupon_no", groupon_no);
//					intent.putExtra("100", zhuangtai);
//					intent.putExtra("stare", "2");
//					intent.putExtra("type","1");//�۾���״̬
//					intent.putExtra("jiekou","1");//�۾��ʽӿ�״̬
//					intent.putExtra("fx_shuzi","groupon");
//					startActivity(intent);
//				} else {
//				Intent intent = new Intent(JuJingCaiXqActivity.this, TishiWxBangDingActivity.class);
//				startActivity(intent);
//				progress.CloseProgress();
//				}
//			}else {
//				System.out.println("user_name_phone===================="+user_name_phone);
//			if (!user_name_phone.equals("")) {
				try {
					
//			Intent intent = new Intent(JuJingCaiXqActivity.this,JuTuanConfrimActivity.class);
//			choujiang = getIntent().getStringExtra("choujiang");
//			System.out.println("choujiang===================="+choujiang);
////			if (choujiang != null) {
//////				zhuangtai = "110";
////				intent.putExtra("type_wx","type_wx");//֧����ʽ
////			}
////			System.out.println("zhuangtai================================="+zhuangtai);
//			String groupon_id = getIntent().getStringExtra("id");
////		 	if (foreman_id == null) {
////		 		foreman_id = user_id;
////		 		foreman_name = user_name_phone;
////			}
////		 	System.out.println("foreman_id==================1==============="+foreman_id);
////		 	System.out.println("foreman_name=================1================"+foreman_name);
//			System.out.println("user_id==================1==============="+user_id);
//		 	System.out.println("user_name_phone=================1================"+user_name_phone);
////			System.out.println("sp_id==========1======"+sp_id);
//			intent.putExtra("title", data.title);
//			intent.putExtra("price", data.sell_price);
//			intent.putExtra("img_url", data.img_url);
////			intent.putExtra("groupon_price", tuangoujia);
//			intent.putExtra("groupon_price", data.price);
//			intent.putExtra("item_id", item_id);
////			intent.putExtra("fx_key", "fx_key");
//			intent.putExtra("ct_id", ct_id);
////			intent.putExtra("foreman_id", data.user_id);
////			intent.putExtra("foreman_name", data.user_name);
//			intent.putExtra("foreman_id",user_id);
//			intent.putExtra("foreman_name",user_name_phone);
//			intent.putExtra("groupon_no", groupon_no);
////			intent.putExtra("id",groupon_id);
////			intent.putExtra("100", zhuangtai);
//			intent.putExtra("stare", "2");
//			intent.putExtra("type","1");//�۾���״̬
//			intent.putExtra("jiekou","1");//�۾��ʽӿ�״̬
//			intent.putExtra("fx_shuzi","groupon");
//		 	intent.putExtra("type_wx","type_wx");//֧����ʽ
//			startActivity(intent);
			
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
//			} else {
//				Intent intent = new Intent(JuJingCaiXqActivity.this,UserLoginActivity.class);
//				startActivity(intent);
//				progress.CloseProgress();
//			}
//			}
			break;
		default:
			break;
		}
	}
	
	
      /**
       * ��ȡ���ŵĵ���ʱ	
       */
      public void getCantuantime() {
    	  System.out.println("��ȡ���ŵĵ���ʱ----------");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("timer_time-------------"+timer_time);
		System.out.println("datetime-------------"+datetime);
		try {
//			timer_time = "2017-05-02 20:04:30";
//			timer_time = "2017-05-16 08:03:05";
			now_1 = df.parse(timer_time);
		} catch (java.text.ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
//			datetime = "2017-05-02 18:04:10";
//			datetime = "2017-05-14 17:23:15";
			date_1 = df.parse(datetime);
		} catch (java.text.ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
		   long l=now_1.getTime()-date_1.getTime();
		   long day=l/(24*60*60*1000);
		   long hourl=(l/(60*60*1000)-day*24);
		   min=((l/(60*1000))-day*24*60-hour*60);
		   s=(l/1000-day*24*60*60-hour*60*60-min*60);
		   
           long xiaoshi = day*24;
		   zongxs = xiaoshi;
		   System.out.println("----------"+xiaoshi+"--"+zongxs);
		   System.out.println(""+day+"��"+hourl+"Сʱ"+min+"��"+s+"��");
		   
		    time = (zongxs * 3600 + min * 60 + s) * 1000; 
		    
			System.out.println("time--------------"+time);
			count = new MyCount(time, 1000);
			System.out.println("2-------------"+count);
			count.start();//��ʼ��ʱ 
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}


	/**
	 * �����嵥
	 */
	private void loadgouwuche() {
		try {
//			progress.CreateProgress();
			
			AsyncHttp.get(RealmName.REALM_NAME_LL+ "/add_shopping_buy?user_id="+user_id+"&user_name="+user_name_phone+
					"&article_id="+data.article_id+"&goods_id="+data.goods_id+"&quantity="+1+"",new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int arg0,String arg1) {
							// TODO Auto-generated method stub
							super.onSuccess(arg0, arg1);
							try {
								JSONObject jsonObject = new JSONObject(arg1);
								String status = jsonObject.getString("status");
								System.out.println("�����嵥================"+arg1);
								String info = jsonObject.getString("info");
								if (status.equals("y")) {
									progress.CloseProgress();
									JSONObject obj = jsonObject.getJSONObject("data");
									String id = obj.getString("id");
//									String count = obj.getString("count");
//									Toast.makeText(JuTuanGouXqActivity.this, info, 200).show();
										Intent intent=new Intent(JuJingCaiXqActivity.this, JuTuanConfrimActivity.class);
//										if (choujiang != null) {
//											intent.putExtra("type_wx","type_wx");//֧����ʽ
//										}
										
										intent.putExtra("shopping_ids",id);
										intent.putExtra("fx_key", "fx_key");
//										intent.putExtra("item_id", goumai_id);
//										intent.putExtra("foreman_id", foreman_id);
//										intent.putExtra("foreman_name", data.user_name);
										startActivity(intent);
								}else {
									progress.CloseProgress();
									Toast.makeText(JuJingCaiXqActivity.this, info, 200).show();
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							progress.CloseProgress();
						}
						@Override
						public void onFailure(Throwable arg0, String arg1) {
							// TODO Auto-generated method stub
							System.out.println("==========================���ʽӿ�ʧ�ܣ�");
							System.out.println("========================="+arg0);
							System.out.println("=========================="+arg1);
							super.onFailure(arg0, arg1);
						}
						

					}, getApplicationContext());
			
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
	}
	
	
	public class MyCountAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return list_ll.size();
		}

		@Override
		public Object getItem(int position) {
			return list_ll.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if(convertView == null){
				convertView = View.inflate(JuJingCaiXqActivity.this, R.layout.cantuanjia_item, null);
				holder = new ViewHolder();
				holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			TextView tv_renshu = (TextView) convertView.findViewById(R.id.tv_renshu);
			TextView tv_qucantuan = (TextView) convertView.findViewById(R.id.tv_qucantuan);
			
			try {
				
			System.out.println("user_id-------------"+user_id);
			System.out.println("list_ll.get(position).getForeman_id()-------------"+list_ll.get(position).getForeman_id());
//			System.out.println("people-------------"+people);
			System.out.println("type-------------"+type);
//			if (type != null) {
				
			if (user_id.equals(list_ll.get(position).getForeman_id())) {
				try {
					System.out.println("list_ll.get(position).getTimer_time()-------------"+list_ll.get(position).getTimer_time());
					ll_qu_kaituan.setVisibility(View.VISIBLE);
					new_list.setVisibility(View.GONE);
//					timer_time = list_ll.get(position).getTimer_time();
//					System.out.println("timer_time------11-------"+timer_time);
//					System.out.println("people-----1--------"+people);
					String people = String.valueOf(list_ll.get(position).getActivity_people()- list_ll.get(position).getActivity_member());
					System.out.println("people-----2--------"+people);
//					String people_ct = String.valueOf(list_ll.get(position).getGroupon_item_people()- list_ll.get(position).getGroupon_item_member());
//					System.out.println("people_ct-------------"+people_ct);
//					tv_tuanshu_ll.setText(people);
					
//					getCantuantime();//��ȡ���ŵĵ���ʱ
					
					Message msg = new Message();
					msg.what = 8;
					msg.obj = String.valueOf(list_ll.get(position).getGroupon_item_people()- list_ll.get(position).getGroupon_item_member());
					handler.sendMessage(msg);
					
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}else {
				new_list.setVisibility(View.GONE);
			}
			
			
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			
//			Date date_finish = listData.get(position);
			
//			updateTextView( now.getTime()-time_Current, holder);
			
			//��ȡ����id����
			if (user_id.equals(list_ll.get(position).getForeman_id())) {
				Message msg = new Message();
				msg.what = 7;
				msg.obj = list_ll.get(position).getOrder_id();
				handler.sendMessage(msg);
			}
			
		    
			return convertView;
		}
		
		/****
		 * ˢ�µ���ʱ�ؼ�
		 */
//		public void updateTextView(long times_remain,ViewHolder hoder) {
//			
//			if (times_remain <= 0) {
//				Message msg = new Message();
//				msg.what = 5;
//				handler.sendMessage(msg);
//				return;
//			}
//			
//			current_time = times_remain;
////			System.out.println("current_time-------------"+current_time);
//			   long day=current_time/(24*60*60*1000);
//			   long hour=(current_time/(60*60*1000)-day*24);
//			   long min=((current_time/(60*1000))-day*24*60-hour*60);
//			   long s=(current_time/1000-day*24*60*60-hour*60*60-min*60);
////			   System.out.println(""+day+"��"+hour+"Сʱ"+min+"��"+s+"��");
//			hoder.tv_time.setText("ʣ��ʱ��: "+day+"��" + hour + "Сʱ" + min + "��" + s+"��"); 
//		}
		
		private class ViewHolder{
			/** Сʱ **/
			private TextView tv_time;
			/** Сʱ **/
			private TextView tv_hour;
			/** ���� **/
			private TextView tv_minute;
			/** �� **/
			private TextView tv_second;		
		}
	}
	
	//����ʵ�ּ�ʱ���ܵ��� 
	class MyCount extends MyCountdownTimer { 

		public MyCount(long millisInFuture, long countDownInterval) { 
			super(millisInFuture, countDownInterval); 
		} 
		
		@Override 
		public void onFinish() { 
			//ý����� 
			txt_time.setText("�����ѽ���"); 
//			ll_qu_kaituan.setVisibility(View.GONE);
//			ll_qu_kaituan.setBackgroundResource(R.drawable.bg_ccc_3_5_bg); 
			tv_anniu1.setVisibility(View.GONE);
			tv_anniu2.setVisibility(View.VISIBLE);
		} 
		
		//����ʣ��ʱ�� 
		@Override 
		public void onTick(long millisUntilFinished, int percent) {
			current_time = millisUntilFinished;
			
			   long day=current_time/(24*60*60*1000);
			   long hour=(current_time/(60*60*1000)-day*24);
			   long min=((current_time/(60*1000))-day*24*60-hour*60);
			   long s=(current_time/1000-day*24*60*60-hour*60*60-min*60);
			   
//			   System.out.println(""+day+"��"+hour+"Сʱ"+min+"��"+s+"��");
//			   txt_time.setText("ʣ��ʱ��: "+day+":" + hour + ":" + min + ":" + s); 
			   txt_time.setText("ʣ��: "+day+"��"+hour+"Сʱ"+min+"��"+s+"��"); 
		} 
	} 
	
	
}
