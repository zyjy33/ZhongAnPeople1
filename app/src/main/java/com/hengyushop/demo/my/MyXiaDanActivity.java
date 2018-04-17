package com.hengyushop.demo.my;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.androidquery.AQuery;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.demo.home.XiaDanActivity;
import com.hengyushop.demo.home.jsondata;
import com.hengyushop.demo.wec.NewWare;
import com.hengyushop.entity.DataBean_2;
import com.hengyushop.entity.DataBean_ll;
import com.hengyushop.entity.ShopCartData;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.R;
import com.zams.www.UserLoginActivity;
import com.zijunlin.Zxing.Demo.CaptureActivity;

public class MyXiaDanActivity extends BaseActivity implements OnClickListener {
//	public static List<ShopCartData> list = new ArrayList<ShopCartData>();
	public static List<jsondata> list_ll = new ArrayList<jsondata>();
	public static List<DataBean_ll> list_canshu = new ArrayList<DataBean_ll>();
	private static final int INITIALIZE = 0;
	private SharedPreferences spPreferences;
	private ListView mListView;// �б�
	public static String user_name,user_id;
	private ListAdapter mListAdapter;// adapter
	private LinearLayout adv_pager;
	private LinearLayout list_shops, list_none,ll_xianshi;
	public static List<DataBean_ll> mListData = new ArrayList<DataBean_ll>();// ����
//	double di_hongbao = 0;
	public static Handler handler1;
	private boolean isBatchModel;// �Ƿ��ɾ��ģʽ

	private RelativeLayout mBottonLayout;
	private CheckBox mCheckAll; // ȫѡ ȫ��ѡ
	private TextView mEdit; // �л���ɾ��ģʽ

	private TextView mPriceAll; // ��Ʒ�ܼ�

	private TextView mSelectNum; // ѡ������

	private TextView mFavorite; // �Ƶ��ղؼ�,����

	private TextView mDelete; // ɾ�� ����
	
	private TextView subtitle;

	private double totalPrice = 0; // ��Ʒ�ܼ�
	public static double dzongjia = 0;
	/** ����ģʽ�£�������¼��ǰѡ��״̬ */
	private SparseArray<Boolean> mSelectState = new SparseArray<Boolean>();
//	private ImageView back;
	private boolean flag = true; // ȫѡ��ȫȡ��
	List<DataBean_ll> result = new ArrayList<DataBean_ll>();
	private DialogProgress progress;
	private AQuery query;
	private static List<String> list_id = new ArrayList<String>(); 
	private static List<String> list_size = new ArrayList<String>(); 
	String num = "1";
	public static StringBuffer str;
	boolean zhuangtai = true;
	private Button btn_register;
	ShopCartData data;
	jsondata jsondata;
	String jsondata_zhi;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_xiadan_list);
		progress = new DialogProgress(MyXiaDanActivity.this);
		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		user_name = spPreferences.getString("user_name", "");
		user_id = spPreferences.getString("user_id", "");
		query = new AQuery(this);
		initView();
		initListener();
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		try {
			
//		if (list_id.size() > 0) {
//			list_id.clear();
//		}
		
		//����������
//		if (list_size.size() > 0) {
//			list_size.clear();
//			mDelete.setText(getResources().getString(R.string.menu_sett)+"(" + list_size.size() + ")");
//		}
		
//		getgouwuche();
		
			System.out.println("CaptureActivity.ytpe========"+CaptureActivity.ytpe);
			if (HaomaActivity.zhuangtai == true) {
				HaomaActivity.zhuangtai = false;
				System.out.println("====����ֵ=====================");
				
			}else {
				
		if (CaptureActivity.ytpe.equals("2")) {
			String content = CaptureActivity.bianma;
			System.out.println("content========22========"+content);
			CaptureActivity.zhuangtai = false;
			if (content != null) {
				getgouwuche(content);
			}
		 }else if (CaptureActivity.ytpe.equals("1")){
			String content = getIntent().getStringExtra("bianma");
			System.out.println("content==========11======"+content);
			if (content != null) {
				getgouwuche(content);
			}
		  }
	    else {
	    String content = "6920110106345";//6939814195647  6920110106345
		System.out.println("content==========22======"+content);
		if (content != null) {
			getgouwuche(content);
		  }
		
        }
		
	   }
		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
	
	private void getgouwuche(String content) {
		// TODO Auto-generated method stub
		progress.CreateProgress();
//		result = new ArrayList<DataBean_ll>();
		System.out.println("content=====================content" + content);
		if (content != null) {
		AsyncHttp.get(RealmName.REALM_NAME_LL+ "/get_goods_content?goods_no="+content+""
				,new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0,String arg1) {
						// TODO Auto-generated method stub
						super.onSuccess(arg0, arg1);
						try {
							JSONObject jsonObject = new JSONObject(arg1);
							System.out.println("=====================����ֵ11" + arg1);
							String status = jsonObject.getString("status");
							String info = jsonObject.getString("info");
							if (status.equals("y")) {
								try {
								JSONObject jsonobt = jsonObject.getJSONObject("data");
								DataBean_ll dm = new DataBean_ll();
								dm.setId(jsonobt.getInt("id"));
								dm.setTitle(jsonobt.getString("title"));
								dm.setImg_url(jsonobt.getString("img_url"));
								String spec_item = jsonobt.getString("spec_item");
								JSONArray ja = new JSONArray(spec_item);
								for (int j = 0; j < ja.length(); j++) {
									JSONObject obct = ja.getJSONObject(j);
									dm.setMarket_price(obct.getString("market_price"));
									dm.setSell_price(obct.getDouble("sell_price"));
									dm.setArticle_id(obct.getString("article_id"));
									dm.setGoods_id(obct.getString("goods_id"));
//									dm.setQuantity(obct.getInt("quantity"));
									
									jsondata = new jsondata();
									jsondata.setGoods_id(obct.getString("goods_id"));
									jsondata.setArticle_id(obct.getString("article_id"));
									
								}
								result.add(dm);
//								list.add(data);
								list_ll.add(jsondata);
								System.out.println("====11====================="+result.size());
								
								
								CaptureActivity.bianma = null;
								progress.CloseProgress();
								
								} catch (Exception e) {
									// TODO: handle exception
									e.printStackTrace();
								}
							} else {
								progress.CloseProgress();
								Toast.makeText(MyXiaDanActivity.this,info, 200).show();
							}
							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

				}, null);
		
		}else {
			progress.CloseProgress();
//			adv_pager.setVisibility(View.VISIBLE);
		}
		
		totalPrice = 0;
		mPriceAll.setText("��"+totalPrice);
		mCheckAll.setChecked(false);
		loadData();
		System.out.println("result22-------------"+result.size());
	}

	
	private void getgouwuche() {
		// TODO Auto-generated method stub
//		progress.CreateProgress();
		result = new ArrayList<DataBean_ll>();
		user_id = spPreferences.getString("user_id", "");
		if (!user_id.equals("")) {
			
		AsyncHttp.get(RealmName.REALM_NAME_LL+ "/get_shopping_cart?pageSize=10&pageIndex=1&user_id="+user_id+""
				,new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0,String arg1) {
						// TODO Auto-generated method stub
						super.onSuccess(arg0, arg1);
						try {
							JSONObject jsonObject = new JSONObject(arg1);
							System.out.println("1================"+arg1);
							JSONArray jsot = jsonObject.getJSONArray("data");
							System.out.println("jsot================"+jsot.length());
							if (jsot.length() > 0) {
							for (int i = 0; i < jsot.length(); i++) {
								JSONObject object = jsot.getJSONObject(i);
								DataBean_ll dm = new DataBean_ll();
								dm.setId(object.getInt("id"));
								dm.setTitle(object.getString("title"));
								dm.setMarket_price(object.getString("market_price"));
								dm.setSell_price(object.getDouble("sell_price"));
								dm.setImg_url(object.getString("img_url"));
								dm.setQuantity(object.getInt("quantity"));
								dm.setArticle_id(object.getString("article_id"));
								dm.setGoods_id(object.getString("goods_id"));
								result.add(dm);
							}
							progress.CloseProgress();
							adv_pager.setVisibility(View.GONE);
							subtitle.setVisibility(View.VISIBLE);
							mListView.setVisibility(View.VISIBLE);
							ll_xianshi.setVisibility(View.VISIBLE);
							System.out.println("1================");
							}else {
								progress.CloseProgress();
								System.out.println("2================");
								adv_pager.setVisibility(View.VISIBLE);
								subtitle.setVisibility(View.GONE);
								mListView.setVisibility(View.GONE);
//								mPriceAll.setText("��"+0.00);
								ll_xianshi.setVisibility(View.GONE);
							}
							
//							refreshListView();
							progress.CloseProgress();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

				}, null);
		
		}else {
			progress.CloseProgress();
//			adv_pager.setVisibility(View.VISIBLE);
		}
		totalPrice = 0;
		mPriceAll.setText("��"+totalPrice);
		mCheckAll.setChecked(false);
		loadData();
		System.out.println("result22-------------"+result.size());
	}
	
	private void initView() {
		try {
//			back = (ImageView) findViewById(R.id.back);
			adv_pager = (LinearLayout)findViewById(R.id.adv_pager);
			ll_xianshi = (LinearLayout)findViewById(R.id.ll_xianshi);
			mBottonLayout = (RelativeLayout) findViewById(R.id.cart_rl_allprie_total);
			mCheckAll = (CheckBox) findViewById(R.id.check_box_all);
			mEdit = (TextView) findViewById(R.id.subtitle);
			mPriceAll = (TextView) findViewById(R.id.tv_cart_total);
			// mSelectNum = (TextView) findViewById(R.id.tv_cart_select_num);
			mFavorite = (TextView) findViewById(R.id.tv_cart_move_favorite);
			mDelete = (TextView) findViewById(R.id.tv_cart_buy_or_del);
			subtitle = (TextView) findViewById(R.id.subtitle);
			mListView = (ListView) findViewById(R.id.listview);
//			mListView.setSelector(R.drawable.list_selector);
			btn_register = (Button) findViewById(R.id.btn_register);
			
			//���ﳵ����Ʒȥ���
					btn_register.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							user_id = spPreferences.getString("user_id", "");
							if (user_id.equals("")) {
								Intent intentll = new Intent(MyXiaDanActivity.this,UserLoginActivity.class);
								startActivity(intentll);
							} else {
									Intent intentll = new Intent(MyXiaDanActivity.this,NewWare.class);
									startActivity(intentll);
							}
						}
					});
					ImageView iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
					iv_fanhui.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							finish();
						}
					});
					
					TextView tv_xiabu = (TextView) findViewById(R.id.tv_xiabu);
					tv_xiabu.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							Intent Intent2 = new Intent(MyXiaDanActivity.this,CaptureActivity.class);
//							Intent2.putExtra("sp_sys", "2");
							startActivity(Intent2);
						}
					});
				
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	private void initListener() {
		mEdit.setOnClickListener(this);
		mDelete.setOnClickListener(this);
		mCheckAll.setOnClickListener(this);
//		back.setOnClickListener(this);
	}

	private void loadData() {
		new LoadDataTask().execute(new Params(INITIALIZE));
	}

	private void refreshListView() {
		if (mListAdapter == null) {
			mListAdapter = new ListAdapter();
			mListView.setAdapter(mListAdapter);
			mListView.setOnItemClickListener(mListAdapter);

		} else {
			mListAdapter.notifyDataSetChanged();
		}
	}

	private List<DataBean_ll> getData() {
		System.out.println("result11-------------"+result.size());
		return result;
	}

	class Params {
		int op;

		public Params(int op) {
			this.op = op;
			System.out.println("result1-------------");
		}

	}

	class Result {
		int op;
		List<DataBean_ll> list;
	}

	private class LoadDataTask extends AsyncTask<Params, Void, Result> {
		@Override
		protected Result doInBackground(Params... params) {
			Params p = params[0];
			Result result = new Result();
			result.op = p.op;
			try {// ģ���ʱ
				Thread.sleep(500L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			result.list = getData();
			return result;
		}

		@Override
		protected void onPostExecute(Result result) {
			super.onPostExecute(result);
			if (result.op == INITIALIZE) {
				mListData = result.list;
				System.out.println("result2-------------");
			} else {
				System.out.println("result3-------------");
				mListData.addAll(result.list);
				Toast.makeText(MyXiaDanActivity.this, "��ӳɹ���",Toast.LENGTH_SHORT).show();
			}

			refreshListView();
		}

	}

	boolean isSelect = false;

	private class ListAdapter extends BaseAdapter implements
			OnItemClickListener {
		@Override
		public int getCount() {
			return mListData.size();
		}

		@Override
		public Object getItem(int position) {
			return mListData.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		ViewHolder holder = null;

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			View view = convertView;
			if (view == null) {
				try {
					
				holder = new ViewHolder();
				view = LayoutInflater.from(MyXiaDanActivity.this).inflate(R.layout.cart_list_item, null);
				holder.checkBox = (CheckBox) view.findViewById(R.id.check_box);
				holder.tv_size = (TextView) view.findViewById(R.id.tv_size);
				holder.image = (ImageView) view.findViewById(R.id.iv_adapter_list_pic);
				holder.content = (TextView) view.findViewById(R.id.tv_intro);
				holder.carNum = (TextView) view.findViewById(R.id.tv_num);
				holder.price = (TextView) view.findViewById(R.id.tv_price);
				holder.add = (TextView) view.findViewById(R.id.tv_add);
				holder.red = (TextView) view.findViewById(R.id.tv_reduce);
				holder.frontView = view.findViewById(R.id.item_left);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}

			final DataBean_ll data = mListData.get(position);
			bindListItem(holder, data);

//			mListData.get(position).setChoose(true);
			if (data != null) {
				// �ж��Ƿ�ѡ��
				if (data.isChoose()) {
					holder.checkBox.setChecked(true);
				} else {
					holder.checkBox.setChecked(false);
				}

				// ѡ�в���
//				holder.checkBox.setOnClickListener(new CheckBoxOnClick(data));
				// ���ٲ���
				holder.red.setOnClickListener(new ReduceOnClick(data,holder.carNum));

				// ���Ӳ���
				holder.add.setOnClickListener(new AddOnclick(data,holder.carNum));

			}
			return view;
		}

		class CheckBoxOnClick implements OnClickListener {
			DataBean_ll shopcartEntity;

			public CheckBoxOnClick(DataBean_ll shopcartEntity) {
				this.shopcartEntity = shopcartEntity;
			}

			@Override
			public void onClick(View view) {
				CheckBox cb = (CheckBox) view;
				if (cb.isChecked()) {
					shopcartEntity.setChoose(true);
					count();
				} else {
					shopcartEntity.setChoose(false);
				}
				select();

			}

		}

		private class AddOnclick implements OnClickListener {
			DataBean_ll shopcartEntity;
			TextView shopcart_number_btn;

			private AddOnclick(DataBean_ll shopcartEntity,
					TextView shopcart_number_btn) {
				this.shopcartEntity = shopcartEntity;
				this.shopcart_number_btn = shopcart_number_btn;
				
			}

			@Override
			public void onClick(View arg0) {
				shopcartEntity.setChoose(true);
				String numberStr = shopcart_number_btn.getText().toString();
				if (!TextUtils.isEmpty(numberStr)) {
					int number = Integer.parseInt(numberStr);

					int currentNum = number + 1;
					// �����б�
					shopcartEntity.setQuantity(currentNum);
					holder.carNum.setText("" + currentNum);
					int cart_id = shopcartEntity.getId();
					System.out.println("============cart_id=============="+cart_id);
					
//					AsyncHttp.get(RealmName.REALM_NAME_LL+ "/cart_goods_update?cart_id="+cart_id+"&user_id="+user_id+"&quantity="+currentNum+"",new AsyncHttpResponseHandler() {
//						@Override
//						public void onSuccess(int arg0, String arg1) {
//							// TODO Auto-generated method stub
//							System.out.println("==========================���ʽӿڳɹ���"+arg1);
//							super.onSuccess(arg0, arg1);
//						}
//						
//					}, MyXiaDanActivity.this);
					notifyDataSetChanged();
				}
				count();
			}
			
			

		}

		private class ReduceOnClick implements OnClickListener {
			DataBean_ll shopcartEntity;
			TextView shopcart_number_btn;

			private ReduceOnClick(DataBean_ll shopcartEntity,
					TextView shopcart_number_btn) {
				this.shopcartEntity = shopcartEntity;
				this.shopcart_number_btn = shopcart_number_btn;
			}

			@Override
			public void onClick(View arg0) {
				shopcartEntity.setChoose(true);
				String numberStr = shopcart_number_btn.getText().toString();
				if (!TextUtils.isEmpty(numberStr)) {
					int number = Integer.parseInt(numberStr);
					if (number == 1) {
						 Toast.makeText(MyXiaDanActivity.this, "�������¼�����", 200).show();
					} else {
						int currentNum = number - 1;
						// �����б�
						shopcartEntity.setQuantity(currentNum);

						holder.carNum.setText("" + currentNum);
						int cart_id = shopcartEntity.getId();
						System.out.println("============cart_id=============="+cart_id);
//						AsyncHttp.get(RealmName.REALM_NAME_LL+ "/cart_goods_update?cart_id="+cart_id+"&user_id="+user_id+"&quantity="+currentNum+"",new AsyncHttpResponseHandler() {
//							@Override
//							public void onSuccess(int arg0, String arg1) {
//								// TODO Auto-generated method stub
//								System.out.println("==========================2���ʽӿڳɹ���"+arg1);
//								super.onSuccess(arg0, arg1);
//							}
//							
//						}, MyXiaDanActivity.this);
						notifyDataSetChanged();

					}

				}
				count();
			}

		}

		private void bindListItem(ViewHolder holder, DataBean_ll data) {

			// holder.shopName.setText(data.getShopName());
			holder.content.setText(data.getTitle());
			holder.price.setText("��" + data.getSell_price());
			System.out.println("data.getQuantity()=============="+data.getQuantity());
			holder.carNum.setText(data.getQuantity() + "");
//			holder.carNum.setText("1");
			holder.tv_size.setText("��"+data.getMarket_price());
			holder.tv_size.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
			
			query.id(holder.image).image(RealmName.REALM_NAME_HTTP+data.getImg_url());
			int _id = data.getId();
			boolean selected = mSelectState.get(_id, false);
//			System.out.println("selected-------------"+selected);
			holder.checkBox.setChecked(selected);

		}

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			DataBean_ll bean = mListData.get(position);

			ViewHolder holder = (ViewHolder) view.getTag();
			int _id = (int) bean.getId();

			boolean selected = !mSelectState.get(_id, false);
			holder.checkBox.toggle();

			// ��CheckBox��ѡ��״����¼����
			mListData.get(position).setChoose(holder.checkBox.isChecked());
			// ����ѡ����Ŀ
			if (holder.checkBox.isChecked() == true) {
				list_size.add(num);
				if (zhuangtai == true) {
					mDelete.setText(getResources().getString(R.string.menu_sett)+"(" + list_size.size() + ")");
				}else {
					mDelete.setText(getResources().getString(R.string.menu_del)+"(" + list_size.size() + ")");
				}
				totalPrice += bean.getQuantity() * bean.getSell_price();
			} else {
				list_size.remove(num);
				if (zhuangtai == true) {
					mDelete.setText(getResources().getString(R.string.menu_sett)+"(" + list_size.size() + ")");
				}else {
					mDelete.setText(getResources().getString(R.string.menu_del)+"(" + list_size.size() + ")");
				}
				mSelectState.delete(position);
				totalPrice -= bean.getQuantity() * bean.getSell_price();
			}
			BigDecimal   c   =   new   BigDecimal(totalPrice);
			dzongjia = c.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
			mPriceAll.setText("��" + dzongjia + "");
			if (mSelectState.size() == mListData.size()) {
				mCheckAll.setChecked(true);
			} else {
				mCheckAll.setChecked(false);
			}

		}

	}

	class ViewHolder {
		CheckBox checkBox;

		ImageView image;
		TextView shopName;
		TextView content,tv_size;
		TextView carNum;
		TextView price;
		TextView add;
		TextView red;
		Button button; // ����ִ��ɾ����button
		View frontView;
		LinearLayout item_right, item_left;

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.subtitle:
			isBatchModel = !isBatchModel;
			if (isBatchModel) {
				mEdit.setText(getResources().getString(R.string.menu_enter));
				mDelete.setText(getResources().getString(R.string.menu_del));
				mBottonLayout.setVisibility(View.VISIBLE);
				mFavorite.setVisibility(View.GONE);
				BigDecimal   c   =   new   BigDecimal(totalPrice);
				dzongjia = c.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
				mPriceAll.setText("��" + dzongjia + "");
				System.out.println("totalPrice1-------------"+totalPrice);
				mDelete.setText(getResources().getString(R.string.menu_del)+"(" + list_size.size() + ")");
				zhuangtai = false;
				
			} else {
				mEdit.setText(getResources().getString(R.string.menu_edit));

				mFavorite.setVisibility(View.GONE);
				mBottonLayout.setVisibility(View.VISIBLE);
				mDelete.setText(getResources().getString(R.string.menu_sett));
//				totalPrice=0;
				BigDecimal   c   =   new   BigDecimal(totalPrice);
				dzongjia = c.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
				mPriceAll.setText("��" + dzongjia + "");
				System.out.println("totalPrice2-------------"+totalPrice);
				mDelete.setText(getResources().getString(R.string.menu_sett)+"(" + list_size.size() + ")");
				zhuangtai = true;
			}

			break;

		case R.id.check_box_all:
			totalPrice = 0;
			if (mCheckAll.isChecked()) {
				list_size.clear();
				for (int i = 0; i < mListData.size(); i++) {
					mListData.get(i).setChoose(true);
					// ���Ϊѡ��
					if (mListData.get(i).isChoose()) {
						totalPrice = totalPrice + mListData.get(i).getQuantity()* mListData.get(i).getSell_price();
						list_size.add(num);
						if (zhuangtai == true) {
							mDelete.setText(getResources().getString(R.string.menu_sett)+"(" + list_size.size() + ")");
						}else {
							mDelete.setText(getResources().getString(R.string.menu_del)+"(" + list_size.size() + ")");
						}
					}
				}

				// ˢ��
				mListAdapter.notifyDataSetChanged();
				// ��ʾ
				BigDecimal   c   =   new   BigDecimal(totalPrice);
				dzongjia = c.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
				mPriceAll.setText("��" + dzongjia + "");
			} else {
				for (int i = 0; i < mListData.size(); i++) {
					mListData.get(i).setChoose(false);
					list_size.clear();
					if (zhuangtai == true) {
						mDelete.setText(getResources().getString(R.string.menu_sett)+"(" + list_size.size() + ")");
					}else {
						mDelete.setText(getResources().getString(R.string.menu_del)+"(" + list_size.size() + ")");
					}
					// // ˢ��
					mListAdapter.notifyDataSetChanged();
				}
				BigDecimal   c   =   new   BigDecimal(totalPrice);
				dzongjia = c.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
				mPriceAll.setText("��" + dzongjia + "");
			}
			break;

		case R.id.tv_cart_buy_or_del:
			System.out.println("isBatchModel-------------"+isBatchModel);
			if (isBatchModel) {
			
//				Iterator it = mListData.iterator();
//				while (it.hasNext()) {
//					// �õ���Ӧ����Ԫ��
//					DataBean_ll g = (DataBean_ll) it.next();
//					// �ж�
//					if (g.isChoose()) {
//						// �Ӽ�����ɾ����һ��next�������ص�Ԫ��
//						it.remove();
//					}
//				}
				
				 String str1="";
				for(int i=0;i<mListData.size();i++){
					if (mListData.get(i).isChoose()) {
						String fegefu = str1.length()>0?",":"";
						str1 = str1+fegefu+String.valueOf(mListData.get(i).getArticle_id());
					}
				}
				System.out.println("mListData.size()==========1================"+mListData.size());
				if (mListData.size() > 0) {
					mListData.clear();
					loadData();
					
				}
				System.out.println("mListData.size()==========2================"+mListData.size());
				
				String strUrl = RealmName.REALM_NAME_LL + "/cart_goods_delete?"+ "clear=0&user_id=" + user_id+ "&cart_id=" + str1;
				AsyncHttp.get(strUrl,new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						// TODO Auto-generated method stub
						System.out.println("==========================���ʽӿڳɹ���"+arg1);
						super.onSuccess(arg0, arg1);
						try {
							
						JSONObject object = new JSONObject(arg1);
						  String status = object.getString("status");
						    String info = object.getString("info");
						    if (status.equals("y")) {
						    	Toast.makeText(MyXiaDanActivity.this, info, 200).show();
						    	// ˢ��
								mListAdapter.notifyDataSetChanged();
						    }else {
						    	Toast.makeText(MyXiaDanActivity.this, info, 200).show();
							}
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
					}
					
				}, MyXiaDanActivity.this);
				

			} else {
			    String str1="";
			    String str2="";
			    String str3="";
			    System.out.println("totalPrice------1-------"+totalPrice);
			    System.out.println("mListData.size()-------------"+mListData.size());
			    
				if (totalPrice != 0) {
					for(int i=0;i<mListData.size();i++){
						if (mListData.get(i).isChoose()) {
							
//							str1 = "";//�����
//							str2 = "";//�����
//							str3 = "";//�����
							String fegefu = str1.length()>0?",":"";
							str1 = str1+fegefu+String.valueOf(mListData.get(i).getArticle_id());
							str2 = str2+fegefu+String.valueOf(mListData.get(i).getGoods_id());
							str3 = str3+fegefu+String.valueOf(mListData.get(i).getQuantity());
							
							
					        String jsonString2 = JSON.toJSONString(mListData);  
					        System.out.println("mListData---------------"+jsonString2);
					        
							String zhou = mListData.get(i).getArticle_id();
							String zhou_ll = mListData.get(i).getGoods_id();
							String num = "1";
							
							
							 ArrayList<jsondata> users3 = new ArrayList<jsondata>();
						        jsondata jsondata10 = new jsondata();
						        jsondata10.setArticle_id(mListData.get(i).getArticle_id());
						        jsondata10.setGoods_id(mListData.get(i).getGoods_id());
						        jsondata10.setQuantity(mListData.get(i).getQuantity());
						        users3.add(jsondata10);
						        
							
						        jsondata_zhi = JSON.toJSONString(users3);  
					        System.out.println("jsondata_zhi---------------"+jsondata_zhi);
							
						}
					}
					
					
					System.out.println("str1-------------"+str1);
					String zhou = str1+"/"+str2+"/"+str3;
					System.out.println("zhou-------------"+zhou);
					
					 if (str1.equals("")) {
						 Toast.makeText(MyXiaDanActivity.this, "��ѡ��Ҫ֧������Ʒ", 200).show();
//							mListAdapter.notifyDataSetChanged();
					 }else {
						    System.out.println("totalPrice-------2------"+totalPrice);
							Intent Intent2 = new Intent(MyXiaDanActivity.this,HaomaActivity.class);
							Intent2.putExtra("jsondata_zhi", jsondata_zhi);
							startActivity(Intent2);
						 
					 }
				
				} else {
					 Toast.makeText(MyXiaDanActivity.this, "��ѡ��Ҫ֧������Ʒ������", 200).show();
					mListAdapter.notifyDataSetChanged();
					return;
				}
			}

			break;
		case R.id.back:
//			onBackPressed();
			break;
		default:
			break;
		}
	}

	/**
	 * ����۸�
	 */
	public void count() {

		totalPrice = 0;// �����
		if (mListData != null && mListData.size() > 0) {
			for (int i = 0; i < mListData.size(); i++) {
				if (mListData.get(i).isChoose()) {

					totalPrice = totalPrice + mListData.get(i).getQuantity()
							* mListData.get(i).getSell_price();

				}
			}
			BigDecimal   c   =   new   BigDecimal(totalPrice);
			dzongjia = c.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
			mPriceAll.setText("��" + dzongjia + "");
		}

	}
	
	
	

	public void select() {
		int count = 0;
		for (int i = 0; i < mListData.size(); i++) {
			if (mListData.get(i).isChoose()) {
				count++;
			}
		}
		if (count == mListData.size()) {
			mCheckAll.setChecked(true);
		} else {
			isSelect = true;
			mCheckAll.setChecked(false);
		}

	}

}