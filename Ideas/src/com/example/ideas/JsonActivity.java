package com.example.ideas;

import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;

public class JsonActivity extends Activity{
	
	@SuppressWarnings("unused")
	private void sendRequest(){
		new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try{
					HttpClient httpClient = new DefaultHttpClient();
					HttpGet httpGet = new HttpGet(url);
					HttpResponse httpResponse = httpClient.execute(httpGet);
					if(httpResponse.getStatusLine().getStatusCode()==200){
						//请求响应成功
						HttpEntity entity = httpResponse.getEntity();
						String response = EntityUtils.toString(entity,"utf-8");
						parseJSONWithJSONObject(response);
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	private void parseJSONWithJSONObject(String jsonData){
		try{
			JSONArray jsonArray = new JSONArray(jsonData);
			for(int i = 0;i<jsonArray.length();i++){
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				String name = jsonObject.getString("name");
				String phone = jsonObject.getString("phone");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
