package com.app.activities;

import java.util.HashMap;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.app.R;
import com.app.activities.base.AppFragmentActivity;
import com.app.adapter.TestListAdapter;
import com.app.model.itunes.ItunesManager;
import com.app.model.itunes.ItunesSearchResults;

public class TestActivity extends AppFragmentActivity {
	private ListView listView;
	private ItunesSearchResults searchResults;
	private TestListAdapter adapter;
	private String term;

	private TestListAdapter getAdapter() {
		if (adapter == null) {
			adapter = new TestListAdapter(this);
		}
		return adapter;
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);

		setupUIConnection();
		setupUIEvents();

		if (savedInstanceState != null) {
			this.term = (String) savedInstanceState.getString("term");
			this.searchResults = (ItunesSearchResults) savedInstanceState
					.getSerializable("searchresult");

			if (this.searchResults == null) {
				loadInitialData(this.term);
			}

			setUpUI();
		} else {
			if (getIntent().hasExtra("term")) {
				this.term = getIntent().getStringExtra("term");
			}
			loadInitialData(this.term);
		}

	}

	private void setUpUI() {
		getAdapter().setItems(this.searchResults.getResults());
		listView.setAdapter(getAdapter());
		getAdapter().notifyDataSetChanged();
	}

	private void loadInitialData(final String term) {
		ItunesManager itunesManager = new ItunesManager();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("term", term);
		startProgressInView();
		itunesManager.getSearch(params, new Listener<ItunesSearchResults>() {

			@Override
			public void onResponse(ItunesSearchResults response) {
				stopAndHideProgressInView();
				TestActivity.this.searchResults = response;
				setUpUI();
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				stopAndHideProgressInView();
				handleVolleyError(error,
						"Unable to load the data. Please tap to refresh!",
						new OnClickListener() {

							@Override
							public void onClick(View v) {
								loadInitialData(term);
							}
						});
			}
		});
	}

	private void setupUIConnection() {
		listView = (ListView) findViewById(R.id.listView);
	}

	private void setupUIEvents() {
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				onCellTapped(position);
			}
		});
	}

	protected void onCellTapped(int position) {
		showToastMsg("Row: " + Integer.toString(position), Toast.LENGTH_SHORT);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putSerializable("searchresult", this.searchResults);
		super.onSaveInstanceState(outState);
	}
}
