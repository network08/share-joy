package cn.com.swpu.network08.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import cn.com.swpu.network08.util.ImageUtil;
import android.graphics.Bitmap;

/**
 * @author xkk
 *
 */
public class Image {
	private String 			id;
	private String 			name;
	private byte[] 			image;
	private java.util.Date	date;
	private static final String FORMAT_DATE = "yyyy-mm-dd HH:mm:ss";

	public Image(){
	}
	public Image(String name, byte[] image, java.util.Date date) {
		super();
		this.name = name;
		this.image = image;
		this.date  = date;
	}
	public java.util.Date getDate() {
		return date;
	}
	public void setDate(java.util.Date date) {
		this.date = date;
	}
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public void setDate(String strDate){
		SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_DATE); 
		try {
			this.date = formatter.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public String getDateString(){
		SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_DATE); 
		String strDate = formatter.format(date);
		return strDate;
	}
	
	public Bitmap GetImage() {
		return ImageUtil.byte2Bitmap(this.image);
	}

}