package com.shandi.live.util;

import android.util.Log;

public class LogUtil {
	//日志管理工具类
	private static boolean isOpen = true;
	
	public static void i(String tag,String msg){
		if(isOpen){
			Log.e(tag,msg);
		}
	}
}
