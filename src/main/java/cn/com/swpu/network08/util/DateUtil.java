package cn.com.swpu.network08.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 
 * @author franklin.li
 *
 */
public class DateUtil {
	private static final String FORMAT_DATE = "yyyy-mm-dd HH:mm:ss";
	public static long getUTC() {
        Calendar calendar = Calendar.getInstance();
        TimeZone tz = TimeZone.getTimeZone("GMT");
        calendar.setTimeZone(tz);
        return calendar.getTimeInMillis();
    }
    /**
     * @param time 标准utc时间
     * @param ID   "GMT+8"
     * @return
     */
    public static String dateFormat(long time, String ID) {
    	DateFormat format = new SimpleDateFormat(FORMAT_DATE, Locale.getDefault());
        TimeZone zone = TimeZone.getTimeZone(ID);
        format.setTimeZone(zone);
        return format.format(time);
    }
}
