package com.hengyushop.demo.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.ctrip.openapi.java.utils.BitUtil;
import com.ctrip.openapi.java.utils.GetImgUtil;
import com.ctrip.openapi.java.utils.LogoConfig;
import com.example.uploadpicdemo.Utils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.hengyushop.demo.at.BaseActivity;
import com.zams.www.R;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * 面对面推广
 *
 * 中安民生
 * @author Administrator
 *
 */
public class MainDuiMianllActivity extends BaseActivity implements OnClickListener{
	/**
	 * 黑点颜色
	 */
	private static final int BLACK = 0xFF000000;
	/**
	 * 白色
	 */
	private static final int WHITE = 0xFFFFFFFF;
	/**
	 * 正方形二维码宽度
	 */
	private static final int CODE_WIDTH = 440;
	/**
	 * LOGO宽度值,最大不能大于二维码20%宽度值,大于可能会导致二维码信息失效
	 */
	private static final int LOGO_WIDTH_MAX = CODE_WIDTH / 5;
	/**
	 *LOGO宽度值,最小不能小鱼二维码10%宽度值,小于影响Logo与二维码的整体搭配
	 */
	private static final int LOGO_WIDTH_MIN = CODE_WIDTH / 10;
	/**
	 * 生成的二维码图片存储的URI
	 */
	private Uri imageFileUri;
	private ImageView iv_fanhui,im_shanchu,mImageView1,mImageView2;
	private DialogProgress progress;
	private SharedPreferences spPreferences;
	private EditText mEditText;
	private Button btn_data;
	private LinearLayout ll_mdm_sys,ll_fx_zshb;
	private List<String> list;
	private ListView lv;
	private TextView tv_geshu,tv_shanchu;
	TestAdapter adapter;
	RelativeLayout rl_tupian;
	String erweima = "";
	Bitmap bitmap;// 二维码中间图片
	private int iv_halfWidth = 20;// 显示中间图片的宽度的一半
	Bitmap mBitmap;// 二维码图片;

	Bitmap bitmap_tx,bitmap_touxiang;
	String avatar = "";
	String touxiang = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mianduimian);
		getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		progress = new DialogProgress(MainDuiMianllActivity.this);

		//		String content = "http://183.62.138.31:1010/appinvite/"+7028+"/"+00+".html";
		//		try {
		//			if (!TextUtils.isEmpty(content)) {
		//				LogoConfig logoConfig = new LogoConfig();
		//				Bitmap logoBitmap = logoConfig.modifyLogo(BitmapFactory.decodeResource(getResources(),
		//								R.drawable.white_bg), BitmapFactory
		//								.decodeResource(getResources(),
		//										R.drawable.app_zams));
		//				System.out.println("logoBitmap============="+logoBitmap);
		//				Bitmap codeBitmap = createCode(content, logoBitmap);
		//////				imgCode.setImageBitmap(codeBitmap);
		//				System.out.println("codeBitmap============="+codeBitmap);
		//			} else {
		//				Toast.makeText(MainDuiMianllActivity.this, "请输入要生成的字符串",
		//						Toast.LENGTH_SHORT).show();
		//			}
		//		} catch (Exception e) {
		//			e.printStackTrace();
		//		}


		intren();
	}

	//	@Override
	//	protected void onResume() {
	//
	//		super.onResume();
	//		TextView tv_geshu = (TextView) findViewById(R.id.tv_geshu);
	//		if (list.size() > 0) {
	//			tv_geshu.setText(list.size());
	//		}
	//	}

	public void intren() {
		try {
			avatar = spPreferences.getString("avatar", "");
			System.out.println("avatar============="+avatar);
			if (!avatar.equals("")) {
				new Thread(getPicByUrl).start();
			}

			Bitmap bitmap_dihua = BitmapFactory.decodeResource(getResources(), R.drawable.dihua);
			//bitmap = Bitmap.createBitmap(100, 20, Config.ARGB_8888);
			BitmapDrawable drawable = new BitmapDrawable(bitmap_dihua);
			//			drawable.setTileModeXY(TileMode.REPEAT , TileMode.REPEAT );
			drawable.setTileModeX(TileMode.REPEAT);
			drawable.setDither(true);
			View view = findViewById(R.id.iv_dihua);
			view.setBackgroundDrawable(drawable);

			//		mImageView = (ImageView) findViewById(R.id.iv_qr_image);
			//		mImageView1 = (ImageView) findViewById(R.id.iv_qr_image1);
			//		mImageView2 = (ImageView) findViewById(R.id.iv_qr_image2);
			iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
			im_shanchu = (ImageView) findViewById(R.id.im_shanchu);
			mEditText = (EditText) findViewById(R.id.et_haoma);
			btn_data = (Button) findViewById(R.id.btn_data);
			tv_geshu = (TextView) findViewById(R.id.tv_geshu);
			tv_shanchu = (TextView) findViewById(R.id.tv_shanchu);
			//
			//		mImageView1.setBackgroundResource(R.drawable.rwm);
			//		mImageView2.setBackgroundResource(R.drawable.rwmhs);
			ll_mdm_sys = (LinearLayout) findViewById(R.id.ll_mdm_sys);
			ll_fx_zshb = (LinearLayout) findViewById(R.id.ll_fx_zshb);
			ll_mdm_sys.setOnClickListener(this);
			ll_fx_zshb.setOnClickListener(this);
			iv_fanhui.setOnClickListener(this);
			btn_data.setOnClickListener(this);
			im_shanchu.setOnClickListener(this);
			tv_shanchu.setOnClickListener(this);
			list = new ArrayList<String>();
			//		list.add("我是小明");
			//		list.add("我是小张");
			//		list.add("我是小海");
			lv = (ListView) findViewById(R.id.myList);
			adapter = new TestAdapter(list, this);
			lv.setAdapter(adapter);
			//		setListViewHeightBasedOnChildren(lv);

			btn_data.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					String haoma = mEditText.getText().toString().trim();
					//http://183.62.138.31:1010/appinvite/109/13117711520_13117711521.html
					//https://www.pgyer.com/ZnX8

					if (TextUtils.isEmpty(haoma)) {
						Toast.makeText(MainDuiMianllActivity.this, "请输入手机号", Toast.LENGTH_SHORT).show();
					}else if (haoma.length() < 11 ) {
						Toast.makeText(MainDuiMianllActivity.this, "手机号少于11位", Toast.LENGTH_SHORT).show();
					}else {
						//					String content = "http://183.62.138.31:1010/appinvite/"+7028+"/"+00+".html";
						try {
							//						if (!TextUtils.isEmpty(content)) {
							//							LogoConfig logoConfig = new LogoConfig();
							//							Bitmap logoBitmap = logoConfig.modifyLogo(BitmapFactory.decodeResource(getResources(),
							//											R.drawable.white_bg), BitmapFactory
							//											.decodeResource(getResources(),
							//													R.drawable.app_zams));
							//							Bitmap codeBitmap = createCode(haoma, logoBitmap);
							//							saveBitmap(codeBitmap, "guolin_code.png");
							//							imgCode.setImageBitmap(codeBitmap);
							//						} else {
							//							Toast.makeText(MainDuiMianllActivity.this, "请输入要生成的字符串",
							//									Toast.LENGTH_SHORT).show();
							//						}
						} catch (Exception e) {
							e.printStackTrace();
						}


						Bitmap _Bitmap;
						try {

							String id = spPreferences.getString("user_id", "");

							System.out.println("============="+haoma);
							list.add(haoma); //添加新数据 改变List集合
							adapter.list = list;  //将改变后的List集合赋给Adapter中的集合

							StringBuffer str = new StringBuffer();
							for(String s:list){
								str.append(s+"_");
							}

							str.delete(str.lastIndexOf("_"),str.length());

							System.out.println("22---------------"+str);

							//						String str1 = "http://183.62.138.31:1011/appinvite/"+id+"/"+str+".html";
							String str1 = RealmName.REALM_NAME_HTTP+"/appinvite/"+id+"/"+str+".html";

							System.out.println("str============="+str1);

							//						mImageView1.setVisibility(View.GONE);
							//						mImageView2.setVisibility(View.GONE);
							//						mImageView.setVisibility(View.VISIBLE);

							//						_Bitmap = EncodingHandler.createQRCode(str1, 350);
							//						erweima = BitUtil.bitmaptoString(_Bitmap);
							//						System.out.println("id111============="+id);
							//						mImageView.setImageBitmap(_Bitmap);

							//						avatar = spPreferences.getString("avatar", "");
							//						new Thread(getPicByUrl).start();
							System.out.println("avatar============="+avatar);
							System.out.println("bitmap_tx1============="+bitmap_tx);
							if (!avatar.equals("")) {
								//							new Thread(getPicByUrl).start();
								bitmap_tx = bitmap_touxiang;
							}else {
								bitmap_tx =  BitmapFactory.decodeResource(getResources(),R.drawable.app_zams);
							}

							System.out.println("bitmap_tx============="+bitmap_tx);
							LogoConfig logoConfig = new LogoConfig();
							bitmap = logoConfig.modifyLogo(BitmapFactory.decodeResource(getResources(),R.drawable.white_bg),bitmap_tx);
							// 缩放图片，用到矩阵去做
							Matrix matrix = new Matrix();
							float sx = (float) 2 * iv_halfWidth / bitmap.getWidth();
							float sy = (float) 2 * iv_halfWidth / bitmap.getHeight();
							matrix.setScale(sx, sy);
							// 生成缩放后的图片
							bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
									bitmap.getHeight(), matrix, false);

							String content = "http://183.62.138.31:1011/appinvite/"+id+"/"+str+".html";
							http://183.62.138.31:1011/appinvite/21734/13117711522.html
							System.out.println("content============="+content);
							try {
								mBitmap = createBitmap(new String(content.getBytes(), "ISO-8859-1"));
								erweima = BitUtil.bitmaptoString(mBitmap);
								System.out.println("erweima============="+erweima);
							} catch (WriterException e) {
								e.printStackTrace();
							}  catch (IOException e) {
								e.printStackTrace();
							}

							//						zhou();
							if (list.size() > 0) {
								int gs = list.size();
								String geshu = String.valueOf(gs);
								tv_geshu.setText(geshu);
								//								btn_data.setText("+继续添加");
								im_shanchu.setVisibility(View.VISIBLE);
								tv_shanchu.setVisibility(View.VISIBLE);
							}

							mEditText.setText("");
							//						setListViewHeightBasedOnChildren(lv);
							adapter.notifyDataSetChanged(); //调用notifyDataSetChanged方法
						} catch (Exception e) {

							e.printStackTrace();
						}

						//					} catch (WriterException e) {
						////						showToast("异常");
						//						e.printStackTrace();
						//						Toast.makeText(MainDuiMianActivity.this, "异常", 200).show();
						//					}

					}

				}
			});

		} catch (Exception e) {

			e.printStackTrace();
		}

	}


	Runnable getPicByUrl = new Runnable() {
		@Override
		public void run() {
			try {
				//					String img_url2 = "http://183.62.138.31:1010/upload/phone/113875199/20170217164544307.jpg";
				String img_url2 = RealmName.REALM_NAME_HTTP +avatar;
				System.out.println("img_url2=============="+img_url2);

				bitmap_touxiang = GetImgUtil.getImage(img_url2);// BitmapFactory：图片工厂！
				Bitmap	bitMap_tx = Utils.toRoundBitmap(bitmap_touxiang,null);// 这个时候的图片已经被处理成圆形的了
				touxiang = BitUtil.bitmaptoString(bitMap_tx);
				System.out.println("touxiang=============="+touxiang);
				System.out.println("bitmap_touxiang=============="+bitmap_touxiang);
				//					bitmap_tx = bitmap_touxiang;
			} catch (Exception e) {
				Log.i("ggggg", e.getMessage());
			}
		}
	};

	private void zhou() {

		try {

			TextView tv_geshu = (TextView) findViewById(R.id.tv_geshu);
			if (list.size() > 0) {
				int gs = list.size();
				String geshu = String.valueOf(gs);
				tv_geshu.setText(geshu);
			}
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
			case R.id.ll_mdm_sys:
				if (erweima.equals("")) {
					Toast.makeText(MainDuiMianllActivity.this, "请添加好友手机号", Toast.LENGTH_SHORT).show();
				}else {
					Intent intent = new Intent(MainDuiMianllActivity.this, MianDuiMianSySActivity.class);
					intent.putExtra("erweima", erweima);
					startActivity(intent);
				}
				break;
			case R.id.tv_shanchu:
				list.clear();
				adapter = new TestAdapter(list, this);
				lv.setAdapter(adapter);
				adapter.notifyDataSetChanged();
				if (list.size() == 0) {
					int gs = list.size();
					String geshu = String.valueOf(gs);
					tv_geshu.setText(geshu);
					im_shanchu.setVisibility(View.GONE);
					tv_shanchu.setVisibility(View.GONE);
				}else {

				}
				break;
			case R.id.ll_fx_zshb:
				if (erweima.equals("")) {
					Toast.makeText(MainDuiMianllActivity.this, "请添加好友手机号", Toast.LENGTH_SHORT).show();
				}else {
					Intent intent = new Intent(MainDuiMianllActivity.this, MianDuiMianFxhbActivity.class);
					intent.putExtra("erweima", erweima);
					intent.putExtra("touxiang", touxiang);
					startActivity(intent);
				}
				break;
			default:
				break;
		}
	}


	public class TestAdapter extends BaseAdapter {
		public List<String> list;
		private Context context;
		private LayoutInflater mInflater;
		public TestAdapter(List<String> list,Context context) {
			super();
			this.list = list;
			this.context = context;
			mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {

			return list.size();
		}

		@Override
		public Object getItem(int position) {

			return list.get(position);
		}

		@Override
		public long getItemId(int position) {

			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolder vh = null;
			if(convertView == null){
				vh = new ViewHolder();
				convertView = mInflater.inflate(R.layout.listitem, null);
				vh.tv = (TextView) convertView.findViewById(R.id.textView1);
				vh.tv2 = (ImageView) convertView.findViewById(R.id.textView2);

				convertView.setTag(vh);
			}else{
				vh = (ViewHolder) convertView.getTag();

				vh.tv2.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {

						//						list.clear();
						list.remove(position);
						System.out.println("============="+position);
						try {
							int gs = list.size();
							String geshu = String.valueOf(gs);
							if (list.size() > 0) {
								tv_geshu.setText(geshu);
								im_shanchu.setVisibility(View.VISIBLE);
								tv_shanchu.setVisibility(View.VISIBLE);
							}else {
								tv_geshu.setText(geshu);
								im_shanchu.setVisibility(View.GONE);
								tv_shanchu.setVisibility(View.GONE);
								//								mImageView1.setVisibility(View.VISIBLE);
								//								mImageView2.setVisibility(View.VISIBLE);
								//								mImageView.setVisibility(View.GONE);
								//								btn_data.setText("请添加");
							}
						} catch (Exception e) {

							e.printStackTrace();
						}
						adapter.notifyDataSetChanged();
					}
				});

			}
			vh.tv.setText(list.get(position));
			return convertView;
		}

		public class ViewHolder{
			public TextView tv;
			public ImageView tv2;
		}
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

	public static void setListViewHeightBasedOnChildren(ListView listView) {
		// 获取ListView对应的Adapter
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
			// listAdapter.getCount()返回数据项的数目
			View listItem = listAdapter.getView(i, null, listView);
			// 计算子项View 的宽高
			listItem.measure(0, 0);
			// 统计所有子项的总高度
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		// listView.getDividerHeight()获取子项间分隔符占用的高度
		// params.height最后得到整个ListView完整显示需要的高度
		listView.setLayoutParams(params);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
