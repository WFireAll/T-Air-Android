package com.silenoff.frame.requests;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.silenoff.frame.asynchttp.BinaryNetWorkCallBack;
import com.silenoff.frame.asynchttp.TextNetWorkCallBack;

public class AsyncRequest {
	
	private static AsyncHttpClient client = new AsyncHttpClient(); 
	
	public static void ClientGet(String url,TextNetWorkCallBack callback){
		client.get(url, callback);
	}
	
	public static void Client(String url,RequestParams params, TextNetWorkCallBack callback){
		client.get(url, params, callback);
	}
	
	public static void ClientPost(String url,RequestParams params, TextNetWorkCallBack callback){
		client.post(url, params, callback);
	}
	
	
	public static void ClientGet(String url,BinaryNetWorkCallBack bcallback){
		client.get(url, bcallback);
	}
	
	public static void Client(String url,RequestParams params, BinaryNetWorkCallBack bcallback){
		client.get(url, params, bcallback);
	}
	
	public static void ClientPost(String url,RequestParams params, BinaryNetWorkCallBack bcallback){
		client.post(url, params, bcallback);
	}
}
