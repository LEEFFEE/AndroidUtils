package cn.huafei.widget;

import java.util.ArrayList;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.huafei.androidutils.DensityUtil;
import cn.huafei.androidutils.R;

/**
 * 轮播条控件
 * @author lhfei
 * @date 2016-10-14
 */
public class CarouselBar extends FrameLayout {

	private LinearLayout mPointContainer;
	private TextView mTitle;
	private ViewPager mViewPager;
	private ImageView mCurPoint;
	private int normalPointsBGId;// 正常圆点状态的背景选择器
	private int curPointBGId;// 滚动圆点的背景选择器
	private float leftMargin = 10; // 除了第一个小圆点外 其他圆点的leftMargin
	private int disPoint;// 每两个点之间的距离
	private ArrayList<CarouselInfo> mDatas;
	private long speed = 3000;// 轮询速度 ms
	public final int PATTERN_FORWARD = 0;// 向前滚动
	public final int PATTERN_BACKWARD = 1;// 向后滚动
	public final int PATTERN_INTERSECT = 2;// 交叉方式
	private int scrollPattern = PATTERN_FORWARD;

	/**
	 * 设置轮播条滚动方式
	 * @param scrollPattern   PATTERN_FORWARD,PATTERN_FORWARD,PATTERN_INTERSECT
	 */
	public void setScrollPattern(int scrollPattern) {
		if (scrollPattern < 0 || scrollPattern > 2) {
			this.scrollPattern = PATTERN_FORWARD;
		}
		this.scrollPattern = scrollPattern;
	}

	public void setSpeed(long speed) {
		this.speed = speed;
	}

	public void setLeftMargin(float leftMargin) {
		this.leftMargin = leftMargin;
	}

	public void setNormalPointsBGId(int normalPointsBGId) {
		this.normalPointsBGId = normalPointsBGId;
	}

	public void setCurPointBGId(int curPointBGId) {
		this.curPointBGId = curPointBGId;
	}

	public void setDisPoint(int disPoint) {
		this.disPoint = disPoint;
	}

	public CarouselBar(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		ininLayout();
	}

	public CarouselBar(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CarouselBar(Context context) {
		this(context, null);
	}

	private void ininLayout() {
		View view = View.inflate(getContext(), R.layout.view_carousel, this);
		mViewPager = (ViewPager) view.findViewById(R.id.vp_carousel_viewpager);
		mTitle = (TextView) view.findViewById(R.id.tv_carousel_title);
		mPointContainer = (LinearLayout) view.findViewById(R.id.ll_carousel_point_container);
		mCurPoint = (ImageView) view.findViewById(R.id.iv_carousel_curPoint);
	}

	public void setData(ArrayList<CarouselInfo> data) {
		this.mDatas = data;
		ImageView pointView;
		LinearLayout.LayoutParams params;
		for (int i = 0; i < data.size(); i++) {
			// 初始化小小圆点
			pointView = new ImageView(getContext());
			pointView.setBackgroundResource(normalPointsBGId);
			// 初始化布局参数, 宽高包裹内容,父控件是谁,就是谁声明的布局参数
			params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			if (i != 0) {
				// 从第二个点开始设置左边距
				params.leftMargin = (int) DensityUtil.dip2px(getContext(), leftMargin);
			}
			pointView.setLayoutParams(params);
			mPointContainer.addView(pointView);
		}
	}

	private Handler mhandler;

	@SuppressWarnings("deprecation")
	private void initAdpater() {
		mViewPager.setAdapter(new CarouselPagerAdapter());
		mCurPoint.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				// 移除监听,避免重复回调
				mCurPoint.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				// iv_guide_redpoint.getViewTreeObserver().removeOnGlobalLayoutListener(this);//API
				// level 16以上
				if (mPointContainer.getChildCount() > 1) {
					disPoint = mPointContainer.getChildAt(1).getLeft() - mPointContainer.getChildAt(0).getLeft();
				}
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
				position = position % mDatas.size();
				mTitle.setText(mDatas.get(position).title);
			}

			@Override
			public void onPageScrollStateChanged(int state) {
			}

		});
		if (mhandler == null) {

			mhandler = new Handler() {
				int intersect = PATTERN_FORWARD;

				@Override
				public void handleMessage(Message msg) {
					int currentItem = mViewPager.getCurrentItem();
					// 0. 正向轮播
					if (scrollPattern == PATTERN_FORWARD) {
						currentItem += 1;
						if (currentItem >= mDatas.size()) {
							currentItem = 0;// 如果已经到了最后一个页面,跳到第一页
						}
					}
					// 1. 逆向轮播
					if (scrollPattern == PATTERN_BACKWARD) {
						currentItem--;
						if (currentItem < 0) {
							currentItem = mDatas.size() - 1;
						}
					}
					// 2.交叉轮播
					if (scrollPattern == PATTERN_INTERSECT) {
						if (currentItem == 0) {
							intersect = PATTERN_FORWARD;
							currentItem++;
						} else if (currentItem == mDatas.size() - 1) {
							currentItem--;
							intersect = PATTERN_BACKWARD;
						} else {
							if (intersect == PATTERN_FORWARD) {
								currentItem++;
							} else {
								currentItem--;
							}
						}
					}

					mViewPager.setCurrentItem(currentItem);
					mhandler.sendEmptyMessageDelayed(0, speed);
				}

			};
			mhandler.sendEmptyMessageDelayed(0, speed);
		}
		mViewPager.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					mhandler.removeCallbacksAndMessages(null);// 删除handler的所有消息
					break;
				case MotionEvent.ACTION_CANCEL:
					// 取消事件,
					// 当按下viewpager后,直接滑动listview,导致抬起事件无法响应,但会走此事件
					mhandler.sendEmptyMessageDelayed(0, speed);
					break;
				case MotionEvent.ACTION_UP:
					mhandler.sendEmptyMessageDelayed(0, speed);
					break;

				default:
					break;
				}
				return false;
			}
		});
	}

	/**
	 * ViewPager适配器
	 * @author lhfei
	 * @date 2016-10-14
	 */
	private class CarouselPagerAdapter extends PagerAdapter {
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// container.removeViewAt(position);
			container.removeView((View) object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			position = position % mDatas.size();
			ImageView imageView = new ImageView(getContext());
			// imageView.setImageResource(mDatas.get(position).imageResId);
			imageView.setBackgroundResource(mDatas.get(position).imageResId);
			container.addView(imageView);
			return imageView;
		}

		@Override
		public int getCount() {
			if (mDatas == null) {
				return 0;
			}
			return mDatas.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

	}

	/**
	 * 将数据绑定并运行
	 */
	public void run() {
		mCurPoint.setBackgroundResource(curPointBGId);
		initAdpater();
	}

	public static class CarouselInfo {
		String title;
		int imageResId;

		public CarouselInfo(String title, int imageResId) {
			super();
			this.title = title;
			this.imageResId = imageResId;
		}
	}
}
