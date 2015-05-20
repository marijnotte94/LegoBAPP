package com.share2pley.share2pleyapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
	private int index;
	private int s = 0;
	private long startTime;
	private long endTime;
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
		current = set.getInstruction(0);
		index = 0;

		//display first instruction
		message = (TextView)findViewById(R.id.message_instruction);
		message.setTextColor(current.setForeGround(current.getColor()));
		message.setText(current.toString());
		
		startTime = System.nanoTime();
		
		
		nextButton = (Button)findViewById(R.id.build_next);
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
		
		previousButton = (Button)findViewById(R.id.build_previous);
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
	
	//find instruction if previous or next button is pushed
	public void update(int i){
		current = set.getInstruction(index);
		message.setTextColor(current.setForeGround(current.getColor()));
		message.setBackgroundColor(current.setBackGround(current.getColor()));
		message.setText(current.toString());
	}
	
	


}
