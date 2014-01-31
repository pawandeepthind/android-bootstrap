package com.app.activities.base;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.app.App;
import com.app.R;
import com.app.receivers.UserProfileRespReceiver;
import com.app.services.ProfileSyncIntentService;
import com.app.util.Logger;
import com.app.views.YouTubePlayer;
import com.google.analytics.tracking.android.EasyTracker;
import com.urbanairship.UAirship;

public class AppFragmentActivity extends BaseSherlockFragmentActivity implements
		ActivityInterface {

	private static String TAG = AppFragmentActivity.class.getSimpleName()
			.substring(0, 22);

	protected App application;
	private UserProfileRespReceiver userProfileResponseReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// getWindow().setWindowAnimations(R.style.Fade);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		application = (App) getApplication();

		getSupportActionBar().setDisplayHomeAsUpEnabled(isHomeButtonEnabled());
		initAnimations();
	}

	@Override
	public void onStart() {
		super.onStart();
		registerActivityReceivers();

		UAirship.shared().getAnalytics().activityStarted(this);
		EasyTracker.getInstance(this).activityStart(this);

		if (isProfileSyncOnResumeEnabled()) {
			Logger.i(this.getClass().getSimpleName(),
					"Start profile sync service");
			startService(new Intent(this, ProfileSyncIntentService.class));
		}
	}

	@Override
	public void onStop() {
		super.onStop();
		UAirship.shared().getAnalytics().activityStopped(this);
		EasyTracker.getInstance(this).activityStop(this);
		deregisterActivityReceivers();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (isHandlingPushNotification())
			handlePush();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		application = null;
	}

	private void registerActivityReceivers() {
		IntentFilter filter = new IntentFilter(
				UserProfileRespReceiver.ACTION_RESP);
		filter.addCategory(Intent.CATEGORY_DEFAULT);
		userProfileResponseReceiver = new UserProfileRespReceiver(this);
		registerReceiver(userProfileResponseReceiver, filter);
	}

	private void deregisterActivityReceivers() {
		if (userProfileResponseReceiver != null)
			unregisterReceiver(userProfileResponseReceiver);
	}

	public void setContentView(int viewId) {
		super.setContentView(viewId);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	private void handlePush() {
		// Restore message from stored area and setup display of message
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.main, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onHomeButtonTapped();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onHomeButtonTapped() {
		finish();
	}

	@Override
	public boolean isHomeButtonEnabled() {
		return true;
	}

	@Override
	public boolean isHandlingPushNotification() {
		return true;
	}

	@Override
	public void updateUserProfileUIFields() {
		Logger.i(TAG, "In updateUserProfileUIFields()");
	}

	@Override
	public boolean isProfileSyncOnResumeEnabled() {
		return false;
	}

	/**
	 * Call this method to send Broadcast intent for updating the user loves.
	 */
	public void requestUserLovesUpdate() {
	}

	@Override
	public void onLinkClicked(String type, String id) {
	}

	public void onVideoClicked(String videoId) {
		Intent intent = new Intent(this, YouTubePlayer.class);
		intent.putExtra("videoid", videoId);
		startActivity(intent);
	}
}
