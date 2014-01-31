/**
 * 
 */
package com.app.util;

import android.util.Log;

import com.app.BuildConfig;


/*
 * Usage:
 * Logger.d(“App”, “Sample debug message”);
 * If the device/emulator running the app has the log level set to debug the message will appear.
 * 
 * The benefit here is you won’t have to worry about removing print lines or maintaining variables
 * for levels that you might forget to change before building your release apk.
 * 
 * Changing the log level on device is actually very simple. Simply run 
 * adb shell setprop log.tag.<YOUR_LOG_TAG> <LEVEL>
 */

public class Logger {
	
	private static boolean isLoggable(String tag, int logLevel) {
		if(BuildConfig.DEBUG) {
			return Log.isLoggable(tag, logLevel);
		} else
			return false;
	}
	
	/**
	 * I. Information
	 * 
	 * @param tag
	 *            the tag
	 * @param string
	 *            the string
	 */
	public static void i(String tag, Object obj) {
		if (isLoggable(tag, Log.INFO)) {
			if (obj instanceof String)
				Log.i(tag, (String) obj);
			else if (obj instanceof Exception) {
				Log.i(tag, Utils.getExceptionStringFromException((Exception)obj));
			} else if(obj instanceof Throwable) {
				Log.i(tag, Utils.getExceptionStringFromThrowable((Throwable)obj));
			}
		}
	}

	/**
	 * E. Error
	 * 
	 * @param tag
	 *            the tag
	 * @param string
	 *            the string
	 */
	public static void e(String tag, Object obj) {
		if (isLoggable(tag, Log.ERROR)){
			if (obj instanceof String)
				Log.e(tag, (String) obj);
			else if (obj instanceof Exception) {
				Log.e(tag, Utils.getExceptionStringFromException((Exception)obj));
			} else if(obj instanceof Throwable) {
				Log.e(tag, Utils.getExceptionStringFromThrowable((Throwable)obj));
			}
		}
	}

	/**
	 * D. Debug
	 * 
	 * @param tag
	 *            the tag
	 * @param string
	 *            the string
	 */
	public static void d(String tag, Object obj) {
		if (isLoggable(tag, Log.DEBUG)){
			if (obj instanceof String)
				Log.d(tag, (String) obj);
			else if (obj instanceof Exception) {
				Log.d(tag, Utils.getExceptionStringFromException((Exception)obj));
			} else if(obj instanceof Throwable) {
				Log.d(tag, Utils.getExceptionStringFromThrowable((Throwable)obj));
			}
		}
	}

	/**
	 * V. Verbose
	 * 
	 * @param tag
	 *            the tag
	 * @param string
	 *            the string
	 */
	public static void v(String tag, Object obj) {
		if (isLoggable(tag, Log.VERBOSE)) {
			if (obj instanceof String)
				Log.v(tag, (String) obj);
			else if (obj instanceof Exception) {
				Log.v(tag, Utils.getExceptionStringFromException((Exception)obj));
			} else if(obj instanceof Throwable) {
				Log.v(tag, Utils.getExceptionStringFromThrowable((Throwable)obj));
			}
		}
	}

	/**
	 * W. Warning
	 * 
	 * @param tag
	 *            the tag
	 * @param string
	 *            the string
	 */
	public static void w(String tag, Object obj) {
		if (isLoggable(tag, Log.WARN)){
			if (obj instanceof String)
				Log.w(tag, (String) obj);
			else if (obj instanceof Exception) {
				Log.w(tag, Utils.getExceptionStringFromException((Exception)obj));
			} else if(obj instanceof Throwable) {
				Log.w(tag, Utils.getExceptionStringFromThrowable((Throwable)obj));
			}
		}	
	}
}
