package com.silenoff.frame.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.silenoff.frame.Config;
import com.silenoff.frame.R;
import com.silenoff.frame.contentview.ContentWidget;
import com.silenoff.frame.ui.LoginActivity;

public class SelfCenterFragment extends Fragment implements OnClickListener{
	
	/**
	 * 退出或者登陆按钮
	 */
	private Button user_exit;
	/**
	 * 判断按钮显示登陆还是退出
	 */
	private boolean loginOrExits;
	/**
	 * token值
	 */
	String token;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_selfcenter, container, false);
	}
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		user_exit = (Button)getActivity().findViewById(R.id.user_exit);
		
		token = Config.getCachedToken(getActivity());
		
		if(token!=null && !TextUtils.isEmpty(token)) //不为空，说明已经登陆，显示退出
		{
			user_exit.setText(R.string.exit);
			loginOrExits = true;
		}
		else{
			user_exit.setText(R.string.selfCenterUserLogin);//为空，说明未登陆，显示登陆
			loginOrExits = false;
		}
		
		
		user_exit.setOnClickListener(this);
		
	}
	
	
	
	@Override
	public void onClick(View v) {

		switch(v.getId())
		{
		case R.id.user_exit:
			if(loginOrExits) //显示退出
			{
				Config.cacheToken(getActivity().getApplicationContext(),null);
				Config.cachePhoneAndPassword(getActivity().getApplicationContext(), null, null);
				loginOrExits = false;
				user_exit.setText(R.string.selfCenterUserLogin);
			}else{          //显示登陆
				startActivity(new Intent(getActivity(),LoginActivity.class));
				getActivity().finish();
			}
			
			break;
			
		}
	}
	
}
