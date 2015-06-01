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

public class ClearActivity extends Activity {
	
	private Set mSet;
	private int mSetIndex;
	private TextView mMessage;
	private Button mNextButton;
	private Button mPreviousButton;
	private Brick mCurrent;
	private ImageView mFigure;
	private ImageView mBrick;
	private ImageView mBag;
	private int mBrickIndex = 0;
	private long xNewPos, yNewPos;
	private long mStartTime, mEndTime;
	private ProgressBar mProgressbar;
	
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clear);

		//find set belonging to button pressed
		Intent i = getIntent();
		Bundle b = i.getExtras();
		if(b != null){
			mSetIndex = b.getInt("page");
		}
		
		mSet = SetLab.get(this).getSet(mSetIndex);
		
		//start timer
		mMessage = (TextView)findViewById(R.id.textview_message_instruction);
		mProgressbar = (ProgressBar)findViewById(R.id.progressbar_clear);
		mStartTime = System.nanoTime();

		//update(index);
		
		//match index with array
		mFigure = (ImageView)findViewById(R.id.imageView_clear_figurine);
		mFigure.setImageResource(SetLab.get(this).getSet(mSetIndex).getFigurineImageResource(mSetIndex));
		
		//next button pressed for new instruction
		mNextButton = (Button)findViewById(R.id.button_clear_next);
		mNextButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//If there is next instruction, display instruction
				if(mSet.hasNext(mBrickIndex)){
					mBrickIndex ++;
					update(mBrickIndex);
					mProgressbar.setProgress(mProgressbar.getProgress()+5);
				}
				//If no more instructions go to timeactivity
				else{
					mEndTime = System.nanoTime();
					long mClearTime = (mEndTime - mStartTime);
					Intent i = new Intent(getBaseContext(), TimeActivity.class);
					i.putExtra("TIME", mClearTime);
					startActivity(i);
				}
			}
		});

		mPreviousButton = (Button)findViewById(R.id.button_clear_previous);
		mPreviousButton.setOnClickListener(new View.OnClickListener(){
			//find previous instruction
			public void onClick(View v){
				if(mSet.hasPrevious(mBrickIndex)){
					mBrickIndex --;
					update(mBrickIndex);
				}
				//at first instruction: go to choose set screen
				else{
					finish();
				}
			}
		});		
	}


	//find instruction if previous or next button is pushed + layout of letters so that its readable
	public void update(int i){
		ImageView brick = (ImageView)findViewById(R.id.imageview_brick);
		mCurrent = mSet.getBrick(mBrickIndex);
		brick.setImageResource(mCurrent.getSource());
		mMessage.setText(mCurrent.getAmount() + "x");
		animateBrick();
	}

	//animation of brick into bag
	public void animateBrick(){	
		mBrick = (ImageView)findViewById(R.id.imageview_brick);
		mBag = (ImageView)findViewById(R.id.imageview_bag);

		xNewPos = mBag.getLeft() - mBrick.getLeft();
		yNewPos = mBag.getTop() - mBrick.getTop();
		
		mBrick.bringToFront();

		Animation transAnim = new TranslateAnimation(0, xNewPos, 0, yNewPos);
		transAnim.setRepeatCount(transAnim.INFINITE);
		
		Animation alphaAnim = new AlphaAnimation(1,0);
		alphaAnim.setRepeatCount(alphaAnim.INFINITE);
		
		
		
		AnimationSet set = new AnimationSet(true);
		set.addAnimation(transAnim);
		set.addAnimation(alphaAnim);
		set.setDuration(1500);
		
		mBrick.startAnimation(set);
	}
	
	




}
