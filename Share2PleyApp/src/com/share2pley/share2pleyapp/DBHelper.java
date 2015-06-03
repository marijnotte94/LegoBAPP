package com.share2pley.share2pleyapp;

import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 
 * @author Richard Vink - 4233867
 * 
 */
public class DBHelper extends SQLiteOpenHelper {
	public static final String DATABASE_NAME = "spDatabase.db";
	public static final String PERSONS_TABLE_NAME = "Persons";
	public static final String PERSONS_COLUMN_FIRSTNAME = "Firstname";
	public static final String PERSONS_COLUMN_LASTNAME = "Lastname";
	public static final String PERSONS_COLUMN_CLEARED = "Cleared";
	private HashMap hp;

	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table Persons "
				+ "(id integer primary key, Firstname text, Lastname text, Cleared integer)");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS Persons");
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

	public Cursor getData(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("select * from Persons where id =" + id + "",
				null);
		return res;
	}

	public int numberOfRows() {
		SQLiteDatabase db = this.getReadableDatabase();
		int numRows = (int) DatabaseUtils.queryNumEntries(db,
				PERSONS_TABLE_NAME);
		return numRows;
	}

	public boolean updateContent(Integer id, String firstname, String lastname,
			int cleared) {
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
}