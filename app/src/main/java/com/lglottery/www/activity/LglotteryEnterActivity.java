package com.lglottery.www.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.hengyushop.demo.at.BaseActivity;
import com.lglottery.www.domain.LglotteryBean;
import com.lglottery.www.domain.Lglottery_Item;
import com.zams.www.R;

public class LglotteryEnterActivity extends BaseActivity {
	private Button lglottery_enter_go;
	private TextView  lglottery_enter_view2;
	private RadioGroup lglottery_enter_rg;
	private RadioButton  lglottery_enter_rb2;
	private Lglottery_Item item;
	//	private int doll = 0;
	private Bundle bundle ;
	private int tag = -1;//0代表代金券，1代表金豆
	/**
	 * 组件的创建和生成
	 */
	private void init() {
		lglottery_enter_rg = (RadioGroup) findViewById(R.id.lglottery_enter_rg);
		lglottery_enter_rg.setOnCheckedChangeListener(changeListener);

		lglottery_enter_rb2 = (RadioButton) findViewById(R.id.lglottery_enter_rb2);
		lglottery_enter_rb2.setChecked(true);
		lglottery_enter_view2 = (TextView) findViewById(R.id.lglottery_enter_view2);
		lglottery_enter_go = (Button) findViewById(R.id.lglottery_enter_go);
		lglottery_enter_go.setOnClickListener(clickListener);
		bundle = getIntent().getExtras();
		if (bundle.containsKey("pay_lottery")) {
			item = (Lglottery_Item) bundle.getSerializable("pay_lottery");

			//			doll = bundle.getInt("pay_lottery");
			lglottery_enter_view2.setText(getString(R.string.lottery_view2,
					item.getJinbi()));
		}
	}

	/**
	 * 事件监听
	 */
	private OnClickListener clickListener = new OnClickListener() {
		@Override
		public void onClick(View view) {
			switch (view.getId()) {
				case R.id.lglottery_enter_go:
					Intent mainIntent = new Intent(LglotteryEnterActivity.this,
							LglotteryMainActivity.class);
					LglotteryBean bean = new LglotteryBean();
					bean.setTag(tag);
					//				switch (tag) {
					//				case 0:
					//					break;
					//				case 1:
					//				break;
					//				default:
					//					break;
					//				}
					bean.setId(item.getId());
					bean.setBalance(item.getBalance());
					bean.setJinbi(item.getJinbi());
					Bundle extras = new Bundle();
					extras.putSerializable("object", bean);
					mainIntent.putExtras(extras);
					startActivity(mainIntent);
					break;
				default:
					break;
			}
		}
	};
	/**
	 * 关于复合组件按钮
	 */
	private OnCheckedChangeListener changeListener = new OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(RadioGroup arg0, int arg1) {
			switch (arg1) {
				case R.id.lglottery_enter_rb2:
					tag = 0;
					break;
				default:
					break;
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lglottery_enter_activity);
		init();
	}
}
