package com.share2pley.share2pleyapp;

import java.text.DecimalFormat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * 
 * @author Marijn Otte - ?
 * 
 */
public class TimeActivity extends Activity {

	private TextView mText;
	private Button mButton;
	private long mTime;

	// display time after instructions finished
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_time);
		Bundle b = getIntent().getExtras();
		if (b != null) {
			mTime = b.getLong("TIME");
			
		}
		mText = (TextView) findViewById(R.id.timeView);
		mText.setText(timeToString(mTime));

		mButton = (Button) findViewById(R.id.goToPhoto);
		mButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(getBaseContext(), ResultActivity.class);				
				startActivity(i);
				finish();
			}
		});
	}

	// convert time to mm:ss
	public String timeToString(long time) {
		DecimalFormat formatter = new DecimalFormat("00");

		time = time / 1000000000;
		long mMinutes = time / 60;
		long mSeconds = time % 60;

		String secondsFormat = formatter.format(mSeconds);
		String minutesFormat = formatter.format(mMinutes);

		return "Your time is " + minutesFormat + ":" + secondsFormat;

	}
}
