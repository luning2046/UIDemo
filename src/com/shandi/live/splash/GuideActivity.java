package com.shandi.live.splash;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.sdlive.main.splash.R;
import com.shandi.live.main.MainActivity;

public class GuideActivity extends Activity {

	private ViewPager view_pager;
	private Button bt_begin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_guide);
		initView();
	}

	private void initView() {

		view_pager = (ViewPager) findViewById(R.id.vp_guide);
		bt_begin = (Button) findViewById(R.id.bt_begin);
		
		
		List<View> viewList = new ArrayList<View>();
		
		ImageView page_a = new ImageView(this);
		page_a.setBackgroundResource(R.drawable.page_one);
		ImageView page_b = new ImageView(this);
		page_b.setBackgroundResource(R.drawable.page_two);
		ImageView page_c = new ImageView(this);
		page_c.setBackgroundResource(R.drawable.page_three);
		ImageView page_d = new ImageView(this);
		page_d.setBackgroundResource(R.drawable.page_fore);
		
		viewList.add(page_a);
		viewList.add(page_b);
		viewList.add(page_c);
		viewList.add(page_d);
		ViewPagerAdapter mPageAdapter = new ViewPagerAdapter(viewList);
		view_pager.setAdapter(mPageAdapter);
		bt_begin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(GuideActivity.this, MainActivity.class));
				finish();
			}
		});
		view_pager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// 如果是最后一个引导界面的话，就出现按钮
				//如果不是最后一个的话，就不出现
				if(arg0 == 3){
					bt_begin.setVisibility(View.VISIBLE);

				}else{
					bt_begin.setVisibility(View.GONE);
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

	class ViewPagerAdapter extends PagerAdapter{

		private List<View> pages;

		public ViewPagerAdapter(List<View> pages){
			this.pages = pages;
		}

		@Override
		public int getCount() {
			return pages.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			((ViewPager)container).removeView(pages.get(position));
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			((ViewPager)container).addView(pages.get(position));
			return pages.get(position);
		}
	}

}
