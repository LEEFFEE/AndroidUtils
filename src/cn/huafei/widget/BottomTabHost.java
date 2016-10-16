package cn.huafei.widget;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.huafei.androidutils.R;

public class BottomTabHost extends FragmentTabHost {

	public BottomTabHost(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public BottomTabHost(Context context) {
		this(context, null);
	}

	/**
	 * 设置数据
	 * @param tabHostBeans	数据集合
	 * @param fm  FragmentManager管理器
	 * @param resId   FrameLayout布局的资源Id
	 */
	public void setData(ArrayList<TabHostBean> tabHostBeans, FragmentManager fm, int resId) {
		// 2.使tabHost和FrameLayout关联
		this.setup(getContext(), fm, resId);
		TabSpec tabSpec;
		Bundle bundle;
		View view;
		ImageView imageView;
		TextView textView;
		for (TabHostBean tabHostBean : tabHostBeans) {
			// 3.添加tab和其对应的fragment
			tabSpec = this.newTabSpec(tabHostBean.title);
//			view = View.inflate(getContext(), R.layout.tab_indicator, null);
//			imageView = (ImageView) view.findViewById(R.id.icon_tab);
//			textView = (TextView) view.findViewById(R.id.txt_indicator);
			view = (LinearLayout) View.inflate(getContext(), R.layout.tab, null);
			view.setClickable(true);
			imageView = (ImageView) view.findViewById(R.id.iv_tab_icon);
			textView = (TextView) view.findViewById(R.id.txt_tab_title);
			imageView.setBackgroundResource(tabHostBean.resId);
			textView.setText(tabHostBean.title);
			textView.setTextColor(getResources().getColorStateList(tabHostBean.textColorId));
			tabSpec.setIndicator(view);
			bundle = new Bundle();
			bundle.putString("param", tabHostBean.title);
			this.addTab(tabSpec, tabHostBean.fragmentClass, bundle);
		}
		this.getTabWidget().setDividerDrawable(null);// 取出tab之间的分割线
	}

	/**
	 * 页签基本信息
	 * @author lhfei
	 * @date 2016-10-16
	 */
	public static class TabHostBean {
		String title;// tab标题
		boolean checked;// 是否选中
		int resId;// 资源选择器
		int textColorId;// 文字颜色选择器
		Class<?> fragmentClass;// fragment的字节码

		// Bundle fragmentBundle;// fragment的Bundle参数

		/**
		 * 设置BottomTab的基本信息
		 * @param title	tab标题
		 * @param checked	是否选中
		 * @param drawableTopId	顶部图片资源选择器
		 * @param textColorId	文字颜色选择器
		 * @param fragmentClass	fragment的字节码
		 * @param textColorId	文字颜色选择器
		 */
		public TabHostBean(String title, boolean checked, int resId, int textColorId, Class<?> fragmentClass) {
			super();
			this.title = title;
			this.checked = checked;
			this.resId = resId;
			this.textColorId = textColorId;
			this.fragmentClass = fragmentClass;
			// this.fragmentBundle = fragmentBundle;
		}
	}

}
