package com.app.activities.base;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Window;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.app.R;
import com.app.helper.AppHelper;
import com.app.util.Logger;
import com.app.util.VolleyErrorHelper;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class BaseSherlockFragmentActivity extends SherlockFragmentActivity {
	private static String TAG = AppFragmentActivity.class
			.getSimpleName();

	private BroadcastReceiver networkStateReceiver;
	private TextView noInternetConnnectivityView, notLoadedView;
	private RelativeLayout progressView;
	private FrameLayout contentView;
	private Animation mSlideTopDownAnimation;
	private Animation mSlideTopUpAnimation;

	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		initAnimations();
	}

	@Override
	public void onStart() {
		super.onStart();
		// Network State Receiver
		networkStateReceiver = new BroadcastReceiver() {
			public void onReceive(Context context, Intent intent) {
				ConnectivityManager connectivityManager = (ConnectivityManager) context
						.getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo activeNetInfo = connectivityManager
						.getActiveNetworkInfo();
				if (activeNetInfo == null || !activeNetInfo.isConnected()) {
					showNoInternetConnectivityView();
				} else {
					hideNoInternetConnectivityView();
				}
			}
		};
		registerReceiver(networkStateReceiver, new IntentFilter(
				"android.net.conn.CONNECTIVITY_CHANGE"));
	}

	@Override
	public void onStop() {
		super.onStop();
		if (networkStateReceiver != null)
			unregisterReceiver(networkStateReceiver);
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		AppHelper.getRequestQueue().cancelAll(
				new RequestQueue.RequestFilter() {

					@Override
					public boolean apply(Request<?> request) {
						if (request.getTag() != null
								&& request.getTag().equals("image")) {
							Logger.i(TAG, "Cancel image request with URL: "
									+ request.getUrl());
							return true;
						}
						return false;
					}
				});
	}

	public void createProgressDialog() {
		progressDialog = new ProgressDialog(this,
				ProgressDialog.THEME_HOLO_LIGHT);
		progressDialog.setMessage("Please wait...");
		progressDialog.setCancelable(false);
		progressDialog.setIndeterminate(true);
	}

	public void destroyProgressDialog() {
		if (progressDialog != null) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}

	public void showProgressDialog() {
		if (progressDialog == null) {
			createProgressDialog();
		}
		if (!isFinishing()) {
			progressDialog.show();
		}
	}

	public void hideProgressDialog() {
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.hide();
		}
	}

	public void setContentView(int viewId) {
		LinearLayout fullLayout = (LinearLayout) this.getLayoutInflater()
				.inflate(R.layout.base, null);
		noInternetConnnectivityView = (TextView) fullLayout
				.findViewById(R.id.noInternetConnnectivityView);
		notLoadedView = (TextView) fullLayout.findViewById(R.id.notLoadedView);
		progressView = (RelativeLayout) fullLayout
				.findViewById(R.id.progressView);
		contentView = (FrameLayout) fullLayout.findViewById(R.id.contentView);
		this.getLayoutInflater().inflate(viewId, contentView, true);
		this.setContentView(fullLayout);
		setSupportProgressBarIndeterminateVisibility(false);
	}

	public void initAnimations() {

		final AnimationListener makeTopGone = new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				Logger.d(TAG, "onAnimationEnd - makeTopGone");
				noInternetConnnectivityView.setVisibility(View.GONE);
			}
		};

		final AnimationListener makeTopVisible = new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				Logger.d(TAG, "onAnimationStart - makeTopVisible");
				noInternetConnnectivityView.setVisibility(View.VISIBLE);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
			}
		};

		mSlideTopUpAnimation = AnimationUtils.loadAnimation(this,
				R.anim.slide_top_up);
		mSlideTopUpAnimation.setAnimationListener(makeTopGone);
		mSlideTopDownAnimation = AnimationUtils.loadAnimation(this,
				R.anim.slide_top_down);

		mSlideTopDownAnimation.setAnimationListener(makeTopVisible);
	}

	private void hideNoInternetConnectivityView() {
		noInternetConnnectivityView.startAnimation(mSlideTopUpAnimation);
	}

	private void showNoInternetConnectivityView() {
		noInternetConnnectivityView.startAnimation(mSlideTopDownAnimation);
	}

	public void startProgressInView() {
		progressView.setVisibility(View.VISIBLE);
		contentView.setVisibility(View.GONE);
		notLoadedView.setVisibility(View.GONE);
	}

	public void stopAndHideProgressInView() {
		progressView.setVisibility(View.GONE);
		contentView.setVisibility(View.VISIBLE);
		notLoadedView.setVisibility(View.GONE);
	}

	public void hideKeyboard(EditText textView) {
		InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(textView.getWindowToken(),
				InputMethodManager.RESULT_UNCHANGED_SHOWN);
	}

	public void handleVolleyError(VolleyError error, String notLoadedText,
			OnClickListener onclickListener) {
		handleVolleyError(error, false);
		contentView.setVisibility(View.GONE);
		progressView.setVisibility(View.GONE);
		notLoadedView.setVisibility(View.VISIBLE);
		notLoadedView.setText(notLoadedText);
		notLoadedView.setOnClickListener(onclickListener);
	}

	public void handleVolleyError(VolleyError error, boolean displayNormalToast) {
		if (!displayNormalToast) {
			showAppMsg(VolleyErrorHelper.getMessage(error, this));
		} else {
			Toast t = Toast.makeText(this,
					VolleyErrorHelper.getMessage(error, this),
					Toast.LENGTH_LONG);
			t.setGravity(Gravity.TOP, 0, 100);
			t.show();
		}
	}

	public void handleVolleyError(VolleyError error) {
		showAppMsg(VolleyErrorHelper.getMessage(error, this));
	}

	public void showAppMsg(String msg) {
		showAppMsg(msg, Style.ALERT);
	}

	public void showAppMsg(String msg, Style style) {
		Crouton.makeText(this, msg, style, R.id.container).show();
	}
}
