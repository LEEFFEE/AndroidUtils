package cn.huafei.widget;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import cn.huafei.androidutils.DensityUtil;
import cn.huafei.androidutils.R;

/**
 * 向导页布局   第一次启动时的向导页
 * @author lhfei
 * @date 2016-10-13
 */
public class GuideView extends FrameLayout {
	private ViewPager mViewPager;
	private LinearLayout mLLayout;// 所有底部的点
	private ImageView mCurPoint;// 当前指示的点
	private int disPoint;// 每两个点之间的距离
	private Button btnStart;// 开始进入主页面按钮
	private ArrayList<Bitmap> images;// 页信息容器
	private int normalPointsBGId;// 正常圆点状态的背景
	private int curPointBGId;// 滚动圆点的背景

	public void setNormalPointsBGId(int normalPointsBGId) {
		this.normalPointsBGId = normalPointsBGId;
	}

	public void setCurPointBGId(int curPointBGId) {
		this.curPointBGId = curPointBGId;
	}

	public Button getBtnStart() {
		return btnStart;
	}

	public void setBtnStart(Button btnStart) {
		this.btnStart = btnStart;
	}

	public void setImages(ArrayList<Bitmap> images) {
		this.images = images;
	}

	public GuideView(Context context) {
		this(context, null);
	}

	public GuideView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public GuideView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initLayout(context);
	}

	private void initLayout(Context context) {
		View mGuideView = View.inflate(context, R.layout.view_guide, this);
		mViewPager = (ViewPager) mGuideView.findViewById(R.id.vp_guide_viewpager);
		mLLayout = (LinearLayout) mGuideView.findViewById(R.id.ll_guide_points);
		mCurPoint = (ImageView) mGuideView.findViewById(R.id.iv_guide_curPoint);
		btnStart = (Button) mGuideView.findViewById(R.id.btn_guide_start);
	}

	private void initAdpater() {
		mViewPager.setAdapter(new GuidPagerAdapter());
		mCurPoint.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				// 移除监听,避免重复回调
				mCurPoint.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				// iv_guide_redpoint.getViewTreeObserver().removeOnGlobalLayoutListener(this);//API
				// level 16以上
				disPoint = mLLayout.getChildAt(1).getLeft() - mLLayout.getChildAt(0).getLeft();
			}
		});
		// @SuppressWarnings("deprecation")
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			// mViewPager.addOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				// positionOffset：偏移百分比0-1之间
				int marginLeft = (int) (disPoint * (position + positionOffset));
				// iv_guide_redpoint.setLeft(marginLeft);//只能通过父亲节点去设置，在父元素中的位置

				RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mCurPoint.getLayoutParams();
				params.leftMargin = marginLeft;// 修改左边距

				// 重新设置布局参数
				mCurPoint.setLayoutParams(params);
			}

			@Override
			public void onPageSelected(int position) {
				if (position == mImageViews.size() - 1) {
					btnStart.setVisibility(View.VISIBLE);
				} else {
					btnStart.setVisibility(View.INVISIBLE);
				}
			}

			@Override
			public void onPageScrollStateChanged(int state) {
			}

		});
	}

	private ArrayList<ImageView> mImageViews = new ArrayList<ImageView>();;// 装载所有小圆点的容器

	private class GuidPagerAdapter extends PagerAdapter {
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// container.removeViewAt(position);
			container.removeView((View) object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			ImageView imageView = mImageViews.get(position);
			container.addView(imageView);
			return imageView;
		}

		@Override
		public int getCount() {
			return mImageViews.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

	}

	private void initData() {
		mCurPoint.setBackgroundResource(curPointBGId);
		if (images != null) {
			ImageView point;
			for (int i = 0; i < images.size(); i++) {
				point = new ImageView(getContext());
				// point.setBackgroundResource(imageIds[i]);
				point.setBackgroundDrawable(new BitmapDrawable(getResources(), images.get(i)));
				mImageViews.add(point);
				// 将灰色小圆点放入线性布局中
				point = new ImageView(getContext());
				point.setImageResource(normalPointsBGId);
				// 初始化布局参数, 宽高包裹内容,父控件是谁,就是谁声明的布局参数
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
						LayoutParams.WRAP_CONTENT);
				if (i != 0) {
					// 从第二个点开始设置左边距
					params.leftMargin = (int) DensityUtil.dip2px(getContext(), 10);
				}
				point.setLayoutParams(params);
				mLLayout.addView(point);
			}
		}

	}

	public void bind() {
		initData();
		initAdpater();
	}
}
