package com.app.views;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;

import com.app.R;
import com.app.activities.base.AppFragmentActivity;

public class YouTubePlayer extends AppFragmentActivity {

	private WebView myWebView;
	private String videoId;

	@SuppressLint("SetJavaScriptEnabled")
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().hide();

		if (getIntent().hasExtra("videoid")) {
			videoId = getIntent().getStringExtra("videoid");
		} else {
			finish();
		}

		setContentView(R.layout.youtubeplayer);

		myWebView = (WebView) findViewById(R.id.youTubePlayerWebView);
		myWebView.getSettings().setJavaScriptEnabled(true);
		myWebView.getSettings().setPluginState(PluginState.ON);
		myWebView.setWebChromeClient(new WebChromeClient());
	}

	@Override
	protected void onResume() {
		super.onResume();
		myWebView.loadUrl("http://www.youtube.com/embed/" + videoId);
	}

	@Override
	public void onPause() {
		super.onPause();
		myWebView.loadUrl("file:///android_asset/nonexistent.html");

	}

	@Override
	public void onStop() {
		super.onPause();
		myWebView.loadUrl("file:///android_asset/nonexistent.html");
	}

}
