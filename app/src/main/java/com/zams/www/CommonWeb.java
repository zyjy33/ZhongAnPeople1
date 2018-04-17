package com.zams.www;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.hengyushop.demo.at.BaseActivity;
import com.zams.www.R;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.widget.TextView;

public class CommonWeb extends BaseActivity{
	private WebView commonView;
	private TextView title;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.common_webview);
		title = (TextView) findViewById(R.id.title);
		commonView = (WebView) findViewById(R.id.common_web);
		commonView.getSettings().setBuiltInZoomControls(true);
		commonView.getSettings().setJavaScriptEnabled(true);
		
		
		WebViewClient webViewClient = new WebViewClient(){
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
			 
					 commonView.loadUrl(url);
				
				 return true;
			}
		};
		WebChromeClient webChromeClient = new WebChromeClient(){
			public void onReceivedTitle(WebView view, String tl) {
				
			};
		};
		commonView.setWebChromeClient(webChromeClient);
		commonView.setWebViewClient(webViewClient);
		if(getIntent().hasExtra("title")){
			title.setText(getIntent().getStringExtra("title"));
		}
		if(getIntent().hasExtra("url")){
			commonView.loadUrl(getIntent().getStringExtra("url"));
		}else if (getIntent().hasExtra("content")) {
			
			commonView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
			commonView.getSettings().setLoadWithOverviewMode(true);
//			tv_detail.loadData(details, "text/html", "UTF-8");
			
			Document doc_Dis = Jsoup.parse(getIntent().getStringExtra("content"));
			Elements ele_Img = doc_Dis.getElementsByTag("img");
			if (ele_Img.size() != 0) {
				for (Element e_Img : ele_Img) {
					e_Img.attr("style", "width:100%");
				}
			}
			String newHtmlContent = doc_Dis.toString();
			 commonView.loadDataWithBaseURL("", newHtmlContent, "text/html", "UTF-8",null);
		}
	}
}
