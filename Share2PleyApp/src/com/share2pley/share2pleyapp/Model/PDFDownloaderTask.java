package com.share2pley.share2pleyapp.Model;

import java.io.File;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;

import com.share2pley.share2pleyapp.Downloader;

public class PDFDownloaderTask extends AsyncTask<File, String, Intent> {

	private final ProgressDialog dialog;
	private final Activity mActivity;
	private final Set mSet;
	private final int clicked;

	public PDFDownloaderTask(Activity activity, ProgressDialog dialog, Set set,
			int clicked) {
		mActivity = activity;
		this.dialog = dialog;
		this.clicked = clicked;
		mSet = set;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected Intent doInBackground(File... file) {
		Downloader.DownloadFile(mSet.getInstructions().get(clicked), file[0]);
		return showPdf(file[0]);
	}

	public Intent showPdf(File file) {
		PackageManager packageManager = mActivity.getPackageManager();
		Intent testIntent = new Intent(Intent.ACTION_VIEW);
		testIntent.setType("application/pdf");
		List list = packageManager.queryIntentActivities(testIntent,
				PackageManager.MATCH_DEFAULT_ONLY);
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_VIEW);
		Uri uri = Uri.fromFile(file);
		intent.setDataAndType(uri, "application/pdf");
		return intent;
	}

	@Override
	protected void onPostExecute(Intent result) {
		super.onPostExecute(result);
		dialog.dismiss();
	}

}
