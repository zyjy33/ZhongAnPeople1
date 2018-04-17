package com.zams.www;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;
import com.android.hengyu.web.RealmName;
import com.hengyushop.dao.WareDao;
import com.hengyushop.demo.airplane.AirPlaneHomeActivity;
import com.hengyushop.demo.hotel.HotelHomeActivity;
import com.hengyushop.demo.movie.MovieHomeActivity;
import com.hengyushop.demo.train.TrainHomeActivity;
import com.hengyushop.demo.wec.NewWare;
import com.hengyushop.entity.UserRegisterData;
import com.lglottery.www.activity.LglotteryActivity;
import com.zams.www.R;

public class MenuActivity extends Fragment implements OnClickListener {

	private ImageButton btn_recharge, btn_micro_coal, btn_ware_classify,
			btn_colleation, btn_city_wide, btn_airplane, btn_lottery,
			btn_movie, img_jd, btn_hotel, btn_wec, btn_train, btn_at, demo,
			img_wide, img_tuan_three;
	private WareDao wareDao;
	private Context mContext;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View layout = inflater.inflate(R.layout.hengyu_service, null);
		wareDao = new WareDao(getActivity());
		initdata(layout);
		return layout;

	}

	private void initdata(View layout) {
		img_wide = (ImageButton) layout.findViewById(R.id.img_wide);
		demo = (ImageButton) layout.findViewById(R.id.demo);
		btn_recharge = (ImageButton) layout.findViewById(R.id.img_recharge);
		btn_micro_coal = (ImageButton) layout.findViewById(R.id.img_micro_coal);
		btn_ware_classify = (ImageButton) layout
				.findViewById(R.id.img_ware_classify);
		btn_colleation = (ImageButton) layout.findViewById(R.id.img_collection);
		btn_city_wide = (ImageButton) layout.findViewById(R.id.img_city_wide);
		btn_airplane = (ImageButton) layout.findViewById(R.id.img_airplane);
		btn_lottery = (ImageButton) layout.findViewById(R.id.img_lottery);
		btn_movie = (ImageButton) layout.findViewById(R.id.img_movie);
		img_jd = (ImageButton) layout.findViewById(R.id.img_jd);
		btn_hotel = (ImageButton) layout.findViewById(R.id.img_hotel);
		img_tuan_three = (ImageButton) layout.findViewById(R.id.img_tuan_three);
		btn_wec = (ImageButton) layout.findViewById(R.id.img_wec);
		btn_train = (ImageButton) layout.findViewById(R.id.img_train);
		// btn_at = (ImageButton) findViewById(R.id.img_at);
		img_jd.setOnClickListener(this);
		btn_city_wide.setOnClickListener(this);
		btn_colleation.setOnClickListener(this);
		btn_ware_classify.setOnClickListener(this);
		btn_recharge.setOnClickListener(this);
		btn_micro_coal.setOnClickListener(this);
		btn_airplane.setOnClickListener(this);
		btn_lottery.setOnClickListener(this);
		btn_movie.setOnClickListener(this);
		btn_hotel.setOnClickListener(this);
		btn_wec.setOnClickListener(this);
		btn_train.setOnClickListener(this);
		demo.setOnClickListener(this);
		img_tuan_three.setOnClickListener(this);
		img_wide.setOnClickListener(this);
		// btn_at.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		List<UserRegisterData> list = wareDao.findisLogin();
		switch (v.getId()) {
			case R.id.img_tuan_three:
				UserRegisterData data1 = wareDao.findByChat();
				if (data1.getHengyuCode() == null) {
					Toast.makeText(getActivity(), "请登录", 200).show();
				} else {
					String hengyucode1 = data1.getHengyuCode().toString();
					String password1 = data1.getPassword().toString();
					Intent intent123 = new Intent(getActivity(),
							LglotteryActivity.class);
					Bundle bundle1 = new Bundle();
					bundle1.putString("userName", hengyucode1);
					bundle1.putString("passWord", password1);
					// bundle.putString("url", "http://www.hengyushop.com/");
					intent123.putExtras(bundle1);
					startActivity(intent123);
				}
				break;
			case R.id.img_jd:
				Intent intent_jd = new Intent(getActivity(), JDActivity.class);
				startActivity(intent_jd);
				break;
			case R.id.img_recharge:
				Intent intent1 = new Intent(getActivity(), RechargeActivity.class);
				startActivity(intent1);
				break;
			case R.id.img_ware_classify:
				Intent intent2 = new Intent(getActivity(), NewWare.class);
				startActivity(intent2);
				break;
			case R.id.img_collection:
				if (list.size() != 0) {
					Intent intent3 = new Intent(getActivity(),
							CollectWareActivity.class);
					startActivity(intent3);
				} else {
					int login = 0;
					Intent intent = new Intent(getActivity(),
							UserLoginActivity.class);
					intent.putExtra("login", login);
					startActivity(intent);
				}
				break;
			case R.id.img_city_wide:
			/*
			 * Intent intent20 = new Intent(getActivity(),
			 * PostMainActivity.class); startActivity(intent20);
			 */

				break;
			case R.id.img_micro_coal: // 进入聊天界面

				if (list.size() != 0) {
					UserRegisterData data = wareDao.findByChat();
					String hengyucode = data.getHengyuCode().toString();
					String password = data.getPassword().toString();

					Intent intent = new Intent();
					Bundle bundle = new Bundle();
					bundle.putString("userName", hengyucode);
					bundle.putString("passWord", password);
					bundle.putString("url", RealmName.REALM_NAME + "/");
					intent.putExtras(bundle);
					intent.setAction("cn.welltalk.www");
					ComponentName cn = new ComponentName("cn.welltalk",
							"cn.welltalk.activity.LoginActivity");
					intent.setComponent(cn);
					try {
						startActivity(intent);
					} catch (ActivityNotFoundException e) {
						e.printStackTrace();
						// 如果程序未安装 跳过来安装文件
						if (copyApkFromAssets(getActivity(), "WellTalk.apk",
								Environment.getExternalStorageDirectory()
										.getAbsolutePath() + "/WellTalk.apk")) {
							Intent intent5 = new Intent(Intent.ACTION_VIEW);
							intent5.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							intent5.setDataAndType(Uri.parse("file://"
											+ Environment.getExternalStorageDirectory()
											.getAbsolutePath() + "/WellTalk.apk"),
									"application/vnd.android.package-archive");
							mContext.startActivity(intent5);
						}
					}
				} else {
					int login = 0;
					Intent intent = new Intent(getActivity(),
							UserLoginActivity.class);
					intent.putExtra("login", login);
					startActivity(intent);
				}

				break;
			case R.id.demo:
			/*
			 * // zhongguo.jinsheng
			 *
			 * Intent intent122 = new Intent(); Bundle bundle = new Bundle(); //
			 * bundle.putString("userName", "117683411"); //
			 * bundle.putString("passWord", "0"); // bundle.putString("url",
			 * "http://www.hengyushop.com/"); intent122.putExtras(bundle); //
			 * intent122.setAction("zhongguo.jinsheng"); ComponentName cn = new
			 * ComponentName("zhongguo.sqwtxzs",
			 * "zhongguo.sqwtxzs.nnnview.New_Open"); intent122.setComponent(cn);
			 * try { startActivity(intent122); } catch
			 * (ActivityNotFoundException e) { // TODO: handle exception
			 *
			 * // TODO Auto-generated catch block e.printStackTrace();
			 *
			 * // 如果程序未安装 跳过来安装文件 if (copyApkFromAssets(getActivity(),
			 * "jinsheng.apk", Environment.getExternalStorageDirectory()
			 * .getAbsolutePath() + "/jinsheng.apk")) { Intent intent5 = new
			 * Intent(Intent.ACTION_VIEW);
			 * intent5.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			 * intent5.setDataAndType(Uri.parse("file://" +
			 * Environment.getExternalStorageDirectory() .getAbsolutePath() +
			 * "/jinsheng.apk"), "application/vnd.android.package-archive");
			 * startActivity(intent5); }
			 *
			 * }
			 */

				Toast.makeText(getActivity(), "正在对接中，后续开放", 200).show();
				break;
			case R.id.img_airplane:// 机票
				Intent intent6 = new Intent(getActivity(),
						AirPlaneHomeActivity.class);
				startActivity(intent6);
				break;
			case R.id.img_lottery:
			/*
			 * Intent intent7 = new Intent(getActivity(),
			 * LotteryMainActivity.class); startActivity(intent7);
			 */
				Toast.makeText(getActivity(), "正在对接中，后续开放", 200).show();
				break;
			case R.id.img_wide:
				Intent intent22 = new Intent(getActivity(),
						WideMarketActivity.class);
				startActivity(intent22);
				break;
			case R.id.img_movie:
				Intent intent8 = new Intent(getActivity(), MovieHomeActivity.class);
				startActivity(intent8);
				break;
			case R.id.img_hotel:
				Intent intent9 = new Intent(getActivity(), HotelHomeActivity.class);
				startActivity(intent9);
				break;

			case R.id.img_train:
				Intent intent12 = new Intent(getActivity(), TrainHomeActivity.class);
				startActivity(intent12);
				break;
		/*
		 * case R.id.img_at: Intent intent13 = new Intent(getActivity(),
		 * ATHomeActivity.class); startActivity(intent13); break;
		 */
			default:
				break;
		}
	}

	// 将安装的聊天软件存至SD卡上面
	public boolean copyApkFromAssets(Context context, String fileName,
									 String path) {
		boolean copyIsFinish = false;
		try {
			InputStream is = context.getAssets().open(fileName);
			File file = new File(path);
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			byte[] temp = new byte[1024];
			int i = 0;
			while ((i = is.read(temp)) > 0) {
				fos.write(temp, 0, i);
			}
			fos.close();
			is.close();
			copyIsFinish = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return copyIsFinish;
	}

}
