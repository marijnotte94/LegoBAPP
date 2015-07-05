package com.share2pley.share2pleyapp;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.CountDownLatch;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.android.JavaCameraView;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SubMenu;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

public class CameraActivity extends Activity implements CvCameraViewListener2, View.OnTouchListener{
	private static final String TAG = "TakePhotoActivity";
	public static final String EXTRA_PHOTO_FILENAME = "com.share2pley.share2pleyapp.photo_filename";
	private Context context = this;
	private CameraView mOpenCvCameraView;
	private byte[] imageData;
	private Mat original;

	static {
		if(!OpenCVLoader.initDebug()){}
	}
	private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
		@Override
		public void onManagerConnected(int status) {
			switch (status) {
			case LoaderCallbackInterface.SUCCESS:
			{
				Log.d(TAG, "OpenCV loaded successfully");
				mOpenCvCameraView.enableView();
				mOpenCvCameraView.setOnTouchListener(CameraActivity.this);
			} break;
			default:
			{
				super.onManagerConnected(status);
			} break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_camera);
		Log.d(TAG, "Creating and setting view");
		mOpenCvCameraView = (CameraView) findViewById(R.id.camera_activity_java_surface_view);
		mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
		mOpenCvCameraView.setCvCameraViewListener(this);
	}

	@Override
	public void onPause()
	{
		super.onPause();
		if (mOpenCvCameraView != null)
			mOpenCvCameraView.disableView();
	}

	@Override
	protected void onResume() {
		try {
			Log.i(TAG, "onResume");
			super.onResume();
			mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
		} catch (final Exception e) {
			//Uts.writeException(getApplicationContext(), e);
		}
	}

	public void onDestroy() {
		super.onDestroy();
		if (mOpenCvCameraView != null)
			mOpenCvCameraView.disableView();


	}

	public void onCameraViewStarted(int width, int height) {
	}

	public void onCameraViewStopped() {
		finish();
	}

	public boolean onTouch(View v, MotionEvent event){
		Log.d("wielrennen","wielrennen");
		
		mOpenCvCameraView.takePicture();
		//Log.d("wielrennen1","wielrennen");
		//mOpenCvCameraView.getImageData();	
		//Log.d("wielrennen2","wielrennen");



		//		Intent i = new Intent(getBaseContext(), TakePhotoActivity.class);
		//		startActivity(i);
		//		try{
		//			imageData = mOpenCvCameraView.getImageData();
		//			if(correctImage(imageData,original)){
		//				Log.d("correctimage","correctimage");
		//				Toast.makeText(this, "photo taken", Toast.LENGTH_SHORT).show();
		//				
		//			}
		//			else{
		//				Log.d("correctimage","correctimage");
		//				Toast.makeText(this, "not correct image", Toast.LENGTH_SHORT).show();
		//				Intent i = getIntent();
		//				finish();
		//				startActivity(i);
		//			}
		//		}
		//		catch(NullPointerException e){
		//
		//			Log.d("nullpointer","nullpointer");
		//		}
		return false;
	}

	@Override
	public Mat onCameraFrame(CvCameraViewFrame Frame) {
		Mat inputFrame = Frame.rgba();
		Bitmap bp = BitmapFactory.decodeResource(context.getResources(), R.drawable.teststeentje7);
		Bitmap mutablebp = bp.copy(Bitmap.Config.ARGB_8888, true);

		//		int c = mutablebp.getPixel(10,10);
		//		int c1 = mutablebp.getPixel(100,100);
		//		int c2 = mutablebp.getPixel(mutablebp.getWidth()-10, mutablebp.getHeight()-10);
		//		int c3 = mutablebp.getPixel(mutablebp.getWidth()-100, mutablebp.getHeight()-100);
		//
		//		for(int x = 0; x<mutablebp.getWidth(); x++){
		//			for(int y = 0; y<mutablebp.getHeight(); y++){
		//				if(mutablebp.getPixel(x, y) == c || mutablebp.getPixel(x,y) == c1 || mutablebp.getPixel(x,y) == c2 || mutablebp.getPixel(x,y) == c3){
		//					mutablebp.setPixel(x, y, Color.TRANSPARENT);
		//				}
		//			}
		//		}

		for(int x = 0; x<mutablebp.getWidth(); x++){
			for(int y = 0; y<mutablebp.getHeight(); y++){
				int pixel = mutablebp.getPixel(x,y);
				int R = (pixel >> 16) & 0xff;
				int G = (pixel >> 8) & 0xff;
				int B = pixel & 0xff;
				if(R > 200 & R > 200 & B > 200){
					mutablebp.setPixel(x,y,Color.TRANSPARENT);
				}
			}
		}
		Mat m = new Mat();
		inputFrame.copyTo(m);
		Utils.bitmapToMat(mutablebp,m);
		
		Imgproc.resize(m,m,new Size(250,250));
		
		Mat subMat = inputFrame.submat((inputFrame.height() / 2) - 125, (inputFrame.height() /2) + 125, (inputFrame.width() / 2) - 125, (inputFrame.width() /2) + 125);
		 	Mat dst = new Mat(subMat.rows(), subMat.cols(), CvType.CV_8UC4);
		    Mat tmp = new Mat(subMat.rows(), subMat.cols(), CvType.CV_8UC4);
		    Mat alpha = new Mat(subMat.rows(), subMat.cols(), CvType.CV_8UC4);

		    // convert image to grayscale
		    Imgproc.cvtColor(subMat, tmp, Imgproc.COLOR_BGR2GRAY);

		    // threshold the image to create alpha channel with complete transparency in black background region and zero transparency in foreground object region.
		    Imgproc.threshold(tmp, alpha, 100, 255, Imgproc.THRESH_BINARY);

		    // split the original image into three single channel.
		    List<Mat> rgb = new ArrayList<Mat>(3);
		    Core.split(subMat, rgb);

		    // Create the final result by merging three single channel and alpha(BGRA order)
		    List<Mat> rgba = new ArrayList<Mat>(4);
		    rgba.add(rgb.get(0));
		    rgba.add(rgb.get(1));
		    rgba.add(rgb.get(2));
		    rgba.add(alpha);
		    Core.merge(rgba, subMat);
	    
		    m.copyTo(subMat);
		    
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = prefs.edit();
		bp = Bitmap.createBitmap(m.width(), m.height(),Bitmap.Config.ARGB_8888);
		Utils.matToBitmap(m, bp);
		String originalImageString = BitMapToString(bp);
		editor.putString("original_image", originalImageString);
		editor.commit();
		
		return inputFrame;
	}


	public String BitMapToString(Bitmap bitmap){
		ByteArrayOutputStream baos=new  ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
		byte [] b=baos.toByteArray();
		String temp=Base64.encodeToString(b, Base64.DEFAULT);
		return temp;
	}

}

