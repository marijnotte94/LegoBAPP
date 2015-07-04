package com.share2pley.share2pleyapp;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ClipDrawable;
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
	 * @author Marijn Otte - 4222695 & Richard Vink 4233867
	 * 
	 */
	private Set mSet;
	private int mSetIndex;
	private TextView mMessage;
	private Brick mCurrent;
	private ImageView mBrick;
	private int mBrickIndex;
	private TextProgressBar mProgressBar;
	private ProgressBar mProgressImage;
	private int mPercentage;
	private long mStartTime;
	private int mAmountBricks;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clear);

		mStartTime = System.nanoTime();

		// find set belonging to button pressed
		Bundle b = getIntent().getExtras();
		if (b != null) {
			mSetIndex = b.getInt("page");
		}

		mSet = SetLab.get(this).getSet(mSetIndex);

		int mAmountSteps = mSet.getBricks().size();
		for (Brick brick : mSet.getBricks()) {
			mAmountBricks += brick.getQuantity();
		}
		mPercentage = (int) ((1.0 / mAmountSteps) * 10000.0);

		mMessage = (TextView) findViewById(R.id.textview_message_instruction);
		mProgressBar = (TextProgressBar) findViewById(R.id.progressbar_clear);
		mBrick = (ImageView) findViewById(R.id.imageview_brick);

		update();

		mProgressImage = (ProgressBar) findViewById(R.id.progressimage_clear);
		@SuppressWarnings("deprecation")
		ClipDrawable clip = new ClipDrawable(new BitmapDrawable(
				mSet.getBitmap()), 3, 1);
		mProgressImage.setProgressDrawable(clip);
		mProgressImage.setProgress(10000);

		ImageView mFigure = (ImageView) findViewById(R.id.imageView_clear_figurine);
		mFigure.setImageResource(R.drawable.figurine_city);

		// next button pressed for new instruction
		Button mNextButton = (Button) findViewById(R.id.button_clear_next);
		mNextButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// If there is next instruction, display instruction
				if (mBrickIndex < mSet.getBricks().size() - 1) {
					mBrickIndex++;
					mProgressBar.setProgress(mProgressBar.getProgress()
							+ mPercentage);
					mProgressImage.setProgress(mProgressImage.getProgress()
							- mPercentage);
					update();
				}
				// If no more instructions go to timeactivity
				else {
					long mEndTime = System.nanoTime();
					long mClearTime = (mEndTime - mStartTime);
					Intent i = new Intent(getBaseContext(), TimeActivity.class);
					i.putExtra("TIME", mClearTime);
					i.putExtra("SETNO", mSetIndex);
					i.putExtra("AMOUNTBRICKS", mAmountBricks);
					startActivity(i);
					finish();
				}
			}
		});

		Button mPreviousButton = (Button) findViewById(R.id.button_clear_previous);
		mPreviousButton.setOnClickListener(new View.OnClickListener() {
			// find previous instruction
			@Override
			public void onClick(View v) {
				if (mBrickIndex > 1) {
					mBrickIndex--;
					mProgressBar.setProgress(mProgressBar.getProgress()
							- mPercentage);
					mProgressImage.setProgress(mProgressImage.getProgress()
							+ mPercentage);
					update();

				}
				// at first instruction: go to choose set screen
				else {
					finish();
				}
			}

		});

		Button mMissingButton = (Button) findViewById(R.id.button_clear_missing);
		mMissingButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				FragmentManager fm = getSupportFragmentManager();
				String name = mCurrent.getName();
				int amount = mSet.getBricks().get(mBrickIndex).getQuantity();
				MissingBrickDialogFragment dialog = new MissingBrickDialogFragment();
				Bundle bundle = new Bundle();
				bundle.putInt("SETNUMBER", mSetIndex);
				bundle.putString("BRICKNAME", name);
				bundle.putInt("AMOUNT", amount);
				dialog.setArguments(bundle);
				dialog.show(fm, "Dialog Fragment");
			}
		});

		
	}

	// find instruction if previous or next button is pushed + layout of letters
	// so that its readable
	public void update() {
		mCurrent = mSet.getBricks().get(mBrickIndex);
		mBrick.setImageBitmap(mCurrent.getImage());
		mMessage.setText(mCurrent.getQuantity() + "x");
		mProgressBar.setText(mProgressBar.getProgress() / 100 + "%");
		animateBrick(mCurrent.getQuantity() - 1);
	}

	// animation of brick into bag
	public void animateBrick(int amount) {
		mBrick.bringToFront();

		Animation transAnim = new TranslateAnimation(0, 25, 0, 175);
		transAnim.setRepeatCount(amount);

		Animation alphaAnim = new AlphaAnimation(1, 0);
		alphaAnim.setRepeatCount(amount);

		AnimationSet animation = new AnimationSet(true);
		animation.addAnimation(transAnim);
		animation.addAnimation(alphaAnim);
		animation.setDuration(1500);
		mBrick.startAnimation(animation);
	}

	@Override
	protected void onStop() {
		super.onStop();
		SetLab.get(this).getSet(mSetIndex).setBricks(null);
	}
}
