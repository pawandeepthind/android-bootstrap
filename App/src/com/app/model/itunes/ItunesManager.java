package com.app.model.itunes;

import java.io.UnsupportedEncodingException;
import java.security.InvalidParameterException;
import java.util.HashMap;

import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.app.helper.AppHelper;
import com.app.model.manager.Manager;
import com.app.util.JacksonRequest;
import com.app.util.Logger;
import com.app.util.Utils;

public class ItunesManager extends Manager {
	private static String TAG = ItunesManager.class.getSimpleName();

	public ItunesManager() {

	}

	public JacksonRequest<ItunesSearchResults> getSearch(
			HashMap<String, String> params,
			Listener<ItunesSearchResults> listener, ErrorListener errorListener) {
		//Validate input parameters
		if(params==null || !params.containsKey("terms")) {
			throw new InvalidParameterException("term parameter is required");
		}
		
		String url = SERVERLINK;
		try {
			url += "search" + Utils.encodedURLParameters(params);
		} catch (UnsupportedEncodingException e) {
			Logger.i(TAG, e.toString());
		}
		JacksonRequest<ItunesSearchResults> request = new JacksonRequest<ItunesSearchResults>(
				Request.Method.GET, url, ItunesSearchResults.class, null,
				listener, errorListener);
		AppHelper.getRequestQueue().add(request);
		return request;
	}
}
