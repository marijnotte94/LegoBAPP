package com.share2pley.share2pleyapp;

import java.util.HashMap;

import android.content.Context;
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
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {}
}