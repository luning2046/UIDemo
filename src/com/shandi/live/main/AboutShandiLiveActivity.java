package com.shandi.live.main;

import com.sdlive.main.splash.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutShandiLiveActivity extends Activity {
	
	private ImageView imgbtn_left;
	private TextView txt_title, navbarRighTextView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_about_shandi);
		initTitle();
	}

	private void initTitle() {
		imgbtn_left = (ImageView) findViewById(R.id.imgbtn_left);
		txt_title = (TextView) findViewById(R.id.txt_title);
		navbarRighTextView = (TextView) findViewById(R.id.navbarRighTextView);
		navbarRighTextView.setVisibility(View.GONE);
		txt_title.setText("关于闪递生活");
		imgbtn_left.setVisibility(View.VISIBLE);
		imgbtn_left.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				AboutShandiLiveActivity.this.finish();
			}
		});
		
	}
	
}
