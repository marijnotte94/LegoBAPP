package com.share2pley.share2pleyapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.internal.view.ContextThemeWrapper;
import android.widget.ArrayAdapter;

public class MissingBrickDialogFragment extends DialogFragment {
	private int mSetNumber;
	private String mBrickName;
	private DBHelper spDb;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		super.onCreateDialog(savedInstanceState);
		mSetNumber = getArguments().getInt("SETNUMBER");
		mBrickName = getArguments().getString("BRICKNAME");
		int mAmount = getArguments().getInt("AMOUNT");

		spDb = new DBHelper(getActivity());

		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(
				new ContextThemeWrapper(getActivity(),
						android.R.layout.select_dialog_multichoice));
		builder.setTitle(R.string.missing_bricks_title);

		final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
				getActivity(), android.R.layout.select_dialog_item);
		for (int i = 0; i < mAmount; i++) {
			arrayAdapter.add(i + 1 + "");
		}
		builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (spDb.getMissing(mSetNumber, mBrickName) != null) {
					spDb.updateMissing(mSetNumber, mBrickName, which + 1);
				} else {
					spDb.insertMissing(mSetNumber, mBrickName, which + 1);
				}
				dialog.dismiss();
			}
		}).setNegativeButton(R.string.cancel,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();

					}
				});

		// Create the AlertDialog object and return it
		return builder.create();
	}
}
