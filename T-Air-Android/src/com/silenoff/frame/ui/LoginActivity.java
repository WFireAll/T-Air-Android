package com.silenoff.frame.ui;

import org.apache.http.Header;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.silenoff.frame.Config;
import com.silenoff.frame.R;
import com.silenoff.frame.asynchttp.TextNetWorkCallBack;
import com.silenoff.frame.contentview.ContentWidget;
import com.silenoff.frame.parser.JsonParser;
import com.silenoff.frame.requests.AsyncRequest;
import com.silenoff.frame.utils.InjectUtils;
import com.silenoff.frame.utils.ToastUtils;
import com.silenoff.frame.widgets.ClearEditText;

public class LoginActivity extends Activity implements OnClickListener{
	/**
	 * 用户输入手机号框
	 */
	@ContentWidget(R.id.et_login_input_number)
	private ClearEditText et_login_input_number;
	/**
	 * 用户输入密码框
	 */
	@ContentWidget(R.id.et_login_input_password)
	private ClearEditText et_login_input_password;
	/**
	 * 用户登录按钮
	 */
	@ContentWidget(R.id.btn_login_login)
	private Button btn_login_login ;
	
	/**
	 * 用户忘记密码按钮
	 */
	@ContentWidget(R.id.tv_forget_password)
	private TextView tv_forget_password;
	/**
	 * 用户注册账号按钮
	 */
	@ContentWidget(R.id.tv_regist_account)
	private TextView tv_regist_account;
		

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTheme(android.R.style.Theme_Light_NoTitleBar);
		setContentView(R.layout.activity_login);
		InjectUtils.injectObject(this, this);
		
		btn_login_login.setOnClickListener(this);
		tv_forget_password.setOnClickListener(this);
		tv_regist_account.setOnClickListener(this);
		
		//接收从注册页面传回的数据，用于在输入框中显示出来，给用户减少输入
		Intent intent = getIntent();
		String phoneNum = intent.getStringExtra(Config.KEY_PHONE_NUM);
		String password = intent.getStringExtra(Config.KEY_PHONE_PWD);
		if(!TextUtils.isEmpty(phoneNum) && !TextUtils.isEmpty(password))
		{
			et_login_input_number.setText(phoneNum);
			et_login_input_password.setText(password);
		}
	}


	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btn_login_login:
			final String phoneNumber = et_login_input_number.getText().toString().trim();
			final String password = et_login_input_password.getText().toString().trim();
			
			if(TextUtils.isEmpty(phoneNumber)){
				ToastUtils.showToast(this, "用户名不能为空", 1);
				return;
			}
			if(TextUtils.isEmpty(password)){
				ToastUtils.showToast(this, "密码不能为空", 1);
				return;
			}
			
			AsyncRequest.ClientPost(Config.getRequestURL(Config.ACTION_LOGIN), Config.getRequestParams(Config.ACTION_LOGIN, phoneNumber, password), new TextNetWorkCallBack() {
				@Override
				public void onMySuccess(int statusCode, Header[] header, String result) {
					String token = JsonParser.parseLogin(result);
				    Config.cacheToken(LoginActivity.this, token);
					Config.cachePhoneAndPassword(LoginActivity.this, phoneNumber, password);
				    Intent intent = new Intent(LoginActivity.this, TabFragmentActivity.class);
				    intent.putExtra(Config.KEY_TOKEN, token);
				    intent.putExtra(Config.KEY_PHONE_NUM, phoneNumber);
				    startActivity(intent);
				    finish();
				}
				
				@Override
				public void onMyFailure(int statusCode, Header[] header, String result,
						final Throwable th) {
					runOnUiThread(new Runnable() {
						public void run() {
							ToastUtils.showToast(LoginActivity.this, "网络访问失败,请稍后再试", 1);
						}
					});
				}
			});
			
			break;
		case R.id.tv_forget_password:
			launchActivity(ForgetPasswordActivity.class);
			break;
		case R.id.tv_regist_account:
			launchActivity(RegistActivity.class);
			break;
		default:
			break;
		}
	}
	
	
	protected void launchActivity(Class<? extends Activity> cls){
		launchActivity(cls, null);
	}
	protected void launchActivity(Class<? extends Activity> cls, Bundle extras){
		Intent intent = new Intent(LoginActivity.this, cls);
		if(extras!=null){
			intent.putExtras(extras);
		}
		startActivity(intent);
	}

}
