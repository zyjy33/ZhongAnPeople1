package com.hengyushop.demo.train;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.hengyu.web.RealmName;
import com.hengyushop.dao.WareDao;
import com.hengyushop.demo.at.AppManager;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.entity.UserRegisterData;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zams.www.R;

public class TrainAddPersonActivity extends BaseActivity {
	private EditText v1, v2, v5;
	private RadioGroup v3, v4;
	private RadioButton tempBtn1, tempBtn2;
	private Button button;
	private WareDao wareDao;
	private String yth;

	private void init() {
		v1 = (EditText) findViewById(R.id.v1);
		v2 = (EditText) findViewById(R.id.v2);
		v5 = (EditText) findViewById(R.id.v5);
		v3 = (RadioGroup) findViewById(R.id.v3);
		v4 = (RadioGroup) findViewById(R.id.v4);
		button = (Button) findViewById(R.id.submit);
	}

	private Handler handler = new Handler() {
		public void dispatchMessage(android.os.Message msg) {
			switch (msg.what) {
				case 1:
					Toast.makeText(getApplicationContext(), "Ìí¼Ó³É¹¦!", 200).show();
					AppManager.getAppManager().finishActivity();
					break;

				default:
					break;
			}
		};
	};



	@Override
	protected void onCreate(Bundle savedInstanceState) {

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.train_add_person);
		init();
		wareDao = new WareDao(getApplicationContext());
		UserRegisterData registerData = wareDao.findIsLoginHengyuCode();
		yth = registerData.getHengyuCode();
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				tempBtn1 = (RadioButton) findViewById(v3
						.getCheckedRadioButtonId());
				tempBtn2 = (RadioButton) findViewById(v4
						.getCheckedRadioButtonId());
				if (yth == null) {
					Toast.makeText(getApplicationContext(), "Î´µÇÂ¼!", 200).show();
				} else {
					if (tempBtn1 != null && tempBtn2 != null) {
						RequestParams params = new RequestParams();
						params.put("PiaoZhong", tempBtn1.getText().toString());
						params.put("DocumentType", tempBtn2.getText()
								.toString());
						params.put("ContactUserName", v1.getText().toString());
						params.put("ContactUserPhone", v2.getText().toString());
						params.put("DocumentNumber", v5.getText().toString());
						params.put("yth", yth);
						AsyncHttp
								.post(RealmName.REALM_NAME
												+ "/mi/TrainHandler.ashx?act=InsertTrainUserContact",
										params, new AsyncHttpResponseHandler() {
											public void onSuccess(int arg0,
																  String arg1) {
												System.out.println(arg1);
												try {
													JSONObject object = new JSONObject(
															arg1);
													String msg = object
															.getString("status");
													if (msg.equals("1")) {
														handler.sendEmptyMessage(1);
													}
												} catch (JSONException e) {
													  catch
													// block
													e.printStackTrace();
												}
											};
										}, getApplicationContext());
					}

				}
			}
		});
	}
}
