package com.share2pley.share2pleyapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.share2pley.share2pleyapp.Model.SetFetcherTask;

//activity to homescren.
/**
 * 
 * @author Richard Vink - 4233867
 * 
 */
public class StartActivity extends Activity {
	Button mStartButton;
	private ProgressDialog loadingDialog;
	private static final String ENDPOINT = "http://brickset.com/api/v2.asmx/";
	private static final String METHOD_GET_SETS = "getSets";

	@Override
	public void onCreate(Bundle savedInstanceState) {

		// Setup the GUI with the corresponding XML file
		super.onCreate(savedInstanceState);
		String url = Uri.parse(ENDPOINT).buildUpon()
				.appendEncodedPath(METHOD_GET_SETS).build().toString();
		loadingDialog = new ProgressDialog(this);
		final SetFetcherTask setTask = new SetFetcherTask(this, loadingDialog,
				this);
		setTask.execute(url);
		setContentView(R.layout.activity_start);

		/**
		 * StartButton with new OnClickListener Starts new NameActivity and
		 * finishes the StartActivity
		 */
		mStartButton = (Button) findViewById(R.id.button_start);
		mStartButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (setTask.getStatus() == AsyncTask.Status.FINISHED) {
					Intent i = new Intent(getBaseContext(), NameActivity.class);
					startActivity(i);
				} else {
					loadingDialog.show();
				}
			}
		});
	}
}
