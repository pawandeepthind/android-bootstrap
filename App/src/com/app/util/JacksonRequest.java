package com.app.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.app.helper.AppHelper;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * Volley adapter for JSON requests that will be parsed into Java objects by
 * Jackson.
 */
public class JacksonRequest<T> extends Request<T> {
	private final Class<T> clazz;
	private final Map<String, String> headers;
	private final Listener<T> listener;

	private static String TAG = JacksonRequest.class.getSimpleName();

	/**
	 * Make a GET request and return a parsed object from JSON.
	 * 
	 * @param url
	 *            URL of the request to make
	 * @param clazz
	 *            Relevant class object, for Gson's reflection
	 * @param headers
	 *            Map of request headers
	 */
	public JacksonRequest(int method, String url, Class<T> clazz,
			Map<String, String> headers, Listener<T> listener,
			ErrorListener errorListener) {
		super(method, url, errorListener);
		this.clazz = clazz;
		this.headers = headers;
		this.listener = listener;
		Logger.i(
				TAG,
				"Constructing JacksonRequest object with URL: " + url + ": "
						+ clazz.getSimpleName() + "Method: "
						+ Integer.toString(method));
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		return headers != null ? headers : super.getHeaders();
	}

	@Override
	protected void deliverResponse(T response) {
		listener.onResponse(response);
	}

	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
		Logger.i(TAG, "Entering parseNetworkResponse()" + response.toString());
		try {
			return Response.success(AppHelper.getObjectMapper()
					.readValue(response.data, clazz), HttpHeaderParser
					.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			Logger.e(TAG, e);
			return Response.error(new ParseError(e));
		} catch (JsonParseException e) {
			Logger.e(TAG, e);
			return Response.error(new ParseError(e));
		} catch (JsonMappingException e) {
			Logger.e(TAG, e);
			return Response.error(new ParseError(e));
		} catch (IOException e) {
			Logger.e(TAG, e);
			return Response.error(new ParseError(e));
		} finally {
			Logger.i(TAG, "Exiting parseNetworkResponse()");
		}
	}
}
