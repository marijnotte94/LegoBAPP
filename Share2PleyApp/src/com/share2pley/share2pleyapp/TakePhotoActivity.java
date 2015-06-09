package com.share2pley.share2pleyapp;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * 
 * @author Richard Vink - 4233867. Activity to take photo.
 * 
 */
public class TakePhotoActivity extends Activity {
	private static final String TAG = "TakePhotoActivity";
	public static final String EXTRA_PHOTO_FILENAME = "com.share2pley.share2pleyapp.photo_filename";
	private ImageView mImageView;
	private Button mTakePhotoButton;
	private Button mConfirmButton;
	private Bitmap attachment;
	private Context context = this;
	private boolean photoTaken = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_take_photo);

		mImageView = (ImageView) findViewById(R.id.imageView_take_photo);
		mTakePhotoButton = (Button) findViewById(R.id.button_takePhoto_takePicture);
		mTakePhotoButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				open();
				attachment = mImageView.getDrawingCache();
				if (mImageView.getDrawingCache() != null) {
					try {
						FileOutputStream fos = openFileOutput(
								EXTRA_PHOTO_FILENAME,
								Context.MODE_WORLD_READABLE);
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

		mConfirmButton = (Button) findViewById(R.id.button_takePhoto_confirm);
		mConfirmButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if(photoTaken == true){
					finish();	
				}
				else{
					CharSequence text = "Please take a picture";
					int duration = Toast.LENGTH_SHORT;
					Toast toast = Toast.makeText(context, text, duration);
					toast.show();
				}
			}
		});
	}

	public void open() {
		Intent intent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(intent, 0);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		try {
			super.onActivityResult(requestCode, resultCode, data);
			Bitmap bp = (Bitmap) data.getExtras().get("data");
			mImageView.setImageBitmap(bp);
			photoTaken = true;
			
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
			SharedPreferences.Editor editor = prefs.edit();
			
			String stringAttachment = BitMapToString(bp);
			editor.putString("string_id", stringAttachment);
			editor.commit();
		
		} catch (Exception e) {
			Log.e(TAG, "Error in retrieving back photo ", e);
		}
	}
	
	public String BitMapToString(Bitmap bitmap){
	     ByteArrayOutputStream baos=new  ByteArrayOutputStream();
	     bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
	     byte [] b=baos.toByteArray();
	     String temp=Base64.encodeToString(b, Base64.DEFAULT);
	     return temp;
	}
}