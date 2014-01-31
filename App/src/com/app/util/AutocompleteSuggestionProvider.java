package com.app.util;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class AutocompleteSuggestionProvider extends ContentProvider {
	private static final String LOG_TAG = "OS AutoComplete";

	public static final String AUTHORITY = "com.app.search_suggestion_provider";
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
			+ "/search");

	// UriMatcher constant for search suggestions
	private static final int SEARCH_SUGGEST = 1;

	private static final UriMatcher uriMatcher;

	private static final String[] SEARCH_SUGGEST_COLUMNS = { BaseColumns._ID,
			SearchManager.SUGGEST_COLUMN_ICON_1,
			SearchManager.SUGGEST_COLUMN_TEXT_1,
			SearchManager.SUGGEST_COLUMN_QUERY, };

	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(AUTHORITY, SearchManager.SUGGEST_URI_PATH_QUERY,
				SEARCH_SUGGEST);
		uriMatcher.addURI(AUTHORITY, SearchManager.SUGGEST_URI_PATH_QUERY
				+ "/*", SEARCH_SUGGEST);
	}

	@Override
	public int delete(Uri uri, String arg1, String[] arg2) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getType(Uri uri) {
		switch (uriMatcher.match(uri)) {
		case SEARCH_SUGGEST:
			return SearchManager.SUGGEST_MIME_TYPE;
		default:
			throw new IllegalArgumentException("Unknown URL " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean onCreate() {

		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		Logger.d(LOG_TAG, "uri = " + uri);

		// Use the UriMatcher to see what kind of query we have
		switch (uriMatcher.match(uri)) {
		case SEARCH_SUGGEST:
			if (uri.getLastPathSegment() != null
					&& !uri.getLastPathSegment().toString()
							.equals("search_suggest_query")) {
				// String q = uri.getLastPathSegment().toString();
				final MatrixCursor cursor = new MatrixCursor(
						SEARCH_SUGGEST_COLUMNS, 1);

				//
				return cursor;
			} else {
				final MatrixCursor cursor = new MatrixCursor(
						SEARCH_SUGGEST_COLUMNS, 1);

				return cursor;
			}

		default:
			throw new IllegalArgumentException("Unknown Uri: " + uri);
		}
	}

	@Override
	public int update(Uri uri, ContentValues arg1, String arg2, String[] arg3) {
		throw new UnsupportedOperationException();
	}
}