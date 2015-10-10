package com.example.birdnest.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.string;


/**
 * 仅使用于本工程
 * @author ljw
 **/
public class ParseJson
{
	public static JSONArray  ParseJsonArray(String jsonstring,String  key)
	{
		JSONArray jsonArray = null; 
		try {
			JSONObject jsonObject = new JSONObject(jsonstring);
			jsonObject = new JSONObject(jsonObject.get("message").toString()) ;
			String  ArrString = jsonObject.getString(key);
		    jsonArray = new JSONArray(ArrString);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonArray ;
	}	
		
}
