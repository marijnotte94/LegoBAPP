package com.share2pley.share2pleyapp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.share2pley.share2pleyapp.Model.Missing;
import com.share2pley.share2pleyapp.Model.Set;
import com.share2pley.share2pleyapp.Model.SetLab;

public class ResultActivity extends Activity {

	private DBHelper mDBHelper;
	private List<Missing> mMissings;
	private int mMissingBricks;
	private int mSetIndex;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		String data = prefs.getString("string_id", "no id");
		Bitmap bp = StringToBitMap(data);

		ImageView mReportImage = (ImageView) findViewById(R.id.imageView_final_set);
		mReportImage.setImageBitmap(bp);

		TextView messageTextView = (TextView) findViewById(R.id.textView_final_intro);

		mSetIndex = getIntent().getExtras().getInt("SETNO");

		TextView missingTextView = (TextView) findViewById(R.id.textView_result_missing);
		mDBHelper = new DBHelper(this);
		mMissings = mDBHelper.getMissingBricksById();
		for (Missing m : mMissings) {
			missingTextView.append(m.toString() + "\n");
			mMissingBricks += m.getAmount();
		}

		if (mMissings.isEmpty()) {
			messageTextView.setText(getString(R.string.nomissing));
		} else if (mMissingBricks == 1) {
			messageTextView.setText(getString(R.string.onemissing1) + " "
					+ mMissingBricks + " " + getString(R.string.onemissing2));
		}

		else if (mMissingBricks <= 10 && mMissingBricks > 0) {
			messageTextView.setText(getString(R.string.fewmissing1) + " "
					+ mMissingBricks + " " + getString(R.string.fewmissing2));
		} else {
			messageTextView.setText(getString(R.string.moremissing1) + " "
					+ mMissingBricks + " " + getString(R.string.moremissing2));
		}
		mDBHelper.deleteMissings();
		Set set = SetLab.get(this).getSet(mSetIndex);
		String setFullId = set.getFullId();
		mDBHelper.insertSet(setFullId);

		Button mExit = (Button) findViewById(R.id.button_result_exit);
		mExit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Bitmap bitmap = takeScreenShot();
				saveBitmap(bitmap);
				SetLab.get(getBaseContext()).removeSet(
						SetLab.get(getBaseContext()).getSet(mSetIndex));
				finish();
			}
		});

	}

	public Bitmap takeScreenShot() {
		View rootView = findViewById(android.R.id.content).getRootView();
		rootView.setDrawingCacheEnabled(true);
		return rootView.getDrawingCache();
	}

	public void saveBitmap(Bitmap bitmap) {
		String setName = SetLab.get(this).getSet(mSetIndex).getName();
		String externalStorage = getAvailableExternalMemorySize();
		File imagePath;
		if (checksIfMb(externalStorage)) {
			imagePath = new File(Environment.getExternalStorageDirectory()
					+ "/results_" + setName + ".jpg");
		} else {
			imagePath = new File(Environment.getDataDirectory() + "/results_"
					+ setName + ".jpg");
		}
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(imagePath);
			bitmap.compress(CompressFormat.JPEG, 100, fos);
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

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

	/**
	 * Checks if SD card is available
	 * 
	 * @return
	 */
	public boolean externalMemoryAvailable() {
		return android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
	}

	/**
	 * Checks the free space of the external SD card
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public String getAvailableExternalMemorySize() {
		if (externalMemoryAvailable()) {
			File path = Environment.getExternalStorageDirectory();
			StatFs stat = new StatFs(path.getPath());
			long blockSize = stat.getBlockSize();
			long availableBlocks = stat.getAvailableBlocks();
			return formatSize(availableBlocks * blockSize);
		} else {
			return "ERROR";
		}
	}

	/**
	 * Formats the size to a KB or MB value
	 * 
	 * @param size
	 * @return
	 */
	public String formatSize(long size) {
		String suffix = null;

		if (size >= 1024) {
			suffix = "KB";
			size /= 1024;
			if (size >= 1024) {
				suffix = "MB";
				size /= 1024;
			}
		}

		StringBuilder resultBuffer = new StringBuilder(Long.toString(size));

		if (suffix != null)
			resultBuffer.append(suffix);
		return resultBuffer.toString();
	}

	/**
	 * Checks if the value is MB
	 * 
	 * @param s
	 * @return
	 */
	public boolean checksIfMb(String s) {
		if (s != null && s.length() >= 2) {
			String lastTwo = s.substring(s.length() - 2);
			if (lastTwo.equals("MB")) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
}
