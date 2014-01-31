package com.app.activities;

import android.os.Bundle;

import com.app.R;
import com.app.activities.base.AppFragmentActivity;

public class MainActivity extends AppFragmentActivity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		setSupportProgressBarIndeterminateVisibility(false);
	}

	@Override
	public boolean isHomeButtonEnabled() {
		return false;
	}
}
