package com.share2pley.share2pleyapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
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
import com.share2pley.share2pleyapp.Model.TextProgressBar;

public class ClearActivity extends FragmentActivity {

	/**
	 * 
	 * @author Marijn Otte - & Richard Vink 4233867
	 * 
	 */
	private Set mSet;
	private int mSetIndex;
	private TextView mMessage;
	private Button mNextButton, mPreviousButton, mMissingButton;
	private Brick mCurrent;
	private ImageView mFigure;
	private ImageView mBrick;
	private ImageView mBag;
	private int mBrickIndex = 0;
	private long mStartTime, mEndTime;
	private TextProgressBar mProgressBar;
	private ProgressBar mProgressImage;
	private double mAmountBricks;
	private int percentage;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clear);

		mStartTime = System.nanoTime();

		// find set belonging to button pressed
		Intent i = getIntent();
		Bundle b = i.getExtras();
		if (b != null) {
			mSetIndex = b.getInt("page");
		}
		mSet = SetLab.get(this).getSet(mSetIndex);
		mAmountBricks = mSet.getAmountBricks();
		percentage = (int) ((1.0 / mAmountBricks) * 10000.0);

		mMessage = (TextView) findViewById(R.id.textview_message_instruction);
		mProgressBar = (TextProgressBar) findViewById(R.id.progressbar_clear);

		update();

		mProgressImage = (ProgressBar) findViewById(R.id.progressimage_clear);
		mProgressImage.setProgressDrawable(mSet
				.getProgressImageDrawable(mSetIndex));
		mProgressImage.setProgress(10000);

		mFigure = (ImageView) findViewById(R.id.imageView_clear_figurine);
		mFigure.setImageResource(mSet.getFigurineImageResource(mSetIndex));

		// next button pressed for new instruction
		mNextButton = (Button) findViewById(R.id.button_clear_next);
		mNextButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				// If there is next instruction, display instruction
				if (mSet.hasNext(mBrickIndex)) {
					mBrickIndex++;
					mProgressBar.setProgress(mProgressBar.getProgress()
							+ percentage);
					mProgressImage.setProgress(mProgressImage.getProgress()
							- percentage);
					update();

				}
				// If no more instructions go to timeactivity
				else {
					mEndTime = System.nanoTime();
					long mClearTime = (mEndTime - mStartTime);
					Intent i = new Intent(getBaseContext(), TimeActivity.class);
					i.putExtra("TIME", mClearTime);
					startActivity(i);
					finish();
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
					mProgressBar.setProgress(mProgressBar.getProgress()
							- percentage);
					mProgressImage.setProgress(mProgressImage.getProgress()
							+ percentage);
					update();

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
				FragmentManager fm = getSupportFragmentManager();
				String source = getResources().getResourceName(
						mCurrent.getSource());
				String sources[] = source.split(" ");
				String name = sources[0];
				name = name.replaceAll("\\D+", "");
				MissingBrickDialogFragment dialog = new MissingBrickDialogFragment();
				Bundle bundle = new Bundle();
				bundle.putInt("SETNUMBER", mSetIndex);
				bundle.putString("BRICKNAME", name);
				dialog.setArguments(bundle);
				dialog.show(fm, "Dialog Fragment");
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
		mProgressBar.setText(mProgressBar.getProgress() / 100 + "%");
		animateBrick();

	}

	// animation of brick into bag
	public void animateBrick() {
		mBrick = (ImageView) findViewById(R.id.imageview_brick);
		mBag = (ImageView) findViewById(R.id.imageview_bag);

		// xNewPos = mBag.getLeft() - mBrick.getLeft();
		// yNewPos = mBag.getTop() - mBrick.getTop();

		mBrick.bringToFront();

		Animation transAnim = new TranslateAnimation(0, 25, 0, 175);
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
