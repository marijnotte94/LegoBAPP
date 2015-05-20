package com.share2pley.share2pleyapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TimeActivity extends Activity{

	private TextView text;
	private Button button;
	int time  = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_time);
		Intent i = getIntent();
		Bundle b = i.getExtras();
		if(b != null){
			time = b.getInt("TIME");
		}
		text = (TextView)findViewById(R.id.timeView);
		text.setText(time + "");

		button = (Button)findViewById(R.id.goToPhoto);
		button.setOnClickListener(new View.OnClickListener(){
			
			public void onClick(View v){ 
				Intent i = new Intent(getBaseContext(), TakePhotoActivity.class);
				startActivity(i);
			}
		});
	}
}

