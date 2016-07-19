package com.silenoff.frame.parser;

import org.json.JSONException;
import org.json.JSONObject;

import com.silenoff.frame.Config;

public class JsonParser {

	/**
	 * 解析登录服务器返回的JSON数据，方法返回服务器给出的token值
	 * @param jsonResult 服务器发出的JSON数据
	 * @return token值
	 */
	public static String parseLogin(String jsonResult){
		
		try {
			JSONObject obj = new JSONObject(jsonResult);
			switch(obj.getInt(Config.KEY_STATUS)){
			case Config.RESULT_STATUS_SUCCESS:
				return obj.getString(Config.KEY_TOKEN);
			default:
				break;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 解析注册用户服务器返回的数据格式，方法返回服务器是否注册成功
	 * @param jsonResult 服务器回传的Json字符串
	 * @return 服务器是否注册成功
	 */
	public static boolean parseRegist(String jsonResult)
	{
		try{
			JSONObject obj = new JSONObject(jsonResult);
			int status = obj.getInt(Config.KEY_STATUS);
			if(status == 1)
			{
				return true;
			}else{
				return false;
			}
		}catch(JSONException je){
			je.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 解析重置用户密码服务器返回的数据格式，方法返回服务器是否重置成功
	 * @param jsonResult 服务器回传的Json字符串
	 * @return 服务器回传的Json字符串
	 */
	public static boolean parseReplace(String jsonResult)
	{
		try{
			JSONObject obj = new JSONObject(jsonResult);
			int status = obj.getInt(Config.KEY_STATUS);
			if(status == 1)
			{
				return true;
			}else{
				return false;
			}
		}catch(JSONException je){
			je.printStackTrace();
		}
		return false;
	}
	
}
