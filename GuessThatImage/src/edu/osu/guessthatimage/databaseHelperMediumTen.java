package edu.osu.guessthatimage;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;


import java.util.ArrayList;
import java.util.List;

public class databaseHelperMediumTen {
	private static final String DATABASE_NAME = "GuessThatImage8.db";
	private static final int DATABASE_VERSION = 1;
	private static final String TABLE_MEDIUM_TENMIN = "Medium_10";//"Difficulty: Medium, Time: 10 min";
	private Context context;
	private SQLiteDatabase db;
	private SQLiteStatement insertStmt;
	private static final String INSERT_MEDIUM_TENMIN = "insert into " + TABLE_MEDIUM_TENMIN + "(name, score) values (?, ?)";

	public databaseHelperMediumTen(Context context) {
	      this.context = context;
	      GuessThatImageOpenHelperMediumTen openHelper = new GuessThatImageOpenHelperMediumTen(this.context);
	      this.db = openHelper.getWritableDatabase();
	      this.insertStmt = this.db.compileStatement(INSERT_MEDIUM_TENMIN);
	   }
 
 private static class GuessThatImageOpenHelperMediumTen extends SQLiteOpenHelper {
	   GuessThatImageOpenHelperMediumTen(Context context) {
  	  super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       db.execSQL("CREATE TABLE " + TABLE_MEDIUM_TENMIN + "(id INTEGER PRIMARY KEY, name TEXT, score TEXT)");
       }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

       //Log.w("Example", "Upgrading database; this will drop and recreate the tables.");
       //db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEDIUM_TENMIN);
       //onCreate(db);
    }
 }
 
 public long insert(String name, String score) {
    this.insertStmt.bindString(1, name);
    this.insertStmt.bindString(2, score);
    return this.insertStmt.executeInsert();
 }
 public void deleteAll() {
    this.db.delete(TABLE_MEDIUM_TENMIN, null, null);
 }
	  
 public List<String> selectAll() {
    List<String> list = new ArrayList<String>();
    Cursor cursor = this.db.rawQuery("select * from Medium_10 order by score desc", null);
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
