package edu.osu.guessthatimage;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class databaseHelperEasyTwo {
	private static final String DATABASE_NAME = "GuessThatImage.db";
	private static final int DATABASE_VERSION = 1;
	private static final String TABLE_EASY_TWOMIN = "Difficulty: Easy, Time: 10 min";
	private Context context;
	private SQLiteDatabase db;
	private SQLiteStatement insertStmt;
	private static final String INSERT_EASY_TWOMIN = "insert into " + TABLE_EASY_TWOMIN + "(name, score) values (?, ?)";
	
	public databaseHelperEasyTwo(Context context) {
	      this.context = context;
	      GuessThatImageOpenHelperEasyTwo openHelper = new GuessThatImageOpenHelperEasyTwo(this.context);
	      this.db = openHelper.getWritableDatabase();
	      this.insertStmt = this.db.compileStatement(INSERT_EASY_TWOMIN);
	   }
 
 private static class GuessThatImageOpenHelperEasyTwo extends SQLiteOpenHelper {
	   GuessThatImageOpenHelperEasyTwo(Context context) {
  	  super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       db.execSQL("CREATE TABLE " + TABLE_EASY_TWOMIN + "(id INTEGER PRIMARY KEY, name TEXT, score TEXT)");
       }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

       Log.w("Example", "Upgrading database; this will drop and recreate the tables.");
       db.execSQL("DROP TABLE IF EXISTS " + TABLE_EASY_TWOMIN);
       onCreate(db);
    }
 }
 
 public long insert(String name, String score) {
    this.insertStmt.bindString(1, name);
    this.insertStmt.bindString(2, score);
    return this.insertStmt.executeInsert();
 }
 public void deleteAll() {
    this.db.delete(TABLE_EASY_TWOMIN, null, null);
 }
	  
 public List<String> selectAll(String username, String score) {
    List<String> list = new ArrayList<String>();
    Cursor cursor = this.db.query(TABLE_EASY_TWOMIN, new String[] { "name", "score" }, "name = '"+ username +"' AND score= '"+ score+"'", null, null, null, "score desc");
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
