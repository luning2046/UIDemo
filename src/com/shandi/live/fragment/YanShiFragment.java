package com.shandi.live.fragment;

import java.util.ArrayList;
import java.util.List;

import com.sdlive.main.splash.R;
import com.shandi.live.base.BaseFragment;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ValidFragment")
public class YanShiFragment extends BaseFragment {
	public static final String tag = "YanShiFragment";
	public int commodityNum = 0;
	private View view;
	private ListView yanShiList;
	private List<String> viewList;
	private Handler mHanlder;
	public YanShiFragment(Handler mHandler){
		this.mHanlder = mHandler;
	}
	
	@Override
	public View initView() {
		view = View.inflate(context, R.layout.yanshi_listview, null);
		yanShiList = (ListView) view.findViewById(R.id.lv_yanshi);
		return view;
	}

	@Override
	public void initData() {
		viewList = new ArrayList<String>();
		viewList.add("0");
		viewList.add("1");
		viewList.add("2");
		yanShiList.setAdapter(new YanShiAdapter());
		yanShiList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Toast.makeText(context, "跳转到商品详情", 0).show();
			}
		});
	}

	class YanShiAdapter extends BaseAdapter{
		
		@Override
		public int getCount() {
			return viewList.size();
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final ViewHolder holder;
			View view;
			if(convertView == null){
				holder = new ViewHolder();
				view = View.inflate(context, R.layout.commodity_table_item, null);
				holder.rl_add_button = (RelativeLayout) view.findViewById(R.id.rl_add_button);
				holder.rl_cut_button = (RelativeLayout) view.findViewById(R.id.rl_cut_button);
				holder.rl_num_bg = (RelativeLayout) view.findViewById(R.id.rl_num_bg);
				holder.tv_commodity_num = (TextView) view.findViewById(R.id.tv_commodity_num);
				view.setTag(holder);
			}else{
				view = convertView;
				holder = (ViewHolder) view.getTag();
			}
			holder.rl_add_button.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					commodityNum++;
					holder.number++;
					holder.rl_num_bg.setVisibility(View.VISIBLE);
					holder.tv_commodity_num.setText(holder.number +"");
					mHanlder.sendEmptyMessage(10);
				}
			});
			holder.rl_cut_button.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					if(holder.number > 1){
						holder.number--;
						holder.rl_num_bg.setVisibility(View.VISIBLE);
						holder.tv_commodity_num.setText(holder.number + "");
					}else if(holder.number == 1){
						holder.rl_num_bg.setVisibility(View.GONE);
						holder.number = 0;
					}
					commodityNum--;
					mHanlder.sendEmptyMessage(10);
				}
			});
			return view;
		}
		
	}
	static class ViewHolder{
		RelativeLayout rl_add_button, rl_cut_button, rl_num_bg;
		TextView tv_commodity_num;
		int number = 0;
	}
}
