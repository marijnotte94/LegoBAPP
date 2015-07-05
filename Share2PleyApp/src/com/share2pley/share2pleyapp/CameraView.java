package com.share2pley.share2pleyapp;

import java.io.ByteArrayOutputStream;

import org.opencv.android.JavaCameraView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.Size;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

public class CameraView extends JavaCameraView implements PictureCallback {

	private static final String TAG = "CameraView";

	private Context	mContext;
	private byte[] imageData;




	public CameraView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	//	public List<String> getEffectList() {
	//		return mCamera.getParameters().getSupportedColorEffects();
	//	}
	//
	//	public boolean isEffectSupported() {
	//		return (mCamera.getParameters().getColorEffect() != null);
	//	}
	//
	//	public String getEffect() {
	//		return mCamera.getParameters().getColorEffect();
	//	}
	//
	//	public void setEffect(String effect) {
	//		Camera.Parameters params = mCamera.getParameters();
	//		params.setColorEffect(effect);
	//		mCamera.setParameters(params);
	//	}
	//
	//	public List<Size> getResolutionList() {
	//		return mCamera.getParameters().getSupportedPreviewSizes();
	//	}
	//
	//	public void setResolution(Size resolution) {
	//		disconnectCamera();
	//		mMaxHeight = resolution.height;
	//		mMaxWidth = resolution.width;
	//		
	//		connectCamera(getWidth(), getHeight());
	//	}
	//
	//	public Size getResolution() {
	//		return mCamera.getParameters().getPreviewSize();
	//	}


	public void takePicture() {
		Log.i(TAG, "Taking picture");


		// Postview and jpeg are sent in the same buffers if the queue is not empty when performing a capture.
		// Clear up buffers to avoid mCamera.takePicture to be stuck because of a memory issue   

		mCamera.setPreviewCallback(null);
		// PictureCallback is implemented by the current class    

		mCamera.takePicture(null, null, this);        
	}


	@Override
	public void onPictureTaken(byte[] data, Camera camera) {
		Log.i(TAG, "Saving a bitmap to file");

		Camera.Parameters parameters = camera.getParameters();
		Size size = parameters.getPreviewSize();
//	
//		YuvImage yuvimage = new YuvImage(data, ImageFormat.NV21, size.width,size.height,null);
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		yuvimage.compressToJpeg(new Rect(0,0,yuvimage.getWidth(), yuvimage.getHeight()), 100, baos);
//		imageData = baos.toByteArray();
		
		
		
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(getContext());
		String originalst = prefs.getString("original_image", "no id");
		Bitmap originalBp = StringToBitMap(originalst);
		
		Bitmap photoBp = BitmapFactory.decodeByteArray(data , 0, data.length);
		photoBp = Bitmap.createBitmap(photoBp, (photoBp.getWidth()/2 -125), (photoBp.getHeight()/2 - 125), 250,  250);
	
//		if(ImageProcessor.isCorrectBrick(originalBp,photoBp)){
//			String s = BitMapToString(photoBp);
//			Log.d("correctbrick","correctbrick");
//			Toast toast = Toast.makeText(getContext(), ImageProcessor.getDif()+","+photoBp.getHeight()+","+photoBp.getWidth(), Toast.LENGTH_LONG);
//			toast.show();
//			Intent i = new Intent(getContext(), TakePhotoActivity.class);
//			i.putExtra("photoTaken","YES");
//			i.putExtra("image", s);
//			getContext().startActivity(i);
//			ImageProcessor.reset();
//		}
//		else{
//			int duration = Toast.LENGTH_SHORT;
//			CharSequence text = getResources().getString(R.string.notCorrectPhoto) + ImageProcessor.getDif() + ","  +photoBp.getHeight()+","+photoBp.getWidth();
//			Toast toast = Toast.makeText(getContext(), text, duration);
//			toast.show();
//			Log.d("notcorrectbrick", "notcorrectkbrick");
//			Intent i = new Intent(getContext(), CameraActivity.class);
//			getContext().startActivity(i);
//			ImageProcessor.reset();
//		}
		String sPhoto = BitMapToString(photoBp);
		String sOriginal = BitMapToString(originalBp);
		Intent i = new Intent(getContext(), TakePhotoActivity.class);
		i.putExtra("image", sPhoto);
		i.putExtra("original", sOriginal);
		getContext().startActivity(i);
			
				
		




		//Bitmap bmp = BitmapFactory.decodeByteArray(outstr.toByteArray(), 0, outstr.size());
		//bmp = scaleDownBitmap(bmp, 50, getContext());
		//String im = BitMapToString(bmp);     
		//byte[] outstrarray = outstr.toByteArray();
	}


	public byte[] getImageData(){
		return imageData;
	}



	//	public String BitMapToString(Bitmap bitmap){
	//		ByteArrayOutputStream baos=new  ByteArrayOutputStream();
	//		bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
	//		byte [] b=baos.toByteArray();
	//		String temp=Base64.encodeToString(b, Base64.DEFAULT);
	//		return temp;
	//	}
	//
	//	public static Bitmap scaleDownBitmap(Bitmap photo, int newHeight, Context context) {
	//
	//		final float densityMultiplier = context.getResources().getDisplayMetrics().density;        
	//
	//		int h= (int) (newHeight*densityMultiplier);
	//		int w= (int) (h * photo.getWidth()/((double) photo.getHeight()));
	//
	//		photo=Bitmap.createScaledBitmap(photo, w, h, true);
	//
	//		return photo;
	//	}
	
	public Bitmap StringToBitMap(String encodedString) {
		try {
			byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
			Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0,
					encodeByte.length);
			return bitmap;
		} catch (Exception e) {
			e.getMessage();
			return null;
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
