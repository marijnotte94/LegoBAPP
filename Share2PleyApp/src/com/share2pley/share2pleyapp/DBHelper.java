package com.share2pley.share2pleyapp;

import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	public static final String DATABASE_NAME = "spDatabase.db";
	public static final String PERSONS_TABLE_NAME = "Persons";
	public static final String PERSONS_COLUMN_FIRSTNAME = "Firstname";
	public static final String PERSONS_COLUMN_LASTNAME = "Lastname";
	public static final String PERSONS_COLUMN_CLEARED = "Cleared";
	
	private HashMap hp;
	
	public DBHelper(Context context){
		super(context, DATABASE_NAME , null, 1);
	}
	
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(
			"create table Persons " + 
		"(Firsname text, Lastname text, cleared integer)"
		);
		
	}
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
	
	public Cursor getData(String firstname, String lastname) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("select * from Persons where Firstname =" + firstname + "AND Lastname = " + lastname, null);
		return res;
	}
	
	public int numberOfRows() {
		SQLiteDatabase db = this.getReadableDatabase();
		int numRows = (int) DatabaseUtils.queryNumEntries(db, PERSONS_TABLE_NAME);
		return numRows;
	}
	
	public boolean updateContent(String firstname, String lastname, int cleared) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("Firstname", firstname);
		contentValues.put("Lastname", lastname);
		contentValues.put("Cleared", cleared);
		db.update("Persons", contentValues, "FirstName = ?", new String[] {firstname});
		return true;
	}
	
	public Integer deletePerson(String firstname, String lastname) {
		return Integer.decode("3");
	}
}