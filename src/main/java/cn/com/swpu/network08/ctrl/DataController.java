package cn.com.swpu.network08.ctrl;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import cn.com.swpu.network08.db.ImageSqliteService;
import cn.com.swpu.network08.model.Image;

/**
 * 
 * @author hq
 *
 */
public class DataController {
	private List<Image> mHistory = null;
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
		mHistory = new ArrayList<Image>();
		mImageReader = new ImageSqliteService(context);
	}
	
	
	public boolean LoadHistory(long deadline){
		
		mHistory.clear();
		mHistory = mImageReader.read(deadline);
		
		return true;
	}
	
	public Image GetBefore(){
		if(mCurIndex > 0){
			--mCurIndex;	
		}
		return mHistory.get(mCurIndex);
	}
	
	public Image GetAfter(){
		if (mCurIndex < (mHistory.size() - 1)){
			++mCurIndex;
		}
		return mHistory.get(mCurIndex);
	}
}
