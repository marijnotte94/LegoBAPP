package com.share2pley.share2pleyapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class MissingBrickDialogFragment extends DialogFragment {

	private int mSetNumber;
	private String mBrickName;
	private DBHelper spDb;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		super.onCreateDialog(savedInstanceState);
		mSetNumber = getArguments().getInt("SETNUMBER");
		mBrickName = getArguments().getString("BRICKNAME");
		spDb = new DBHelper(getActivity());

		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(R.string.missing_bricks_title);
		builder.setItems(R.array.numbers,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						spDb.insertMissing(mSetNumber, mBrickName, which + 1);
					}
				}).setNegativeButton(R.string.cancel,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

					}
				});

		// Create the AlertDialog object and return it
		return builder.create();
	}
}
