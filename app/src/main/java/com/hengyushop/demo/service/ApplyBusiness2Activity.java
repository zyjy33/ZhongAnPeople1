package com.hengyushop.demo.service;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.example.uploadpicdemo.Utils;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.zams.www.PersonCenterActivity;
import com.zams.www.R;
import com.zams.www.weiget.PermissionSetting;

import org.json.JSONObject;

import java.io.File;
import java.util.List;

/**
 * 申请商家2
 *
 * @author Administrator
 *
 */
public class ApplyBusiness2Activity extends BaseActivity implements OnClickListener{

	private ImageView iv_fanhui;
	private TextView tv_xiabu;
	private EditText edt_leibie,edt_sj_jianjie,edt_sj_tsys,edt_sj_fwzh,edt_sj_yyzch,edt_sj_tjr;
	private ImageView iv_personal_icon,iv_personal_icon1,iv_personal_icon2,iv_personal_icon3;
	private int zhaopian;
	private String tupian1 = "";
	private String tupian2 = "";
	private String tupian3 = "";
	private String tupian4 = "";
	protected static final int CHOOSE_PICTURE = 0;
	protected static final int TAKE_PICTURE = 1;
	private static final int CROP_SMALL_PICTURE = 2;
	protected static Uri tempUri;
	private DialogProgress progress;
	private String sj_name,sj_bq,haoma,province,city,area,dizhi,xq_dizhi;
	private SharedPreferences spPreferences;
	String user_name, user_id;
	double weidu,jingdu;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sq2_shangjia);
		getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		progress = new DialogProgress(ApplyBusiness2Activity.this);
		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		user_name = spPreferences.getString("user", "");
		user_id = spPreferences.getString("user_id", "");

		sj_name = getIntent().getStringExtra("sj_name");
		sj_bq = getIntent().getStringExtra("sj_bq");
		haoma = getIntent().getStringExtra("haoma");
		//		dizhi = getIntent().getStringExtra("dizhi");
		province = getIntent().getStringExtra("province");
		city = getIntent().getStringExtra("city");
		area = getIntent().getStringExtra("area");
		xq_dizhi = getIntent().getStringExtra("xq_dizhi");
		//double
		jingdu = ApplyBusiness1Activity.jingdu;
		weidu = ApplyBusiness1Activity.weidu;
		System.out.println("sj_name--------------------"+jingdu);
		initUI();
	}

	private void initUI() {

		edt_leibie = (EditText) findViewById(R.id.edt_leibie);
		edt_sj_jianjie = (EditText) findViewById(R.id.edt_sj_jianjie);
		edt_sj_tsys = (EditText) findViewById(R.id.edt_sj_tsys);
		edt_sj_fwzh = (EditText) findViewById(R.id.edt_sj_fwzh);
		edt_sj_yyzch = (EditText) findViewById(R.id.edt_sj_yyzch);
		edt_sj_tjr = (EditText) findViewById(R.id.edt_sj_tjr);
		iv_personal_icon = (ImageView) findViewById(R.id.iv_personal_icon);
		iv_personal_icon1 = (ImageView) findViewById(R.id.iv_personal_icon1);
		iv_personal_icon2 = (ImageView) findViewById(R.id.iv_personal_icon2);
		iv_personal_icon3 = (ImageView) findViewById(R.id.iv_personal_icon3);

		iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
		tv_xiabu = (TextView) findViewById(R.id.tv_xiabu);
		iv_fanhui.setOnClickListener(this);
		tv_xiabu.setOnClickListener(this);
		iv_personal_icon.setOnClickListener(this);
		iv_personal_icon1.setOnClickListener(this);
		iv_personal_icon2.setOnClickListener(this);
		iv_personal_icon3.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {


		switch (v.getId()) {
			case R.id.iv_fanhui:
				finish();
				break;
			case R.id.iv_personal_icon:
				showChoosePicDialog();
				zhaopian = 1;
				break;
			case R.id.iv_personal_icon1:
				showChoosePicDialog();
				zhaopian = 2;
				break;
			case R.id.iv_personal_icon2:
				showChoosePicDialog();
				zhaopian = 3;
				break;
			case R.id.iv_personal_icon3:
				showChoosePicDialog();
				zhaopian = 4;
				break;
			case R.id.tv_xiabu:
				try {
					String leibie = edt_leibie.getText().toString().trim();
					String sj_jianjie = edt_sj_jianjie.getText().toString().trim();
					String ts_youshi = edt_sj_tsys.getText().toString().trim();
					String fwgh = edt_sj_fwzh.getText().toString().trim();
					String zhucehao = edt_sj_yyzch.getText().toString().trim();
					String tjr_haoma = edt_sj_tjr.getText().toString().trim();

					if (leibie.equals("")) {
						Toast.makeText(ApplyBusiness2Activity.this, "请填写商家类别", 100).show();
					} else if (sj_jianjie.equals("")) {
						Toast.makeText(ApplyBusiness2Activity.this, "请填写商家简介", 100).show();
					} else if (sj_jianjie.length() > 500) {
						Toast.makeText(ApplyBusiness2Activity.this, "内容不能大于500字", 100).show();
					} else if (ts_youshi.equals("")) {
						Toast.makeText(ApplyBusiness2Activity.this, "请填写特色优势", 100).show();
					} else if (ts_youshi.length() > 500) {
						Toast.makeText(ApplyBusiness2Activity.this, "内容不能大于500字", 100).show();
					} else if (fwgh.equals("")) {
						Toast.makeText(ApplyBusiness2Activity.this, "请填写服务工号", 100).show();
					} else if (tupian1.equals("")) {
						Toast.makeText(ApplyBusiness2Activity.this, "请上传商家Logo", 100).show();
					} else if (tupian2.equals("")) {
						Toast.makeText(ApplyBusiness2Activity.this, "请上传商家资质", 100).show();
					} else if (zhucehao.equals("")) {
						Toast.makeText(ApplyBusiness2Activity.this, "请填写营业执照注册号", 100).show();
					} else if (tupian3.equals("")) {
						Toast.makeText(ApplyBusiness2Activity.this, "请上传税务登记证", 100).show();
					} else if (tupian4.equals("")) {
						Toast.makeText(ApplyBusiness2Activity.this, "请上传组织机构代码证", 100).show();
					} else if (tjr_haoma.equals("")) {
						Toast.makeText(ApplyBusiness2Activity.this, "请填写您的推荐人号码", 100).show();
					} else if (tjr_haoma.length() > 11 || tjr_haoma.length() < 11) {
						Toast.makeText(ApplyBusiness2Activity.this, "手机号码不对", 100).show();
					} else{
						progress.CreateProgress();
						String address = dizhi +"、"+xq_dizhi;
						//				Toast.makeText(ApplyBusiness2Activity.this, "升级成功", 100).show();
						//sj_name,sj_bq,haoma,dizhi,xq_dizhi;
						try {
							String strUrlone = RealmName.REALM_NAME_LL

									+ "/add_user_commpany?user_id="+user_id+"&user_name="+user_name+"&trade_id=1&name="+sj_name+"&content="+sj_jianjie+"&artperson=&contact=" +
									"&mobile="+haoma+"&tel=&nature="+leibie+"&post_code=&email=&address="+xq_dizhi+"&sort_id=1&logo_url="+tupian1+"&img_url=" +
									"&seo_title=&seo_keywords=&seo_description=&province="+province+"&city="+city+"&area="+area+"&regtime=&lng="+jingdu+"" +
									"&lat="+weidu+"&advantage="+ts_youshi+"&idcard_a=&idcard_b=&license="+tupian2+"&accredit=&aptitude=&revenue_card="+tupian3+
									"&organi_card="+tupian4+"&brand_card=&licence_card=&trade_aptitude=&account_name=&bank_name=&bank_account=" +
									"&registeredid="+zhucehao+"";


							System.out.println("===================" + strUrlone);

							AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
								@Override
								public void onSuccess(int arg0, String arg1) {

									super.onSuccess(arg0, arg1);
									try {
										JSONObject jsonObject = new JSONObject(arg1);
										System.out.println("=================1=="
												+ arg1);

										String status = jsonObject.getString("status");
										System.out.println("status: " + status);
										String info = jsonObject.getString("info");
										if (status.equals("y")) {
											Toast.makeText(ApplyBusiness2Activity.this,info, 200).show();
											progress.CloseProgress();
											// finish();
											//									handler.sendEmptyMessage(0);
										} else if (status.equals("n")) {
											Toast.makeText(ApplyBusiness2Activity.this,info, 200).show();
											progress.CloseProgress();
										}

									} catch (Exception e) {

										e.printStackTrace();
									}

								}
							}, null);
						} catch (Exception e1) {

							e1.printStackTrace();
						}
					}


				} catch (Exception e) {

					e.printStackTrace();
				}
				break;

			default:
				break;
		}
	}

	/**
	 * 显示修改头像的对话框
	 */
	protected void showChoosePicDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("设置头像");
		String[] items = { "选择本地照片", "拍照" };
		builder.setNegativeButton("取消", null);
		builder.setItems(items, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
					case CHOOSE_PICTURE: // 选择本地照片
						Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
						openAlbumIntent.setType("image/*");
						startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
						break;
					case TAKE_PICTURE: // 拍照

						AndPermission.with(ApplyBusiness2Activity.this)
								.permission(Permission.CAMERA)
								.onGranted(new Action() {
									@Override
									public void onAction(List<String> permissions) {
										Intent openCameraIntent = new Intent(
												MediaStore.ACTION_IMAGE_CAPTURE);
										tempUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "image.jpg"));
										// 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
										openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
										startActivityForResult(openCameraIntent, TAKE_PICTURE);
									}
								})
								.onDenied(new Action() {
									@Override
									public void onAction(List<String> permissions) {
										new PermissionSetting(ApplyBusiness2Activity.this).showSetting(permissions);
									}
								}).start();

						break;
				}
			}
		});
		builder.create().show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) { // 如果返回码是可以用的
			switch (requestCode) {
				case TAKE_PICTURE:
					startPhotoZoom(tempUri); // 开始对图片进行裁剪处理
					break;
				case CHOOSE_PICTURE:
					startPhotoZoom(data.getData()); // 开始对图片进行裁剪处理
					break;
				case CROP_SMALL_PICTURE:
					if (data != null) {
						setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
					}
					break;
			}
		}
	}

	/**
	 * 裁剪图片方法实现
	 *
	 * @param uri
	 */
	protected void startPhotoZoom(Uri uri) {
		if (uri == null) {
			Log.i("tag", "The uri is not exist.");
		}
		tempUri = uri;
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 设置裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, CROP_SMALL_PICTURE);
	}

	/**
	 * 保存裁剪之后的图片数据
	 *
	 * @param
	 *
	 * @param picdata
	 */
	protected void setImageToView(Intent data) {
		Bundle extras = data.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			System.out.println("图片的值================="+photo);
			//			photo = Utils.toRoundBitmap(photo, tempUri); // 这个时候的图片已经被处理成圆形的了
			System.out.println("图片的值1================="+photo);
			System.out.println("图片的值2================="+tempUri);
			if (!photo.equals("")) {
				if (zhaopian == 1) {
					System.out.println("图片的值1=================");
					iv_personal_icon.setImageBitmap(photo);
				}else if (zhaopian == 2){
					iv_personal_icon1.setImageBitmap(photo);
				}else if (zhaopian == 3){
					iv_personal_icon2.setImageBitmap(photo);
				}else if (zhaopian == 4){
					iv_personal_icon3.setImageBitmap(photo);
				}
			}else {
			}
			uploadPic(photo);
		}
	}

	private void uploadPic(Bitmap bitmap) {
		// 上传至服务器
		// ... 可以在这里把Bitmap转换成file，然后得到file的url，做文件上传操作
		// 注意这里得到的图片已经是圆形图片了
		// bitmap是没有做个圆形处理的，但已经被裁剪了

		String imagePath = Utils.savePhoto(bitmap, Environment
				.getExternalStorageDirectory().getAbsolutePath(), String
				.valueOf(System.currentTimeMillis()));
		if (zhaopian  == 1) {
			tupian1 = imagePath;
		}else if (zhaopian  == 2){
			tupian2 = imagePath;
		}else if (zhaopian  == 3){
			tupian3 = imagePath;
		}else if (zhaopian  == 4){
			tupian4 = imagePath;
		}
		Log.e("imagePath", imagePath+"");
		if(imagePath != null){
			// 拿着imagePath上传了
			// ...
		}
		//storage/sdcard0/1474193068665.png
	}
}
