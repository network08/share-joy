package cn.com.swpu.network08;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;


public class ExitActivity extends Activity {
	private LinearLayout layout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exit_dialog);
		//dialog=new MyDialog(this);
		layout=(LinearLayout)findViewById(R.id.exit_layout);
		layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "ÌáÊ¾£ºµã»÷´°¿ÚÍâ²¿¹Ø±Õ´°¿Ú£¡", 
						Toast.LENGTH_SHORT).show();	
			}
		});
	}

	@Override
	public boolean onTouchEvent(MotionEvent event){
		finish();
		return true;
	}

	public void exitbutton1(View v) {  
		this.finish();    	
	}  
	public void exitbutton0(View v) {  
		this.finish();
		MainActivity.instance.finish();//¹Ø±ÕMain Õâ¸öActivity
	}  

}

