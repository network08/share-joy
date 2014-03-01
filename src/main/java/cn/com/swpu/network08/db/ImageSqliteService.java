package cn.com.swpu.network08.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import cn.com.swpu.network08.model.Image;

public class ImageSqliteService {
	private DatabaseHelper databaseHelper;
	private SQLiteDatabase db;
	private static String [] imageColums = new String[]{DatabaseHelper.KEY_ID, DatabaseHelper.KEY_NAME, DatabaseHelper.KEY_CODE, DatabaseHelper.KEY_IMAGE};
	public static final String QUERY_BY_NAME = "where name = ";
	public ImageSqliteService(Context context) {
		databaseHelper = new DatabaseHelper(context);
	}
	public long insert(Image img){
		if(img == null || img.getImage() == null){
			return -1l;
		}else{
			db = databaseHelper.getWritableDatabase();
			ContentValues cv = new ContentValues();
			cv.put(DatabaseHelper.KEY_ID, img.getId());
			cv.put(DatabaseHelper.KEY_NAME, img.getName());
			cv.put(DatabaseHelper.KEY_CODE, img.getName());
			cv.put(DatabaseHelper.KEY_IMAGE, img.getImage());
			return db.insert(DatabaseHelper.TABLE_IMAGE, null, cv);
		}
	}
	
	public Image read(String where){
		Image img = null;
		String whereArgs[] = null;
		String groupBy = null;
		String having = null;
		String order = null;
		db = databaseHelper.getReadableDatabase();
		Cursor cursor = db.query(DatabaseHelper.TABLE_IMAGE, imageColums, where, whereArgs, groupBy, having, order);
		if(cursor != null){
			img = new Image();
			img.setName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_NAME)));
			img.setImage(cursor.getBlob(cursor.getColumnIndex(DatabaseHelper.KEY_IMAGE))); 
		}
		return img;
	}
}
