package cn.com.swpu.network08.ctrl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import cn.com.swpu.network08.db.*;
import cn.com.swpu.network08.model.*;

public class DataController {
	private List<Image> mHistory = null;
	private int mCurIndex;
	private ImageSqliteService mImageReader = null;
	private Context context = null;
	private Date mCurDate = null;
	
	
	public DataController(){
		mCurIndex = 0;
		mHistory = new ArrayList<Image>();
		mImageReader = new ImageSqliteService(context);
	}
	
	public boolean LoadHistory(Date deadline){
		return true;
	}
	
	public Image GetLast(){
		--mCurIndex;
		return mHistory.get(mCurIndex);
	}
}
