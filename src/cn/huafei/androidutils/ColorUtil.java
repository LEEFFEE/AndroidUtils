package cn.huafei.androidutils;

import java.util.Random;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PixelFormat;

public class ColorUtil {
	/**
	 * 根据比例获取开始颜色和结束颜色中的某一个值
	 * @param fraction  比例
	 * @param startValue  开始颜色
	 * @param endValue 结束颜色
	 * @return 颜色值
	 */
	public static Object evaluateColor(float fraction, Object startValue, Object endValue) {
		int startInt = (Integer) startValue;
		int startA = (startInt >> 24) & 0xff;
		int startR = (startInt >> 16) & 0xff;
		int startG = (startInt >> 8) & 0xff;
		int startB = startInt & 0xff;

		int endInt = (Integer) endValue;
		int endA = (endInt >> 24) & 0xff;
		int endR = (endInt >> 16) & 0xff;
		int endG = (endInt >> 8) & 0xff;
		int endB = endInt & 0xff;

		return (int) ((startA + (int) (fraction * (endA - startA))) << 24)
				| (int) ((startR + (int) (fraction * (endR - startR))) << 16)
				| (int) ((startG + (int) (fraction * (endG - startG))) << 8)
				| (int) ((startB + (int) (fraction * (endB - startB))));
	}

	/***
	 * 根据传入的颜色和透明度，返回叠加后的颜色
	 * @param color  颜色
	 * @param alpha 透明度
	 * @return  叠加后的颜色
	 */
	public static int multiplyColorAlpha(int color, int alpha) {
		if (alpha == 255) {
			return color;
		}
		if (alpha == 0) {
			return color & 0x00FFFFFF;
		}
		alpha = alpha + (alpha >> 7); // make it 0..256
		int colorAlpha = color >>> 24;
		int multipliedAlpha = colorAlpha * alpha >> 8;
		return (multipliedAlpha << 24) | (color & 0x00FFFFFF);
	}

	/**
	 * 从给定的颜色中获取透明度	ets the opacity from a color. Inspired by Android ColorDrawable.
	 * @param color 传入的颜色值
	 * @return 透明度 	opacity expressed by one of PixelFormat constants
	 */
	public static int getOpacityFromColor(int color) {
		int colorAlpha = color >>> 24;
		if (colorAlpha == 255) {
			return PixelFormat.OPAQUE;
		} else if (colorAlpha == 0) {
			return PixelFormat.TRANSPARENT;
		} else {
			return PixelFormat.TRANSLUCENT;
		}
	}

	/**
	 * 获取随机颜色
	 * @return  
	 */
	public static int randomColor() {
		Random random = new Random();
		int red = random.nextInt(150) + 50;
		int green = random.nextInt(150) + 50;
		int blue = random.nextInt(150) + 50;
		return Color.rgb(red, green, blue);
	}

	/**设置不同状态时其文字颜色。 */
	public static ColorStateList createColorStateList(int normalColor, int pressedColor) {
		// int[] colors = new int[] { pressed, focused, normal, focused, unable,
		// normal };
		int[] colors = new int[] { pressedColor, pressedColor, normalColor, pressedColor, normalColor, normalColor };
		int[][] states = new int[6][];
		states[0] = new int[] { android.R.attr.state_pressed, android.R.attr.state_enabled };
		states[1] = new int[] { android.R.attr.state_enabled, android.R.attr.state_focused };
		states[2] = new int[] { android.R.attr.state_enabled };
		states[3] = new int[] { android.R.attr.state_focused };
		states[4] = new int[] { android.R.attr.state_window_focused };
		states[5] = new int[] {};
		ColorStateList colorList = new ColorStateList(states, colors);
		return colorList;
	}
}
