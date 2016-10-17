package cn.huafei.androidutils;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

public class DrawableUtil {

	/**
	 *创建一个shape对象
	 *@param rgb            颜色值
	 *@param radius           圆半径
	 *@param shapeMode		形状 GradientDrawable.RING  GradientDrawable.OVAL GradientDrawable.RECTANGLE等
	 *@return
	 */
	public static GradientDrawable getGradientDrawable(int argb, float radius, int shapeMode) {
		// xml中定义的shape标签 对应此类
		GradientDrawable shape = new GradientDrawable();
		shape.setShape(shapeMode);
		shape.setCornerRadius(radius);
		shape.setColor(argb);
		return shape;
	}
	/**
	 *创建一个圆形对象
	 *@param rgb            颜色值
	 *@param radius           圆半径
	 *@return
	 */
	public static GradientDrawable getGradientDrawable(int argb, int radius) {
		// xml中定义的shape标签 对应此类
		GradientDrawable shape = new GradientDrawable();
		shape.setShape(GradientDrawable.OVAL);
		shape.setSize(radius, radius);
		shape.setColor(argb);
		return shape;
	}

	/**
	 * 	根据Drawable对象获取状态选择器
	 * @param normalDrawable
	 * @param pressDrawable
	 * @return
	 */
	public static StateListDrawable getSelector(Drawable normalDrawable, Drawable pressDrawable) {
		StateListDrawable stateListDrawable = new StateListDrawable();
		stateListDrawable.addState(new int[] { android.R.attr.state_pressed }, pressDrawable);
		stateListDrawable.addState(new int[] {}, normalDrawable);
		return stateListDrawable;
	}

	/**
	 * 根据颜色值获取状态选择器
	 *@param normalColor*            默认颜色
	 *@param pressColor*            按下颜色
	 *@param radius*            圆角半径
	 *@param shapeMode		形状 GradientDrawable.RING  GradientDrawable.OVAL GradientDrawable.RECTANGLE等
	 *@return
	 */
	public static StateListDrawable getSelector(int normalColor, int pressColor, float radius, int shapeMode) {
		GradientDrawable normalDrawable = getGradientDrawable(normalColor, radius, shapeMode);
		GradientDrawable pressDrawable = getGradientDrawable(pressColor, radius, shapeMode);
		return getSelector(normalDrawable, pressDrawable);
	}
}
