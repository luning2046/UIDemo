package com.shandi.live.base;

import com.sdlive.main.splash.R;
import com.shandi.live.main.MainActivity;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public abstract class BasePager {
	public MainActivity context;
	public View view;
	public TextView txt_title;
	public BasePager(MainActivity context){
		this.context = context;
		view = initView();
	}

	//返回当前界面显示效果的方法
	public View getRootView(){
		return view;
	}

	
	/**
	 * 请求网络获取初始数据的方法(预留)
	 * @return
	 */
//	public void getData();
	
	//初始化标题的方法
	public abstract void initTitleBar();
	
	//预设UI方法
	public abstract View initView();
	//预设构建数据方法
	public abstract void initData();
}
