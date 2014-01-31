package com.app;

import android.app.Application;
import android.util.Log;

import com.app.helper.AppHelper;
import com.app.helper.UserSessionHelper;
import com.app.util.Constants;
import com.app.util.Utils;
import com.urbanairship.push.PushManager;

public class App extends Application {
	public static String TAG = App.class.getSimpleName();

	@Override
	public void onCreate() {
		super.onCreate();

		// Init OpenSky helper
		AppHelper.init(this);

		// Init OpenSky User Session
		UserSessionHelper.init(this);

		// Printing environment information
		logEnvInfo();
	}

	private void logEnvInfo() {
		StringBuilder envInfo = new StringBuilder();
		envInfo.append(Constants.NEWLINE).append("********")
				.append(Constants.NEWLINE);
		envInfo.append("Running Mode: ")
				.append(BuildConfig.DEBUG ? "Debug" : "Production")
				.append(Constants.NEWLINE);
		envInfo.append("Server Link: ")
				.append(this.getResources().getString(R.string.public_api,
						Utils.getAppProfile(this))).append(Constants.NEWLINE);
		envInfo.append("APID: ")
				.append(PushManager.shared().getAPID() != null ? PushManager
						.shared().getAPID() : "Not set yet")
				.append(Constants.NEWLINE);
		envInfo.append("********").append(Constants.NEWLINE);

		Log.i("Env:", envInfo.toString());

	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		AppHelper.terminate();
		UserSessionHelper.terminate();
	}
}
