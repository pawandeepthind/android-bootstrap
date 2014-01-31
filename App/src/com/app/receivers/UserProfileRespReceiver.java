package com.app.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.app.activities.base.ActivityInterface;
import com.app.util.Logger;

public class UserProfileRespReceiver extends BroadcastReceiver {
	public static final String TAG = "UserProfileReceiver";
	public static final String ACTION_RESP = "com.opensky.intent.action.RECEIVE_USERPROFILERESPONSE";
	ActivityInterface activity;

	public UserProfileRespReceiver(ActivityInterface activity) {
		this.activity = activity;
	}

	@Override
	public void onReceive(Context context, Intent intent) {

		if (activity != null) {
			Logger.i(
					TAG,
					"In UserProfileRespReceiver.onReceive() for updating the user profile. Current Activity: "
							+ activity.getClass().getSimpleName());

			activity.updateUserProfileUIFields();
		}
	}
}