package com.share2pley.share2pleyapp;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NameActivity extends Activity{
	Button mNextButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		//Setup the GUI with the corresponding XML file
		insertIntoDataBase();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_name_fill);

		mNextButton = (Button)findViewById(R.id.confirm_button);
		mNextButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getBaseContext(), MenuActivity.class);
				startActivity(i);
				finish();
			}
		});		
	}
	
	public void insertIntoDataBase() {
		SQLiteDatabase spDatabase = openOrCreateDatabase("Share2Pley",MODE_PRIVATE,null);
		spDatabase.execSQL("INSERT INTO Person (Firstname, Lastname, Cleared) VALUES ('TEST', 'TEST', 'TEST') ");
	}

}
