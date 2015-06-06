package com.share2pley.share2pleyapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.share2pley.share2pleyapp.Model.Set;
import com.share2pley.share2pleyapp.Model.SetLab;

public class ResultActivity extends Activity{
	
	private int mSetIndex;
	private Set mSet;
	private ImageView mSetImage;
	private Button mExit;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		
		Intent i = getIntent();
		Bundle b = i.getExtras();
		if (b != null) {
			mSetIndex = b.getInt("SETNO");
		}
		
		mSet = SetLab.get(this).getSet(mSetIndex);
		Log.d("index", mSetIndex +"");
		mSetImage = (ImageView)findViewById(R.id.imageView_final_set);
		mSetImage.setImageResource(mSet.getModelImageResource(mSetIndex));
		
		
		mExit = (Button) findViewById(R.id.button_result_exit);
		mExit.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				finish();
				Intent intent = new Intent(Intent.ACTION_MAIN);
				intent.addCategory(Intent.CATEGORY_HOME);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
			}
		});
		
	}
	
	

}
