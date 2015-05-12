package com.share2pley.share2pleyapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Window;

public class StartActivity extends SingleFragmentActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
	}

	@Override
	protected Fragment createFragment() {
		return new StartFragment();
	}
}
