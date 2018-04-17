package com.hengyushop.demo.home;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.pub.MyOrderllAdapter;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.home.YangLaoChongZhiActivity;
import com.hengyushop.demo.my.MyOrderZFActivity;
import com.hengyushop.demo.my.TishiCarArchivesActivity;
import com.hengyushop.entity.UserRegisterllData;
import com.hengyushop.entity.UserSenJiBean;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.MyOrderConfrimActivity;
import com.zams.www.PersonCenterActivity;
import com.zams.www.R;

/**
 * 提示养老金
 * 
 * @author
 * 
 */
public class TishiPensionActivity extends Activity implements OnClickListener {
	private Intent intent;
	public Activity mContext;
	String user_name, user_id, nichen, order_no;
	private DialogProgress progress;
	private SharedPreferences spPreferences;
	String login_sign, amount;
	public static String give_pension, article_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tishi_pension);
		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		user_name = spPreferences.getString("user", "");
		user_id = spPreferences.getString("user_id", "");
		progress = new DialogProgress(TishiPensionActivity.this);
		initUI();
	}

	protected void initUI() {
		try {
			System.out.println("5=================================");
			// ImageView iv_fanhui2 = (ImageView) findViewById(R.id.iv_fanhui2);
			// iv_fanhui2.setBackgroundResource(R.drawable.ylth_tc);
			TextView textView1 = (TextView) findViewById(R.id.textView1);//
			if (TishiCarArchivesActivity.give_pension != null) {
				try {

					give_pension = TishiCarArchivesActivity.give_pension;
					article_id = TishiCarArchivesActivity.article_id;
					TishiCarArchivesActivity.accept_name = null;
					TishiCarArchivesActivity.user_mobile = null;
					TishiCarArchivesActivity.province = null;
					TishiCarArchivesActivity.city = null;
					TishiCarArchivesActivity.area = null;
					TishiCarArchivesActivity.user_address = null;
					TishiCarArchivesActivity.recharge_no = null;
					TishiCarArchivesActivity.datetime = null;
					TishiCarArchivesActivity.sell_price = null;
					TishiCarArchivesActivity.give_pension = null;
					TishiCarArchivesActivity.article_id = null;
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			} else if (MyOrderConfrimActivity.accept_name1 != null) {
				try {
					give_pension = MyOrderConfrimActivity.give_pension1;
					article_id = MyOrderConfrimActivity.article_id1;
					MyOrderConfrimActivity.accept_name1 = null;
					MyOrderConfrimActivity.user_mobile1 = null;
					MyOrderConfrimActivity.province1 = null;
					MyOrderConfrimActivity.city1 = null;
					MyOrderConfrimActivity.area1 = null;
					MyOrderConfrimActivity.user_address1 = null;
					MyOrderConfrimActivity.recharge_no1 = null;
					MyOrderConfrimActivity.datetime1 = null;
					MyOrderConfrimActivity.sell_price1 = null;
					MyOrderConfrimActivity.give_pension1 = null;
					MyOrderConfrimActivity.article_id1 = null;
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			} else if (MyOrderZFActivity.accept_name != null) {
				try {
					give_pension = MyOrderZFActivity.give_pension;
					article_id = MyOrderZFActivity.article_id;
					MyOrderZFActivity.accept_name = null;
					MyOrderZFActivity.user_mobile = null;
					MyOrderZFActivity.province = null;
					MyOrderZFActivity.city = null;
					MyOrderZFActivity.area = null;
					MyOrderZFActivity.user_address = null;
					MyOrderZFActivity.recharge_no = null;
					MyOrderZFActivity.datetime = null;
					MyOrderZFActivity.sell_price = null;
					MyOrderZFActivity.give_pension = null;
					MyOrderZFActivity.article_id = null;
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			System.out.println("6=================================");
			// Toast.makeText(TishiPensionActivity.this, give_pension,
			// 200).show();
			textView1.setText("将进" + give_pension + "元");

			ImageView iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui1);
			iv_fanhui.setOnClickListener(this);
			ImageView iv_fanhui3 = (ImageView) findViewById(R.id.iv_fanhui3);
			iv_fanhui3.setOnClickListener(this);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * 点击触发事件
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		intent = new Intent();
		switch (v.getId()) {
		case R.id.iv_fanhui1:// 取消
			finish();
			break;
		case R.id.iv_fanhui3://
			Intent intent = new Intent(TishiPensionActivity.this,DBFengXiangActivity.class);
			intent.putExtra("sp_id", article_id);
			startActivity(intent);
			finish();
			break;
		default:
			break;
		}
	}

}