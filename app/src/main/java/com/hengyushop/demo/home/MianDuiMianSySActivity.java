package com.hengyushop.demo.home;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.ctrip.openapi.java.utils.BitUtil;
import com.hengyushop.demo.at.BaseActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.R;

/**
 * 面对面扫一扫
 * 
 * @author Administrator
 * 
 */
public class MianDuiMianSySActivity extends BaseActivity implements
		OnClickListener {

	private ImageView iv_fanhui, iv_qr_image1, iv_touxiang;
	private TextView tv_titel;
	private DialogProgress progress;
	private SharedPreferences spPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mainduimian_sys);
		try {
			spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
			progress = new DialogProgress(MianDuiMianSySActivity.this);
			tv_titel = (TextView) findViewById(R.id.tv_titel);
			String saoyisao = getIntent().getStringExtra("saoyisao");
			if (saoyisao != null) {
				if (saoyisao.equals("1")) {
					tv_titel.setText("分享好友扫描，升级服务顾问！");
				}
			} else {
				tv_titel.setText("好友扫描下载，立享优惠！");
			}

			intren();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void intren() {
		try {

			iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
			iv_qr_image1 = (ImageView) findViewById(R.id.iv_qr_image1);
			// iv_touxiang = (ImageView) findViewById(R.id.iv_touxiang);
			String erweima = getIntent().getStringExtra("erweima");
			Bitmap bitmap = BitUtil.stringtoBitmap(erweima);
			iv_qr_image1.setImageBitmap(bitmap);

			// String avatar = spPreferences.getString("avatar", "");
			// ImageLoader imageLoader=ImageLoader.getInstance();
			// imageLoader.displayImage((String)RealmName.REALM_NAME_HTTP+avatar,iv_touxiang);

			iv_fanhui.setOnClickListener(this);

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
			try {
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
