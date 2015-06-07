package com.share2pley.share2pleyapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.share2pley.share2pleyapp.Model.Missing;

/**
 * 
 * @author Richard Vink - 4233867
 * 
 */
public class DBHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "spDatabase.db";
	public static final String PERSONS_TABLE_NAME = "Persons";
	public static final String PERSONS_COLUMN_FIRSTNAME = "Firstname";
	public static final String PERSONS_COLUMN_LASTNAME = "Lastname";
	public static final String PERSONS_COLUMN_CLEARED = "Cleared";
	public static final String MISSING_TABLE_NAME = "Missing";
	public static final String MISSING_COLUMN_PERSONID = "PersonId";
	public static final String MISSING_COLUMN_SET = "SetNumber";
	public static final String MISSING_COLUMN_BRICKNAME = "BrickName";
	public static final String MISSING_COLUMN_AMOUNT = "Amount";
	private static final String SETS_TABLE_NAME = "Sets";
	private static final String SETS_COLUMN_SETNUMBER = "setNumber";
	private static final String SETS_COLUMN_ISDONE = "isDone";
	public HashMap hp;

	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table Persons "
				+ "(id integer primary key, Firstname text, Lastname text, Cleared integer)");
		db.execSQL("create table Missing "
				+ "(id integer primary key, PersonId integer, SetNumber integer, BrickName text, Amount integer)");
		// db.execSQL("create table Sets "
		// + "(setId integer primary key, isDone integer)");
		// addSets();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS Persons");
		db.execSQL("DROP TABLE IF EXISTS Missing");
		db.execSQL("DROP TABLE IF EXISTS Sets");
		onCreate(db);
	}

	public boolean insertPerson(String firstname, String lastname, int cleared) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();

		contentValues.put("Firstname", firstname);
		contentValues.put("Lastname", lastname);
		contentValues.put("Cleared", cleared);

		db.insert("Persons", null, contentValues);
		return true;
	}

	public boolean insertMissing(int setNumber, String brickName, int amount) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();

		contentValues.put("SetNumber", setNumber);
		contentValues.put("BrickName", brickName);
		contentValues.put("Amount", amount);

		db.insert("Missing", null, contentValues);
		return true;
	}

	public Cursor getPersonData(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("select * from Persons where id =" + id + "",
				null);
		return res;
	}

	public Cursor getMissingData(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("select * from Missing where id=" + id + "",
				null);
		return res;
	}

	public int personNumberOfRows() {
		SQLiteDatabase db = this.getReadableDatabase();
		int numRows = (int) DatabaseUtils.queryNumEntries(db,
				PERSONS_TABLE_NAME);
		return numRows;
	}

	public int missingNumberOfRows() {
		SQLiteDatabase db = this.getReadableDatabase();
		int numRows = (int) DatabaseUtils.queryNumEntries(db,
				MISSING_TABLE_NAME);
		return numRows;
	}

	public boolean updatePersonContent(Integer id, String firstname,
			String lastname, int cleared) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("Firstname", firstname);
		contentValues.put("Lastname", lastname);
		contentValues.put("Cleared", cleared);
		db.update("Persons", contentValues, "id = ?",
				new String[] { Integer.toString(id) });
		return true;
	}

	public Integer deletePerson(Integer id) {
		SQLiteDatabase db = this.getWritableDatabase();
		return db.delete("Persons", "id = ?",
				new String[] { Integer.toString(id) });
	}

	public List<Missing> getMissingBicksById() {
		List<Missing> missings = new ArrayList<Missing>();

		String selectQuery = "SELECT * FROM " + MISSING_TABLE_NAME;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		if (c.moveToFirst()) {
			do {
				int personId = c.getInt(c
						.getColumnIndex(MISSING_COLUMN_PERSONID));
				int setNumber = c.getInt(c.getColumnIndex(MISSING_COLUMN_SET));
				String description = c.getString(c
						.getColumnIndex(MISSING_COLUMN_BRICKNAME));
				int amount = c.getInt(c.getColumnIndex(MISSING_COLUMN_AMOUNT));
				Missing missing = new Missing(personId, setNumber, description,
						amount);
				missings.add(missing);
			} while (c.moveToNext());
		}
		return missings;
	}

	public Integer deleteMissing(Integer id) {
		SQLiteDatabase db = this.getWritableDatabase();
		return db.delete("Missing", "id = ?",
				new String[] { Integer.toString(id) });
	}

	public void addSets() {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		for (int i = 1; i <= 16; i++) {
			values.put(SETS_COLUMN_SETNUMBER, i);
			values.put(SETS_COLUMN_ISDONE, 0);
			db.insert(SETS_TABLE_NAME, null, values);
		}
	}
}