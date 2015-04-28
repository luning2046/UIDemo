package com.shandi.live.pager;

import android.content.Intent;
import android.location.Address;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sdlive.main.splash.R;
import com.shandi.live.base.BasePager;
import com.shandi.live.main.AboutShandiLiveActivity;
import com.shandi.live.main.AddressActivity;
import com.shandi.live.main.MainActivity;

public class MyShanDiPager extends BasePager implements OnClickListener{

	
	private RelativeLayout rl_my_order,rl_address_manager,rl_chage_pass,rl_feedback,rl_call_phone,rl_update,rl_about_us;
	private Intent intent;
	
	public MyShanDiPager(MainActivity context) {
		super(context);
	}

	@Override
	public View initView() {
		view = View.inflate(context, R.layout.my_shandi_frame, null);
		rl_my_order = (RelativeLayout) view.findViewById(R.id.rl_my_order);
		rl_address_manager = (RelativeLayout) view.findViewById(R.id.rl_address_manager);
		rl_chage_pass = (RelativeLayout) view.findViewById(R.id.rl_chage_pass);
		rl_feedback = (RelativeLayout) view.findViewById(R.id.rl_feedback);
		rl_call_phone = (RelativeLayout) view.findViewById(R.id.rl_call_phone);
		rl_update = (RelativeLayout) view.findViewById(R.id.rl_update);
		rl_about_us = (RelativeLayout) view.findViewById(R.id.rl_about_us);
		
		rl_my_order.setOnClickListener(this);
		rl_address_manager.setOnClickListener(this);
		rl_chage_pass.setOnClickListener(this);
		rl_feedback.setOnClickListener(this);
		rl_call_phone.setOnClickListener(this);
		rl_update.setOnClickListener(this);
		rl_about_us.setOnClickListener(this);
		
		initTitleBar();
		return view;
	}

	@Override
	public void initData() {
		// TODO 初始化数据

	}

	@Override
	public void initTitleBar() {
		ImageView imgbtn_left = (ImageView) view.findViewById(R.id.imgbtn_left);
		imgbtn_left.setVisibility(View.INVISIBLE);
		txt_title = (TextView) view.findViewById(R.id.txt_title);
		txt_title.setText("我的闪递");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_my_order:
			//TODO 跳转到我的订单
			break;
		case R.id.rl_address_manager:
			intent = new Intent(context,AddressActivity.class);
			context.startActivity(intent);
			break;
		case R.id.rl_chage_pass:
			//TODO 跳转到修改密码
			break;
		case R.id.rl_feedback:
			//TODO 跳转到意见反馈
			break;
		case R.id.rl_call_phone:
			intent = new Intent(Intent.ACTION_CALL, Uri
					.parse("tel:" + "4008087018"));
			context.startActivity(intent);
			break;
		case R.id.rl_update:
			//TODO 更新
			break;
		case R.id.rl_about_us:
			intent = new Intent(context, AboutShandiLiveActivity.class);
			context.startActivity(intent);
			break;
		}
	}
	
}
