package cn.huafei.androidutils;

import android.text.TextUtils;

public class DateUtil {
	/**
	 * 将秒时间转换成日时分格式
	 * 
	 * @param lasttime
	 * @return
	 */
	public static String getLasttime(String lasttime) {
		StringBuffer result = new StringBuffer();
		if (TextUtils.isDigitsOnly(lasttime)) {
			int time = Integer.parseInt(lasttime);
			int day = time / (24 * 60 * 60);
			result.append(day).append("天");
			if (day > 0) {
				time = time - day * 24 * 60 * 60;
			}
			int hour = time / 3600;
			result.append(hour).append("时");
			if (hour > 0) {
				time = time - hour * 60 * 60;
			}
			int minute = time / 60;
			result.append(minute).append("分");
		}
		return result.toString();
	}
}
