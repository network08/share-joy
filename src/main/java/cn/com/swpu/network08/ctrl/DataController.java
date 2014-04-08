package cn.com.swpu.network08.ctrl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import cn.com.swpu.network08.db.DatabaseHelper;
import cn.com.swpu.network08.db.ImageSqliteService;
import cn.com.swpu.network08.model.Image;
import cn.com.swpu.network08.util.ImageUtil;

/**
 * 
 * @author hq
 *
 */
public class DataController {
	private List<String> mHistoryUris = null;
	private int mCurIndex;
	private ImageSqliteService mImageReader = null;
	private boolean hasChanged = false;
	private static DataController mInstance = null;
	
	public static boolean Initialize(Context context, long deadline){
		context.deleteDatabase(DatabaseHelper.DB_NAME);
		mInstance = new DataController(context);
		mInstance.LoadHistory(deadline);
		return true;
	}
	
	public static DataController Data(){
		return mInstance;
	}
	
	private DataController(Context context){
		mCurIndex = 0;
		mHistoryUris = new ArrayList<String>();
		mImageReader = new ImageSqliteService(context);
	}
	
	
	public boolean LoadHistory(long deadline){

		mHistoryUris.clear();
		mHistoryUris = mImageReader.read(deadline);
		if (mHistoryUris.size() > 0) {
			mCurIndex = mHistoryUris.size() - 1;
		}
		return true;
	}
	
	public boolean checkAndReload() {
		if (hasChanged){
			mCurIndex = mHistoryUris.size() - 1;
			hasChanged = false;
			return true;
		}
		return false;
	}
	
	public int Size(){
		return mHistoryUris.size();
	}
	
	public boolean InsertImage(Image img){
		long ret = mImageReader.insert(img);
		if (ret == 0){
			Log.e("DB", "insert faild, ret = " + String.valueOf(ret));
			return false;
		}
		//mHistoryUris.add(img.getName());
		mHistoryUris.add(mHistoryUris.size(), img.getName());
		hasChanged = true;
		return true;
	}

	public Image GetByNameUri(String nameUri){
		return mImageReader.read(ImageSqliteService.QUERY_BY_NAME, new String[]{nameUri});
	}
	
	public void reset(){
		mCurIndex = mHistoryUris.size() - 1;
	}
	
	public boolean isLast(){
		return mCurIndex == (mHistoryUris.size() - 1);
		}
	public boolean isFirst(){
		return mCurIndex == 0;
	}
	
	public String getCurrNameUri(){
		return mHistoryUris.get(mCurIndex);
	}
	
	public void moveToBefore(){
		if(mCurIndex > 0){
			--mCurIndex;	
		}
	}
	
	public void moveToAfter(){
		if (mCurIndex < (mHistoryUris.size() - 1)){
			++mCurIndex;
		}
	}
	
	private Bitmap getDiskBitmap(String pathString)  
	{  
	    Bitmap bitmap = null;  
	    try  
	    {  
	        File file = new File(pathString);  
	        if(file.exists())  
	        {  
	            bitmap = BitmapFactory.decodeFile(pathString);  
	        }  
	    } catch (Exception e)  
	    {  
	        // TODO: handle exception  
	    }  
	       
	    return bitmap;  
	}
}
