package com.share2pley.share2pleyapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class TakePhotoActivity extends Activity{
	private static final String TAG = "TakePhotoActivity";
	public static final String EXTRA_PHOTO_FILENAME = "com.share2pley.share2pleyapp.photo_filename";
	private ImageView mImageView;
	private Button mTakePhotoButton;

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