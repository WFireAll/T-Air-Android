package com.silenoff.frame.asynchttp;

import org.apache.http.Header;

import com.loopj.android.http.TextHttpResponseHandler;

public abstract class TextNetWorkCallBack extends TextHttpResponseHandler {

	@Override
	public void onStart() {
		super.onStart();
	}
	
	@Override
	public void onSuccess(int arg0, Header[] arg1, String arg2) {
		onMySuccess(arg0,arg1,arg2);
	}
	
	@Override
	public void onFailure(int arg0, Header[] arg1, String arg2, Throwable arg3) {
		onMyFailure(arg0,arg1,arg2,arg3);
	}
	
	public abstract void onMySuccess(int statusCode, Header[] header, String result);
	public abstract void onMyFailure(int statusCode, Header[] header, String result, Throwable th);

}
