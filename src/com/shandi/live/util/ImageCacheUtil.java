package com.shandi.live.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.util.Log;


public class ImageCacheUtil {
	public static final int SUCCESS = 100;
	public static final int FAIL = 101;
	private static final String tag = "ImageCacheUtil";
	private Context context;
	private LruCache<String,Bitmap> lruCache;
	private File cacheDir;
	private ExecutorService newFixedThreadPool;
	private Handler handler;
	
	public ImageCacheUtil(Context context,Handler handler) {
		this.context = context;
		this.handler = handler;
		newFixedThreadPool = Executors.newFixedThreadPool(5);
		cacheDir = context.getCacheDir();
		//获取运行环境内存的1/8用作缓存图片使用
		int maxSize = (int) (Runtime.getRuntime().maxMemory()/8);
		lruCache = new LruCache<String, Bitmap>(maxSize){
			//每存储一张图片的大小
			protected int sizeOf(String key, Bitmap value) {
				//value.getRowBytes()返回的就是当前一行上占用的字节数*图片高度
				return value.getRowBytes()*value.getHeight();
			};
		};
	}
	//指定ImageView给设置上tag，当下载完成后，如果当前tag有对应的ImageView控件，
	//就将当前下载图片设置给当前ImageView
	public Bitmap getBitmapFromUrl(String url,int position){
		//内存中获取图片（LRU）
		Bitmap bitmap = lruCache.get(url);
		if(bitmap!=null){
			LogUtil.i(tag, "从内存中获取到了图片");
			return bitmap;
		}
		
		//文件中获取
		bitmap = getBitmapFromLocal(url);
		if(bitmap!=null){
			LogUtil.i(tag, "从文件中获取到了图片");
			return bitmap;
		}
		
		//网络获取
		LogUtil.i(tag, "从网络中获取到了图片");
		getBitmapFromNet(url,position);
		return null;
	}
	
	private void getBitmapFromNet(String url, int position) {
		newFixedThreadPool.execute(new RunnableTask(url,position));
	}
	
	class RunnableTask implements Runnable{
		private String imgUrl;
		private int position;
		private HttpURLConnection connection;
		public RunnableTask(String imgUrl, int position) {
			this.imgUrl = imgUrl;
			this.position = position;
		}
		@Override
		public void run() {
			//子线程中所做操作,开启网络请求操作
			try {
				URL url = new URL(imgUrl);
				connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("GET");
				
				connection.setConnectTimeout(5000);
				
				connection.setReadTimeout(5000);
				
				if(connection.getResponseCode() == 200){
					//成功获取数据
					InputStream inputStream = connection.getInputStream();
					Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
					//handler机制将图片放置到主线程中去做显示
					Message msg = new Message();
					msg.obj = bitmap;
					msg.arg1 = position;
					msg.what = SUCCESS;
					handler.sendMessage(msg);
					
					//存储到内存中
					lruCache.put(imgUrl, bitmap);
					//写入到文件的操作
					writeToLocal(imgUrl, bitmap);
					return;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				if(connection!=null){
					connection.disconnect();
				}
			}
			//发送一个空消息
			handler.obtainMessage(FAIL).sendToTarget();
		}
	}
	//读取文件中图片的操作
	private Bitmap getBitmapFromLocal(String url) {
		try {
			String fileName = MD5Encoder.encode(url).substring(10);
			File file = new File(cacheDir, fileName);
			Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
			//内存中获取数据较为高效，所以将其加到内存中去
			lruCache.put(url, bitmap);
			return bitmap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	//图片写入文件操作
	private void writeToLocal(String imgUrl, Bitmap bitmap) {
		try {
			String fileName = MD5Encoder.encode(imgUrl).substring(10);
			File file = new File(cacheDir, fileName);
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			//写入文件的操作
			bitmap.compress(CompressFormat.JPEG, 100, fileOutputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
