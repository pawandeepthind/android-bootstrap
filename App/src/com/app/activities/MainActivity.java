package com.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.app.R;
import com.app.activities.base.AppFragmentActivity;

public class MainActivity extends AppFragmentActivity {
	private Button startTestActivityBtn;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		setupUIConnection();
		setupUIEvents();
	}

	@Override
	public boolean isHomeButtonEnabled() {
		return false;
	}

	private void setupUIConnection() {
		startTestActivityBtn = (Button) findViewById(R.id.startTestActivityBtn);
	}

	private void setupUIEvents() {
		startTestActivityBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startTestActivityBtn();
			}
		});
	}

	protected void startTestActivityBtn() {
		startActivity(new Intent(this, TestActivity.class).putExtra("term",
				"linkinpark"));
	}
}
