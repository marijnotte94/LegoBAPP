package com.share2pley.share2pleyapp;

import java.text.DecimalFormat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TimeActivity extends Activity{

	private TextView text;
	private Button button;
	private long time;
	private long minutes;
	private long seconds;

	//display time after instructions finished
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_time);
		Intent i = getIntent();
		Bundle b = i.getExtras();
		if(b != null){
			time = b.getLong("TIME");
		}
		text = (TextView)findViewById(R.id.timeView);
		text.setText(timeToString(time));

		button = (Button)findViewById(R.id.goToPhoto);
		button.setOnClickListener(new View.OnClickListener(){

			public void onClick(View v){ 
				Intent i = new Intent(getBaseContext(), TakePhotoActivity.class);
				startActivity(i);
			}
		});
	}
	//convert time to mm:ss
	public String timeToString(long time){
		DecimalFormat formatter = new DecimalFormat("00");
		
		time = time / 1000000000;
		minutes = time / 60;
		seconds = time % 60;
		
		String secondsFormat = formatter.format(seconds);
		String minutesFormat = formatter.format(minutes);
		
		return "Your time is " + minutesFormat + ":" + secondsFormat;

	}
}

