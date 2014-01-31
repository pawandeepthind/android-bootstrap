package com.app.helper;

import android.app.ActivityManager;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.app.util.BitmapLruCache;
import com.app.util.Logger;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AppHelper {
	private static RequestQueue mRequestQueue;
	private static ImageLoader mImageLoader;
	private static AppDbHelper mDatabaseHelper;

	// Setup all the Managers

	private static String mAppVersion;

	private static String TAG = AppHelper.class.getSimpleName();

	public static ObjectMapper objectMapper;

	private AppHelper() {
		// no instances
	}

	public static void init(Context context) {
		Logger.i(TAG, "Entering init()");

		mRequestQueue = Volley.newRequestQueue(context);

		int memClass = ((ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
		// Use 1/8th of the available memory for this memory cache.
		int cacheSize = 1024 * 1024 * memClass / 8;
		mImageLoader = new ImageLoader(mRequestQueue, new BitmapLruCache(
				cacheSize));

		mDatabaseHelper = new AppDbHelper(context);

		Logger.i(TAG, "Exiting init()");
	}

	public static String getAppVersion() {
		if (mAppVersion != null)
			return mAppVersion;
		else
			return "1.0";
	}

	public static RequestQueue getRequestQueue() {
		if (mRequestQueue != null) {
			return mRequestQueue;
		} else {
			Logger.e(TAG, "RequestQueue not initialized");
			throw new IllegalStateException("RequestQueue not initialized");
		}
	}

	public static ImageLoader getImageLoader() {
		if (mImageLoader != null) {
			return mImageLoader;
		} else {
			Logger.e(TAG, "ImageLoader not initialized");
			throw new IllegalStateException("ImageLoader not initialized");
		}
	}

	public static AppDbHelper getDatabaseHelper() {
		if (mDatabaseHelper != null) {
			return mDatabaseHelper;
		} else {
			Logger.e(TAG, "DatabaseHelper not initialized");
			throw new IllegalStateException("DatabaseHelper not initialized");
		}
	}

	public static void terminate() {
		if (mImageLoader != null) {
			mImageLoader = null;
		}
		if (mRequestQueue != null) {
			mRequestQueue.cancelAll(null);
			mRequestQueue = null;
		}

		if (mDatabaseHelper != null) {
			mDatabaseHelper.close();
			mDatabaseHelper = null;
		}
	}

	public static ObjectMapper getObjectMapper() {
		if (objectMapper == null) {
			objectMapper = new ObjectMapper();
			objectMapper.configure(
					DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			objectMapper.configure(
					DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		}
		return objectMapper;
	}

	public static void cleanupAllManager() {
	}
}
