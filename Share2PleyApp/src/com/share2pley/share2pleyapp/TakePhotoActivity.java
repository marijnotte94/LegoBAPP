package com.share2pley.share2pleyapp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

//activity to take photo of lego

public class TakePhotoActivity extends Activity{
	private static final String TAG = "TakePhotoActivity";
	public static final String EXTRA_PHOTO_FILENAME = "com.share2pley.share2pleyapp.photo_filename";
	private ImageView mImageView;
	private Button mTakePhotoButton;
	private Button mConfirmButton;
	private Bitmap attachment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_take_photo);
		mImageView = (ImageView)findViewById(R.id.imageView_take_photo);
		mTakePhotoButton = (Button)findViewById(R.id.button_takePhoto_takePicture);
		mTakePhotoButton.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				open();
				attachment = mImageView.getDrawingCache();
				if (mImageView.getDrawingCache() != null) {
					try {
						FileOutputStream fos = openFileOutput(EXTRA_PHOTO_FILENAME, Context.MODE_WORLD_READABLE);
						attachment.compress(Bitmap.CompressFormat.JPEG, 0, fos);
						fos.close();
					} catch (FileNotFoundException e) {
						Log.e(TAG, "Error file not found ", e);
					} catch (IOException e) {
						Log.e(TAG, "Error in closing Stream ", e);
					}
				}
			}
		});

		mConfirmButton = (Button)findViewById(R.id.button_takePhoto_confirm);
		mConfirmButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// set up message subject and message
//				String subject = "Cleared LEGO set";
//				Uri address = Uri.parse("mailto:share2pleytest@gmail.com");
//				String message = "Test 123 test";
//				Intent intent = new Intent(Intent.ACTION_SENDTO, address);
				// ACTION_SENDTO filters for email apps (discard bluetooth and others)
				Uri file = Uri.fromFile(new File(EXTRA_PHOTO_FILENAME+".JPEG"));
				String uriText =
						"mailto:share2pleytest@gmail.com" + 
								"?subject=" + Uri.encode("Cleared LEGO set") + 
								"&body=" + Uri.encode("Test 123 Test") + "&attachment=" + file;

				Uri uri = Uri.parse(uriText);

				Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
				sendIntent.setData(uri);
				try {
					startActivity(Intent.createChooser(sendIntent, "Send email"));
				} catch (android.content.ActivityNotFoundException ex) {
					Log.e(TAG, "There are no email clients installed", ex);
				} 
				finish();								
			}
		});
	}

	public void open() {
		Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(intent,0);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		try {
			super.onActivityResult(requestCode, resultCode, data);
			Bitmap bp = (Bitmap) data.getExtras().get("data");
			mImageView.setImageBitmap(bp);
		} catch (Exception e) {
			Log.e(TAG, "Error in retrieving back photo ", e);
		}
	}
}