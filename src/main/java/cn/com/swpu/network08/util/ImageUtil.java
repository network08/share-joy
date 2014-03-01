package cn.com.swpu.network08.util;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;

/**
 * @author xkk
 *
 */
public class ImageUtil {
	public static byte[] BitMap2Byte(Bitmap bitmap){
		byte[] body = null;
		if(bitmap != null){
			int size=bitmap.getWidth()*bitmap.getHeight()*4; 
			ByteArrayOutputStream baos=new ByteArrayOutputStream(size);
			body = baos.toByteArray();
		}
		return body;
	}
}
