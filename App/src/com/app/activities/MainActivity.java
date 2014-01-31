package com.app.activities;

import android.os.Bundle;

import com.app.activities.base.AppFragmentActivity;

public class MainActivity extends AppFragmentActivity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setSupportProgressBarIndeterminateVisibility(false);
	}
}
