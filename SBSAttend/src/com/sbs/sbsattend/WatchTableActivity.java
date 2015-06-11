package com.sbs.sbsattend;


import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WatchTableActivity extends Activity {
	private WebView webView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.table);
		init();
	}

	private void init() {
		webView = (WebView) findViewById(R.id.webView);
		// WebView����web��Դ
		webView.loadUrl("http://192.168.50.88:8890/jsjwork/");
		//����WebView���ԣ��ܹ�ִ��Javascript�ű� 
		webView.getSettings().setJavaScriptEnabled(true);
		//���ü��ؽ�����ҳ������Ӧ�ֻ���Ļ 
		webView.getSettings().setUseWideViewPort(true); 
		webView.getSettings().setLoadWithOverviewMode(true); 
		//����ʹ�û���
		webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		// ����WebViewĬ��ʹ�õ�������ϵͳĬ�����������ҳ����Ϊ��ʹ��ҳ��WebView��
        webView.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// ����ֵ��true��ʱ�����ȥWebView�򿪣�Ϊfalse����ϵͳ�����������������
				view.loadUrl(url);
				return true;
			}
		});
	}
}
