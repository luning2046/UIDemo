package com.shandi.live.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {
	public View view;
	public Context context;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.context = getActivity();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = initView();
		return view;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		//拿数据去填充UI
		initData();
		super.onActivityCreated(savedInstanceState);
	}
	//预设UI方法
	public abstract View initView();
	//填充数据方法
	public abstract void initData();
}
