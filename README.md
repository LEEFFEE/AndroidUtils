# AndroidUtils

## 一些常用工具类及自定义控件

### 自定义控件有
#### 1.引导页   GuideView；
![enter description here][1]

		GuideView mGuideView = (GuideView) findViewById(R.id.guideView);

		ArrayList<Bitmap> images = new ArrayList<Bitmap>();
		images.add(BitmapFactory.decodeResource(getResources(), R.drawable.guide_1));
		images.add(BitmapFactory.decodeResource(getResources(), R.drawable.guide_2));
		images.add(BitmapFactory.decodeResource(getResources(), R.drawable.guide_3));
		mGuideView.setImages(images);// 设置ViewPager图片列表

		mGuideView.setNormalPointsBGId(R.drawable.shape_point_gray);// 设置默认形状
		mGuideView.setCurPointBGId(R.drawable.shape_point_red);// 设置滚动圆点背景
		// 获取button
		Button btnStart = mGuideView.getBtnStart();
		btnStart.setText("进入主页");
		btnStart.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_guide_btn_bg));
		btnStart.setPadding(10, 10, 10, 10);
		// btnStart.setTextColor(createColorStateList(Color.WHITE, Color.BLACK,
		// Color.BLUE, Color.GRAY));
		btnStart.setTextColor(getResources().getColorStateList(R.color.selector_guide_txt_color));
		btnStart.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "进入主页", 0).show();
			}
		});
		mGuideView.bind();
#### 2.底部页签  TabBottomPager；
![enter description here][2]

          @Override
          protected void onCreate(Bundle savedInstanceState) {
              super.onCreate(savedInstanceState);
              setContentView(R.layout.activity_tabbottompager);
              TabBottomPager mTabBottomPager = (TabBottomPager) findViewById(R.id.tabBottomPager);
              ArrayList<TabInfo> tabInfos = new ArrayList<TabInfo>();
              tabInfos.add(new TabInfo("主页", R.drawable.selector_icon_home, R.color.selector_tab_text, null));
              tabInfos.add(new TabInfo("热卖", R.drawable.selector_icon_hot, R.color.selector_tab_text, null));
              tabInfos.add(new TabInfo("分类", R.drawable.selector_icon_category, R.color.selector_tab_text, null));
              //tabInfos.add(new TabInfo("购物车", R.drawable.selector_icon_cart, R.color.selector_tab_text, null));
              tabInfos.add(new TabInfo("我的", R.drawable.selector_icon_mine, R.color.selector_tab_text, null));
              mTabBottomPager.setData(tabInfos);

              View view = View.inflate(getApplicationContext(), R.layout.test, null);
              mTabBottomPager.setCurrentTabView(view);
          }   

#### 3. 轮播条  CarouselBar；
![enter description here][3]
                
                // 图片资源id数组
                int[] imageResIds = new int[] { R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e };
                // 文本描述
                String[] contentDescs = new String[] { "巩俐不低俗，我就不能低俗", "扑树又回来啦！再唱经典老歌引万人大合唱", "揭秘北京电影如何升级", "乐视网TV版大派送",
                        "热血屌丝的反杀" };

                CarouselBar mCarouselBar = (CarouselBar) findViewById(R.id.mCarouselBar);
                mCarouselBar.setNormalPointsBGId(R.drawable.shape_point_gray);
                mCarouselBar.setCurPointBGId(R.drawable.shape_point_red);
                mCarouselBar.setLeftMargin(5.0f);// 自动适应
                mCarouselBar.setSpeed(1000);// 设置轮播速度 毫秒
                mCarouselBar.setScrollPattern(mCarouselBar.PATTERN_INTERSECT);// 交叉滚动
                ArrayList<CarouselInfo> data = new ArrayList<CarouselInfo>();
                for (int i = 0; i < contentDescs.length; i++) {
                    data.add(new CarouselInfo(contentDescs[i], imageResIds[i]));
                }
                mCarouselBar.setData(data);
                mCarouselBar.run();

#### 4. 底部页签 TabIndicator；
![enter description here][4]

      ViewPager mViewPager = (ViewPager) findViewById(R.id.mViewPager);
          // mViewPager.setAdapter(new MyPagerAdapter());
          mViewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager()));

          mTabIndicator = (TabIndicator) findViewById(R.id.mTabIndicator);
          mTabIndicator.setTabBackgroundResId(R.drawable.bg_tab_text);// 设置tab背景选择器
          mTabIndicator.setTabTextColorResId(R.color.tab_text_color);// 设置文字颜色选择器
          mTabIndicator.setViewPager(mViewPager);// 设置ViewPager


  [1]: ./images/Screenshot_1476463793.png "Screenshot_1476463793.png"
  [2]: ./images/Screenshot_1476449972.png "Screenshot_1476449972.png"
  [3]: ./images/Screenshot_1476463203.png "Screenshot_1476463203.png"
  [4]: ./images/20161015185133.png "20161015185133.png"
  
#### 5. 底部页签 BottomTabHost；

		mBottomTabHost = (BottomTabHost) findViewById(R.id.bottomTabHost);
		
		ArrayList<TabHostBean> tabBeans = new ArrayList<TabHostBean>();
		int txtColorId = R.color.selector_content_txt_color;
		tabBeans.add(new TabHostBean("首页", true, R.drawable.selector_content_rb_bg_home, txtColorId,
				BlankFragment.class));
		tabBeans.add(new TabHostBean("新闻", false, R.drawable.selector_content_rb_bg_news, txtColorId,
				BlankFragment.class));
		tabBeans.add(new TabHostBean("服务", false, R.drawable.selector_content_rb_bg_service, txtColorId,
				BlankFragment.class));
		tabBeans.add(new TabHostBean("政务", false, R.drawable.selector_content_rb_bg_govaffairs, txtColorId,
				BlankFragment.class));
		tabBeans.add(new TabHostBean("设置", false, R.drawable.selector_content_rb_bg_setting, txtColorId,
				BlankFragment.class));

		mBottomTabHost.setData(tabBeans,getSupportFragmentManager(),R.id.fl_tabcontent);
		
#### 6. 底部页签 BottomTab；

		mBottomTab = (BottomTab) findViewById(R.id.rg_tabs);
		ArrayList<BottomTab.BottomTabBean> tabBeans = new ArrayList<BottomTab.BottomTabBean>();
		int txtColorId = R.color.selector_content_txt_color;
		tabBeans.add(new BottomTab.BottomTabBean("首页", true, R.drawable.selector_content_rb_bg_home, txtColorId));
		tabBeans.add(new BottomTab.BottomTabBean("新闻", false, R.drawable.selector_content_rb_bg_news, txtColorId));
		tabBeans.add(new BottomTab.BottomTabBean("服务", false, R.drawable.selector_content_rb_bg_service, txtColorId));
		tabBeans.add(new BottomTab.BottomTabBean("政务", false, R.drawable.selector_content_rb_bg_govaffairs, txtColorId));
		tabBeans.add(new BottomTab.BottomTabBean("设置", false, R.drawable.selector_content_rb_bg_setting, txtColorId));

		mBottomTab.setData(tabBeans);
		mBottomTab.setCheckedListener(new CheckedClickListener() {
			@Override
			public void onItemClick(RadioButton rButton) {
				System.out.println(rButton.getText());
			}

		});
