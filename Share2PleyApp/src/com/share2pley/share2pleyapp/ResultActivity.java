package com.share2pley.share2pleyapp;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.share2pley.share2pleyapp.Model.Missing;
import com.share2pley.share2pleyapp.Model.Set;
import com.share2pley.share2pleyapp.Model.SetLab;



public class ResultActivity extends Activity {


	private Button mExit;
	private DBHelper mDBHelper;
	private List<Missing> mMissings;
	private int mSetIndex;
	private Set mSet;
	private ImageView mSetImage;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);

		
		Intent i = getIntent();
		Bundle b = i.getExtras();
		if (b != null) {
			mSetIndex = b.getInt("SETNO");
		}
		
		mSet = SetLab.get(this).getSet(mSetIndex);
		Log.d("index", mSetIndex +"");
		mSetImage = (ImageView)findViewById(R.id.imageView_final_set);
		mSetImage.setImageResource(mSet.getModelImageResource(mSetIndex));
		
		


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
}
