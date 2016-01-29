package com.example.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import com.example.view.adapter.CommonAdapter;
import com.example.view.adapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
	private Context mContext;

	private GridView mGvContent;
	private GVAdapter mAdapter;

	private int currentPosition = -1;//当前选中的条目

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		setContentView(R.layout.activity_tab_selected);
		initViews();
	}

	private void initViews() {
		mGvContent = (GridView) findViewById(R.id.gv_content);
		initData();
	}

	private void initData() {
		final List<TabDomain> mTabList = new ArrayList<>();
        /*--------------模拟初始化数据----------------*/
		TabDomain tab1 = new TabDomain();
		tab1.text = "签到（8:30）";
		tab1.isSelected = true;
		TabDomain tab2 = new TabDomain();
		tab2.text = "中午签退（12:00）";
		TabDomain tab3 = new TabDomain();
		tab3.text = "签到（1:30）";
		TabDomain tab4 = new TabDomain();
		tab4.text = "下午签退（1:30）";
		mTabList.add(tab1);
		mTabList.add(tab2);
		mTabList.add(tab3);
		mTabList.add(tab4);
		if (currentPosition == -1) {//默认第一个选中
			mTabList.get(0).isSelected = true;
			currentPosition = 0;
		}
		//设置gridveiw内容
		mAdapter = new GVAdapter(mContext, mTabList, R.layout.activity_tab_selected_item);
		mGvContent.setAdapter(mAdapter);
		//gridview点击事件
		mGvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (currentPosition != position) {
					mTabList.get(currentPosition).isSelected = false;
					mTabList.get(position).isSelected = true;
					currentPosition = position;
					mAdapter.notifyDataSetChanged();
				}
			}
		});
	}

	/**
	 * adapter
	 */
	private class GVAdapter extends CommonAdapter<TabDomain> {

		public GVAdapter(Context context, List list, int itemLayoutId) {
			super(context, list, itemLayoutId);
		}

		@Override
		public void convert(ViewHolder helper, TabDomain item) {
			helper.setText(R.id.tv_time, item.text);
			if (item.isSelected) {
				((TextView) helper.getView(R.id.tv_time)).setTextColor(getResources().getColor(item.selectedColor));
				helper.getView(R.id.v_time).setBackgroundColor(getResources().getColor(item.selectedColor));
			}else{
				((TextView) helper.getView(R.id.tv_time)).setTextColor(getResources().getColor(item.unselectedColor));
				helper.getView(R.id.v_time).setBackgroundColor(getResources().getColor(item.unselectedColor));
			}
		}
	}
}
