package com.hengyushop.demo.home;

import java.util.ArrayList;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.hengyu.pub.GoodsLieAdapter;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.entity.GuigeBean;
import com.hengyushop.entity.GuigeData;
import com.hengyushop.entity.GuigellBean;
import com.zams.www.R;
import com.zams.www.WareInformationActivity;

/**
 * 
 * 症状详情
 * 
 * @author Administrator
 * 
 */
public class SymptomsJieShaoActivity extends BaseActivity implements OnClickListener {
	private ImageView iv_fanhui, iv_jyh1, iv_jyh2, iv_jyh3, iv_jyh4;
	private LinearLayout ll_buju1, ll_buju2,ll_buju3,ll_buju4;
	private LinearLayout ll_dierbuju1, ll_dierbuju2,ll_dierbuju3,ll_dierbuju4,ll_dierbuju5
	,ll_dierbuju6,ll_dierbuju7,ll_quanbu;
	private SharedPreferences spPreferences;
	private TextView tv_ticket, tv_shop_ticket, tv_jifen_ticket,tv_djjifen_ticket;
	String user_name, user_id;
	private ListView new_list1,new_list2,new_list3,new_list4,new_list5
	,new_list6,new_list7;
	GuigeData md; 
	GuigeBean mb;
	ArrayList<GuigeData> list_ll = new ArrayList<GuigeData>();
	boolean zhuantai1 = false;
	boolean zhuantai2 = false;
	boolean zhuantai3 = false;
	ArrayList<GuigellBean> list;
	String item_id,article_id,article_id2,article_id3,article_id4,article_id5,article_id6,article_id7;
	ArrayList<String> list_id;
	ArrayList<String> list_tiem;
	ArrayList<String> list_tupian;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_zhengzhuang);
		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		user_name = spPreferences.getString("user", "");
		user_id = spPreferences.getString("user_id", "");
		
		Initialize();
	}

	/**
	 * 控件初始化
	 */
	private void Initialize() {

		try {
			tv_ticket = (TextView) findViewById(R.id.tv_ticket);
			tv_shop_ticket = (TextView) findViewById(R.id.tv_shop_ticket);
			tv_jifen_ticket = (TextView) findViewById(R.id.tv_jifen_ticket);
			tv_djjifen_ticket = (TextView) findViewById(R.id.tv_djjifen_ticket);
			TextView tv_title = (TextView) findViewById(R.id.textView1);
			tv_title.setText(getIntent().getStringExtra("title"));
			
			new_list1 = (ListView) findViewById(R.id.new_list1);
			new_list2 = (ListView) findViewById(R.id.new_list2);
			new_list3 = (ListView) findViewById(R.id.new_list3);
			new_list4 = (ListView) findViewById(R.id.new_list4);
			new_list5 = (ListView) findViewById(R.id.new_list5);
			new_list6 = (ListView) findViewById(R.id.new_list6);
			new_list7 = (ListView) findViewById(R.id.new_list7);
			ll_buju1 = (LinearLayout) findViewById(R.id.ll_buju1);
			ll_buju2 = (LinearLayout) findViewById(R.id.ll_buju2);
			ll_buju3 = (LinearLayout) findViewById(R.id.ll_buju3);
//			ll_buju4 = (LinearLayout) findViewById(R.id.ll_buju4);
			ll_dierbuju1 = (LinearLayout) findViewById(R.id.ll_dierbuju1);
			ll_dierbuju2 = (LinearLayout) findViewById(R.id.ll_dierbuju2);
			ll_dierbuju3 = (LinearLayout) findViewById(R.id.ll_dierbuju3);
			ll_dierbuju4 = (LinearLayout) findViewById(R.id.ll_dierbuju4);
			ll_dierbuju5 = (LinearLayout) findViewById(R.id.ll_dierbuju5);
			ll_dierbuju6 = (LinearLayout) findViewById(R.id.ll_dierbuju6);
			ll_dierbuju7 = (LinearLayout) findViewById(R.id.ll_dierbuju7);
			ll_quanbu = (LinearLayout) findViewById(R.id.ll_quanbu);
			iv_jyh1 = (ImageView) findViewById(R.id.iv_jyh1);
			iv_jyh2 = (ImageView) findViewById(R.id.iv_jyh2);
			iv_jyh3 = (ImageView) findViewById(R.id.iv_jyh3);
			iv_jyh4 = (ImageView) findViewById(R.id.iv_jyh4);
			ll_buju1.setOnClickListener(this);
			ll_buju2.setOnClickListener(this);
			ll_buju3.setOnClickListener(this);
//			ll_buju4.setOnClickListener(this);
//			ll_dierbuju.setVisibility(View.GONE);
			
			
//			System.out.println("---------summary-------------"+getIntent().getStringExtra("summary") );
//			System.out.println("---------cause-------------"+getIntent().getStringExtra("cause") );
//			System.out.println("---------doctor-------------"+getIntent().getStringExtra("doctor") );
//			System.out.println("---------proposal-------------"+getIntent().getStringExtra("proposal") );
			if (!getIntent().getStringExtra("summary").equals("")) {
				tv_ticket.setText(getIntent().getStringExtra("summary"));
			}else {
				tv_ticket.setVisibility(View.GONE);
			}
			
			if (!getIntent().getStringExtra("cause").equals("")) {
				tv_shop_ticket.setText(getIntent().getStringExtra("cause"));
			}else {
				ll_buju1.setVisibility(View.GONE);
			}
			
			if (!getIntent().getStringExtra("doctor").equals("")) {
				tv_jifen_ticket.setText(getIntent().getStringExtra("doctor"));
			}else {
				ll_buju2.setVisibility(View.GONE);
			}
			
			if (!getIntent().getStringExtra("proposal").equals("")) {
				tv_djjifen_ticket.setText(getIntent().getStringExtra("proposal"));
			}else {
				ll_buju3.setVisibility(View.GONE);
			}
			
			try {
			
			if (getIntent().getStringExtra("num").equals("1")) {
					
			list = (ArrayList<GuigellBean>)getIntent().getSerializableExtra("list");
				
				System.out.println("--------- list.size()-------------"+ list.size());
//				System.out.println("--------- list-------------"+ list.get(0).getImg_url());
//			System.out.println("--------- list-------------"+ list.get(0).getTitle());
			if (list.size() == 0) {
				ll_dierbuju1.setVisibility(View.GONE);
				ll_dierbuju2.setVisibility(View.GONE);
				ll_dierbuju3.setVisibility(View.GONE);
				ll_dierbuju4.setVisibility(View.GONE);
				ll_dierbuju5.setVisibility(View.GONE);
				ll_dierbuju6.setVisibility(View.GONE);
				ll_dierbuju7.setVisibility(View.GONE);
			}else {
				for (int i = 0; i < list.size(); i++) {
					System.out.println("item_id================="+list.get(i).item_id);
					item_id = list.get(i).item_id;
//					article_id = list.get(i).article_id;
					list_id = new ArrayList<String>();
					list_tiem = new ArrayList<String>();
					list_tupian = new ArrayList<String>();
//					if (item_id.equals("1")) {
//						list_tiem.add(list.get(i).title);
//					}if (item_id.equals("3")){
//						System.out.println("list.get(i).title================="+list.get(i).title);
//						list_tiem.add(list.get(i).title);
//						System.out.println("list_tiem========3========="+list_tiem.size());
//					}
					
				if (item_id.equals("1")) {
//					list_id.add(list.get(i).article_id);
					article_id = list.get(i).article_id;
					list_tiem.add(list.get(i).title);
					list_tupian.add(list.get(i).img_url);
					System.out.println("========2========="+list.get(i).article_id);
					GoodsLieAdapter adapter = new GoodsLieAdapter(list_tiem,list_tupian,SymptomsJieShaoActivity.this, imageLoader);
					new_list1.setAdapter(adapter);
					setListViewHeightBasedOnChildren(new_list1);
					ll_dierbuju1.setVisibility(View.VISIBLE);
					new_list1.setOnItemClickListener(new OnItemClickListener() {
					        @Override
					        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					        	try {
									
					        	System.out.println("========1========="+article_id);
					            Intent intent= new Intent(SymptomsJieShaoActivity.this,WareInformationActivity.class);
					            intent.putExtra("id",article_id);
					            intent.putExtra("num",2);
								startActivity(intent);
								
					        	} catch (Exception e) {
									// TODO: handle exception
					        		e.printStackTrace();
								}
					        }
					});
				}else
//				{
//					ll_dierbuju1.setVisibility(View.GONE);
//				}
				
				if (item_id.equals("2")) {
					System.out.println("--------- 2-------------");
					article_id2 = list.get(i).article_id;
					list_tiem.add(list.get(i).title);
					list_tupian.add(list.get(i).img_url);
					GoodsLieAdapter adapter = new GoodsLieAdapter(list_tiem,list_tupian,SymptomsJieShaoActivity.this, imageLoader);
					new_list2.setAdapter(adapter);
					setListViewHeightBasedOnChildren(new_list2);
					ll_dierbuju2.setVisibility(View.VISIBLE);
					System.out.println("========2========="+list.get(i).article_id);
					new_list2.setOnItemClickListener(new OnItemClickListener() {
				        @Override
				        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				        	System.out.println("========2========="+article_id2);
				            Intent intent= new Intent(SymptomsJieShaoActivity.this,WareInformationActivity.class);
				            intent.putExtra("id",article_id2);
							startActivity(intent);
				        }
				    });
				}else 
				if (item_id.equals("3")){
					System.out.println("--------- 3-------------");
//					list_id.add(list.get(i).article_id);
					article_id3 = list.get(i).article_id;
					list_tiem.add(list.get(i).title);
					list_tupian.add(list.get(i).img_url);
					System.out.println("========3========="+list.get(i).article_id);
					GoodsLieAdapter adapter = new GoodsLieAdapter(list_tiem,list_tupian,SymptomsJieShaoActivity.this, imageLoader);
					new_list3.setAdapter(adapter);
					setListViewHeightBasedOnChildren(new_list3);
					ll_dierbuju3.setVisibility(View.VISIBLE);
					new_list3.setOnItemClickListener(new OnItemClickListener() {
				        @Override
				        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				        	System.out.println("========3========="+article_id3);
				            Intent intent= new Intent(SymptomsJieShaoActivity.this,WareInformationActivity.class);
				            intent.putExtra("id",article_id3);
							startActivity(intent);
				        }
				    });
				}else 
//				{
//					ll_dierbuju3.setVisibility(View.GONE);
//				}
				if (item_id.equals("4")){
					article_id4 = list.get(i).article_id;
					list_tiem.add(list.get(i).title);
					list_tupian.add(list.get(i).img_url);
					System.out.println("========4========="+list.get(i).article_id);
					GoodsLieAdapter adapter = new GoodsLieAdapter(list_tiem,list_tupian,SymptomsJieShaoActivity.this, imageLoader);
					new_list1.setAdapter(adapter);
					setListViewHeightBasedOnChildren(new_list4);
					ll_dierbuju4.setVisibility(View.VISIBLE);
					new_list4.setOnItemClickListener(new OnItemClickListener() {
				        @Override
				        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				        	System.out.println("========4========="+article_id4);
				            Intent intent= new Intent(SymptomsJieShaoActivity.this,WareInformationActivity.class);
				            intent.putExtra("id",article_id4);
							startActivity(intent);
				        }
				    });
				}else 
//				{
//					ll_dierbuju4.setVisibility(View.GONE);
//				}
				
				if (item_id.equals("5")){
					article_id5 = list.get(i).article_id;
					list_tiem.add(list.get(i).title);
					list_tupian.add(list.get(i).img_url);
					GoodsLieAdapter adapter = new GoodsLieAdapter(list_tiem,list_tupian,SymptomsJieShaoActivity.this, imageLoader);
					new_list1.setAdapter(adapter);
					setListViewHeightBasedOnChildren(new_list5);
					ll_dierbuju5.setVisibility(View.VISIBLE);
					System.out.println("========5========="+list.get(i).article_id);
					new_list5.setOnItemClickListener(new OnItemClickListener() {
				        @Override
				        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				        	System.out.println("========5========="+article_id5);
				            Intent intent= new Intent(SymptomsJieShaoActivity.this,WareInformationActivity.class);
				            intent.putExtra("id",article_id5);
							startActivity(intent);
				        }
				    });
				}else 
//				{
//					ll_dierbuju5.setVisibility(View.GONE);
//				}
				
				if (item_id.equals("6")){
					article_id6 = list.get(i).article_id;
					list_tiem.add(list.get(i).title);
					list_tupian.add(list.get(i).img_url);
					GoodsLieAdapter adapter = new GoodsLieAdapter(list_tiem,list_tupian,SymptomsJieShaoActivity.this, imageLoader);
					new_list1.setAdapter(adapter);
					setListViewHeightBasedOnChildren(new_list6);
					ll_dierbuju6.setVisibility(View.VISIBLE);
					System.out.println("========6========="+list.get(i).article_id);
					new_list6.setOnItemClickListener(new OnItemClickListener() {
				        @Override
				        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				        	System.out.println("========6========="+article_id6);
				            Intent intent= new Intent(SymptomsJieShaoActivity.this,WareInformationActivity.class);
				            intent.putExtra("id",article_id6);
							startActivity(intent);
				        }
				    });
				}else
//				{
//					ll_dierbuju6.setVisibility(View.GONE);
//				}
				
				if (item_id.equals("7")){
					System.out.println("--------- 7-------------");
					article_id7 = list.get(i).article_id;
					list_tiem.add(list.get(i).title);
					list_tupian.add(list.get(i).img_url);
					System.out.println("========7========="+list.get(i).article_id);
					GoodsLieAdapter adapter = new GoodsLieAdapter(list_tiem,list_tupian,SymptomsJieShaoActivity.this, imageLoader);
					new_list1.setAdapter(adapter);
					setListViewHeightBasedOnChildren(new_list7);
					ll_dierbuju7.setVisibility(View.VISIBLE);
					new_list7.setOnItemClickListener(new OnItemClickListener() {
				        @Override
				        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				        	System.out.println("========7========="+article_id7);
				            Intent intent= new Intent(SymptomsJieShaoActivity.this,WareInformationActivity.class);
				            intent.putExtra("id",article_id7);
							startActivity(intent);
				        }
				    });
				}
//				else {
//					ll_dierbuju7.setVisibility(View.GONE);
//				}
			}
			
			}
			}else {
				ll_quanbu.setVisibility(View.GONE);
			}
			
			
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			ImageView iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
			iv_fanhui.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					finish();
				}
			});

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ll_buju1:
			if (zhuantai1 == false) {
				tv_shop_ticket.setVisibility(View.VISIBLE);
				iv_jyh1.setBackgroundResource(R.drawable.yjf_xx);
				zhuantai1 = true;
			}else {
				tv_shop_ticket.setVisibility(View.GONE);
				iv_jyh1.setBackgroundResource(R.drawable.yjf);
				zhuantai1 = false;
			}
			break;
		case R.id.ll_buju2:
			if (zhuantai2 == false) {
				tv_jifen_ticket.setVisibility(View.VISIBLE);
				iv_jyh2.setBackgroundResource(R.drawable.yjf_xx);
				zhuantai2 = true;
			}else {
				tv_jifen_ticket.setVisibility(View.GONE);
				iv_jyh2.setBackgroundResource(R.drawable.yjf);
				zhuantai2 = false;
			}
			break;
		case R.id.ll_buju3:
			if (zhuantai3 == false) {
				tv_djjifen_ticket.setVisibility(View.VISIBLE);
				iv_jyh3.setBackgroundResource(R.drawable.yjf_xx);
				zhuantai3 = true;
			}else {
				tv_djjifen_ticket.setVisibility(View.GONE);
				iv_jyh3.setBackgroundResource(R.drawable.yjf);
				zhuantai3 = false;
			}
			break;
//		case R.id.ll_buju4:
//			break;

		default:
			break;
		}
	}

	

	Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
			case 0:
//				 
//				 gridView2.setOnItemClickListener(new OnItemClickListener() {
//				        @Override
//				        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//				           
//				            Intent intent= new Intent(MainRemenshipuActivity.this,LiebieXqPFActivity.class);
//							intent.putExtra("list",  data5.get(arg2)+"");
//							intent.putExtra("list_tz", datashipull.get(arg2)+"");
//							intent.putExtra("list_img", datalb2.get(arg2)+"");
//							intent.putExtra("list_nr", datanr2.get(arg2)+"");
//							startActivity(intent);
//				        }
//				    });
				break;

			default:
				break;
			}
		};
	};

	

	public void setListViewHeightBasedOnChildren(ListView listView) {   
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
}
