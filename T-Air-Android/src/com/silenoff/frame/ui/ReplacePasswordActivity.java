package com.silenoff.frame.ui;

import org.apache.http.Header;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.silenoff.frame.Config;
import com.silenoff.frame.R;
import com.silenoff.frame.asynchttp.TextNetWorkCallBack;
import com.silenoff.frame.contentview.ContentWidget;
import com.silenoff.frame.parser.JsonParser;
import com.silenoff.frame.requests.AsyncRequest;
import com.silenoff.frame.utils.InjectUtils;
import com.silenoff.frame.utils.ToastUtils;
import com.silenoff.frame.widgets.CenterTitleActionBar;
import com.silenoff.frame.widgets.CenterTitleActionBar.OnClickActionBarListener;

public class ReplacePasswordActivity extends Activity{

	@ContentWidget(R.id.et_new_password_input_number)
	private EditText et_new_password_input_number;
	@ContentWidget(R.id.et_new_password_input_number_confirm)
	private EditText et_new_password_input_number_confirm;
	@ContentWidget(R.id.btn_new_password_confirm)
	private Button btn_new_password_confirm;
	
	private String phoneNum;
	private String newPassword;
	private String newPasswordConform;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_replace_password);
		InjectUtils.injectObject(this, this);
		CenterTitleActionBar actionBar = new CenterTitleActionBar(this, getActionBar());
		actionBar.setTitle("重置密码");
		actionBar.setCustomActionBar();
		actionBar.setOnClickActionBarListener(new OnClickActionBarListener() {
			@Override
			public void onTitleClick() {}
			@Override
			public void onSecondBtnClick() {}
			@Override
			public void onFirstBtnClick() {}
			@Override
			public void onBackBtnClick() {
				ReplacePasswordActivity.this.finish();
			}
		});
		Intent intent = getIntent();
		phoneNum = intent.getStringExtra(Config.KEY_PHONE_NUM);
		
		btn_new_password_confirm.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				newPassword = et_new_password_input_number.getText().toString().trim();
				newPasswordConform = et_new_password_input_number_confirm.getText().toString().trim();
				
				if(TextUtils.isEmpty(newPassword))
				{
					ToastUtils.showToast(ReplacePasswordActivity.this, getResources().getString(R.string.passwordCanNotNone), 1);
				    return;
				}
				if(!TextUtils.equals(newPassword, newPasswordConform))
				{
					ToastUtils.showToast(ReplacePasswordActivity.this, getResources().getString(R.string.passwordMustBeSame), 1);
				    return;
				}
				
				AsyncRequest.ClientPost(Config.getRequestURL(Config.ACTION_REPLACE), Config.getRequestParams(Config.ACTION_REPLACE, phoneNum,newPassword), new TextNetWorkCallBack() {
					
					@Override
					public void onMySuccess(int statusCode, Header[] header, String result) {
						boolean isReplaceSuccess = JsonParser.parseReplace(result);
						if(isReplaceSuccess)
						{
							Config.cacheToken(ReplacePasswordActivity.this, null);
							Config.cachePhoneAndPassword(ReplacePasswordActivity.this, null, null);
							Intent intent = new Intent(ReplacePasswordActivity.this,LoginActivity.class);
							intent.putExtra(Config.KEY_PHONE_NUM, phoneNum);
							intent.putExtra(Config.KEY_PHONE_PWD, newPassword);
							startActivity(intent);
							ReplacePasswordActivity.this.finish();
						}else
						{
							ToastUtils.showToast(ReplacePasswordActivity.this, "密码重置失败，请稍后再试", 1);
						}
					}
					
					@Override
					public void onMyFailure(int statusCode, Header[] header, String result,
							Throwable th) {
						ToastUtils.showToast(ReplacePasswordActivity.this, getResources().getString(R.string.networkFail), 1);
					}
				});
			}
		});
	}
	
	
}
