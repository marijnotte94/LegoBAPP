package com.share2pley.share2pleyapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.share2pley.share2pleyapp.Model.Instruction;
import com.share2pley.share2pleyapp.Model.Set;
import com.share2pley.share2pleyapp.Model.SetLab;

public class ClearActivity extends Activity {

	private Set set;
	private TextView message;
	private Button nextButton;
	private Instruction current;
	private int index;
	private int s = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clear);

		Intent i = getIntent();
		Bundle b = i.getExtras();
		if(b != null){
			s = b.getInt("SETNO");
		}


		set = SetLab.get().getSet(s);
		current = set.getInstruction(0);
		index = 0;

		message = (TextView)findViewById(R.id.textview_message_instruction);
		message.setTextColor(current.setForeGround(current.getColor()));
		message.setText(current.toString());

		nextButton = (Button)findViewById(R.id.button_build_next);
		nextButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if(set.hasNext(index)){
					index ++;
					current = set.getInstruction(index);
					message.setTextColor(current.setForeGround(current.getColor()));
					message.setBackgroundColor(current.setBackGround(current.getColor()));
					message.setText(current.toString());
				}
				else{
					message.setText("Well Done");
				}
			}
		});
	}


}
