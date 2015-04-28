package com.shandi.live.view;

import java.util.ArrayList;
import java.util.List;

import com.sdlive.main.splash.R;



import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class BaneRollViewPager extends ViewPager {

	//图片加载url的集合
	private List<String> imgUrlList;

	private MyPagerAdapter mPagerAdapter;
	private int downX;
	private int downY;
	//当前viewpager指向的轮播图片的索引
	private int currentPosition = 0;
	private OnViewClickListener onViewClickListener;
	public interface OnViewClickListener{
		public void viewClick(String url);
	}
	
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			BaneRollViewPager.this.setCurrentItem(currentPosition);
			startRoll();
		}

	};
	private RunnableTask runnableTask;
	class RunnableTask implements Runnable{
		@Override
		public void run() {
			//切换指向图片操作
			currentPosition = (currentPosition+1)%imgUrlList.size();
			//发送消息
			//发送一个空消息
			mHandler.obtainMessage().sendToTarget();
		}
	}
	public BaneRollViewPager(Context context,OnViewClickListener onViewClickListener) {
		super(context);
		this.onViewClickListener = onViewClickListener;
		//这段代码是做展示临时用的
		imgUrlList = new ArrayList<String>();
		imgUrlList.add("1");
		imgUrlList.add("2");
		imgUrlList.add("3");

		runnableTask = new RunnableTask();
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			downX = (int) ev.getX();
			downY = (int)ev.getY();
			//当前viewpager对应的夫控件不能去拦截事件
			getParent().requestDisallowInterceptTouchEvent(true);
			break;
		case MotionEvent.ACTION_MOVE:
			int moveX = (int) ev.getX();
			//滑动内部viewpager指向的item，夫控件不拦截事件
			//滑动整个模块，指向下一个模块,夫控件要去拦截事件

			// moveX-downX>0 有左向右滑动
			// moveX-downX<0 由右向左滑动
			int diff = moveX-downX;

			//由右边向左边滑动，并且在viewpager最后一个页面的时候
			if(diff <0 && getCurrentItem() == getAdapter().getCount()-1){
				getParent().requestDisallowInterceptTouchEvent(true);
				this.setCurrentItem(0);
			}else if(diff <0 && getCurrentItem()>0){
				getParent().requestDisallowInterceptTouchEvent(true);
			}else if(diff >0 && getCurrentItem() == 0){
				getParent().requestDisallowInterceptTouchEvent(true);
				this.setCurrentItem(imgUrlList.size() - 1);
			}else if(diff > 0 && getCurrentItem()<getAdapter().getCount()){
				getParent().requestDisallowInterceptTouchEvent(true);
			}

			break;
		}
		return super.dispatchTouchEvent(ev);
	}

	public void initImgUrl(List<String> imgUrlList) {
		this.imgUrlList = imgUrlList;
	} 

	public void startRoll() {
		if(mPagerAdapter == null){
			mPagerAdapter = new MyPagerAdapter();
			this.setAdapter(mPagerAdapter);
		}else{
			mPagerAdapter.notifyDataSetChanged();
		}
		//2,滚动轮播图片,定时器，handler机制
		mHandler.postDelayed(runnableTask, 3000);
	};

	class MyPagerAdapter extends PagerAdapter{

		@Override
		public int getCount() {
			return imgUrlList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, final int position) {
			View view = View.inflate(getContext(), R.layout.bane_pager_item, null);
			ImageView imag = (ImageView) view.findViewById(R.id.image);
			//			bitmapUtli.(加载图片方法设置到ImageView中)
			view.setOnTouchListener(new OnTouchListener() {
				private int downX;
				private int upX;
				private long downTime;
				private long upTime;
				@Override
				public boolean onTouch(View v, MotionEvent event) {

					switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						downX = (int) event.getX();
						downTime = System.currentTimeMillis();
						//handler维护的任务移除掉
						mHandler.removeCallbacksAndMessages(null);
						break;
					case MotionEvent.ACTION_UP:
						upX = (int) event.getX();
						upTime = System.currentTimeMillis();
						if(downX == upX && upTime - downTime<500){
							//这是个点击事件,有什么操作可以在这里写
							//暴露一个接口的回调方法
							onViewClickListener.viewClick("点击进入活动页面");
						}
						startRoll();
						break;
					case MotionEvent.ACTION_CANCEL:
						//抓住view不响应事件的临界点去解决bug
						startRoll();
						break;
					}
					return true;
				}
			});
			container.addView(view);
			return view;
		}
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View)object);
		}
	}

}
