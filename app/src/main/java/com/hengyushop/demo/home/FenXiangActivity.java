package com.hengyushop.demo.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.web.Constant;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.ctrip.openapi.java.utils.BitUtil;
import com.ctrip.openapi.java.utils.GetImgUtil;
import com.ctrip.openapi.java.utils.LogoConfig;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.hengyushop.demo.at.BaseActivity;
import com.lglottery.www.http.Util;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.zams.www.R;
import com.zams.www.UserLoginActivity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * 中安民生分享
 *
 * @author Administrator
 *
 */
public class FenXiangActivity extends BaseActivity implements OnClickListener {

	private ImageView iv_fanhui;
	private TextView tv_xiabu;
	private DialogProgress progress;
	private SharedPreferences spPreferences;
	private LinearLayout ll_jufen, ll_mdmtg, ll_fenxiang, ll_fzjf;
	String user_name;
	LayoutInflater mLayoutInflater;
	PopupWindow mPopupWindow;
	protected PopupWindow pop;
	private View view;
	private ImageButton btn_wechat;
	private View btn_sms;
	private View btn_wx_friend;
	private ImageButton img_btn_tencent;
	private IWXAPI api;
	String title, img_url;
	String unionid;
	Bitmap thumb;
	String link_url;
	String mdm_sys = "";
	String erweima = "";
	Bitmap bitmap;// 二维码中间图片
	private int iv_halfWidth = 20;// 显示中间图片的宽度的一半
	Bitmap mBitmap;// 二维码图片;
	Bitmap bitmap_tx, bitmap_touxiang;
	String avatar = "";
	String touxiang = "";
	private ImageView zams_fw_1,zams_fw_2,zams_fw_3,zams_fw_4;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fenxiang);
		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		user_name = spPreferences.getString("user", "");
		progress = new DialogProgress(FenXiangActivity.this);
		avatar = spPreferences.getString("avatar", "");
		System.out.println("avatar=============" + avatar);
		if (!avatar.equals("")) {
			new Thread(getPicByUrl).start();
		}
		if (!avatar.equals("")) {
			new Thread(getPicByUrl2).start();
		}
		intren();
		getzhou();
	}

	private void getzhou() {

		String user_id = spPreferences.getString("user_id", "");
		link_url = RealmName.REALM_NAME_FX + "/appshare/" + user_id + ".html";
	}

	public void intren() {
		try {
			zams_fw_1 = (ImageView) findViewById(R.id.iv_zhuti1);
			zams_fw_2 = (ImageView) findViewById(R.id.iv_zhuti2);
			zams_fw_3 = (ImageView) findViewById(R.id.iv_zhuti3);
			zams_fw_4 = (ImageView) findViewById(R.id.iv_zhuti4);
			//			iv_zhuti1.setBackgroundResource(R.drawable.juyou);
			//			iv_zhuti2.setBackgroundResource(R.drawable.mianduim);
			//			iv_zhuti3.setBackgroundResource(R.drawable.shejiao);
			//			iv_zhuti4.setBackgroundResource(R.drawable.jiqiao);
			Bitmap bm1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.juyou);
			BitmapDrawable bd1 = new BitmapDrawable(this.getResources(), bm1);
			zams_fw_1.setBackgroundDrawable(bd1);
			Bitmap bm2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.mianduim);
			BitmapDrawable bd2 = new BitmapDrawable(this.getResources(), bm2);
			zams_fw_2.setBackgroundDrawable(bd2);
			Bitmap bm3 = BitmapFactory.decodeResource(this.getResources(), R.drawable.shejiao);
			BitmapDrawable bd3 = new BitmapDrawable(this.getResources(), bm3);
			zams_fw_3.setBackgroundDrawable(bd3);
			Bitmap bm4 = BitmapFactory.decodeResource(this.getResources(), R.drawable.jiqiao);
			BitmapDrawable bd4 = new BitmapDrawable(this.getResources(), bm4);
			zams_fw_4.setBackgroundDrawable(bd4);
			ll_jufen = (LinearLayout) findViewById(R.id.ll_jufen);
			ll_mdmtg = (LinearLayout) findViewById(R.id.ll_mdmtg);
			ll_fenxiang = (LinearLayout) findViewById(R.id.ll_fenxiang);
			ll_fzjf = (LinearLayout) findViewById(R.id.ll_fzjf);
			iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
			tv_xiabu = (TextView) findViewById(R.id.tv_xiabu);
			iv_fanhui.setOnClickListener(this);
			ll_mdmtg.setOnClickListener(this);
			ll_fenxiang.setOnClickListener(this);
			ll_jufen.setOnClickListener(this);
			ll_fzjf.setOnClickListener(this);

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	@Override
	public void onClick(View v) {


		switch (v.getId()) {
			case R.id.iv_fanhui:
				finish();
				break;
			case R.id.ll_jufen:
				// Intent intent = new
				// Intent(FenXiangActivity.this,MyJuFenActivity.class);
				Intent intent = new Intent(FenXiangActivity.this,
						MyQuanZiActivity.class);
				startActivity(intent);
				break;
			case R.id.ll_mdmtg:
				// Intent intent1 = new Intent(FenXiangActivity.this,
				// MainDuiMianllActivity.class);
				// startActivity(intent1);
				getmianduimian();
				break;
			case R.id.ll_fenxiang:
				user_name = spPreferences.getString("user", "");
				if (user_name.equals("")) {
					Intent intentll = new Intent(FenXiangActivity.this,
							UserLoginActivity.class);
					startActivity(intentll);
				} else {
					try {
						if (UserLoginActivity.wx_fanhui == false) {
							Intent intent5 = new Intent(FenXiangActivity.this,
									UserLoginActivity.class);
							startActivity(intent5);
						} else {
							SoftWarePopuWindow(ll_mdmtg, FenXiangActivity.this);
						}

					} catch (Exception e) {

						e.printStackTrace();
					}
				}
				break;
			case R.id.ll_fzjf:
				// Intent intent4 = new
				// Intent(FenXiangActivity.this,Webview1.class);
				// intent4.putExtra("web_id", "10332");
				// startActivity(intent4);
				break;

			default:
				break;
		}
	}

	/**
	 * 分享
	 *
	 * @param view2
	 * @param context
	 */
	private void SoftWarePopuWindow(View view2, final Context context) {
		try {
			mLayoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
			// inflater = LayoutInflater.from(context);
			view = mLayoutInflater.inflate(R.layout.ware_infromation_share,
					null);
			pop = new PopupWindow(view, LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT, false);

			pop.setBackgroundDrawable(new BitmapDrawable());
			pop.setOutsideTouchable(true);
			// pop.setFocusable(true);
			// pop.setTouchable(true); // 设置PopupWindow可触摸
			//
			if (!pop.isShowing()) {
				pop.showAtLocation(view2, Gravity.BOTTOM, 0, 0);
			}
			btn_wechat = (ImageButton) view.findViewById(R.id.img_btn_wechat);// 微信
			btn_wx_friend = (ImageButton) view
					.findViewById(R.id.img_btn_wx_friend);// 朋友圈
			btn_sms = (ImageButton) view.findViewById(R.id.img_btn_sms);// 短信
			img_btn_tencent = (ImageButton) view
					.findViewById(R.id.img_btn_tencent);// 腾讯微博

		} catch (Exception e) {

			e.printStackTrace();
		}
		// 微博
		img_btn_tencent.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				pop.dismiss();
				// con(19, 1);
				//				Toast.makeText(FenXiangActivity.this, "抱歉，暂时不支持", 200).show();
				Toast.makeText(FenXiangActivity.this, "功能还未开发，敬请期待", Toast.LENGTH_SHORT).show();
			}
		});

		btn_wechat.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pop.dismiss();
				// progress.CreateProgress();
				con(16, 1);
			}
		});
		btn_wx_friend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				pop.dismiss();
				con(17, 1);
			}
		});

		btn_sms.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				pop.dismiss();
				con(18,0);
				//				Toast.makeText(FenXiangActivity.this, "抱歉，暂时不支持", 200).show();
			}
		});
	}

	private void con(final int index, int type) {
		try {

			String user_name = spPreferences.getString("user", "");
			String user_id = spPreferences.getString("user_id", "");
			String sp_id = getIntent().getStringExtra("sp_id");
			System.out.println("id==========" + user_id);
			// if (sp_id != null) {
			// //
			// http://183.62.138.31:1010/goods/show-6063.html?user_id=4761&user_name=13714758507;
			// String data = "http://183.62.138.31:1011/mobile/goods/show-" +
			// sp_id + ".html?user_id="+user_id+"&user_name="+user_name+"";
			// System.out.println("分享11======================" + data);
			// String zhou = "分享中安民生产品,打开即可浏览中安民生产品" + data;
			// System.out.println("==========" + zhou);
			// softshareWxChat(zhou);
			// }else {
			// http://183.62.138.31:1010/appshare/133.html
			// String data = "http://183.62.138.31:1010/appshare/" + user_id +
			// ".html";
			// System.out.println("分享11======================" + data);
			// String zhou = "云商聚下载地址,下载后可帮分享的好友获得福利" + data;
			// System.out.println("==========" + zhou);
			// softshareWxChat(zhou);

			// http://a.app.qq.com/o/simple.jsp?pkgname=com.zams.www
			// String data =
			// "http://183.62.138.31:1011/appshare/"+user_id+".html";
			// String data =
			// "http://a.app.qq.com/o/simple.jsp?pkgname=com.zams.www";
			String data = RealmName.REALM_NAME_FX + "/appshare/" + user_id
					+ ".html";
			// link_url = RealmName.REALM_NAME_FX+"/appshare/"+user_id+".html";
			System.out.println("分享11======================" + data);
			String zhou = "中安民生下载地址,下载后可帮分享的好友获得福利" + data;
			System.out.println("==========" + zhou);
			if (index == 16) {
				System.out.println("==========" + 16);
				softshareWxChat(zhou);
			}else if (index == 17) {
				System.out.println("==========" + 17);
				softshareWxFriend(zhou);
			}else if (index == 18) {
				System.out.println("==========" + 18);
				Uri uri = Uri.parse("smsto:");
				Intent it = new Intent(Intent.ACTION_SENDTO, uri);
				it.putExtra("sms_body", zhou);
				startActivity(it);
			}
			// }
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * 微信分享
	 *
	 * @param text
	 */
	private void softshareWxChat(String text) {
		String temp[] = text.split("http");

		api = WXAPIFactory.createWXAPI(FenXiangActivity.this, Constant.APP_ID,
				false);
		api.registerApp(Constant.APP_ID);
		WXWebpageObject webpage = new WXWebpageObject();
		webpage.webpageUrl = "http" + temp[1];
		// webpage.webpageUrl = temp[1];
		WXMediaMessage msg = new WXMediaMessage(webpage);
		// msg.title = "我发你一个软件,看看呗!";
		msg.title = "中安民生APP分享";
		msg.description = temp[0];
		Bitmap thumb = BitmapFactory.decodeResource(getResources(),
				R.drawable.app_zams);
		msg.thumbData = Util.bmpToByteArray(thumb, true);

		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("webpage");
		req.message = msg;
		req.scene = SendMessageToWX.Req.WXSceneSession;
		boolean flag = api.sendReq(req);

		System.out.println("微信注册" + flag);

	}

	/**
	 * 微信分享朋友圈
	 *
	 * @param text
	 */
	private void softshareWxFriend(String text) {
		String temp[] = text.split("http");
		api = WXAPIFactory.createWXAPI(FenXiangActivity.this, Constant.APP_ID,
				false);
		api.registerApp(Constant.APP_ID);
		WXWebpageObject webpage = new WXWebpageObject();
		webpage.webpageUrl = "http" + temp[1];
		WXMediaMessage msg = new WXMediaMessage(webpage);
		msg.title = "中安民生APP分享";
		// msg.title = "ni"+"我发你一个软件,看看呗!";
		msg.description = temp[0];
		Bitmap thumb = BitmapFactory.decodeResource(
				FenXiangActivity.this.getResources(), R.drawable.app_zams);
		msg.thumbData = Util.bmpToByteArray(thumb, true);
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("webpage");
		req.message = msg;
		req.scene = SendMessageToWX.Req.WXSceneTimeline;
		boolean flag = api.sendReq(req);
		System.out.println(flag + "-->" + msg.thumbData);
	}

	private String buildTransaction(final String type) {
		return (type == null) ? String.valueOf(System.currentTimeMillis())
				: type + System.currentTimeMillis();
	}

	private void getmianduimian() {
		try {
			// avatar = spPreferences.getString("avatar", "");
			// System.out.println("avatar============="+avatar);
			// bitmap_touxiang = BitUtil.stringtoBitmap(avatar);
			// bitmap_touxiang = GetImgUtil.getImage(avatar);
			System.out
					.println("bitmap_touxiang=============" + bitmap_touxiang);
			System.out.println("avatar=============" + avatar);
			if (avatar.contains("http")) {
				bitmap_tx = BitmapFactory.decodeResource(getResources(),
						R.drawable.app_zams);
			} else if (bitmap_touxiang != null) {
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

			String content = link_url;
			System.out.println("content=============" + content);
			try {
				mBitmap = createBitmap(new String(content.getBytes(),
						"ISO-8859-1"));
				erweima = BitUtil.bitmaptoString(mBitmap);
				System.out.println("mBitmap=============" + mBitmap);
				System.out.println("erweima======分享=======" + erweima);

				Intent intent1 = new Intent(FenXiangActivity.this,
						MainDuiMianPromoteActivity.class);
				intent1.putExtra("mdm_sys", mdm_sys);
				intent1.putExtra("erweima", erweima);
				startActivity(intent1);
				avatar = "";
				// Intent intent1 = new Intent(FenXiangActivity.this,
				// MianDuiMianFxhbActivity.class);
				// intent1.putExtra("erweima", erweima);
				// intent1.putExtra("touxiang", touxiang);
				// startActivity(intent1);

			} catch (WriterException e) {
				e.printStackTrace();
			}catch (IOException e) {
				e.printStackTrace();
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
				String img_url = getIntent().getStringExtra("img_url");
				System.out.println("img_url==========" + img_url);
				String img_url2 = RealmName.REALM_NAME_HTTP + img_url;

				System.out.println("img_url2==============" + img_url2);
				thumb = GetImgUtil.getImage(img_url2);// BitmapFactory：图片工厂！
				// Bitmap bitMap_tx = Utils.toRoundBitmap(bmp,null);//
				// 这个时候的图片已经被处理成圆形的了
				// System.out.println("bitMap_tx=============="+bitMap_tx);
				System.out.println("bmp==============" + thumb);

			} catch (Exception e) {
				Log.i("ggggg", e.getMessage());
			}
		}
	};

	Runnable getPicByUrl2 = new Runnable() {
		@Override
		public void run() {
			try {
				String img_url2 = RealmName.REALM_NAME_HTTP + avatar;
				System.out.println("img_url2==============" + img_url2);
				bitmap_touxiang = GetImgUtil.getImage(img_url2);// BitmapFactory：图片工厂！
				// Bitmap bitMap_tx =
				// Utils.toRoundBitmap(bitmap_touxiang,null);//
				// 这个时候的图片已经被处理成圆形的了
				// touxiang = BitUtil.bitmaptoString(bitMap_tx);
				// mdm_sys = BitUtil.bitmaptoString(bitmap_touxiang);
				// System.out.println("touxiang=============="+touxiang);
				// System.out.println("mdm_sys=============="+mdm_sys);
				System.out.println("bitmap_touxiang=============="
						+ bitmap_touxiang);
			} catch (Exception e) {
				Log.i("ggggg", e.getMessage());
			}
		}
	};

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

	//当Activity被销毁时会调用onDestory方法
	@Override
	protected void onDestroy() {
		super.onDestroy();
		try {
			BitmapDrawable bd1 = (BitmapDrawable)zams_fw_1.getBackground();
			zams_fw_1.setBackgroundResource(0);//别忘了把背景设为null，避免onDraw刷新背景时候出现used a recycled bitmap错误
			bd1.setCallback(null);
			bd1.getBitmap().recycle();
			BitmapDrawable bd2 = (BitmapDrawable)zams_fw_2.getBackground();
			zams_fw_2.setBackgroundResource(0);//别忘了把背景设为null，避免onDraw刷新背景时候出现used a recycled bitmap错误
			bd2.setCallback(null);
			bd2.getBitmap().recycle();
			BitmapDrawable bd3 = (BitmapDrawable)zams_fw_3.getBackground();
			zams_fw_3.setBackgroundResource(0);//别忘了把背景设为null，避免onDraw刷新背景时候出现used a recycled bitmap错误
			bd3.setCallback(null);
			bd3.getBitmap().recycle();
			BitmapDrawable bd4 = (BitmapDrawable)zams_fw_4.getBackground();
			zams_fw_4.setBackgroundResource(0);//别忘了把背景设为null，避免onDraw刷新背景时候出现used a recycled bitmap错误
			bd4.setCallback(null);
			bd4.getBitmap().recycle();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

}
