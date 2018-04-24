package com.hengyushop.movie.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hengyushop.demo.movie.MovieCinemaSelectActivity;
import com.zams.www.R;

public class MovieHomeAdapter extends BaseAdapter {

	private List<Map<String, String>> list;
	private Context context;

	public MovieHomeAdapter(List<Map<String, String>> allGriddatas,
			Context context) {

		this.list = allGriddatas;
		this.context = context;
	}

	@Override
	public int getCount() {

		return list.size();
	}

	@Override
	public Object getItem(int arg0) {

		return arg0;
	}

	@Override
	public long getItemId(int arg0) {

		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = RelativeLayout.inflate(context,
					R.layout.movie_listitem_home, null);
		}
		TextView tv = (TextView) convertView.findViewById(R.id.tv_movie_name);
		tv.setText(list.get(position).get("text"));
		Button btn_buy = (Button) convertView.findViewById(R.id.btn_choose_buy);
		btn_buy.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(context,
						MovieCinemaSelectActivity.class);
				context.startActivity(intent);
			}
		});
		return convertView;
	}

}
