package com.zams.www;

import com.android.hengyu.ui.MyPopupWindowMenu;
import com.hengyushop.demo.at.BaseActivity;
import com.zams.www.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.Toast;
public class FinanceManageActivity extends BaseActivity implements OnClickListener {
	private RelativeLayout cash_info, cash_recharge, cash_cession,
			cash_withdraw, coin_info,jifen_info,jubi_info;
	private MyPopupWindowMenu popupWindowMenu;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.financemanage);
		popupWindowMenu = new MyPopupWindowMenu(this);
		example();

	}
	private void example() {
		jubi_info = (RelativeLayout) findViewById(R.id.jubi_info);
		cash_info = (RelativeLayout) findViewById(R.id.cash_info);
		cash_recharge = (RelativeLayout) findViewById(R.id.cash_recharge);
		cash_cession = (RelativeLayout) findViewById(R.id.cash_cession);
		cash_withdraw = (RelativeLayout) findViewById(R.id.cash_withdraw);
		coin_info = (RelativeLayout) findViewById(R.id.coin_info);
		jifen_info = (RelativeLayout) findViewById(R.id.jifen_info);
		cash_info.setOnClickListener(this);
		cash_recharge.setOnClickListener(this);
		cash_cession.setOnClickListener(this);
		cash_withdraw.setOnClickListener(this);
		coin_info.setOnClickListener(this);
		jifen_info.setOnClickListener(this);
		jubi_info.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {

		switch (v.getId()) {
			case R.id.jubi_info:
				Intent intent0 = new Intent(FinanceManageActivity.this,
						JubiDetailActivity.class);
				startActivity(intent0);
				break;
			case R.id.cash_info:
				Intent intent1 = new Intent(FinanceManageActivity.this,
						TicketDetailActivity.class);
				startActivity(intent1);
				break;
			case R.id.cash_recharge:
				Toast.makeText(getApplicationContext(), "此功能尚待完善", 200).show();
				break;
			case R.id.cash_cession:
				Intent intent3 = new Intent(FinanceManageActivity.this,
						TicketAttornActivity.class);
				startActivity(intent3);
				break;
			case R.id.cash_withdraw:
				Toast.makeText(getApplicationContext(), "此功能尚待完善", 200).show();
				break;
			case R.id.coin_info:
				Intent intent5 = new Intent(FinanceManageActivity.this,
						TicketShopDetailActivity.class);
				startActivity(intent5);
				break;
			case R.id.jifen_info:
				Intent intent6 = new Intent(FinanceManageActivity.this,
						JifenDetailActivity.class);
				startActivity(intent6);
				break;
			default:
				break;
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("menu");
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {

		if (0 == popupWindowMenu.currentState && popupWindowMenu.isShowing()) {
			popupWindowMenu.dismiss(); // 对话框消失
			popupWindowMenu.currentState = 1; // 标记状态，已消失
		} else {
			popupWindowMenu.showAtLocation(findViewById(R.id.layout),
					Gravity.BOTTOM, 0, 0);
			popupWindowMenu.currentState = 0; // 标记状态，显示中
		}
		return false; // true--显示系统自带菜单；false--不显示。
	}
}
