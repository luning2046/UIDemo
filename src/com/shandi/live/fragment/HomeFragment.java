package com.shandi.live.fragment;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.sdlive.main.splash.R;
import com.sdlive.main.splash.R.layout;
import com.shandi.live.base.BaseFragment;
import com.shandi.live.base.BasePager;
import com.shandi.live.main.MainActivity;
import com.shandi.live.pager.MyShanDiPager;
import com.shandi.live.pager.ShanDiLivePager;
import com.shandi.live.pager.ShoppingCartPager;
import com.shandi.live.util.LogUtil;
import com.shandi.live.view.LazyViewPager.OnPageChangeListener;
import com.shandi.live.view.MyViewPager;

public class HomeFragment extends BaseFragment {
	public static final String tag = "HomeFragment";
	private MyViewPager layout_content;
	private RadioGroup main_radio;
	
	private List<BasePager> pagerList = new ArrayList<BasePager>();
	
	@Override
	public View initView() {
		view = View.inflate(context, R.layout.fragment_home, null);
		layout_content = (MyViewPager) view.findViewById(R.id.layout_content);
		main_radio = (RadioGroup) view.findViewById(R.id.main_radio);
		return view;
	}

	public void goPager(){
		if(this.getArguments() != null){
			LogUtil.i(tag, this.getArguments().getString("goToCart"));
			if(this.getArguments().getString("goToCart").equals("shoppingCart")){
				if(layout_content != null){
					main_radio.check(R.id.rb_shopping_cart);
					layout_content.setCurrentItem(1);
				}
			}
		}
	}
	
	@Override
	public void initData() {
		//TODO首页,购物车,我的闪递 三个页面添加到集合中,用于给viewpager填充
		pagerList.clear();
		pagerList.add(new ShanDiLivePager((MainActivity)getActivity()));
		pagerList.add(new ShoppingCartPager((MainActivity)getActivity()));
		pagerList.add(new MyShanDiPager((MainActivity)getActivity()));
		layout_content.setAdapter(new MyPagerAdapter());
		layout_content.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				//选中某一个界面的时候，需要去调用当前界面对应对象上的initData()方法，获取数据
				pagerList.get(position).initData();
			}
			
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int state) {
				
			}
		});
		
		main_radio.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.rb_home:
					layout_content.setCurrentItem(0);
					break;
				case R.id.rb_shopping_cart:
					layout_content.setCurrentItem(1);
					break;
				case R.id.rb_myshandi:
					layout_content.setCurrentItem(2);
					break;
				}
			}
		});
		main_radio.check(R.id.rb_home);
		goPager();
	}

	class MyPagerAdapter extends PagerAdapter{
		@Override
		public int getCount() {
			return pagerList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0==arg1;
		}
		
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			Log.i(tag, "instantiateItem position = "+position);
			container.addView(pagerList.get(position).getRootView());
			return pagerList.get(position).getRootView();
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			Log.i(tag,  "destroyItem position = "+position);
			container.removeView((View)object);
		}
	}
}
