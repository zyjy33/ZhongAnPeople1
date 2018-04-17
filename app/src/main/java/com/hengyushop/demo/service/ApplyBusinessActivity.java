package com.hengyushop.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.android.hengyu.web.RealmName;
import com.androidquery.AQuery;
import com.ctrip.openapi.java.utils.GetImgUtil;
import com.hengyushop.dao.AdvertDao1;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.zams.www.R;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * �����Ϊ�̼�
 * 
 * @author Administrator
 * 
 */
public class ApplyBusinessActivity extends BaseActivity implements
		OnClickListener, OnPageChangeListener {

	private ViewPager vp;
	private ImageView iv_fanhui;
	private ViewPagerAdapter vpAdapter;
	private Button btn_settle_accounts;
	public static Handler handlerll;
	ArrayList<AdvertDao1> images;
	List<String> list;
	public static AQuery mAq;
	// String[] pics;
	String ad_url;
	Bitmap bitmap_tx, bitmap_touxiang;
	ImageView iv;
	// ����ͼƬ��Դ
	private static final int[] pics = { R.drawable.sj_sq1 };
	// private static final int[] pics = { R.drawable.sj_sq1, R.drawable.pic1};
	// R.drawable.pic4,R.drawable.pic5
	// ,R.drawable.pic6,R.drawable.pic7,R.drawable.pic8};

	// �ײ�С��ͼƬ

	private ImageView[] dots;

	// ��¼��ǰѡ��λ��

	private int currentIndex;

	private List<View> views;

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// loadguanggao();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_shenqing_shangjia);// activity_shenqing_shangjia
															// activity_shengqing
		mAq = new AQuery(ApplyBusinessActivity.this);
		loadguanggao();

		handlerll = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 2:
					finish();
					break;
				}
			}
		};

		// iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
		// iv_fanhui.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		// // TODO Auto-generated method stub
		// finish();
		// }
		// });

		// iv_fanhui.setOnClickListener(this);
		btn_settle_accounts = (Button) findViewById(R.id.btn_settle_accounts);
		btn_settle_accounts.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				try {
					Intent intent = new Intent(ApplyBusinessActivity.this,
							ApplyBusiness1Activity.class);
					startActivity(intent);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		});

	}

	private void initDots() {

		LinearLayout ss = (LinearLayout) findViewById(R.id.ss);
		ImageView iv_anniu1 = (ImageView) findViewById(R.id.iv_anniu1);
		ImageView iv_anniu2 = (ImageView) findViewById(R.id.iv_anniu2);
		ImageView iv_anniu3 = (ImageView) findViewById(R.id.iv_anniu3);
		ImageView iv_anniu4 = (ImageView) findViewById(R.id.iv_anniu4);
		ImageView iv_anniu5 = (ImageView) findViewById(R.id.iv_anniu5);
		dots = new ImageView[pics.length];

		// ѭ��ȡ��С��ͼƬ

		for (int i = 0; i < pics.length; i++) {

			// if (i == 0) {
			// iv_anniu1.setVisibility(View.VISIBLE);
			// }
			if (i == 1) {
				iv_anniu1.setVisibility(View.VISIBLE);
				iv_anniu2.setVisibility(View.VISIBLE);
			}
			if (i == 2) {
				iv_anniu3.setVisibility(View.VISIBLE);
			}
			if (i == 3) {
				iv_anniu4.setVisibility(View.VISIBLE);
			}
			if (i == 4) {
				iv_anniu5.setVisibility(View.VISIBLE);
			}
			// �õ�һ��LinearLayout�����ÿһ����Ԫ��

			dots[i] = (ImageView) ss.getChildAt(i);

			dots[i].setEnabled(true);// ����Ϊ��ɫ

			dots[i].setOnClickListener(this);

			dots[i].setTag(i);// ����λ��tag������ȡ���뵱ǰλ�ö�Ӧ

		}

		currentIndex = 0;

		dots[currentIndex].setEnabled(false);// ����Ϊ��ɫ����ѡ��״̬

	}

	private void setCurView(int position)

	{

		if (position < 0 || position >= pics.length) {

			return;

		}

		vp.setCurrentItem(position);

	}

	private void setCurDot(int positon)

	{

		if (positon < 0 || positon > pics.length - 1 || currentIndex == positon) {

			return;

		}

		dots[positon].setEnabled(false);

		dots[currentIndex].setEnabled(true);

		currentIndex = positon;

	}

	// ������״̬�ı�ʱ����

	public void onPageScrollStateChanged(int arg0) {

		// TODO Auto-generated method stub

	}

	// ����ǰҳ�汻����ʱ����

	public void onPageScrolled(int arg0, float arg1, int arg2) {

		// TODO Auto-generated method stub

	}

	// ���µ�ҳ�汻ѡ��ʱ����

	public void onPageSelected(int arg0) {

		// ���õײ�С��ѡ��״̬

		setCurDot(arg0);

	}

	public void onClick(View v) {
		int position = (Integer) v.getTag();

		setCurView(position);

		setCurDot(position);

		// switch (v.getId()) {
		// case R.id.iv_fanhui:
		// finish();
		// break;
		//
		// default:
		// break;
		// }
	}

	public class ViewPagerAdapter extends PagerAdapter {

		// �����б�

		private List<View> views;

		public ViewPagerAdapter(List<View> views) {

			this.views = views;

		}

		// ����arg1λ�õĽ���

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {

			((ViewPager) arg0).removeView(views.get(arg1));
		}

		@Override
		public void finishUpdate(View arg0) {

			// TODO Auto-generated method stub

		}

		// ��õ�ǰ������

		@Override
		public int getCount() {

			if (views != null)

			{

				return views.size();

			}

			return 0;

		}

		// ��ʼ��arg1λ�õĽ���

		@Override
		public Object instantiateItem(View arg0, int arg1) {

			((ViewPager) arg0).addView(views.get(arg1), 0);

			return views.get(arg1);

		}

		// �ж��Ƿ��ɶ������ɽ���

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {

			return (arg0 == arg1);

		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {

			// TODO Auto-generated method stub

		}

		@Override
		public Parcelable saveState() {

			// TODO Auto-generated method stub

			return null;

		}

		@Override
		public void startUpdate(View arg0) {

			// TODO Auto-generated method stub

		}

	}

	private void loadguanggao() {
		try {
			list = new ArrayList<String>();
			// ������
			AsyncHttp.get(RealmName.REALM_NAME_LL
					+ "/get_adbanner_list?advert_id=1017",
					new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int arg0, String arg1) {
							super.onSuccess(arg0, arg1);
							System.out
									.println("======���33=============" + arg1);
							try {
								JSONObject object = new JSONObject(arg1);
								String status = object.getString("status");
								if (status.equals("y")) {
									JSONArray array = object
											.getJSONArray("data");
									int len = array.length();
									// ArrayList<AdvertDao1>
									images = new ArrayList<AdvertDao1>();
									for (int i = 0; i < len; i++) {
										AdvertDao1 ada = new AdvertDao1();
										JSONObject json = array
												.getJSONObject(i);
										ada.setId(json.getString("id"));
										ada.setAd_url(json.getString("ad_url"));
										ad_url = ada.getAd_url();
										// ImageLoader
										// imageLoader=ImageLoader.getInstance();
										// imageLoader.displayImage(RealmName.REALM_NAME_HTTP
										// + ad_url, ling_tip);
										// imageLoader.clearMemoryCache();//����ڴ滺��
										images.add(ada);
										list.add(ad_url);
										// new Thread(getPicByUrl2).start();
									}
								} else {
								}
								if (list.size() > 0) {
									getzhou();
								}

							} catch (JSONException e) {
								e.printStackTrace();
							}
						}

						@Override
						public void onFailure(Throwable arg0, String arg1) {
							// TODO Auto-generated method stub
							super.onFailure(arg0, arg1);
							System.out.println("======���1=============" + arg0);
							System.out.println("======���2=============" + arg1);
						}

					}, null);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private void getzhou() {
		// TODO Auto-generated method stub
		views = new ArrayList<View>();

		LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		try {

			// ��ArrayList<Integer>ת����int[]����
			ArrayList<Integer> tmpList1 = new ArrayList<Integer>();// ��������У���Ϊ�����಻���ܻ������ͣ�ֻ���ܶ��������
			int num = 2;
			tmpList1.add(new Integer(num));
			tmpList1.add(new Integer(2));
			tmpList1.add(new Integer(num + 1));

			// ��������
			// String tmpInteger1[] = new String[list.size()];
			// String tmpInt1[] = new String[list.size()];
			// list.toArray(tmpInteger1);
			// // ��ֵ���
			// for (int i = 0; i < tmpInteger1.length; i++) {
			// tmpInt1[i] = tmpInteger1[i];
			// System.out.print("==========================================="+tmpInt1[i]
			// + " ");
			// }

			// List<String> list=new ArrayList<String>();
			// list.add("������");
			// list.add("����");
			// list.add("����");
			// http://ju918.com/upload/201706/06/201706061608344247.jpg
			// list.add("http://ju918.com/upload/201706/06/201706061608344247.jpg");
			// list.add("http://ju918.com/upload/201706/06/201706061608344247.jpg");
			// list.add("http://ju918.com/upload/201706/06/201706061608344247.jpg");

			// System.out.println("list.size()-------------------"+list.size());
			// int size=list.size();
			// pics = (String[])list.toArray(new String[size]);
			// for(int i=0;i<pics.length;i++){
			// System.out.println("-------------------"+pics[i]);
			// }

			// ��ʼ������ͼƬ�б�

			for (int i = 0; i < pics.length; i++) {

				ImageView iv = new ImageView(this);

				iv.setLayoutParams(mParams);
				iv.setImageResource(pics[i]);
				// Bitmap bitmap_dihua =
				// BitmapFactory.decodeResource(getResources(),
				// R.drawable.pic1);
				// iv.setImageBitmap(bitmap_touxiang);
				// iv.setBackgroundResource(R.drawable.sj_sq1);
				// ImageLoader imageLoader=ImageLoader.getInstance();
				// imageLoader.displayImage(RealmName.REALM_NAME_HTTP + pics[i],
				// iv);
				// imageLoader.clearMemoryCache();//����ڴ滺��
				// mAq.id(iv).image(RealmName.REALM_NAME_HTTP + pics[i]);
				views.add(iv);

			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		vp = (ViewPager) findViewById(R.id.viewpager);

		// LinearLayout layout = (LinearLayout) findViewById(R.id.ll_buju);
		// DisplayMetrics dm = new DisplayMetrics();
		// getWindowManager().getDefaultDisplay().getMetrics(dm);
		// LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)
		// layout.getLayoutParams();
		// params.height = dm.heightPixels/2;
		// params.width = dm.widthPixels;
		// layout.setLayoutParams(params);

		vpAdapter = new ViewPagerAdapter(views);

		vp.setAdapter(vpAdapter);

		// �󶨻ص�

		vp.setOnPageChangeListener(this);

		// ��ʼ���ײ�С��

		initDots();
	}

	// Runnable getPicByUrl2 = new Runnable() {
	// @Override
	// public void run() {
	// try {
	// String img_url2 = RealmName.REALM_NAME_HTTP +ad_url;
	// System.out.println("img_url2=============="+img_url2);
	// bitmap_touxiang = GetImgUtil.getImage(img_url2);// BitmapFactory��ͼƬ������
	// // Bitmap bitMap_tx = Utils.toRoundBitmap(bitmap_touxiang,null);//
	// ���ʱ���ͼƬ�Ѿ��������Բ�ε���
	// // touxiang = BitUtil.bitmaptoString(bitMap_tx);
	// System.out.println("bitmap_touxiang=============="+bitmap_touxiang);
	// if (list.size() > 0) {
	// getzhou();
	// }
	// // iv.setImageBitmap(bitmap_touxiang);
	// } catch (Exception e) {
	// Log.i("ggggg", e.getMessage());
	// }
	// }
	// };
}
