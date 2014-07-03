package edu.osu.guessthatimage;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class databaseHelperHardFive {
	private static final String DATABASE_NAME = "GuessThatImage4.db";
	private static final int DATABASE_VERSION = 1;
	private static final String TABLE_HARD_FIVEMIN = "Hard_5";//"Difficulty: Hard, Time: 5 min";
	private Context context;
	private SQLiteDatabase db;
	private SQLiteStatement insertStmt;
	private static final String INSERT_HARD_FIVEMIN = "insert into " + TABLE_HARD_FIVEMIN + "(name, score) values (?, ?)";

	public databaseHelperHardFive(Context context) {
	      this.context = context;
	      GuessThatImageOpenHelperHardFive openHelper = new GuessThatImageOpenHelperHardFive(this.context);
	      this.db = openHelper.getWritableDatabase();
	      this.insertStmt = this.db.compileStatement(INSERT_HARD_FIVEMIN);
	   }
 
 private static class GuessThatImageOpenHelperHardFive extends SQLiteOpenHelper {
	   GuessThatImageOpenHelperHardFive(Context context) {
  	  super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       db.execSQL("CREATE TABLE " + TABLE_HARD_FIVEMIN + "(id INTEGER PRIMARY KEY, name TEXT, score TEXT)");
       }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

       //Log.w("Example", "Upgrading database; this will drop and recreate the tables.");
       //db.execSQL("DROP TABLE IF EXISTS " + TABLE_HARD_FIVEMIN);
       //onCreate(db);
    }
 }
 
 public long insert(String name, String score) {
    this.insertStmt.bindString(1, name);
    this.insertStmt.bindString(2, score);
    return this.insertStmt.executeInsert();
 }
 public void deleteAll() {
    this.db.delete(TABLE_HARD_FIVEMIN, null, null);
 }
	  
 public List<String> selectAll() {
    List<String> list = new ArrayList<String>();
    Cursor cursor = this.db.rawQuery("select * from Hard_5 order by score desc", null);
    if (cursor.moveToFirst()) {
      do {
      	 list.add(cursor.getString(1));
      	 list.add(cursor.getString(2));
       } while (cursor.moveToNext()); 
    }
    if (cursor != null && !cursor.isClosed()) {
       cursor.close();
    }
    return list;
 }
}
