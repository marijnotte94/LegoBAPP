package com.share2pley.share2pleyapp;

import java.util.ArrayList;
import java.util.Random;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
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
	private ArrayList<Brick> mMinis = new ArrayList<Brick>();
	private int mSetIndex;
	private TextView mMessage;
	private Button mNextButton, mPreviousButton, mMissingButton;
	private Brick mCurrent;
	private ImageView mFigure;
	private ImageView mBrick;
	private ImageView mBag;
	private int mBrickIndex;
	private long mStartTime, mEndTime;
	private TextProgressBar mProgressBar;
	private ProgressBar mProgressImage;
	private double mAmountSteps;
	private int mAmountBricks;
	private int percentage;
	private int mIndexPhoto;
	private int mIndexDrawing = 1;

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

		mAmountSteps = mSet.getAmountSteps();
		mAmountBricks = mSet.getAmountBricks();
		percentage = (int) ((1.0 / mAmountSteps) * 10000.0);

		Random r = new Random();
		mIndexPhoto = r.nextInt((int) (mAmountSteps - 2)) + 1;

		mMessage = (TextView) findViewById(R.id.textview_message_instruction);
		mProgressBar = (TextProgressBar) findViewById(R.id.progressbar_clear);
		mBrick = (ImageView) findViewById(R.id.imageview_brick);

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
					if (mIndexPhoto==mBrickIndex) {
						Intent i = new Intent(getBaseContext(),
								TakePhotoActivity.class);
						startActivity(i);
					}
					mBrickIndex++;
					mProgressBar.setProgress(mProgressBar.getProgress()
							+ percentage);
					mProgressImage.setProgress(mProgressImage.getProgress()
							- percentage);
					update();
					mIndexDrawing++;

				}
				// If no more instructions go to timeactivity
				else {
					mEndTime = System.nanoTime();
					long mClearTime = (mEndTime - mStartTime);
					Intent i = new Intent(getBaseContext(), TimeActivity.class);
					i.putExtra("TIME", mClearTime);
					i.putExtra("SETNO", mSetIndex + 1);
					i.putExtra("AMOUNTBRICKS", mAmountBricks);
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
					mIndexDrawing--;

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

		mCurrent = mSet.getBrick(mBrickIndex);
		mBrick.setImageResource(mCurrent.getSource());
		mMessage.setText(mCurrent.getAmount() + "x");
		mProgressBar.setText(mProgressBar.getProgress() / 100 + "%");
		animateBrick(mCurrent.getAmount()-1);

	}

	// animation of brick into bag
	public void animateBrick(int amount) {
		mBag = (ImageView) findViewById(R.id.imageview_bag);
		mBrick.bringToFront();

		Animation transAnim = new TranslateAnimation(0, 25, 0, 175);
		transAnim.setRepeatCount(amount);

		Animation alphaAnim = new AlphaAnimation(1, 0);
		alphaAnim.setRepeatCount(amount);

		AnimationSet set = new AnimationSet(true);
		set.addAnimation(transAnim);
		set.addAnimation(alphaAnim);
		set.setDuration(1500);
		set.setAnimationListener(new AnimationListener(){
			@Override public void onAnimationStart(Animation animation) {}
			@Override public void onAnimationRepeat(Animation animation) {}
			@Override public void onAnimationEnd(Animation animation)
			{	
				if (mSet.hasNext(mBrickIndex)){
					if(getResources().getResourceName(mSet.getBrick(mBrickIndex+1).getSource()).contains("_"+mIndexDrawing+"_")){
						mBrickIndex++;
						mProgressBar.setProgress(mProgressBar.getProgress()
								+ percentage);
						mProgressImage.setProgress(mProgressImage.getProgress()
								- percentage);

						update();
					}

				}

			}

		});

		mBrick.startAnimation(set);

	}

	//	public void addMiniBricks(){
	//		mBrickIndex = 0;
	//		for(int i = 0; i<mMinis.size()-1; i++){
	//			mCurrent = mMinis.get(i);
	//			mBrick.setImageResource(mCurrent.getSource());
	//			mMessage.setText(mCurrent.getAmount() + "x");
	//			mProgressBar.setText(mProgressBar.getProgress() / 100 + "%");
	//			animateBrick(mCurrent.getAmount()-1);
	//			mBrickIndex++;
	//			try {
	//			    Thread.sleep(3000);                 
	//			} catch(InterruptedException ex) {
	//			    Thread.currentThread().interrupt();
	//			}
	//		}
	//		

	//	}

}
