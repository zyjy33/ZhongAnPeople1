package com.hengyushop.demo.home;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.hengyu.web.Constant;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.ctrip.openapi.java.utils.BitUtil;
import com.ctrip.openapi.java.utils.GetImgUtil;
import com.ctrip.openapi.java.utils.LogoConfig;
import com.ctrip.openapi.java.utils.Util;
import com.example.taobaohead.headview.RoundImageView;
import com.example.uploadpicdemo.Utils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.demo.wec.MyGridView;
import com.hengyushop.entity.GuigeBean;
import com.hengyushop.entity.GuigeData;
import com.hengyushop.entity.GuigellBean;
import com.hengyushop.entity.MyAssetsBean;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.zams.www.R;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 *
 * 面对面分享
 *
 * @author Administrator
 *
 */
@SuppressWarnings("ResourceType")
public class MainDuiMianPromoteActivity extends BaseActivity implements
		OnClickListener {
	private ImageView iv_fanhui, cursor1, cursor2, cursor3, cursor4;
	private ImageView iv_qr_image1, iv_qr_image2, iv_touxiang;
	private LinearLayout index_item0, index_item1, ll_buju1, index_item3;
	private SharedPreferences spPreferences;
	private TextView tv_ticket, tv_shop_ticket, tv_jifen_ticket;
	private ArrayList<GuigeBean> list;
	private ArrayList<MyAssetsBean> list_lb;
	String user_name, user_id;
	int len;
	String fund_id = "0";
	private MyGridView gridView2;
	private ListView new_list;
	GuigeData md;
	GuigeBean mb;
	GuigellBean mbll;
	GuigeBean data_ll;
	MyAssetsBean data;
	public static Handler handler;
	ArrayList<GuigeBean> list_l;
	private static final int THUMB_SIZE = 100;
	private DialogProgress progress;
	String erweima = "";
	RoundImageView networkImage, imv_user_photo;
	Bitmap bitmap;// 二维码中间图片
	private int iv_halfWidth = 20;// 显示中间图片的宽度的一半
	Bitmap mBitmap;// 二维码图片;
	Bitmap bitmap_tx, bitmap_touxiang;
	String avatar = "";
	String touxiang = "";
	String mdm_sys = "";
	private ImageLoader mImageLoader;
	private ImageView iv_touxiang2;
	private TextView tv_xiabu;
	private Button btn_fenxiang, btn_zhuti;
	Bitmap alterBitmap;
	private IWXAPI api;
	Bitmap bitmap_fx;
	ImageView iv;
	LinearLayout ll_zhuti, ll_user_buju;
	Bitmap bitMap_tx, bitMap_ewm_tx;
	Bitmap bitMap1;
	Bitmap bitMap2;
	ArrayList<GuigeData> list_ll = new ArrayList<GuigeData>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mdm_promote);
		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		progress = new DialogProgress(this);
		user_name = spPreferences.getString("user", "");
		user_id = spPreferences.getString("user_id", "");
		Initialize();
		// String fx_erweimw = getIntent().getStringExtra("fx_erweimw");
		// System.out.println("fx_erweimw========"+fx_erweimw);
		// if (fx_erweimw != null) {
		// getmianduimian();
		// }
		erweima = getIntent().getStringExtra("erweima");
		System.out.println("erweima========" + erweima);

		String zhuti_tp = getIntent().getStringExtra("num");
		System.out.println("zhuti_tp========" + zhuti_tp);
		if (zhuti_tp != null) {
			intren();
			cursor1.setVisibility(View.INVISIBLE);
			cursor2.setVisibility(View.VISIBLE);
			ll_buju1.setVisibility(View.GONE);
			ll_user_buju.setVisibility(View.VISIBLE);
		} else {
			if (!erweima.equals("")) {
				Bitmap bitmap_erweima = BitUtil.stringtoBitmap(erweima);
				System.out.println("1========" + bitmap_erweima);
				iv_qr_image1.setImageBitmap(bitmap_erweima);
			} else {
				System.out.println("2========" + mBitmap);
				// iv_qr_image1.setImageBitmap(mBitmap);
			}
			cursor1.setVisibility(View.VISIBLE);
			cursor2.setVisibility(View.INVISIBLE);
			ll_buju1.setVisibility(View.VISIBLE);
			ll_user_buju.setVisibility(View.GONE);
		}

		handler = new Handler() {
			public void dispatchMessage(Message msg) {
				switch (msg.what) {
					case 1:
						// bitmap_fx.recycle(); //回收图片所占的内存
						finish();
						break;

					default:
						break;
				}
			}
		};

	}

	@Override
	protected void onResume() {

		super.onResume();
	}
	public void onDestroy() {
		super.onDestroy();
		try {
			//			BitmapDrawable bd1 = (BitmapDrawable)ll_zhuti.getBackground();
			//			ll_zhuti.setBackgroundResource(0);//别忘了把背景设为null，避免onDraw刷新背景时候出现used a recycled bitmap错误
			//			bd1.setCallback(null);
			//			bd1.getBitmap().recycle();
		} catch (Exception e) {

			e.printStackTrace();
		}
	};
	/**
	 * 控件初始化
	 */
	private void Initialize() {

		avatar = spPreferences.getString("avatar", "");
		System.out.println("avatar=============" + avatar);
		// if (!avatar.equals("")) {
		// new Thread(getPicByUrl).start();
		// }
		try {
			// tv_djjifen_ticket = (TextView)
			// findViewById(R.id.tv_djjifen_ticket);
			index_item0 = (LinearLayout) findViewById(R.id.index_item0);
			index_item1 = (LinearLayout) findViewById(R.id.index_item1);
			ll_buju1 = (LinearLayout) findViewById(R.id.ll_buju1);
			cursor1 = (ImageView) findViewById(R.id.cursor1);
			cursor2 = (ImageView) findViewById(R.id.cursor2);
			index_item0.setOnClickListener(this);
			index_item1.setOnClickListener(this);
			iv_qr_image1 = (ImageView) findViewById(R.id.iv_qr_image1);

			btn_fenxiang = (Button) findViewById(R.id.btn_fenxiang);
			btn_zhuti = (Button) findViewById(R.id.btn_zhuti);
			iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
			iv_fanhui.setOnClickListener(this);
			ll_user_buju = (LinearLayout) findViewById(R.id.ll_user_buju);
			ll_user_buju.setVisibility(View.VISIBLE);
			iv_touxiang = (ImageView) findViewById(R.id.iv_touxiang);
			networkImage = (RoundImageView) findViewById(R.id.roundImage_network);
			// iv_touxiang2 = (ImageView) findViewById(R.id.iv_touxiang2);
			iv_qr_image2 = (ImageView) findViewById(R.id.iv_qr_image2);
			btn_fenxiang.setOnClickListener(this);
			btn_zhuti.setOnClickListener(this);
			ll_zhuti = (LinearLayout) findViewById(R.id.ll_zhuti);

			ImageView iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
			iv_fanhui.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {

					finish();
				}
			});

			ll_buju1.setVisibility(View.VISIBLE);
			ll_user_buju.setVisibility(View.GONE);

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
			case R.id.index_item0:
				cursor1.setVisibility(View.VISIBLE);
				cursor2.setVisibility(View.INVISIBLE);
				ll_buju1.setVisibility(View.VISIBLE);
				ll_user_buju.setVisibility(View.GONE);
				erweima = getIntent().getStringExtra("erweima");
				System.out.println("erweima========" + erweima);
				if (!erweima.equals("")) {
					Bitmap bitmap_erweima = BitUtil.stringtoBitmap(erweima);
					System.out.println("1========" + bitmap_erweima);
					iv_qr_image1.setImageBitmap(bitmap_erweima);
				} else {
					System.out.println("2========" + mBitmap);
					// iv_qr_image1.setImageBitmap(mBitmap);
				}
				break;
			case R.id.index_item1:
				try {

					cursor1.setVisibility(View.INVISIBLE);
					cursor2.setVisibility(View.VISIBLE);
					ll_buju1.setVisibility(View.GONE);
					ll_user_buju.setVisibility(View.VISIBLE);
					// Toast.makeText(MainDuiMianPromoteActivity.this, "暂无日常调理",
					// 200).show();
					intren();
				} catch (Exception e) {

					e.printStackTrace();
				}
				break;
			case R.id.btn_fenxiang:
				softshareWxChat();
				break;
			case R.id.btn_zhuti:
				try {
					Intent intent = new Intent(MainDuiMianPromoteActivity.this,
							MianDuiMianGhztActivity.class);
					intent.putExtra("erweima", erweima);
					intent.putExtra("mdm_sys", mdm_sys);
					startActivity(intent);
				} catch (Exception e) {

					e.printStackTrace();
				}
				break;
			default:
				break;
		}
	}

	public void intren() {
		try {
			// btn_fenxiang = (Button) findViewById(R.id.btn_fenxiang);
			// btn_zhuti = (Button) findViewById(R.id.btn_zhuti);
			// iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
			// iv_fanhui.setOnClickListener(this);
			// ll_user_buju = (LinearLayout) findViewById(R.id.ll_user_buju);
			// ll_user_buju.setVisibility(View.VISIBLE);
			// iv_touxiang = (ImageView) findViewById(R.id.iv_touxiang);
			// networkImage =
			// (RoundImageView)findViewById(R.id.roundImage_network);
			// // iv_touxiang2 = (ImageView) findViewById(R.id.iv_touxiang2);
			// iv_qr_image2 = (ImageView) findViewById(R.id.iv_qr_image2);
			// btn_fenxiang.setOnClickListener(this);
			// btn_zhuti.setOnClickListener(this);
			// ll_zhuti = (LinearLayout) findViewById(R.id.ll_zhuti);

			String zhuti_tp = getIntent().getStringExtra("num");
			System.out.println("zhuti_tp========" + zhuti_tp);
			if (zhuti_tp != null) {
				if (zhuti_tp.equals("1")) {

					ll_zhuti.setBackgroundResource(R.drawable.ysj_hb1);
					//					Bitmap bm1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.ysj_hb1);
					//					BitmapDrawable bd1 = new BitmapDrawable(this.getResources(), bm1);
					//					ll_zhuti.setBackgroundDrawable(bd1);
					// Toast.makeText(MianDuiMianFxhbActivity.this, "1",200).show();
					//销毁的时候使用
					//					Bitmap bm = BitmapFactory.decodeResource(this.getResources(), R.drawable.ysj_hb1);
					//					BitmapDrawable bd = new BitmapDrawable(this.getResources(), bm);
					//					ll_zhuti.setBackgroundDrawable(bd);
					//					//销毁的时候使用
					//					BitmapDrawable bd1 = (BitmapDrawable)ll_zhuti.getBackground();
					//					ll_zhuti.setBackgroundResource(0);//别忘了把背景设为null，避免onDraw刷新背景时候出现used a recycled bitmap错误
					//					bd1.setCallback(null);
					//					bd1.getBitmap().recycle();
				} else if (zhuti_tp.equals("2")) {
					try {
						ll_zhuti.setBackgroundResource(R.drawable.ysj_hb2);
						//						Bitmap bm1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.ysj_hb2);
						//						BitmapDrawable bd1 = new BitmapDrawable(this.getResources(), bm1);
						//						ll_zhuti.setBackgroundDrawable(bd1);
						// Toast.makeText(MianDuiMianFxhbActivity.this, "2",
						// 200).show();
						//						Bitmap bm = BitmapFactory.decodeResource(this.getResources(), R.drawable.ysj_hb2);
						//						BitmapDrawable bd = new BitmapDrawable(this.getResources(), bm);
						//						ll_zhuti.setBackgroundDrawable(bd);
						//						//销毁的时候使用
						//						BitmapDrawable bd1 = (BitmapDrawable)ll_zhuti.getBackground();
						//						ll_zhuti.setBackgroundResource(0);//别忘了把背景设为null，避免onDraw刷新背景时候出现used a recycled bitmap错误
						//						bd1.setCallback(null);
						//						bd1.getBitmap().recycle();
					} catch (Exception e) {

						e.printStackTrace();
					}
				}
			} else {
				ll_zhuti.setBackgroundResource(R.drawable.ysj_hb1);
				//				Bitmap bm1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.ysj_hb1);
				//				BitmapDrawable bd1 = new BitmapDrawable(this.getResources(), bm1);
				//				ll_zhuti.setBackgroundDrawable(bd1);
			}

			erweima = getIntent().getStringExtra("erweima");
			System.out.println("erweima========" + erweima);
			if (!erweima.equals("")) {
				Bitmap bitmap_erweima = BitUtil.stringtoBitmap(erweima);
				System.out.println("1========" + bitmap_erweima);
				iv_qr_image2.setImageBitmap(bitmap_erweima);
			} else {
				System.out.println("2========" + mBitmap);
				iv_qr_image2.setImageBitmap(mBitmap);
			}

			avatar = spPreferences.getString("avatar", "");
			System.out.println("avatar========" + avatar);
			if (!avatar.equals("")) {
				new Thread(getPicByUrl).start();
				mImageLoader = initImageLoader(MainDuiMianPromoteActivity.this,
						mImageLoader, "test");
				if (!avatar.equals("")) {
					mImageLoader.displayImage(
							RealmName.REALM_NAME_FTP + avatar, networkImage);
				}
			} else {

			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	private void getmianduimian() {

		try {

			mdm_sys = getIntent().getStringExtra("mdm_sys");
			System.out.println("mdm_sys=================" + mdm_sys);
			bitmap_touxiang = BitUtil.stringtoBitmap(mdm_sys);
			// bitmap_touxiang = GetImgUtil.getImage(mdm_sys);
			System.out.println("avatar=============" + avatar);
			// System.out.println("mdm_sys============="+mdm_sys);
			System.out
					.println("bitmap_touxiang=============" + bitmap_touxiang);
			if (avatar.contains("http")) {
				bitmap_tx = BitmapFactory.decodeResource(getResources(),
						R.drawable.app_zams);
			} else if (!avatar.equals("")) {
				bitmap_tx = bitmap_touxiang;
			} else {
				bitmap_tx = BitmapFactory.decodeResource(getResources(),
						R.drawable.app_zams);
			}

			System.out.println("bitmap_tx=============" + bitmap_tx);
			LogoConfig logoConfig = new LogoConfig();
			bitmap = logoConfig.modifyLogo(BitmapFactory.decodeResource(
					getResources(), R.drawable.white_bg), bitmap_tx);
			// 缩放图片，用到矩阵去做
			Matrix matrix = new Matrix();
			float sx = (float) 2 * iv_halfWidth / bitmap.getWidth();
			float sy = (float) 2 * iv_halfWidth / bitmap.getHeight();
			matrix.setScale(sx, sy);
			System.out.println("bitmap=============" + bitmap);
			// 生成缩放后的图片
			bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
					bitmap.getHeight(), matrix, false);

			String content = getIntent().getStringExtra("erweima");
			System.out.println("content=============" + content);
			try {
				mBitmap = createBitmap(new String(content.getBytes(),
						"ISO-8859-1"));
				erweima = BitUtil.bitmaptoString(mBitmap);
				System.out.println("mBitmap=============" + mBitmap);
				iv_qr_image1.setImageBitmap(mBitmap);
			} catch (WriterException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			// btn_fenxiang = (Button) findViewById(R.id.btn_fenxiang);
			// btn_zhuti = (Button) findViewById(R.id.btn_zhuti);
			// iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
			// iv_fanhui.setOnClickListener(this);
			// ll_user_buju = (LinearLayout) findViewById(R.id.ll_user_buju);
			// ll_user_buju.setVisibility(View.VISIBLE);
			// iv_touxiang = (ImageView) findViewById(R.id.iv_touxiang);
			// networkImage =
			// (RoundImageView)findViewById(R.id.roundImage_network);
			// // iv_touxiang2 = (ImageView) findViewById(R.id.iv_touxiang2);
			// iv_qr_image2 = (ImageView) findViewById(R.id.iv_qr_image2);
			// btn_fenxiang.setOnClickListener(this);
			// btn_zhuti.setOnClickListener(this);
			// ll_zhuti = (LinearLayout) findViewById(R.id.ll_zhuti);

			// String zhuti_tp = getIntent().getStringExtra("num");
			// System.out.println("zhuti_tp========"+zhuti_tp);
			// if (zhuti_tp != null) {
			// if (zhuti_tp.equals("1")) {
			//
			// ll_zhuti.setBackgroundResource(R.drawable.ysj_hb1);
			// // Toast.makeText(MianDuiMianFxhbActivity.this, "1", 200).show();
			// }else if (zhuti_tp.equals("2")){
			// try {
			// ll_zhuti.setBackgroundResource(R.drawable.ysj_hb2);
			// // Toast.makeText(MianDuiMianFxhbActivity.this, "2", 200).show();
			// } catch (Exception e) {
			//
			// e.printStackTrace();
			// }
			// }
			// }else {
			// ll_zhuti.setBackgroundResource(R.drawable.ysj_hb1);
			// }

			System.out.println("erweima========" + erweima);
			if (erweima.equals("")) {
				Bitmap bitmap_erweima = BitUtil.stringtoBitmap(erweima);
				System.out.println("1========" + bitmap_erweima);
				iv_qr_image2.setImageBitmap(bitmap_erweima);
			} else {
				System.out.println("2========" + mBitmap);
				iv_qr_image2.setImageBitmap(mBitmap);
			}

			avatar = spPreferences.getString("avatar", "");
			System.out.println("avatar========" + avatar);
			if (!avatar.equals("")) {
				new Thread(getPicByUrl).start();
				mImageLoader = initImageLoader(MainDuiMianPromoteActivity.this,
						mImageLoader, "test");
				if (!avatar.equals("")) {
					mImageLoader.displayImage(
							RealmName.REALM_NAME_FTP + avatar, networkImage);
				}
			} else {
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	Runnable getPicByUrl = new Runnable() {
		@Override
		public void run() {
			try {
				// String img_url2 =
				// "http://183.62.138.31:1010/upload/phone/113875199/20170217164544307.jpg";
				String img_url2 = RealmName.REALM_NAME_HTTP + avatar;
				System.out.println("img_url2==============" + img_url2);
				if (!img_url2.equals("")) {
					Bitmap bmp = GetImgUtil.getImage(img_url2);// BitmapFactory：图片工厂！
					bitMap_ewm_tx = bmp;
					bitMap_tx = Utils.toRoundBitmap(bmp, null);// 这个时候的图片已经被处理成圆形的了
					System.out.println("bitMap1==============" + bitMap_tx);
				}
			} catch (Exception e) {
				Log.i("ggggg", e.getMessage());
			}
		}
	};

	// Runnable getPicByUrl = new Runnable() {
	// @Override
	// public void run() {
	// try {
	// String img_url2 = RealmName.REALM_NAME_HTTP +avatar;
	// System.out.println("img_url2=============="+img_url2);
	// bitmap_touxiang = GetImgUtil.getImage(img_url2);// BitmapFactory：图片工厂！
	// Bitmap bitMap_tx = Utils.toRoundBitmap(bitmap_touxiang,null);//
	// 这个时候的图片已经被处理成圆形的了
	// touxiang = BitUtil.bitmaptoString(bitMap_tx);
	// System.out.println("touxiang=============="+touxiang);
	// System.out.println("bitmap_touxiang=============="+bitmap_touxiang);
	// // bitmap_tx = bitmap_touxiang;
	// } catch (Exception e) {
	// Log.i("ggggg", e.getMessage());
	// }
	// }
	// };

	/**
	 * 微信分享图片
	 *
	 * @param
	 */
	private void softshareWxChat() {
		try {
			// if (bitMap_tx != null) {
			// 图片合成-画布 先去画A 再去画B
			String zhuti_tp = getIntent().getStringExtra("num");
			System.out.println("zhuti_tp========" + zhuti_tp);
			System.out.println("bitmap_fx0==============" + bitmap_fx);
			if (zhuti_tp != null) {
				// bitmap_fx.recycle(); //回收图片所占的内存
				if (zhuti_tp.equals("1")) {
					// String fx_tp2 = spPreferences.getString("fx_tp2", "");
					// bitmap_fx = BitUtil.stringtoBitmap(fx_tp2);
					// bitmap_fx =
					// BitmapFactory.decodeResource(getResources(),R.drawable.ysj_haibao1);
					// // bitmap为只读的
					InputStream is = this.getResources().openRawResource(R.drawable.ysj_hb1);
					BitmapFactory.Options options = new BitmapFactory.Options();
					options.inJustDecodeBounds = false;
					options.inSampleSize = 1; // width，hight设为原来的十分一
					bitmap_fx = BitmapFactory.decodeStream(is, null, options);
					zhou();
				} else if (zhuti_tp.equals("2")) {
					try {
						// bitmap_fx =
						// BitmapFactory.decodeResource(getResources(),R.drawable.ysj_haibao2);
						// // bitmap为只读的
						InputStream is = this.getResources().openRawResource(R.drawable.ysj_hb2);
						BitmapFactory.Options options = new BitmapFactory.Options();
						options.inJustDecodeBounds = false;
						options.inSampleSize = 2; // width，hight设为原来的十分一
						bitmap_fx = BitmapFactory.decodeStream(is, null,
								options);
						zhoull();
					} catch (Exception e) {

						e.printStackTrace();
					}
				}
			} else {
				// bitmap_fx =
				// BitmapFactory.decodeResource(getResources(),R.drawable.ysj_haibao1);
				// // bitmap为只读的
				// yuanban();
				InputStream is = this.getResources().openRawResource(
						R.drawable.ysj_hb1);
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inJustDecodeBounds = false;
				options.inSampleSize = 1; // width，hight设为原来的十分一
				bitmap_fx = BitmapFactory.decodeStream(is, null, options);
				zhou();
			}

			System.out.println("bitmap_fx1==============" + bitmap_fx);

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	private String buildTransaction(final String type) {
		return (type == null) ? String.valueOf(System.currentTimeMillis())
				: type + System.currentTimeMillis();
	}

	private void zhou() {

		System.out.println("bitmap_fx1==============" + bitmap_fx);

		alterBitmap = Bitmap.createBitmap(bitmap_fx.getWidth(),
				bitmap_fx.getHeight(), bitmap_fx.getConfig());
		Canvas canvas = new Canvas(alterBitmap);
		Paint paint = new Paint();
		paint.setColor(Color.BLACK);
		canvas.drawBitmap(bitmap_fx, new Matrix(), paint);
		System.out.println("bitmap_fx2==============" + bitmap_fx);
		bitmap_fx.recycle(); // 回收图片所占的内存

		// 头像
		// String touxiang = getIntent().getStringExtra("touxiang");
		// System.out.println("touxiang=============="+touxiang);
		if (bitMap_tx == null) {
			bitMap2 = BitmapFactory.decodeResource(getResources(),
					R.drawable.app_zams);
		} else {
			// String touxiang1 = getIntent().getStringExtra("touxiang");
			// System.out.println("touxiang=============="+touxiang1);
			// Bitmap bitmap_tx = BitUtil.stringtoBitmap(touxiang1);
			// System.out.println("bitmap_tx=============="+bitmap_tx);
			bitMap2 = bitMap_tx;
		}
		System.out.println("bitMap2==============" + bitMap2);
		if (bitMap2 != null) {
			try {
				// bitMap2 =
				// BitmapFactory.decodeResource(getResources(),R.drawable.ysj_tx);
				int width2 = bitMap2.getWidth();
				int height2 = bitMap2.getHeight();
				// 设置想要的大小
				int newWidth2 = 180;
				int newHeight2 = 180;
				// 计算缩放比例
				float scaleWidth2 = ((float) newWidth2) / width2;
				float scaleHeight2 = ((float) newHeight2) / height2;
				// 取得想要缩放的matrix参数
				Matrix matrix2 = new Matrix();
				matrix2.postScale(scaleWidth2, scaleHeight2);
				// 得到新的图片
				bitMap2 = Bitmap.createBitmap(bitMap2, 0, 0, width2, height2,
						matrix2, true);
				canvas.drawBitmap(bitMap2, 290, 180, null);
			} catch (Exception e) {

				e.printStackTrace();
			}
		} else {

		}

		// 二维码
		// erweima = getIntent().getStringExtra("erweima");
		System.out.println("erweima=================================="
				+ erweima);
		Bitmap bitMap = BitUtil.stringtoBitmap(erweima);
		int width = bitMap.getWidth();
		int height = bitMap.getHeight();
		// // 设置想要的大小
		int newWidth = 270;
		int newHeight = 270;
		// 计算缩放比例
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// 取得想要缩放的matrix参数
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		// 得到新的图片
		bitMap = Bitmap.createBitmap(bitMap, 0, 0, width, height, matrix, true);
		canvas.drawBitmap(bitMap, 240, 935, null);

		System.out.println("bitMap_tx==============" + bitMap_tx);

		// iv.setImageBitmap(alterBitmap);
		System.out.println("alterBitmap==============" + alterBitmap);
		// ll_user_buju.setVisibility(View.GONE);
		api = WXAPIFactory.createWXAPI(MainDuiMianPromoteActivity.this,
				Constant.APP_ID, false);
		api.registerApp(Constant.APP_ID);
		WXImageObject imgObj1 = new WXImageObject(alterBitmap);
		WXMediaMessage msg = new WXMediaMessage();
		msg.mediaObject = imgObj1;

		Bitmap thumbBmp = Bitmap.createScaledBitmap(alterBitmap, THUMB_SIZE,
				THUMB_SIZE, true);
		alterBitmap.recycle();
		msg.thumbData = Util.bmpToByteArray(thumbBmp, true); // 设置缩略图

		SendMessageToWX.Req req = new SendMessageToWX.Req();

		req.transaction = buildTransaction("webpage");
		req.message = msg;
		req.scene = SendMessageToWX.Req.WXSceneSession;
		boolean flag = api.sendReq(req);

		System.out.println("微信注册" + flag);

	}

	private void zhoull() {

		System.out.println("bitmap_fx1==============" + bitmap_fx);

		alterBitmap = Bitmap.createBitmap(bitmap_fx.getWidth(),
				bitmap_fx.getHeight(), bitmap_fx.getConfig());
		Canvas canvas = new Canvas(alterBitmap);
		Paint paint = new Paint();
		paint.setColor(Color.BLACK);
		canvas.drawBitmap(bitmap_fx, new Matrix(), paint);
		System.out.println("bitmap_fx2==============" + bitmap_fx);
		bitmap_fx.recycle(); // 回收图片所占的内存

		// 头像
		if (bitMap_tx == null) {
			bitMap2 = BitmapFactory.decodeResource(getResources(),
					R.drawable.app_zams);
		} else {
			bitMap2 = bitMap_tx;
		}
		System.out.println("bitMap2==============" + bitMap2);
		if (bitMap2 != null) {
			try {
				int width2 = bitMap2.getWidth();
				int height2 = bitMap2.getHeight();
				// 设置想要的大小
				int newWidth2 = 140;
				int newHeight2 = 140;
				// 计算缩放比例
				float scaleWidth2 = ((float) newWidth2) / width2;
				float scaleHeight2 = ((float) newHeight2) / height2;
				// 取得想要缩放的matrix参数
				Matrix matrix2 = new Matrix();
				matrix2.postScale(scaleWidth2, scaleHeight2);
				// 得到新的图片
				bitMap2 = Bitmap.createBitmap(bitMap2, 0, 0, width2, height2,
						matrix2, true);
				canvas.drawBitmap(bitMap2, 200, 120, null);
			} catch (Exception e) {

				e.printStackTrace();
			}
		} else {

		}

		// 二维码
		// erweima = getIntent().getStringExtra("erweima");
		Bitmap bitMap = BitUtil.stringtoBitmap(erweima);
		int width = bitMap.getWidth();
		int height = bitMap.getHeight();
		// // 设置想要的大小
		int newWidth = 200;
		int newHeight = 200;
		// 计算缩放比例
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// 取得想要缩放的matrix参数
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		// 得到新的图片
		bitMap = Bitmap.createBitmap(bitMap, 0, 0, width, height, matrix, true);
		canvas.drawBitmap(bitMap, 170, 670, null);

		System.out.println("bitMap_tx==============" + bitMap_tx);

		System.out.println("alterBitmap==============" + alterBitmap);
		// ll_user_buju.setVisibility(View.GONE);
		api = WXAPIFactory.createWXAPI(MainDuiMianPromoteActivity.this,
				Constant.APP_ID, false);
		api.registerApp(Constant.APP_ID);
		WXImageObject imgObj1 = new WXImageObject(alterBitmap);
		WXMediaMessage msg = new WXMediaMessage();
		msg.mediaObject = imgObj1;
		// import com.lglottery.www.http.Util;
		Bitmap thumbBmp = Bitmap.createScaledBitmap(alterBitmap, THUMB_SIZE,
				THUMB_SIZE, true);
		alterBitmap.recycle();
		msg.thumbData = Util.bmpToByteArray(thumbBmp, true); // 设置缩略图

		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("webpage");
		req.message = msg;
		req.scene = SendMessageToWX.Req.WXSceneSession;
		boolean flag = api.sendReq(req);

		System.out.println("微信注册" + flag);
	}

	/**
	 * 根据字符串生成二维码
	 *
	 * @param
	 * @return
	 * @throws WriterException
	 */
	private Bitmap createBitmap(String str) throws WriterException {
		// 生成而为矩阵，编码是指定大小，不要生成了图片在进行缩放，这样会导致模糊识别失败，就是扫描失败了。
		BitMatrix mBitMatrix = new MultiFormatWriter().encode(str,
				BarcodeFormat.QR_CODE, 300, 300);// BarcodeFormat.QR_CODE-编码格式
		// 二维矩阵的宽高
		int w = mBitMatrix.getWidth();
		int h = mBitMatrix.getHeight();

		// 头像的宽度
		int halfw = w / 2;
		int halfh = h / 2;
		// 准备画二维码，把二维矩阵转换为一维数组，一直横着画
		int[] pixels = new int[w * h];// 数组长度就是矩阵的面积值
		for (int y = 0; y < h; y++) {
			int outputOffset = y * w;
			for (int x = 0; x < w; x++) {
				// 画一个普通的二维码
				// if (mBitMatrix.get(x, y)) {// 表示二维矩阵有值，对应画一个黑点
				// pixels[outputOffset + x] = 0xff000000;
				// } else {
				// pixels[outputOffset + x] = 0xffffffff;
				// }

				// 画一个有图片的二维码图片
				if (x > (halfw - iv_halfWidth) && x < (halfw + iv_halfWidth)
						&& y > (halfh - iv_halfWidth)
						&& y < (halfh + iv_halfWidth)) {// 中间图片的区域
					pixels[outputOffset + x] = bitmap.getPixel(x - halfw
							+ iv_halfWidth, y - halfh + iv_halfWidth);// 这里画图之后会很明显的显示出来
				} else {
					if (mBitMatrix.get(x, y)) {// 表示二维矩阵有值，对应画一个黑点
						pixels[outputOffset + x] = 0xff000000;
					} else {
						pixels[outputOffset + x] = 0xffffffff;
					}
				}
			}
		}
		Bitmap bitmap = Bitmap.createBitmap(w, h, Config.ARGB_8888);// 创建一个300*300bitmap
		bitmap.setPixels(pixels, 0, w, 0, 0, w, h);// 像素点、起始点、宽度、其起始像素、宽、高
		return bitmap;

	}

	/**
	 * 初始化图片下载器，图片缓存地址<i>("/Android/data/[app_package_name]/cache/dirName")</i>
	 */
	public ImageLoader initImageLoader(Context context,
									   ImageLoader imageLoader, String dirName) {
		imageLoader = ImageLoader.getInstance();
		if (imageLoader.isInited()) {
			// 重新初始化ImageLoader时,需要释放资源.
			imageLoader.destroy();
		}
		imageLoader.init(initImageLoaderConfig(context, dirName));
		return imageLoader;
	}

	/**
	 * 配置图片下载器
	 *
	 * @param dirName
	 *            文件名
	 */
	private ImageLoaderConfiguration initImageLoaderConfig(Context context,
														   String dirName) {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)
				.threadPoolSize(3).memoryCacheSize(getMemoryCacheSize(context))
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.discCache(new UnlimitedDiscCache(new File(dirName)))
				.tasksProcessingOrder(QueueProcessingType.LIFO).build();
		return config;
	}

	private int getMemoryCacheSize(Context context) {
		int memoryCacheSize;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
			int memClass = ((ActivityManager) context
					.getSystemService(Context.ACTIVITY_SERVICE))
					.getMemoryClass();
			memoryCacheSize = (memClass / 8) * 1024 * 1024; // 1/8 of app memory
			// limit
		} else {
			memoryCacheSize = 2 * 1024 * 1024;
		}
		return memoryCacheSize;
	}

}
