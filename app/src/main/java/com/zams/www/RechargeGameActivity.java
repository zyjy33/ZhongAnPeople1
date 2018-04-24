package com.zams.www;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.hengyu.ui.MyPopupWindowMenu;
import com.hengyushop.demo.at.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class RechargeGameActivity extends BaseActivity implements
		OnClickListener {

	Spinner sp_game_name, sp_game_money;
	Button btn_game;
	private LinearLayout ll_information;
	List<String> game_name;
	List<String> game_money;
	String name;
	String money;
	private LayoutInflater inflater;
	private View view;
	private PopupWindow pop;
	private TextView tv_name, tv_money;
	private MyPopupWindowMenu popupWindowMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recharge_game_card);
		popupWindowMenu = new MyPopupWindowMenu(this);
		btn_game = (Button) findViewById(R.id.btn_game);
		GameData();
		getGameData();

		// btn_game.setOnClickListener(this);
	}

	private void GameData() {
		sp_game_name = (Spinner) findViewById(R.id.sp_game_name);
		sp_game_money = (Spinner) findViewById(R.id.sp_game_money);
		sp_game_name.setPrompt("请选择游戏名称");
		sp_game_money.setPrompt("请选择充值金额");

		ll_information = (LinearLayout) findViewById(R.id.ll_information);
		ll_information.setVisibility(View.GONE);

		String[] str3 = new String[] { "梦幻西游", "魔兽世界", "永恒之塔", "奇迹世界", "征途",
				"热血传奇", "传奇世界", "大话西游2", "大唐豪侠", "天龙八部", "完美世界", "魔域", "诛仙2",
				"梦幻诛仙", "剑网3", "热血江湖", "问道", "劲舞团", "完美国际", "剑侠世界", "武林外传",
				"剑情网络版", "剑侠情缘2", "封神榜", "QQ幻想", "泡泡堂", "冒险岛", "水浒Q传", "彩虹岛",
				"街头篮球", "跑跑卡丁车", "三国群英传", "惊天动地", "超级舞者", "梦幻古龙", "梦幻国度",
				"天堂2", "风云", "卓越之剑", "华夏2", "联众世界", "春秋Q传", "传奇归来", "新英雄年代",
				"信长之野望", "热舞派对", "赤壁", "大话西游外传", "SD敢达", "穿越火线", "QQ自由幻想",
				"QQ三国", "华夏", "传奇外传", "封神榜2", "征服", "真三国无双", "口袋西游", "地下城与勇士",
				"星尘传说", "神鬼传奇", "兽血沸腾", "蜀门", "LUNA(露娜)", "问道" };
		game_name = new ArrayList<String>();
		for (int i = 0; i < str3.length; i++) {
			game_name.add(str3[i]);
		}
		ArrayAdapter aa3 = new ArrayAdapter(this,
				android.R.layout.simple_spinner_item, game_name);
		aa3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_game_name.setAdapter(aa3);

		String[] str4 = new String[] { "5元", "10元", "20元", "30元", "50元",
				"100元", "150元", "200元" };
		game_money = new ArrayList<String>();
		for (int i = 0; i < str4.length; i++) {
			game_money.add(str4[i]);
		}
		ArrayAdapter aa4 = new ArrayAdapter(this,
				android.R.layout.simple_spinner_item, game_money);
		aa4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_game_money.setAdapter(aa4);
	}

	private void getGameData() {

		sp_game_name.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
									   int arg2, long arg3) {

				name = game_name.get(arg2);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
		sp_game_money.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
									   int arg2, long arg3) {

				money = game_money.get(arg2);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
			case R.id.btn_game:

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
			popupWindowMenu
					.showAtLocation(findViewById(R.id.recharge_game_card),
							Gravity.BOTTOM, 0, 0);
			popupWindowMenu.currentState = 0; // 标记状态，显示中
		}
		return false; // true--显示系统自带菜单；false--不显示。
	}

}
