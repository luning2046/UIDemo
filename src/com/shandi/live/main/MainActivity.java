package com.shandi.live.main;

import com.sdlive.main.splash.R;
import com.shandi.live.base.BaseFragment;
import com.shandi.live.fragment.HomeFragment;
import com.shandi.live.util.LogUtil;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

public class MainActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		BaseFragment mHomeFragment = new HomeFragment();
		if(getIntent().getExtras() != null){
			String pagerStatus = getIntent().getExtras().getString("goToCart");
			LogUtil.i("MainActivity:pagerStatus==", pagerStatus);
			mHomeFragment.setArguments(getIntent().getExtras());
		}
		//预填充
		getSupportFragmentManager()
			.beginTransaction()
			.replace(R.id.content_frame, mHomeFragment, "HOME")
			.commit();
	}
}
