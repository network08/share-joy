package cn.com.swpu.network08.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import cn.com.swpu.network08.model.Image;

public class ImageSqliteService {
	private DatabaseHelper databaseHelper;
	private SQLiteDatabase db;
	@SuppressWarnings("unused")
	private static String [] imageColums = new String[]{DatabaseHelper.KEY_ID, DatabaseHelper.KEY_NAME, DatabaseHelper.KEY_CODE, DatabaseHelper.KEY_IMAGE};
	public static final String QUERY_BY_NAME = "select * from "+DatabaseHelper.TABLE_IMAGE+" where name=?";
	
	public static final String QUERY_BY_DATE = "select * from " + DatabaseHelper.TABLE_IMAGE+ " where date >= ?";
	
	public ImageSqliteService(Context context) {
		databaseHelper = new DatabaseHelper(context);
	}
	public long insert(Image img){
		if(img == null || img.getImage() == null){
			return -1l;
		}else{
			db = databaseHelper.getWritableDatabase();
			ContentValues cv = new ContentValues();
			//cv.put(DatabaseHelper.KEY_ID, img.getId());
			cv.put(DatabaseHelper.KEY_NAME, img.getName());
			//cv.put(DatabaseHelper.KEY_CODE, img.getName());
			cv.put(DatabaseHelper.KEY_IMAGE, img.getImage());
			return db.insert(DatabaseHelper.TABLE_IMAGE, null, cv);
		}
	}
	
	public List<Image> readTestFunc(){
		db = databaseHelper.getReadableDatabase();
		List<Image> imgList = new ArrayList<Image>();
		Cursor cursor = db.rawQuery("select * from image ", null);
		if(cursor != null){
			while (cursor.moveToNext()) {
				Image img = new Image();
				img.setName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_NAME)));
				img.setImage(cursor.getBlob(cursor.getColumnIndex(DatabaseHelper.KEY_IMAGE)));
//				img.setDate(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.KEY_DATE)));
				imgList.add(img);
			}
		}
		return imgList;
	}
	
	public List<Image> read(long deadline){
		db = databaseHelper.getReadableDatabase();
		List<Image> dataImages = new ArrayList<Image>();
		Cursor cursor = QueryData(deadline);
		if (null != cursor && cursor.moveToNext()){
			Image img = new Image();
			img.setName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_NAME)));
			img.setImage(cursor.getBlob(cursor.getColumnIndex(DatabaseHelper.KEY_IMAGE)));
			dataImages.add(img);
		}
		return dataImages;
	}
	
	private Cursor QueryData(long deadline){
		return db.rawQuery(QUERY_BY_DATE, new String[]{Long.valueOf(deadline).toString()});
	}

	public Image read(String sql, String[] name){
		Image img = null;
		try {
			db = databaseHelper.getReadableDatabase();
			Cursor cursor = db.rawQuery(sql, name);
			if(cursor != null && cursor.moveToFirst()){
				do{
					img = new Image();
					img.setName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_NAME)));
					img.setImage(cursor.getBlob(cursor.getColumnIndex(DatabaseHelper.KEY_IMAGE)));	
				}while (cursor.isLast());
				 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return img;
	}
}
