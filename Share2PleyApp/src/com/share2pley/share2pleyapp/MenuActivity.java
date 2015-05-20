package com.share2pley.share2pleyapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends Activity {
	Button mChooseSetButton;
	Button mAboutButton;
	Button mOtherAppsButton;
	Button mReferencesButton;
	Button mExitButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);

		/**
		 * find the choose set button in the menu and start its activity
		 */
		mChooseSetButton = (Button)findViewById(R.id.button_menu_choose_set);
		mChooseSetButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getBaseContext(), ChooseActivity.class);
				startActivity(i);
			}
		});
		/**
		 * find the about button in the menu and start its activity
		 */
		mAboutButton = (Button)findViewById(R.id.button_menu_about_us);
		mAboutButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(getBaseContext(), AboutUsActivity.class);
				startActivity(i);
			}
		});
		/**
		 * find the reference button in the menu and start its activity
		 */
		mReferencesButton = (Button)findViewById(R.id.button_menu_references);
		mReferencesButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(getBaseContext(), ReferenceActivity.class);
				startActivity(i);
			}
		});
		/**
		 * find the exit button and close application
		 */
		mExitButton = (Button)findViewById(R.id.button_menu_exit);
		mExitButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				System.exit(0);
			}
		});
	}

}
