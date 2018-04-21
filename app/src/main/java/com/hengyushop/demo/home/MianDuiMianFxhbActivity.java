package com.hengyushop.demo.home;

import java.io.File;
import java.io.InputStream;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
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
import android.widget.TextView;

import com.android.hengyu.web.Constant;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.ctrip.openapi.java.utils.BitUtil;
import com.ctrip.openapi.java.utils.GetImgUtil;
import com.ctrip.openapi.java.utils.Util;
import com.example.taobaohead.headview.RoundImageView;
import com.example.uploadpicdemo.Utils;
import com.hengyushop.demo.at.BaseActivity;
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

/**
 * 面对面海报分享
 *
 * @author Administrator
 *
 */
public class MianDuiMianFxhbActivity extends BaseActivity implements OnClickListener{
	private ImageView iv_fanhui,iv_qr_image1,iv_touxiang,iv_touxiang2;
	private TextView tv_xiabu;
	private Button btn_fenxiang,btn_zhuti;
	private DialogProgress progress;
	private SharedPreferences spPreferences;
	LinearLayout ll_zhuti,ll_user_buju;
	String erweima;
	String avatar = "";
	public static Handler handler;
	private ImageLoader mImageLoader;
	RoundImageView networkImage,imv_user_photo;
	private IWXAPI api;
	private static final int THUMB_SIZE = 100;
	Bitmap alterBitmap;
	Bitmap bitmap_fx;
	ImageView iv;
	Bitmap bitMap_tx,bitMap_ewm_tx;
	Bitmap bitMap1;
	Bitmap bitMap2;
	private static final String TAG = "ActivityDemo";
	public static  boolean zhuangtai = true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mainduimian_fxhb);
		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		progress = new DialogProgress(MianDuiMianFxhbActivity.this);
		intren();

		handler = new Handler() {
			public void dispatchMessage(Message msg) {
				switch (msg.what) {
					case 0:
						break;
					case 1:
						//					bitmap_fx.recycle();  //回收图片所占的内存
						finish();
						break;
					case 2:
						break;

					default:
						break;
				}
			}
		};
	}

	//	 @Override
	//	    protected void onStart() {
	//	        super.onStart();
	//	        Log.e(TAG, "start onStart~~~");
	//	    }
	//	    @Override
	//	    protected void onRestart() {
	//	        super.onRestart();
	//	        Log.e(TAG, "start onRestart~~~");
	//	    }
	//	    @Override
	//	    protected void onResume() {
	//	        super.onResume();
	//	        Log.e(TAG, "start onResume~~~");
	//	    }
	//	    @Override
	//	    protected void onPause() {
	//	        super.onPause();
	//	        Log.e(TAG, "start onPause~~~");
	//	    }
	//	    @Override
	//	    protected void onStop() {
	//	        super.onStop();
	//	        Log.e(TAG, "start onStop~~~");
	//	    }
	//	    @Override
	//	    protected void onDestroy() {
	//	        super.onDestroy();
	//	        Log.e(TAG, "start onDestroy~~~");
	//	    }

	//	@Override
	//	protected void onNewIntent(Intent intent) {
	//	    super.onNewIntent(intent);
	//
	//	    setIntent(intent);
	//	    api.handleIntent(intent, this);
	//	}

	public void intren() {
		try {

			btn_fenxiang = (Button) findViewById(R.id.btn_fenxiang);
			btn_zhuti = (Button) findViewById(R.id.btn_zhuti);
			iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
			iv = (ImageView) findViewById(R.id.iv);
			iv_fanhui.setOnClickListener(this);
			ll_user_buju = (LinearLayout) findViewById(R.id.ll_user_buju);
			//			iv.setVisibility(View.VISIBLE);
			//			ll_user_buju.setVisibility(View.GONE);

			iv.setVisibility(View.GONE);
			ll_user_buju.setVisibility(View.VISIBLE);
			iv_touxiang = (ImageView) findViewById(R.id.iv_touxiang);
			networkImage = (RoundImageView)findViewById(R.id.roundImage_network);
			//			iv_touxiang2 = (ImageView) findViewById(R.id.iv_touxiang2);
			iv_qr_image1 = (ImageView) findViewById(R.id.iv_qr_image1);
			btn_fenxiang.setOnClickListener(this);
			btn_zhuti.setOnClickListener(this);
			ll_zhuti = (LinearLayout) findViewById(R.id.ll_zhuti);

			String zhuti_tp = getIntent().getStringExtra("num");
			System.out.println("zhuti_tp========"+zhuti_tp);
			if (zhuti_tp != null) {
				if (zhuti_tp.equals("1")) {

					ll_zhuti.setBackgroundResource(R.drawable.zams_haibao1);
					//					Toast.makeText(MianDuiMianFxhbActivity.this, "1", 200).show();
				}else if (zhuti_tp.equals("2")){
					try {
						ll_zhuti.setBackgroundResource(R.drawable.zams_haibao1);
						//					Toast.makeText(MianDuiMianFxhbActivity.this, "2", 200).show();
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
			}else {
				ll_zhuti.setBackgroundResource(R.drawable.zams_haibao1);
			}

			erweima = getIntent().getStringExtra("erweima");
			//			System.out.println("erweima========"+erweima);
			if (erweima != null) {
				Bitmap bitmap_erweima = BitUtil.stringtoBitmap(erweima);
				iv_qr_image1.setImageBitmap(bitmap_erweima);
			}

			avatar = spPreferences.getString("avatar", "");
			System.out.println("avatar========"+avatar);
			if (!avatar.equals("")) {
				new Thread(getPicByUrl).start();
				mImageLoader = initImageLoader(MianDuiMianFxhbActivity.this, mImageLoader, "test");
				if (!avatar.equals("")){
					mImageLoader.displayImage(RealmName.REALM_NAME_FTP +avatar,networkImage);
				}

				//				ImageLoader imageLoader=ImageLoader.getInstance();
				//				imageLoader.displayImage(RealmName.REALM_NAME_HTTP+avatar,iv_touxiang2);
			}else {

			}

			//	        canvas.drawBitmap(ic_luncher, new Matrix(), paint);
			//			softshareWxChat();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
			case R.id.iv_fanhui:
				finish();
				break;
			case R.id.btn_fenxiang:
				softshareWxChat();
				break;
			case R.id.btn_zhuti:
				try {

					//			Intent intent = new Intent(MianDuiMianFxhbActivity.this, MianDuiMianFxhbActivity.class);
					Intent intent = new Intent(MianDuiMianFxhbActivity.this, MianDuiMianGhztActivity.class);
					intent.putExtra("erweima", erweima);
					startActivity(intent);
					//			finish();

				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				break;
			default:
				break;
		}
	}

	//	@Override
	//	protected void onDestroy() {
	//		// TODO Auto-generated method stub
	//		super.onDestroy();
	//	}


	/**
	 * 微信分享图片
	 *
	 * @param text
	 */
	private void softshareWxChat() {
		try {
			//        	 if (bitMap_tx != null) {

			// 图片合成-画布 先去画A 再去画B
			String zhuti_tp = getIntent().getStringExtra("num");
			System.out.println("zhuti_tp========"+zhuti_tp);
			System.out.println("bitmap_fx0=============="+bitmap_fx);
			if (zhuti_tp != null) {
				// 				bitmap_fx.recycle();  //回收图片所占的内存
				if (zhuti_tp.equals("1")) {
					// 					String fx_tp2 = spPreferences.getString("fx_tp2", "");
					// 					bitmap_fx = BitUtil.stringtoBitmap(fx_tp2);
					// 					bitmap_fx = BitmapFactory.decodeResource(getResources(),R.drawable.ysj_haibao1); // bitmap为只读的
					InputStream is = this.getResources().openRawResource(R.drawable.zams_haibao1);
					BitmapFactory.Options options=new BitmapFactory.Options();
					options.inJustDecodeBounds = false;
					options.inSampleSize = 2;   //width，hight设为原来的十分一
					bitmap_fx =BitmapFactory.decodeStream(is,null,options);
					//				     zhou();
					zhoull();
				}else if (zhuti_tp.equals("2")){
					try {
						// 					bitmap_fx = BitmapFactory.decodeResource(getResources(),R.drawable.ysj_haibao2); // bitmap为只读的
						InputStream is = this.getResources().openRawResource(R.drawable.zams_haibao1);
						BitmapFactory.Options options=new BitmapFactory.Options();
						options.inJustDecodeBounds = false;
						options.inSampleSize = 2;   //width，hight设为原来的十分一
						bitmap_fx =BitmapFactory.decodeStream(is,null,options);
						zhoull();
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
			} else {
				// 				bitmap_fx = BitmapFactory.decodeResource(getResources(),R.drawable.ysj_haibao1); // bitmap为只读的
				// 				yuanban();
				InputStream is = this.getResources().openRawResource(R.drawable.zams_haibao1);
				BitmapFactory.Options options=new BitmapFactory.Options();
				options.inJustDecodeBounds = false;
				options.inSampleSize = 2;   //width，hight设为原来的十分一
				bitmap_fx =BitmapFactory.decodeStream(is,null,options);
				//				     zhou();
				zhoull();
			}

			System.out.println("bitmap_fx1=============="+bitmap_fx);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private String buildTransaction(final String type) {
		return (type == null) ? String.valueOf(System.currentTimeMillis())
				: type + System.currentTimeMillis();
	}

	//	private void zhou() {
	//		// TODO Auto-generated method stub
	//		 System.out.println("bitmap_fx1=============="+bitmap_fx);
	//
	//	        alterBitmap = Bitmap.createBitmap(bitmap_fx.getWidth(),bitmap_fx.getHeight(), bitmap_fx.getConfig());
	//	        Canvas canvas = new Canvas(alterBitmap);
	//	        Paint paint = new Paint();
	//	        paint.setColor(Color.BLACK);
	//	        canvas.drawBitmap(bitmap_fx, new Matrix(), paint);
	//	        System.out.println("bitmap_fx2=============="+bitmap_fx);
	//	        bitmap_fx.recycle();  //回收图片所占的内存
	//
	//	       //头像
	////	       String touxiang = getIntent().getStringExtra("touxiang");
	////	       System.out.println("touxiang=============="+touxiang);
	//	       if (bitMap_tx == null) {
	//	    	     bitMap2 = BitmapFactory.decodeResource(getResources(),R.drawable.app_zams);
	//	       }else {
	//	    	    bitMap2 = bitMap_tx;
	//	       }
	//	       System.out.println("bitMap2=============="+bitMap2);
	//	        if (bitMap2 != null) {
	//	    	   try {
	////	        bitMap2 = BitmapFactory.decodeResource(getResources(),R.drawable.ysj_tx);
	//	        int width2 = bitMap2.getWidth();
	//	        int height2 = bitMap2.getHeight();
	//	        // 设置想要的大小
	//	        int newWidth2= 240;
	//	        int newHeight2 = 240;
	//	        // 计算缩放比例
	//	        float scaleWidth2 = ((float) newWidth2) / width2;
	//	        float scaleHeight2 = ((float) newHeight2) / height2;
	//	        // 取得想要缩放的matrix参数
	//	        Matrix matrix2 = new Matrix();
	//	        matrix2.postScale(scaleWidth2, scaleHeight2);
	//	        // 得到新的图片
	//	        bitMap2 = Bitmap.createBitmap(bitMap2, 0, 0, width2, height2, matrix2, true);
	//	        canvas.drawBitmap(bitMap2, 430, 280, null);
	//	    	  } catch (Exception e) {
	//					// TODO: handle exception
	//	    		  e.printStackTrace();
	//			  }
	//	       }else {
	//
	//		   }
	//
	//	        //二维码
	//	        erweima = getIntent().getStringExtra("erweima");
	//			Bitmap bitMap = BitUtil.stringtoBitmap(erweima);
	//	        int width = bitMap.getWidth();
	//	        int height = bitMap.getHeight();
	//// 	        // 设置想要的大小
	// 	        int newWidth = 350;
	// 	        int newHeight = 350;
	//	        // 计算缩放比例
	//	        float scaleWidth = ((float) newWidth) / width;
	//	        float scaleHeight = ((float) newHeight) / height;
	//	        // 取得想要缩放的matrix参数
	//	        Matrix matrix = new Matrix();
	//	        matrix.postScale(scaleWidth, scaleHeight);
	//	        // 得到新的图片
	//	        bitMap = Bitmap.createBitmap(bitMap, 0, 0, width, height, matrix, true);
	//	        canvas.drawBitmap(bitMap, 360, 1280, null);
	//
	//	       System.out.println("bitMap_tx=============="+bitMap_tx);
	//
	//
	//	       iv.setImageBitmap(alterBitmap);
	//	       System.out.println("alterBitmap=============="+alterBitmap);
	//	       iv.setVisibility(View.VISIBLE);
	//		   ll_user_buju.setVisibility(View.GONE);
	//
	////	        api = WXAPIFactory.createWXAPI(MianDuiMianFxhbActivity.this, Constant.APP_ID,false);
	////			api.registerApp(Constant.APP_ID);
	////			WXImageObject imgObj1 = new WXImageObject(alterBitmap);
	////			WXMediaMessage msg = new WXMediaMessage();
	////			msg.mediaObject = imgObj1;
	////
	////			Bitmap thumbBmp = Bitmap.createScaledBitmap(alterBitmap, THUMB_SIZE, THUMB_SIZE, true);
	////			alterBitmap.recycle();
	////			msg.thumbData = Util.bmpToByteArray(thumbBmp, true);  // 设置缩略图
	////
	////			SendMessageToWX.Req req = new SendMessageToWX.Req();
	////			req.transaction = buildTransaction("webpage");
	////			req.message = msg;
	////			req.scene = SendMessageToWX.Req.WXSceneSession;
	////			boolean flag = api.sendReq(req);
	////
	////			System.out.println("微信注册" + flag);
	//
	//	}

	private void zhoull() {
		// TODO Auto-generated method stub
		System.out.println("bitmap_fx1=============="+bitmap_fx);

		alterBitmap = Bitmap.createBitmap(bitmap_fx.getWidth(),bitmap_fx.getHeight(), bitmap_fx.getConfig());
		Canvas canvas = new Canvas(alterBitmap);
		Paint paint = new Paint();
		paint.setColor(Color.BLACK);
		canvas.drawBitmap(bitmap_fx, new Matrix(), paint);
		System.out.println("bitmap_fx2=============="+bitmap_fx);
		bitmap_fx.recycle();  //回收图片所占的内存

		//头像
		if (bitMap_tx == null) {
			bitMap2 = BitmapFactory.decodeResource(getResources(),R.drawable.app_zams);
		}else {
			bitMap2 = bitMap_tx;
		}
		System.out.println("bitMap2=============="+bitMap2);
		if (bitMap2 != null) {
			try {
				int width2 = bitMap2.getWidth();
				int height2 = bitMap2.getHeight();
				// 设置想要的大小
				int newWidth2= 130;
				int newHeight2 = 130;
				// 计算缩放比例
				float scaleWidth2 = ((float) newWidth2) / width2;
				float scaleHeight2 = ((float) newHeight2) / height2;
				// 取得想要缩放的matrix参数
				Matrix matrix2 = new Matrix();
				matrix2.postScale(scaleWidth2, scaleHeight2);
				// 得到新的图片
				bitMap2 = Bitmap.createBitmap(bitMap2, 0, 0, width2, height2, matrix2, true);
				canvas.drawBitmap(bitMap2, 205, 130, null);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}else {

		}

		//二维码
		erweima = getIntent().getStringExtra("erweima");
		Bitmap bitMap = BitUtil.stringtoBitmap(erweima);
		int width = bitMap.getWidth();
		int height = bitMap.getHeight();
		// 	        // 设置想要的大小
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
		canvas.drawBitmap(bitMap, 170, 640, null);

		System.out.println("bitMap_tx=============="+bitMap_tx);


		//	        iv.setImageBitmap(alterBitmap);
		//	        System.out.println("alterBitmap=============="+alterBitmap);
		//	        iv.setVisibility(View.VISIBLE);
		//		    ll_user_buju.setVisibility(View.GONE);

		api = WXAPIFactory.createWXAPI(MianDuiMianFxhbActivity.this, Constant.APP_ID,false);
		api.registerApp(Constant.APP_ID);
		WXImageObject imgObj1 = new WXImageObject(alterBitmap);
		WXMediaMessage msg = new WXMediaMessage();
		msg.mediaObject = imgObj1;
		//import com.lglottery.www.http.Util;
		Bitmap thumbBmp = Bitmap.createScaledBitmap(alterBitmap, THUMB_SIZE, THUMB_SIZE, true);
		alterBitmap.recycle();
		msg.thumbData = Util.bmpToByteArray(thumbBmp, true);  // 设置缩略图

		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("webpage");
		req.message = msg;
		req.scene = SendMessageToWX.Req.WXSceneSession;
		boolean flag = api.sendReq(req);

		System.out.println("微信注册" + flag);
	}

	//	private void yuanban() {
	//		// TODO Auto-generated method stub
	//		 	Bitmap alterBitmap = Bitmap.createBitmap(bitmap_fx.getWidth(),bitmap_fx.getHeight(), bitmap_fx.getConfig());
	//	        Canvas canvas = new Canvas(alterBitmap);
	//	        Paint paint = new Paint();
	//	        paint.setColor(Color.BLACK);
	//	        canvas.drawBitmap(bitmap_fx, new Matrix(), paint);
	//	        System.out.println("bitmap_fx2=============="+bitmap_fx);
	//	        bitmap_fx.recycle();  //回收图片所占的内存
	//
	//	        //二维码
	//	        erweima = getIntent().getStringExtra("erweima");
	//			Bitmap bitMap = BitUtil.stringtoBitmap(erweima);
	//	        int width = bitMap.getWidth();
	//	        int height = bitMap.getHeight();
	//	        // 设置想要的大小
	//	        int newWidth = 610;
	//	        int newHeight = 610;
	//	        // 计算缩放比例
	//	        float scaleWidth = ((float) newWidth) / width;
	//	        float scaleHeight = ((float) newHeight) / height;
	//	        // 取得想要缩放的matrix参数
	//	        Matrix matrix = new Matrix();
	//	        matrix.postScale(scaleWidth, scaleHeight);
	//	        // 得到新的图片
	//	        bitMap = Bitmap.createBitmap(bitMap, 0, 0, width, height, matrix, true);
	//	        canvas.drawBitmap(bitMap, 440, 1830, null);
	//
	//	       System.out.println("bitMap_tx=============="+bitMap_tx);
	//	       System.out.println("avatar2=============="+avatar);
	//
	//
	////	        System.out.println("bitMap2=============="+bitMap2);
	//	       //头像
	//	       String touxiang = getIntent().getStringExtra("touxiang");
	//	       System.out.println("touxiang=============="+touxiang);
	//	       if (touxiang == null) {
	////	    	     InputStream is = this.getResources().openRawResource(R.drawable.ysj_logn);
	////		     BitmapFactory.Options options=new BitmapFactory.Options();
	////		     options.inJustDecodeBounds = false;
	////		     options.inSampleSize = 10;   //width，hight设为原来的十分一
	////		     bitMap2 =BitmapFactory.decodeStream(is,null,options);
	//	    	     bitMap2 = BitmapFactory.decodeResource(getResources(),R.drawable.app_zams);
	//	       }else {
	//	    	   Bitmap bitmap_tx = BitUtil.stringtoBitmap(touxiang);
	//	    	   bitMap2 = bitmap_tx;
	//	       }
	//	       System.out.println("bitMap2=============="+bitMap2);
	//	        if (bitMap2 != null) {
	//	    	   try {
	////	        bitMap2 = BitmapFactory.decodeResource(getResources(),R.drawable.ysj_tx);
	//	        int width2 = bitMap2.getWidth();
	//	        int height2 = bitMap2.getHeight();
	//	        // 设置想要的大小
	//	        int newWidth2= 400;
	//	        int newHeight2 = 400;
	//	        // 计算缩放比例
	//	        float scaleWidth2 = ((float) newWidth2) / width2;
	//	        float scaleHeight2 = ((float) newHeight2) / height2;
	//	        // 取得想要缩放的matrix参数
	//	        Matrix matrix2 = new Matrix();
	//	        matrix2.postScale(scaleWidth2, scaleHeight2);
	//	        // 得到新的图片
	//	        bitMap2 = Bitmap.createBitmap(bitMap2, 0, 0, width2, height2, matrix2, true);
	//	        canvas.drawBitmap(bitMap2, 540, 350, null);
	//	    	  } catch (Exception e) {
	//					// TODO: handle exception
	//	    		  e.printStackTrace();
	//			  }
	//	       }
	//
	//	        iv.setImageBitmap(alterBitmap);
	//
	//	        System.out.println("alterBitmap=============="+alterBitmap);
	////	   Bitmap bmp = BitmapFactory.decodeResource(getResources(),R.drawable.ysj_haibao1);
	//	       iv.setVisibility(View.VISIBLE);
	//	   ll_user_buju.setVisibility(View.GONE);
	//	}
	Runnable getPicByUrl = new Runnable() {
		@Override
		public void run() {
			try {
				//					String img_url2 = "http://183.62.138.31:1010/upload/phone/113875199/20170217164544307.jpg";
				String img_url2 = RealmName.REALM_NAME_HTTP +avatar;
				System.out.println("img_url2=============="+img_url2);
				Bitmap bmp = GetImgUtil.getImage(img_url2);// BitmapFactory：图片工厂！
				bitMap_ewm_tx = bmp;
				bitMap_tx = Utils.toRoundBitmap(bmp,null);// 这个时候的图片已经被处理成圆形的了
				System.out.println("bitMap1=============="+bitMap_tx);
			} catch (Exception e) {
				Log.i("ggggg", e.getMessage());
			}
		}
	};

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
	private ImageLoaderConfiguration initImageLoaderConfig(
			Context context, String dirName) {
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
