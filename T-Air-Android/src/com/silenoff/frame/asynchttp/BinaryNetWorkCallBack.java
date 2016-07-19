package com.silenoff.frame.asynchttp;

import org.apache.http.Header;

import com.loopj.android.http.AsyncHttpResponseHandler;

public abstract class BinaryNetWorkCallBack extends AsyncHttpResponseHandler {

	@Override
	public void onStart() {
		super.onStart();
	}
	
	@Override
	public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
		onMyFailure(arg0,arg1,arg2,arg3);
	}

	@Override
	public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
		onMySuccess(arg0,arg1,arg2);
	}
	
	public abstract void onMySuccess(int statusCode, Header[] header, byte[] result);
	public abstract void onMyFailure(int statusCode, Header[] header, byte[] result, Throwable th);

}
