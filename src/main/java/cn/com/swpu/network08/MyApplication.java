package cn.com.swpu.network08;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.MKEvent;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

/**
 * @author xkk
 *
 */
public class MyApplication extends Application{
	private static MyApplication mInstance = null;
	private static final String bKey = "ZrZ8bKy7y2l9sRkCSdDP6K5X";
	
	//map init start
	private BMapManager bMapManager = null;
	private boolean bKeyRight = true;
	
	
	@Override
    public void onCreate() {
	    super.onCreate();
		mInstance = this;
		initMapManager(this);
	}
	
	public static MyApplication getInstance() {
		return mInstance;
	}
	
	
	////map init start
	public void initMapManager(Context context) {
        if (bMapManager == null) {
            bMapManager = new BMapManager(context);
        }

        if (!bMapManager.init(bKey,new MyGeneralListener())) {
            Toast.makeText(MyApplication.getInstance().getApplicationContext(), 
                    "BMapManager  初始化错误!", Toast.LENGTH_LONG).show();
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
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "您的网络出错啦！",
                    Toast.LENGTH_LONG).show();
            }
            else if (iError == MKEvent.ERROR_NETWORK_DATA) {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "输入正确的检索条件！",
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
                        "请在 DemoApplication.java文件输入正确的授权Key,并检查您的网络连接是否正常！error: "+iError, Toast.LENGTH_LONG).show();
                MyApplication.getInstance().bKeyRight = false;
            }
            else{
            	MyApplication.getInstance().bKeyRight = true;
            	Toast.makeText(MyApplication.getInstance().getApplicationContext(), 
                        "key认证成功", Toast.LENGTH_LONG).show();
            }
        }
    }
}
