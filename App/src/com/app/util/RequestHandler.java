package com.app.util;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.json.JSONObject;

import android.content.Context;
import android.content.res.Resources;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.app.R;

public class RequestHandler {
	private Context context;
	private String baseUrl;

	public RequestHandler(Context ctx) {
		this.context = ctx;
		Resources res = this.context.getResources();
		String profile = Utils.getAppProfile(ctx);
		this.baseUrl = res.getString(R.string.public_api, profile);
	}

	public StringRequest getStringData(String query, Listener<String> listener,
			ErrorListener errorListener) {
		StringRequest strRequest = new StringRequest(this.baseUrl + query,
				listener, errorListener) {
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				return Utils.commonRequestHeaders();
			}
		};
		return strRequest;
	}

	public StringRequest postStringRequest(String query,
			final Map<String, String> params, Listener<String> listener,
			ErrorListener errorListener) {
		StringRequest strRequest = new StringRequest(Request.Method.POST,
				this.baseUrl + query, listener, errorListener) {

			@Override
			public byte[] getBody() {
				try {
					return Utils.encodedParameters(params);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				return null;
			}

			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				return Utils.commonRequestHeaders();
			}
		};
		return strRequest;
	}
	
	public StringRequest deleteStringRequest(String query,
			final Map<String, String> params, Listener<String> listener,
			ErrorListener errorListener) {
		StringRequest strRequest = new StringRequest(Request.Method.DELETE,
				this.baseUrl + query, listener, errorListener) {

			@Override
			public byte[] getBody() {
				try {
					return Utils.encodedParameters(params);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				return null;
			}

			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				return Utils.commonRequestHeaders();
			}
		};
		return strRequest;
	}

	public JsonObjectRequest getJsonData(String query,
			Listener<JSONObject> listener, ErrorListener errorListener) {
		JsonObjectRequest jsObjRequest = new JsonObjectRequest(
				Request.Method.GET, this.baseUrl + query, null, listener,
				errorListener) {

			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				return Utils.commonRequestHeaders();
			}

		};
		return jsObjRequest;
	}

	public JsonObjectRequest postJsonData(String query,
			final Map<String, String> params, Listener<JSONObject> listener,
			ErrorListener errorListener) {
		JsonObjectRequest jsObjRequest = new JsonObjectRequest(
				Request.Method.POST, this.baseUrl + query, null, listener,
				errorListener) {

			@Override
			public byte[] getBody() {
				try {
					return Utils.encodedParameters(params);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				return null;
			}

			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				return Utils.commonRequestHeaders();
			}

		};
		return jsObjRequest;
	}
}
