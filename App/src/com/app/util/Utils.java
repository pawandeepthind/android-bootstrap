package com.app.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings.Secure;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.util.DisplayMetrics;
import android.util.Patterns;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.TextView;

import com.app.BuildConfig;
import com.app.R;
import com.app.activities.base.ActivityInterface;

public class Utils {
	private static String TAG = Utils.class.getSimpleName();

	public static String getExceptionStringFromException(Exception e) {
		if (e.getMessage() != null) {
			return e.getMessage();
		} else if (e.getCause() != null) {
			if (e.getCause().getMessage() != null) {
				return e.getCause().getMessage();
			} else {
				return e.getClass().getSimpleName();
			}
		} else
			return e.getClass().getSimpleName();
	}

	public static String getExceptionStringFromThrowable(Throwable e) {
		if (e.getCause() != null) {
			if (e.getCause().getMessage() != null) {
				return e.getCause().getMessage();
			} else {
				return e.getClass().getSimpleName();
			}
		} else
			return e.getClass().getSimpleName();
	}

	public static HashMap<String, String> headers = null;

	public static Map<String, String> commonRequestHeaders() {
		Logger.i(TAG, "Entering commonRequestHeaders()");

		if (headers == null) {
			headers = new HashMap<String, String>();
			headers.put(
					Constants.RequestResponseHeaders.Content_Type,
					Constants.RequestResponseHeaders.Application_xwwwformurlencodedKey);
		}

		Logger.i(TAG, "Header :" + headers.toString());
		Logger.i(TAG, "Exiting commonRequestHeaders()");
		return headers;
	}

	public static byte[] encodedParameters(Map<String, String> params)
			throws UnsupportedEncodingException {
		Logger.i(TAG,
				"Entering encodedParameters() with params:" + params.toString());
		StringBuilder encodedParams = new StringBuilder();
		try {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				encodedParams
						.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
				encodedParams.append('=');
				encodedParams.append(URLEncoder.encode(entry.getValue(),
						"UTF-8"));
				encodedParams.append('&');
			}
			Logger.i(TAG, "Entering encodedParameters() with params:"
					+ encodedParams.toString());
			return encodedParams.toString().getBytes("UTF-8");
		} catch (UnsupportedEncodingException uee) {
			Logger.e(TAG, uee);
			throw new RuntimeException("Encoding not supported: " + "UTF-8",
					uee);
		} finally {
			Logger.i(TAG, "Exiting encodedParameters()");
		}
	}

	public static String encodedURLParameters(Map<String, String> params)
			throws UnsupportedEncodingException {
		Logger.i(TAG,
				"Entering encodedParameters() with params:" + params.toString());
		StringBuilder encodedParams = new StringBuilder();
		try {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				encodedParams
						.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
				encodedParams.append('=');
				encodedParams.append(URLEncoder.encode(entry.getValue(),
						"UTF-8"));
				encodedParams.append('&');
			}
			Logger.i(TAG, "Entering encodedParameters() with params:"
					+ encodedParams.toString().getBytes("UTF-8"));
			return encodedParams.toString();
		} catch (UnsupportedEncodingException uee) {
			Logger.e(TAG, uee);
			throw new RuntimeException("Encoding not supported: " + "UTF-8",
					uee);
		} finally {
			Logger.i(TAG, "Exiting encodedParameters()");
		}
	}

	public static boolean isValidUrl(String url) {
		Logger.i(TAG, "Entering isValidUrl() with url: " + url);
		return Uri.parse(url).getHost() != null ? true : false;
	}

	public static boolean isValidEmail(String email) {
		return Patterns.EMAIL_ADDRESS.matcher(email).matches();
	}

	private static boolean deleteDir(File dir) {
		Logger.i(TAG,
				"Entering deleteDir() with file: " + dir.getAbsolutePath());
		if (dir != null && dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		Logger.i(TAG, "Exiting deleteDir()");
		return dir.delete();
	}

	public static void clearApplicationCache(Context ctx) {
		Logger.i(TAG, "Entering clearApplicationCache()");
		File cache = ctx.getCacheDir();
		File appDir = new File(cache.getParent());
		if (appDir.exists()) {
			String[] children = appDir.list();
			for (String s : children) {
				// lib, cache, databases, shared_prefs
				if (s.equals("cache")) {
					deleteDir(new File(appDir, s));
				}
			}
		}
		Logger.i(TAG, "Exiting clearApplicationCache()");
	}

	public static String getUDID(Context context) {
		String udid = "emulator";
		if (Secure.getString(context.getContentResolver(), Secure.ANDROID_ID) != null)
			udid = Secure.getString(context.getContentResolver(),
					Secure.ANDROID_ID);

		return udid;
	}

	public static boolean isEditTextEmpty(EditText editField) {
		return TextUtils.isEmpty(editField.getText().toString());
	}

	public static void saveBooleanToSettings(Context context, String key,
			boolean val) {
		SharedPreferences osAppSettings = context.getSharedPreferences(
				Constants.App_Settings.apppref_filename, 0);
		SharedPreferences.Editor prefEditor = osAppSettings.edit();
		prefEditor.putBoolean(key, val);
		prefEditor.commit();
	}

	public static boolean getBooleanFromSettings(Context context, String key,
			boolean defVal) {
		return context.getSharedPreferences(
				Constants.App_Settings.apppref_filename, 0).getBoolean(key,
				defVal);
	}

	public static void saveStringToSettings(Context context, String key,
			String val) {
		SharedPreferences osAppSettings = context.getSharedPreferences(
				Constants.App_Settings.apppref_filename, 0);
		SharedPreferences.Editor prefEditor = osAppSettings.edit();
		prefEditor.putString(key, val);
		prefEditor.commit();
	}

	public static String getStringFromSettings(Context context, String key,
			String defVal) {
		return context.getSharedPreferences(
				Constants.App_Settings.apppref_filename, 0).getString(key,
				defVal);
	}

	public static String getAppProfile(Context ctx) {
		if (BuildConfig.DEBUG)
			return ctx.getString(R.string.debugprofile);
		else
			return ctx.getString(R.string.productionprofile);
	}

	public static boolean isAppInstalled(Context ctx, String uri) {
		PackageManager packageManager = ctx.getPackageManager();
		boolean installed = false;
		try {
			packageManager.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
			installed = true;
		} catch (PackageManager.NameNotFoundException e) {
			installed = false;
		}
		return installed;
	}

	public static String toTitleCase(String str) {
		if (str == null) {
			return null;
		}
		boolean space = true;
		StringBuilder builder = new StringBuilder(str);
		final int len = builder.length();

		for (int i = 0; i < len; ++i) {
			char c = builder.charAt(i);
			if (space) {
				if (!Character.isWhitespace(c)) {
					// Convert to title case and switch out of whitespace mode.
					builder.setCharAt(i, Character.toTitleCase(c));
					space = false;
				}
			} else if (Character.isWhitespace(c)) {
				space = true;
			} else {
				builder.setCharAt(i, Character.toLowerCase(c));
			}
		}

		return builder.toString();
	}

	private static final String HTTPS = "https://";
	private static final String HTTP = "http://";

	public static void openBrowser(final Context context, String url) {
		if (!url.startsWith(HTTP) && !url.startsWith(HTTPS)) {
			url = HTTP + url;
		}

		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		context.startActivity(Intent.createChooser(intent, "Chose browser"));

	}

	public static int getColumnWidth(Context mContext, int numColumns,
			int sideMargins, int columnSpacing) {
		int screenWidth = getScreenWidth(mContext);
		int colWidth = (screenWidth - (numColumns - 1) * columnSpacing - sideMargins * 2)
				/ numColumns;
		return colWidth;
	}

	private static DisplayMetrics outMetrics = new DisplayMetrics();

	public static int getScreenWidth(Context mContext) {
		Display display = ((Activity) mContext).getWindowManager()
				.getDefaultDisplay();
		display.getMetrics(outMetrics);

		int screenWidth = (int) outMetrics.widthPixels;
		return screenWidth;
	}

	/**
	 * Display string.
	 * 
	 * @param inStr
	 *            the in str
	 * @return the string
	 */
	public static final String displayString(String inStr) {
		if (inStr == null) {
			Logger.d("UIUtilities",
					"inStr should not be null, but if null then we will return empty");
			return Constants.EMPTY_STRING;
		}

		inStr = inStr.replace("&amp;amp", Constants.SpecialCharacters.AMP);
		inStr = inStr.replace("&amp;", Constants.SpecialCharacters.AMP);
		inStr = inStr.replace("&trade;", Constants.SpecialCharacters.TRADEMARK);
		inStr = inStr.replace("&lt;", Constants.SpecialCharacters.LESSTHEN);
		inStr = inStr.replace("&gt;", Constants.SpecialCharacters.MORETHEN);
		inStr = inStr.replace("&reg;", Constants.SpecialCharacters.REG);
		inStr = inStr
				.replace("&#039;", Constants.SpecialCharacters.SINGLEQUOTE);
		inStr = inStr.replace("&quot;", Constants.SpecialCharacters.QUOTE);
		inStr = inStr.replace("&quot;;", Constants.SpecialCharacters.QUOTE);
		inStr = inStr.replace("&#8230;", Constants.SpecialCharacters.ELLIPSIZE);
		inStr = inStr.replace("&apos;", "'");
		inStr = inStr.replace("&copy;", Constants.SpecialCharacters.COPYRIGHT);
		inStr = inStr.replace("&#153;", Constants.SpecialCharacters.TRADEMARK);
		inStr = inStr.replace("&#174;", Constants.SpecialCharacters.REG);
		inStr = inStr.replace("&mdash;", Constants.SpecialCharacters.MDASH);
		inStr = inStr.replace("&euro;", Constants.SpecialCharacters.EURO);
		inStr = inStr.replace("&ldquo;", Constants.SpecialCharacters.LDQUO);
		inStr = inStr.replace("&rdquo;", Constants.SpecialCharacters.RDQUO);
		inStr = inStr.replace("&nbsp;", Constants.SPACE);
		inStr = inStr.replace("</n>", "");
		return inStr;
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	public static void removeOnGlobalLayoutListener(View v,
			ViewTreeObserver.OnGlobalLayoutListener listener) {
		if (Build.VERSION.SDK_INT < 16) {
			v.getViewTreeObserver().removeGlobalOnLayoutListener(listener);
		} else {
			v.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
		}
	}

	public static void stripUnderlines(Context ctx, TextView textView,
			ActivityInterface act) {
		Spannable s = Spannable.Factory.getInstance().newSpannable(
				textView.getText());
		URLSpan[] spans = s.getSpans(0, s.length(), URLSpan.class);
		for (URLSpan span : spans) {
			int start = s.getSpanStart(span);
			int end = s.getSpanEnd(span);
			s.removeSpan(span);
			span = new URLSpanWithoutUnderline(span.getURL(), act);
			s.setSpan(span, start, end, 0);
		}
		textView.setText(s);
	}

	public static void linkify(Context ctx, TextView tv, String expression,
			String type, String id, ActivityInterface act) {
		Pattern wordMatcher = Pattern.compile(Pattern.quote(expression));

		String viewURL = "app://feed?type=" + type + "&id=" + id + "&link=";

		tv.setLinkTextColor(ctx.getResources().getColor(R.color.blue));
		Linkify.addLinks(tv, wordMatcher, viewURL);

		stripUnderlines(ctx, tv, act);
	}

	@SuppressWarnings("deprecation")
	public static void copyTxtToClipboardManager(Context ctx, String text) {
		int sdk = android.os.Build.VERSION.SDK_INT;
		if (sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
			android.text.ClipboardManager clipboard = (android.text.ClipboardManager) ctx
					.getSystemService(Context.CLIPBOARD_SERVICE);
			clipboard.setText(text);
		} else {
			android.content.ClipboardManager clipboard = (android.content.ClipboardManager) ctx
					.getSystemService(Context.CLIPBOARD_SERVICE);
			android.content.ClipData clip = android.content.ClipData
					.newPlainText(text, text);
			clipboard.setPrimaryClip(clip);
		}
	}

	public static String convertToDuration(Long millis) {
		return new SimpleDateFormat("mm:ss", Locale.US)
				.format(new Date(millis));
	}
}
