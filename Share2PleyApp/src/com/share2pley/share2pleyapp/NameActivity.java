package com.share2pley.share2pleyapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 
 * @author Richard Vink - 4233867
 * 
 */
@SuppressLint("ShowToast")
public class NameActivity extends Activity {
	private EditText mFirstName;
	private EditText mLastName;
	private DBHelper spDb;
	private boolean isAlreadyFilled = false;
	private final Context context = this;

	// activity to fill in name. Name is saved into db and automatically filled
	// in after app restart.
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// Setup the GUI with the corresponding XML file
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_name_fill);

		mFirstName = (EditText) findViewById(R.id.edit_fillname_first_name);
		mLastName = (EditText) findViewById(R.id.edit_fillname_last_name);

		// get name from database if exists
		spDb = new DBHelper(this);
		try {
			if (spDb.personNumberOfRows() > 0) {
				isAlreadyFilled = true;
				Cursor rs = spDb.getPersonData(1);
				rs.moveToFirst();
				String firstName = rs.getString(rs
						.getColumnIndex(DBHelper.PERSONS_COLUMN_FIRSTNAME));
				String lastName = rs.getString(rs
						.getColumnIndex(DBHelper.PERSONS_COLUMN_LASTNAME));

				if (!rs.isClosed()) {
					rs.close();
				}

				if (!firstName.equals(null) && !lastName.equals(null)) {
					mFirstName.setText(firstName);
					mLastName.setText(lastName);
				}
			}
		} catch (CursorIndexOutOfBoundsException e) {
			Log.i("NameActivity", e.toString());
		}

		// if confirmed save name to db
		Button mConfirmButton = (Button) findViewById(R.id.button_fillname_confirm);
		mConfirmButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mFirstName.getText().toString().matches("")
						|| mLastName.getText().toString().matches("")) {
					Toast.makeText(context, "Please fill in your full name.",
							100).show();
				} else {
					if (isAlreadyFilled) {
						spDb.updatePersonContent(1, mFirstName.getText()
								.toString(), mLastName.getText().toString());
					} else {
						spDb.insertPerson(1, mFirstName.getText().toString(),
								mLastName.getText().toString());
					}
					Intent i = new Intent(getBaseContext(), MenuActivity.class);
					startActivity(i);
					finish();
				}
			}
		});
	}
}
