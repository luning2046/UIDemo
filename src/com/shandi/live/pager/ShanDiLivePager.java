package com.shandi.live.pager;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sdlive.main.splash.R;
import com.shandi.live.base.BasePager;
import com.shandi.live.main.AddressActivity;
import com.shandi.live.main.MainActivity;
import com.shandi.live.main.ShoppingActivity;
import com.shandi.live.view.BaneRollViewPager;
import com.shandi.live.view.BaneRollViewPager.OnViewClickListener;

public class ShanDiLivePager extends BasePager {


	private LinearLayout ll_header_rootview;
	
	private View layout_roll_view;
	
	private View list_header_frame;
	
	private LinearLayout top_banes_viewpager;
	
	private ListView lv_activity_list;
	
	//ListView加载图片的Url集合
	private List<String> imgUrlList;
	
	/**
	 * (待定)显示数据JavaBean的集合
	 * @param context
	 */
//	private List<Object> shopInfoList;
	
	public ShanDiLivePager(MainActivity context) {
		super(context);
	}

	@Override
	public View initView() {
		view = View.inflate(context, R.layout.shandi_live_frame, null);
		//listView的头
		list_header_frame = View.inflate(context, R.layout.list_header_frame, null);
		ll_header_rootview = (LinearLayout) list_header_frame.findViewById(R.id.ll_header_rootview);
		//轮播图和大按钮的View
		layout_roll_view = View.inflate(context, R.layout.layout_roll_view, null);
		initButton();
		initData();
		//把轮播图和大按钮添加到页面中
		ll_header_rootview.addView(layout_roll_view);
		lv_activity_list = (ListView) view.findViewById(R.id.lv_activity_list);
		lv_activity_list.addHeaderView(list_header_frame);
		lv_activity_list.setAdapter(new MyListAdapter());
		lv_activity_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO 跳转到WebView页面
				Toast.makeText(context, "跳转到活动页", 0).show();
			}
		});
		top_banes_viewpager = (LinearLayout) layout_roll_view.findViewById(R.id.top_banes_viewpager);
		BaneRollViewPager rollPager = new BaneRollViewPager(context, new OnViewClickListener() {
			
			@Override
			public void viewClick(String url) {
				// TODO 跳转到webview
				Toast.makeText(context, url, 0).show();
			}
		});
		rollPager.startRoll();
		top_banes_viewpager.removeAllViews();
		top_banes_viewpager.addView(rollPager);
		initTitleBar();
		return view;
	}

	private ImageView iv_supermarket;
	private ImageView iv_flower;
	private void initButton() {
		iv_supermarket = (ImageView) layout_roll_view.findViewById(R.id.iv_supermarket);
		iv_supermarket.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(context, ShoppingActivity.class);
				context.startActivity(intent);
			}
		});
		iv_flower = (ImageView) layout_roll_view.findViewById(R.id.iv_flower);
		iv_flower.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(context, ShoppingActivity.class);
				context.startActivity(intent);
			}
		});
	}

	@Override
	public void initData() {
		// TODO 初始化数据
		
		//为了演示效果的临时代码
		imgUrlList = new ArrayList<String>();
		imgUrlList.add("0");
		imgUrlList.add("1");
		imgUrlList.add("2");
		imgUrlList.add("3");
		imgUrlList.add("4");
		
	}

	
	
	@Override
	public void initTitleBar() {
		ImageView imgbtn_left = (ImageView) view.findViewById(R.id.imgbtn_left);
		imgbtn_left.setVisibility(View.INVISIBLE);
		ImageView iv_location = (ImageView) view.findViewById(R.id.iv_location);
		iv_location.setVisibility(View.VISIBLE);
		ImageView iv_dropdown = (ImageView) view.findViewById(R.id.iv_dropdown);
		iv_dropdown.setVisibility(View.VISIBLE);
		txt_title = (TextView) view.findViewById(R.id.txt_title);
		txt_title.setText("送货地址:彩虹大厦");
		txt_title.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(context, AddressActivity.class);
				context.startActivity(intent);
			}
		});
	}

	class MyListAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return imgUrlList.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup prent) {
			View view;
			ViewHolder holder;
			if(convertView == null){
				view = View.inflate(context, R.layout.list_activity_item, null);
				holder = new ViewHolder();
				holder.iv_go_web = (ImageView) view.findViewById(R.id.iv_go_web);
				holder.tv_shop_info = (TextView) view.findViewById(R.id.tv_shop_info);
				holder.tv_shop_name = (TextView) view.findViewById(R.id.tv_shop_name);
				holder.tv_shop_time = (TextView) view.findViewById(R.id.tv_shop_time);
				view.setTag(holder);
			}else{
				view = convertView;
				holder = (ViewHolder) view.getTag();
			}
			return view;
		}
		
	}
	
	static class ViewHolder{
		ImageView iv_go_web;
		TextView tv_shop_info, tv_shop_name, tv_shop_time;
	}
	
}
