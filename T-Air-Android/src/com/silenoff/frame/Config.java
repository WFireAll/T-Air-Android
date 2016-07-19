package com.silenoff.frame;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences.Editor;

import com.loopj.android.http.RequestParams;
import com.silenoff.frame.utils.Md5Encoder;

public class Config {

	/**
	 * 是否开启debug模式
	 */
	public static final boolean DEBUG = false;	
	/**
	 * 服务器的路径
	 */
    public static final String SERVER_URL = "http://192.168.191.1:8080/T-Air-Server";
    /**
     * token最多保存七天
     */
    public static final long MAX_TOKEN = 1000 * 60 * 60 * 24 * 7;
    
	public static final String KEY_TOKEN = "token";
	public static final String KEY_ACTION = "action";
	public static final String KEY_PHONE_NUM = "phone_number";
	public static final String KEY_PHONE_PWD = "password";
	public static final String KEY_STATUS = "status";
	public static final String KEY_PHONE_MD5 = "phone_md5";
	
	public static final String APP_ID="com.example.frame";
	public static final String CHARSET = "utf-8";
	
	public static final int RESULT_STATUS_Fail = 0;
	public static final int RESULT_STATUS_SUCCESS = 1;
	public static final int RESULT_STATUS_INVALID_TOKEN = 2;
	
	
	 //用于发送系统消息的what值，从100开始
	public static final int MESSAGE_WHAT_SMS_OBSERVER_REGIST = 100;
	
	
	/**
	 * 登录请求的action
	 */
	//public static final String ACTION_LOGIN = "login.do";
	public static final String ACTION_LOGIN = "LoginServlet";
    /**
     * 注册请求的action
     */
	//public static final String ACTION_REGIST = "regist.do";
	public static final String ACTION_REGIST = "RegistServlet";
	
	/**
	 * 更换密码的action
	 */
	//public static final String ACTION_REGIST = "replace.do";
	public static final String ACTION_REPLACE = "ReplaceServlet";
	
	
	/**
	 * 得到缓存的token值
	 * @param context 当前上下文
	 * @return 已经缓存的token值
	 */
	public static String getCachedToken(Context context){
		return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_TOKEN, null);
	}
	
	/**
	 * 缓存token
	 * @param context 当前上下文
	 * @param token token值
	 */
	public static void cacheToken(Context context, String token){
		Editor editor = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
		editor.putString(KEY_TOKEN, token);
		editor.commit();
	}
	
	/**
	 * 得到缓存中的手机号和密码
	 * @param context
	 * @return
	 */
	public static List<String> getCachedPhoneAndPassword(Context context){
		List<String> caches = new ArrayList<String>();
		caches.add(context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_PHONE_NUM, null));
		caches.add(context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_PHONE_PWD, null));
		return caches;
	}
	
	/**
	 * 缓存用户的手机号和密码
	 * @param context 当前上下文
	 * @param phoneNum 手机号码
	 * @param password 密码
	 */
	public static void cachePhoneAndPassword(Context context, String phoneNum, String password){
		Editor editor = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
		editor.putString(KEY_PHONE_NUM, phoneNum);
		editor.putString(KEY_PHONE_PWD, password);
		editor.commit();
	}
	
	/**
	 * 得到相应action请求的URL路径
	 * @param action 需要请求的action
	 * @return URL路径
	 */
	public static String getRequestURL(String action){
		return SERVER_URL + "/" + action;
	}
	
	/**
	 * 得到请求的参数键值对
	 * @param action 需要访问的action
	 * @param values 键值对的值
	 *                第一个参数表示电话号码
	 *                第二个参数表示密码
	 * @return RequestParams
	 */
	public static RequestParams getRequestParams(String action, String... values){
		RequestParams params = new RequestParams();
		if(ACTION_LOGIN.equals(action)){
			String md5Num = Md5Encoder.encode(values[0]);
			String md5Pwd = Md5Encoder.encode(values[1]);
			params.put(KEY_PHONE_NUM, md5Num);
			params.put(KEY_PHONE_PWD, md5Pwd);
		}else if(ACTION_LOGIN.equals(action))
		{
			String md5Num = Md5Encoder.encode(values[0]);
			String md5Pwd = Md5Encoder.encode(values[1]);
			params.put(KEY_PHONE_NUM, md5Num);
			params.put(KEY_PHONE_PWD, md5Pwd);
		}
		
		return params;
	}
	
}
