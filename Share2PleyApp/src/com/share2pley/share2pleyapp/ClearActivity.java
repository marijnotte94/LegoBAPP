package com.share2pley.share2pleyapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.share2pley.share2pleyapp.Model.Instruction;
import com.share2pley.share2pleyapp.Model.Set;
import com.share2pley.share2pleyapp.Model.SetLab;

public class ClearActivity extends Activity {

	private Set set;
	private TextView message;
	private Button nextButton;
	private Button previousButton;
	private Instruction current;
	private ImageView brick;
	private ImageView bag;
	
	private long xCurrentPos, yCurrentPos, xNewPos, yNewPos;
	
	private int index = 0;
	private int s = 0;
	
	private long startTime, endTime;
	private long clearTime = 0;
	private String timeString;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clear);


		//find set belonging to button pressed
		Intent i = getIntent();
		Bundle b = i.getExtras();
		if(b != null){
			s = b.getInt("SETNO");
		}

		
		set = SetLab.get().getSet(s);
		message = (TextView)findViewById(R.id.textview_message_instruction);
		startTime = System.nanoTime();
		
		update(index);	
		
		//next button pressed for new instruction
		nextButton = (Button)findViewById(R.id.button_build_next);
		nextButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//If there is next instruction, display instruction
				if(set.hasNext(index)){
					index ++;
					update(index);
					
				}
				//If no more instructions go to timeactivity
				else{
					endTime = System.nanoTime();
					clearTime = (endTime - startTime);
					Intent i = new Intent(getBaseContext(), TimeActivity.class);
					i.putExtra("TIME", clearTime);
					startActivity(i);
				}
			}
		});
		
		previousButton = (Button)findViewById(R.id.button_build_previous);
		previousButton.setOnClickListener(new View.OnClickListener(){
			//find previous instruction
			public void onClick(View v){
				if(set.hasPrevious(index)){
					index --;
					update(index);
				}
				//at first instruction: go to choose set screen
				else{
					Intent i = new Intent(getBaseContext(), ChooseActivity.class);
					startActivity(i);
				}
			}
		});		
	}

	
	//find instruction if previous or next button is pushed + layout of letters so that its readable
	public void update(int i){
		current = set.getInstruction(index);
		message.setTextColor(current.setForeGround(current.getColor()));
		if(current.getColor().equals("Yellow") || current.getColor().equals("White")){
			message.setShadowLayer(2, 0, 0, 0xff000000);
		}
		else{
			message.setShadowLayer(0, 0, 0, 0xff000000);
		}
		message.setText(current.toString());
		animateBrick();
	}
	
	//animation of brick into bag
	public void animateBrick(){	
		brick = (ImageView)findViewById(R.id.imageview_brick);
		bag = (ImageView)findViewById(R.id.imageview_bag);
				
		xNewPos = bag.getLeft() - brick.getLeft();
		yNewPos = bag.getTop() - brick.getTop();
		
		Animation anim = new TranslateAnimation(0, xNewPos, 0, yNewPos);
		anim.setDuration(1500);
		anim.setRepeatCount(Animation.INFINITE);
		brick.bringToFront();
	
		brick.startAnimation(anim);
	}
	



}
