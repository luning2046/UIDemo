package com.shandi.live.pager;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.sax.StartElementListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sdlive.main.splash.R;
import com.shandi.live.base.BasePager;
import com.shandi.live.main.MainActivity;

public class ShoppingCartPager extends BasePager {

	private ListView lv_commodity;
	
	private List<String> imgUrlList;
	
	//预留的解析JSON后JavaBean的集合
//	private List<Object> commodityList;
	
	public ShoppingCartPager(MainActivity context) {
		super(context);
	}

	@Override
	public View initView() {
		//TODO 初始化页面
		view = View.inflate(context, R.layout.shopping_cart_frame, null);
		lv_commodity = (ListView) view.findViewById(R.id.lv_commodity);
		initTitleBar();
		return view;
	}

	@Override
	public void initData() {
		//TODO 填充数据
	}
	@Override
	public void initTitleBar() {
		ImageView imgbtn_left = (ImageView) view.findViewById(R.id.imgbtn_left);
		imgbtn_left.setVisibility(View.INVISIBLE);
		txt_title = (TextView) view.findViewById(R.id.txt_title);
		txt_title.setText("购物车");
	}
}
