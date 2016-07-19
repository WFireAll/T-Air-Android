package com.silenoff.frame.ui;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;

import com.silenoff.frame.R;
import com.silenoff.frame.fragment.MyDeviceFragment;
import com.silenoff.frame.fragment.SelfCenterFragment;
import com.silenoff.frame.fragment.SettingFragment;

public class TabFragmentActivity extends FragmentActivity {

	private Fragment[] fragments;
	private MyDeviceFragment mMyDeviceFragment;
	private SelfCenterFragment mSelfCenterFragment;
	private SettingFragment mSettingFragment;
	
	private Button[] mTabs;
	private int index;
	private int currentTabIndex;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_below);
		initView();
		
		ActionBar actionBar = getActionBar(); 
		actionBar.hide(); 
		
		mMyDeviceFragment = new MyDeviceFragment();
		mSelfCenterFragment = new SelfCenterFragment();
		mSettingFragment = new SettingFragment();
		fragments = new Fragment[] {mMyDeviceFragment,mSettingFragment,mSelfCenterFragment};
		getSupportFragmentManager().beginTransaction()
		.add(R.id.fragment_container, mSettingFragment)
		.commit();
		
	}
	
	private void initView() {
		mTabs = new Button[3];
		mTabs[0] = (Button) findViewById(R.id.fm_below_mydevice);
		mTabs[1] = (Button) findViewById(R.id.fm_below_setting);
		mTabs[2] = (Button) findViewById(R.id.fm_below_self_center);
		mTabs[1].setSelected(true);  //设置当前选择的按钮初值
		currentTabIndex = 1;         //设置当前选择的下标初值
	}
	
	public void onTabClicked(View view) {
		switch (view.getId()) {
		case R.id.fm_below_mydevice:
			index = 0;
			break;

		case R.id.fm_below_setting:
			index = 1;
			break;

		case R.id.fm_below_self_center:
			index = 2;
			break;
		}
		if (currentTabIndex != index) {
			FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
			trx.hide(fragments[currentTabIndex]);
			if (!fragments[index].isAdded()) {
				trx.add(R.id.fragment_container, fragments[index]);
			}
			trx.show(fragments[index]).commit();
		}
		mTabs[currentTabIndex].setSelected(false);   
		mTabs[index].setSelected(true);             
		currentTabIndex = index;    
	}
	
}
