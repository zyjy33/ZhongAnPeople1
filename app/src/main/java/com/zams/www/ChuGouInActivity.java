package com.zams.www;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.hengyushop.demo.at.BaseActivity;
import com.zams.www.R;

public class ChuGouInActivity extends BaseActivity {
	private Spinner price;
	private ArrayAdapter priceAdapter;
	private String priceValue;
	private ImageButton img_btn_add, img_btn_reduce;
	private EditText et_number;
	private TextView right_price;
	private Button btn;
	private int Current;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chugou_in);
		price = (Spinner) findViewById(R.id.price);
		et_number = (EditText) findViewById(R.id.et_number);
		img_btn_add = (ImageButton) findViewById(R.id.img_btn_add);
		img_btn_reduce = (ImageButton) findViewById(R.id.img_btn_reduce);
		right_price = (TextView) findViewById(R.id.right_price);
		btn = (Button) findViewById(R.id.btn);

		ArrayList<String> st = new ArrayList<String>();
		st.add("500Ԫ");
		st.add("1Ԫ");
		final ArrayList<String> sts = new ArrayList<String>();
		sts.add("500");
		sts.add("1");
		Current = Integer.parseInt(sts.get(0));
		priceAdapter = new ArrayAdapter(ChuGouInActivity.this,
				android.R.layout.simple_spinner_item, st);
		priceAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		price.setAdapter(priceAdapter);
		price.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				priceValue = sts.get(arg2);
				Current = Integer.parseInt(priceValue);
				String value = et_number.getText().toString();
				int ve = Integer.parseInt(value);
				et_number.setText(String.valueOf(ve));
				right_price.setText(String.valueOf(Current * ve));
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
		img_btn_add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String value = et_number.getText().toString();
				int ve = Integer.parseInt(value);
				int total = ve + 1;
				et_number.setText(String.valueOf(total));
				right_price.setText(String.valueOf(Current * total));
			}
		});
		img_btn_reduce.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String value = et_number.getText().toString();
				int ve = Integer.parseInt(value);
				int total = (ve - 1) <= 0 ? 0 : (ve - 1);
				et_number.setText(String.valueOf(total));
				right_price.setText(String.valueOf(Current * total));

			}
		});

		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(ChuGouInActivity.this,
						ChuGouDetailActivity.class);
				intent.putExtra("price", right_price.getText().toString());
				startActivity(intent);

			}
		});

	}
}
