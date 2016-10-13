package cn.huafei.androidutils;

import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
/**
 * 密度工具类
 * @author lhfei
 * @date 2016-10-13
 */
public class DensityUtil {

	private static final float DOT_FIVE = 0.5f;

	private DensityUtil(){}
	/**
	 * dip to px	dp装换成像数
	 *
	 * @param context
	 * @param dip
	 * @return
	 */
	public static int dip2px(Context context, float dip) {
		float density = getDensity(context);
		return (int) (dip * density + DOT_FIVE);
	}

	/**
	 * px to dip     像数转换成dp
	 *
	 * @param context
	 * @param px
	 * @return
	 */
	public static int px2dip(Context context, float px) {
		float density = getDensity(context);
		return (int) (px / density + DOT_FIVE);
	}

	private static DisplayMetrics sDisplayMetrics;

	/**
	 * get screen width		获取屏幕宽度
	 *
	 * @param context
	 * @return
	 */
	public static int getDisplayWidth(Context context) {
		initDisplayMetrics(context);
		return sDisplayMetrics.widthPixels;
	}

	/**
	 * get screen height   获取屏幕高度
	 *
	 * @param context
	 * @return
	 */
	public static int getDisplayHeight(Context context) {
		initDisplayMetrics(context);
		return sDisplayMetrics.heightPixels;
	}

	/**
	 * get screen density  获取屏幕密度
	 *
	 * @param context
	 * @return
	 */
	public static float getDensity(Context context) {
		initDisplayMetrics(context);
		return sDisplayMetrics.density;
	}

	/**
	 * get screen density dpi    获取屏幕密度（dpi）
	 *
	 * @param context
	 * @return
	 */
	public static int getDensityDpi(Context context) {
		initDisplayMetrics(context);
		return sDisplayMetrics.densityDpi;
	}

	/**
	 * init display metrics
	 *
	 * @param context
	 */
	private static synchronized void initDisplayMetrics(Context context) {
		sDisplayMetrics = context.getResources().getDisplayMetrics();
	}

	/**
	 * is landscape   是否是横屏
	 *
	 * @param context
	 * @return
	 */
	public static boolean isLandscape(Context context) {
		return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
	}

	/**
	 * is portrait		是否是竖屏
	 *
	 * @param context
	 * @return
	 */
	public static boolean isPortrait(Context context) {
		return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
	}
}