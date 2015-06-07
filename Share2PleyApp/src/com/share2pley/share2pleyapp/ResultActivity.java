package com.share2pley.share2pleyapp;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.share2pley.share2pleyapp.Model.Missing;

public class ResultActivity extends Activity {

	private Button mExit;
	private DBHelper mDBHelper;
	private List<Missing> mMissings;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);

		TextView messageTextView = (TextView) findViewById(R.id.textView_final_intro);
		TextView missingTextView = (TextView) findViewById(R.id.textView_result_missing);
		mDBHelper = new DBHelper(this);
		mMissings = mDBHelper.getMissingBicksById();
		if (mMissings.isEmpty()) {
			messageTextView
					.setText("Well done! You cleared the whole set without missing pieces!");
		}
		for (Missing m : mMissings) {
			missingTextView.append(m.toString() + "\n");
		}
		mExit = (Button) findViewById(R.id.button_result_exit);
		mExit.setOnClickListener(new View.OnClickListener() {
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
