package com.share2pley.share2pleyapp;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.share2pley.share2pleyapp.Model.Missing;



public class ResultActivity extends Activity {

	private Button mExit;
	private DBHelper mDBHelper;
	private List<Missing> mMissings;
	private ImageView mReportImage;
	private static final String TAG = "ResultActivity";
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		String data = prefs.getString("string_id", "no id");
		Bitmap bp = StringToBitMap(data);
		

		
		

		mReportImage = (ImageView)findViewById(R.id.imageView_final_set);
		mReportImage.setImageBitmap(bp);

		TextView missingTextView = (TextView) findViewById(R.id.textView_result_missing);
		mDBHelper = new DBHelper(this);
		mMissings = mDBHelper.getMissingBicksById();
		for (Missing m : mMissings) {
			missingTextView.append(m.toString() + "\n");
		}

		mExit = (Button) findViewById(R.id.button_result_exit);
		mExit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
				Intent i = new Intent(getBaseContext(), MenuActivity.class);
				startActivity(i);
			}
		});

	}
	
	public Bitmap StringToBitMap(String encodedString){
		   try {
		      byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
		      Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
		      return bitmap;
		   } catch(Exception e) {
		      e.getMessage();
		      return null;
		   }
		}
	
}