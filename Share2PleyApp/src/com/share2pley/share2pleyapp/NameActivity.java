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

public class NameActivity extends Activity{
	private DBHelper spDb;
	Button mConfirmButton;
	EditText mFirstName;
	EditText mLastName;
	private boolean isAlreadyFilled = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		//Setup the GUI with the corresponding XML file
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_name_fill);

		mFirstName = (EditText)findViewById(R.id.edit_first_name);
		mLastName = (EditText)findViewById(R.id.edit_last_name);

		getBaseContext().deleteDatabase("Persons");
		spDb = new DBHelper(this);
		try {
			if(spDb.getData(1)!= null) {
				isAlreadyFilled=true;
				Cursor rs = spDb.getData(1);
				rs.moveToFirst();
				String firstName = rs.getString(rs.getColumnIndex(DBHelper.PERSONS_COLUMN_FIRSTNAME));
				String lastName = rs.getString(rs.getColumnIndex(DBHelper.PERSONS_COLUMN_LASTNAME));

				if(!rs.isClosed()) {
					rs.close();
				}

				if(!firstName.equals(null) && !lastName.equals(null)) {
					mFirstName.setText((CharSequence)firstName);
					mLastName.setText((CharSequence)lastName);
				}
			}
		} catch (CursorIndexOutOfBoundsException e) {
			Log.i("NameActivity", e.toString());
		}

		mConfirmButton = (Button)findViewById(R.id.confirm_button);
		mConfirmButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(isAlreadyFilled) {
					spDb.updateContent(1, mFirstName.getText().toString(), mLastName.getText().toString(), 0);
				}
				spDb.insertPerson(mFirstName.getText().toString(), mLastName.getText().toString(), 0);
				Intent i = new Intent(getBaseContext(), MenuActivity.class);
				startActivity(i);
				finish();
			}
		});		
	}

}
