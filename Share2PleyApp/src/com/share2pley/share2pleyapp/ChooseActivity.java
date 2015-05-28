package com.share2pley.share2pleyapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

public class ChooseActivity extends Activity {
	
	int number = 1;
	int no;
	String name;
	
	//Adding buttons to layout
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_set);

		TableLayout tableLayout = (TableLayout)findViewById(R.id.activity_choose_set_tableLayout);
		
		for (int i = 0; i < tableLayout.getChildCount(); i++) {
			TableRow row = (TableRow)tableLayout.getChildAt(i);
			for (int j = 0; j < row.getChildCount(); j++) {
				Button button = (Button)row.getChildAt(j);
				button.setText("" + number);
				button.setTag(Integer.valueOf(number));
				button.setOnClickListener(setListener);
				number++;
			}
			
		}
	}


	/**
	 * Additional method to use for all 16 buttons. Start instructions after button of setno pressed.
	 */
	View.OnClickListener setListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent i = new Intent(getBaseContext(), ClearActivity.class);
			int tag = (Integer) v.getTag();
			switch (tag){
			case 1: name = "speelhuis"; break;
			default: name = "hetgrotebos"; break;
			}
			
			i.putExtra("SETNO", name);
			startActivity(i);
			
		}
	};
}
