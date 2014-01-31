package com.app.helper;

import java.sql.SQLException;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.app.dao.UserDao;
import com.app.util.Logger;
import com.app.util.Utils;
import com.j256.ormlite.dao.Dao.CreateOrUpdateStatus;

public class UserSessionHelper {
	public static Context context;

	private static UserDao loggedInUserData;

	public static UserDao getLoggedInUserData() {
		return loggedInUserData;
	}

	private static String TAG = UserSessionHelper.class.getSimpleName();

	private UserSessionHelper() {
		// no instances
	}

	public static void init(Context ctx) {
		context = ctx;
		if (loggedInUserData == null) {
			loadLoggedInUserData();
		}
	}

	public static void loadLoggedInUserData() {
		try {
			if (AppHelper.getDatabaseHelper().getUserDao().queryForAll().size() > 0) {
				loggedInUserData = AppHelper.getDatabaseHelper().getUserDao()
						.queryForAll().get(0);
			}
		} catch (SQLException e) {
			Logger.e(TAG, "SQL Exception " + e);
		}
	}

	public static boolean saveLoggedInUserData(UserDao userData) {
		Logger.i(TAG, "Entering saveLoggedInUserData() with userData :"
				+ userData.toString());
		boolean retVal = false;
		try {

			CreateOrUpdateStatus createOrUpdateStatus = AppHelper
					.getDatabaseHelper().getUserDao().createOrUpdate(userData);

			if (createOrUpdateStatus != null
					&& createOrUpdateStatus.isCreated()) {
				Logger.i(TAG, "New user created");
			}

			if (createOrUpdateStatus != null
					&& createOrUpdateStatus.isUpdated()) {
				Logger.i(TAG, "Existing user updated");
			}

			loadLoggedInUserData();

			retVal = true;
		} catch (SQLException e) {
			Logger.e(TAG, "SQL Exception " + e);
		} catch (Exception e) {
			Logger.e(TAG, "Exception " + e);
		}
		return retVal;
	}

	private static void deleteLoggedInUserData() {
		try {
			UserSessionHelper.loggedInUserData = null;
			AppHelper.getDatabaseHelper().clearUserTable();
		} catch (SQLException e) {
			Logger.i(TAG, "Delete Logged In User Failed with SQLException " + e);
		}
	}

	public static void logOut() {
		// cleanup all managers
		AppHelper.cleanupAllManager();

		AppHelper.getRequestQueue().cancelAll(new RequestQueue.RequestFilter() {

			@Override
			public boolean apply(Request<?> request) {
				return true;
			}
		});

		// Local User Store
		deleteLoggedInUserData();

		Utils.clearApplicationCache(context);
	}

	public static void terminate() {
		if (loggedInUserData != null) {
			loggedInUserData = null;
		}
	}
}
