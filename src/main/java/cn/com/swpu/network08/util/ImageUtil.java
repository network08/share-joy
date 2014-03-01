package cn.com.swpu.network08.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * @author xkk
 *
 */
public class ImageUtil {
	public static byte[] BitMap2Byte(Bitmap bitmap){
		byte[] body = null;
		if(bitmap != null){
			int size = bitmap.getWidth() * bitmap.getHeight() * 4; 
			ByteArrayOutputStream baos = new ByteArrayOutputStream(size);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
			body = baos.toByteArray();
			try {
				baos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return body;
	}
	
	public static Bitmap byte2Bitmap(byte[] bytes){
		Bitmap bitmap = null;
		if(bytes != null){
			bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length); 
		}
		return bitmap;
	}
}
