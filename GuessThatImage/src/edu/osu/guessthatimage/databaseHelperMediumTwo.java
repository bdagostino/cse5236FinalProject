package edu.osu.guessthatimage;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class databaseHelperMediumTwo {
	private static final String DATABASE_NAME = "GuessThatImage.db";
	private static final int DATABASE_VERSION = 1;
	private static final String TABLE_MEDIUM_TWOMIN = "Difficulty: Medium, Time: 2 min";
	private Context context;
	private SQLiteDatabase db;
	private SQLiteStatement insertStmt;
	private static final String INSERT_MEDIUM_TWOMIN = "insert into " + TABLE_MEDIUM_TWOMIN + "(name, score) values (?, ?)";

	public databaseHelperMediumTwo(Context context) {
	      this.context = context;
	      GuessThatImageOpenHelperMediumTwo openHelper = new GuessThatImageOpenHelperMediumTwo(this.context);
	      this.db = openHelper.getWritableDatabase();
	      this.insertStmt = this.db.compileStatement(INSERT_MEDIUM_TWOMIN);
	   }
 
 private static class GuessThatImageOpenHelperMediumTwo extends SQLiteOpenHelper {
	   GuessThatImageOpenHelperMediumTwo(Context context) {
  	  super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       db.execSQL("CREATE TABLE " + TABLE_MEDIUM_TWOMIN + "(id INTEGER PRIMARY KEY, name TEXT, score TEXT)");
       }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

       Log.w("Example", "Upgrading database; this will drop and recreate the tables.");
       db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEDIUM_TWOMIN);
       onCreate(db);
    }
 }
 
 public long insert(String name, String score) {
    this.insertStmt.bindString(1, name);
    this.insertStmt.bindString(2, score);
    return this.insertStmt.executeInsert();
 }
 public void deleteAll() {
    this.db.delete(TABLE_MEDIUM_TWOMIN, null, null);
 }
	  
 public List<String> selectAll(String username, String score) {
    List<String> list = new ArrayList<String>();
    Cursor cursor = this.db.query(TABLE_MEDIUM_TWOMIN, new String[] { "name", "score" }, "name = '"+ username +"' AND score= '"+ score+"'", null, null, null, "score desc");
    if (cursor.moveToFirst()) {
      do {
      	 list.add(cursor.getString(0));
      	 list.add(cursor.getString(1));
       } while (cursor.moveToNext()); 
    }
    if (cursor != null && !cursor.isClosed()) {
       cursor.close();
    }
    return list;
 }
}
