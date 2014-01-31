package com.app.services;

import android.app.IntentService;
import android.content.Intent;

import com.app.helper.UserSessionHelper;
import com.app.receivers.UserProfileRespReceiver;

public class ProfileSyncIntentService extends IntentService {
	protected static final String TAG = "ProfileSync";

	public ProfileSyncIntentService() {
		super("ProfileSyncIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		if (UserSessionHelper.getLoggedInUserData() != null) {
			Intent broadcastIntent = new Intent();
			broadcastIntent.setAction(UserProfileRespReceiver.ACTION_RESP);
			broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
			sendBroadcast(broadcastIntent);
		}
	}
}