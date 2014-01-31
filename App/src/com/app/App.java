package com.app;

import android.app.Application;
import android.util.Log;

import com.app.helper.AppHelper;
import com.app.helper.UserSessionHelper;
import com.app.util.Constants;
import com.app.util.Utils;

public class App extends Application {
	public static String TAG = App.class.getSimpleName();

	@Override
	public void onCreate() {
		super.onCreate();

		// Init helper
		AppHelper.init(this);

		// Init User Session
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
