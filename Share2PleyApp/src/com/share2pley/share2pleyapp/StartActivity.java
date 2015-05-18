package com.share2pley.share2pleyapp;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class StartActivity extends Activity {
	Button mStartButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setDatabase();
//		spDatabase.close();
		
		//Setup the GUI with the corresponding XML file
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		
		/**
		 * StartButton with new OnClickListener
		 * Starts new NameActivity and finishes the StartActivity
		 */
		mStartButton = (Button)findViewById(R.id.start_button);
		mStartButton.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getBaseContext(), NameActivity.class);
				startActivity(i);
			}
		});
	}
	
	/**
	 * This method sets the Database
	 * If not exists it creates one Person table, with first name, last name and cleared values.
	 */
	private void setDatabase() {
		SQLiteDatabase spDatabase = openOrCreateDatabase("Share2Pley",MODE_PRIVATE,null);
		spDatabase.execSQL("CREATE TABLE IF NOT EXISTS Person(Firstname VARCHAR(15), Lastname VARCHAR(30), Cleared VARCHAR(6)) ");
		spDatabase.close();
	}
}
