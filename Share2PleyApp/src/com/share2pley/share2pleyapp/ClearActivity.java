package com.share2pley.share2pleyapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.share2pley.share2pleyapp.Model.Brick;
import com.share2pley.share2pleyapp.Model.Set;
import com.share2pley.share2pleyapp.Model.SetLab;

/**
 * 
 * @author Marijn Otte - & Richard Vink 4233867
 * 
 */
public class ClearActivity extends Activity {

	private Set mSet;
	private int mSetIndex;
	private TextView mMessage;
	private Button mNextButton, mPreviousButton, mMissingButton;
	private Brick mCurrent;
	private ImageView mFigure;
	private ImageView mBrick;
	private ImageView mBag;
	private int mBrickIndex = 0;
	private long xNewPos, yNewPos;
	private long mStartTime, mEndTime;
	private ProgressBar mProgressbar;
	private double mAmountBricks;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clear);

		// find set belonging to button pressed
		Intent i = getIntent();
		Bundle b = i.getExtras();
		if (b != null) {
			mSetIndex = b.getInt("page");
		}
		mSet = SetLab.get(this).getSet(mSetIndex);
		mAmountBricks = mSet.getAmountBricks();

		// start timer
		mMessage = (TextView) findViewById(R.id.textview_message_instruction);
		mProgressbar = (ProgressBar) findViewById(R.id.progressbar_clear);
		mStartTime = System.nanoTime();
		update();

		// update(index);

		// match index with array
		mFigure = (ImageView) findViewById(R.id.imageView_clear_figurine);
		mFigure.setImageResource(SetLab.get(this).getSet(mSetIndex)
				.getFigurineImageResource(mSetIndex));

		// next button pressed for new instruction
		mNextButton = (Button) findViewById(R.id.button_clear_next);
		mNextButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// If there is next instruction, display instruction
				if (mSet.hasNext(mBrickIndex)) {
					mBrickIndex++;
					update();
					double t = 1.0 / mAmountBricks;
					int d = (int) (t * 100);
					mProgressbar.setProgress(mProgressbar.getProgress() + d);
				}
				// If no more instructions go to timeactivity
				else {
					mEndTime = System.nanoTime();
					long mClearTime = (mEndTime - mStartTime);
					Intent i = new Intent(getBaseContext(), TimeActivity.class);
					i.putExtra("TIME", mClearTime);
					startActivity(i);
				}
			}
		});

		mPreviousButton = (Button) findViewById(R.id.button_clear_previous);
		mPreviousButton.setOnClickListener(new View.OnClickListener() {
			// find previous instruction
			@Override
			public void onClick(View v) {
				if (mSet.hasPrevious(mBrickIndex)) {
					mBrickIndex--;
					update();
					mProgressbar.setProgress(mProgressbar.getProgress() - 1);
				}
				// at first instruction: go to choose set screen
				else {
					finish();
				}
			}
		});

		mMissingButton = (Button) findViewById(R.id.button_clear_missing);
		mMissingButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//
			}
		});
	}

	// find instruction if previous or next button is pushed + layout of letters
	// so that its readable
	public void update() {
		ImageView brick = (ImageView) findViewById(R.id.imageview_brick);
		mCurrent = mSet.getBrick(mBrickIndex);
		brick.setImageResource(mCurrent.getSource());
		mMessage.setText(mCurrent.getAmount() + "x");
		animateBrick();
	}

	// animation of brick into bag
	public void animateBrick() {
		mBrick = (ImageView) findViewById(R.id.imageview_brick);
		mBag = (ImageView) findViewById(R.id.imageview_bag);

		xNewPos = mBag.getLeft() - mBrick.getLeft();
		yNewPos = mBag.getTop() - mBrick.getTop();

		mBrick.bringToFront();

		Animation transAnim = new TranslateAnimation(0, xNewPos, 0, yNewPos);
		transAnim.setRepeatCount(transAnim.INFINITE);

		Animation alphaAnim = new AlphaAnimation(1, 0);
		alphaAnim.setRepeatCount(alphaAnim.INFINITE);

		AnimationSet set = new AnimationSet(true);
		set.addAnimation(transAnim);
		set.addAnimation(alphaAnim);
		set.setDuration(1500);

		mBrick.startAnimation(set);
	}

}
