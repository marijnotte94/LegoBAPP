package com.share2pley.share2pleyapp;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.share2pley.share2pleyapp.Model.Set;
import com.share2pley.share2pleyapp.Model.SetLab;

public class MenuActivity extends Activity {
	Button mChooseSetButton;
	Button mAboutButton;
	Button mOtherAppsButton;
	Button mReferencesButton;
	Button mExitButton;
	
	//menu with 4 buttons: choose set, other apps, about us and exit
	
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
				
				Intent i = new Intent(getBaseContext(), SetPagerActivity.class);
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
			
			
			//exit app
			@Override
			public void onClick(View v) {
				finish();
				Intent intent = new Intent(Intent.ACTION_MAIN);
				intent.addCategory(Intent.CATEGORY_HOME);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
			}
		});
	}

}