//package com.hengyushop.airplane.adapter;
//
//
//import java.util.ArrayList;
//
//import android.content.Context;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.nostra13.universalimageloader.core.ImageLoader;
//import com.zams.www.R;
//
///**
// * @Description:gridview的Adapter
// * @author http://blog.csdn.net/finddreams
// */
//public class CeshiAdapter extends BaseAdapter {
//	private Context mContext;
//	private ArrayList data_tm;
//	private Context context;
//
//	public CeshiAdapter(ArrayList data_tm, Context mContext) {
//		super();
//		System.out.println("=====================100======");
//		this.data_tm = data_tm;
//		this.mContext = mContext;
//		Log.i("data_tm", data_tm+"");
//	}
//
//	@Override
//	public int getCount() {
//
//		return data_tm.size();
//	}
//
//	@Override
//	public Object getItem(int position) {
//
//		return data_tm.get(position);
//	}
//
//	@Override
//	public long getItemId(int position) {
//
//		return position;
//	}
//
//	public class ViewHolder{  
//        TextView tv_yhwenzi;  
//        ImageView iv_yhtupian;  
//        ImageView iv_yhtupian2;
//        ImageView iv_yhtupian3;
//    }
//	private ViewHolder viewHolder;
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//
//		if(convertView==null){
//			convertView=LinearLayout.inflate(context, R.layout.yhzhishi_listview, null);
//			viewHolder = new ViewHolder();
//			viewHolder.tv_yhwenzi = (TextView)convertView.findViewById(R.id.tv_yhwenzi);  
//			convertView.setTag(viewHolder);
//		}
////        viewHolder.tv_yhwenzi.setText((String) data1.get(position)); 
//		return convertView;
//	}
//	
//
//}
//
