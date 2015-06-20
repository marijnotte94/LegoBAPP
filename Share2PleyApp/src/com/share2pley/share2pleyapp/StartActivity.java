package com.share2pley.share2pleyapp;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.share2pley.share2pleyapp.Model.HttpAsyncTask;
import com.share2pley.share2pleyapp.Model.PrototypeSetLab;

//activity to homescren.
/**
 * 
 * @author Richard Vink - 4233867
 * 
 */
public class StartActivity extends Activity {
	Button mStartButton;

	private static final String HOST = "brickset.com";
	private static final String ENDPOINT = "http://brickset.com/api/v2.asmx/";
	private static final String API_KEY = "hTsg-Vmye-dO8e";
	private static final String METHOD_CHECK_KEY = "getSets";

	@Override
	public void onCreate(Bundle savedInstanceState) {

		// Setup the GUI with the corresponding XML file
		super.onCreate(savedInstanceState);
		String url = Uri.parse(ENDPOINT).buildUpon()
				.appendEncodedPath(METHOD_CHECK_KEY).build().toString();
		new HttpAsyncTask(this).execute(url);
		getResources().getStringArray(R.array.assetsArray);
		setContentView(R.layout.activity_start);

		/**
		 * StartButton with new OnClickListener Starts new NameActivity and
		 * finishes the StartActivity
		 */
		mStartButton = (Button) findViewById(R.id.button_start);
		mStartButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getBaseContext(), NameActivity.class);
				startActivity(i);
			}
		});
	}

	private void copyReadAssets() {
		AssetManager assetManager = getAssets();
		InputStream in = null;
		OutputStream out = null;
		try {
			String[] fileNames = assetManager.list("");
			for (int i = 0; i < fileNames.length; i++) {
				File file = new File(getFilesDir(), fileNames[i]);
				try {
					in = assetManager.open(fileNames[i]);
					out = openFileOutput(file.getName(),
							Context.MODE_WORLD_READABLE);

					copyFile(in, out);
					in.close();
					in = null;
					out.flush();
					out.close();
					out = null;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {

		}
	}

	private void copyFile(InputStream in, OutputStream out) throws IOException {
		byte[] buffer = new byte[1024];
		int read;
		while ((read = in.read(buffer)) != -1) {
			out.write(buffer, 0, read);
		}
	}
}
