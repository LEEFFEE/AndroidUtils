package cn.huafei.androidutils;

import android.R.bool;
import android.bluetooth.BluetoothAdapter;
import android.text.StaticLayout;

/**
 * 蓝牙工具类
 * @author lhfei
 * @date 2016-8-29
 */
public class BluetoothUtil {
	private static BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

	/**打开蓝牙*/
	public static boolean openBluetooth() {
		if (!bluetoothAdapter.isEnabled()) {
			// 打开蓝牙
			return bluetoothAdapter.enable();
		}
		return false;
	}

	/**关闭蓝牙*/
	public static boolean closeBluetooth() {
		// 关闭蓝牙
		if (bluetoothAdapter.isEnabled()) {
			return bluetoothAdapter.disable();
		}
		return false;
	}

	/**开始扫描*/
	public static boolean startDiscovery() {
		if (bluetoothAdapter.isEnabled()) {
			bluetoothAdapter.startDiscovery();
		}
		return false;
	}

	/**停止扫描*/
	public static boolean cancelDiscovery() {
		if (bluetoothAdapter.isEnabled()) {
			bluetoothAdapter.cancelDiscovery();
		}
		return false;
	}
}
