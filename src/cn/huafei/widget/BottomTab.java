package cn.huafei.widget;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import cn.huafei.androidutils.DensityUtil;

/**
 * 底部页签
 * @author lhfei
 * @date 2016-10-16
 */
public class BottomTab extends RadioGroup implements OnClickListener {

	int rbDrawPadding = 3;// dp

	int rbPadding = 5;

	/**
	 * 设置RadioButton  的DrawPadding    默认单位dp
	 * @param rbDrawPadding
	 */
	public void setRbDrawPadding(int rbDrawPadding) {
		this.rbDrawPadding = rbDrawPadding;
	}

	/**
	 * 设置RadioButton  的Padding	默认单位dp
	 * @param rbPadding
	 */
	public void setRbPadding(int rbPadding) {
		this.rbPadding = rbPadding;
	}

	public BottomTab(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public BottomTab(Context context) {
		this(context, null);
	}

	/**
	 * 给底部页签设置数据     （其他信息应在此方法之前设置）
	 * @param tabBeans 页签数据集合
	 */
	public void setData(ArrayList<BottomTabBean> tabBeans) {
		RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT,
				RadioGroup.LayoutParams.WRAP_CONTENT, 1);
		int padding = DensityUtil.dip2px(getContext(), rbPadding);
		RadioButton radioButton;
		for (BottomTabBean tabBean : tabBeans) {
			radioButton = new RadioButton(getContext());
			radioButton.setLayoutParams(params);
			radioButton.setText(tabBean.title);
			radioButton.setChecked(tabBean.checked);
			radioButton.setBackgroundColor(Color.TRANSPARENT);
			radioButton.setButtonDrawable(null);
			radioButton.setCompoundDrawablePadding(DensityUtil.dip2px(getContext(), rbDrawPadding));
			radioButton.setPadding(padding, padding, padding, padding);
			radioButton.setGravity(Gravity.CENTER);
			radioButton.setSingleLine(true);
			radioButton.setTextColor(getResources().getColorStateList(tabBean.textColorId));
			// 设置四个方向背景图片支援
			radioButton.setCompoundDrawablesWithIntrinsicBounds(0, tabBean.drawableTopId, 0, 0);
			this.addView(radioButton);

			radioButton.setOnClickListener(this);
		}
	}

	public CheckedClickListener checkedClickListener;

	/**
	 * 设置选中以后的监听   ，每点击一次就执行一次
	 * @param onItemClickListener
	 */
	public void setCheckedListener(CheckedClickListener checkedClickListener) {
		this.checkedClickListener = checkedClickListener;
	}

	public static interface CheckedClickListener {
		void onItemClick(RadioButton rButton);
	}

	@Override
	public void onClick(View v) {
		for (int i = 0; i < this.getChildCount(); i++) {
			if (this.getChildAt(i).getId() != v.getId()) {
				RadioButton rButton = (RadioButton) this.getChildAt(i);
				rButton.setChecked(false);
			} else {
				RadioButton rButton = (RadioButton) this.getChildAt(i);
				rButton.setChecked(true);
				if (checkedClickListener != null) {
					checkedClickListener.onItemClick(rButton);
				}
			}

		}
	}

	/**
	 * 会调用两次     @Deprecated弃用   用setOnItemClickListener 代替
	 */
	@Deprecated
	@Override
	public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
		// super.setOnCheckedChangeListener(listener);

	}

	/**
	 * 页签基本信息
	 * @author lhfei
	 * @date 2016-10-16
	 */
	public static class BottomTabBean {
		String title;// tab标题
		boolean checked;// 是否选中
		int drawableTopId;// 资源选择器
		int textColorId;// 文字颜色选择器

		/**
		 * 设置BottomTab的基本信息
		 * @param title	tab标题
		 * @param checked	是否选中
		 * @param drawableTopId	顶部图片资源选择器
		 * @param textColorId	文字颜色选择器
		 */
		public BottomTabBean(String title, boolean checked, int drawableTopId, int textColorId) {
			super();
			this.title = title;
			this.checked = checked;
			this.drawableTopId = drawableTopId;
			this.textColorId = textColorId;
		}
	}
}
