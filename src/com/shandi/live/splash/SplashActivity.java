package com.shandi.live.splash;

import com.sdlive.main.splash.R;
import com.shandi.live.main.MainActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

public class SplashActivity extends Activity {

	private SharedPreferences sp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);
		sp = getSharedPreferences("config", 0);
		new Handler(){
			@Override
			public void handleMessage(Message msg) {
				/**
				 * 1 判断如果当前用户是第一次进入app的话，就需要进入引导界面
				 * 2 如果用户不是第一次进入app的话，就直接进入主界面
				 */
				if(sp.getBoolean("is_first", true)){
					//返回true进入导航页，记录is_first状态为false，下次就直接进入主页
					sp.edit().putBoolean("is_first", false).commit();
					startActivity(new Intent(SplashActivity.this,GuideActivity.class));
					finish();
				}else{
					//返回false进入主页
					startActivity(new Intent(SplashActivity.this,MainActivity.class));
					finish();
				}
			};
		}.sendEmptyMessageDelayed(0, 3000);
	}

}
