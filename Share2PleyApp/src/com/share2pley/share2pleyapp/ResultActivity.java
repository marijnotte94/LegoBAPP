package com.share2pley.share2pleyapp;

import java.util.List;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
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
	private int mMissingBricks;
	private int mSetIndex;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		String data = prefs.getString("string_id", "no id");
		Bitmap bp = StringToBitMap(data);

		mReportImage = (ImageView) findViewById(R.id.imageView_final_set);
		mReportImage.setImageBitmap(bp);

		TextView messageTextView = (TextView) findViewById(R.id.textView_final_intro);

		mSetIndex = getIntent().getExtras().getInt("SETNO");

		TextView missingTextView = (TextView) findViewById(R.id.textView_result_missing);
		mDBHelper = new DBHelper(this);
		mMissings = mDBHelper.getMissingBicksById();
		for (Missing m : mMissings) {
			missingTextView.append(m.toString() + "\n");
			mMissingBricks += m.getAmount();
		}

		if (mMissings.isEmpty()) {
			messageTextView
					.setText("Well done! You cleared the whole set without missing pieces!");
		}

		else if (mMissingBricks <= 10 && mMissingBricks > 0) {
			messageTextView.setText("Good job! You cleared the whole set with "
					+ mMissingBricks + " bricks missing, try looking for them");
		} else {
			messageTextView.setText("Okay, you miss " + mMissingBricks
					+ " bricks. Try to find them.");
		}

		mExit = (Button) findViewById(R.id.button_result_exit);
		mExit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mDBHelper.deleteMissings();
				mDBHelper.updateSetContent(mSetIndex, 1);
				finish();
			}
		});

	}

	public Bitmap StringToBitMap(String encodedString) {
		try {
			byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
			Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0,
					encodeByte.length);
			return bitmap;
		} catch (Exception e) {
			e.getMessage();
			return null;
		}
	}

}
