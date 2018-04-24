package com.hengyushop.demo.home;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.hengyu.web.RealmName;
import com.hengyushop.airplane.adapter.MyGridAdapter;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.wec.NewWare;
import com.hengyushop.entity.WareData;
import com.hengyushop.entity.WareDatall;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.R;

public class CommomConfrim extends Activity {
	private static GridView gridView;
	private static ArrayList<WareDatall> listll;
	private static int INDX = -1;
	private static Activity context;
	private static ArrayList data2;
	private static ArrayList data_id2;
	static MyGridAdapter arrayadapter;
	public static String sp_id;

	public interface onDeleteSelect {
		void onClick(String resID);

	}

	/**
	 * 二级类别
	 */
	private CommomConfrim() {
		final Dialog dlg;
	}

	public static Dialog showSheet(final Context context,
								   final onDeleteSelect cancelListener, final int id) {
		final Dialog dlg = new Dialog(context, R.style.delete_pop_style);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout layout = (LinearLayout) inflater.inflate(
				R.layout.common_pay_pop_leibie, null);
		final int cFullFillWidth = 10000;
		final int cFullFillWidthll = 10000;
		layout.setMinimumWidth(cFullFillWidth);
		layout.setMinimumHeight(cFullFillWidthll);

		layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				dlg.dismiss();
			}
		});
		gridView = (GridView) layout.findViewById(R.id.gridView);
		System.out.println("111111===============" + id);

		data2 = new ArrayList();
		data_id2 = new ArrayList();
		listll = new ArrayList<WareDatall>();
		AsyncHttp.get(RealmName.REALM_NAME_LL + "/get_category_child_list?"
						+ "channel_name=life&parent_id=" + id + "",
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {

						super.onSuccess(arg0, arg1);
						data2 = new ArrayList();
						data_id2 = new ArrayList();
						listll = new ArrayList<WareDatall>();
						try {
							System.out
									.println("=====第二层数据====================="
											+ arg1);
							JSONObject object = new JSONObject(arg1);
							String status = object.getString("status");
							if (status.equals("y")) {
								JSONArray jsonArray = object
										.getJSONArray("data");
								for (int i = 0; i < jsonArray.length(); i++) {
									JSONObject obj = jsonArray.getJSONObject(i);
									WareDatall datall = new WareDatall();
									WareDatall dm = new WareDatall();
									dm.setId(obj.getString("id"));
									String title = obj.getString("title");
									datall.id = obj.getString("id");
									String id = obj.getString("id");//
									INDX = Integer.parseInt(id);
									data_id2.add(id);
									data2.add(title);
									listll.add(dm);

									System.out
											.println("=====循环的id====================="
													+ data2);
									// load_list(INDX, true);
								}

								// gridView.setVisibility(View.VISIBLE);
								System.out
										.println("=====11====================="
												+ data2);

								// arrayadapter = new
								// MyGridAdapter(data2,context);
								// gridView.setAdapter(arrayadapter);
								System.out
										.println("=====22=====================");

								gridView.setOnItemClickListener(new OnItemClickListener() {
									@Override
									public void onItemClick(
											AdapterView<?> arg0, View arg1,
											int arg2, long arg3) {
										arrayadapter.setSeclection(arg2);
										arrayadapter.notifyDataSetChanged();
										// String id = listll.get(arg2).getId();
										// System.out.println("=====第二层数据11====================="+id);
										// INDX = Integer.parseInt(id);
										// load_list(INDX, true);
										// String id = String.valueOf(arg2);
										System.out
												.println("=====第三层数据id11====================="
														+ arg2);
										// Intent intent = new
										// Intent(context,NewWare.class);
										// intent.putExtra("id", id);
										String list_id = listll.get(arg2)
												.getId();
										// String list = String.valueOf(id);
										System.out
												.println("=====第三层数据id11====================="
														+ list_id);
										sp_id = list_id;
										System.out
												.println("=====第三层数据id22====================="
														+ sp_id);
										// context.startActivity(intent);
										dlg.dismiss();
									}
								});

							} else {
								gridView.setVisibility(View.GONE);
								System.out
										.println("=====第二层数据2====================="
												+ INDX);
								// load_list(INDX, true);
							}

						} catch (JSONException e) {

							e.printStackTrace();
						}
					}
				}, null);
		// formatWeather(id);

		Window w = dlg.getWindow();
		WindowManager.LayoutParams lp = w.getAttributes();
		lp.x = 0;
		final int cMakeBottom = -1000;
		lp.y = cMakeBottom;
		lp.gravity = Gravity.BOTTOM;
		dlg.onWindowAttributesChanged(lp);
		dlg.setCanceledOnTouchOutside(true);

		dlg.setContentView(layout);
		dlg.show();
		return dlg;
	}

	private void formatWeather(int id) {
		data2 = new ArrayList();
		data_id2 = new ArrayList();
		listll = new ArrayList<WareDatall>();
		AsyncHttp.get(RealmName.REALM_NAME_LL + "/get_category_child_list?"
						+ "channel_name=life&parent_id=" + id + "",
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {

						super.onSuccess(arg0, arg1);
						data2 = new ArrayList();
						data_id2 = new ArrayList();
						listll = new ArrayList<WareDatall>();
						try {
							System.out
									.println("=====第二层数据====================="
											+ arg1);
							JSONObject object = new JSONObject(arg1);
							String status = object.getString("status");
							if (status.equals("y")) {
								JSONArray jsonArray = object
										.getJSONArray("data");
								for (int i = 0; i < jsonArray.length(); i++) {
									JSONObject obj = jsonArray.getJSONObject(i);
									WareDatall datall = new WareDatall();
									// dm = new WareDatall();
									// dm.setId(obj.getString("id"));
									String title = obj.getString("title");
									datall.id = obj.getString("id");
									String id = obj.getString("id");//
									INDX = Integer.parseInt(id);
									data_id2.add(id);
									data2.add(title);
									// listll.add(dm);

									System.out
											.println("=====循环的id====================="
													+ data2);
									// load_list(INDX, true);
								}

								// gridView.setVisibility(View.VISIBLE);
								System.out
										.println("=====11====================="
												+ data2);

								// MyGridAdapter arrayadapter = new
								// MyGridAdapter(data2,context);
								// gridView.setAdapter(arrayadapter);
								System.out
										.println("=====22=====================");

								// gridView.setOnItemClickListener(new
								// OnItemClickListener() {
								// @Override
								// public void onItemClick(AdapterView<?> arg0,
								// View arg1, int arg2, long arg3) {
								// // flag = false;
								// String id = listll.get(arg2).getId();
								// //
								// System.out.println("=====第二层数据11====================="+id);
								// INDX = Integer.parseInt(id);
								// System.out.println("=====第二层数据1====================="+INDX);
								// load_list(INDX, true);
								//
								// arrayadapter.setSeclection(arg2);
								// arrayadapter.notifyDataSetChanged();
								//
								//
								// }
								// });

							} else {
								// // flag = true;
								gridView.setVisibility(View.GONE);
								String id = "614";
								System.out
										.println("=====第二层数据2====================="
												+ INDX);
								// load_list(INDX, true);
							}

						} catch (JSONException e) {

							e.printStackTrace();
						}
					}
				}, null);
	}

}
