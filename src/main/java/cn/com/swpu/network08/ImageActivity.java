package cn.com.swpu.network08;

import java.io.FileNotFoundException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Toast;
import cn.com.swpu.network08.fragment.MeFragment;

/**
 * @author xkk
 *
 */
public class ImageActivity extends Activity implements android.view.View.OnClickListener{
	private static final int SELECT_PICTURE = 1;
	private static final int SELECT_CAMER = 2;
	private static Bitmap bitmap = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.image_navigate_layout);
		CharSequence[] items = {"相册", "相机"};    
		new AlertDialog.Builder(this)  
		.setTitle("选择图片来源")  
		.setItems(items, new OnClickListener() {  
			public void onClick(DialogInterface dialog, int which) {  
				if( which == 1 ){  
					Intent intent = new Intent(Intent.ACTION_GET_CONTENT);  
					intent.addCategory(Intent.CATEGORY_OPENABLE);  
					intent.setType("image/*");  
					startActivityForResult(Intent.createChooser(intent, "选择图片"), SELECT_PICTURE);   
				}else{  
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);    
					startActivityForResult(intent, SELECT_CAMER);    
				}  
			}  
		})  
		.create().show(); 
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);  
		if(resultCode == RESULT_OK){  
			Uri uri = data.getData();   
			ContentResolver cr = this.getContentResolver();   
			try {  
				if(bitmap != null)//如果不释放的话，不断取图片，将会内存不够  
					bitmap.recycle();  
				bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));  
			} catch (FileNotFoundException e) {  
				e.printStackTrace();  
			}  
			Bundle extras = data.getExtras(); 
			Bitmap b = (Bitmap) extras.get("data"); 
			Intent intent = new Intent();
			intent.setClass(this, MeFragment.class); 
			intent.putExtra("image",b); 
			this.startActivity(intent);  
		}else{  
			Toast.makeText(ImageActivity.this, "请重新选择图片", Toast.LENGTH_SHORT).show();  
		}  
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}
