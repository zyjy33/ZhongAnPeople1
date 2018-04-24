package com.zams.www;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.hengyu.web.Constant;
import com.hengyushop.demo.at.BaseActivity;
import com.lglottery.www.domain.TuiGuangBean;
import com.lglottery.www.http.Util;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TuiGuangDetailActivity extends BaseActivity {
	private WebView webview;
	private ImageButton btn_wechat;
	private ImageButton btn_wx_friend;
	private ImageButton btn_sms;
	private LayoutInflater inflater;
	private View view;
	private PopupWindow pop;
	private Button fenxiang;
	private TuiGuangBean bean;
	private IWXAPI api;
	private TextView title, time;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tuiguang_detail);
		webview = (WebView) findViewById(R.id.content);
		title = (TextView) findViewById(R.id.title);
		time = (TextView) findViewById(R.id.time);

		bean = (TuiGuangBean) getIntent().getSerializableExtra("content");

	/*	String release = android.os.Build.VERSION.RELEASE; // android系统版本号
		release = release.substring(0, 3);
		// Android 4.4图片显示不全
		if ("4.4".equals(release)) {
			// toastinfo("4.4");
			System.out.println("4.4");
			webview.setWebViewClient(new MyWebViewClient());

		} else {
			WebSettings webSettings = webview.getSettings(); // webView:
																// 类WebView的实例
			webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		}
*/
		Document doc_Dis = Jsoup.parse(bean.getDetail());
		Elements ele_Img = doc_Dis.getElementsByTag("img");
		if (ele_Img.size() != 0) {
			for (Element e_Img : ele_Img) {
				e_Img.attr("style", "width:100%");
			}
		}
		String newHtmlContent = doc_Dis.toString();
		System.out.println(bean.getDetail()+"--"+bean.getDetail().length());
		if(bean.getDetail().length()!=0){
			webview.loadDataWithBaseURL("", newHtmlContent, "text/html", "UTF-8",
					null);
		}else{
			System.out.println("地址"+bean.getHttp());
			webview.getSettings().setJavaScriptEnabled(true);
			webview.setWebChromeClient(new WebChromeClient());
			webview.loadUrl(bean.getHttp());
			//			webview.setWebViewClient(new WebViewClient(){
			//
			//				@Override
			//				   public boolean shouldOverrideUrlLoading(WebView view, String url) {
			//
			//					webview.loadUrl(bean.getHttp());   //在2.3上面不加这句话，可以加载出页面，在4.0上面必须要加入，不然出现白屏
			//				    return true;
			//				   }
			//
			//			});
			//			webview.loadDataWithBaseURL(baseUrl, data, mimeType, encoding, historyUrl)
		}

		fenxiang = (Button) findViewById(R.id.fenxiang);

		title.setText(bean.getTitle());
		time.setText(bean.getTime());
		fenxiang.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				SoftWarePopuWindow();
			}
		});
	}

	// webview 4.4
	class MyWebViewClient extends WebViewClient {
		boolean scaleChangedRunnablePending = false;

		@Override
		public void onPageFinished(WebView view, String url) {

			// //获取宽高
			// DisplayMetrics dm = new DisplayMetrics();
			// getWindowManager().getDefaultDisplay().getMetrics(dm);
			// int imgwidth = dm.widthPixels - 30;
			// int appheight = dm.heightPixels;

			int imgwidth = 280;
			webview.setWebChromeClient(new WebChromeClient());

			webview.loadUrl("javascript:(function(){"
					+ "var objs = document.getElementsByTagName(\"img\"); "
					+ "for(var i=0;i<objs.length;i++) {" + "if("
					+ VERSION.SDK_INT + ">=19){"
					+ // //webview图片自适应，android4.4之前都有用，4.4之后google优化后，无法支持，需要自己手动缩放
					"AutoResizeImage(" + imgwidth + ",0,objs[i]);" + "}" + "}"
					+ "})()");
			super.onPageFinished(view, url);
		}
	}

	private void SoftWarePopuWindow() {

		inflater = LayoutInflater.from(getApplicationContext());
		view = inflater.inflate(R.layout.ware_infromation_share, null);
		pop = new PopupWindow(view, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT, false);

		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.setOutsideTouchable(true);
		// pop.setFocusable(true);
		// pop.setTouchable(true); // 设置PopupWindow可触摸
		//
		if (!pop.isShowing()) {
			pop.showAtLocation(webview, Gravity.TOP, 0, 50);
		}
		/**
		 *
		 */
		btn_wechat = (ImageButton) view.findViewById(R.id.img_btn_wechat);
		btn_wx_friend = (ImageButton) view.findViewById(R.id.img_btn_wx_friend);
		btn_sms = (ImageButton) view.findViewById(R.id.img_btn_sms);
		btn_wechat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				softshareWxChat(bean);

			}
		});
		btn_wx_friend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				softshareWxFriend(bean);
			}
		});

		btn_sms.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Uri uri = Uri.parse("smsto:");
				Intent it = new Intent(Intent.ACTION_SENDTO, uri);
				it.putExtra("sms_body", bean.getContent() + bean.getHttp());
				startActivity(it);
			}
		});
	}

	private void softshareWxChat(TuiGuangBean bean) {
		api = WXAPIFactory.createWXAPI(getApplicationContext(),
				Constant.APP_ID, false);
		api.registerApp(Constant.APP_ID);
		WXWebpageObject webpage = new WXWebpageObject();
		webpage.webpageUrl = bean.getHttp();
		WXMediaMessage msg = new WXMediaMessage(webpage);
		msg.title = bean.getTitle();
		msg.description = bean.getContent();
		Bitmap thumb = BitmapFactory.decodeResource(getApplicationContext()
				.getResources(), R.drawable.icon);
		msg.thumbData = Util.bmpToByteArray(thumb, true);
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("webpage");
		req.message = msg;
		req.scene = SendMessageToWX.Req.WXSceneSession;
		boolean flag = api.sendReq(req);
		System.out.println("微信註冊" + flag + "-->" + msg.thumbData);
	}

	private void softshareWxFriend(TuiGuangBean bean) {
		api = WXAPIFactory.createWXAPI(getApplicationContext(),
				Constant.APP_ID, false);
		api.registerApp(Constant.APP_ID);
		WXWebpageObject webpage = new WXWebpageObject();
		webpage.webpageUrl = bean.getHttp();
		WXMediaMessage msg = new WXMediaMessage(webpage);
		msg.title = bean.getTitle();
		msg.description = bean.getContent();
		Bitmap thumb = BitmapFactory.decodeResource(getApplicationContext()
				.getResources(), R.drawable.icon);
		msg.thumbData = Util.bmpToByteArray(thumb, true);
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("webpage");
		req.message = msg;
		req.scene = SendMessageToWX.Req.WXSceneTimeline;
		boolean flag = api.sendReq(req);
		System.out.println(flag + "-->" + msg.thumbData);
	}

	private String buildTransaction(final String type) {
		return (type == null) ? String.valueOf(System.currentTimeMillis())
				: type + System.currentTimeMillis();
	}
}
