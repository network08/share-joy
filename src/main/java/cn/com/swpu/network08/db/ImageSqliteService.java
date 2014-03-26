package cn.com.swpu.network08.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import cn.com.swpu.network08.model.Image;
import cn.com.swpu.network08.util.DateUtil;

public class ImageSqliteService {
	private DatabaseHelper databaseHelper = null;
	private SQLiteDatabase db = null;
	@SuppressWarnings("unused")
	private static String [] imageColums = new String[]{DatabaseHelper.KEY_ID, DatabaseHelper.KEY_NAME, DatabaseHelper.KEY_CODE, DatabaseHelper.KEY_IMAGE};
	public static final String QUERY_BY_NAME = "select * from "+DatabaseHelper.TABLE_IMAGE+" where name=?";
	
	public static final String QUERY_BY_DATE = "select * from " + DatabaseHelper.TABLE_IMAGE+ " where date<=?";
	
	public ImageSqliteService(Context context) {
		databaseHelper = new DatabaseHelper(context);
	}
	public long insert(Image img){
		long		ret = -1l;
		
		if(img == null || img.getImage() == null){
			return -1l;
		}else{
			db = databaseHelper.getWritableDatabase();
			if (db.isOpen()){
			
				ContentValues cv = new ContentValues();
				cv.put(DatabaseHelper.KEY_NAME, img.getName());
				cv.put(DatabaseHelper.KEY_IMAGE, img.getImage());
				//cv.put(DatabaseHelper.KEY_DATE, img.getDateString());
				ret = db.insert(DatabaseHelper.TABLE_IMAGE, null, cv);
				db.close();
			}
			return ret;
		}
	}
	
	public List<String> read(long deadline){
		List<String> imgUris = new ArrayList<String>();
		Cursor cursor = QueryData(deadline);
		if (null != cursor && cursor.moveToFirst()){
			do{
				String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_NAME));
				imgUris.add(name);
			}while(cursor.moveToNext());
			cursor.close();
			if (null != db && db.isOpen()){
				db.close();
			}
		}
		return imgUris;
	}
	
	private Cursor QueryData(long deadline){
		Cursor cursor = null;
		
		db = databaseHelper.getReadableDatabase();
		if (db.isOpen()){
			//String strDateString = DateUtil.dateFormat(deadline, DateUtil.FORMAT_ID);
			cursor = db.rawQuery("select * from image", null);
			//db.close();	
		}
		return cursor;
	}

	public Image read(String sql, String[] name){
		Image img = null;

		db = databaseHelper.getReadableDatabase();
		if (db.isOpen()) {
			Cursor cursor = db.rawQuery(sql, name);
			if (cursor != null && cursor.moveToFirst()) {
				img = new Image();
				img.setName(cursor.getString(cursor
						.getColumnIndex(DatabaseHelper.KEY_NAME)));
				img.setImage(cursor.getBlob(cursor
						.getColumnIndex(DatabaseHelper.KEY_IMAGE)));
				cursor.close();
			}
			db.close();
		}
		return img;
	}
}
