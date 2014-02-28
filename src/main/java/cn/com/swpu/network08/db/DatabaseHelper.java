package cn.com.swpu.network08.db;

import cn.com.swpu.network08.model.Image;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 
 * @author franklin.li
 *
 */
public class DatabaseHelper extends SQLiteOpenHelper{
	public static final String DB_NAME = "share-joy.db";
	public static final int DB_VERSION = 1;
	
	public static final String TABLE_USER = "user";
	public static final String TABLE_IMAGE = "image";
	
	public static final String KEY_ID = "_id";
	public static final String KEY_NAME = "name";
	public static final String KEY_EMAIL = "email";
	public static final String KEY_PHONE = "phone";
	public static final String KEY_IMAGE = "image";
	public static final String KEY_CODE = "code";
	
	public DatabaseHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String createTableUser = "Create table " + TABLE_USER + 
				"(" + KEY_ID + " integer primary key autoincrement," 
				+ KEY_NAME + " text," + KEY_EMAIL + " text," + KEY_PHONE + " text" + " );";
		String createTableImage = "Create table " + TABLE_IMAGE + 
				"(" + KEY_ID + " integer primary key autoincrement," 
				+ KEY_NAME + " text," + KEY_IMAGE + " BLOB" + " );";
		db.execSQL(createTableUser);
		db.execSQL(createTableImage);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String dropTableUser = " DROP TABLE IF EXISTS " + TABLE_USER;
		String dropTableImage = " DROP TABLE IF EXISTS " + TABLE_IMAGE;
        db.execSQL(dropTableUser);
        db.execSQL(dropTableImage);
        onCreate(db);
	}
	
}