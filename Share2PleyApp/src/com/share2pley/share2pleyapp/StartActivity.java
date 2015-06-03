package com.share2pley.share2pleyapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

//activity to homescren.
/**
 * 
 * @author Richard Vink - 4233867
 * 
 */
public class StartActivity extends Activity {
	Button mStartButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		// Setup the GUI with the corresponding XML file
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);

		/**
		 * StartButton with new OnClickListener Starts new NameActivity and
		 * finishes the StartActivity
		 */
		mStartButton = (Button) findViewById(R.id.button_start);
		mStartButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getBaseContext(), NameActivity.class);
				startActivity(i);
			}
		});
	}
}
