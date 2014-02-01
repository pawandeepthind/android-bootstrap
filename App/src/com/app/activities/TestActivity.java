package com.app.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.app.R;
import com.app.activities.base.AppFragmentActivity;

public class TestActivity extends AppFragmentActivity {
	private ListView listView;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);

		setupUIConnection();
		setupUIEvents();
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
}
