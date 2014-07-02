package edu.osu.guessthatimage;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class databaseHelperHardTen {
	private static final String DATABASE_NAME = "GuessThatImage.db";
	private static final int DATABASE_VERSION = 10;
	private static final String TABLE_HARD_TENMIN = "Hard_10";//"Difficulty: Hard, Time: 10 min";
	private Context context;
	private SQLiteDatabase db;
	private SQLiteStatement insertStmt;
	private static final String INSERT_HARD_TENMIN = "insert into " + TABLE_HARD_TENMIN + "(name, score) values (?, ?)";

	public databaseHelperHardTen(Context context) {
	      this.context = context;
	      GuessThatImageOpenHelperHardTen openHelper = new GuessThatImageOpenHelperHardTen(this.context);
	      this.db = openHelper.getWritableDatabase();
	      this.insertStmt = this.db.compileStatement(INSERT_HARD_TENMIN);
	   }
 
 private static class GuessThatImageOpenHelperHardTen extends SQLiteOpenHelper {
	   GuessThatImageOpenHelperHardTen(Context context) {
  	  super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       db.execSQL("CREATE TABLE " + TABLE_HARD_TENMIN + "(id INTEGER PRIMARY KEY, name TEXT, score TEXT)");
       }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

       Log.w("Example", "Upgrading database; this will drop and recreate the tables.");
       db.execSQL("DROP TABLE IF EXISTS " + TABLE_HARD_TENMIN);
       onCreate(db);
    }
 }
 
 public long insert(String name, String score) {
    this.insertStmt.bindString(1, name);
    this.insertStmt.bindString(2, score);
    return this.insertStmt.executeInsert();
 }
 public void deleteAll() {
    this.db.delete(TABLE_HARD_TENMIN, null, null);
 }
	  
 public List<String> selectAll(String username, String score) {
    List<String> list = new ArrayList<String>();
    Cursor cursor = this.db.query(TABLE_HARD_TENMIN, new String[] { "name", "score" }, "name = '"+ username +"' AND score= '"+ score+"'", null, null, null, "score desc");
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
