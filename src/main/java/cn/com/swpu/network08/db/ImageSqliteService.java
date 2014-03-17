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
	private DatabaseHelper databaseHelper;
	private SQLiteDatabase db;
	@SuppressWarnings("unused")
	private static String [] imageColums = new String[]{DatabaseHelper.KEY_ID, DatabaseHelper.KEY_NAME, DatabaseHelper.KEY_CODE, DatabaseHelper.KEY_IMAGE};
	public static final String QUERY_BY_NAME = "select * from "+DatabaseHelper.TABLE_IMAGE+" where name=?";
	
	public static final String QUERY_BY_DATE = "select * from " + DatabaseHelper.TABLE_IMAGE+ " where date <= ?";
	
	public ImageSqliteService(Context context) {
		databaseHelper = new DatabaseHelper(context);
	}
	public long insert(Image img){
		if(img == null || img.getImage() == null){
			return -1l;
		}else{
			db = databaseHelper.getWritableDatabase();
			ContentValues cv = new ContentValues();
			cv.put(DatabaseHelper.KEY_NAME, img.getName());
			cv.put(DatabaseHelper.KEY_IMAGE, img.getImage());
			cv.put(DatabaseHelper.KEY_DATE, img.getDateString());
			return db.insert(DatabaseHelper.TABLE_IMAGE, null, cv);
		}
	}
	
	public List<String> read(long deadline){
		db = databaseHelper.getReadableDatabase();
		List<String> imgUris = new ArrayList<String>();
		Cursor cursor = QueryData(deadline);
		if (null != cursor && cursor.moveToFirst()){
			do{
				String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_NAME));
				imgUris.add(name);
			}while(cursor.moveToNext());
		}
		return imgUris;
	}
	
	private Cursor QueryData(long deadline){
		return db.rawQuery(QUERY_BY_DATE, new String[]{DateUtil.dateFormat(deadline, DateUtil.FORMAT_ID)});
	}

	public Image read(String sql, String[] name){
		Image img = new Image();
		try {
			db = databaseHelper.getReadableDatabase();
			Cursor cursor = db.rawQuery(sql, name);
			if(cursor != null && cursor.moveToFirst()){
					img.setName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_NAME)));
					img.setImage(cursor.getBlob(cursor.getColumnIndex(DatabaseHelper.KEY_IMAGE)));			 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return img;
	}
}
