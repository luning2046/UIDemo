package com.shandi.live.main;

import com.sdlive.main.splash.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MyOrderActivity extends Activity {

	private ImageView imgbtn_left;
	private TextView txt_title, navbarRighTextView;
	private ListView lv_order_list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_my_order);
		initTitle();
	}

	private void initTitle() {
		imgbtn_left = (ImageView) findViewById(R.id.imgbtn_left);
		imgbtn_left.setVisibility(View.VISIBLE);
		imgbtn_left.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				MyOrderActivity.this.finish();
			}
		});
		txt_title = (TextView) findViewById(R.id.txt_title);
		txt_title.setText("我的订单");
		navbarRighTextView = (TextView) findViewById(R.id.navbarRighTextView);
		navbarRighTextView.setVisibility(View.GONE);
		lv_order_list = (ListView) findViewById(R.id.lv_order_list);
	}
	
}
