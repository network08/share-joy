package cn.com.swpu.network08;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
/**
 * 
 * @author xkk
 *
 */
public class WelcomeActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.welcome);
        new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(3000);
					Intent intent = new Intent(WelcomeActivity.this, MainNavigatePage.class);
			        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					getApplicationContext().startActivity(intent);
					finish();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
		}).start();
    }
}