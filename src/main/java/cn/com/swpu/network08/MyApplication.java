package cn.com.swpu.network08;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.MKEvent;

/**
 * @author xkk
 *
 */
public class MyApplication extends Application{
	private static MyApplication mInstance = null;
	
	//map element
	private static final String bKey = "ZrZ8bKy7y2l9sRkCSdDP6K5X";
	private BMapManager bMapManager = null;
	private boolean bKeyRight = true;
	
	//other element
	
	
	@Override
    public void onCreate() {
	    super.onCreate();
		mInstance = this;
		//initMapManager(this);
	}
	
	public static MyApplication getInstance() {
		return mInstance;
	}
	
	
	//map init start
	public void initMapManager(Context context) {
        if (bMapManager == null) {
            bMapManager = new BMapManager(context);
        }

        if (!bMapManager.init(bKey,new MyGeneralListener())) {
            Toast.makeText(MyApplication.getInstance().getApplicationContext(), 
                    "map init error!", Toast.LENGTH_LONG).show();
        }
	}
	

	/**
	 * @return the bMapManager
	 */
	public BMapManager getbMapManager() {
		return bMapManager;
	}

	/**
	 * @param bMapManager the bMapManager to set
	 */
	public void setbMapManager(BMapManager bMapManager) {
		this.bMapManager = bMapManager;
	}

	/**
	 * @return the bKeyRight
	 */
	public boolean isbKeyRight() {
		return bKeyRight;
	}

	/**
	 * @param bKeyRight the bKeyRight to set
	 */
	public void setbKeyRight(boolean bKeyRight) {
		this.bKeyRight = bKeyRight;
	}

	/**
	 * @return the bkey
	 */
	public static String getBkey() {
		return bKey;
	}



	// 常用事件监听，用来处理通常的网络错误，授权验证错误等
    static class MyGeneralListener implements MKGeneralListener {
        
        @Override
        public void onGetNetworkState(int iError) {
            if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "network error!",
                    Toast.LENGTH_LONG).show();
            }
            else if (iError == MKEvent.ERROR_NETWORK_DATA) {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "network data error!",
                        Toast.LENGTH_LONG).show();
            }
            // ...
        }

        @Override
        public void onGetPermissionState(int iError) {
        	//非零值表示key验证未通过
            if (iError != 0) {
                //授权Key错误：
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), 
                        "key error: "+iError, Toast.LENGTH_LONG).show();
                MyApplication.getInstance().bKeyRight = false;
            }else{
            	MyApplication.getInstance().bKeyRight = true;
            }
        }
    }
}
