package com.android.hengyu.pub;

import java.io.File;
import java.util.ArrayList;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.hengyu.web.RealmName;
import com.androidquery.AQuery;
import com.ctrip.openapi.java.utils.GetImgUtil;
import com.example.taobaohead.headview.RoundImageView;
import com.example.uploadpicdemo.Utils;
import com.hengyushop.entity.JuTuanGouData;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.zams.www.R;

public class GuaYiGuaAdapter extends BaseAdapter {

	private ArrayList<JuTuanGouData> list;
	private ArrayList<Integer> list_num;
	private ArrayList datadz2;
	private Context context;
	public AQuery mAq;
	String user_id, avatar;
	private ImageLoader mImageLoader;
	ImageView image;
	String jutuan_tx;
	int num;
	String img_url;
	Bitmap bitMap_tx;
	String data_tx;

	public GuaYiGuaAdapter(ArrayList datadz2, Context context) {
		this.context = context;
		this.datadz2 = datadz2;
		mAq = new AQuery(context);
	}

	public int getCount() {

		return datadz2.size();
	}

	public Object getItem(int position) {

		return datadz2.get(position);
		// return position;
	}

	public long getItemId(int position) {

		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = LinearLayout.inflate(context, R.layout.guayigua_item,
					null);
		}
		try {
			// image = (ImageView) convertView.findViewById(R.id.img_ware);
			// LinearLayout ll_touxiang = (LinearLayout)
			// convertView.findViewById(R.id.ll_touxiang);
			System.out.println("position=================" + position);

		} catch (Exception e) {

			e.printStackTrace();
		}

		return convertView;
	}

}
