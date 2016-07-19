package com.silenoff.frame.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.silenoff.frame.Config;
import com.silenoff.frame.R;
import com.silenoff.frame.contentview.ContentWidget;
import com.silenoff.frame.utils.InjectUtils;
import com.silenoff.frame.widgets.CenterTitleActionBar;
import com.silenoff.frame.widgets.CenterTitleActionBar.OnClickActionBarListener;

public class ForgetPasswordActivity extends Activity implements OnClickListener{
	
	@ContentWidget(R.id.et_forget_input_number)
	private EditText et_forget_input_number;
	@ContentWidget(R.id.et_forget_input_identifying_code)
	private EditText et_forget_input_identifying_code;
	@ContentWidget(R.id.tv_forget_get_identifying_code)
	private TextView tv_forget_get_identifying_code;
	@ContentWidget(R.id.btn_forget_confirm)
	private Button btn_forget_confirm;
	
	private String phoneNember;
	private String IdentifyCode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_forget_password);
		InjectUtils.injectObject(this, this);
		
		CenterTitleActionBar actionBar = new CenterTitleActionBar(this, getActionBar());
		actionBar.setTitle("忘记密码");
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
				ForgetPasswordActivity.this.finish();
			}
		});
		
		tv_forget_get_identifying_code.setOnClickListener(this);
		btn_forget_confirm.setOnClickListener(this);
		
		
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.tv_forget_get_identifying_code:
			break;
		case R.id.btn_forget_confirm:
			phoneNember = et_forget_input_number.getText().toString().trim();
			IdentifyCode = et_forget_input_identifying_code.getText().toString().trim();
			if(!TextUtils.isEmpty(phoneNember))  //根据手机号发送验证码，验证身份成功之后
			{
				Intent intent = new Intent(ForgetPasswordActivity.this,ReplacePasswordActivity.class);
				intent.putExtra(Config.KEY_PHONE_NUM, phoneNember);
				startActivity(intent);
				this.finish();
			}
			break;
		default:
			break;
		}
	}
	
	
	
}
