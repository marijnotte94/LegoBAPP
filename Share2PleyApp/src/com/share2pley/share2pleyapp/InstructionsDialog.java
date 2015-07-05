package com.share2pley.share2pleyapp;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.support.v4.app.DialogFragment;
import android.support.v7.internal.view.ContextThemeWrapper;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.share2pley.share2pleyapp.Model.PDFDownloaderTask;
import com.share2pley.share2pleyapp.Model.Set;
import com.share2pley.share2pleyapp.Model.SetLab;

public class InstructionsDialog extends DialogFragment {
	private int mPageNumber;
	private String mNotEnoughSpace = "";

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPageNumber = getArguments().getInt("PAGE");
		int mAmount = getArguments().getInt("AMOUNT");
		AlertDialog.Builder builder = new AlertDialog.Builder(
				new ContextThemeWrapper(getActivity(),
						android.R.layout.select_dialog_multichoice));
		final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
				getActivity(), android.R.layout.select_dialog_item);
		for (int i = 0; i < mAmount; i++) {
			arrayAdapter.add(SetLab.get(getActivity()).getSet(mPageNumber)
					.getName()
					+ " " + (i + 1));
		}
		builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				String internalStorage = getAvailableInternalMemorySize();
				String externalStorage = getAvailableExternalMemorySize();
				if (checksIfMb(externalStorage)) {
					String[] split = externalStorage.split("M");
					double value = Integer.parseInt(split[0]);
					if (value > 10) {
						createPdfFile(Environment.getExternalStorageDirectory()
								.toString(), which);
					}
				} else if (checksIfMb(internalStorage)) {
					String[] split = externalStorage.split("M");
					int value = Integer.parseInt(split[0]);
					if (value > 10) {
						createPdfFile(Environment.getDownloadCacheDirectory()
								.toString(), which);
					}
				} else {
					mNotEnoughSpace = "Not enough space on Phone";
					Toast.makeText(getActivity(), mNotEnoughSpace,
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		return builder.create();
	}

	public void createPdfFile(String location, int clicked) {
		Set set = SetLab.get(getActivity()).getSet(mPageNumber);
		String setID = set.getSetNumber() + "";
		File folder = new File(location);
		folder.mkdir();
		File file = new File(folder, setID + "_" + clicked + ".pdf");
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ProgressDialog dialog = new ProgressDialog(getActivity());
		PDFDownloaderTask downloadTask = new PDFDownloaderTask(getActivity(),
				dialog, set, clicked);
		downloadTask.execute(file);
		try {
			Intent intent = downloadTask.get();
			try {
				getActivity().startActivity(intent);
			} catch (ActivityNotFoundException e) {
				Toast.makeText(this.getActivity(), "No PDF Reader Found",
						Toast.LENGTH_SHORT).show();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
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
	 * Checks the internal Free space
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public String getAvailableInternalMemorySize() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		return formatSize(availableBlocks * blockSize);
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
