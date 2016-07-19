package com.silenoff.frame.widgets;

import android.app.ActionBar;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.silenoff.frame.R;

public class CenterTitleActionBar{
	
	private ActionBar mActionBar = null;
	private Context mContext;
	private int mBackgroundID = 0;
	private int mBackImageID  = 0;
	private String mTitle     = "";
	private int mFirstBtnID   = 0;
	private int mSecondBtnID  = 0;
	
	private OnClickActionBarListener mOnClickActionBarListener = null;
	
	
	public CenterTitleActionBar(Context context,ActionBar actionBar){
		if(mContext == null){
			this.mContext = context;
			this.mActionBar = actionBar;
		}
	}
	
	public void setCustomActionBar(){       
		 if (mActionBar == null) {            
			 return;
		 }
		 if(mBackgroundID !=0 ){
			 mActionBar.setBackgroundDrawable(mContext.getResources().getDrawable(mBackgroundID));
		 }else{
			 mActionBar.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.actionbar_bg));
		 }
		 mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);        
		 mActionBar.setDisplayShowCustomEnabled(true);        
		 mActionBar.setCustomView(R.layout.center_title_action_bar); 
		 TextView tvTitle = (TextView) mActionBar.getCustomView().findViewById(R.id.menuTvTitle);  
		 if(!TextUtils.isEmpty(mTitle)){
			 tvTitle.setText(mTitle);
		 }else{
			 tvTitle.setText(getApplicationName());
		 }
		 ImageButton IBtnBack = (ImageButton) mActionBar.getCustomView().findViewById(R.id.menuBtnBack);        
		 if(mBackImageID != 0){
			 IBtnBack.setBackground(mContext.getResources().getDrawable(mBackImageID));
		 }else{
			 IBtnBack.setBackground(mContext.getResources().getDrawable(R.drawable.back));
		 }
		 ImageButton IBtnFirst = (ImageButton) mActionBar.getCustomView().findViewById(R.id.menuFirstBtn);        
		 if(mFirstBtnID != 0){
			 IBtnFirst.setVisibility(View.VISIBLE);
			 IBtnFirst.setBackground(mContext.getResources().getDrawable(mFirstBtnID)); 
		 }else{
			 IBtnFirst.setVisibility(View.GONE);
		 }
		 ImageButton IBtnSecond = (ImageButton) mActionBar.getCustomView().findViewById(R.id.menuSecondBtn);        
		 if(mSecondBtnID != 0){
			 IBtnSecond.setVisibility(View.VISIBLE);
			 IBtnSecond.setBackground(mContext.getResources().getDrawable(mSecondBtnID));
		 }else{
			 IBtnSecond.setVisibility(View.GONE);
		 }
		 mActionBar.getCustomView().findViewById(R.id.menuBtnBack).setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				if(mOnClickActionBarListener != null){
					mOnClickActionBarListener.onBackBtnClick();
				}
			}
		});
		mActionBar.getCustomView().findViewById(R.id.menuTvTitle).setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				if(mOnClickActionBarListener != null){
					mOnClickActionBarListener.onTitleClick();
				}
			}
		});
		mActionBar.getCustomView().findViewById(R.id.menuFirstBtn).setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				if(mOnClickActionBarListener != null){
					mOnClickActionBarListener.onFirstBtnClick();
				}
			}
		});
		mActionBar.getCustomView().findViewById(R.id.menuSecondBtn).setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				if(mOnClickActionBarListener != null){
					mOnClickActionBarListener.onSecondBtnClick();
				}
			}
		});
	 }
	
	 private String getApplicationName() { 
		PackageManager packageManager = null; 
		ApplicationInfo applicationInfo = null; 
		try { 
		    packageManager = mContext.getApplicationContext().getPackageManager(); 
		    applicationInfo = packageManager.getApplicationInfo(mContext.getPackageName(), 0); 
		}catch (PackageManager.NameNotFoundException e) { 
		    applicationInfo = null; 
		} 
		String applicationName = (String) packageManager.getApplicationLabel(applicationInfo); 
		return applicationName; 
	} 
	 
	 
	 
	 public static interface OnClickActionBarListener{
		 void onBackBtnClick();
		 void onTitleClick();
		 void onFirstBtnClick();
		 void onSecondBtnClick();
	 }
	

	public int getBackgroundID() {
		return mBackgroundID;
	}

	public void setBackgroundID(int backgroundID) {
		mBackgroundID = backgroundID;
	}

	public int getBackImageID() {
		return mBackImageID;
	}

	public void setBackImageID(int backImageID) {
		mBackImageID = backImageID;
	}

	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String title) {
		mTitle = title;
	}

	public int getFirstBtnID() {
		return mFirstBtnID;
	}

	public void setFirstBtnID(int firstBtnID) {
		mFirstBtnID = firstBtnID;
	}

	public int getSecondBtnID() {
		return mSecondBtnID;
	}

	public void setSecondBtnID(int secondBtnID) {
		mSecondBtnID = secondBtnID;
	}

	public OnClickActionBarListener getOnClickActionBarListener() {
		return mOnClickActionBarListener;
	}

	public void setOnClickActionBarListener(
			OnClickActionBarListener onClickActionBarListener) {
		mOnClickActionBarListener = onClickActionBarListener;
	}
	
}