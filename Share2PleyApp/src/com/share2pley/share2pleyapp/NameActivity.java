package com.share2pley.share2pleyapp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * 
 * @author Richard Vink - 4233867
 * 
 */
public class NameActivity extends Activity {
	private DBHelper spDb;
	Button mConfirmButton;
	EditText mFirstName;
	EditText mLastName;
	private boolean isAlreadyFilled = false;

	// activity to fill in name. Name is saved into db and automatically filled
	// in after app restart.
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// Setup the GUI with the corresponding XML file
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_name_fill);

		mFirstName = (EditText) findViewById(R.id.edit_fillname_first_name);
		mLastName = (EditText) findViewById(R.id.edit_fillname_last_name);

		this.deleteDatabase("spDatabase.db");
		// get name from database if exists
		spDb = new DBHelper(this);
		try {
			if (spDb.getPersonData(1) != null) {
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
		mConfirmButton = (Button) findViewById(R.id.button_fillname_confirm);
		mConfirmButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isAlreadyFilled) {
					spDb.updatePersonContent(1,
							mFirstName.getText().toString(), mLastName
									.getText().toString(), 0);
				} else {
					spDb.insertPerson(mFirstName.getText().toString(),
							mLastName.getText().toString(), 0);
				}
				Intent i = new Intent(getBaseContext(), MenuActivity.class);
				startActivity(i);
				finish();
			}
		});
	}

}
