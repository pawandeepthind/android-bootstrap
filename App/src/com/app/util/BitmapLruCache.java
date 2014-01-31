package com.app.util;

import android.graphics.Bitmap;

import com.android.volley.toolbox.ImageLoader.ImageCache;

public class BitmapLruCache extends
		android.support.v4.util.LruCache<String, Bitmap> implements ImageCache {
	
	private static String TAG = BitmapLruCache.class.getSimpleName();

	public BitmapLruCache(int maxSize) {
		super(maxSize);
		Logger.i(TAG, "Creating BitmapLruCache object");
	}

	@Override
	protected int sizeOf(String key, Bitmap value) {
		return value.getRowBytes() * value.getHeight();
	}

	@Override
	public Bitmap getBitmap(String url) {
		Logger.i(TAG, "Calling getBitmap() with url " + url);
		return get(url);
	}

	@Override
	public void putBitmap(String url, Bitmap bitmap) {
		Logger.i(TAG, "Putting getBitmap() with url " + url);
		put(url, bitmap);
	}
}
