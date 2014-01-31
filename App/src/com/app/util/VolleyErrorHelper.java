package com.app.util;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.app.R;
import com.app.model.error.Error;
import com.app.model.error.Items;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class VolleyErrorHelper {
	/**
	 * Returns appropriate message which is to be displayed to the user against
	 * the specified error object.
	 * 
	 * @param error
	 * @param context
	 * @return
	 */
	public static String getMessage(Object error, Context context) {
		if (isServerProblem(error)) {
			return handleServerError(error, context);
		}
		return context.getResources().getString(R.string.generic_error);
	}

	@SuppressWarnings("unused")
	private static boolean isParseError(Object error) {
		return (error instanceof ParseError);
	}

	/**
	 * Determines whether the error is related to network
	 * 
	 * @param error
	 * @return
	 */
	@SuppressWarnings("unused")
	private static boolean isNetworkProblem(Object error) {
		return (error instanceof NetworkError)
				|| (error instanceof NoConnectionError);
	}

	/**
	 * Determines whether the error is related to server
	 * 
	 * @param error
	 * @return
	 */
	private static boolean isServerProblem(Object error) {
		return (error instanceof ServerError)
				|| (error instanceof AuthFailureError);
	}

	/**
	 * Handles the server error, tries to determine whether to show a stock
	 * message or to show a message retrieved from the server.
	 * 
	 * @param err
	 * @param context
	 * @return
	 */
	private static String handleServerError(Object err, Context context) {
		VolleyError error = (VolleyError) err;

		NetworkResponse response = error.networkResponse;

		if (response != null) {
			switch (response.statusCode) {
			case 404:
			case 422:
			case 417:
			case 401:
				String volleyErrorData = new String(error.networkResponse.data);
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.configure(
						DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
						false);

				try {
					Error errorDto = objectMapper.readValue(volleyErrorData,
							Error.class);
					StringBuilder errorMessage = new StringBuilder(
							Constants.EMPTY_STRING);
					boolean flag = false;
					for (Items item : errorDto.getCollection().getItems()) {
						if (flag)
							errorMessage.append(Constants.NEWLINE);

						errorMessage.append(item.getProperties().getMessage());
						flag = true;
					}

					return errorMessage.toString();
				} catch (Exception e) {
					return context.getResources().getString(
							R.string.generic_error);
				}

			default:
				Logger.e("VolleyErrorHelper", "Server Error: response code: "
						+ Integer.toString(response.statusCode));
				return context.getResources().getString(R.string.generic_error);
			}
		}
		return context.getResources().getString(R.string.generic_error);
	}
}
