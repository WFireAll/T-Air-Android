package com.silenoff.frame.ui;

import org.apache.http.Header;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.silenoff.frame.Config;
import com.silenoff.frame.R;
import com.silenoff.frame.asynchttp.TextNetWorkCallBack;
import com.silenoff.frame.contentview.ContentWidget;
import com.silenoff.frame.observer.SmsObserver;
import com.silenoff.frame.parser.JsonParser;
import com.silenoff.frame.requests.AsyncRequest;
import com.silenoff.frame.utils.InjectUtils;
import com.silenoff.frame.utils.ToastUtils;
import com.silenoff.frame.widgets.CenterTitleActionBar;

public class RegistActivity extends Activity implements OnClickListener{
	
	@ContentWidget(R.id.et_regist_input_number)
	private EditText et_regist_input_number;
	@ContentWidget(R.id.et_regist_input_password)
	private EditText et_regist_input_password;
	@ContentWidget(R.id.et_regist_input_identifying_code)
	private EditText et_regist_input_identifying_code;
	@ContentWidget(R.id.tv_get_identifying_code)
	private TextView tv_get_identifying_code;
	@ContentWidget(R.id.btn_regist_confirm)
	private Button   btn_regist_confirm;
	/**
	 * 短信内容观察者
	 */
	private SmsObserver mObserver;
	
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			//这里判断从短息观察者那里发送过来的消息，更新UI
			switch(msg.what)
			{
			case Config.MESSAGE_WHAT_SMS_OBSERVER_REGIST:
				String code = (String)msg.obj;
				if(!TextUtils.isEmpty(code)){
					et_regist_input_identifying_code.setText(code);
				}
				break;
			default:
				break;
		
			}
		}
		
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_regist); 
		InjectUtils.injectObject(this, this);
		CenterTitleActionBar actionBar = new CenterTitleActionBar(this, getActionBar());
		actionBar.setTitle("注册");
		actionBar.setCustomActionBar();
		actionBar.setOnClickActionBarListener(new CenterTitleActionBar.OnClickActionBarListener() {
			@Override
			public void onTitleClick() {}
			@Override
			public void onSecondBtnClick() {}
			@Override
			public void onFirstBtnClick() {}
			
			@Override
			public void onBackBtnClick() {
				RegistActivity.this.finish();
			}
		}); 
		
		tv_get_identifying_code.setOnClickListener(this);
		btn_regist_confirm.setOnClickListener(this);
		
		mObserver = new SmsObserver(this, handler);
		Uri uri = Uri.parse("content://sms");
		getContentResolver().registerContentObserver(uri, true, mObserver);
		
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		getContentResolver().unregisterContentObserver(mObserver);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.tv_get_identifying_code:
			//获取验证码
			break;
		case R.id.btn_regist_confirm:
			final String phoneNumber = et_regist_input_number.getText().toString().trim();
			final String password = et_regist_input_password.getText().toString().trim();
			final String identifying_code = tv_get_identifying_code.getText().toString().trim();
			if(TextUtils.isEmpty(phoneNumber)){
				Toast.makeText(RegistActivity.this, "手机号不能为空", Toast.LENGTH_SHORT).show();
			    return;
			}
			if(phoneNumber.indexOf(0)!=1 && phoneNumber.length() != 11){
				Toast.makeText(RegistActivity.this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
				return;
			}
			if(password.length() < 6){
				Toast.makeText(RegistActivity.this, "密码长度不能少于6位数", Toast.LENGTH_SHORT).show();
				return;
			}
			
			/***********************需要更改*********************************/
			if(!TextUtils.isEmpty(identifying_code)){ //这里应该是验证验证码是否正确
				AsyncRequest.ClientPost(Config.getRequestURL(Config.ACTION_REGIST), Config.getRequestParams(Config.ACTION_REGIST, phoneNumber, password), new TextNetWorkCallBack() {
					@Override
					public void onMySuccess(int statusCode, Header[] header, String result) {
						boolean isRegistSuccess = JsonParser.parseRegist(result);
						if(isRegistSuccess)
						{
							Intent intent = new Intent(RegistActivity.this,LoginActivity.class);
							intent.putExtra(Config.KEY_PHONE_NUM, phoneNumber);
							intent.putExtra(Config.KEY_PHONE_PWD, password);
							startActivity(intent);
						}else
						{
							ToastUtils.showToast(RegistActivity.this, "注册失败", 1);
						}
					}
					@Override
					public void onMyFailure(int statusCode, Header[] header, String result,
							Throwable th) {
						ToastUtils.showToast(RegistActivity.this, "网络访问失败,请稍后再试", 1);
					}
				});
			}
			
			break;
		default:
			break;
		}
	}


	
 
}
