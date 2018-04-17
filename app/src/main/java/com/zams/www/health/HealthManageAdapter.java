package com.zams.www.health;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zams.www.R;

import java.util.ArrayList;
import java.util.List;

public class HealthManageAdapter extends BaseAdapter{
	private Context mContext;
	private List<HealthManagerModel> mList;
	private LayoutInflater mInflater;

	public HealthManageAdapter(Context context, List<HealthManagerModel> list) {
		this.mContext = context;
		mInflater = LayoutInflater.from(mContext);
		if(list == null){
			this.mList = new ArrayList<HealthManagerModel>();
		}else{
			this.mList = list;
		}

	}

	@Override
	public int getCount() {
		return mList==null ? 0 :mList.size();
	}

	@Override
	public HealthManagerModel getItem(int arg0) {
		return mList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View conterView, ViewGroup parentView) {
		ViewHolder viewHolder;
		if (conterView == null) {
			conterView = mInflater.inflate(R.layout.health_manage_list_item, null);
			viewHolder = new ViewHolder(conterView);
			conterView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) conterView.getTag();
		}
		/*
		 * 加载数据/
		 */
		HealthManagerModel modle=mList.get(position);
		viewHolder.name.setText(modle.getCompany_name());
		viewHolder.content.setText("已有"+modle.getCount()+"位客户购买了体检项项目");
		return conterView;
	}

	static class ViewHolder{
		ImageView pic;
		TextView name,content;
		public ViewHolder(View view) {
			pic = (ImageView) view.findViewById(R.id.pic);
			name = (TextView) view.findViewById(R.id.name);
			content = (TextView) view.findViewById(R.id.content);
		}
	}

	public void upData(List<HealthManagerModel> healthData) {
		if(healthData!=null&& healthData.size()>0){
			mList.clear();
			mList.addAll(healthData);
			this.notifyDataSetChanged();
		}
	}

}
