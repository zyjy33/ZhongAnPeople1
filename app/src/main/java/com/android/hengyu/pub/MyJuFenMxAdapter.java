package com.android.hengyu.pub;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.hengyushop.entity.MyJuFenData;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.R;

import java.util.ArrayList;
import java.util.List;

public class MyJuFenMxAdapter extends BaseAdapter {

	private List<MyJuFenData> list;
	private ArrayList<String> list_avatar;
	private ArrayList datadz1;
	private Context context;
	private ImageLoader loader;
	public static AQuery aQuery;
	private LayoutInflater mInflater;
	String img_url = null;
	public boolean tpye = false;
	String haoma;

	public MyJuFenMxAdapter(List<MyJuFenData> list, ArrayList datadz1,
							Context context, ImageLoader loader) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list = list;
		this.datadz1 = datadz1;
		// this.list_avatar = list_avatar;
		this.loader = loader;
		mInflater = LayoutInflater.from(context);
		aQuery = new AQuery(context);
	}

	// public int getCount() {
	// // TODO Auto-generated method stub
	// return list.size();
	// }
	//
	// public Object getItem(int position) {
	// // TODO Auto-generated method stub
	// return position;
	// }

	@Override
	public int getCount() {
		if (list.size() < 1) {

			return 0;
		} else {

			return list.size();
		}
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			// convertView =
			// LinearLayout.inflate(context,R.layout.listitem_my_jufen, null);
			convertView = mInflater.inflate(R.layout.listitem_my_jufen, null);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.image = (ImageView) convertView.findViewById(R.id.img_ware);
		holder.tv_name = (TextView) convertView.findViewById(R.id.tv_ware_name);
		TextView tv_tiem = (TextView) convertView.findViewById(R.id.tv_tiem);
		// TextView tv_price = (TextView)
		// convertView.findViewById(R.id.tv_ware_price);
		// TextView tv_total = (TextView)
		// convertView.findViewById(R.id.tv_ware_total);

		String real_name = list.get(position).real_name;//
		String nick_name = list.get(position).nick_name;//
		// System.out.println("real_name====================="+real_name);
		// System.out.println("nick_name====================="+nick_name);
		try {

			String haoma_ll = list.get(position).mobile;
			haoma = haoma_ll.substring(0, 3) + "****"
					+ haoma_ll.substring(7, 11);
			System.out.println("haoma=====================" + haoma);
			if (!real_name.equals("")) {
				holder.tv_name.setText(real_name + "（" + haoma + ")");
			} else if (!nick_name.equals("")) {
				holder.tv_name.setText(nick_name + "（" + haoma + ")");
			} else {
				holder.tv_name.setText("匿名用户（" + haoma + ")");
			}

			if (list.get(position).audit_time != null) {
				tv_tiem.setText(list.get(position).audit_time);
			}else {
				tv_tiem.setText(list.get(position).reg_time);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		System.out.println("================================" + position);

		// if (!list.get(position).avatar.equals("")) {
		// String data_tx = (String) list.get(position).avatar;
		// System.out.println("data_tx================================"+data_tx);
		// // String img_url =
		// "http://mobile.ju918.com/templates/mobile/images/leader-user.png";//默认图片
		// // img_url = null;
		// if (data_tx.contains("http")) {
		// System.out.println("================================http");
		// // tpye = true;
		// img_url = list.get(position).avatar;
		// // if (tpye == true) {
		// // tpye = false;
		// // aQuery.id(holder.image).image(data_tx);
		// // }
		// } else if(data_tx.contains("upload")){
		// img_url = RealmName.REALM_NAME_HTTP+list.get(position).avatar;
		// // aQuery.id(holder.image).image(img_url);
		// }
		// aQuery.id(holder.image).image(img_url);
		// }

		try {
			String data_tx = (String) datadz1.get(position);
			System.out.println("data_tx================================"
					+ data_tx);
			if (data_tx.equals("")) {
				holder.image.setBackgroundResource(R.drawable.app_zams);
			} else {
				aQuery.id(holder.image).image(data_tx);
			}

			// if (!data_tx.equals("")) {
			// System.out.println("================================加载图片");
			// aQuery.id(holder.image).image(data_tx);
			// }if (data_tx.equals("null")) {
			// System.out.println("================================图片空值");
			// }

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		// String data_tx = (String) list.get(position).avatar;
		// aQuery.id(holder.image).image(data_tx);

		// if(!TextUtils.isEmpty(pNumber) && pNumber.length() > 6 ){
		// StringBuilder sb =new StringBuilder();
		// for (int i = 0; i < pNumber.length(); i++) {
		// char c = pNumber.charAt(i);
		// if (i >= 3 && i <= 6) {
		// sb.append('*');
		// } else {
		// sb.append(c);
		// }
		// }
		//
		// mPhoneNumber.setText(sb.toString());
		// }
		return convertView;
	}

	public class ViewHolder {
		ImageView image;
		TextView tv_name;
	}
}
