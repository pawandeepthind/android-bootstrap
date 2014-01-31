package com.app.services;

import android.app.IntentService;
import android.content.Intent;

import com.app.helper.UserSessionHelper;

public class ProfileSyncIntentService extends IntentService {
	protected static final String TAG = "ProfileSync";

	public ProfileSyncIntentService() {
		super("ProfileSyncIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		if (UserSessionHelper.getLoggedInUserData() != null) {
			// Only do sync if user is logged in
//			final BaseApp app = (BaseApp) getApplication();
//			OpenSkyAppHelper.getUserManager(app).getUserProfile(
//					OpenSkyUserSession.getLoggedInUserData().getUserid(),
//					new Response.Listener<UserDto>() {
//						@Override
//						public void onResponse(UserDto response) {
//							// Not calling super.onResponse() as we want to
//							// update the cart count in the receiver
//							if (response != null
//									&& OpenSkyUserSession.getLoggedInUserData() != null) {
//
//								UserDao user = OpenSkyAppHelper.getUserManager(
//										app).convertUserDtoToUserDao(response);
//								user.setId(OpenSkyUserSession
//										.getLoggedInUserData().getId());
//								user.setCredit(OpenSkyUserSession
//										.getLoggedInUserData().getCredit());
//								OpenSkyUserSession.saveLoggedInUserData(user);
//
//								Intent broadcastIntent = new Intent();
//								broadcastIntent
//										.setAction(UserProfileRespReceiver.ACTION_RESP);
//								broadcastIntent
//										.addCategory(Intent.CATEGORY_DEFAULT);
//								sendBroadcast(broadcastIntent);
//							}
//						}
//					}, new Response.ErrorListener() {
//
//						@Override
//						public void onErrorResponse(VolleyError error) {
//							Logger.e(TAG, VolleyErrorHelper.getMessage(error,
//									getApplicationContext()));
//						}
//					});
		}
	}
}