package cn.huafei.androidutils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class ApkUtil {

	private static final String LOG_TAG = "ApkUtil";

	/**
	 * 加载apk图标，应该放在子线程中运行，以免ANR
	 * 
	 * @param context
	 *            上下文
	 * @param apkPath
	 *            apk文件所在路径
	 * @return apk图标：采用了新的办法获取APK图标，之前的失败是因为android中存在的一个BUG,通过
	 *         appInfo.publicSourceDir = apkPath;来修正这个问题，详情参见:
	 *         http://code.google.com/p/android/issues/detail?id=9151
	 */
	public static Drawable getApkIcon(Context context, String apkPath) {
		PackageManager pm = context.getPackageManager();
		PackageInfo info = pm.getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES);
		if (info != null) {
			ApplicationInfo appInfo = info.applicationInfo;
			appInfo.sourceDir = apkPath;
			appInfo.publicSourceDir = apkPath;
			try {
				return appInfo.loadIcon(pm);
			} catch (OutOfMemoryError e) {
				Log.e(LOG_TAG, e.toString());
			}
		}
		return null;
	}

}