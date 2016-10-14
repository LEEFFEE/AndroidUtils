package cn.huafei.widget;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.huafei.androidutils.R;
/**
 * 底部页签控件
 * @author lhfei
 * @date 2016-10-14
 */
public class TabBottomPager extends FrameLayout {

	private ViewPager mViewPager;
	// private RadioGroup mRadioGroup;
	private LinearLayout mTabContainer;
	private int pagePosition;// 当前Tab的位置
	private ArrayList<TabInfo> mTabInfos;

	public TabBottomPager(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initLayout(context);
	}

	public TabBottomPager(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public TabBottomPager(Context context) {
		this(context, null);
	}

	private void initLayout(Context context) {
		View mView = View.inflate(context, R.layout.view_tab_bottom_pager, this);
		mViewPager = (ViewPager) mView.findViewById(R.id.vp_viewPager);
		mTabContainer = (LinearLayout) mView.findViewById(R.id.ll_tabContainer);
		// mRadioGroup = (RadioGroup) mView.findViewById(R.id.rg_radioGroup);
	}

	/**
	 * 页签信息
	 * @author lhfei
	 * @date 2016-10-14
	 */
	public static class TabInfo {
		String title;// 标题
		int imgIconId;// 背景状态选择器
		int txtColorId;// 文字状态选择器
		View vpView;// vPager显示的内容

		/**
		 * 带参构造
		 * @param title  页签标题
		 * @param imgIconId  图标选择器  （需带有selected状态）
		 * @param txtColorId	文字颜色选择器（需带有selected状态）
		 * @param vpView	ViewPager需要显示的布局
		 */
		public TabInfo(String title, int imgIconId, int txtColorId, View vpView) {
			super();
			this.title = title;
			this.imgIconId = imgIconId;
			this.txtColorId = txtColorId;
			this.vpView = vpView;
		}
	}

	/**
	 * 设置页签数据信息
	 * @param tabInfos
	 */
	public void setData(ArrayList<TabInfo> tabInfos) {
		this.mTabInfos = tabInfos;
		// 1.初始化底部Tab
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT, 1);
		for (TabInfo tabInfo : tabInfos) {
			LinearLayout llTab = (LinearLayout) View.inflate(getContext(), R.layout.tab, null);
			llTab.setClickable(true);
			llTab.setLayoutParams(layoutParams);
			ImageView mTabIcon = (ImageView) llTab.findViewById(R.id.iv_tab_icon);
			TextView mTabTitle = (TextView) llTab.findViewById(R.id.txt_tab_title);
			mTabIcon.setBackgroundResource(tabInfo.imgIconId);
			mTabTitle.setText(tabInfo.title);
			mTabTitle.setTextColor(getResources().getColorStateList(tabInfo.txtColorId));
			mTabContainer.addView(llTab);

			llTab.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View tab) {
					for (int j = 0; j < mTabContainer.getChildCount(); j++) {
						LinearLayout llTab = (LinearLayout) mTabContainer.getChildAt(j);
						// ImageView imgView = (ImageView) llTab.getChildAt(0);
						// TextView txtView = (TextView) llTab.getChildAt(1);
						if (tab == llTab) {
							llTab.getChildAt(0).setSelected(true);
							llTab.getChildAt(1).setSelected(true);
							mViewPager.setCurrentItem(j, true);// 平滑滚动
							pagePosition = j;
						} else {
							llTab.getChildAt(0).setSelected(false);
							llTab.getChildAt(1).setSelected(false);
						}
					}
				}
			});
		}
		// 默认选中第一个Tab
		((LinearLayout) mTabContainer.getChildAt(0)).getChildAt(0).setSelected(true);
		((LinearLayout) mTabContainer.getChildAt(0)).getChildAt(1).setSelected(true);

		// 2.初始化ViewPager
		TabPagerAdapter pa = new TabPagerAdapter(tabInfos);
		mViewPager.setAdapter(pa);
		mViewPager.setOnPageChangeListener(new TabPageChangeListener());
	}

	/**
	 * ViewPager滚动监听器
	 * @author lhfei
	 * @date 2016-10-14
	 */
	class TabPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		}

		@Override
		public void onPageSelected(int position) {
			setCurrentTab(position);
		}

		@Override
		public void onPageScrollStateChanged(int state) {
		}

	}

	/**
	 * 根据position 设置当前标签页
	 * @param v
	 */
	private void setCurrentTab(int position) {
		pagePosition = position;
		for (int j = 0; j < mTabContainer.getChildCount(); j++) {
			LinearLayout llTab = (LinearLayout) mTabContainer.getChildAt(j);
			// ImageView imgView = (ImageView) llTab.getChildAt(0);
			// TextView txtView = (TextView) llTab.getChildAt(1);
			if (position == j) {
				llTab.getChildAt(0).setSelected(true);
				llTab.getChildAt(1).setSelected(true);
			} else {
				llTab.getChildAt(0).setSelected(false);
				llTab.getChildAt(1).setSelected(false);
			}
		}
	}

	/**
	 * 设置当前页签布局
	 * @param view
	 */
	public void setCurrentTabView(View view) {
		mTabInfos.get(pagePosition).vpView = view;
	}

	/**
	 * ViewPager适配器
	 * @author lhfei
	 * @date 2016-10-14
	 */
	class TabPagerAdapter extends PagerAdapter {

		private ArrayList<TabInfo> mTabInfos;

		public TabPagerAdapter(ArrayList<TabInfo> tabInfos) {
			this.mTabInfos = tabInfos;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			if (mTabInfos.get(position).vpView == null) {
				TextView tv = new TextView(getContext());
				tv.setText(mTabInfos.get(position).title);
				tv.setTextColor(Color.RED);
				tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
				mTabInfos.get(position).vpView = tv;

			}
			container.addView(mTabInfos.get(position).vpView);
			return mTabInfos.get(position).vpView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public int getCount() {
			if (mTabInfos != null) {
				return mTabInfos.size();
			}
			return 0;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}
	}
}
