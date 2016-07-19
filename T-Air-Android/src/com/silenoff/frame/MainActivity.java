package com.silenoff.frame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.silenoff.frame.ui.LoginActivity;
import com.silenoff.frame.ui.TabFragmentActivity;

public class MainActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		String token = Config.getCachedToken(this);
		if(token!=null && !TextUtils.isEmpty(token)){
			Intent intent = new Intent(this, TabFragmentActivity.class);
			intent.putExtra(Config.KEY_TOKEN, token);
			startActivity(intent);
		}else{
			startActivity(new Intent(this, LoginActivity.class));
		}
		finish();
		
	}
	
	
}
