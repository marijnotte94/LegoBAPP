package com.share2pley.share2pleyapp;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
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
	private int mMissingBricks;
	private int mSetIndex;
	private Set mSet;
	private ImageView mSetImage;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		TextView messageTextView = (TextView) findViewById(R.id.textView_final_intro);

		mSetIndex = getIntent().getExtras().getInt("SETNO");

		mSet = SetLab.get(this).getSet(mSetIndex);
		mSetImage = (ImageView) findViewById(R.id.imageView_final_set);
		mSetImage.setImageResource(mSet.getModelImageResource(mSetIndex));

		TextView missingTextView = (TextView) findViewById(R.id.textView_result_missing);
		mDBHelper = new DBHelper(this);
		mMissings = mDBHelper.getMissingBicksById();
		if (mMissings.isEmpty()) {
			messageTextView
					.setText("Well done! You cleared the whole set without missing pieces!");
		}
		for (Missing m : mMissings) {
			missingTextView.append(m.toString() + "\n");
			mMissingBricks += m.getAmount();
		}
		if (mMissingBricks <= 10 && mMissingBricks > 0) {
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
}
