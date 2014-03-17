package cn.com.swpu.network08.ctrl;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import cn.com.swpu.network08.db.ImageSqliteService;
import cn.com.swpu.network08.model.Image;

/**
 * 
 * @author hq
 *
 */
public class DataController {
	private List<String> mHistoryUris = null;
	private int mCurIndex;
	private ImageSqliteService mImageReader = null;
	
	private static DataController mInstance = null;
	
	public static boolean Initialize(Context context, long deadline){
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
		if (mHistoryUris.size() == 0){
			//读取sd卡测试图片，存入数据库
			
		}
		return true;
	}
	
	public Image GetByNameUri(String nameUri){
		return mImageReader.read(ImageSqliteService.QUERY_BY_NAME, new String[]{nameUri});
	}
	
	public boolean HasNext(){
		return mCurIndex == (mHistoryUris.size() - 1);
		}
	
	public String GetBefore(){
		if(mCurIndex > 0){
			--mCurIndex;	
		}
		return mHistoryUris.get(mCurIndex);
	}
	
	public String GetAfter(){
		if (mCurIndex < (mHistoryUris.size() - 1)){
			++mCurIndex;
		}
		return mHistoryUris.get(mCurIndex);
	}
}
