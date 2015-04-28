package com.shandi.live.main;

import java.util.ArrayList;
import java.util.List;

import com.sdlive.main.splash.R;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class AddressActivity extends Activity {

	private ImageView imgbtn_left;
	private TextView txt_title, navbarRighTextView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_address);
		initTitle();
		initView();
		initData();
	}

	private ListView lv_addrinfo_list;

	private List<String> jiaList;

	private void initView() {
		lv_addrinfo_list = (ListView) findViewById(R.id.lv_addrinfo_list);
	}

	private void initData(){
		jiaList = new ArrayList<String>();
		jiaList.add("0");
		jiaList.add("1");
		lv_addrinfo_list.setAdapter(new AddrListAdapter());
	}

	private void initTitle() {
		imgbtn_left = (ImageView) findViewById(R.id.imgbtn_left);
		navbarRighTextView = (TextView) findViewById(R.id.navbarRighTextView);
		navbarRighTextView.setVisibility(View.VISIBLE);
		navbarRighTextView.setText("+新增");
		navbarRighTextView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(AddressActivity.this, AddNewAddrActivity.class);
				AddressActivity.this.startActivity(intent);
			}
		});
		txt_title = (TextView) findViewById(R.id.txt_title);
		txt_title.setText("地址管理");
		imgbtn_left.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				AddressActivity.this.finish();
			}
		});
	}

	class AddrListAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return jiaList.size();
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
			View view;
			ViewHolder holder;
			if(convertView == null){
				holder = new ViewHolder();
				view = View.inflate(getApplicationContext(), R.layout.addr_info_item, null);
				holder.ll_set_result_item = (LinearLayout) view.findViewById(R.id.ll_set_result_item);
				holder.ll_edit = (LinearLayout) view.findViewById(R.id.ll_edit);
				holder.ll_delete = (LinearLayout) view.findViewById(R.id.ll_delete);
				holder.iv_set_result = (ImageView) view.findViewById(R.id.iv_set_result);
				view.setTag(holder);
			}else{
				view = convertView;
				holder = (ViewHolder) view.getTag();
			}
			holder.ll_edit.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					//TODO 进入地址编辑页面
				}
			});
			holder.ll_delete.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO 删除这个条目
				}
			});
			holder.iv_set_result.setBackgroundResource(R.drawable.icon_selected);
			return view;
		}

	}
	static class ViewHolder{
		boolean isResult = false;
		LinearLayout ll_set_result_item, ll_edit, ll_delete;
		ImageView iv_set_result;
	}
}
