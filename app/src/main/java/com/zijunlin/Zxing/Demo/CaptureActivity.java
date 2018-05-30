package com.zijunlin.Zxing.Demo;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.hengyushop.demo.activity.QianDaoListActivity;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.demo.home.XiaDanActivity;
import com.hengyushop.demo.my.TishiBaoMinQianDaoActivity;
import com.zams.www.R;
import com.zijunlin.Zxing.Demo.camera.CameraManager;
import com.zijunlin.Zxing.Demo.decoding.CaptureActivityHandler;
import com.zijunlin.Zxing.Demo.decoding.InactivityTimer;
import com.zijunlin.Zxing.Demo.view.ViewfinderView;

import java.io.IOException;
import java.util.Vector;

public class CaptureActivity extends BaseActivity implements Callback {

	private CaptureActivityHandler handler;
	private ViewfinderView viewfinderView;
	private boolean hasSurface;
	private Vector<BarcodeFormat> decodeFormats;
	private String characterSet;
	private TextView txtResult;
	private InactivityTimer inactivityTimer;
	private MediaPlayer mediaPlayer;
	private boolean playBeep;
	private static final float BEEP_VOLUME = 0.10f;
	private boolean vibrate;
	public static String bianma;
	public static boolean zhuangtai = false;
	public static String ytpe = "";
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//初始化 CameraManager
		CameraManager.init(getApplication());
		viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
		txtResult = (TextView) findViewById(R.id.txtResult);
		hasSurface = false;
		inactivityTimer = new InactivityTimer(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		zhuangtai = false;
		ytpe = "0";
		SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
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
		CameraManager.get().closeDriver();
	}

	@Override
	protected void onDestroy() {
		inactivityTimer.shutdown();
		super.onDestroy();
	}

	private void initCamera(SurfaceHolder surfaceHolder) {
		try {
			CameraManager.get().openDriver(surfaceHolder);
		} catch (IOException ioe) {
			return;
		} catch (RuntimeException e) {
			return;
		}
		if (handler == null) {
			handler = new CaptureActivityHandler(this, decodeFormats,
					characterSet);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
							   int height) {

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
		try {

			inactivityTimer.onActivity();
			//		viewfinderView.drawResultBitmap(barcode);
			playBeepSoundAndVibrate();
			//		txtResult.setText(obj.getBarcodeFormat().toString() + ":"+ obj.getText());
			System.out.println("bianma======================"+bianma);
			//		 System.out.println("bianma=======2==============="+obj.getBarcodeFormat().toString() + ":"+ obj.getText());
			bianma = obj.getText();
			System.out.println("bianma--------0--------"+bianma);

			String sp_sys = getIntent().getStringExtra("sp_sys");
			System.out.println("sp_sys-------"+sp_sys);

			if (sp_sys.equals("1")) {
				ytpe = "1";
				try {
					Intent intent = new Intent(CaptureActivity.this,XiaDanActivity.class);
					//			    Intent intent = new Intent(CaptureActivity.this,MyXiaDanActivity.class);
					intent.putExtra("bianma", bianma);
					intent.putExtra("sp_sys", sp_sys);
					startActivity(intent);
					finish();

				} catch (Exception e) {

					e.printStackTrace();
				}
			} else if (sp_sys.equals("2")) {
				try {
					//		    	md.setOrder_no(obj.getString("order_no"));
					//				md.setTrade_no(obj.getString("trade_no"));
					//		        Intent intent = new Intent(CaptureActivity.this,Webview1.class);
					//			    intent.putExtra("link_url", "https://www.taobao.com/");
					Intent intent = new Intent(CaptureActivity.this,TishiBaoMinQianDaoActivity.class);
					intent.putExtra("bianma", bianma);
					intent.putExtra("qiandao","2");
					intent.putExtra("sp_sys", getIntent().getStringExtra("sp_sys"));
					startActivity(intent);
					finish();
				} catch (Exception e) {

					e.printStackTrace();
				}
			} else if (sp_sys.equals("3")) {
				try {
					Intent intent = new Intent(CaptureActivity.this,QianDaoListActivity.class);
					intent.putExtra("bianma", bianma);
					//		    	intent.putExtra("sp_sys", getIntent().getStringExtra("sp_sys"));
					startActivity(intent);
					finish();
				} catch (Exception e) {

					e.printStackTrace();
				}
			} else {
				//			zhuangtai = true;
				ytpe = "2";
				finish();
			}
		} catch (Exception e) {

			e.printStackTrace();
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

			AssetFileDescriptor file = getResources().openRawResourceFd(
					R.raw.beep);
			try {
				mediaPlayer.setDataSource(file.getFileDescriptor(),
						file.getStartOffset(), file.getLength());
				file.close();
				mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
				mediaPlayer.prepare();
			} catch (IOException e) {
				mediaPlayer = null;
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

}