package cn.huafei.androidutils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * 吐司类
 * @author lhfei
 * @date 2016-8-28
 */
public class ToastUtil {
	private static Toast toast;

	/**
	 * 能够连续弹的吐司，不会等上个吐司消失
	 * @param context
	 * @param msg 要显示的内容
	 */
	public static void showToast(Context context, String msg) {
		if (toast == null) {
			toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
		}
		toast.setText(msg);
		toast.show();
	}
	/**
	 * 能够连续弹的吐司，不会等上个吐司消失
	 * @param context
	 * @param msg 要显示的内容
	 * @param gravity Toast显示的位置  Gravity.CENTER
	 */
	public static void showToast(Context context, String msg,int gravity) {
		if (toast == null) {
			toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
		}
		
		toast.setGravity(gravity, 0, 0);
		toast.setText(msg);
		toast.show();
	}
}
