package com.hengyushop.demo.home;

import java.io.InputStream;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.hengyu.web.DialogProgress;
import com.ctrip.openapi.java.utils.BitUtil;
import com.hengyushop.demo.at.BaseActivity;
import com.zams.www.R;

/**
 * ������������
 * 
 * @author Administrator
 * 
 */
public class MianDuiMianGhztActivity extends BaseActivity implements
		OnClickListener {

	private ImageView iv_fanhui, iv_qr_image1, iv_qr_image2, iv_touxiang;
	private TextView tv_xiabu;
	private Button btn_fenxiang, btn_zhuti;
	private DialogProgress progress;
	private SharedPreferences spPreferences;
	public static String erweima, mdm_sys;
	String fx_tp2;
	public static String shuzi = "0";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mainduimian_ghzt);
		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		progress = new DialogProgress(MianDuiMianGhztActivity.this);

		intren();
	}
	
	public void onDestroy() {
		 super.onDestroy(); 
		 try {
			BitmapDrawable bd1 = (BitmapDrawable)iv_qr_image1.getBackground();
			iv_qr_image1.setBackgroundResource(0);//�����˰ѱ�����Ϊnull������onDrawˢ�±���ʱ�����used a recycled bitmap����
			bd1.setCallback(null);
			bd1.getBitmap().recycle();
			BitmapDrawable bd2 = (BitmapDrawable)iv_qr_image2.getBackground();
			iv_qr_image2.setBackgroundResource(0);//�����˰ѱ�����Ϊnull������onDrawˢ�±���ʱ�����used a recycled bitmap����
			bd2.setCallback(null);
			bd2.getBitmap().recycle();
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	};

	public void intren() {
		try {
			Bitmap bitmap_dihua = BitmapFactory.decodeResource(getResources(),
					R.drawable.dihua);
			// Bitmap bitmap_fx =
			// BitmapFactory.decodeResource(getResources(),R.drawable.ysj_haibao2);
			// // bitmapΪֻ����
			// fx_tp2 = BitUtil.bitmaptoString(bitmap_fx);
			System.out.println("fx_tp2==============" + bitmap_dihua);
			iv_qr_image1 = (ImageView) findViewById(R.id.iv_qr_image1);
			// InputStream is =
			// this.getResources().openRawResource(R.drawable.ysj_haibao1);
			// BitmapFactory.Options options=new BitmapFactory.Options();
			// options.inJustDecodeBounds = false;
			// options.inSampleSize = 5; //width��hight��Ϊԭ����ʮ��һ
			// Bitmap bitmap_fx1 =BitmapFactory.decodeStream(is,null,options);
			// iv_qr_image1.setImageBitmap(bitmap_fx1);
//			iv_qr_image1.setBackgroundResource(R.drawable.ysj_hb1);
			Bitmap bm1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.ysj_hb1);
			BitmapDrawable bd1 = new BitmapDrawable(this.getResources(), bm1);
			iv_qr_image1.setBackgroundDrawable(bd1);
			iv_qr_image2 = (ImageView) findViewById(R.id.iv_qr_image2);
			// InputStream is1 =
			// this.getResources().openRawResource(R.drawable.ysj_haibao2);
			// BitmapFactory.Options options1=new BitmapFactory.Options();
			// options.inJustDecodeBounds = false;
			// options.inSampleSize = 5; //width��hight��Ϊԭ����ʮ��һ
			// Bitmap bitmap_fx2 =BitmapFactory.decodeStream(is1,null,options1);
			// iv_qr_image2.setImageBitmap(bitmap_fx2);
//			iv_qr_image2.setBackgroundResource(R.drawable.ysj_hb2);
			iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
			Bitmap bm2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.ysj_hb2);
			BitmapDrawable bd2 = new BitmapDrawable(this.getResources(), bm2);
			iv_qr_image2.setBackgroundDrawable(bd2);
			iv_fanhui.setOnClickListener(this);
			iv_qr_image1.setOnClickListener(this);
			iv_qr_image2.setOnClickListener(this);
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
		case R.id.iv_qr_image1:
			try {
				shuzi = "1";
				erweima = getIntent().getStringExtra("erweima");
				mdm_sys = getIntent().getStringExtra("mdm_sys");
				// Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(),
				// R.drawable.ysj_haibao1);
				// String fx_tp2 = BitUtil.bitmaptoString(bitmap1);
				Intent intent = new Intent(MianDuiMianGhztActivity.this,
						MainDuiMianPromoteActivity.class);
				intent.putExtra("num", "1");
				intent.putExtra("erweima", erweima);
				intent.putExtra("mdm_sys", mdm_sys);
				startActivity(intent);
				MainDuiMianPromoteActivity.handler.sendEmptyMessage(1);
				finish();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			break;
		case R.id.iv_qr_image2:
			try {
				shuzi = "2";
				erweima = getIntent().getStringExtra("erweima");
				mdm_sys = getIntent().getStringExtra("mdm_sys");
				System.out.println("erweima==============" + erweima);
				Intent intent1 = new Intent(MianDuiMianGhztActivity.this,
						MainDuiMianPromoteActivity.class);
				intent1.putExtra("num", "2");
				intent1.putExtra("erweima", erweima);
				intent1.putExtra("mdm_sys", mdm_sys);
				intent1.putExtra("fx_tp2", fx_tp2);
				startActivity(intent1);
				MainDuiMianPromoteActivity.handler.sendEmptyMessage(1);
				finish();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
	}
}
