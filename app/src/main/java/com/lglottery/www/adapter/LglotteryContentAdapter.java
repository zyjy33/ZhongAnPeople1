package com.lglottery.www.adapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import com.lglottery.www.common.U;
import com.lglottery.www.common.WLog;
import com.lglottery.www.domain.Lglottery_Main;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.R;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LglotteryContentAdapter extends BaseAdapter {
	private int longPx;
	private Context context;
	private int selectItem;
	private ArrayList<Lglottery_Main> lists;
	private ImageLoader imageLoader;
	private Queue<String> stack;
	private Handler handler;
	private Map<String, Queue<String>> maps;

	public LglotteryContentAdapter(int longPx, Context context,
			ArrayList<Lglottery_Main> lists, ImageLoader imageLoader,
			Handler handler, Map<String, Queue<String>> maps) {
		this.longPx = longPx;
		this.context = context;
		this.lists = lists;
		this.imageLoader = imageLoader;
		this.handler = handler;
		this.maps = maps;
	}

	public void setSelectItem(int selectItem) {
		this.selectItem = selectItem;
		// add(stack, selectItem);
	}

	public void add(Queue<String> stack, String i) {
		Iterator<String> it = stack.iterator();
		if (!it.hasNext()) {
			// ��һ��
			System.out.println("?");
			stack.add(i);
//			stack.offer(new Integer(i));
		} else {
			if (!isContains(it, i)) {
				//
				WLog.v("���һ������ֵ");
				stack.add(i);
				if (stack.size() > 2) {
					stack.poll();
				}

			}
		}
		Iterator<String> it1 = stack.iterator();
		for (int j = 0; j < lists.size(); j++) {
			// ��ԭ����ӛ�
			lists.get(j).setClicked(false);
		}
		while (it1.hasNext()) {
			String k = it1.next();
			WLog.v("while��ֵ" + k);
			for (int j = 0; j < lists.size(); j++) {
				if (k.equals(j)) {
					WLog.v("����" + j + "��λ");
					lists.get(j).setClicked(true);
					// this.notifyDataSetChanged();
				}
			}

		}
		maps.put(lists.get(0).getTypeId(), stack);
		Message msg = new Message();
		msg.what = 1;
		msg.obj = lists;
		handler.sendMessage(msg);
	}

	/**
	 * ������������ֵ
	 * 
	 * @param it
	 * @param i
	 * @return
	 */
	private boolean isContains(Iterator<String> it, String i) {
		boolean flag = false;
		while (it.hasNext()) {
			if (it.next().equals(i)) {
				WLog.v(i + "������");
				// ����
				flag = true;
			} else {
				WLog.v(i + "������");
				flag = false;
			}
		}
		return flag;
	}

	/**
	 * ���ڼ����ı���״̬
	 * @param stack
	 * @param i
	 */

	/**
	 * add a new information
	 * @param lists
	 */
	public void putLists(ArrayList<Lglottery_Main> lists) {
		try {
			// ��֤��������ֵ
			if (lists.size() != 0) {
				Iterator<String> iterator = maps.get(lists.get(0).getTypeId())
						.iterator();
				while (iterator.hasNext()) {
					String status = iterator.next();//ID����
					WLog.v("status"+status);
					for(int i=0;i<lists.size();i++){
						if(lists.get(i).getItemId().equals(status)){
							lists.get(i).setClicked(true);
						}
					}
					
				}
			}
		} catch (NullPointerException e) {
			WLog.v("��ָ���쳣");
			e.printStackTrace();
		}
		WLog.v("�������");
		this.selectItem = -1;
		this.lists = lists;
		this.notifyDataSetChanged();
	}

	/**
	 * ���ö���
	 */
	public void reloadQueen() {
		stack = new LinkedList<String>();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lists.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return lists.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View view, ViewGroup arg2) {
		// TODO Auto-generated method stub
		final ViewHolder holder;
		if (view == null
				|| view.getTag(R.drawable.ic_launcher + position) == null) {
			holder = new ViewHolder();
			view = LinearLayout.inflate(context,
					R.layout.lglottery_content_item, null);

			view.setLayoutParams(new AbsListView.LayoutParams(longPx, longPx));
			holder.lglottery_item_goods = (FrameLayout) view
					.findViewById(R.id.lglottery_item_goods);
			holder.lglottery_item_cover = (LinearLayout) view
					.findViewById(R.id.lglottery_item_cover);
			holder.lglottery_main_text = (TextView) view
					.findViewById(R.id.lglottery_main_text);
			holder.lglottery_main_image = (ImageView) view
					.findViewById(R.id.lglottery_main_image);
			view.setTag(R.drawable.ic_launcher + position);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		holder.lglottery_main_text.setText(lists.get(position).getName());
		imageLoader.displayImage(U.IP +"admin/"+ lists.get(position).getImage(),
				holder.lglottery_main_image);
		if (lists.get(position).isClicked()) {
			holder.lglottery_item_cover.setVisibility(View.VISIBLE);
		} else {
			holder.lglottery_item_cover.setVisibility(View.INVISIBLE);
		}
		if (selectItem == position) {
			WLog.v("�����һ��");
			add(stack, lists.get(position).getItemId());
			// LglotteryContentAdapter.this.notifyDataSetChanged();
		}
		return view;
	}

	public class ViewHolder {
		public FrameLayout lglottery_item_goods;
		public LinearLayout lglottery_item_cover;
		private TextView lglottery_main_text;
		private ImageView lglottery_main_image;
	}

}
