package com.shandi.live.main;

import com.sdlive.main.splash.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class AddNewAddrActivity extends Activity {
	private ImageView imgbtn_left;
	private TextView txt_title, navbarRighTextView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_add_address);
		initTitle();
	}
	private void initTitle() {
		imgbtn_left = (ImageView) findViewById(R.id.imgbtn_left);
		navbarRighTextView = (TextView) findViewById(R.id.navbarRighTextView);
		navbarRighTextView.setVisibility(View.VISIBLE);
		navbarRighTextView.setText("保存");
		navbarRighTextView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				//TODO 保存后跳转
			}
		});
		txt_title = (TextView) findViewById(R.id.txt_title);
		txt_title.setText("新增地址");
		imgbtn_left.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				AddNewAddrActivity.this.finish();
			}
		});
	}
}
