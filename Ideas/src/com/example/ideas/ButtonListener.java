package com.example.ideas;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ButtonListener extends Button implements OnClickListener {

	public ButtonListener(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	String post(String urlString, String phone, String pw) throws JSONException {
		try {
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setConnectTimeout(1000);
			// 设定传送的内容类型是可序列化的java对象
			// (如果不设此项,在传送序列化对象时,当WEB服务默认的不是这种类型时可能抛java.io.EOFException)
			conn.setRequestProperty("Content-Type", "text/*;charset=utf-8");
			DataOutputStream outputStream = new DataOutputStream(
					conn.getOutputStream());
			JSONObject user = new JSONObject();
			user.put("phone", phone);
			user.put("password", pw);
			outputStream.writeBytes(user.toString());
			outputStream.flush();
			conn.connect();

			conn.setRequestMethod("Get");
			conn.setConnectTimeout(1000);
			conn.setReadTimeout(1000);
			InputStream in = conn.getInputStream();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
			StringBuilder response = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				response.append(line);
			}
			return response.toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	String post(String urlString, String username, String phone, String pw)
			throws JSONException {
		try {
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setConnectTimeout(1000);
			// 设定传送的内容类型是可序列化的java对象
			// (如果不设此项,在传送序列化对象时,当WEB服务默认的不是这种类型时可能抛java.io.EOFException)
			conn.setRequestProperty("Content-Type", "text/*;charset=utf-8");
			DataOutputStream outputStream = new DataOutputStream(
					conn.getOutputStream());
			JSONObject user = new JSONObject();
			user.put("username", username);
			user.put("phone", phone);
			user.put("password", pw);
			outputStream.writeBytes(user.toString());
			outputStream.flush();
			conn.connect();

			conn.setRequestMethod("Get");
			conn.setConnectTimeout(1000);
			conn.setReadTimeout(1000);
			InputStream in = conn.getInputStream();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
			StringBuilder response = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				response.append(line);
			}
			return response.toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
