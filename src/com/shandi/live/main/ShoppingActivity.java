package com.shandi.live.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sdlive.main.splash.R;
import com.shandi.live.fragment.HomeFragment;
import com.shandi.live.fragment.YanShiFragment;
import com.shandi.live.util.LogUtil;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ShoppingActivity extends FragmentActivity {
	private ImageView imgbtn_left;
	private TextView txt_title;
	//可扩展的ListView
	private ExpandableListView mExpandListView;
	private CommodityTitleAdapter adapter = null;

	private boolean colorCtrl = false;

	List<Map<String, Object>> groups;  
	List<List<Map<String, Object>>> childs;  
	private FrameLayout fl_right_layout;
	private ImageView iv_selected_ok;
	
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 10:
				if(ysFragment.commodityNum > 0){
					iv_selected_ok.setVisibility(View.VISIBLE);
				}else{
					iv_selected_ok.setVisibility(View.GONE);
				}                                                                  
				break;
			}
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_shopping);
		initView();
	}
	private void initTitle() {
		imgbtn_left = (ImageView) findViewById(R.id.imgbtn_left);
		txt_title = (TextView) findViewById(R.id.txt_title);
		txt_title.setText("闪递生活");
		imgbtn_left.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				ShoppingActivity.this.finish();
			}
		});	
	}
	private YanShiFragment ysFragment;
	private void initView() {
		initTitle();
		initData();
		mExpandListView = (ExpandableListView) findViewById(R.id.el_title_list);
		mExpandListView.setGroupIndicator(null);
		adapter = new CommodityTitleAdapter(getApplicationContext(), groups, childs);
		mExpandListView.setAdapter(adapter);
		mExpandListView.setOnChildClickListener(new OnChildClickListener() {  

			public boolean onChildClick(ExpandableListView parent, View v,  
					int groupPosition, int childPosition, long id) {  
				LogUtil.i("test", "GroupPosition is " + groupPosition);  
				LogUtil.i("test", "ChildPosition is" + childPosition);
				if(colorCtrl){
					colorCtrl = false;
				}else{
					colorCtrl = true;
				}
				adapter.notifyDataSetChanged();
				return false;  
			}  
		});  
		int groupCount = mExpandListView.getCount();
		for (int i=0; i<groupCount; i++) {
			mExpandListView.expandGroup(i);
		};
		fl_right_layout = (FrameLayout) findViewById(R.id.fl_right_layout);
		ysFragment = new YanShiFragment(mHandler);
		getSupportFragmentManager()
			.beginTransaction()
			.replace(R.id.fl_right_layout, ysFragment)
			.commit();
		iv_selected_ok = (ImageView) findViewById(R.id.iv_selected_ok);
		iv_selected_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getApplicationContext(), MainActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("goToCart", "shoppingCart");
				intent.putExtras(bundle);
				startActivity(intent);
				ShoppingActivity.this.finish();
			}
		});
	}
	private void initData(){
		groups = new ArrayList<Map<String, Object>>();  
		Map<String, Object> group = new HashMap<String, Object>();  
		group.put("title", "闪递超市");  
		groups.add(group);  
		List<Map<String, Object>> child1 = new ArrayList<Map<String, Object>>();
		Map<String, Object> child1Data1 = new HashMap<String, Object>();
		child1Data1.put("commodityTitle", "乳制品");
		child1.add(child1Data1);
		childs = new ArrayList<List<Map<String, Object>>>();
		childs.add(child1);
	}

	class CommodityTitleAdapter extends BaseExpandableListAdapter{
		//父单元
		class ExpandableGroupHolder {
			ImageView iv_tubiao, iv_jiantou;
			TextView tv_title;  
		}   

		//子单元类  
		class ExpandableListHolder {  
			TextView tv_commodityTitle;  
		}   
		private List<Map<String, Object>> groupData;//组显示  
		private List<List<Map<String, Object>>> childData;//子列表 
		private LayoutInflater mGroupInflater; //用于加载group的布局xml  
		private LayoutInflater mChildInflater; //用于加载listitem的布局xml  
		//自宝义构造  
		public CommodityTitleAdapter(Context context, List<Map<String, Object>> groupData, List<List<Map<String, Object>>> childData) {  
			this.childData=childData;  
			this.groupData=groupData;  

			mChildInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
			mGroupInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
		}  
		//必须实现 用于得到子数据
		@Override
		public Object getChild(int groupPosition, int childPosition) {
			return childData.get(groupPosition).get(childPosition);
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return groupPosition;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
				ViewGroup viewgroup) {
			ExpandableListHolder holder = null;  
			if (convertView == null) {  
				convertView = mChildInflater.inflate(R.layout.main_tree_child, null);  

				holder = new ExpandableListHolder();  
				holder.tv_commodityTitle = (TextView) convertView.findViewById(R.id.tv_commodity_title);  
				convertView.setTag(holder);  
			} else {//若行已初始化，直接从tag属性获得子视图的引用  
				holder = (ExpandableListHolder) convertView.getTag();  
			}   
			AbsListView.LayoutParams lp =
					new AbsListView.LayoutParams( LinearLayout.LayoutParams.FILL_PARENT, 90); 
			convertView.setLayoutParams(lp);
			Map<String,Object> unitData=this.childData.get(groupPosition).get(childPosition); 
			holder.tv_commodityTitle.setText((String)unitData.get("commodityTitle"));  
			if(colorCtrl){
				holder.tv_commodityTitle.setTextColor(Color.YELLOW);
			}else{
				holder.tv_commodityTitle.setTextColor(Color.BLACK);
			}
			return convertView;  
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			return childData.get(groupPosition).size();
		}

		@Override
		public Object getGroup(int i) {
			return groupData.get(i);
		}

		@Override
		public int getGroupCount() {
			return groupData.size();
		}

		@Override
		public long getGroupId(int arg0) {
			return arg0;
		}

		@Override
		public View getGroupView(int groupPosition, boolean flag, View convertView,
				ViewGroup viewgroup) {
			ExpandableGroupHolder holder = null; //清空临时变量holder  
			if (convertView == null) { //判断view（即view是否已构建好）是否为空  

				convertView = mGroupInflater.inflate(R.layout.main_tree_group, null);  
				holder = new ExpandableGroupHolder();  
				holder.tv_title = (TextView) convertView.findViewById(R.id.tv_tree_title);  
				holder.iv_tubiao = (ImageView) convertView.findViewById(R.id.iv_tree_picetur);
				holder.iv_jiantou = (ImageView) convertView.findViewById(R.id.iv_jiantou);
				convertView.setTag(holder);  
			} else { //若view不为空，直接从view的tag属性中获得各子视图的引用  
				holder = (ExpandableGroupHolder) convertView.getTag();  
			}  
			//判断isExpanded就可以控制是按下还是关闭，同时更换图片
			//            if(flag){
			//                iv_jiantou.setBackgroundResource(R.drawable.arrow_down);
			//            }else{
			//                iv_jiantou.setBackgroundResource(R.drawable.arrow_up);
			//            }
			AbsListView.LayoutParams lp =
					new AbsListView.LayoutParams( LinearLayout.LayoutParams.FILL_PARENT, 90); 
			convertView.setLayoutParams(lp);
			String title=(String)this.groupData.get(groupPosition).get("title");  
			holder.tv_title.setText(title);  
			notifyDataSetChanged();  
			return convertView;  
		}

		@Override
		public boolean hasStableIds() {//行是否具有唯一id
			return false;
		}

		@Override
		public boolean isChildSelectable(int arg0, int arg1) {//行是否可选
			return true;
		}

	}
}
