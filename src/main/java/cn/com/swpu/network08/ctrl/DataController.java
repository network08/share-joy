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
		File file = null;
		File[] files = null;
		if (mHistoryUris.size() == 0){
			//读取sd卡测试图片，存入数据库,测试时使用
			try {
				file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/Camera/");
				files = file.listFiles();
			} catch (Exception e) {
				// TODO: handle exception
			}

			ArrayList<String> allfile = new ArrayList<String>();
			for (File f : files){
				if (f.getName().substring(0, 1).equals(".")){
					continue;
				}
				if (f.isFile() && f.getPath().endsWith(".jpg")){
					allfile.add(f.getPath());
					if (allfile.size() > 8){
						break;
					}
				}
			}
			int id = 1000;
			if (allfile.size() > 0) {
				// 将图片存入数据库
				for (int i = 0; i < allfile.size(); i++) {
					Image image = new Image();
					image.setDate(new Date());
					image.setId("ID" + String.valueOf(id++));
					image.setName(allfile.get(i));
					image.setImage(ImageUtil.BitMap2Byte(getDiskBitmap(allfile
							.get(i))));
					long ret = mImageReader.insert(image);
					if (ret == 0){
						Log.i("DB", "ret = " + String.valueOf(ret));
					}
				}
			}
			mHistoryUris = mImageReader.read(deadline);
		}
		return true;
	}
	
	public int Size(){
		return mHistoryUris.size();
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
