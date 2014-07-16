package edu.osu.guessthatimage;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

public class databaseHelperHighScores {
	private static final String DATABASE_NAME = "GuessThatImage.db";
	private static final int DATABASE_VERSION = 1;
	private static final String TABLE_HIGHSCORES = "HighScore";//"Difficulty: Easy, Time: 5 min";
	private Context context;
	private SQLiteDatabase db;
	private SQLiteStatement insertStmt;
	private static final String INSERT_HIGHSCORE = "insert into " + TABLE_HIGHSCORES + "(name, score, pictures, time) values (?, ?, ?, ?)";
	private static final String TABLE_HIGHSCORES_NAME=null,TABLE_HIGHSCORES_SCORE=null,TABLE_HIGHSCORES_PICTURES=null,TABLE_HIGHSCORES_TIME=null;

	
	public databaseHelperHighScores(Context context) {
	      this.context = context;
	      GuessThatImageOpenHelperEasyFive openHelper = new GuessThatImageOpenHelperEasyFive(this.context);
	      this.db = openHelper.getWritableDatabase();
	      this.insertStmt = this.db.compileStatement(INSERT_HIGHSCORE);
	   }
 
 private static class GuessThatImageOpenHelperEasyFive extends SQLiteOpenHelper {
	   GuessThatImageOpenHelperEasyFive(Context context) {
  	  super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       db.execSQL("CREATE TABLE " + TABLE_HIGHSCORES + "(id INTEGER PRIMARY KEY, name TEXT, score TEXT, pictures TEXT, time TEXT)");
       }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

      // Log.w("Example", "Upgrading database; this will drop and recreate the tables.");
       //db.execSQL("DROP TABLE IF EXISTS " + TABLE_EASY_FIVEMIN);
       //onCreate(db);
    }
 }
 
 public long insert(String name, String score, String pictures, String time) {
    this.insertStmt.bindString(1, name);
    this.insertStmt.bindString(2, score);
    this.insertStmt.bindString(3, pictures);
    this.insertStmt.bindString(4, time);
    return this.insertStmt.executeInsert();
 }
 public void deleteAll() {
    this.db.delete(INSERT_HIGHSCORE, null, null);
 }
	  
 public List<String> selectAll(String numberPics, String time) {
	List<String> list = new ArrayList<String>();
	Cursor cursor = this.db.rawQuery("SELECT * FROM HighScore WHERE pictures="+numberPics+" AND time="+time,null);
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
