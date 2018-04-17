package com.zxing.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.hengyu.web.RealmName;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.my.ShengJiCkActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.R;
import com.zxing.android.camera.CameraManager;
import com.zxing.android.decoding.CaptureActivityHandler;
import com.zxing.android.decoding.InactivityTimer;
import com.zxing.android.view.ViewfinderView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Vector;

public class CaptureActivity extends Activity implements Callback {
	public static final String QR_RESULT = "RESULT";

	private CaptureActivityHandler handler;
	private ViewfinderView viewfinderView;
	private SurfaceView surfaceView;
	private boolean hasSurface;
	private Vector<BarcodeFormat> decodeFormats;
	private String characterSet;
	private InactivityTimer inactivityTimer;
	private MediaPlayer mediaPlayer;
	private boolean playBeep;
	// private static final float BEEP_VOLUME = 0.10f;
	private boolean vibrate;
	CameraManager cameraManager;
	private SharedPreferences spPreferences;
	String user_name,user_id,nickname;
	public static String agencygroupid,storegroupid,shopsgroupid,salesgroupid;
	private String login_sign;
	private String group_id;
	private String chuangke = "";
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		/*
		 * this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		 * WindowManager.LayoutParams.FLAG_FULLSCREEN);// 去掉信息栏
		 *
		 * RelativeLayout layout = new RelativeLayout(this);
		 * layout.setLayoutParams(new
		 * ViewGroup.LayoutParams(LayoutParams.FILL_PARENT,
		 * LayoutParams.FILL_PARENT));
		 *
		 * this.surfaceView = new SurfaceView(this); this.surfaceView
		 * .setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.FILL_PARENT,
		 * LayoutParams.FILL_PARENT));
		 *
		 * layout.addView(this.surfaceView);
		 *
		 * this.viewfinderView = new ViewfinderView(this);
		 * this.viewfinderView.setBackgroundColor(0x00000000);
		 * this.viewfinderView.setLayoutParams(new
		 * ViewGroup.LayoutParams(LayoutParams.FILL_PARENT,
		 * LayoutParams.FILL_PARENT)); layout.addView(this.viewfinderView);
		 *
		 * TextView status = new TextView(this); RelativeLayout.LayoutParams
		 * params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
		 * LayoutParams.WRAP_CONTENT);
		 * params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		 * params.addRule(RelativeLayout.CENTER_HORIZONTAL);
		 * status.setLayoutParams(params);
		 * status.setBackgroundColor(0x00000000);
		 * status.setTextColor(0xFFFFFFFF); status.setText("请将条码置于取景框内扫描。");
		 * status.setTextSize(14.0f);
		 *
		 * layout.addView(status); setContentView(layout);
		 */

		setContentView(R.layout.activity_capture);
		surfaceView = (SurfaceView) findViewById(R.id.surfaceview);
		viewfinderView = (ViewfinderView) findViewById(R.id.viewfinderview);

		Window window = getWindow();
		window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		hasSurface = false;
		inactivityTimer = new InactivityTimer(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		spPreferences = getSharedPreferences("longuserset", Context.MODE_PRIVATE);
		login_sign = spPreferences.getString("login_sign", "");
		group_id = spPreferences.getString("group_id", "");
		userpanduan();
		if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}

		// CameraManager.init(getApplication());
		cameraManager = new CameraManager(getApplication());

//		viewfinderView.setCameraManager(cameraManager);

		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		if (hasSurface) {
			initCamera(surfaceHolder);
		} else {
			surfaceHolder.addCallback(this);
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
		decodeFormats = null;
		characterSet = null;

		playBeep = true;
		AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
		if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
			playBeep = false;
		}
		initBeepSound();
		vibrate = true;
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		cameraManager.closeDriver();
	}

	@Override
	protected void onDestroy() {
		inactivityTimer.shutdown();
		super.onDestroy();
	}

	private void initCamera(SurfaceHolder surfaceHolder) {
		try {
			// CameraManager.get().openDriver(surfaceHolder);
			cameraManager.openDriver(surfaceHolder);
		} catch (IOException ioe) {
			return;
		} catch (RuntimeException e) {
			return;
		}
		if (handler == null) {
			handler = new CaptureActivityHandler(this, decodeFormats, characterSet);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;

	}

	public CameraManager getCameraManager() {
		return cameraManager;
	}

	public ViewfinderView getViewfinderView() {
		return viewfinderView;
	}

	public Handler getHandler() {
		return handler;
	}

	public void drawViewfinder() {
		viewfinderView.drawViewfinder();

	}

	public void handleDecode(Result obj, Bitmap barcode) {
		inactivityTimer.onActivity();
		playBeepSoundAndVibrate();
		showResult(obj, barcode);
	}

	/**
	 * 判断是否升级
	 * @param
	 */
	public void userpanduan(){
		try{
			String user_name = spPreferences.getString("user", "");
			String strUrlone = RealmName.REALM_NAME_LL + "/get_user_config?username="+user_name+"&sign="+login_sign+"";

			System.out.println("======11============="+strUrlone);
			AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
				public void onSuccess(int arg0, String arg1) {
					try {
						JSONObject object = new JSONObject(arg1);
						System.out.println("======11=======arg1======"+arg1);
						String status = object.getString("status");
						if (status.equals("y")) {
							JSONObject obj = object.getJSONObject("data");
							agencygroupid = obj.getString("agencygroupid");
							storegroupid = obj.getString("storegroupid");
							shopsgroupid = obj.getString("shopsgroupid");
							salesgroupid = obj.getString("salesgroupid");

							System.out.println("======agencygroupid============"+agencygroupid);
							System.out.println("======group_id============"+group_id);
							if (agencygroupid.contains(group_id)) {
								chuangke = "1";
							}else if (storegroupid.contains(group_id)){
								chuangke = "1";
							}else if (shopsgroupid.contains(group_id)){
								chuangke = "1";
							}else if (salesgroupid.contains(group_id)){
								chuangke = "1";
							}else {
								chuangke = "2";
							}

							//							agencygroupid  代理
							//							storegroupid   仓超
							//							shopsgroupid   门店
							//							salesgroupid   业务员
						}else{

						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				};
			}, null);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	private void showResult(final Result rawResult, Bitmap barcode) {

		String zhou = rawResult.getText();
		System.out.println("==============="+zhou);
		String sp_sys = getIntent().getStringExtra("sp_sys");
		System.out.println("sp_sys==============="+sp_sys);
		if (sp_sys != null) {
			if (sp_sys.equals("3")){
				//					Intent intent4 = new Intent(CaptureActivity.this,ShengJiCkActivity.class);
				//					intent4.putExtra("zhou", zhou);
				//					startActivity(intent4);
				finish();
				Toast.makeText(CaptureActivity.this, zhou, Toast.LENGTH_SHORT).show();
			}else {
				Toast.makeText(CaptureActivity.this, zhou, Toast.LENGTH_SHORT).show();
				finish();
			}
		}else {
			if (chuangke.equals("1")) {
				Toast.makeText(CaptureActivity.this, "已升级为服务顾问", Toast.LENGTH_SHORT).show();
				finish();
			}else if (chuangke.equals("2")){
				Intent intent4 = new Intent(CaptureActivity.this,ShengJiCkActivity.class);
				intent4.putExtra("zhou", zhou);
				startActivity(intent4);
				finish();
			}
		}

		//		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		//
		//		Drawable drawable = new BitmapDrawable(barcode);
		//		builder.setIcon(drawable);
		//
		//		builder.setTitle("类型:" + rawResult.getBarcodeFormat() + "\n 结果：" + rawResult.getText());
		//		builder.setPositiveButton("确定", new OnClickListener() {
		//
		//			@Override
		//			public void onClick(DialogInterface dialog, int which) {
		//				dialog.dismiss();
		////				Intent intent = new Intent();
		////				intent.putExtra("result", rawResult.getText());
		////				setResult(RESULT_OK, intent);
		////				finish();
		//				String zhou = rawResult.getText();
		//				System.out.println("==============="+zhou);
		//				Intent intent4 = new Intent(CaptureActivity.this,ChuangKeUserActivity.class);
		//				intent4.putExtra("zhou", zhou);
		//				startActivity(intent4);
		//			}
		//		});
		//		builder.setNegativeButton("重新扫描", new OnClickListener() {
		//
		//			@Override
		//			public void onClick(DialogInterface dialog, int which) {
		//				dialog.dismiss();
		//				restartPreviewAfterDelay(0L);
		//			}
		//		});
		//		builder.setCancelable(false);
		//		builder.show();

		// Intent intent = new Intent();
		// intent.putExtra(QR_RESULT, rawResult.getText());
		// setResult(RESULT_OK, intent);
		// finish();
	}

	public void restartPreviewAfterDelay(long delayMS) {
		if (handler != null) {
			handler.sendEmptyMessageDelayed(MessageIDs.restart_preview, delayMS);
		}
	}

	private void initBeepSound() {
		if (playBeep && mediaPlayer == null) {
			// The volume on STREAM_SYSTEM is not adjustable, and users found it
			// too loud,
			// so we now play on the music stream.
			setVolumeControlStream(AudioManager.STREAM_MUSIC);
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnCompletionListener(beepListener);

			try {
				AssetFileDescriptor fileDescriptor = getAssets().openFd("qrbeep.ogg");
				this.mediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(),
						fileDescriptor.getLength());
				this.mediaPlayer.setVolume(0.1F, 0.1F);
				this.mediaPlayer.prepare();
			} catch (IOException e) {
				this.mediaPlayer = null;
			}
		}
	}

	private static final long VIBRATE_DURATION = 200L;

	private void playBeepSoundAndVibrate() {
		if (playBeep && mediaPlayer != null) {
			mediaPlayer.start();
		}
		if (vibrate) {
			Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
			vibrator.vibrate(VIBRATE_DURATION);
		}
	}

	/**
	 * When the beep has finished playing, rewind to queue up another one.
	 */
	private final OnCompletionListener beepListener = new OnCompletionListener() {
		public void onCompletion(MediaPlayer mediaPlayer) {
			mediaPlayer.seekTo(0);
		}
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			setResult(RESULT_CANCELED);
			finish();
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_FOCUS || keyCode == KeyEvent.KEYCODE_CAMERA) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}