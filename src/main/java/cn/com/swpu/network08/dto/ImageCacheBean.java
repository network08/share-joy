package cn.com.swpu.network08.dto;

import java.util.HashMap;

import android.graphics.Bitmap;

/**
 * 
 * @author franklin.li
 *
 */
public class ImageCacheBean {
	private static HashMap<String, Bitmap> imageCache = new HashMap<String, Bitmap>();
	
	public static void put(String key, Bitmap bmp) {
		imageCache.put(key, bmp);
	}
	
	public static Bitmap get(String key) {
		return imageCache.get(key);
	}
}
